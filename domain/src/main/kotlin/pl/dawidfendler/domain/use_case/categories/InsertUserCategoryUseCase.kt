package pl.dawidfendler.domain.use_case.categories

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import pl.dawidfendler.coroutines.DispatcherProvider
import pl.dawidfendler.domain.model.categories.CategoryIcon
import pl.dawidfendler.domain.model.categories.CategoryType
import pl.dawidfendler.domain.model.categories.FinanceCategory
import pl.dawidfendler.domain.repository.finance_manager.CategoriesRepository
import pl.dawidfendler.util.flow.DataResult
import pl.dawidfendler.util.flow.DomainResult

class InsertUserCategoryUseCase(
    private val getCategoriesRepository: CategoriesRepository,
    private val dispatcherProvider: DispatcherProvider
) {

    operator fun invoke(
        name: String,
        icon: CategoryIcon,
        type: CategoryType,
        isUserCategory: Boolean
    ) = flow {
        val result = getCategoriesRepository.insertCategory(
            category = FinanceCategory(
                name = name,
                icon = icon,
                type = type,
                isUserCategory = isUserCategory
            )
        )
        when (result) {
            is DataResult.Success -> emit(DomainResult.Success(Unit))
            is DataResult.Error -> emit(DomainResult.Error(Exception(result.error.toString())))
        }
    }.flowOn(dispatcherProvider.io)
}