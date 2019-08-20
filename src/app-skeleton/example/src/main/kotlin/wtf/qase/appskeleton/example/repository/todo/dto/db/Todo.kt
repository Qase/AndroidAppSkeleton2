package wtf.qase.appskeleton.example.repository.todo.dto.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import wtf.qase.appskeleton.example.repository.AppDatabase
import java.io.Serializable

@Entity(tableName = AppDatabase.TABLE_TODO)
data class Todo(

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "desc")
    val desc: String,

    @ColumnInfo(name = "tags")
    val tags: String = "",

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
) : Serializable
