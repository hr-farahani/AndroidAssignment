package ir.miare.androidcodechallenge.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ir.miare.androidcodechallenge.database.model.entities.PlayerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(player: PlayerEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(players: List<PlayerEntity>)

    @Query("SELECT * FROM player_tbl WHERE player_id = :id")
    suspend fun getPlayerById(id: String): PlayerEntity

    @Query("SELECT * FROM player_tbl")
    fun getAllPlayers(): Flow<List<PlayerEntity>>

    @Query("SELECT * FROM player_tbl ORDER BY totalGoal DESC")
    fun getAllPlayersOrderByMostGoalsScored(): Flow<List<PlayerEntity>>
}