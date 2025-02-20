package pl.dawidfendler.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pl.dawidfendler.data.datasource.database.currencies.CurrenciesDatabase
import pl.dawidfendler.data.datasource.database.user.UserDatabase
import pl.dawidfendler.data.util.Constants.CURRENCIES_DATABASE_NAME
import pl.dawidfendler.data.util.Constants.USER_DATABASE_NAME

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideCurrenciesDatabase(@ApplicationContext context: Context): CurrenciesDatabase {
        return Room.databaseBuilder(
            context,
            CurrenciesDatabase::class.java,
            CURRENCIES_DATABASE_NAME
        )
            .build()
    }

    @Provides
    fun provideUserDatabase(@ApplicationContext context: Context): UserDatabase{
        return Room.databaseBuilder(
            context,
            UserDatabase::class.java,
            USER_DATABASE_NAME
        )
            .build()
    }
}