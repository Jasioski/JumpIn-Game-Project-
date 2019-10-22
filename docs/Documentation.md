# Documentation

## Usage

### Linux / macOS
The game can be launched on Linux or macOS by running
```sh
java -jar project.main.jar
```

### Windows
Windows Powershell and Command Prompt require a command to be run to enable ANSI Sequences
```
reg add HKCU\Console /v VirtualTerminalLevel /t REG_DWORD /d 1
```

Alternatively you may use the ConEmu console bundled with this deliverable.

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

## Class Descriptions

**Board**

The main object that holds and controls the play area for the game. The items on the board are held in a 2d array, which allows for easy access of the elements in any row and column of a board. These items are all of type BoardItem, an abstract class which is extended to provide specific implementations to each possible piece on the board.

The board can get and set items into its spaces, return its string representation, and check and describe if the game has been won with the currentGameState Boolean.

It is also responsible for sending requests to objects to attempt to slide or jump. To slide or jump, the object and direction (denoted by the Direction enum) are sent to the board object. The actual logic of the slide and jump functions are then delegated to the Slideable and Jumpable objects themselves.

The board handles the actual requests by finding the item requested and sending the slice of the board in the direction that it needs to move in.That way, these requests handled by the board which knows where these items are, but the logic can be left to the objects themselves in the way that they move.

**Direction**

Enum containing each direction an object could move or slide in (up, down, left, right, nonspecified)

**BoardItem**

Abstract class that shares all common attributes between objects that can be on the board. Most importantly, their coordinates on the board as well as their representation in the UI, which is usually a character or two defining the object (F for fox, R for rabbit etc)

The BoardItem class uses a list of Coordinate objects to determine where the item is located on the board. This is important because some longer objects such as the fox can have more than one coordinate on the board (one for the head and one for the tail).

The BoardItem&#39;s UI representation is denoted by the ItemUIRepresentation class, an enum which contains all the representations for possible board objects.

**Coordinate**

A class used to describe where objects are on the board. Consists of an integer row and column value corresponding to the array location in the Board class.

**ItemUIRepresentation**

Enumerator containing each one or two character representation for the item in the UI. Used by the board class in its string representation of the board.

**Jumpable**

Interface defining objects that can jump over other BoardItems. Implemented for decoupling. In this case, Rabbit is a jumpable object, and must implement the jump function to handle jumping logic.

**Containable**

Interface defining objects that can be contained by a ContainerItem. Used by objects such as the Rabbit and mushroom, which can be contained inside a hole or elevated container.

**Rabbit**

Describes the rabbit piece on the board and implements its logic to jump. Extends the BoardItem class and implements Jumpable and Containable. Jump uses the Boolean isCurrentlyJumping and a recursive function to continuously try to move one further space in the given slice, only jumping if at least one obstacle is encountered. This uses the slice given by the Board&#39;s jump request to determine if a valid jump is possible.

**Slideable**

Interface defining objects that can slide across the board. Implemented for decoupling. In this case, the fox can slide across the board, and must implement the slide function to handle sliding logic.

**Fox**

Describes the fox piece on the board and implements sliding logic. Extends the BoardItem class and implements Slidable. The Slide function uses the direction slice passed by Board during a slide request to determine whether the Fox can do a valid slide in that direction.

**Mushroom**

Describes the mushroom piece on the board. A simple extension of BoardItem that also implements containable, since it can be contained in a hole or elevated item.

**ContainerItem**

Abstract class extending boardItem, which represents items that can contain other items. This is implemented for the Hole object and ElevatedBoardItem tile. Has an optional containingItem which can hold a Containable object. Can return this item, removing it from itself, as well as set a new item.

**Hole**

Extends ContainerItem and represents holes on the board which the rabbit must jump into. Overrides ContainerItem&#39;s methods to also change its UI representation depending on if it is filled or empty.

**ElevatedBoardItem**

Extends ContainerItem and represents the tiles on the board that are elevated. Like Hole, can contain a Containable Rabbit or Mushroom object, but is not the win condition like hole is.

**EmptyBoardItem**

Extends BoardItem and represents an empty tile on the board. Does not have any special properties, but is used by the Board to determine if an item can be placed into that location, and by Rabbit and Fox for their sliding logic.


## Contributions

Source code development: Rafi, Kamran, John, Anton, Christopher

Documentation: Anton, Christopher, John

