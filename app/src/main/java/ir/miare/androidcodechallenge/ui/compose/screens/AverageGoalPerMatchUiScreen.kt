package ir.miare.androidcodechallenge.ui.compose.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.miare.androidcodechallenge.ui.compose.components.LeagueUiItem
import ir.miare.androidcodechallenge.ui.compose.components.LoadingWheel
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
                    LeagueUiItem(
                        modifier = Modifier.padding(4.dp),
                        league = it.league
                    ) {
                        Row(
                            modifier = modifier
                                .padding(8.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Text(
                                text = "Avg Goals Per Match:",
                                fontSize = 8.sp
                            )
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "${it.avgGoalsPerMatch}",
                                textAlign = TextAlign.Center,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
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