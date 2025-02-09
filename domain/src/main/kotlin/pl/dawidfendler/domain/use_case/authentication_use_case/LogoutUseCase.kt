package pl.dawidfendler.domain.use_case.authentication_use_case

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import pl.dawidfendler.domain.repository.AuthenticationRepository
import pl.dawidfendler.util.flow.DomainResult
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {

    operator fun invoke() = flow {
        authenticationRepository.logout()
            .onEach { result ->
                emit(DomainResult.Success(result))
            }.catch { err ->
                throw err
            }.collect()
    }
}
