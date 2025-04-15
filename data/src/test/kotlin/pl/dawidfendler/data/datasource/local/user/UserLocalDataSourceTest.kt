package pl.dawidfendler.data.datasource.local.user

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import pl.dawidfendler.data.database.user.UserDao
import pl.dawidfendler.data.model.user.userEntityTest

class UserLocalDataSourceTest {

    private lateinit var userDao: UserDao
    private lateinit var userLocalDataSource: UserLocalDataSourceImpl

    @Before
    fun setUp() {
        userDao = mockk(relaxed = true)
        userLocalDataSource = UserLocalDataSourceImpl(userDao)
    }

    @Test
    fun `When insert is called, then it should call insert on the dao`() {
        runTest {
            // Given
            val user = userEntityTest

            // When
            userLocalDataSource.insert(user)

            // Then
            coVerify { userDao.insert(user) }
        }
    }

    @Test
    fun `When getUser is called, then it should return user entity`() {
        runTest {
            // Given
            val user = userEntityTest
            coEvery { userDao.getUser() } returns user

            // When
            val result = userLocalDataSource.getUser()

            // Then
            assertThat(user).isEqualTo(result)
        }
    }

    @Test
    fun `When getUser is called, then it should return null`() {
        runTest {
            // Given
            coEvery { userDao.getUser() } returns null

            // When
            val result = userLocalDataSource.getUser()

            // Then
            assertThat(result).isNull()
        }
    }

    @Test
    fun `When getAccountBalance is called, then it should return account balance`() {
        runTest {
            // Given
            val accountBalance = 500.0
            coEvery { userDao.getAccountBalance() } returns accountBalance

            // When
            val result = userLocalDataSource.getAccountBalance()

            // Then
            assertThat(accountBalance).isEqualTo(result)
        }
    }

    @Test
    fun `When getAccountBalance is called, then it should return null`() {
        runTest {
            // Given
            coEvery { userDao.getAccountBalance() } returns null

            // When
            val result = userLocalDataSource.getAccountBalance()

            // Then
            assertThat(result).isNull()
        }
    }

    @Test
    fun `When updateAccountBalance is called, then should call updateAccountBalance on the dao`() {
        runTest {
            // Given
            val accountBalance = 300.0
            coEvery { userDao.updateAccountBalance(accountBalance) } just runs

            // When
            userLocalDataSource.updateAccountBalance(accountBalance)

            // Then
            coVerify { userDao.updateAccountBalance(accountBalance) }
        }
    }

    @Test
    fun `When deleteUser is called, then it should call deleteUser on the dao`() {
        runTest {
            // Given
            coEvery { userDao.deleteUser() } just runs

            // When
            userLocalDataSource.deleteUser()

            // Then
            coVerify { userDao.deleteUser() }
        }
    }

    @Test
    fun `When getUserCurrencies is called, then it should return null`() {
        runTest {
            // Given
            coEvery { userDao.getUserCurrencies() } returns null

            // When
            val result = userLocalDataSource.getUserCurrencies()

            // Then
            assertThat(result).isNull()
        }
    }

    @Test
    fun `When updateCurrencies is called, then should call updateCurrencies on the dao`() {
        runTest {
            // Given
            val currencies = "PLN-USD"
            coEvery { userDao.updateCurrencies(currencies) } just runs

            // When
            userLocalDataSource.updateCurrencies(currencies)

            // Then
            coVerify { userDao.updateCurrencies(currencies) }
        }
    }
}