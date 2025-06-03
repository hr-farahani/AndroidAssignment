package ir.miare.androidcodechallenge.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.miare.androidcodechallenge.ui.theme.AndroidCodeChallengeTheme

data class FakePlayer(
    val name: String = "Edin Dzeko",
    val team: FakeTeam = FakeTeam(),
    val totalGoal: Int = 17
)

data class FakeTeam(
    val name: String = "Inter",
    val rank: Int = 2
)

@Composable
fun PlayerUiItem(
    modifier: Modifier = Modifier,
    player: FakePlayer
) {

    Row(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = "${player.team.rank}"
        )
        Column(
            modifier = Modifier.weight(6f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = player.name
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = player.team.name
            )
        }
    }
}

@Preview
@Composable
fun PlayerUiItemPreview() {
    AndroidCodeChallengeTheme {
        PlayerUiItem(player = FakePlayer())
    }
}