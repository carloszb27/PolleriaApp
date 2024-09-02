package com.example.polleriaappandroid

import android.app.Application
import androidx.room.Room
import com.google.firebase.FirebaseApp

class PolleriaApplication : Application() {
    companion object {
        lateinit var database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        database = Room.databaseBuilder(this, AppDatabase::class.java, "polleria_db").build()
    }
}