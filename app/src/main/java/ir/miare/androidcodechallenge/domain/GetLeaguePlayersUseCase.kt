package ir.miare.androidcodechallenge.domain

import ir.miare.androidcodechallenge.data.Repository
import ir.miare.androidcodechallenge.domain.model.LeagueResource
import ir.miare.androidcodechallenge.domain.model.PlayerResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetLeaguePlayersUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(): Flow<Map<LeagueResource, List<PlayerResource>>> = flow {
        emitAll(repository.getLeaguePlayers())
    }
}