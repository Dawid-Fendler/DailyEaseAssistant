package pl.dawidfendler.finance_manager

import androidx.compose.ui.graphics.Color
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pl.dawidfendler.coroutines.DispatcherProvider
import pl.dawidfendler.datastore.DataStore
import pl.dawidfendler.datastore.DataStoreConstants.SELECTED_CURRENCY
import pl.dawidfendler.date.DateTime
import pl.dawidfendler.domain.model.currencies.ExchangeRateTable
import pl.dawidfendler.domain.model.transaction.Transaction
import pl.dawidfendler.domain.use_case.currencies.GetCurrenciesUseCase
import pl.dawidfendler.domain.use_case.currencies.GetSelectedCurrenciesUseCase
import pl.dawidfendler.domain.use_case.transaction.CreateTransactionUseCase
import pl.dawidfendler.domain.use_case.transaction.GetTransactionUseCase
import pl.dawidfendler.domain.use_case.user.GetAccountBalanceUseCase
import pl.dawidfendler.domain.use_case.user.UpdateAccountBalanceUseCase
import pl.dawidfendler.domain.util.Constants.POLISH_ZLOTY_CODE
import pl.dawidfendler.util.exception.MaxAccountBalanceException
import pl.dawidfendler.util.exception.MinAccountBalanceException
import pl.dawidfendler.util.flow.DomainResult
import java.math.BigDecimal

class FinanceManagerViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var financeManagerViewModel: FinanceManagerViewModel
    private lateinit var dataStore: DataStore
    private lateinit var getAccountBalanceUseCase: GetAccountBalanceUseCase
    private lateinit var getTransactionUseCase: GetTransactionUseCase
    private lateinit var getCurrenciesUseCase: GetCurrenciesUseCase
    private lateinit var getSelectedCurrenciesUseCase: GetSelectedCurrenciesUseCase
    private lateinit var createTransactionUseCase: CreateTransactionUseCase
    private lateinit var updateAccountBalanceUseCase: UpdateAccountBalanceUseCase
    private lateinit var dateTime: DateTime
    private lateinit var dispatcherProvider: DispatcherProvider

    @Before
    fun setUp() {
        dataStore = mockk(relaxed = true)
        getAccountBalanceUseCase = mockk()
        getCurrenciesUseCase = mockk()
        getTransactionUseCase = mockk()
        getSelectedCurrenciesUseCase = mockk()
        createTransactionUseCase = mockk()
        updateAccountBalanceUseCase = mockk()
        dispatcherProvider = mockk {
            every { io } returns mainDispatcherRule.testDispatcher
        }
        dateTime = mockk()

        every { getCurrenciesUseCase.invoke() } returns flowOf(DomainResult.Success(emptyList()))
        every { getAccountBalanceUseCase.invoke() } returns flowOf(DomainResult.Success(BigDecimal.ZERO))

        financeManagerViewModel = FinanceManagerViewModel(
            getAccountBalanceUseCase = getAccountBalanceUseCase,
            getTransactionUseCase = getTransactionUseCase,
            getCurrenciesUseCase = getCurrenciesUseCase,
            getSelectedCurrenciesUseCase = getSelectedCurrenciesUseCase,
            createTransactionUseCase = createTransactionUseCase,
            updateAccountBalanceUseCase = updateAccountBalanceUseCase,
            dateTime = dateTime,
            dataStore = dataStore
        )
    }

    @Test
    fun `When FinanceManagerViewModel is initialize, then FinanceManagerState has initial value`() {
        runTest {

            // THEN
            val state = financeManagerViewModel.state
            assertThat(state.currencies).isEqualTo(
                listOf(
                    ExchangeRateTable(
                        currencyName = "Polski złoty",
                        currencyCode = "PLN",
                        currencyMidValue = 1.0
                    )
                )
            )
            assertThat(state.isCurrenciesFetchDataError).isFalse()
            assertThat(state.isTransactionFetchDataError).isFalse()
            assertThat(state.selectedCurrency).isEqualTo(POLISH_ZLOTY_CODE)
            assertThat(state.accountBalance).isEqualTo("0")
            assertThat(state.accountBalanceColor).isEqualTo(Color.Black)
            assertThat(state.transaction).isEmpty()
        }
    }

    @Test
    fun `When getCurrenciesUseCase returns currencies, then state is updated`() = runTest {

        val currencies = listOf(ExchangeRateTable("Euro", "EUR", 10.0))
        every { getCurrenciesUseCase.invoke() } returns flowOf(DomainResult.Success(currencies))

        financeManagerViewModel = FinanceManagerViewModel(
            getAccountBalanceUseCase = getAccountBalanceUseCase,
            getTransactionUseCase = getTransactionUseCase,
            getCurrenciesUseCase = getCurrenciesUseCase,
            getSelectedCurrenciesUseCase = getSelectedCurrenciesUseCase,
            createTransactionUseCase = createTransactionUseCase,
            updateAccountBalanceUseCase = updateAccountBalanceUseCase,
            dateTime = dateTime,
            dataStore = dataStore
        )

        assertThat(financeManagerViewModel.state.currencies).contains(currencies[0])
    }

    @Test
    fun `When getCurrenciesUseCase returns error, then send ShowErrorBottomDialog event`() =
        runTest {

            val error = Throwable("Test")
            every { getCurrenciesUseCase.invoke() } returns flowOf(DomainResult.Error(error))

            financeManagerViewModel = FinanceManagerViewModel(
                getAccountBalanceUseCase = getAccountBalanceUseCase,
                getTransactionUseCase = getTransactionUseCase,
                getCurrenciesUseCase = getCurrenciesUseCase,
                getSelectedCurrenciesUseCase = getSelectedCurrenciesUseCase,
                createTransactionUseCase = createTransactionUseCase,
                updateAccountBalanceUseCase = updateAccountBalanceUseCase,
                dateTime = dateTime,
                dataStore = dataStore
            )

            financeManagerViewModel.eventChannel.test {
                // THEN
                val event = awaitItem()
                assertThat(event).isInstanceOf(FinanceManagerEvent.ShowErrorBottomDialog::class.java)
                expectNoEvents()
            }
        }

    @Test
    fun `When getAccountBalanceUseCase returns balance, then state is updated`() = runTest {
        val error = Throwable("Test")
        every { getCurrenciesUseCase.invoke() } returns flowOf(DomainResult.Success(emptyList()))
        every { getAccountBalanceUseCase.invoke() } returns flowOf(DomainResult.Error(error))

        financeManagerViewModel = FinanceManagerViewModel(
            getAccountBalanceUseCase = getAccountBalanceUseCase,
            getTransactionUseCase = getTransactionUseCase,
            getCurrenciesUseCase = getCurrenciesUseCase,
            getSelectedCurrenciesUseCase = getSelectedCurrenciesUseCase,
            createTransactionUseCase = createTransactionUseCase,
            updateAccountBalanceUseCase = updateAccountBalanceUseCase,
            dateTime = dateTime,
            dataStore = dataStore
        )

        financeManagerViewModel.eventChannel.test {
            // THEN

            val event = awaitItem()
            assertThat(event).isInstanceOf(FinanceManagerEvent.ShowErrorBottomDialog::class.java)
            expectNoEvents()
        }
    }

    @Test
    fun `When getAccountBalanceUseCase returns error, then show error dialog`() = runTest {
        every { getTransactionUseCase.invoke() } returns flowOf(DomainResult.Success(emptyList()))

        financeManagerViewModel.onAction(FinanceManagerAction.GetTransaction)

        assertThat(financeManagerViewModel.state.accountBalance).isEqualTo("0")
        assertThat(financeManagerViewModel.state.accountBalanceColor).isEqualTo(Color.Black)
    }

    @Test
    fun `When SavedSelectedCurrencies action is triggered, then selected currency is saved`() =
        runTest {
            // GIVEN
            val selectedCurrency = "USD"

            // WHEN
            financeManagerViewModel.onAction(
                FinanceManagerAction.SavedSelectedCurrencies(
                    selectedCurrency
                )
            )

            // THEN
            coVerify { dataStore.putPreference(SELECTED_CURRENCY, selectedCurrency) }
        }

    @Test
    fun `When getSelectedCurrenciesUseCase returns currency, then state is updated`() = runTest {
        every { getSelectedCurrenciesUseCase.invoke() } returns flowOf("USD")
        financeManagerViewModel.onAction(FinanceManagerAction.GetSelectedCurrencies)
        assertThat(financeManagerViewModel.state.selectedCurrency).isEqualTo("USD")
    }

    @Test
    fun `When getSelectedCurrenciesUseCase returns null, then state is updated`() = runTest {
        every { getSelectedCurrenciesUseCase.invoke() } returns flowOf(null)
        financeManagerViewModel.onAction(FinanceManagerAction.GetSelectedCurrencies)
        assertThat(financeManagerViewModel.state.selectedCurrency).isEqualTo("PLN")
    }

    @Test
    fun `When AddMoney is called, then balance and transaction are updated`() = runTest {
        every { updateAccountBalanceUseCase.invoke(BigDecimal("50"), true) } returns flowOf(
            DomainResult.Success(Unit)
        )

        financeManagerViewModel.onAction(FinanceManagerAction.AddMoney("50"))

        verify { updateAccountBalanceUseCase.invoke(BigDecimal("50"), true) }
    }

    @Test
    fun `When AddMoney is called, then throw MaxAccountBalanceException`() = runTest {

        val throwable = MaxAccountBalanceException()
        every { updateAccountBalanceUseCase.invoke(BigDecimal("50"), true) } returns
                flowOf(DomainResult.Error(throwable))

        financeManagerViewModel.eventChannel.test {

            financeManagerViewModel.onAction(FinanceManagerAction.AddMoney("50"))

            val event = awaitItem()
            assertThat(event).isInstanceOf(FinanceManagerEvent.ShowErrorBottomDialog::class.java)
            val showErrorDialogEvent = event as FinanceManagerEvent.ShowErrorBottomDialog
            assertThat(showErrorDialogEvent.errorMessage).isEqualTo("You have exceeded your account limit")
            expectNoEvents()
        }
    }

    @Test
    fun `When AddMoney is called, then throw Exception`() = runTest {

        val throwable = Exception()
        every { updateAccountBalanceUseCase.invoke(BigDecimal("50"), true) } returns
                flowOf(DomainResult.Error(throwable))

        financeManagerViewModel.eventChannel.test {

            financeManagerViewModel.onAction(FinanceManagerAction.AddMoney("50"))

            val event = awaitItem()
            assertThat(event).isInstanceOf(FinanceManagerEvent.ShowErrorBottomDialog::class.java)
            expectNoEvents()
        }
    }

    @Test
    fun `When SpentMoney is called, then balance and transaction are updated`() = runTest {
        every { updateAccountBalanceUseCase.invoke(BigDecimal("20"), false) } returns flowOf(
            DomainResult.Success(Unit)
        )

        financeManagerViewModel.onAction(FinanceManagerAction.SpentMoney("20"))

        verify { updateAccountBalanceUseCase.invoke(BigDecimal("20"), false) }
    }

    @Test
    fun `When SpentMoney is called, then throw MinAccountBalanceException`() = runTest {

        val throwable = MinAccountBalanceException()
        every { updateAccountBalanceUseCase.invoke(BigDecimal("50"), false) } returns flowOf(
            DomainResult.Error(throwable)
        )

        financeManagerViewModel.eventChannel.test {

            financeManagerViewModel.onAction(FinanceManagerAction.SpentMoney("50"))

            val event = awaitItem()
            assertThat(event).isInstanceOf(FinanceManagerEvent.ShowErrorBottomDialog::class.java)
            assertThat(throwable.message).isEqualTo(throwable.message)
            expectNoEvents()
        }
    }

    @Test
    fun `When SpentMoney is called, then throw Exception`() = runTest {

        val throwable = Exception()
        every { updateAccountBalanceUseCase.invoke(BigDecimal("50"), false) } returns flowOf(
            DomainResult.Error(throwable)
        )

        financeManagerViewModel.eventChannel.test {

            financeManagerViewModel.onAction(FinanceManagerAction.SpentMoney("50"))

            val event = awaitItem()
            assertThat(event).isInstanceOf(FinanceManagerEvent.ShowErrorBottomDialog::class.java)
            expectNoEvents()
        }
    }

    @Test
    fun `When getTransactionUseCase returns transactions, then state is updated`() = runTest {
        val transactions = listOf(Transaction("Bought coffee for 5$"))
        every { getTransactionUseCase.invoke() } returns flowOf(DomainResult.Success(transactions))

        financeManagerViewModel.onAction(FinanceManagerAction.GetTransaction)

        assertThat(financeManagerViewModel.state.transaction).contains("Bought coffee for 5$")
    }

    @Test
    fun `When getTransactionUseCase returns transactions, then return Error`() = runTest {
        val throwable = Throwable()
        every { getTransactionUseCase.invoke() } returns flowOf(DomainResult.Error(throwable))

        financeManagerViewModel.onAction(FinanceManagerAction.GetTransaction)

        assertThat(financeManagerViewModel.state.transaction).isEmpty()
    }

    @Test
    fun `When getCurrenciesUseCase fails, then error event is sent`() = runTest {
        every { getCurrenciesUseCase.invoke() } returns flowOf(DomainResult.Error(Throwable("Network error")))

        financeManagerViewModel = FinanceManagerViewModel(
            getAccountBalanceUseCase = getAccountBalanceUseCase,
            getTransactionUseCase = getTransactionUseCase,
            getCurrenciesUseCase = getCurrenciesUseCase,
            getSelectedCurrenciesUseCase = getSelectedCurrenciesUseCase,
            createTransactionUseCase = createTransactionUseCase,
            updateAccountBalanceUseCase = updateAccountBalanceUseCase,
            dateTime = dateTime,
            dataStore = dataStore
        )

        val event = financeManagerViewModel.eventChannel.first()
        assertThat(event).isEqualTo(FinanceManagerEvent.ShowErrorBottomDialog("Problem with download currencies"))
    }
}