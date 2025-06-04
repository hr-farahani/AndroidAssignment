package ir.miare.androidcodechallenge.network.model

import ir.miare.androidcodechallenge.database.model.entities.TeamEntity
import kotlinx.serialization.Serializable

@Serializable
data class Team(
    val name: String,
    val rank: Int
)

fun Team.toEntity(teamId: String): TeamEntity {
    return TeamEntity(
        id = teamId,
        name = name,
        rank = rank
    )
}