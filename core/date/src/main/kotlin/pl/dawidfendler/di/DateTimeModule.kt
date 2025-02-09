package pl.dawidfendler.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.dawidfendler.date.DateTime
import pl.dawidfendler.date.DateTimeUtils

@Module
@InstallIn(SingletonComponent::class)
object DateTimeModule {

    @Provides
    fun provideDateTime(): DateTime = DateTimeUtils()
}