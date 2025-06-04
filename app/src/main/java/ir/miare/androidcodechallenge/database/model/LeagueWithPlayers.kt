package ir.miare.androidcodechallenge.database.model

import androidx.room.Embedded
import androidx.room.Relation
import ir.miare.androidcodechallenge.database.model.entities.LeagueEntity
import ir.miare.androidcodechallenge.database.model.entities.PlayerEntity

data class LeagueWithPlayers(
    @Embedded val league: LeagueEntity,
    @Relation(
        parentColumn = "league_id",
        entityColumn = "league_id"
    )
    val players: List<PlayerEntity>
)