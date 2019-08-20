package wtf.qase.appskeleton.example.main.todos

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import wtf.qase.appskeleton.example.repository.todo.dto.db.Todo
import wtf.qase.appskeleton.example.repository.todo.dao.TodoDao

class TodoListViewModel(
    private val todoDao: TodoDao
) : ViewModel() {

    fun getAllTodos(): LiveData<List<Todo>> {
        return todoDao.getAll()
    }

    fun deleteTodo(todo: Todo): Single<Unit> {
        return Single.fromCallable {
            todoDao.delete(todo)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
