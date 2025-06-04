package ir.miare.androidcodechallenge.database.model

import androidx.room.Embedded
import androidx.room.Relation
import ir.miare.androidcodechallenge.database.model.entities.PlayerEntity
import ir.miare.androidcodechallenge.database.model.entities.TeamEntity

data class TeamWithPlayers(
    @Embedded val team: TeamEntity,
    @Relation(
        parentColumn = "team_id",
        entityColumn = "team_id"
    )
    val players: List<PlayerEntity>
)