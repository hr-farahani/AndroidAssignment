package ir.miare.androidcodechallenge.ui.compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
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
import ir.miare.androidcodechallenge.domain.model.LeagueResource
import ir.miare.androidcodechallenge.ui.fakedata.Fake
import ir.miare.androidcodechallenge.ui.theme.AndroidCodeChallengeTheme
import ir.miare.androidcodechallenge.ui.theme.PurpleGrey80


@Composable
fun LeagueUiItem(
    modifier: Modifier = Modifier,
    league: LeagueResource,
    avgGoalsPerMatch: @Composable () -> Unit = {}
) {
    Surface(
        modifier = modifier
            .fillMaxWidth(),
        color = PurpleGrey80,
        shadowElevation = 4.dp
    ) {

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = "League Name:",
                        fontSize = 11.sp
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = league.name,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }

                Row(
                    modifier = modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = "Country:",
                        fontSize = 11.sp
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = league.country,
                        textAlign = TextAlign.Center,
                        fontSize = 13.sp,
                    )
                }
            }



            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = "League Rank:",
                        fontSize = 11.sp
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "${league.leagueRank}",
                        textAlign = TextAlign.Center
                    )
                }

                Row(
                    modifier = modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = "Total Matches:",
                        fontSize = 11.sp
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "${league.totalMatches}",
                        textAlign = TextAlign.Center
                    )
                }

                avgGoalsPerMatch()
            }
        }


    }
}


@Preview
@Composable
private fun LeagueUiItemPreview() {
    AndroidCodeChallengeTheme {
        LeagueUiItem(league = Fake.league)
    }
}