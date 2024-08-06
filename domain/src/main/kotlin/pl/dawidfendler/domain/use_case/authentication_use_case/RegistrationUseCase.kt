package pl.dawidfendler.domain.use_case.authentication_use_case

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import pl.dawidfendler.domain.repository.AuthenticationRepository
import pl.dawidfendler.util.flow.DataResult
import timber.log.Timber
import javax.inject.Inject

class RegistrationUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {

    operator fun invoke(email: String, password: String) = flow {
        authenticationRepository.registerUser(email, password)
            .onEach { result ->
                Timber.d(message = "User info: $result")
                emit(DataResult.Success(Unit))
            }.catch { err ->
                emit(DataResult.Error(error = err))
            }.collect()
    }
}
