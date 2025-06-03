package ir.miare.androidcodechallenge.database.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "player_tbl",
    foreignKeys = [
        ForeignKey(
            entity = LeagueEntity::class,
            parentColumns = ["league_id"],
            childColumns = ["league_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = TeamEntity::class,
            parentColumns = ["team_id"],
            childColumns = ["team_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("league_id"), Index("team_id")]
)
data class PlayerEntity(
    @PrimaryKey @ColumnInfo("player_id") val id: String,
    @ColumnInfo("league_id") val leagueId: String,
    @ColumnInfo("team_id") val teamId: String,
    val name: String,
    val totalGoal: Int
)