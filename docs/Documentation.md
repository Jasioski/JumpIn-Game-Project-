# Documentation

![Game screenshot](./game.png "Game screenshot")

## Usage

Ensure JDK 11 is installed.

### Linux / macOS
The game can be launched on Linux or macOS by running
```sh
java -jar project.main.jar
```

### Windows
Windows Powershell and Command Prompt require a command to be run to enable ANSI Sequences
```
reg add HKCU\Console /v VirtualTerminalLevel /t REG_DWORD /d 1
java -jar project.main.jar
```

Alternatively you may use the ConEmu console bundled with this deliverable.
```sh
java -jar project.main.jar
```
## Game Rules
- Foxes can only slide.
    -> Fox sliding is only legal if:
      -> From the the fox current location to the destination locaiton, there are no obsticles.
      -> Items that qualify as obstacles for foxes include rabbits, other foxes, elevate blocks/ttiles on the the board, mushrooms.    
- Rabbits can only jump.
    -> Rabbit jumping is only legal if: 
      -> There is an obsticle in-between the rabbit current location to the destinaiton location.
      -> Items that qualify as obstacles for rabbits include other rabbits, foxes, and mushrooms.
      -> Rabbits can jump over multiple obstacles as well. As long as there is an obstacles (as defined in the line above) from the 
         rabbit current location to the desination location and non of the blocks/tiles in-between are empty, the move is legal.      
- Mushrooms are non-moveable items and can not be moved from their position on the board.
- To win the game, user must ensure that all the rabbits that are present on the board have jumped in the holes.
  -> If the game is won, a pop-up window will be displayed; confirming the win of the user.
  -> If the game is not won yet or still in progress, no such message will be displayed.

## Game Instructions
- To play the game; please follow the instructions as follows:
- Left click on the item you want to move.
- Left click on the tile/block/box on the boad where you want to place the item.
- If  move is legal and follow the rules, the selected item will be moved to the destination box.
- If move is illegal, a pop-up window will be displayed; displaying the reason why the move was unsuccessful.

## JavaDocs
Our source code has been commented using the JavaDocs convention.

Feel free to open docs/javadoc/index.html to view the generated documentation inside a browser.

## Git
Throughout our development process, we used git for version control, creating branches with our individual work and pushing completed changes to the master branch.

By using git's pull requests to check all of our work before each merge, we ensured that the proper changes were being added without any merge conflicts.

We used short-lived branches for feature development and bug fixes. We adopted a trunk based branch style where we were always focusing on making small, testable changes which could be quickly merged into master.

## Test Driven Development

We used a test driven development methodology to drive the way we were coding our game. Before implementing each class, we created tests that could be run to ensure that each class was performing its proper functions. For example, a Rabbit test which tested each possible jump it could perform and ensured that expected output

Was recieved. This made it easy to develop the class while using the tests as a reference. With each change we made to the class, we could re-run the tests to ensure that it was still implemented properly. After passing all initial tests, we would try to break the implementation by adding more tests and edge

cases for the class to pass. We would repeat this process until we were satisfied with the capabilities of the class.

## Continuous Integration
Early on, we setup a CI process using Gradle and GitHub Actions. Our project is managed using the gradle build system which allows automated testing, building and analysis. We are using PMD for static code analysis as well as test coverage tools to monitor our tests.



## Contributions

- Source Code Development: Rafi, Kamran, John, Anton, Christopher
- Documentation: Anton, Kamran, Christopher, John
- Project Management / CI: Rafi, John

