package pl.dawidfendler.finance_manager.currency_converter

sealed interface CurrencyConverterAction {
    data object SwitchCurrency : CurrencyConverterAction
    data class ChangeCurrencyValue(val value: String) : CurrencyConverterAction
    data class ChangeCurrency(val code: String, val isMainCurrency: Boolean) :
        CurrencyConverterAction
}
