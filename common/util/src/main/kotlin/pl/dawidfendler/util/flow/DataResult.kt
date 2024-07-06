package pl.dawidfendler.util.flow

sealed class DataResult<out T> {
    data object Loading : DataResult<Nothing>()
    data object Initial : DataResult<Nothing>()
    data class Success<T>(val data: T) : DataResult<T>()
    data class Error(val throwable: Throwable) : DataResult<Nothing>()
}