package ir.miare.androidcodechallenge.ui.compose.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.miare.androidcodechallenge.domain.model.PlayerWithLeagueAndTeamResource
import ir.miare.androidcodechallenge.ui.fakedata.Fake
import ir.miare.androidcodechallenge.ui.theme.AndroidCodeChallengeTheme

@Composable
fun PlayerUiItem(
    modifier: Modifier = Modifier,
    player: PlayerWithLeagueAndTeamResource,
    onPlayerClicked: (player: PlayerWithLeagueAndTeamResource) -> Unit,
) {

    Card(
        modifier = modifier
            .padding(8.dp)
            .clickable { onPlayerClicked(player) }
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "Team Rank:",
                        fontSize = 11.sp
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "${player.team.teamRank}",
                        textAlign = TextAlign.Center
                    )
                }
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "Team Name:",
                        fontSize = 11.sp
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = player.team.name,
                        textAlign = TextAlign.Center
                    )
                }
            }


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = player.player.name,
                    fontWeight = FontWeight.Bold
                )
                Row {
                    Text(
                        text = "Total Goals:",
                        fontSize = 11.sp
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "${player.player.totalGoal}",
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }

}

@Preview
@Composable
fun PlayerUiItemPreview() {
    AndroidCodeChallengeTheme {
        PlayerUiItem(
            player = Fake.playerTeamLeague,
            onPlayerClicked = {}
        )
    }
}