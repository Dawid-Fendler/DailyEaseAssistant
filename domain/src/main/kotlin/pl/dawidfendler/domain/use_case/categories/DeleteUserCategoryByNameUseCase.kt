package pl.dawidfendler.domain.use_case.categories

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import pl.dawidfendler.coroutines.DispatcherProvider
import pl.dawidfendler.domain.repository.finance_manager.CategoriesRepository
import pl.dawidfendler.util.flow.DataResult
import pl.dawidfendler.util.flow.DomainResult

class DeleteUserCategoryByNameUseCase(
    private val getCategoriesRepository: CategoriesRepository,
    private val dispatcherProvider: DispatcherProvider
) {

    operator fun invoke(categoryName: String) = flow {
        when (val result = getCategoriesRepository.deleteCategoryByName(categoryName)) {
            is DataResult.Error -> emit(DomainResult.Error(Exception(result.error.toString())))
            is DataResult.Success -> emit(DomainResult.Success(Unit))
        }
    }.flowOn(dispatcherProvider.io)
}