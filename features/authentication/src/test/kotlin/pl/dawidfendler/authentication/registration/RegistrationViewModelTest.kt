package pl.dawidfendler.authentication.registration

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pl.dawidfendler.authentication.MainDispatcherRule
import pl.dawidfendler.coroutines.DispatcherProvider
import pl.dawidfendler.datastore.DataStore
import pl.dawidfendler.datastore.DataStoreConstants.DISPLAY_HOME
import pl.dawidfendler.domain.use_case.authentication.GoogleLoginUseCase
import pl.dawidfendler.domain.use_case.authentication.RegistrationUseCase
import pl.dawidfendler.domain.validator.EmailValidResult
import pl.dawidfendler.domain.validator.EmailValidator
import pl.dawidfendler.domain.validator.PasswordValidResult
import pl.dawidfendler.domain.validator.PasswordValidator
import pl.dawidfendler.util.flow.DomainResult

class RegistrationViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var registrationViewModel: RegistrationViewModel
    private lateinit var registrationUseCase: RegistrationUseCase
    private lateinit var googleLoginUseCase: GoogleLoginUseCase
    private lateinit var emailValidator: EmailValidator
    private lateinit var passwordValidator: PasswordValidator
    private lateinit var dataStore: DataStore
    private lateinit var dispatcherProvider: DispatcherProvider

    @Before
    fun setUp() {
        registrationUseCase = mockk()
        googleLoginUseCase = mockk()
        emailValidator = mockk()
        passwordValidator = mockk()
        dataStore = mockk()
        dispatcherProvider = mockk {
            every { io } returns mainDispatcherRule.testDispatcher
        }
        registrationViewModel = RegistrationViewModel(
            googleLoginUseCase = googleLoginUseCase,
            registrationUseCase = registrationUseCase,
            emailValidator = emailValidator,
            passwordValidator = passwordValidator,
            dataStore = dataStore,
            dispatcherProvider = dispatcherProvider
        )
    }

    @Test
    fun `When RegistrationViewModel is initialize, then RegistrationState has initial value`() {
        // GIVEN
        val registrationState = registrationViewModel.state

        // THEN
        assertThat(registrationState.isRegistering).isFalse()
        assertThat(registrationState.isPasswordVisible).isFalse()
        assertThat(registrationState.passwordErrorMessage).isEmpty()
        assertThat(registrationState.email).isEmpty()
        assertThat(registrationState.password).isEmpty()
        assertThat(registrationState.emailErrorMessage).isEmpty()
        assertThat(registrationState.isPasswordValid).isFalse()
        assertThat(registrationState.isEmailValid).isFalse()
    }

    @Test
    fun `When onAction is called with OnTogglePasswordVisibilityClick, then isPasswordVisible is equal true`() {
        // GIVEN
        registrationViewModel.onAction(RegistrationAction.OnTogglePasswordVisibilityClick)

        // THEN
        assertThat(registrationViewModel.state.isPasswordVisible).isTrue()
    }

    @Test
    fun `When onAction is called with OnLoginUpdate, then email is equal 'test'`() {
        // GIVEN
        registrationViewModel.onAction(RegistrationAction.OnLoginUpdate(login = "test"))

        // THEN
        assertThat(registrationViewModel.state.email).isEqualTo("test")
    }

    @Test
    fun `When onAction is called with OnPasswordUpdate, then password is equal 'test'`() {
        // GIVEN
        registrationViewModel.onAction(RegistrationAction.OnPasswordUpdate(password = "test"))

        // THEN
        assertThat(registrationViewModel.state.password).isEqualTo("test")
    }

    @Test
    fun `When onAction is called with OnGoogleLoginError, then send Error`() =
        runTest {
            // WHEN
            registrationViewModel.eventChannel.test {

                registrationViewModel.onAction(RegistrationAction.OnGoogleLoginError)

                // THEN
                val event = awaitItem()
                assertThat(event).isInstanceOf(RegistrationEvent.Error::class.java)
                expectNoEvents()
            }
        }

    @Test
    fun `When onAction is called with OnGoogleLoginClick and googleLoginUseCase return DataResult Success, then send RegistrationEvent equal Success`() =
        runTest {
            // GIVEN
            coEvery { googleLoginUseCase(idToken = "token") } returns flowOf(
                DomainResult.Success(
                    Unit
                )
            )

            // WHEN
            googleLoginUseCase(idToken = "token").test {
                val result = awaitItem()

                // THEN
                assertThat(result).isEqualTo(DomainResult.Success(Unit))
                awaitComplete()
            }

            // WHEN
            registrationViewModel.eventChannel.test {
                registrationViewModel.onAction(RegistrationAction.OnGoogleLoginClick("token"))

                // THEN
                val event = awaitItem()
                assertThat(event).isInstanceOf(RegistrationEvent.LoginSuccess::class.java)
                expectNoEvents()
            }
            verify(exactly = 2) { googleLoginUseCase(idToken = "token") }
        }

    @Test
    fun `When onAction is called with OnGoogleLoginClick and googleLoginUseCase return DataResult Error, then send RegistrationEvent equal Error`() =
        runTest {
            // GIVEN
            val mockThrowable = Throwable("Something wrong")
            coEvery { googleLoginUseCase(idToken = "token") } returns flowOf(
                DomainResult.Error(
                    mockThrowable
                )
            )

            // WHEN
            googleLoginUseCase(idToken = "token").test {
                val result = awaitItem()

                // THEN
                assertThat(result).isEqualTo(DomainResult.Error(mockThrowable))
                awaitComplete()
            }

            // WHEN
            registrationViewModel.eventChannel.test {
                registrationViewModel.onAction(RegistrationAction.OnGoogleLoginClick("token"))

                // THEN
                val event = awaitItem()
                assertThat(event).isInstanceOf(RegistrationEvent.Error::class.java)
                expectNoEvents()
            }
            verify(exactly = 2) { googleLoginUseCase(idToken = "token") }
        }

    @Test
    fun `When onAction is called with OnRegisterClick, email and password validator return error, then update registration state`() =
        runTest {
            // GIVEN
            val emailError =
                "The email has an incorrect format, it must look like this: abc@test.pl"
            val passwordError = "- The password must be at least 8 characters sign \n" +
                    "- The password not contain upper letter \n" +
                    "- The password not contain digit \n" +
                    "- The password not contain special character"
            registrationViewModel.onAction(RegistrationAction.OnLoginUpdate("test"))
            registrationViewModel.onAction(RegistrationAction.OnPasswordUpdate("test"))
            val emailValidatorResult = mockk<EmailValidResult> {
                every { isEmailError } returns true
                every { errorMessage } returns emailError
            }

            val passwordValidatorResult = mockk<PasswordValidResult> {
                every { isPasswordError } returns true
                every { errorMessage } returns passwordError
            }

            coEvery {
                emailValidator.validateEmail(
                    "test"
                )
            } returns emailValidatorResult

            coEvery {
                passwordValidator.validatePassword(
                    "test"
                )
            } returns passwordValidatorResult
            registrationViewModel.onAction(RegistrationAction.OnRegisterClick)
            assertThat(registrationViewModel.state.isEmailValid).isTrue()
            assertThat(registrationViewModel.state.isPasswordValid).isTrue()
            assertThat(registrationViewModel.state.passwordErrorMessage).isEqualTo(passwordError)
            assertThat(registrationViewModel.state.emailErrorMessage).isEqualTo(emailError)
        }

    @Test
    fun `When onAction is called with OnRegisterClick and registrationUseCase return DataResult Success, then send RegistrationEvent equal Success`() =
        runTest {

            // GIVEN
            val mockThrowable = Throwable("Something wrong")
            registrationViewModel.onAction(RegistrationAction.OnLoginUpdate("test"))
            registrationViewModel.onAction(RegistrationAction.OnPasswordUpdate("test"))
            val emailValidatorResult = mockk<EmailValidResult> {
                every { isEmailError } returns false
                every { errorMessage } returns ""
            }

            val passwordValidatorResult = mockk<PasswordValidResult> {
                every { isPasswordError } returns false
                every { errorMessage } returns ""
            }

            coEvery {
                emailValidator.validateEmail(
                    "test"
                )
            } returns emailValidatorResult

            coEvery {
                passwordValidator.validatePassword(
                    "test"
                )
            } returns passwordValidatorResult

            coEvery {
                registrationUseCase(
                    email = "test",
                    password = "test"
                )
            } returns flowOf(DomainResult.Error(mockThrowable))

            // WHEN
            registrationUseCase(email = "test", password = "test").test {
                val result = awaitItem()

                // THEN
                assertThat(result).isEqualTo(DomainResult.Error(mockThrowable))
                awaitComplete()
            }

            // WHEN
            registrationViewModel.eventChannel.test() {
                registrationViewModel.onAction(RegistrationAction.OnRegisterClick)

                // THEN
                val event = awaitItem()
                assertThat(event).isInstanceOf(RegistrationEvent.Error::class.java)
                expectNoEvents()
            }
            verify(exactly = 2) { registrationUseCase(email = "test", password = "test") }
        }

    @Test
    fun `When onAction is called with OnRegisterClick and registrationUseCase return DataResult Error, then send RegistrationEvent equal Error`() =
        runTest {

            // GIVEN
            registrationViewModel.onAction(RegistrationAction.OnLoginUpdate("test"))
            registrationViewModel.onAction(RegistrationAction.OnPasswordUpdate("test"))
            val emailValidatorResult = mockk<EmailValidResult> {
                every { isEmailError } returns false
                every { errorMessage } returns ""
            }

            val passwordValidatorResult = mockk<PasswordValidResult> {
                every { isPasswordError } returns false
                every { errorMessage } returns ""
            }

            coEvery {
                emailValidator.validateEmail(
                    "test"
                )
            } returns emailValidatorResult

            coEvery {
                passwordValidator.validatePassword(
                    "test"
                )
            } returns passwordValidatorResult

            coEvery {
                registrationUseCase(
                    email = "test",
                    password = "test"
                )
            } returns flowOf(DomainResult.Success(Unit))

            // WHEN
            registrationUseCase(email = "test", password = "test").test {
                val result = awaitItem()

                // THEN
                assertThat(result).isEqualTo(DomainResult.Success(Unit))
                awaitComplete()
            }

            // WHEN
            registrationViewModel.eventChannel.test {
                registrationViewModel.onAction(RegistrationAction.OnRegisterClick)

                // THEN
                val event = awaitItem()
                assertThat(event).isInstanceOf(RegistrationEvent.RegistrationSuccess::class.java)
                expectNoEvents()
            }
            verify(exactly = 2) { registrationUseCase(email = "test", password = "test") }
        }

    @Test
    fun `When login on success, then save onboarding displayed to datastore`() =
        runTest {
            // GIVEN
            coEvery {
                dataStore.putPreference(DISPLAY_HOME, true)
            } returns Unit

            // WHEN
            registrationViewModel.saveOnboardingDisplayed()

            // THEN
            advanceUntilIdle()
            coVerify { dataStore.putPreference(DISPLAY_HOME, true) }

        }
}