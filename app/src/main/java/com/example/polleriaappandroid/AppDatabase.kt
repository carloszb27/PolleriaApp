package com.example.polleriaappandroid

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Rol::class, Usuario::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
}
