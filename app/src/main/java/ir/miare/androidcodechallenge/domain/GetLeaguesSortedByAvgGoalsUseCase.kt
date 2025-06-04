package ir.miare.androidcodechallenge.domain

import ir.miare.androidcodechallenge.data.Repository
import ir.miare.androidcodechallenge.domain.model.LeagueWithAverageGoalsResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetLeaguesSortedByAvgGoalsUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(): Flow<List<LeagueWithAverageGoalsResource>> = flow {
        emitAll(repository.getLeaguesSortedByAvgGoals())
    }
}