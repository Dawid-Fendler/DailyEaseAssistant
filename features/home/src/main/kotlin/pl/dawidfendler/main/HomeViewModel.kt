package pl.dawidfendler.main

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import pl.dawidfendler.domain.model.user.User
import pl.dawidfendler.domain.use_case.user.CreateUserUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    createUserUseCase: CreateUserUseCase
) : ViewModel() {

    var state by mutableStateOf(HomeState())
        private set

    init {
        createUserUseCase.invoke(user = (User()))
            .onEach {
                Log.d("HomeViewModel", "Success: User created")
            }
            .catch {
                Log.d("HomeViewModel", "Error: $it")
            }.launchIn(viewModelScope)
    }
}
