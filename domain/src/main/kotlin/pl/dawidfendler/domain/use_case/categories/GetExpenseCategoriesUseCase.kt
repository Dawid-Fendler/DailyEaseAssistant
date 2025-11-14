package pl.dawidfendler.domain.use_case.categories

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import pl.dawidfendler.coroutines.DispatcherProvider
import pl.dawidfendler.domain.model.categories.FinanceCategory
import pl.dawidfendler.domain.repository.finance_manager.CategoriesRepository
import pl.dawidfendler.util.flow.DataResult
import pl.dawidfendler.util.flow.DomainResult

class GetExpenseCategoriesUseCase(
    private val getCategoriesRepository: CategoriesRepository,
    private val dispatcherProvider: DispatcherProvider
) {

    operator fun invoke() = flow {
        val expenseCategories = mutableListOf<FinanceCategory>()
        val userCategoriesResult = getCategoriesRepository.getUserExpenseCategories()
        val defaultCategoriesResult = getCategoriesRepository.getDefaultExpenseCategories()

        when (userCategoriesResult) {
            is DataResult.Error -> Unit
            is DataResult.Success -> expenseCategories.addAll(userCategoriesResult.data)
        }

        when (defaultCategoriesResult) {
            is DataResult.Error -> Unit
            is DataResult.Success -> expenseCategories.addAll(defaultCategoriesResult.data)
        }

        emit(DomainResult.Success(expenseCategories.toList()))
    }.flowOn(dispatcherProvider.io)
}
