package pl.dawidfendler.domain.use_case.authentication

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import pl.dawidfendler.coroutines.DispatcherProvider
import pl.dawidfendler.domain.repository.AuthenticationRepository
import pl.dawidfendler.domain.util.mapToCustomException
import pl.dawidfendler.util.flow.DataResult
import pl.dawidfendler.util.flow.DomainResult
import javax.inject.Inject

class RegistrationUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val dispatcherProvider: DispatcherProvider
) {

    operator fun invoke(email: String, password: String) = flow {
        when (val dataResult = authenticationRepository.registerUser(email, password)) {
            is DataResult.Success -> emit(DomainResult.Success(dataResult.data))
            is DataResult.Error -> emit(DomainResult.Error(dataResult.error.mapToCustomException()))
        }
    }.flowOn(dispatcherProvider.io)
}
