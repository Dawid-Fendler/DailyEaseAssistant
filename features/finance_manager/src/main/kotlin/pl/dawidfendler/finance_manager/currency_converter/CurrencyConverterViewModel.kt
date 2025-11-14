package pl.dawidfendler.finance_manager.currency_converter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import pl.dawidfendler.date.DateTime
import pl.dawidfendler.domain.model.currencies.ExchangeRateTable
import pl.dawidfendler.domain.use_case.currencies.GetCurrenciesUseCase
import pl.dawidfendler.finance_manager.util.CurrencyFlagEmoji.getFlagEmojiForCurrency
import pl.dawidfendler.finance_manager.util.getPolishCurrency
import pl.dawidfendler.util.flow.DomainResult
import pl.dawidfendler.util.logger.Logger
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

@HiltViewModel
class CurrencyConverterViewModel @Inject constructor(
    getCurrenciesUseCase: GetCurrenciesUseCase,
    private val logger: Logger,
    private val dateTime: DateTime
) : ViewModel() {

    private val _state = MutableStateFlow(CurrencyConverterState())
    val state = _state.asStateFlow()

    init {
        getCurrenciesUseCase.invoke().onEach {
            when (it) {
                is DomainResult.Success -> {
                    val newCurrencies = it.data.toMutableList()
                    newCurrencies.add(
                        0,
                        getPolishCurrency()
                    )
                    setStateForFetchCurrencies(currencies = newCurrencies)
                }

                is DomainResult.Error -> {
                    logger.e(TAG, "Error while fetching currencies: ${it.error.message}")
                    setStateForFetchCurrencies(
                        currencies = listOf(getPolishCurrency()),
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun setStateForFetchCurrencies(
        currencies: List<ExchangeRateTable>,
    ) {
        _state.update {
            it.copy(
                currencies = currencies,
                mainCurrencyCode = currencies.first().currencyCode,
                mainCurrencyMidValue = currencies.first().currencyMidValue?.toBigDecimal()
                    ?: BigDecimal.ONE,
                mainCurrencyFullName = currencies.first().currencyName,
                mainCurrencyFlag = getFlagEmojiForCurrency(currencies.first().currencyCode),
                secondCurrencyCode = currencies[1].currencyCode,
                secondCurrencyMidValue = currencies[1].currencyMidValue?.toBigDecimal()
                    ?: BigDecimal.ONE,
                secondCurrencyFullName = currencies[1].currencyName,
                secondCurrencyFlag = getFlagEmojiForCurrency(currencies[1].currencyCode),
                exchangeRateDate = dateTime.convertDateToDayMonthYearHourMinuteFormat(dateTime.getCurrentDate())
            )
        }
    }

    fun onAction(action: CurrencyConverterAction) {
        when (action) {
            is CurrencyConverterAction.ChangeCurrency -> changeCurrency(
                action.isMainCurrency,
                action.code
            )

            is CurrencyConverterAction.ChangeCurrencyValue -> changeCurrencyValue(
                action.value
            )

            CurrencyConverterAction.SwitchCurrency -> switchCurrency()
        }
    }

    private fun changeCurrency(mainCurrency: Boolean, code: String) {
        if (mainCurrency) {
            val newCurrency = state.value.currencies.firstOrNull { it.currencyCode == code }
            if (newCurrency != null) {
                _state.update {
                    it.copy(
                        mainCurrencyCode = newCurrency.currencyCode,
                        mainCurrencyMidValue = newCurrency.currencyMidValue?.toBigDecimal()
                            ?: BigDecimal.ONE,
                        mainCurrencyFullName = newCurrency.currencyName,
                        mainCurrencyFlag = getFlagEmojiForCurrency(newCurrency.currencyCode),
                        mainCurrencyValue = state.value.mainCurrencyValue,
                        secondCurrencyValue = calculateCurrencyValue()
                    )
                }
            }
        } else {
            val newCurrency = state.value.currencies.firstOrNull { it.currencyCode == code }
            if (newCurrency != null) {
                _state.update {
                    it.copy(
                        secondCurrencyFullName = newCurrency.currencyName,
                        secondCurrencyMidValue = newCurrency.currencyMidValue?.toBigDecimal()
                            ?: BigDecimal.ONE,
                        secondCurrencyFlag = getFlagEmojiForCurrency(newCurrency.currencyCode),
                        secondCurrencyValue = calculateCurrencyValue()
                    )
                }
            }
        }
    }

    private fun calculateCurrencyValue(value: String? = null): String {
        val newValue = if (value.isNullOrEmpty()) {
            state.value.mainCurrencyValue
        } else {
            value
        }

        return if (newValue.isNotEmpty()) {
            (newValue.toBigDecimal() * state.value.secondCurrencyMidValue / state.value.mainCurrencyMidValue).setScale(
                2,
                RoundingMode.HALF_UP
            ).toString()
        } else {
            ZERO
        }
    }

    private fun changeCurrencyValue(value: String) {
        _state.update {
            it.copy(
                mainCurrencyValue = value,
                secondCurrencyValue = calculateCurrencyValue(value = value)
            )
        }
    }

    private fun switchCurrency() {
        _state.update {
            it.copy(
                mainCurrencyValue = state.value.secondCurrencyValue,
                secondCurrencyValue = state.value.mainCurrencyValue,
                mainCurrencyCode = state.value.secondCurrencyCode,
                secondCurrencyCode = state.value.mainCurrencyCode,
                mainCurrencyFlag = state.value.secondCurrencyFlag,
                secondCurrencyFlag = state.value.mainCurrencyFlag,
                mainCurrencyFullName = state.value.secondCurrencyFullName,
                secondCurrencyFullName = state.value.mainCurrencyFullName,
                mainCurrencyMidValue = state.value.secondCurrencyMidValue,
                secondCurrencyMidValue = state.value.mainCurrencyMidValue
            )
        }
    }

    companion object {
        private const val TAG = "CurrencyConverterViewModel"
        private const val ZERO = "0.00"
    }
}
