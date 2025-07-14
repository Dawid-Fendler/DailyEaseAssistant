package pl.dawidfendler.util.exception

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.dawidfendler.util.logger.Logger
import pl.dawidfendler.util.logger.LoggerImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CommonModule {

    @Provides
    @Singleton
    fun provideLogger(): Logger = LoggerImpl()
}
