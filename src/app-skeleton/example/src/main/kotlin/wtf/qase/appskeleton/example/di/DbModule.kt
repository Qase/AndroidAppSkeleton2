package wtf.qase.appskeleton.example.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import wtf.qase.appskeleton.example.repository.AppDatabase

val dbModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            AppDatabase.DB_NAME
        )
            // TODO Remove fallback to destructive migration
            .fallbackToDestructiveMigration()
            .addMigrations(*AppDatabase.MIGRATIONS)
            .build()
    }

    single {
        get<AppDatabase>().todoDao()
    }
}
