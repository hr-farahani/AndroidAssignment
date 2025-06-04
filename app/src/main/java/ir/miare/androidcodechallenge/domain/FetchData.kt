package ir.miare.androidcodechallenge.domain

import ir.miare.androidcodechallenge.data.Repository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class FetchData @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(): Flow<Unit> = flow {
        emitAll(repository.fetchData())
    }
}