package ir.miare.androidcodechallenge.domain.model

data class LeagueResource(
    val leagueId: String,
    val name: String,
    val country: String,
    val leagueRank: Int,
    val totalMatches: Int,
)
