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
import ir.miare.androidcodechallenge.domain.model.LeagueWithAverageGoalsResource
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

    private val _playersGoalScoreUiState =
        MutableStateFlow<PlayersGoalScoreUiState>(PlayersGoalScoreUiState.Loading)
    val playersGoalScoreUiState = _playersGoalScoreUiState.asStateFlow()

    private val _leagueWithAvgGoalUiState =
        MutableStateFlow<LeagueAvgGoalUiState>(LeagueAvgGoalUiState.Loading)
    val leagueWithAvgGoalUiState = _leagueWithAvgGoalUiState.asStateFlow()

    init {
        viewModelScope.launch {
            fetchData.invoke().asResult().collectLatest {
                _fetchDataUiState.value = when (it) {
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
                        _leaguePlayersUiState.value = when (result) {
                            is Result.Success<Map<LeagueResource, List<PlayerWithLeagueAndTeamResource>>> ->
                                LeaguePlayersUiState.Success(result.data)

                            is Result.Error -> LeaguePlayersUiState.Error
                            is Result.Loading -> LeaguePlayersUiState.Loading
                        }
                    }
                }

                FilterOption.TEAM_LEAGUE -> {}
                FilterOption.MOST_GOAL -> {
                    _playersGoalScoreUiState.value = PlayersGoalScoreUiState.Loading
                    getPlayerInfoUseCase.getAllPlayersOrderByGoalsScored().asResult()
                        .collectLatest { result ->
                            _playersGoalScoreUiState.value = when (result) {
                                is Result.Success<List<PlayerWithLeagueAndTeamResource>> -> {
                                    PlayersGoalScoreUiState.Success(result.data)
                                }

                                is Result.Error -> PlayersGoalScoreUiState.Error
                                is Result.Loading -> PlayersGoalScoreUiState.Loading
                            }
                        }

                }

                FilterOption.AVERAGE_GOAL -> {
                    _leagueWithAvgGoalUiState.value = LeagueAvgGoalUiState.Loading
                    getLeaguesSortedByAvgGoalsUseCase.invoke().asResult().collectLatest { result ->
                        _leagueWithAvgGoalUiState.value = when (result) {
                            is Result.Success<List<LeagueWithAverageGoalsResource>> ->
                                LeagueAvgGoalUiState.Success(result.data)

                            is Result.Error -> LeagueAvgGoalUiState.Error
                            is Result.Loading -> LeagueAvgGoalUiState.Loading
                        }
                    }
                }
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
    data class Success(
        val data: Map<LeagueResource, List<PlayerWithLeagueAndTeamResource>>
    ) : LeaguePlayersUiState

    data object Error : LeaguePlayersUiState
    data object Loading : LeaguePlayersUiState
}

sealed interface PlayersGoalScoreUiState {
    data class Success(
        val data: List<PlayerWithLeagueAndTeamResource>
    ) : PlayersGoalScoreUiState

    data object Error : PlayersGoalScoreUiState
    data object Loading : PlayersGoalScoreUiState
}

sealed interface LeagueAvgGoalUiState {
    data class Success(
        val data: List<LeagueWithAverageGoalsResource>
    ) : LeagueAvgGoalUiState

    data object Error : LeagueAvgGoalUiState
    data object Loading : LeagueAvgGoalUiState
}