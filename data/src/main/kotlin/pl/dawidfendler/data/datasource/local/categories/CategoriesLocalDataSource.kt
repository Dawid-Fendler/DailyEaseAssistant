package pl.dawidfendler.data.datasource.local.categories

import pl.dawidfendler.data.model.categories.CategoryEntity

interface CategoriesLocalDataSource {

    suspend fun getAllIncomeCategories(): List<CategoryEntity>?

    suspend fun getAllExpenseCategories(): List<CategoryEntity>?

    suspend fun deleteAllCategories()

    suspend fun deleteCategoryByName(name: String)

    suspend fun insert(category: CategoryEntity)
}