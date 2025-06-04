package ir.miare.androidcodechallenge.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.miare.androidcodechallenge.domain.model.LeagueResource
import ir.miare.androidcodechallenge.ui.fakedata.Fake
import ir.miare.androidcodechallenge.ui.theme.AndroidCodeChallengeTheme
import ir.miare.androidcodechallenge.ui.theme.Purple40


@Composable
fun LeagueUiItem(
    modifier: Modifier = Modifier,
    league: LeagueResource
) {
    Row(
        modifier = modifier
            .background(Purple40)
            .padding(8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Text(text = league.name)
        Text("-")
        Text(text = league.country)
    }
}


@Preview
@Composable
private fun LeagueUiItemPreview() {
    AndroidCodeChallengeTheme {
        LeagueUiItem(league = Fake.league)
    }
}