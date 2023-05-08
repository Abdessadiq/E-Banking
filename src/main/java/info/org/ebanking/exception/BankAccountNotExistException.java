package info.org.ebanking.exception;

public class BankAccountNotExistException extends Exception {
    public BankAccountNotExistException(String message) {
        super(message);
    }
}
