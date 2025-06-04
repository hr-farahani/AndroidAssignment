package ir.miare.androidcodechallenge.ui.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ir.miare.androidcodechallenge.domain.model.PlayerWithLeagueAndTeamResource
import ir.miare.androidcodechallenge.ui.compose.rankingscreen.LeaguePlayersUiState
import ir.miare.androidcodechallenge.ui.fakedata.Fake
import ir.miare.androidcodechallenge.ui.theme.AndroidCodeChallengeTheme

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LeaguePlayersUiScreen(
    modifier: Modifier = Modifier,
    leaguePlayerState: LeaguePlayersUiState
) {

    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedPlayer by remember { mutableStateOf<PlayerWithLeagueAndTeamResource?>(null) }

    if (showBottomSheet) {
        PlayerInfoModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
        ) {
            Text(selectedPlayer?.player?.name ?: "NULL")
            Text("${selectedPlayer?.player?.totalGoal ?: "NULL"}")
            Text(selectedPlayer?.team?.name ?: "NULL")
        }
    }

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

                    items(items = players, key = { it.player.playerId }) { player ->
                        PlayerUiItem(
                            player = player,
                            onPlayerClicked = {
                                showBottomSheet = true
                                selectedPlayer = it
                            }
                        )
                    }
                }
            }

            is LeaguePlayersUiState.Error -> {}
            is LeaguePlayersUiState.Loading -> {
                item {
                    LoadingWheel(
                        modifier = Modifier.fillMaxSize(),
                        contentDesc = "league player loading wheel"
                    )
                }
            }
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