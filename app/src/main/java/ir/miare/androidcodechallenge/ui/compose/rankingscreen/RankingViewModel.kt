package ir.miare.androidcodechallenge.ui.compose.rankingscreen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.miare.androidcodechallenge.ui.compose.FilterOption
import javax.inject.Inject

@HiltViewModel
class RankingViewModel @Inject constructor(): ViewModel() {

    fun applyFilter(option: FilterOption) {
    }
}