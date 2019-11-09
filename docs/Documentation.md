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

## Project Descriptions
The project has been divided into five packages:
- project:
  This package contains two classes: 
  
    **Application**
    This is the mian class that runs the Graphical User Interface (GUI) of the the game. This classs makes call to two classes; ImageResources.java and BoardGui.java.The ImageResources.java class contains the images that are to be displaces on the board for items. a call to this class loads those images. The BoardGui.java class is passed with DefaultBoard() as a parameter that contains the instrucitons to set up the game default board with default items at default positoins.
   
   **DefaultBoard** 
    This class contians the default settings for the game including the default number of rabbits, foxes, holes, elevated tiles/blocks on the board and mushrooms.
  
- project.model
  This package contains fifteen classes in total:
  
    **Board**
    The main object that holds and controls the play area for the game. The items on the board are held in a 2d array, which allows for easy access of the elements in any row and column of a board. These items are all of type BoardItem, an abstract class which is extended to provide specific implementations to each possible piece on the board.

  The board can get and set items into its spaces, return its string representation, and check and describe if the game has been won with the currentGameState Boolean.

  It is also responsible for sending requests to objects to attempt to slide or jump. To slide or jump, the object and direction (denoted by the Direction enum) are sent to the board object. The actual logic of the slide and jump functions are then delegated to the Slideable and Jumpable objects themselves.

  The board handles the actual requests by finding the item requested and sending the slice of the board in the direction that it needs to move in.That way, these requests handled by the board which knows where these items are, but the logic can be left to the objects themselves in the way that they move.

   **BoardItem**
  Abstract class that shares all common attributes between objects that can be on the board. Most importantly, their coordinates on the board as well as their representation in the UI, which is usually a character or two defining the object (F for fox, R for rabbit etc)

   The BoardItem class uses a list of Coordinate objects to determine where the item is located on the board. This is important because some longer objects such as the fox can have more than one coordinate on the board (one for the head and one for the tail).

   The BoardItem&#39;s UI representation is denoted by the ItemUIRepresentation class, an enum which contains all the representations for possible board objects.
   
   **ContainerItem**
  Abstract class extending boardItem, which represents items that can contain other items. This is implemented for the Hole object and ElevatedBoardItem tile. Has an optional containingItem which can hold a Containable object. Can return this item, removing it from itself, as well as set a new item.

  **Coordinate**
  A class used to describe where objects are on the board. Consists of an integer row and column value corresponding to the array location in the Board class.
  
  **Direction**
  Enum containing each direction an object could move or slide in (up, down, left, right, nonspecified)
  
  **EmptyBoardItem**
  Extends BoardItem and represents an empty tile on the board. Does not have any special properties, but is used by the Board to determine if an item can be placed into that location, and by Rabbit and Fox for their sliding logic.
  
  **ElevatedBoardItem**
  Extends ContainerItem and represents the tiles on the board that are elevated. Like Hole, can contain a Containable Rabbit or Mushroom object, but is not the win condition like hole is.
  
  **Fox**
  Describes the fox piece on the board and implements sliding logic. Extends the BoardItem class and implements Slidable. The Slide function uses the direction slice passed by Board during a slide request to determine whether the Fox can do a valid slide in that direction.

  **GameState**
  Contains the gmae state enums that tells whether the game is in porgress or has been solved.
  
  **Hole**
  Extends ContainerItem and represents holes on the board which the rabbit must jump into. Overrides ContainerItem&#39;s methods to also change its UI representation depending on if it is filled or empty.
  
  **Jumpable**
  Interface defining objects that can jump over other BoardItems. Implemented for decoupling. In this case, Rabbit is a jumpable object, and must implement the jump function to handle jumping logic.
  
  **Mushroom**
  Describes the mushroom piece on the board. A simple extension of BoardItem that also implements containable, since it can be contained in a hole or elevated item.
  
  **Rabbit**
  Describes the rabbit piece on the board and implements its logic to jump. Extends the BoardItem class and implements Jumpable and Containable. Jump uses the Boolean isCurrentlyJumping and a recursive function to continuously try to move one further space in the given slice, only jumping if at least one obstacle is encountered. This uses the slice given by the Board&#39;s jump request to determine if a valid jump is possible.
  
  **Slideable**
  Interface defining objects that can slide across the board. Implemented for decoupling. In this case, the fox can slide across the board, and must implement the slide function to handle sliding logic.

