package pl.dawidfendler.currency_converter

sealed interface CurrencyConverterAction {
    data object SwitchCurrency: CurrencyConverterAction
    data class ChangeCurrencyValue(val value: String, val isMainCurrency: Boolean): CurrencyConverterAction
    data class ChangeCurrency(val code: String, val isMainCurrency: Boolean): CurrencyConverterAction
    data class QueryChange(val query: String): CurrencyConverterAction
}