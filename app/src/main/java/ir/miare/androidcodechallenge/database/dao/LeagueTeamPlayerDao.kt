package ir.miare.androidcodechallenge.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import ir.miare.androidcodechallenge.database.model.LeagueWithAverageGoals
import ir.miare.androidcodechallenge.database.model.LeagueWithPlayers
import ir.miare.androidcodechallenge.database.model.PlayerWithLeagueAndTeam
import ir.miare.androidcodechallenge.database.model.TeamWithPlayers
import ir.miare.androidcodechallenge.database.model.entities.LeagueEntity
import ir.miare.androidcodechallenge.database.model.entities.PlayerEntity
import ir.miare.androidcodechallenge.database.model.entities.TeamEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LeagueTeamPlayerDao {

    @Transaction
    @Query("SELECT * FROM league_tbl WHERE league_id = :leagueId")
    fun getLeagueWithPlayers(leagueId: String): Flow<LeagueWithPlayers>

    @Transaction
    @Query("SELECT * FROM league_tbl WHERE league_id = :leagueId")
    suspend fun getLeagueWithPlayersOneShot(leagueId: String): LeagueWithPlayers

    @Transaction
    @Query("SELECT * FROM team_tbl WHERE team_id = :teamId")
    fun getTeamWithPlayers(teamId: String): Flow<TeamWithPlayers>

    @Transaction
    @Query("SELECT * FROM player_tbl WHERE player_id = :playerId")
    fun getPlayerWithLeagueAndTeam(playerId: String): Flow<PlayerWithLeagueAndTeam>


    @Query(
        """
    SELECT league.*, 
           IFNULL(SUM(player.totalGoal) * 1.0 / league.totalMatches, 0) AS avgGoalsPerMatch
    FROM league_tbl league
    LEFT JOIN player_tbl player ON league.league_id = player.league_id
    GROUP BY league.league_id
    ORDER BY avgGoalsPerMatch DESC
    """
    )
    fun getLeaguesSortedByAvgGoals(): Flow<List<LeagueWithAverageGoals>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun  insertAllLeagues(leagues: List<LeagueEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPlayers(players: List<PlayerEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTeams(teams: List<TeamEntity>)

    @Transaction
    suspend fun insertAll(
        leagues: List<LeagueEntity>,
        players: List<PlayerEntity>,
        teams: List<TeamEntity>
    ) {
        insertAllLeagues(leagues)
        insertAllPlayers(players)
        insertAllTeams(teams)
    }
}