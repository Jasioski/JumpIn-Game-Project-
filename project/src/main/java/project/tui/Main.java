package project.tui;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import project.model.Board;
import project.model.Coordinate;
import project.model.DefaultBoard;
import project.model.GameState;
import project.model.Fox;
import project.solver.Solver;

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
        Solver solver = new Solver();

        print("Starting JumpIn");
        DefaultBoard defBoard = new DefaultBoard();
        Board board = defBoard.getBoard();
        System.out.println(board.currentGameState);
        System.out.println(board.getItem(new Coordinate(0,3)));
        System.out.println(board.getItem(new Coordinate(4,2)));
        System.out.println(board.getItem(new Coordinate(1,1)));
        Fox fox = (Fox) board.getItem(new Coordinate(1,1));
        System.out.println(fox.orientation);
        System.out.println(board.getItem(new Coordinate(3,2)));
        // JumpIn client
        Scanner scanner = new Scanner(System.in);

        JumpInClient client = new JumpInClient();

        while (board.currentGameState == GameState.IN_PROGRESS) {

            try {
                print(client.getPrompt(board));

                String userInput = scanner.nextLine();
                JumpInClient.Command command = client.parseInput(userInput);

                if (command instanceof JumpInClient.RabbitCommand) {
                    board = board.jump(command.direction, command.coordinate);
                }

                if (command instanceof JumpInClient.FoxCommand) {
                    System.out.println("In Fox");
                    JumpInClient.FoxCommand foxCommand =
                            (JumpInClient.FoxCommand) command;

                    System.out.println("Parsed Command");
                    board = board.slide(foxCommand.direction,
                            foxCommand.moveSpaces,
                            foxCommand.coordinate);
                    System.out.println("No error");
                }
                solver.solve(board);
            }
            catch(Exception e) {
                System.out.println(e);
            }

        }

        scanner.close();
       // board.getCurrentGameState();
        print(board.toString());
        print(ANSIColor.GREEN + "Game has been solved " +
                "successfully!" + ANSIColor.RESET);

    }

}
