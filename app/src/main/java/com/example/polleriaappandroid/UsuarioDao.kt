package com.example.polleriaappandroid

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UsuarioDao {
    @Query("SELECT * FROM usuarios WHERE username = :username AND password = :password AND estado = 1")
    fun login(username: String, password: String): Usuario?

    @Insert
    fun insertUsuario(usuario: Usuario)

    @Update
    fun updateUsuario(usuario: Usuario)

    @Delete
    fun deleteUsuario(usuario: Usuario)

    @Query("SELECT * FROM usuarios")
    fun getAllUsuarios(): List<Usuario>
}
