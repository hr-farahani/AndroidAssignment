package ir.miare.androidcodechallenge.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import ir.miare.androidcodechallenge.database.model.LeagueWithAverageGoals
import ir.miare.androidcodechallenge.database.model.LeagueWithPlayers
import ir.miare.androidcodechallenge.database.model.PlayerWithLeagueAndTeam
import ir.miare.androidcodechallenge.database.model.TeamWithPlayers
import kotlinx.coroutines.flow.Flow

@Dao
interface LeagueTeamPlayerDao {

    @Transaction
    @Query("SELECT * FROM league_tbl WHERE league_id = :leagueId")
    fun getLeagueWithPlayers(leagueId: Int): Flow<LeagueWithPlayers>

    @Transaction
    @Query("SELECT * FROM team_tbl WHERE team_id = :teamId")
    fun getTeamWithPlayers(teamId: Int): Flow<TeamWithPlayers>

    @Transaction
    @Query("SELECT * FROM player_tbl WHERE player_id = :playerId")
    fun getPlayerWithLeagueAndTeam(playerId: Int): Flow<PlayerWithLeagueAndTeam>


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
}