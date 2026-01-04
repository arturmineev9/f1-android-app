package ru.itis.f1app.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "races")
data class RaceEntity(
    @PrimaryKey val id: String,
    val name: String,
    val country: String,
    val city: String,
    val circuitName: String,
    val dateStart: String,
    val round: Int,
    val year: Int
)
