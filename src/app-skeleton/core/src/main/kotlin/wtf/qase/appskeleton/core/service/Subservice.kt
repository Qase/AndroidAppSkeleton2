package wtf.qase.appskeleton.core.service

import io.reactivex.disposables.Disposable

abstract class Subservice {

    private var disposable: Disposable? = null
    protected abstract fun subscribe(): Disposable

    @Synchronized
    fun start() {
        if (disposable == null) {
            disposable = subscribe()
        }
    }

    @Synchronized
    fun stop() {
        disposable?.dispose()
        disposable = null
    }
}
