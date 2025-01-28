package pl.dawidfendler.authentication.login

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
import kotlinx.coroutines.launch
import pl.dawidfendler.authentication.R
import pl.dawidfendler.coroutines.DispatcherProvider
import pl.dawidfendler.datastore.DataStore
import pl.dawidfendler.datastore.DataStoreConstants.DISPLAY_HOME
import pl.dawidfendler.domain.use_case.authentication_use_case.GoogleLoginUseCase
import pl.dawidfendler.domain.use_case.authentication_use_case.LoginUseCase
import pl.dawidfendler.util.UiText
import pl.dawidfendler.util.flow.DataResult
import javax.inject.Inject

@HiltViewModel
internal class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val googleLoginUseCase: GoogleLoginUseCase,
    private val dataStore: DataStore,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    var state by mutableStateOf(LoginState())
        private set

    private val _eventChannel = Channel<LoginEvent>()
    val eventChannel get() = _eventChannel.receiveAsFlow()

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.OnLoginClick -> login()
            is LoginAction.OnTogglePasswordVisibilityClick ->
                state = state.copy(
                    isPasswordVisible = !state.isPasswordVisible
                )

            is LoginAction.OnLoginUpdate -> {
                state = state.copy(
                    email = action.login
                )
            }

            is LoginAction.OnPasswordUpdate -> {
                state = state.copy(
                    password = action.password
                )
            }

            is LoginAction.OnGoogleLoginClick -> googleLogin(action.idToken)

            is LoginAction.OnGoogleLoginError -> googleLoginError()
        }
    }

    private fun login() {
            state = state.copy(isLogin = true)
            loginUseCase.invoke(
                email = state.email,
                password = state.password
            ).onEach { result ->
                state = state.copy(isLogin = false)
                when (result) {
                    is DataResult.Error -> {
                        state = state.copy(
                            isError = true,
                            errorMessage = R.string.login_field_error_message
                        )
                        _eventChannel.send(
                            LoginEvent.Error(
                                error = UiText.StringResource(R.string.login_error_message)
                            )
                        )
                    }

                    is DataResult.Success -> {
                        state = state.copy(
                            isError = false,
                            errorMessage = 0
                        )
                        saveOnboardingDisplayed()
                        _eventChannel.send(LoginEvent.LoginSuccess)
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun googleLogin(idToken: String) {
        googleLoginUseCase.invoke(idToken = idToken)
            .onEach { result ->
                when (result) {
                    is DataResult.Error ->
                        _eventChannel.send(
                            LoginEvent.Error(
                                error = UiText.StringResource(R.string.google_login_error_message)
                            )
                        )

                    is DataResult.Success -> {
                        saveOnboardingDisplayed()
                        _eventChannel.send(LoginEvent.LoginSuccess)
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun googleLoginError() {
        viewModelScope.launch {
            _eventChannel.send(
                LoginEvent.Error(
                    error = UiText.StringResource(R.string.google_login_error_message)
                )
            )
        }
    }

    private fun saveOnboardingDisplayed() {
        viewModelScope.launch(dispatcherProvider.io) {
            dataStore.putPreference(DISPLAY_HOME, true)
        }
    }
}
