package error

enum class Errors(val description: String) {
    InvalidEmailOrPassword("Invalid E-mail/password!"),
    OperatorAlreadyExist("Account already exists!"),
    OperatorDoesNotExist("Account does not exist!"),
    AccountDoesNotExist("Account does not exist!"),
    AccountNotActivated("Account not activated!"),
    SomeDataIsMissing("Some data is missing!"),
    BlockedAccount("Blocked account!"),
    TooManyRequest("Too many requests!"),
}