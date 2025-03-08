package pl.dawidfendler.authentication.login

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.google.firebase.auth.FirebaseUser
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
import pl.dawidfendler.domain.use_case.authentication_use_case.GoogleLoginUseCase
import pl.dawidfendler.domain.use_case.authentication_use_case.LoginUseCase
import pl.dawidfendler.util.flow.DomainResult

class LoginViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var loginUseCase: LoginUseCase
    private lateinit var googleLoginUseCase: GoogleLoginUseCase
    private lateinit var dataStore: DataStore
    private lateinit var dispatcherProvider: DispatcherProvider

    @Before
    fun setUp() {
        loginUseCase = mockk()
        googleLoginUseCase = mockk()
        dataStore = mockk()
        dispatcherProvider = mockk()
        every { dispatcherProvider.io } returns Dispatchers.IO
        loginViewModel = LoginViewModel(
            loginUseCase = loginUseCase,
            googleLoginUseCase = googleLoginUseCase,
            dataStore = dataStore,
            dispatcherProvider = dispatcherProvider
        )
    }

    @Test
    fun `When LoginViewModel is initialize, then LoginState has initial value`() {
        // GIVEN
        val loginState = loginViewModel.state

        // THEN
        assertThat(loginState.isLogin).isFalse()
        assertThat(loginState.isPasswordVisible).isFalse()
        assertThat(loginState.errorMessage).isEqualTo(0)
        assertThat(loginState.email).isEmpty()
        assertThat(loginState.password).isEmpty()
        assertThat(loginState.isError).isFalse()
    }

    @Test
    fun `When onAction is called with OnTogglePasswordVisibilityClick, then isPasswordVisible is equal true`() {
        // GIVEN
        loginViewModel.onAction(LoginAction.OnTogglePasswordVisibilityClick)

        // THEN
        assertThat(loginViewModel.state.isPasswordVisible).isTrue()
    }

    @Test
    fun `When onAction is called with OnLoginUpdate, then email is equal 'test'`() {
        // GIVEN
        loginViewModel.onAction(LoginAction.OnLoginUpdate(login = "test"))

        // THEN
        assertThat(loginViewModel.state.email).isEqualTo("test")
    }

    @Test
    fun `When onAction is called with OnPasswordUpdate, then password is equal 'test'`() {
        // GIVEN
        loginViewModel.onAction(LoginAction.OnPasswordUpdate(password = "test"))

        // THEN
        assertThat(loginViewModel.state.password).isEqualTo("test")
    }

    @Test
    fun `When onAction is called with OnGoogleLoginError, then send Error`() =
        runTest {
            // WHEN
            loginViewModel.eventChannel.test {

                loginViewModel.onAction(LoginAction.OnGoogleLoginError)

                // THEN
                val event = awaitItem()
                assertThat(event).isInstanceOf(LoginEvent.Error::class.java)
                expectNoEvents()
            }
        }

    @Test
    fun `When onAction is called with OnGoogleLoginClick and googleLoginUseCase return DataResult Success, then send LoginEvent equal Success`() =
        runTest {
            // GIVEN
            val mockFirebaseUser = mockk<FirebaseUser>()
            coEvery { googleLoginUseCase(idToken = "token") } returns flowOf(
                DomainResult.Success(
                    mockFirebaseUser
                )
            )

            // WHEN
            googleLoginUseCase(idToken = "token").test {
                val result = awaitItem()

                // THEN
                assertThat(result).isEqualTo(DomainResult.Success(mockFirebaseUser))
                awaitComplete()
            }

            // WHEN
            loginViewModel.eventChannel.test {
                loginViewModel.onAction(LoginAction.OnGoogleLoginClick("token"))

                // THEN
                val event = awaitItem()
                assertThat(event).isInstanceOf(LoginEvent.LoginSuccess::class.java)
                expectNoEvents()
            }
            verify(exactly = 2) { googleLoginUseCase(idToken = "token") }
        }

    @Test
    fun `When onAction is called with OnGoogleLoginClick and googleLoginUseCase return DataResult Error, then send LoginEvent equal Error`() =
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
            loginViewModel.eventChannel.test {
                loginViewModel.onAction(LoginAction.OnGoogleLoginClick("token"))

                // THEN
                val event = awaitItem()
                assertThat(event).isInstanceOf(LoginEvent.Error::class.java)
                expectNoEvents()
            }
            verify(exactly = 2) { googleLoginUseCase(idToken = "token") }
        }

    @Test
    fun `When onAction is called with OnLoginClick and loginUseCase return DataResult Success, then send LoginEvent equal Success`() =
        runTest {
            // GIVEN
            val mockFirebaseUser = mockk<FirebaseUser>()
            coEvery {
                loginUseCase(
                    email = "test",
                    password = "test"
                )
            } returns flowOf(DomainResult.Success(mockFirebaseUser))

            // WHEN
            loginUseCase(email = "test", password = "test").test {
                val result = awaitItem()

                // THEN
                assertThat(result).isEqualTo(DomainResult.Success(mockFirebaseUser))
                awaitComplete()
            }

            // GIVEN
            loginViewModel.onAction(LoginAction.OnLoginUpdate("test"))
            loginViewModel.onAction(LoginAction.OnPasswordUpdate("test"))

            // THEN
            assertThat(loginViewModel.state.email).isEqualTo("test")
            assertThat(loginViewModel.state.password).isEqualTo("test")

            // WHEN
            loginViewModel.eventChannel.test {
                loginViewModel.onAction(LoginAction.OnLoginClick)

                // THEN
                val event = awaitItem()
                assertThat(event).isInstanceOf(LoginEvent.LoginSuccess::class.java)
                expectNoEvents()
            }
            verify(exactly = 2) { loginUseCase(email = "test", password = "test") }
        }

    @Test
    fun `When onAction is called with OnLoginClick and loginUseCase return DataResult Error, then send LoginEvent equal Error`() =
        runTest {
            // GIVEN
            val mockThrowable = Throwable("Something wrong")
            coEvery { loginUseCase(email = "test", password = "test") } returns flowOf(
                DomainResult.Error(
                    mockThrowable
                )
            )

            // WHEN
            loginUseCase(email = "test", password = "test").test {
                val result = awaitItem()

                // THEN
                assertThat(result).isEqualTo(DomainResult.Error(mockThrowable))
                awaitComplete()
            }

            // GIVEN
            loginViewModel.onAction(LoginAction.OnLoginUpdate("test"))
            loginViewModel.onAction(LoginAction.OnPasswordUpdate("test"))

            // THEN
            assertThat(loginViewModel.state.email).isEqualTo("test")
            assertThat(loginViewModel.state.password).isEqualTo("test")

            // WHEN
            loginViewModel.eventChannel.test {
                loginViewModel.onAction(LoginAction.OnLoginClick)

                // THEN
                val event = awaitItem()
                assertThat(event).isInstanceOf(LoginEvent.Error::class.java)
                expectNoEvents()
            }
            verify(exactly = 2) { loginUseCase(email = "test", password = "test") }
        }


    @Test
    fun `When login on success, then save onboarding displayed to datastore`() =
        runTest {
            // GIVEN
            coEvery {
                dataStore.putPreference(DISPLAY_HOME, true)
            } returns Unit

            // WHEN
            loginViewModel.saveOnboardingDisplayed()

            // THEN
            advanceUntilIdle()
            coVerify { dataStore.putPreference(DISPLAY_HOME, true) }

        }
}