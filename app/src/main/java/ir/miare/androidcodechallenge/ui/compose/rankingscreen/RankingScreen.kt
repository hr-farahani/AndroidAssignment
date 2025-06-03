package ir.miare.androidcodechallenge.ui.compose.rankingscreen

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import ir.miare.androidcodechallenge.ui.compose.FakeLeague
import ir.miare.androidcodechallenge.ui.compose.FakePlayer
import ir.miare.androidcodechallenge.ui.compose.FilterOption
import ir.miare.androidcodechallenge.ui.compose.FilterPanelComponent
import ir.miare.androidcodechallenge.ui.compose.LeagueUiItem
import ir.miare.androidcodechallenge.ui.compose.PlayerUiItem
import ir.miare.androidcodechallenge.ui.theme.AndroidCodeChallengeTheme

data class FakeData(
    var league: FakeLeague = FakeLeague(),
    var players: List<FakePlayer> = listOf(FakePlayer(), FakePlayer())
)

@Composable
fun RankingScreenRoute(
    modifier: Modifier = Modifier,
    rankingViewModel: RankingViewModel = hiltViewModel()
) {
    RankingScreen(
        modifier = modifier,
        data = mapOf(
            FakeLeague() to listOf(FakePlayer(), FakePlayer()),
            FakeLeague() to listOf(FakePlayer(), FakePlayer())
        ),
        onFilterClicked = {
            rankingViewModel.applyFilter(it)
        }
    )
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RankingScreen(
    modifier: Modifier = Modifier,
    data : Map<FakeLeague, List<FakePlayer>>,
    onFilterClicked: (FilterOption) -> Unit
) {

    Column(modifier = modifier) {
        FilterPanelComponent(onFilterClicked = onFilterClicked)
        LazyColumn {
            data.forEach { (league, players) ->
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
    }

}




@Preview
@Composable
private fun RankingScreenPreview() {
    val ctx = LocalContext.current
    AndroidCodeChallengeTheme {
        val f1 = FakeLeague()
        val f2 = f1.copy(name = "FF2", country = "kk", rank = 9, totalMatches = 0)
        val f3 = f1.copy(name = "FF3", country = "kk", rank = 9, totalMatches = 0)
        val f4 = f1.copy(name = "FF4", country = "kk", rank = 9, totalMatches = 0)
        val f5 = f1.copy(name = "FF5", country = "kk", rank = 9, totalMatches = 0)
        val f6 = f1.copy(name = "FF6", country = "kk", rank = 9, totalMatches = 0)
        val f7 = f1.copy(name = "FF6", country = "kk", rank = 9, totalMatches = 0)
        val f8 = f1.copy(name = "FF6", country = "kk", rank = 9, totalMatches = 0)
        val f9 = f1.copy(name = "FF6", country = "kk", rank = 9, totalMatches = 0)
        val f10 = f1.copy(name = "FF6", country = "kk", rank = 9, totalMatches = 0)
        RankingScreen(
            data = mapOf(
                f1 to listOf(FakePlayer(), FakePlayer()),
                f2 to listOf(FakePlayer(), FakePlayer()),
                f3 to listOf(FakePlayer(), FakePlayer()),
                f4 to listOf(FakePlayer(), FakePlayer()),
                f5 to listOf(FakePlayer(), FakePlayer()),
                f6 to listOf(FakePlayer(), FakePlayer()),
                f7 to listOf(FakePlayer(), FakePlayer()),
                f8 to listOf(FakePlayer(), FakePlayer()),
                f9 to listOf(FakePlayer(), FakePlayer()),
                f10 to listOf(FakePlayer(), FakePlayer()),
            ),
            onFilterClicked = {
                Toast.makeText(ctx, it.text, Toast.LENGTH_SHORT).show()
            }
        )
    }
}