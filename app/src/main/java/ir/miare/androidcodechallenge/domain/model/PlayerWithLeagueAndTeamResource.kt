package ir.miare.androidcodechallenge.domain.model

data class PlayerWithLeagueAndTeamResource(
    val player: PlayerResource,
    val league: LeagueResource,
    val team: TeamResource
)
