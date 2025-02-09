package pl.dawidfendler.domain.use_case.authentication_use_case

import android.util.Log
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import pl.dawidfendler.domain.repository.AuthenticationRepository
import pl.dawidfendler.util.flow.DomainResult
import javax.inject.Inject

class RegistrationUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {

    operator fun invoke(email: String, password: String) = flow {
        authenticationRepository.registerUser(email, password)
            .onEach { result ->
                Log.d("User info", "$result")
                emit(DomainResult.Success(Unit))
            }.catch { err ->
                emit(DomainResult.Error(error = err))
            }.collect()
    }
}
