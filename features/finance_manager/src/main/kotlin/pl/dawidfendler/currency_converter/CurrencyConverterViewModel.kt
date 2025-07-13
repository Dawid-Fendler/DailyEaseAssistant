package pl.dawidfendler.currency_converter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import pl.dawidfendler.date.DateTime
import pl.dawidfendler.domain.model.currencies.ExchangeRateTable
import pl.dawidfendler.domain.use_case.currencies.GetCurrenciesUseCase
import pl.dawidfendler.finance_manager.FinanceManagerEvent
import pl.dawidfendler.finance_manager.util.getPolishCurrency
import pl.dawidfendler.util.Constants.ZERO
import pl.dawidfendler.util.CurrencyFlagEmoji.getFlagEmojiForCurrency
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

    var state by mutableStateOf(CurrencyConverterState())
        private set

    private val _eventChannel = Channel<FinanceManagerEvent>()
    val eventChannel get() = _eventChannel.receiveAsFlow()

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
                    _eventChannel.send(FinanceManagerEvent.ShowErrorBottomDialog("Problem with download currencies"))
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun setStateForFetchCurrencies(
        currencies: List<ExchangeRateTable>,
    ) {
        state = state.copy(
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
            exchangeRateDate = dateTime.convertDateToDayMonthYearHourMinuteFormat(dateTime.getCurrentDate()),

            )
    }

    fun onAction(action: CurrencyConverterAction) {
        when (action) {
            is CurrencyConverterAction.ChangeCurrency -> changeCurrency(
                action.isMainCurrency,
                action.code
            )

            is CurrencyConverterAction.ChangeCurrencyValue -> changeCurrencyValue(
                action.isMainCurrency,
                action.value
            )

            CurrencyConverterAction.SwitchCurrency -> switchCurrency()
        }
    }

    private fun changeCurrency(mainCurrency: Boolean, code: String) {
        if (mainCurrency) {
            val newCurrency = state.currencies.firstOrNull { it.currencyCode == code }
            if (newCurrency != null) {
                state = state.copy(
                    mainCurrencyCode = newCurrency.currencyCode,
                    mainCurrencyMidValue = newCurrency.currencyMidValue?.toBigDecimal()
                        ?: BigDecimal.ONE,
                    mainCurrencyFullName = newCurrency.currencyName,
                    mainCurrencyFlag = getFlagEmojiForCurrency(newCurrency.currencyCode),
                    mainCurrencyValue = ZERO
                )
            }
        } else {
            val newCurrency = state.currencies.firstOrNull { it.currencyCode == code }
            if (newCurrency != null) {
                state = state.copy(
                    secondCurrencyCode = newCurrency.currencyCode,
                    secondCurrencyMidValue = newCurrency.currencyMidValue?.toBigDecimal()
                        ?: BigDecimal.ONE,
                    secondCurrencyFullName = newCurrency.currencyName,
                    secondCurrencyFlag = getFlagEmojiForCurrency(newCurrency.currencyCode),
                    secondCurrencyValue = ZERO
                )
            }
        }
    }

    private fun changeCurrencyValue(mainCurrency: Boolean, value: String) {
        if (mainCurrency) {
            state = state.copy(
                mainCurrencyValue = value,
                secondCurrencyValue = if (value.isNotEmpty()) {
                    (value.toBigDecimal() * state.secondCurrencyMidValue / state.mainCurrencyMidValue).setScale(
                        2,
                        RoundingMode.HALF_UP
                    ).toString()
                } else {
                    ZERO
                }
            )
        } else {
            state = state.copy(
                secondCurrencyValue = value,
                mainCurrencyValue = if (value.isNotEmpty()) {
                    (value.toBigDecimal() * state.mainCurrencyMidValue / state.secondCurrencyMidValue).setScale(
                        2,
                        RoundingMode.HALF_UP
                    ).toString()
                } else {
                    ZERO
                }
            )
        }
    }

    private fun switchCurrency() {
        state = state.copy(
            mainCurrencyValue = state.secondCurrencyValue,
            secondCurrencyValue = state.mainCurrencyValue,
            mainCurrencyCode = state.secondCurrencyCode,
            secondCurrencyCode = state.mainCurrencyCode,
            mainCurrencyFlag = state.secondCurrencyFlag,
            secondCurrencyFlag = state.mainCurrencyFlag,
            mainCurrencyFullName = state.secondCurrencyFullName,
            secondCurrencyFullName = state.mainCurrencyFullName,
            mainCurrencyMidValue = state.secondCurrencyMidValue,
            secondCurrencyMidValue = state.mainCurrencyMidValue
        )
    }

    companion object {
        private const val TAG = "CurrencyConverterViewModel"
    }
}