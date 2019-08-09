package wtf.qase.appskeleton.example.repo.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import wtf.qase.appskeleton.example.repo.AppDatabase
import wtf.qase.appskeleton.example.repo.model.Todo

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