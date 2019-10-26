package project;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JumpInClientTest {

    @Test
    /**
     *  Test rabbit jumping
     */
     void testRabbitJump() {
         JumpInClient client = new JumpInClient();
         String userInput = "Jump Rabbit 1,1 Up";

         try {
             JumpInClient.Command command = client.parseRabbitCommand(userInput);

             assertTrue(command instanceof JumpInClient.RabbitCommand, "should be" +
                     " a rabbit command");

             JumpInClient.RabbitCommand rabbitCommand =
                     (JumpInClient.RabbitCommand) command;

             assertEquals(rabbitCommand.coordinate, new Coordinate(0,0),
                     "coordinate should be same as input");
             assertEquals(rabbitCommand.direction, Direction.UP, "direction should" +
                     "be same as input");

         } catch (Exception e) {
             fail("Exception was thrown");
         }
     }

    @Test
    /**
     *  Test rabbit jumping
     */
    void testRabbitJumpBadWhiteSpace() {
        JumpInClient client = new JumpInClient();
        String userInput = "Jump    Rabbit    1,1   Up";

        try {
            client.parseRabbitCommand(userInput);
        } catch (Exception e) {
            fail("Exception was thrown");
        }
    }

    @Test
    /**
     *  Test fox sliding
     */
    void testFoxSlide() {
        String userInput = "Slide Fox 1,1 2 Up";
        JumpInClient client = new JumpInClient();

        try {
            JumpInClient.Command command = client.parseFoxCommand(userInput);
            assertTrue(command instanceof JumpInClient.FoxCommand, "should be" +
                    " a fox command");

            JumpInClient.FoxCommand foxCommand = (JumpInClient.FoxCommand) command;

            assertEquals(foxCommand.coordinate, new Coordinate(0,0),
                    "coordinate should be same as input");
            assertEquals(foxCommand.direction, Direction.UP, "direction should" +
                    "be same as input");
            assertEquals(foxCommand.moveSpaces, 2, "move spaces should be " +
                    "same as input");
        } catch (Exception e) {
            fail("Exception was thrown");
        }
    }
}
