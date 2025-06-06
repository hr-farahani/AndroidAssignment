package ir.miare.androidcodechallenge.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import ir.miare.androidcodechallenge.ui.compose.components.LoadingWheel
import ir.miare.androidcodechallenge.ui.compose.components.PlayerInfoModalBottomSheet
import ir.miare.androidcodechallenge.ui.compose.components.PlayerUiItem
import ir.miare.androidcodechallenge.ui.compose.rankingscreen.PlayersGoalScoreUiState
import ir.miare.androidcodechallenge.ui.theme.AndroidCodeChallengeTheme

@Composable
fun MostGoalsScoredUiScreen(
    modifier: Modifier = Modifier,
    playersGoalScoreUiState: PlayersGoalScoreUiState
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
        when (playersGoalScoreUiState) {
            is PlayersGoalScoreUiState.Success -> {
                items(items = playersGoalScoreUiState.data, key = { it.player.playerId }) {
                    PlayerUiItem(
                        player = it,
                        onPlayerClicked = {
                            showBottomSheet = true
                            selectedPlayer = it
                        }
                    )
                }
            }

            is PlayersGoalScoreUiState.Error -> {}
            is PlayersGoalScoreUiState.Loading -> {
                item {
                    LoadingWheel(
                        modifier = Modifier.fillMaxSize(),
                        contentDesc = "player goals loading wheel"
                    )
                }
            }
        }
    }
}


@Preview
@Composable
private fun MostGoalsScoredUiScreenPreview() {
    AndroidCodeChallengeTheme {
        MostGoalsScoredUiScreen(
            playersGoalScoreUiState = PlayersGoalScoreUiState.Loading
        )
    }
}