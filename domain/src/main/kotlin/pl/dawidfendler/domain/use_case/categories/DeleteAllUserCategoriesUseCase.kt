package pl.dawidfendler.domain.use_case.categories

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import pl.dawidfendler.coroutines.DispatcherProvider
import pl.dawidfendler.domain.repository.finance_manager.CategoriesRepository
import pl.dawidfendler.util.flow.DataResult
import pl.dawidfendler.util.flow.DomainResult

class DeleteAllUserCategoriesUseCase(
    private val getCategoriesRepository: CategoriesRepository,
    private val dispatcherProvider: DispatcherProvider
) {

    operator fun invoke() = flow {
        when (val result = getCategoriesRepository.deleteAllCategories()) {
            is DataResult.Success -> {
                emit(DomainResult.Success(Unit))
            }

            is DataResult.Error -> {
                emit(DomainResult.Error(Exception(result.error.toString())))
            }
        }
    }.flowOn(dispatcherProvider.io)
}
