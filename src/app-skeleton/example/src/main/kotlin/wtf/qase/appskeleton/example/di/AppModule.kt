package wtf.qase.appskeleton.example.di

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import wtf.qase.appskeleton.example.main.settings.SettingsViewModel
import wtf.qase.appskeleton.example.main.todos.CreateTodoViewModel
import wtf.qase.appskeleton.example.main.todos.TodoDetailViewModel
import wtf.qase.appskeleton.example.main.todos.TodoListViewModel
import wtf.qase.appskeleton.example.repository.AppPreferences
import wtf.qase.appskeleton.example.service.WatchdogSubservice

val appModule = module {
    single { AppPreferences(androidContext()) }
    single { WatchdogSubservice() }
    viewModel { SettingsViewModel(get(), get()) }
    viewModel { TodoListViewModel(get()) }
    viewModel { TodoDetailViewModel() }
    viewModel { CreateTodoViewModel(get()) }
}
