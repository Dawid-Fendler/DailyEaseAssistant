package pl.dawidfendler.domain.use_case.transaction

import pl.dawidfendler.domain.model.transaction.Transaction

val transactionData = Transaction(
    amount = 100.0,
    date = 1627849200000,
    description = "Grocery shopping",
    accountName = "Checking Account",
    categoryName = "Groceries"
)