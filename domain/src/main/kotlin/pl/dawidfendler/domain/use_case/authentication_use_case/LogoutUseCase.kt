package pl.dawidfendler.domain.use_case.authentication_use_case

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import pl.dawidfendler.coroutines.DispatcherProvider
import pl.dawidfendler.domain.repository.AuthenticationRepository
import pl.dawidfendler.domain.util.mapToCustomException
import pl.dawidfendler.util.flow.DataResult
import pl.dawidfendler.util.flow.DomainResult
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val dispatcherProvider: DispatcherProvider
) {

    operator fun invoke() = flow {
        when (val dataResult = authenticationRepository.logout()) {
            is DataResult.Success -> emit(DomainResult.Success(dataResult.data))
            is DataResult.Error -> emit(DomainResult.Error(dataResult.error.mapToCustomException()))
        }
    }.flowOn(dispatcherProvider.io)
}
