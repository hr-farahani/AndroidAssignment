package ir.miare.androidcodechallenge.data

import ir.miare.androidcodechallenge.domain.model.LeagueResource
import ir.miare.androidcodechallenge.domain.model.LeagueWithAverageGoalsResource
import ir.miare.androidcodechallenge.domain.model.PlayerResource
import ir.miare.androidcodechallenge.domain.model.PlayerWithLeagueAndTeamResource
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun fetchData(): Flow<Unit>

    fun getPlayerInfo(playerId: String): Flow<PlayerWithLeagueAndTeamResource>

    fun getLeaguePlayers(): Flow<Map<LeagueResource, List<PlayerWithLeagueAndTeamResource>>>

    fun getLeaguesSortedByAvgGoals(): Flow<List<LeagueWithAverageGoalsResource>>

    fun getAllPlayersOrderByMostGoalsScored(): Flow<List<PlayerResource>>
}