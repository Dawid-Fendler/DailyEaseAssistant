package pl.dawidfendler.domain.use_case.user

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pl.dawidfendler.coroutines.DispatcherProvider
import pl.dawidfendler.domain.MainDispatcherRule
import pl.dawidfendler.domain.repository.UserRepository
import pl.dawidfendler.domain.util.Constants.MAX_ACCOUNT_BALANCE
import pl.dawidfendler.domain.util.Constants.MIN_ACCOUNT_BALANCE
import pl.dawidfendler.util.exception.MaxAccountBalanceException
import pl.dawidfendler.util.exception.MinAccountBalanceException
import pl.dawidfendler.util.flow.DomainResult

class UpdateAccountBalanceUseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var repository: UserRepository
    private lateinit var updateAccountBalanceUseCase: UpdateAccountBalanceUseCase

    @Before
    fun setUp() {
        dispatcherProvider = mockk {
            every { io } returns mainDispatcherRule.testDispatcher
        }
        repository = mockk()
        updateAccountBalanceUseCase = UpdateAccountBalanceUseCase(repository, dispatcherProvider)
    }

    @Test
    fun `When updateAccountBalance is called and isAddMoney is true, then return DomainResult Success`() {
        runTest {
            // GIVEN
            val accountBalance = 500.0.toBigDecimal()
            coEvery { repository.getAccountBalance() } returns   accountBalance
            coEvery { repository.updateAccountBalance(accountBalance + accountBalance) } just runs

            // WHEN
            updateAccountBalanceUseCase.invoke(accountBalance, isAddMoney = true).test {
                val result = awaitItem()

                // Then
                assertThat(result).isEqualTo(DomainResult.Success(Unit))
                awaitComplete()
            }
        }
    }

    @Test
    fun `When updateAccountBalance is called and isAddMoney is false, then return DomainResult Success`() {
        runTest {
            // GIVEN
            val accountBalance = 500.0.toBigDecimal()
            coEvery { repository.getAccountBalance() } returns   accountBalance
            coEvery { repository.updateAccountBalance(accountBalance - accountBalance) } just runs

            // WHEN
            updateAccountBalanceUseCase.invoke(accountBalance, isAddMoney = false).test {
                val result = awaitItem()

                // Then
                assertThat(result).isEqualTo(DomainResult.Success(Unit))
                awaitComplete()
            }
        }
    }

    @Test
    fun `When updateAccountBalance is called and isAddMoney is true, then return DomainResult Error MaxAccountBalanceException`() {
        runTest {
            // GIVEN
            val accountBalance = 500.0.toBigDecimal()
            val exception = MaxAccountBalanceException()
            coEvery { repository.getAccountBalance() } returns MAX_ACCOUNT_BALANCE.toBigDecimal()
            coEvery { repository.updateAccountBalance(accountBalance) } throws exception

            // WHEN
            updateAccountBalanceUseCase.invoke(accountBalance, isAddMoney = true).test {
                val result = awaitItem()

                // Then
                assertThat(result).isInstanceOf(DomainResult.Error::class.java)

                val error = (result as DomainResult.Error).error
                assertThat(error).isInstanceOf(MaxAccountBalanceException::class.java)
                assertThat(error.message).isEqualTo("You have exceeded your account limit")
                awaitComplete()
            }
        }
    }

    @Test
    fun `When updateAccountBalance is called and isAddMoney is false, then return DomainResult Error MinAccountBalanceException`() {
        runTest {
            // GIVEN
            val accountBalance = 500.0.toBigDecimal()
            val exception = MinAccountBalanceException()
            coEvery { repository.getAccountBalance() } returns MIN_ACCOUNT_BALANCE.toBigDecimal()
            coEvery { repository.updateAccountBalance(accountBalance ) } throws exception

            // WHEN
            updateAccountBalanceUseCase.invoke(accountBalance, isAddMoney = false).test {
                val result = awaitItem()

                // Then
                assertThat(result).isInstanceOf(DomainResult.Error::class.java)

                val error = (result as DomainResult.Error).error
                assertThat(error).isInstanceOf(MinAccountBalanceException::class.java)
                assertThat(error.message).isEqualTo("You have exceeded your account debt limit")
                awaitComplete()
            }
        }
    }

    @Test
    fun `When updateAccountBalance is called, then return DomainResult Error`() {
        runTest {
            // GIVEN
            val accountBalance = 500.0.toBigDecimal()
            val exception = Exception()
            coEvery { repository.getAccountBalance() } throws  exception
            coEvery { repository.updateAccountBalance(accountBalance) } throws exception

            // WHEN
            updateAccountBalanceUseCase.invoke(accountBalance, isAddMoney = true).test {
                val result = awaitItem()

                // Then
                assertThat(result).isEqualTo(DomainResult.Error(exception))
                awaitComplete()
            }
        }
    }
}