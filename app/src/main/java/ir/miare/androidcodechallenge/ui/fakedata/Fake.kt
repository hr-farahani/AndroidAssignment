package ir.miare.androidcodechallenge.ui.fakedata

import ir.miare.androidcodechallenge.domain.model.LeagueResource
import ir.miare.androidcodechallenge.domain.model.PlayerResource
import ir.miare.androidcodechallenge.domain.model.PlayerWithLeagueAndTeamResource
import ir.miare.androidcodechallenge.domain.model.TeamResource

object Fake {

    val league = LeagueResource(
        leagueId = "",
        name = "Serie A",
        country = "Italy",
        leagueRank = 3,
        totalMatches = 32,
    )

    val player = PlayerResource(
        playerId = "",
        name = "Edin Dzeko",
        totalGoal = 17
    )

    val team = TeamResource(
        teamId = "",
        name = "Inter",
        teamRank = 2
    )

    val playerTeamLeague = PlayerWithLeagueAndTeamResource(
        player = player,
        league = league,
        team = team
    )


}
