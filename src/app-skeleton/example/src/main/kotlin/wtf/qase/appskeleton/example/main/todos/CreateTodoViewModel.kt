package wtf.qase.appskeleton.example.main.todos

import androidx.lifecycle.ViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import wtf.qase.appskeleton.example.repo.model.Todo
import wtf.qase.appskeleton.example.repo.dao.TodoDao

class CreateTodoViewModel(
    private val todoDao: TodoDao
): ViewModel() {

    fun createTodo(title: String, desc: String): Single<Unit> {
        if (title.isBlank()) {
            return Single.error(Throwable("Title is blank"))
        }

        return Single.fromCallable {
            todoDao.insert(Todo(title, desc))
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
