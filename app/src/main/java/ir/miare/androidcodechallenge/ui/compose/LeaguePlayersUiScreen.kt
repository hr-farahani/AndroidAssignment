package ir.miare.androidcodechallenge.ui.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ir.miare.androidcodechallenge.ui.compose.rankingscreen.LeaguePlayersUiState
import ir.miare.androidcodechallenge.ui.fakedata.Fake
import ir.miare.androidcodechallenge.ui.theme.AndroidCodeChallengeTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LeaguePlayersUiScreen(
    modifier: Modifier = Modifier,
    leaguePlayerState: LeaguePlayersUiState
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        when (leaguePlayerState) {
            is LeaguePlayersUiState.Success -> {
                leaguePlayerState.data.forEach { (league, players) ->
                    stickyHeader {
                        HorizontalDivider()
                        LeagueUiItem(league = league)
                    }

                    items(players) { player ->
                        PlayerUiItem(
                            player = player
                        )
                    }
                }
            }
            is LeaguePlayersUiState.Loading -> {
                item {
                    LoadingWheel(
                        modifier = Modifier.fillMaxSize(),
                        contentDesc = "league player loading wheel"
                    )
                }
            }
            else -> {}
        }

    }
}

@Preview
@Composable
private fun LeaguePlayersUiScreenPreview() {
    AndroidCodeChallengeTheme {
        LeaguePlayersUiScreen(
            leaguePlayerState = LeaguePlayersUiState.Success(
                data = mapOf(Fake.league to listOf(Fake.playerTeamLeague))
            )
        )
    }
}