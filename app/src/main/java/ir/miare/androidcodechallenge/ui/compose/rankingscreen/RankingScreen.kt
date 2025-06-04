package ir.miare.androidcodechallenge.ui.compose.rankingscreen

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ir.miare.androidcodechallenge.ui.compose.FilterOption
import ir.miare.androidcodechallenge.ui.compose.FilterPanelComponent
import ir.miare.androidcodechallenge.ui.compose.LeagueUiItem
import ir.miare.androidcodechallenge.ui.compose.PlayerUiItem
import ir.miare.androidcodechallenge.ui.theme.AndroidCodeChallengeTheme
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import ir.miare.androidcodechallenge.ui.compose.LoadingWheel
import ir.miare.androidcodechallenge.ui.fakedata.Fake
import timber.log.Timber

@Composable
fun RankingScreenRoute(
    modifier: Modifier = Modifier,
    rankingViewModel: RankingViewModel = hiltViewModel()
) {

    val fetchDataUiState by rankingViewModel.fetchDataUiState.collectAsStateWithLifecycle()
    val leaguePlayerUiState by rankingViewModel.leaguePlayersUiState.collectAsStateWithLifecycle()

    RankingScreen(
        modifier = modifier,
        fetchDataUiState = fetchDataUiState,
        leaguePlayerState = leaguePlayerUiState,
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
    onFilterClicked: (FilterOption) -> Unit
) {

    LaunchedEffect(fetchDataUiState) {
        when(fetchDataUiState) {
            is FetchDataUiState.Success -> {
                Timber.tag("SeedDatabaseWorker").i("Success Loading")
            }
            is FetchDataUiState.Loading -> {

            }
            else -> {}
        }
    }

    Column(modifier = modifier) {
        FilterPanelComponent(onFilterClicked = onFilterClicked)

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
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
            onFilterClicked = {
                Toast.makeText(ctx, it.text, Toast.LENGTH_SHORT).show()
            }
        )
    }
}