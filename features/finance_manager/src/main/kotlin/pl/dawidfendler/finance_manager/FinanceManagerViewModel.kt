package pl.dawidfendler.finance_manager

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import pl.dawidfendler.domain.use_case.currencies_use_case.GetCurrenciesUseCase
import pl.dawidfendler.util.flow.DomainResult
import javax.inject.Inject

@HiltViewModel
class FinanceManagerViewModel @Inject constructor(
    getCurrenciesUseCase: GetCurrenciesUseCase
): ViewModel(){


    init {
        getCurrenciesUseCase.invoke().onEach {
            when (it) {
                is DomainResult.Success -> {
                    Log.d("Testowo", "Test:${it.data} ")
                }
                is DomainResult.Error -> {
                }
            }
        }.catch {
        }.launchIn(viewModelScope)
    }
}