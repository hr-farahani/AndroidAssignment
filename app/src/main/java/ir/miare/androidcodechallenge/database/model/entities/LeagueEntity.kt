package ir.miare.androidcodechallenge.database.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ir.miare.androidcodechallenge.domain.model.LeagueResource

@Entity(tableName = "league_tbl")
data class LeagueEntity(
    @PrimaryKey @ColumnInfo("league_id") val id: String,
    val name: String,
    val country: String,
    val rank: Int,
    val totalMatches: Int,
)

fun LeagueEntity.asExternalModel(): LeagueResource {
    return LeagueResource(
        leagueId = id,
        name = name,
        country = country,
        leagueRank = rank,
        totalMatches = totalMatches
    )
}