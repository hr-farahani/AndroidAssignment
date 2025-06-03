package ir.miare.androidcodechallenge.network.model

import ir.miare.androidcodechallenge.database.model.entities.LeagueEntity
import java.util.UUID

data class League(
    val name: String,
    val country: String,
    val rank: Int,
    val totalMatches: Int,
)

fun League.toEntity(): LeagueEntity {
    return LeagueEntity(
        id = UUID.randomUUID().toString(),
        name = name,
        country = country,
        rank = rank,
        totalMatches = totalMatches
    )
}