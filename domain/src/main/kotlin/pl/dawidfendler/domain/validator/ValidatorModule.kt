package pl.dawidfendler.domain.validator

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ValidatorModule {

    @Provides
    @Singleton
    fun provideEmailValidator() = EmailValidator()

    @Provides
    @Singleton
    fun passwordValidator() = PasswordValidator()
}
