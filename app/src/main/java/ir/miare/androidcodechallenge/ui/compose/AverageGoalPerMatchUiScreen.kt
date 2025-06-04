package ir.miare.androidcodechallenge.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ir.miare.androidcodechallenge.ui.compose.rankingscreen.LeagueAvgGoalUiState
import ir.miare.androidcodechallenge.ui.theme.AndroidCodeChallengeTheme

@Composable
fun AverageGoalPerMatchUiScreen(
    modifier: Modifier = Modifier,
    leagueAvgGoalUiState: LeagueAvgGoalUiState
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (leagueAvgGoalUiState) {
            is LeagueAvgGoalUiState.Success -> {
                items(items = leagueAvgGoalUiState.data, key = { it.league.leagueId }) {
                    LeagueUiItem(league = it.league)
                }
            }

            is LeagueAvgGoalUiState.Error -> {}
            is LeagueAvgGoalUiState.Loading -> {
                item {
                    LoadingWheel(
                        modifier = Modifier.fillMaxSize(),
                        contentDesc = "league Avg goal loading wheel"
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun AverageGoalPerMatchUiScreenPreview() {
    AndroidCodeChallengeTheme {
        AverageGoalPerMatchUiScreen(leagueAvgGoalUiState = LeagueAvgGoalUiState.Loading)
    }
}