package com.example.demofinal.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "character_table")
data class Character(
    @PrimaryKey
    var id: Int? = null,
    var name: String? = null,
    var status: String? = null,
    var species: String? = null,
    var type: String? = null,
    var gender: String? = null,
    var image: String? = null,
    var url: String? = null,
    var created: String? = null
): Serializable