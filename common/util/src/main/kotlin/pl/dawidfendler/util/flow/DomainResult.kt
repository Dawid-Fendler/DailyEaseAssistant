package pl.dawidfendler.util.flow

sealed interface DomainResult<out D, out E> {
    data class Success<D>(val data: D) : DomainResult<D, Nothing>
    data class Error(val error: Throwable) : DomainResult<Nothing, Exception>
}

typealias EmptyResult = DomainResult<Unit, Throwable>
