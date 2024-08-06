package pl.dawidfendler.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.dawidfendler.data.repository.AuthenticationRepositoryImpl
import pl.dawidfendler.domain.repository.AuthenticationRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun binAuthenticationRepository(impl: AuthenticationRepositoryImpl): AuthenticationRepository
}
