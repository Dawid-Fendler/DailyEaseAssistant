package pl.dawidfendler.data.mapper

import pl.dawidfendler.data.model.user.UserEntity
import pl.dawidfendler.domain.model.user.User

internal fun User.toEntity() = UserEntity(
    userName = userName,
)

internal fun UserEntity.toDomain() = User(
    userName = userName
)

internal fun userCurrenciesToDomain(currencies: String): List<String> {
    return currencies.split("-").filter { it.isNotBlank() }.take(5)
}

internal fun userCurrenciesToEntity(list: List<String>): String {
    return list.joinToString("-")
}
