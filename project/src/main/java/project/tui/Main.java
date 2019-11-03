package project.tui;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import project.DefaultBoard;
import project.model.GameState;

public class Main {

    public static Logger logger = LogManager.getLogger(Main.class);

    @SuppressWarnings("PMD")
    public static void print(String message) {
        System.out.println(message);
    }

    @SuppressWarnings("PMD")
    public static void warn(String message) {
        System.out.println(ANSIColor.RED + message + ANSIColor.RESET);
    }

    @SuppressWarnings("PMD")
    public static void trace(String message) {
        logger.trace(message);
    }

    @SuppressWarnings("PMD.UseVarargs")
    public static void main(String[] args) {

        print("Starting JumpIn");
        DefaultBoard board = new DefaultBoard();

        // JumpIn client
        Scanner scanner = new Scanner(System.in);

        JumpInClient client = new JumpInClient();

        while (board.getCurrentGameState() == GameState.IN_PROGRESS) {

            try {
                print(client.getPrompt(board));

                String userInput = scanner.nextLine();
                JumpInClient.Command command = client.parseInput(userInput);

                if (command instanceof JumpInClient.RabbitCommand) {
                    board.jump(command.direction, command.coordinate);
                }

                if (command instanceof JumpInClient.FoxCommand) {
                    JumpInClient.FoxCommand foxCommand =
                            (JumpInClient.FoxCommand) command;

                    board.slide(foxCommand.direction, foxCommand.moveSpaces,
                            foxCommand.coordinate);
                }

            }
            catch(Exception e) {
                warn(e.getMessage());
            }

        }
        scanner.close();
        board.getCurrentGameState();
        print(board.toString());
        print(ANSIColor.GREEN + "Game has been solved " +
                "successfully!" + ANSIColor.RESET);

    }

}
