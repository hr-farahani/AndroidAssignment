package ir.miare.androidcodechallenge.network.model

import ir.miare.androidcodechallenge.database.model.entities.TeamEntity
import java.util.UUID

data class Team(
    val name: String,
    val rank: Int
)

fun Team.toEntity(): TeamEntity {
    return TeamEntity(
        id = UUID.randomUUID().toString(),
        name = name,
        rank = rank
    )
}