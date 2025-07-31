package pl.finance_managerV2.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import pl.dawidfendler.domain.model.currencies.ExchangeRateTable
import pl.dawidfendler.domain.use_case.currencies.GetCurrenciesUseCase
import pl.dawidfendler.finance_manager.util.getPolishCurrency
import pl.dawidfendler.util.flow.DomainResult
import pl.dawidfendler.util.logger.Logger
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getCurrenciesUseCase: GetCurrenciesUseCase,
    private val logger: Logger
) : ViewModel() {

    private val _state: MutableStateFlow<DashboardState> = MutableStateFlow(DashboardState())
    val state: StateFlow<DashboardState> get() = _state.asStateFlow()

    private val _eventChannel = Channel<DashboardEvent>()
    val eventChannel get() = _eventChannel.receiveAsFlow()

    private val currencyRefreshTrigger =
        MutableSharedFlow<Unit>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    init {
        observeCurrencyRefreshTrigger()
        currencyRefreshTrigger.tryEmit(Unit)
    }

    private fun observeCurrencyRefreshTrigger() {
        currencyRefreshTrigger
            .flatMapLatest {
                _state.update {
                    it.copy(
                        isLoading = true,
                        isCurrenciesFetchDataError = false
                    )
                }
                getCurrenciesUseCase.invoke()
                    .map { domainResult ->
                        when (domainResult) {
                            is DomainResult.Success -> {
                                logger.d("DashboardViewModel", "Fetched currencies successfully:${domainResult.data}")
                                setStateForFetchCurrencies(currencies = domainResult.data)
                            }

                            is DomainResult.Error -> {
                                logger.e(
                                    "DashboardViewModel",
                                    "Error fetching currencies: ${domainResult.error}"
                                )
                                _eventChannel.trySend(DashboardEvent.ShowErrorBottomDialog("An unexpected error occurred"))
                                setStateForFetchCurrencies(isFetchError = true)
                            }
                        }
                    }
            }.launchIn(viewModelScope)
    }

    private fun setStateForFetchCurrencies(
        currencies: List<ExchangeRateTable> = listOf(getPolishCurrency()),
        isFetchError: Boolean = false
    ) {
        _state.update {
            it.copy(
                currencies = currencies,
                isCurrenciesFetchDataError = isFetchError,
                isLoading = false
            )
        }
    }

    fun onAction(action: DashboardAction) {
        when (action) {
            is DashboardAction.DataRefresh -> {
                currencyRefreshTrigger.tryEmit(Unit)
            }

            is DashboardAction.AddNewAccount -> {

            }
        }
    }
}