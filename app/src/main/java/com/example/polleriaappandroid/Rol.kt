package com.example.polleriaappandroid

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "roles")
data class Rol(
    @PrimaryKey val idrol: Int,
    @ColumnInfo(name = "nombre") val nombre: String,
    @ColumnInfo(name = "estado") val estado: String
)