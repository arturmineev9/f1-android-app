package ru.itis.f1app.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.itis.f1app.core.database.entity.RaceEntity

@Dao
interface RacesDao {
    @Query("SELECT * FROM races WHERE year = :year ORDER BY dateStart ASC")
    fun getRacesFlow(year: Int): Flow<List<RaceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(races: List<RaceEntity>)

    @Query("DELETE FROM races WHERE year = :year")
    suspend fun clearRacesByYear(year: Int)
}
