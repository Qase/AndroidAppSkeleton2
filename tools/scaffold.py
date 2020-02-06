#!/usr/bin/env python3

from os import mkdir, path as os_path, rename
from shutil import copytree, ignore_patterns, rmtree
import click
import re
import subprocess
import sys


# Allow only Python3
assert sys.version_info.major >= 3


@click.group()
def cli():
    pass


@cli.group()
def new():
    pass


@new.command()
@click.argument('path')
@click.argument('package')
def project(path, package):
    """Create new project from template
    """
    dir_path = os_path.dirname(os_path.realpath(__file__))
    dir_path = os_path.abspath(dir_path + '/../')
    if os_path.abspath(path).startswith(dir_path):
        raise Exception("Can't create project inside App Skeleton directory!")

    copytree(dir_path, path, ignore=ignore_patterns(
        '.git', '.gradle', '.idea', 'build', 'example',
        '*.iml', '*.swp',
    ))

    base = path + '/src/app-skeleton'

    # Rename package in all files
    subprocess.call([
        'find', path, '-type', 'f',
        '-exec', 'sed', '-i', '-e', 's/wtf.qase.appskeleton.template/{}/g'.format(package), '{}', ';'
    ])

    # Delete :example project, rename :template project
    do_in_file(
        base + '/settings.gradle',
        lambda c: re.sub(
            r'\':template\'',
            '\':app\'',
            re.sub(r'(, )?\':example\'(, )?', '', c),
        ),
    )

    # Create new package structure
    for n in ['main', 'test']:
        src = base + '/template/src/{}/kotlin/'.format(n)
        parent = src
        for d in package.split('.'):
            parent += d + '/'
            mkdir(parent)

        # Move to new package structure
        rename(src + '/wtf/qase/appskeleton/template', parent)
        rmtree(src + '/wtf/')

    # Get name from last directory name in path
    name = path.rsplit('/')[-1]
    rename(base + '/template', base + '/app')
    rename(base, path + '/src/' + name)

    print('Change path to SDK and NDK in ' + path + '/src/' + name + '/local.properties')


@new.command()
@click.argument('path')
@click.argument('name')
def fragment(path, name):
    raise Exception('TODO: Implement me')
    try:
        m = re.match('package="([^"]+)"', subprocess.check_output(
            'grep "package" ' + path + '/src/main/AndroidManifest.xml',
            shell=True,
        ).decode('utf-8').strip())
        if m:
            package = m.group(1)
            print(package)
    except subprocess.CalledProcessError as e:
        print(e)


def do_in_file(filename, fn):
    """Read content of file, call callback function and write content to file
    """
    with open(filename, 'r') as f:
        content = f.read()

    content = fn(content)

    with open(filename, 'w') as f:
        f.write(content)


if __name__ == '__main__':
    cli()
