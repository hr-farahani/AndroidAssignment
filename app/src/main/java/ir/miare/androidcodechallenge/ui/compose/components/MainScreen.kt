package ir.miare.androidcodechallenge.ui.compose.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ir.miare.androidcodechallenge.ui.theme.AndroidCodeChallengeTheme

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    orientation: Int,
    reportScreen: @Composable (Modifier, FilterOption) -> Unit
) {
    val options = listOf(
        FilterOption.TEAM_LEAGUE,
        FilterOption.MOST_GOAL,
        FilterOption.AVERAGE_GOAL,
        FilterOption.NONE
    )
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(options[3]) }

    if (orientation == Configuration.ORIENTATION_PORTRAIT) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FilterPanelComponent(
                radioOptions = options,
                selectedOption = selectedOption,
                onOptionSelected = onOptionSelected
            )
            reportScreen(Modifier, selectedOption)
        }
    } else {
        Row(
            modifier = modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            FilterPanelComponent(
                modifier = Modifier.weight(1f),
                radioOptions = options,
                selectedOption = selectedOption,
                onOptionSelected = onOptionSelected
            )
            reportScreen(
                Modifier.weight(1f),
                selectedOption
            )
        }
    }
}


@Preview
@Composable
private fun FilterPanelComponentPreview() {
    AndroidCodeChallengeTheme {
        MainScreen(
            orientation = Configuration.ORIENTATION_PORTRAIT,
            reportScreen = { _, _ ->
                Column(modifier = Modifier.fillMaxSize()) { }
            }
        )
    }
}