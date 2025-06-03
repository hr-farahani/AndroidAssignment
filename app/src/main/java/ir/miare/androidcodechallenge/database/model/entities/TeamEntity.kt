package ir.miare.androidcodechallenge.database.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "team_tbl")
data class TeamEntity(
    @PrimaryKey @ColumnInfo("team_id") val id: String,
    val name: String,
    val rank: Int
)