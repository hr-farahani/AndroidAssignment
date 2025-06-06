package ir.miare.androidcodechallenge.ui.compose.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

enum class FilterOption(val text: String) {
    TEAM_LEAGUE("Team & league ranking"),
    MOST_GOAL("Most goals scored by a player"),
    AVERAGE_GOAL("Average goal per match in a league"),
    NONE("None")
}

@Composable
fun FilterPanelComponent(
    modifier: Modifier = Modifier,
    radioOptions: List<FilterOption>,
    selectedOption: FilterOption,
    onOptionSelected: (FilterOption) -> Unit,
) {
    Column(
        modifier
            .selectableGroup()
            .fillMaxWidth()
    ) {
        Text(text = "Sorted By:")
        radioOptions.forEach { option ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .selectable(
                        selected = option == selectedOption,
                        onClick = { onOptionSelected(option) },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = option == selectedOption,
                    onClick = null // null recommended for accessibility with screen readers
                )
                Text(
                    text = option.text,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}
