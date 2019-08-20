package wtf.qase.appskeleton.example.repository.todo.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import wtf.qase.appskeleton.example.repository.AppDatabase
import wtf.qase.appskeleton.example.repository.todo.dto.db.Todo

@Dao
interface TodoDao {

    @Query("SELECT * FROM " + AppDatabase.TABLE_TODO)
    fun getAll(): LiveData<List<Todo>>

    @Insert
    fun insert(vararg todoList: Todo)

    @Update
    fun update(vararg todoList: Todo)

    @Delete
    fun delete(vararg todoList: Todo)
}
