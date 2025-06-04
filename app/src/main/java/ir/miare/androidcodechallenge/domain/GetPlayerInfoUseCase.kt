package ir.miare.androidcodechallenge.domain

import ir.miare.androidcodechallenge.data.Repository
import ir.miare.androidcodechallenge.domain.model.PlayerWithLeagueAndTeamResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPlayerInfoUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(playerId: String): Flow<PlayerWithLeagueAndTeamResource> = flow {
        emitAll(repository.getPlayerInfo(playerId))
    }

    fun getAllPlayersOrderByGoalsScored(): Flow<List<PlayerWithLeagueAndTeamResource>> = flow {
        emitAll(repository.getAllPlayersOrderByMostGoalsScored())
    }
}