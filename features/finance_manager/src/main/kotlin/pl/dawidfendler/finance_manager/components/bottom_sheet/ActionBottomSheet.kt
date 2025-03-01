package pl.dawidfendler.finance_manager.components.bottom_sheet

sealed class ActionBottomSheet {
    data object AddMoneyBottomSheet : ActionBottomSheet()
    data object SpentMoneyBottomSheet : ActionBottomSheet()
    data object CurrenciesBottomSheet : ActionBottomSheet()
    data object TransactionHistoryBottomSheet : ActionBottomSheet()
}
