package pl.dawidfendler.domain.repository.finance_manager

import pl.dawidfendler.domain.model.categories.FinanceCategory
import pl.dawidfendler.util.flow.DataResult
import pl.dawidfendler.util.network.Error

interface CategoriesRepository {
    suspend fun getDefaultExpenseCategories(): DataResult<List<FinanceCategory>, Error>
    suspend fun getDefaultIncomeCategories(): DataResult<List<FinanceCategory>, Error>
    suspend fun getUserExpenseCategories(): DataResult<List<FinanceCategory>, Error>
    suspend fun getUserIncomeCategories(): DataResult<List<FinanceCategory>, Error>
    suspend fun insertCategory(category: FinanceCategory): DataResult<Unit, Error>
    suspend fun deleteCategoryByName(name: String): DataResult<Unit, Error>
    suspend fun deleteAllCategories(): DataResult<Unit, Error>
}
