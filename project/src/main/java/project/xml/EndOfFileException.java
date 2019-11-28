package project.xml;

public class EndOfFileException extends Throwable {
    public EndOfFileException() {
        super("Reached the end of the file while parsing");
    }

}
