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
import pl.dawidfendler.domain.use_case.authentication_use_case.RegistrationUseCase
import pl.dawidfendler.domain.validator.EmailValidator
import pl.dawidfendler.domain.validator.PasswordValidator
import pl.dawidfendler.util.UiText
import pl.dawidfendler.util.flow.DataResult
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registrationUseCase: RegistrationUseCase,
    private val emailValidator: EmailValidator,
    private val passwordValidator: PasswordValidator
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
        }
    }

    private fun register() {
        viewModelScope.launch {
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
                return@launch
            }
            registrationUseCase.invoke(
                email = state.email,
                password = state.password
            ).onEach { result ->
                state = state.copy(isRegistering = false)
                when (result) {
                    is DataResult.Error -> {
                        _eventChannel.send(
                            RegistrationEvent.Error(
                                error = UiText.StringResource(R.string.registration_error_message)
                            )
                        )
                    }

                    is DataResult.Success -> {
                        _eventChannel.send(RegistrationEvent.RegistrationSuccess)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}
