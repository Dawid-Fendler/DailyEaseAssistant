package pl.dawidfendler.data.model.user

import androidx.room.Embedded
import androidx.room.Relation
import pl.dawidfendler.data.model.account.AccountEntity

data class UserWithAccounts(
    @Embedded
    val user: UserEntity,

    @Relation(
        parentColumn = "userId",
        entityColumn = "ownerUserId"
    )
    val accounts: List<AccountEntity>
)
