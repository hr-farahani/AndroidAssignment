package ir.miare.androidcodechallenge.database.model

import androidx.room.Embedded
import androidx.room.Relation
import ir.miare.androidcodechallenge.database.model.entities.LeagueEntity
import ir.miare.androidcodechallenge.database.model.entities.PlayerEntity
import ir.miare.androidcodechallenge.database.model.entities.TeamEntity
import ir.miare.androidcodechallenge.database.model.entities.asExternalModel
import ir.miare.androidcodechallenge.domain.model.PlayerWithLeagueAndTeamResource

data class PlayerWithLeagueAndTeam(
    @Embedded val player: PlayerEntity,
    @Relation(
        parentColumn = "league_id",
        entityColumn = "league_id"
    )
    val league: LeagueEntity,
    @Relation(
        parentColumn = "team_id",
        entityColumn = "team_id"
    )
    val team: TeamEntity
)

fun PlayerWithLeagueAndTeam.asExternalModel(): PlayerWithLeagueAndTeamResource {
    return PlayerWithLeagueAndTeamResource(
        player = player.asExternalModel(),
        league = league.asExternalModel(),
        team = team.asExternalModel()
    )
}