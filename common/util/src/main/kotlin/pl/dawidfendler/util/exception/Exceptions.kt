package pl.dawidfendler.util.exception

class MaxAccountBalanceException : Exception("You have exceeded your account limit")
class MinAccountBalanceException : Exception("You have exceeded your account debt limit")
