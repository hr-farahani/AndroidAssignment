package ir.miare.androidcodechallenge.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import ir.miare.androidcodechallenge.database.dao.LeagueDao
import ir.miare.androidcodechallenge.database.dao.LeagueTeamPlayerDao
import ir.miare.androidcodechallenge.database.dao.PlayerDao
import ir.miare.androidcodechallenge.database.dao.TeamDao
import ir.miare.androidcodechallenge.database.model.entities.LeagueEntity
import ir.miare.androidcodechallenge.database.model.entities.PlayerEntity
import ir.miare.androidcodechallenge.database.model.entities.TeamEntity
import ir.miare.androidcodechallenge.worker.SeedDatabaseWorker
import ir.miare.androidcodechallenge.worker.SeedDatabaseWorker.Companion.KEY_FILENAME
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
        const val FAKE_DATA_FILENAME = "data.json"

        @Volatile
        private var instance: DB? = null

        fun getInstance(context: Context): DB {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): DB {
            return Room.databaseBuilder(context, DB::class.java, DATABASE_NAME)
                .addCallback(
                    object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            CoroutineScope(Dispatchers.IO).launch {
                                val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>()
                                    .setInputData(workDataOf(KEY_FILENAME to FAKE_DATA_FILENAME))
                                    .build()

                                WorkManager.getInstance(context).enqueue(request)
                            }
                        }
                    }
                )
                .build()
        }
    }

    abstract fun playerDao(): PlayerDao
    abstract fun teamDao(): TeamDao
    abstract fun leagueDao(): LeagueDao
    abstract fun leagueTeamPlayerDao(): LeagueTeamPlayerDao
}