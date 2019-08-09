package wtf.qase.appskeleton.template.module

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import wtf.qase.appskeleton.template.main.MainViewModel
import wtf.qase.appskeleton.template.repo.AppPreferences

val appModule = module {
    single { AppPreferences(androidContext()) }
    viewModel { MainViewModel(get()) }
}
