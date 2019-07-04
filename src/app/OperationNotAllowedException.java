package app;

/**
 * Exception thrown whenever an unauthorized employee tries to perform an operation
 *
 * By Sigurd Thorlund s184189
 */
public class OperationNotAllowedException extends Exception {
    private String errorMessage = "You are not authorized to perform this action";

    public OperationNotAllowedException() {
        super();
    }

    public String getMessage() {
        return errorMessage;
    }
}
