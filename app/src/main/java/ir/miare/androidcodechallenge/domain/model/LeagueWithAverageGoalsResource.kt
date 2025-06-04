package ir.miare.androidcodechallenge.domain.model

data class LeagueWithAverageGoalsResource(
    val league: LeagueResource,
    val avgGoalsPerMatch: Double
)
