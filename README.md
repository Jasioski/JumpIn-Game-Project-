# SYSC3110 Group project

![Game Screenshot](./docs/game.png "Game Screenshot")

## Contributors
- Rafi Khan: rafikhan3@cmail.carleton.ca
- John Grabkowski: johngrabkowski@cmail.carleton.ca
- Kamran Sagheir: kamransagheir@cmail.carleton.ca
- Anton Aroche: antonaroche@cmail.carleton.ca
- Christopher D'silva: chrisdsilva@cmail.carleton.ca

## Resources
- PMD Ruleset: Using bestpractices from [PMD](https://github.com/pmd/pmd/blob/master/pmd-java/src/main/resources/category/java/bestpractices.xml)


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

## Console Based Game Instructions 
- To play the game in the console, the syntax of the commands to be typed in are as follows:
- For Rabbit:
-- "Command Item Item_Current_Location_Coordinates Direction"
-- Example:
-- Jump Rabbit 1,1 Right 
- For Fox: 
-- "Command Item Item_Current_Location_Coordinates Boxes_On_The_Board_To_Be_Displaced Direction"
-- Example:
-- Slide Fox 1,2 2 Left
- Note: Board coordinates are:
--  1,1 at the top left
--  5,1 at the bottom left
--  1,5 at the top right
--  5,5 at the bottom right

## Gui Based Game Instructions
- Todo
