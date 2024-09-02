package com.example.polleriaappandroid

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class Usuario(
    @PrimaryKey(autoGenerate = true) val idusuario: Int = 0,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "nombre") val nombre: String,
    @ColumnInfo(name = "apellido") val apellido: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "telefono") val telefono: String,
    @ColumnInfo(name = "direccion") val direccion: String?,
    @ColumnInfo(name = "estado") val estado: Boolean = true // Por defecto "Activo"
)
