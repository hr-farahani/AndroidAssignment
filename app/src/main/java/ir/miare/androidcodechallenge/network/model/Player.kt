package ir.miare.androidcodechallenge.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Player(
    val name: String,
    val team: Team,
    @SerialName("total_goal") val totalGoal: Int = 0
)