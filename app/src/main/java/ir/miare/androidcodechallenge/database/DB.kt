package ir.miare.androidcodechallenge.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ir.miare.androidcodechallenge.database.dao.LeagueDao
import ir.miare.androidcodechallenge.database.dao.LeagueTeamPlayerDao
import ir.miare.androidcodechallenge.database.dao.PlayerDao
import ir.miare.androidcodechallenge.database.dao.TeamDao
import ir.miare.androidcodechallenge.database.model.entities.LeagueEntity
import ir.miare.androidcodechallenge.database.model.entities.PlayerEntity
import ir.miare.androidcodechallenge.database.model.entities.TeamEntity


@Database(
    entities = [
        LeagueEntity::class,
        TeamEntity::class,
        PlayerEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class DB : RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "miare-db"
    }

    abstract fun playerDao(): PlayerDao
    abstract fun teamDao(): TeamDao
    abstract fun leagueDao(): LeagueDao
    abstract fun leagueTeamPlayerDao(): LeagueTeamPlayerDao
}