package ir.miare.androidcodechallenge.ui.compose.rankingscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.miare.androidcodechallenge.common.result.Result
import ir.miare.androidcodechallenge.common.result.asResult
import ir.miare.androidcodechallenge.domain.FetchData
import ir.miare.androidcodechallenge.domain.GetLeaguePlayersUseCase
import ir.miare.androidcodechallenge.domain.GetLeaguesSortedByAvgGoalsUseCase
import ir.miare.androidcodechallenge.domain.GetPlayerInfoUseCase
import ir.miare.androidcodechallenge.domain.model.LeagueResource
import ir.miare.androidcodechallenge.domain.model.PlayerWithLeagueAndTeamResource
import ir.miare.androidcodechallenge.ui.compose.FilterOption
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RankingViewModel @Inject constructor(
    private val fetchData: FetchData,
    private val getLeaguePlayers: GetLeaguePlayersUseCase,
    private val getPlayerInfoUseCase: GetPlayerInfoUseCase,
    private val getLeaguesSortedByAvgGoalsUseCase: GetLeaguesSortedByAvgGoalsUseCase
) : ViewModel() {

    private val _fetchDataUiState =
        MutableStateFlow<FetchDataUiState>(FetchDataUiState.Loading)
    val fetchDataUiState = _fetchDataUiState.asStateFlow()

    private val _leaguePlayersUiState =
        MutableStateFlow<LeaguePlayersUiState>(LeaguePlayersUiState.Loading)
    val leaguePlayersUiState = _leaguePlayersUiState.asStateFlow()

    init {
        viewModelScope.launch {
            fetchData.invoke().asResult().collectLatest {
                _fetchDataUiState.value = when(it) {
                    is Result.Success<*> -> FetchDataUiState.Success
                    is Result.Error -> FetchDataUiState.Loading
                    else -> FetchDataUiState.Loading
                }
            }
        }
    }

    fun applyFilter(option: FilterOption) {
        viewModelScope.launch {
            when (option) {
                FilterOption.NONE -> {
                    _leaguePlayersUiState.value = LeaguePlayersUiState.Loading
                    getLeaguePlayers.invoke().asResult().collectLatest { result ->
                        when (result) {
                            is Result.Success<Map<LeagueResource, List<PlayerWithLeagueAndTeamResource>>> ->
                                _leaguePlayersUiState.value =
                                    LeaguePlayersUiState.Success(result.data)

                            is Result.Error -> _leaguePlayersUiState.value =
                                LeaguePlayersUiState.Error

                            else -> _leaguePlayersUiState.value = LeaguePlayersUiState.Loading
                        }
                    }


                }

                FilterOption.TEAM_LEAGUE -> {}
                FilterOption.MOST_GOAL -> {}
                FilterOption.AVERAGE_GOAL -> {}
            }
        }
    }
}

sealed interface FetchDataUiState {
    data object Success : FetchDataUiState
    data object Error : FetchDataUiState
    data object Loading : FetchDataUiState
}

sealed interface LeaguePlayersUiState {
    data class Success(val data: Map<LeagueResource, List<PlayerWithLeagueAndTeamResource>>) :
        LeaguePlayersUiState

    data object Error : LeaguePlayersUiState
    data object Loading : LeaguePlayersUiState
}