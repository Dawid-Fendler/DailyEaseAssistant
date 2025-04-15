package pl.dawidfendler.account_balance.bottom_sheet

sealed class ActionBottomSheet {
    data object AddMoneyBottomSheet : ActionBottomSheet()
    data object SpentMoneyBottomSheet : ActionBottomSheet()
    data object CurrenciesBottomSheet : ActionBottomSheet()
    data object TransactionHistoryBottomSheet : ActionBottomSheet()
    data object AccountBalanceCurrenciesBottomSheet: ActionBottomSheet()
}
