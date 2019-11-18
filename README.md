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

## Changes since Milestone 2
The main change since the previous milestone is that we had refactored our model to use immutable data structures. This was done because it allowed us to treat the data as values. As a result the logic for undo/redo and solving the game was made significantly simpler.

The other change, is to add the solver and undo/redo. The solver is a recursive DAG generator that walks the possible moves from different board states until a solution is found, or gives up by reaching a certain depth. Redo and undo is simply a double stack that stores references to previous boards, which are really just value references due to the persistent immutable data structure changes mentioned earlier.

The solver can be evaluated using the solver test