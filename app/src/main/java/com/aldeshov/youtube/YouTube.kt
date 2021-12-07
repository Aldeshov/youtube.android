package com.aldeshov.youtube

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.aldeshov.youtube.service.database.Database
import com.aldeshov.youtube.service.database.models.LocalUser
import com.aldeshov.youtube.service.apiModule
import com.aldeshov.youtube.service.appModule
import com.aldeshov.youtube.service.dbModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class YouTube: Application() {
    private var database: Database? = null
    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder(this, Database::class.java, "user")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()

        // Initializing User Information and Database
        if (database!!.userDao().getUser() == null) {
            database!!.userDao().insert(LocalUser(id = 1))
            Log.i("Database", "User Initialized: code = 1")
        }

        // Start Koin
        startKoin {
            androidLogger()
            androidContext(this@YouTube)
            modules(dbModule, apiModule, appModule)
        }
    }

    fun getDatabase(): Database? {
        return database
    }

    companion object {
        lateinit var instance: YouTube
    }
}