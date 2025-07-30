package pl.dawidfendler.domain.model.account

import pl.dawidfendler.domain.model.user.User

data class UserWithAccounts(
    val user: User,
    val accounts: List<Account>
)
