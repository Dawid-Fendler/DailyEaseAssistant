package pl.dawidfendler.domain.use_case.categories

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import pl.dawidfendler.coroutines.DispatcherProvider
import pl.dawidfendler.domain.model.categories.FinanceCategory
import pl.dawidfendler.domain.repository.finance_manager.CategoriesRepository
import pl.dawidfendler.util.flow.DataResult
import pl.dawidfendler.util.flow.DomainResult

class GetIncomeCategoriesUseCase(
    private val getCategoriesRepository: CategoriesRepository,
    private val dispatcherProvider: DispatcherProvider
) {

    operator fun invoke() = flow {
        val incomeCategories = mutableListOf<FinanceCategory>()
        val userCategoriesResult = getCategoriesRepository.getUserIncomeCategories()
        val defaultCategoriesResult = getCategoriesRepository.getDefaultIncomeCategories()

        when (userCategoriesResult) {
            is DataResult.Error -> Unit
            is DataResult.Success -> incomeCategories.addAll(userCategoriesResult.data)
        }

        when (defaultCategoriesResult) {
            is DataResult.Error -> Unit
            is DataResult.Success -> incomeCategories.addAll(defaultCategoriesResult.data)
        }

        emit(DomainResult.Success(incomeCategories.toList()))
    }.flowOn(dispatcherProvider.io)
}