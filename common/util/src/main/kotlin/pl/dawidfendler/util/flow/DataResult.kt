package pl.dawidfendler.util.flow

sealed class DataResult<out D, out E> {
    data class Success<D>(val data: D) : DataResult<D, Nothing>()
    data class Error(val error: Throwable) : DataResult<Nothing, Throwable>()
}

typealias EmptyResult = DataResult<Unit, Throwable>
