package pl.finance_managerV2.components.currency_picker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import pl.dawidfendler.domain.model.currencies.ExchangeRateTable
import pl.dawidfendler.util.CurrencyFlagEmoji.getFlagEmojiForCurrency
import javax.inject.Inject

@HiltViewModel
class CurrencyPickerViewModel @Inject constructor() : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private val _currencies = MutableStateFlow<List<CurrencyUiModel>>(emptyList())
    val filteredCurrencies: StateFlow<List<CurrencyUiModel>> =
        combine(_currencies, _query) { list, query ->
            if (query.isBlank()) {
                list
            } else {
                list.filter {
                    it.currencyName.contains(query, true) || it.currencyCode.contains(query, true)
                }
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
    }

    fun setCurrencies(currencies: List<ExchangeRateTable>) {
        _currencies.update {
            currencies.map {
                CurrencyUiModel(
                    currencyCode = it.currencyCode,
                    currencyName = it.currencyName,
                    currencySign = getFlagEmojiForCurrency(it.currencyCode)
                )
            }
        }
    }
}