package ir.miare.androidcodechallenge.database.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.miare.androidcodechallenge.database.DB
import ir.miare.androidcodechallenge.database.DB.Companion.DATABASE_NAME
import ir.miare.androidcodechallenge.database.dao.LeagueDao
import ir.miare.androidcodechallenge.database.dao.LeagueTeamPlayerDao
import ir.miare.androidcodechallenge.database.dao.PlayerDao
import ir.miare.androidcodechallenge.database.dao.TeamDao
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDB(application: Application): DB {
        return Room.databaseBuilder(application, DB::class.java, DATABASE_NAME).build()
    }

    @Singleton
    @Provides
    fun provideLeagueDao(db: DB): LeagueDao {
        return db.leagueDao()
    }

    @Singleton
    @Provides
    fun provideTeamDao(db: DB): TeamDao {
        return db.teamDao()
    }

    @Singleton
    @Provides
    fun providePlayerDao(db: DB): PlayerDao {
        return db.playerDao()
    }

    @Singleton
    @Provides
    fun provideLeagueTeamPlayerDao(db: DB): LeagueTeamPlayerDao {
        return db.leagueTeamPlayerDao()
    }
}