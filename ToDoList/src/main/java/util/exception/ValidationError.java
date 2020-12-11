package util.exception;

/**
 * Исключение при создании объектов
 */
public class ValidationError extends Exception {
    public ValidationError(String message) {
        super(message);
    }
}
