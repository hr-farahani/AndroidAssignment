package ir.miare.androidcodechallenge.network.model

import ir.miare.androidcodechallenge.database.model.entities.LeagueEntity
import ir.miare.androidcodechallenge.database.model.entities.PlayerEntity
import ir.miare.androidcodechallenge.database.model.entities.TeamEntity
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class FakeData(
    var league: League,
    var players: List<Player>
)

data class DataEntities(
    val leagues: List<LeagueEntity>,
    val teams: List<TeamEntity>,
    val players: List<PlayerEntity>
)

fun List<FakeData>.extractDataEntities(): DataEntities {
    val leagueIdMap = mutableMapOf<String, String>()
    val teamIdMap = mutableMapOf<String, String>()

    val leagues = mutableListOf<LeagueEntity>()
    val teams = mutableListOf<TeamEntity>()
    val players = mutableListOf<PlayerEntity>()

    forEach { fakeData ->
        val leagueName = fakeData.league.name
        val leagueId = leagueIdMap.getOrPut(leagueName) {
            val id = UUID.randomUUID().toString()
            leagues.add(fakeData.league.toEntity(id))
            id
        }

        fakeData.players.forEach { player ->
            val teamName = player.team.name
            val teamId = teamIdMap.getOrPut(teamName) {
                val id = UUID.randomUUID().toString()
                teams.add(player.team.toEntity(id))
                id
            }

            players.add(
                PlayerEntity(
                    id = UUID.randomUUID().toString(),
                    leagueId = leagueId,
                    teamId = teamId,
                    name = player.name,
                    totalGoal = player.totalGoal
                )
            )
        }
    }

    return DataEntities(leagues, teams, players)
}
