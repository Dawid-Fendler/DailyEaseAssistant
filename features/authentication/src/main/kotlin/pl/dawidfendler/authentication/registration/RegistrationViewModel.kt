package pl.dawidfendler.authentication.registration

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
import pl.dawidfendler.domain.use_case.authentication.GoogleLoginUseCase
import pl.dawidfendler.domain.use_case.authentication.RegistrationUseCase
import pl.dawidfendler.domain.validator.EmailValidator
import pl.dawidfendler.domain.validator.PasswordValidator
import pl.dawidfendler.util.UiText
import pl.dawidfendler.util.flow.DomainResult
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registrationUseCase: RegistrationUseCase,
    private val emailValidator: EmailValidator,
    private val passwordValidator: PasswordValidator,
    private val googleLoginUseCase: GoogleLoginUseCase,
    private val dataStore: DataStore,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    var state by mutableStateOf(RegistrationState())
        private set

    private val _eventChannel = Channel<RegistrationEvent>()
    val eventChannel get() = _eventChannel.receiveAsFlow()

    fun onAction(action: RegistrationAction) {
        when (action) {
            is RegistrationAction.OnRegisterClick -> register()
            is RegistrationAction.OnTogglePasswordVisibilityClick ->
                state = state.copy(
                    isPasswordVisible = !state.isPasswordVisible
                )

            is RegistrationAction.OnLoginUpdate -> {
                state = state.copy(
                    email = action.login
                )
            }

            is RegistrationAction.OnPasswordUpdate -> {
                state = state.copy(
                    password = action.password
                )
            }

            is RegistrationAction.OnGoogleLoginClick -> googleLogin(action.idToken)
            is RegistrationAction.OnGoogleLoginError -> googleLoginError()
        }
    }

    private fun register() {
        state = state.copy(isRegistering = true)
        val emailValidationResult = emailValidator.validateEmail(state.email)
        val passwordValidationResult = passwordValidator.validatePassword(state.password)
        if (emailValidationResult.isEmailError || passwordValidationResult.isPasswordError) {
            state = state.copy(
                isEmailValid = emailValidationResult.isEmailError,
                isPasswordValid = passwordValidationResult.isPasswordError,
                emailErrorMessage = emailValidationResult.errorMessage,
                passwordErrorMessage = passwordValidationResult.errorMessage
            )
            return
        }
        registrationUseCase.invoke(
            email = state.email,
            password = state.password
        ).onEach { result ->
            state = state.copy(isRegistering = false)
            when (result) {
                is DomainResult.Error -> {
                    _eventChannel.send(
                        RegistrationEvent.Error(
                            error = UiText.StringResource(R.string.registration_error_message)
                        )
                    )
                }

                is DomainResult.Success -> {
                    _eventChannel.send(RegistrationEvent.RegistrationSuccess)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun googleLogin(idToken: String) {
        googleLoginUseCase.invoke(idToken = idToken)
            .onEach { result ->
                when (result) {
                    is DomainResult.Error ->
                        _eventChannel.send(
                            RegistrationEvent.Error(
                                error = UiText.StringResource(R.string.google_login_error_message)
                            )
                        )

                    is DomainResult.Success -> {
                        _eventChannel.send(RegistrationEvent.LoginSuccess)
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun googleLoginError() {
        viewModelScope.launch {
            _eventChannel.send(
                RegistrationEvent.Error(
                    error = UiText.StringResource(R.string.google_login_error_message)
                )
            )
        }
    }

    fun saveOnboardingDisplayed() {
        viewModelScope.launch(dispatcherProvider.io) {
            dataStore.putPreference(DISPLAY_HOME, true)
        }
    }
}
