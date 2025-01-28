package pl.dawidfendler.authentication.login

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.google.firebase.auth.FirebaseUser
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pl.dawidfendler.authentication.MainDispatcherRule
import pl.dawidfendler.domain.use_case.authentication_use_case.GoogleLoginUseCase
import pl.dawidfendler.domain.use_case.authentication_use_case.LoginUseCase
import pl.dawidfendler.util.flow.DataResult

class LoginViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var loginUseCase: LoginUseCase
    private lateinit var googleLoginUseCase: GoogleLoginUseCase

    @Before
    fun setUp() {
        loginUseCase = mockk()
        googleLoginUseCase = mockk()
        loginViewModel = LoginViewModel(
            loginUseCase = loginUseCase,
            googleLoginUseCase = googleLoginUseCase
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
                DataResult.Success(
                    mockFirebaseUser
                )
            )

            // WHEN
            googleLoginUseCase(idToken = "token").test {
                val result = awaitItem()

                // THEN
                assertThat(result).isEqualTo(DataResult.Success(mockFirebaseUser))
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
                DataResult.Error(
                    mockThrowable
                )
            )

            // WHEN
            googleLoginUseCase(idToken = "token").test {
                val result = awaitItem()

                // THEN
                assertThat(result).isEqualTo(DataResult.Error(mockThrowable))
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
            } returns flowOf(DataResult.Success(mockFirebaseUser))

            // WHEN
            loginUseCase(email = "test", password = "test").test {
                val result = awaitItem()

                // THEN
                assertThat(result).isEqualTo(DataResult.Success(mockFirebaseUser))
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
                DataResult.Error(
                    mockThrowable
                )
            )

            // WHEN
            loginUseCase(email = "test", password = "test").test {
                val result = awaitItem()

                // THEN
                assertThat(result).isEqualTo(DataResult.Error(mockThrowable))
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


}