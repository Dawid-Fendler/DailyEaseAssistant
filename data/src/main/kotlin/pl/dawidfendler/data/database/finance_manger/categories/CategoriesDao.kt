package pl.dawidfendler.data.database.finance_manger.categories

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pl.dawidfendler.data.model.categories.CategoryEntity

@Dao
interface CategoriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(categoryEntity: CategoryEntity)

    @Query("SELECT * FROM category WHERE type == 'income' ORDER BY name ASC")
    suspend fun getAllIncomeCategories(): List<CategoryEntity>?

    @Query("SELECT * FROM category WHERE type == 'expense' ORDER BY name ASC")
    suspend fun getAllExpenseCategories(): List<CategoryEntity>?

    @Query("DELETE FROM category")
    suspend fun deleteAllCategories()

    @Query("DELETE FROM category WHERE name = :name")
    suspend fun deleteCategoryByName(name: String)
}