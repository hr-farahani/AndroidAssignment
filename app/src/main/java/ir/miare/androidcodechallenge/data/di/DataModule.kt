package ir.miare.androidcodechallenge.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.miare.androidcodechallenge.data.DemoRepository
import ir.miare.androidcodechallenge.data.Repository

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindsDemoRepository(demoRepository: DemoRepository): Repository
}
