package ir.miare.androidcodechallenge.network.utils

import ir.miare.androidcodechallenge.database.model.entities.LeagueEntity
import ir.miare.androidcodechallenge.database.model.entities.PlayerEntity
import ir.miare.androidcodechallenge.database.model.entities.TeamEntity
import ir.miare.androidcodechallenge.network.model.FakeData
import ir.miare.androidcodechallenge.network.model.toEntity
import java.util.UUID

fun List<FakeData>.extractAllLeagueEntities(): List<LeagueEntity> {
    return map { it.league.toEntity() }
}

private fun FakeData.extractPlayerEntities(): List<PlayerEntity> {
    return players.map {
        PlayerEntity(
            id = UUID.randomUUID().toString(),
            leagueId = league.toEntity().id,
            teamId = it.team.toEntity().id,
            name = it.name,
            totalGoal = it.totalGoal
        )
    }
}

fun List<FakeData>.extractAllPlayerEntities(): List<PlayerEntity> {
    return flatMap { it.extractPlayerEntities() }
}

fun List<FakeData>.extractAllTeamEntities(): List<TeamEntity> {
    return flatMap { it.players.map { it.team.toEntity() } }
}
