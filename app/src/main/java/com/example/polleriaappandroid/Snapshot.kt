package com.example.polleriaappandroid

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Snapshot(var id: String = "",
                    var nombre: String = "",
                    var categoria: String = "",
                    var unidadmedida: String = "",
                    var presentacion: String = "",
                    var precio: String = "",
                    var photoUrl: String = "",
                    var likeList: Map<String, Boolean> = mutableMapOf())
