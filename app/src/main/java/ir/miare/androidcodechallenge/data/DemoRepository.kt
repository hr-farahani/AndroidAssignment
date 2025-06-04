package ir.miare.androidcodechallenge.data

import ir.miare.androidcodechallenge.common.di.Dis
import ir.miare.androidcodechallenge.common.di.Dispatcher
import ir.miare.androidcodechallenge.database.dao.LeagueDao
import ir.miare.androidcodechallenge.database.dao.LeagueTeamPlayerDao
import ir.miare.androidcodechallenge.database.dao.PlayerDao
import ir.miare.androidcodechallenge.database.model.asExternalModel
import ir.miare.androidcodechallenge.database.model.asExternalResource
import ir.miare.androidcodechallenge.database.model.entities.asExternalModel
import ir.miare.androidcodechallenge.domain.model.LeagueResource
import ir.miare.androidcodechallenge.domain.model.LeagueWithAverageGoalsResource
import ir.miare.androidcodechallenge.domain.model.PlayerResource
import ir.miare.androidcodechallenge.domain.model.PlayerWithLeagueAndTeamResource
import ir.miare.androidcodechallenge.network.demo.DemoNetworkDataSource
import ir.miare.androidcodechallenge.network.model.extractDataEntities
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DemoRepository @Inject constructor(
    @Dispatcher(Dis.IO) private val ioDispatcher: CoroutineDispatcher,
    private val datasource: DemoNetworkDataSource,
    private val leagueDao: LeagueDao,
    private val playerDao: PlayerDao,
    private val leagueTeamPlayerDao: LeagueTeamPlayerDao
) : Repository {

    override fun fetchData(): Flow<Unit> = flow {
        val data = datasource.getData()
        val meta = data.extractDataEntities()
        leagueTeamPlayerDao.insertAll(meta.leagues, meta.players, meta.teams)
        emit(Unit)
    }.flowOn(ioDispatcher)

    override fun getPlayerInfo(playerId: String): Flow<PlayerWithLeagueAndTeamResource> = flow {
        val result =
            leagueTeamPlayerDao.getPlayerWithLeagueAndTeam(playerId).map { it.asExternalModel() }
        emitAll(result)
    }.flowOn(ioDispatcher)

    override fun getLeaguePlayers(): Flow<Map<LeagueResource, List<PlayerWithLeagueAndTeamResource>>> = flow {
        val result = mutableMapOf<LeagueResource, List<PlayerWithLeagueAndTeamResource>>()
        val leagues = leagueDao.getAllLeaguesOneShot().map { it.asExternalModel() }
        leagues.forEach { league ->
            val leaguePlayer = leagueTeamPlayerDao.getLeagueWithPlayersOneShot(league.leagueId)
            result.put(league, leaguePlayer.players.map {
                leagueTeamPlayerDao.getPlayerWithLeagueAndTeamOneShot(it.id).asExternalModel()
            })
        }
        emit(result)
    }.flowOn(ioDispatcher)

    override fun getLeaguesSortedByAvgGoals(): Flow<List<LeagueWithAverageGoalsResource>> = flow {
        val result = leagueTeamPlayerDao.getLeaguesSortedByAvgGoals()
            .map { it.map { it.asExternalResource() } }
        emitAll(result)
    }.flowOn(ioDispatcher)

    override fun getAllPlayersOrderByMostGoalsScored(): Flow<List<PlayerResource>> = flow {
        val result =
            playerDao.getAllPlayersOrderByMostGoalsScored().map { it.map { it.asExternalModel() } }
        emitAll(result)
    }.flowOn(ioDispatcher)
}