- project.model.exceptions
  This package contains all the exceptions that are thrown when any of the game rules are voilated. This package contains twelve classes in total. 
  
  **BoardItemNotEmptyException**
    An exception that is thrown if an item is being placed on a tile/block that is not empty.
   
  **HoleAlreadyHasRabbitException**
    An exception that is thrown if the object of type, ContainerItem already contians an object of Containable type.
  
  **HoleIsEmptyException**
    An exception that is thrown if the objet of ContainerItem type is already empty and attempted to be removed from.

  **JumpFailedNoObstacleException**
    An exception that is thrown if there is no object for an object of jumpable type to jump over.

  **JumpFailedOutOfBoundsException**
    An exception that is thrown if an  object of jumpable type would land out of bounds.

  **NonJumpableException**
    An exception that is thrown if an object, that not of type jumpable is attempted to make a jump.
   
  **NonMovableItemException**
    An exception that is thrown if an object, that not of type moveable is attempted to make a movement.
    
  **NonSlideableException**
    An exception that is thrown if an object, that not of type slideable is attempted to make a slide movement.
    
  **SlideFailedException**
      An exception that is thrown if the slide fails.
  
  **SlideHitElevatedException**
    An exception that is thrown if the slideable item hits an elevated item.
    
  **SlideHitObstacleException**
     An exception that is thrown if the slideable item would collide with an obstacle.
  
  **SlideOutOfBoundsException**
     An exception that is thrown if the slideable object would be pushed out of bounds.

- project.tui
This package has four classes in total: 

  **ANSIColor**
    This class contians ANSI colors that were used for the first deliverable to display a coloured output for the termianl display.
  
   **ItemUIRepresentation**
      Enumerator containing each one or two character representation for the item in the UI. Used by the board class in its string representation of the board.
      
  **JumpInClient**
   This class does parses for the user provided input to convert it into form, compatable and understadeable by the game code in order to perform required actions.
  
  **Main**
    This class was developed for the first deliverable to run the terminal/ text-based version of the JumpIn' game.
    
      
- project.view
This package has sixteen classes in total:
  **BoardGui**
   This class initializes the GUI of the JumpIn' game. Default constructor takes a Board and passes it to the class GuiOuterFrame.
  
  **ButtonBoardItem**
    This class sets up different types of board boxes/tiles such as elevate items, holes, general box/tiles.
  
  **Circle**
    Sets up the items at the centre of each board box/tile such as the dark green centre or brown centre for the holes.
    
  **ContainerItem**
    This class integrates the GUI logic for the board pieces that can be placed on the board with the game model logic for board pieces that can be placed on the board.
    
  **Elevated**
    This class integrates the GUI logic for the elevated boxes/tiles with the game model logic for elevated boxes/tiles.
    
  **Fox**
   This class integrates the GUI logic for the fox with the game model logic for fox.
   
  **GUIBoardItem**
    This class sets up the item images that can be placed on the GUI board. 
    
  **GuiColor**
    This class defines colors that are being used to make the colors that are being used at different parts in the GUI development.
    
  **GuiInnerComponents**
    GuiInnerComponents initializes with the inner components of the GUI i.e., the board setup.
    
  **GuiOuterFrame**
    GuiOuterFrame deals with the initialization of the the GUI board outer components including the JToolBar i.e., menu bar. It makes call to the class GuiInnerComponents; initializes and renders the inner components of GUI.
    
  **Hole**
    This class integrates the GUI logic for the hole with the game model logic for hole.
    
  **ImageResources**
    This class stores the reference to all the images that are being used in the GUI.
    
  **ItemClickEvent**
    This classs registes the clicks on board and provides the respective coordinates.
    
  **ItemClickListener**
    Interface that conains method that looks for clicks on the GUI.
    
  **Mushroom**
    This class integrates the GUI logic for the mushroom with the game model logic for mushroom.
 
  **Rabbit**
    This class integrates the GUI logic for the rabbit with the game model logic for rabbit.





## Contributions

- Source Code Development: Rafi, Kamran, John, Anton, Christopher
- Documentation: Anton, Kamran, Christopher, John
- Project Management / CI: Rafi, John

