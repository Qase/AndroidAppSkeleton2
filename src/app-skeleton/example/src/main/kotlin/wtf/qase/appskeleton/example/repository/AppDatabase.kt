package wtf.qase.appskeleton.example.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import wtf.qase.appskeleton.example.repository.todo.dao.TodoDao
import wtf.qase.appskeleton.example.repository.todo.dto.db.Todo

@Database(
    entities = [Todo::class],
    version = 2,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        val MIGRATIONS = arrayOf<Migration>(
            object : Migration(1, 2) {
                override fun migrate(database: SupportSQLiteDatabase) {
                    database.execSQL("ALTER TABLE `todo` ADD COLUMN `tags` TEXT DEFAULT '' NOT NULL")
                }
            }
        )
        const val DB_NAME = "db"
        const val TABLE_TODO = "todo"
    }

    abstract fun todoDao(): TodoDao
}
