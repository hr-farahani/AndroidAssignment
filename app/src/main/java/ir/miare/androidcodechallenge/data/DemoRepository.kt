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
import ir.miare.androidcodechallenge.network.utils.extractAllLeagueEntities
import ir.miare.androidcodechallenge.network.utils.extractAllPlayerEntities
import ir.miare.androidcodechallenge.network.utils.extractAllTeamEntities
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
        val leagues = data.extractAllLeagueEntities()
        val teams = data.extractAllTeamEntities()
        val players = data.extractAllPlayerEntities()
        leagueTeamPlayerDao.insertAll(leagues, players, teams)
        emit(Unit)
    }.flowOn(ioDispatcher)

    override fun getPlayerInfo(playerId: String): Flow<PlayerWithLeagueAndTeamResource> = flow {
        val result =
            leagueTeamPlayerDao.getPlayerWithLeagueAndTeam(playerId).map { it.asExternalModel() }
        emitAll(result)
    }.flowOn(ioDispatcher)

    override fun getLeaguePlayers(): Flow<Map<LeagueResource, List<PlayerResource>>> = flow {
        val result = mutableMapOf<LeagueResource, List<PlayerResource>>()
        val leagues = leagueDao.getAllLeaguesOneShot().map { it.asExternalModel() }
        leagues.forEach { league ->
            val leaguePlayer = leagueTeamPlayerDao.getLeagueWithPlayersOneShot(league.leagueId)
            result.put(league, leaguePlayer.players.map { it.asExternalModel() })
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