package ir.miare.androidcodechallenge.database.model

import androidx.room.Embedded
import ir.miare.androidcodechallenge.database.model.entities.LeagueEntity

data class LeagueWithAverageGoals(
    @Embedded val league: LeagueEntity,
    val avgGoalsPerMatch: Double
)