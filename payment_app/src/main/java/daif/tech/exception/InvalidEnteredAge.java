package daif.tech.exception;

public class InvalidEnteredAge extends RuntimeException {

    private String message = "Age must be positive Integer value and equal or over 18";

    @Override
    public String getMessage() {
        return message;
    }
}
