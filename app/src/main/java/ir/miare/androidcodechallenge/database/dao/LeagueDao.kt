package ir.miare.androidcodechallenge.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ir.miare.androidcodechallenge.database.model.entities.LeagueEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LeagueDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(league: LeagueEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun  insertAll(leagues: List<LeagueEntity>)

    @Query("SELECT * FROM league_tbl WHERE league_id = :id")
    suspend fun getLeagueById(id: String): LeagueEntity

    @Query("SELECT * FROM league_tbl")
    fun getAllLeagues(): Flow<List<LeagueEntity>>

    @Query("SELECT * FROM league_tbl")
    suspend fun getAllLeaguesOneShot(): List<LeagueEntity>
}