package pl.dawidfendler.data.model.categories

import androidx.room.Entity
import androidx.room.PrimaryKey
import pl.dawidfendler.data.util.Constants.CATEGORY_TABLE_NAME

@Entity(tableName = CATEGORY_TABLE_NAME)
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val name: String,
    val type: String,
    val iconType: String
)