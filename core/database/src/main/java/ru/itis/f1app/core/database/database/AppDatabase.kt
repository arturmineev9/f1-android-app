package ru.itis.f1app.core.database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.itis.f1app.core.database.dao.RacesDao
import ru.itis.f1app.core.database.dao.UserDao
import ru.itis.f1app.core.database.entity.RaceEntity
import ru.itis.f1app.core.database.entity.UserEntity

@Database(entities = [UserEntity::class, RaceEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun racesDao(): RacesDao
}
