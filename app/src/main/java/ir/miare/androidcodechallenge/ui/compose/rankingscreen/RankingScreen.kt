package ir.miare.androidcodechallenge.ui.compose.rankingscreen

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ir.miare.androidcodechallenge.ui.compose.components.FilterOption
import ir.miare.androidcodechallenge.ui.compose.screens.AverageGoalPerMatchUiScreen
import ir.miare.androidcodechallenge.ui.compose.screens.LeaguePlayersUiScreen
import ir.miare.androidcodechallenge.ui.compose.screens.MainScreen
import ir.miare.androidcodechallenge.ui.compose.screens.MostGoalsScoredUiScreen
import ir.miare.androidcodechallenge.ui.compose.screens.TeamLeagueRankingScree
import ir.miare.androidcodechallenge.ui.fakedata.Fake
import ir.miare.androidcodechallenge.ui.theme.AndroidCodeChallengeTheme
import timber.log.Timber

@Composable
fun RankingScreenRoute(
    modifier: Modifier = Modifier,
    rankingViewModel: RankingViewModel = hiltViewModel()
) {

    val fetchDataUiState by rankingViewModel.fetchDataUiState.collectAsStateWithLifecycle()
    val leaguePlayerUiState by rankingViewModel.leaguePlayersUiState.collectAsStateWithLifecycle()
    val playerGoalScoreUiState by rankingViewModel.playersGoalScoreUiState.collectAsStateWithLifecycle()
    val leagueWithAvgGoalUiState by rankingViewModel.leagueWithAvgGoalUiState.collectAsStateWithLifecycle()

    RankingScreen(
        modifier = modifier,
        fetchDataUiState = fetchDataUiState,
        leaguePlayerState = leaguePlayerUiState,
        playersGoalScoreUiState = playerGoalScoreUiState,
        leagueAvgGoalUiState = leagueWithAvgGoalUiState,
        onFilterClicked = {
            rankingViewModel.applyFilter(it)
        }
    )
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RankingScreen(
    modifier: Modifier = Modifier,
    fetchDataUiState: FetchDataUiState,
    leaguePlayerState: LeaguePlayersUiState,
    playersGoalScoreUiState: PlayersGoalScoreUiState,
    leagueAvgGoalUiState: LeagueAvgGoalUiState,
    onFilterClicked: (FilterOption) -> Unit
) {

    LaunchedEffect(fetchDataUiState) {
        when (fetchDataUiState) {
            is FetchDataUiState.Success -> {
                Timber.tag("SeedDatabaseWorker").i("Success Loading")
            }

            is FetchDataUiState.Loading -> {}

            else -> {}
        }
    }

    Column(modifier = modifier) {
        MainScreen(
            orientation = LocalConfiguration.current.orientation,
            reportScreen = { modifier, filterOption ->

                LaunchedEffect(filterOption) {
                    onFilterClicked(filterOption)
                }

                when (filterOption) {
                    FilterOption.TEAM_LEAGUE -> TeamLeagueRankingScree(modifier = modifier)

                    FilterOption.MOST_GOAL -> MostGoalsScoredUiScreen(
                        modifier = modifier,
                        playersGoalScoreUiState = playersGoalScoreUiState
                    )

                    FilterOption.AVERAGE_GOAL -> AverageGoalPerMatchUiScreen(
                        modifier = modifier,
                        leagueAvgGoalUiState = leagueAvgGoalUiState
                    )

                    else -> LeaguePlayersUiScreen(
                        modifier = modifier,
                        leaguePlayerState = leaguePlayerState
                    )
                }
            }
        )
    }

}


@Preview
@Composable
private fun RankingScreenPreview() {
    val ctx = LocalContext.current
    AndroidCodeChallengeTheme {
        RankingScreen(
            fetchDataUiState = FetchDataUiState.Success,
            leaguePlayerState = LeaguePlayersUiState.Success(
                data = mapOf(Fake.league to listOf(Fake.playerTeamLeague))
            ),
            playersGoalScoreUiState = PlayersGoalScoreUiState.Loading,
            leagueAvgGoalUiState = LeagueAvgGoalUiState.Loading,
            onFilterClicked = {
                Toast.makeText(ctx, it.text, Toast.LENGTH_SHORT).show()
            }
        )
    }
}