package wtf.qase.appskeleton.template.di

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import wtf.qase.appskeleton.template.main.DummyViewModel
import wtf.qase.appskeleton.template.repository.AppPreferences

val appModule = module {
    single { AppPreferences(androidContext()) }
    viewModel { DummyViewModel(get()) }
}
