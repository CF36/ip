package slowbro;

public class InvalidCommandException extends Exception {
    String usageMessage;
    public InvalidCommandException(String message) {
        usageMessage = message;
    }
}
