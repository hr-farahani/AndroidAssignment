package ir.miare.androidcodechallenge.network.model

import ir.miare.androidcodechallenge.database.model.entities.LeagueEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class League(
    val name: String,
    val country: String,
    val rank: Int,
    @SerialName("total_matches") val totalMatches: Int,
)

fun League.toEntity(leagueId: String): LeagueEntity {
    return LeagueEntity(
        id = leagueId,
        name = name,
        country = country,
        rank = rank,
        totalMatches = totalMatches
    )
}