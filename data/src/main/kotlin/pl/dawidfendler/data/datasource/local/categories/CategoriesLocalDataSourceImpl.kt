package pl.dawidfendler.data.datasource.local.categories

import pl.dawidfendler.data.database.finance_manger.categories.CategoriesDao
import pl.dawidfendler.data.model.categories.CategoryEntity
import javax.inject.Inject

class CategoriesLocalDataSourceImpl @Inject constructor(
    private val categoriesDao: CategoriesDao
) : CategoriesLocalDataSource {

    override suspend fun getAllIncomeCategories(): List<CategoryEntity>? =
        categoriesDao.getAllIncomeCategories()

    override suspend fun getAllExpenseCategories(): List<CategoryEntity>? =
        categoriesDao.getAllExpenseCategories()

    override suspend fun deleteAllCategories() =
        categoriesDao.deleteAllCategories()

    override suspend fun deleteCategoryByName(name: String) =
        categoriesDao.deleteCategoryByName(name)

    override suspend fun insert(category: CategoryEntity) =
        categoriesDao.insert(category)
}
