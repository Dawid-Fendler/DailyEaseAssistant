package pl.dawidfendler.data.repository

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import pl.dawidfendler.data.datasource.local.user.UserLocalDataSource
import pl.dawidfendler.data.mapper.toDomain
import pl.dawidfendler.data.mapper.toEntity
import pl.dawidfendler.data.model.user.userEntityTest
import pl.dawidfendler.data.model.user.userTest
import java.math.BigDecimal

class UserRepositoryTest {

    private lateinit var userLocalDataSource: UserLocalDataSource
    private lateinit var userRepository: UserRepositoryImpl

    @Before
    fun setUp() {
        userLocalDataSource = mockk(relaxed = true)
        userRepository = UserRepositoryImpl(userLocalDataSource)
    }

    @Test
    fun `When insert is called and old user is null, then it should call insert on the datasource`() {
        runTest {
            // Given
            val user = userTest
            coEvery { userLocalDataSource.getUser() } returns null
            // When
            userRepository.insertUser(user)

            // Then
            coVerify { userLocalDataSource.insert(user.toEntity()) }
        }
    }

    @Test
    fun `When insert is called and user is not null, then it should not call insert on the datasource`() =
        runTest {
            // Given
            val user = userTest
            coEvery { userLocalDataSource.getUser() } returns userEntityTest

            // When
            userRepository.insertUser(userTest)

            // Then
            coVerify(exactly = 0) { userLocalDataSource.insert(user.toEntity()) }
        }

    @Test
    fun `When getUser is called, then it should return user entity`() {
        runTest {
            // Given
            val user = userEntityTest
            coEvery { userLocalDataSource.getUser() } returns user

            // When
            val result = userRepository.getUser()

            // Then
            assertThat(user.toDomain()).isEqualTo(result)
        }
    }

    @Test
    fun `When getUser is called, then it should return null`() {
        runTest {
            // Given
            coEvery { userLocalDataSource.getUser() } returns null

            // When
            val result = userRepository.getUser()

            // Then
            assertThat(result).isNull()
        }
    }

    @Test
    fun `When getAccountBalance is called, then it should return account balance`() {
        runTest {
            // Given
            val accountBalance = 500.0
            coEvery { userLocalDataSource.getAccountBalance() } returns accountBalance

            // When
            val result = userRepository.getAccountBalance()

            // Then
            assertThat(accountBalance.toBigDecimal()).isEqualTo(result)
        }
    }

    @Test
    fun `When getAccountBalance is called, then it should return big decimal zero`() {
        runTest {
            // Given
            coEvery { userLocalDataSource.getAccountBalance() } returns null

            // When
            val result = userRepository.getAccountBalance()

            // Then
            assertThat(result).isEqualTo(BigDecimal.ZERO)
        }
    }

    @Test
    fun `When updateAccountBalance is called, then should call updateAccountBalance on the datasource`() {
        runTest {
            // Given
            val accountBalance = 300.0
            coEvery { userLocalDataSource.updateAccountBalance(accountBalance) } just runs

            // When
            userRepository.updateAccountBalance(accountBalance.toBigDecimal())

            // Then
            coVerify { userLocalDataSource.updateAccountBalance(accountBalance) }
        }
    }

    @Test
    fun `When deleteUser is called, then it should call deleteUser on the datasource`() {
        runTest {
            // Given
            coEvery { userLocalDataSource.deleteUser() } just runs

            // When
            userRepository.deleteUser()

            // Then
            coVerify { userLocalDataSource.deleteUser() }
        }
    }

    @Test
    fun `When getUserCurrencies is called, then it should return user currencies`() {
        runTest {
            // Given
            val currencies = listOf("PLN", "USD", "GBP")
            val data = "PLN-USD-GBP"
            coEvery { userLocalDataSource.getUserCurrencies() } returns data

            // When
            val result = userRepository.getUserCurrencies()

            // Then
            assertThat(result).isEqualTo(currencies)
        }
    }

    @Test
    fun `When getUserCurrencies is called, then it should return empty`() {
        runTest {
            // Given
            coEvery { userLocalDataSource.getUserCurrencies() } returns null

            // When
            val result = userRepository.getUserCurrencies()

            // Then
            assertThat(result).isEqualTo(emptyList<String>())
        }
    }

    @Test
    fun `When updateUserCurrencies is called, then should call updateCurrencies on the datasource`() {
        runTest {
            // Given
            val userCurrencies = "PLN-USD-GBP"
            coEvery { userLocalDataSource.updateCurrencies(userCurrencies) } just runs

            // When
            userRepository.updateUserCurrencies(listOf("PLN", "USD", "GBP"))

            // Then
            coVerify { userLocalDataSource.updateCurrencies(userCurrencies) }
        }
    }
}