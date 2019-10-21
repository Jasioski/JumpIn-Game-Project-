package project;

/**
 * Thrown if the ContainerItem already has a Containable object.
 */
public class HoleAlreadyHasRabbitException extends Exception {
    /**
     * Creates the exception with the desired message
     * @param s The message for the exception.
     */
    public HoleAlreadyHasRabbitException(String s) {
        super(s);
    }
}
