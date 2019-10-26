package project;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JumpInClient {

    public static Logger logger = LogManager.getLogger(Main.class);

    public String getPrompt(Board board) {
        String prompt = "";

        prompt += board.toString();

        prompt += "\n";

        prompt += "Please type one of the following commands: " +
                "\n";
        prompt += ANSIColor.GREEN + "-> Jump Rabbit row (current row" +
                " of Rabbit), " + "column " +
                "(current column of Rabbit) Direction(Right, Left, Up, Down)"
                + ANSIColor.RESET
                + "\n" ;

        prompt += ANSIColor.GREEN + "-> Slide Fox row (e.g., 1), columns" +
                " (e.g.," + " 2) Number of board " +
                "units / spaces (e.g., 2)" + ANSIColor.RESET +
                "\n";

        prompt += ANSIColor.YELLOW + "Sample commands: \n Jump Rabbit 1,1 " +
                "Right" +
                " \n " +
                "Slide" +
                " Fox " +
                "1,2 2 Left" + ANSIColor.RESET
                + "\n" ;

        prompt += ANSIColor.CYAN + "Please enter command: " + ANSIColor.RESET;
        prompt += "\n";

        return prompt;
    }

    // Coordinate
    // Direction
    // number of items only for fox

    public RabbitCommand parseRabbitCommand(String line) throws Exception {
        // Jump Rabbit 1,2 left
        // Capture groups
        // 1: Coordinate row : 1
        // 2: Coordinate column : 2
        // 3: Direction : left
        Pattern regexPattern = Pattern.compile("Jump\\s+Rabbit\\s+(\\d)," +
                "\\s*(\\d)\\s+" +
                "(up|down|left|right)", Pattern.CASE_INSENSITIVE);

        Matcher matcher = regexPattern.matcher(line);

        logger.trace("attempting to parse rabbit command");

        if (matcher.find()) {

            // Extract arguments
            Coordinate coordinate =
                    new Coordinate(Integer.parseInt(matcher.group(1)) - 1,
                            Integer.parseInt(matcher.group(2)) - 1);

            Direction direction =
                    Direction.valueOf(matcher.group(3).toUpperCase());

            logger.trace("successfully parsed rabbit command: " +
                    "Coordinate: " + coordinate + " " +
                    "Direction: " + direction);

            logger.trace(matcher.group());

            RabbitCommand command = new RabbitCommand(coordinate, direction);
            return command;
        }
        else {
            throw new Exception("Failed to match rabbit command");
        }
    }

    public FoxCommand parseFoxCommand(String line) throws Exception {

        // Slide Fox 1,2 2 left
        // Capture groups
        // 1: Coordinate row : 1
        // 2: Coordinate column : 2
        // 3: MoveSpaces : 2
        // 4: Direction : left
        Pattern regexPattern = Pattern.compile("Slide\\s+Fox\\s+(\\d)," +
                "\\s*(\\d)\\s+(\\d)\\s+" +
                "(up|down|left|right)", Pattern.CASE_INSENSITIVE);

        Matcher matcher = regexPattern.matcher(line);

        logger.trace("attempting to parse fox command");
        if (matcher.find()) {
            // Extract arguments
            Coordinate coordinate =
                    new Coordinate(Integer.parseInt(matcher.group(1)) - 1,
                    Integer.parseInt(matcher.group(2)) - 1);

            int moveSpaces = Integer.parseInt(matcher.group(3));
            Direction direction =
                    Direction.valueOf(matcher.group(4).toUpperCase());

            logger.trace("successfully parsed fox command: " +
                    "Coordinate: " + coordinate +  " " +
                    "Move Spaces: " + moveSpaces + " " +
                    "Direction: " + direction);
            logger.trace(matcher.group());

            FoxCommand command = new FoxCommand(coordinate, direction,
                    moveSpaces);
            return command;
        }
        else {
            throw new Exception("Failed to match fox command");
        }
    }

    public Command parseInput(String input) {
        logger.trace("User input received");
        logger.trace(input);

        if (input.length() == 0) {
            throw new IllegalArgumentException("empty " +
                    "input is invalid");
        }

        // Rabbit commands
        try {
            parseRabbitCommand(input.trim());
        }
        catch (Exception e) {
            logger.trace(e.getMessage());
        }

        // Fox commands
        try {
            return parseFoxCommand(input.trim());
        }
        catch (Exception e) {
            logger.trace(e.getMessage());
        }

       throw new IllegalArgumentException("invalid input");
    }

    class Command {
        Coordinate coordinate;
        Direction direction;

        protected Command(Coordinate coordinate, Direction direction) {
            this.coordinate = coordinate;
            this.direction = direction;
        }
    }

    class FoxCommand extends Command {
        int moveSpaces;

        public FoxCommand(Coordinate coordinate, Direction direction,
                          int moveSpaces) {
            super(coordinate, direction);
            this.moveSpaces = moveSpaces;
        }
    }

    class RabbitCommand extends Command {

        public RabbitCommand(Coordinate coordinate, Direction direction) {
            super(coordinate, direction);
        }
    }

}
