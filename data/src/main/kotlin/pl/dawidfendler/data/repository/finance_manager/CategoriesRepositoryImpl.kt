package pl.dawidfendler.data.repository.finance_manager

import pl.dawidfendler.data.datasource.local.categories.CategoriesLocalDataSource
import pl.dawidfendler.data.mapper.toCategoryEntity
import pl.dawidfendler.data.mapper.toFinanceCategory
import pl.dawidfendler.data.util.DefaultCategories.defaultExpenseCategories
import pl.dawidfendler.data.util.DefaultCategories.defaultIncomeCategories
import pl.dawidfendler.domain.model.categories.FinanceCategory
import pl.dawidfendler.domain.repository.finance_manager.CategoriesRepository
import pl.dawidfendler.domain.util.CustomError
import pl.dawidfendler.util.flow.DataResult
import pl.dawidfendler.util.network.Error
import javax.inject.Inject

class CategoriesRepositoryImpl @Inject constructor(
    private val categoriesDataSource: CategoriesLocalDataSource
) : CategoriesRepository {
    override suspend fun getDefaultExpenseCategories(): DataResult<List<FinanceCategory>, Error> {
        return DataResult.Success(defaultExpenseCategories)
    }

    override suspend fun getDefaultIncomeCategories(): DataResult<List<FinanceCategory>, Error> {
        return DataResult.Success(defaultIncomeCategories)
    }

    override suspend fun getUserExpenseCategories(): DataResult<List<FinanceCategory>, Error> {
        return try {
            val categories = categoriesDataSource.getAllExpenseCategories()
            if (categories.isNullOrEmpty()) {
                DataResult.Success(emptyList())
            } else {
                DataResult.Success(categories.map { it.toFinanceCategory() })
            }
        } catch (e: Exception) {
            DataResult.Error(CustomError.GENERIC_ERROR)
        }
    }

    override suspend fun getUserIncomeCategories(): DataResult<List<FinanceCategory>, Error> {
        return try {
            val categories = categoriesDataSource.getAllIncomeCategories()
            if (categories.isNullOrEmpty()) {
                DataResult.Success(emptyList())
            } else {
                DataResult.Success(categories.map { it.toFinanceCategory() })
            }
        } catch (e: Exception) {
            DataResult.Error(CustomError.GENERIC_ERROR)
        }
    }

    override suspend fun insertCategory(category: FinanceCategory): DataResult<Unit, Error> {
        try {
            categoriesDataSource.insert(
                category = category.toCategoryEntity()
            )
            return DataResult.Success(Unit)
        } catch (e: Exception) {
            return DataResult.Error(CustomError.GENERIC_ERROR)
        }
    }

    override suspend fun deleteCategoryByName(name: String): DataResult<Unit, Error> {
        return try {
            categoriesDataSource.deleteCategoryByName(name)
            DataResult.Success(Unit)
        } catch (e: Exception) {
            DataResult.Error(CustomError.GENERIC_ERROR)
        }
    }

    override suspend fun deleteAllCategories(): DataResult<Unit, Error> {
        return try {
            categoriesDataSource.deleteAllCategories()
            DataResult.Success(Unit)
        } catch (e: Exception) {
            DataResult.Error(CustomError.GENERIC_ERROR)
        }
    }
}
