package ir.miare.androidcodechallenge.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ir.miare.androidcodechallenge.ui.compose.rankingscreen.PlayersGoalScoreUiState
import ir.miare.androidcodechallenge.ui.theme.AndroidCodeChallengeTheme

@Composable
fun MostGoalsScoredUiScreen(
    modifier: Modifier = Modifier,
    playersGoalScoreUiState: PlayersGoalScoreUiState
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (playersGoalScoreUiState) {
            is PlayersGoalScoreUiState.Success -> {
                items(items = playersGoalScoreUiState.data, key = { it.player.playerId }) {
                    PlayerUiItem(player = it)
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