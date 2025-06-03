package ir.miare.androidcodechallenge.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ir.miare.androidcodechallenge.database.model.entities.TeamEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(team: TeamEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(teams: List<TeamEntity>)

    @Query("SELECT * FROM team_tbl WHERE team_id = :id")
    suspend fun getTeamById(id: String): TeamEntity

    @Query("SELECT * FROM team_tbl")
    fun getAllTeams(): Flow<List<TeamEntity>>
}