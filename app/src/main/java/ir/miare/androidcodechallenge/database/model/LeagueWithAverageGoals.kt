package ir.miare.androidcodechallenge.database.model

import androidx.room.Embedded
import ir.miare.androidcodechallenge.database.model.entities.LeagueEntity
import ir.miare.androidcodechallenge.database.model.entities.asExternalModel
import ir.miare.androidcodechallenge.domain.model.LeagueWithAverageGoalsResource

data class LeagueWithAverageGoals(
    @Embedded val league: LeagueEntity,
    val avgGoalsPerMatch: Double
)

fun LeagueWithAverageGoals.asExternalResource(): LeagueWithAverageGoalsResource {
    return LeagueWithAverageGoalsResource(
        league = league.asExternalModel(),
        avgGoalsPerMatch = avgGoalsPerMatch
    )
}