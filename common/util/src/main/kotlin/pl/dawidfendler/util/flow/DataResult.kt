package pl.dawidfendler.util.flow

typealias DomainError = pl.dawidfendler.util.network.Error

sealed interface DataResult<out D, out E : pl.dawidfendler.util.network.Error> {
    data class Success<out D>(val data: D) : DataResult<D, Nothing>
    data class Error<out E : DomainError>(val error: E) : DataResult<Nothing, E>
}

inline fun <T, E : pl.dawidfendler.util.network.Error, R> DataResult<T, E>.map(map: (T) -> R): DataResult<R, E> {
    return when (this) {
        is DataResult.Error -> DataResult.Error(error)
        is DataResult.Success -> DataResult.Success(map(data))
    }
}
