# Rock-Paper-Scissors

## About
This is a Rock-Paper-Scissors-Lizard-Spock game, a more advanced version of Rock-Paper-Scissors.

## Learning outcomes
- A playable Rock-Paper-Scissors game, with a Player vs. Computer mode. 
- Practice using 
  - arrays, 
  - the Random class, 
  - formatted strings,
  - reading and writing files,
  - and algorithms.

## Objectives
Before the game starts, users can choose the options. 
After entering the name, they should provide a list of the options separated by a comma. 
For example: rock,paper,scissors,lizard,spock
If users input an empty line, start the game with default options: rock, paper, and scissors

## Algorithm
How to determine which options defeat which options?
The order in which the options are entered by the user is important.
An option defeats the half of the options coming before itself.
For example, the user's list of options is rock,paper,scissors,lizard,spock. 
You want to know what options are weaker than lizard. Lizard defeats 
the half of the options coming before it. They are paper and scissors.
Lizard is defeated by the other half of the options coming after it.
They are spock and rock.
If there are an even number of choices, the current implementation will favor the computer
because the stronger half will be one more than the weaker half. 

### Storing scores
- Reads old ratings from rating.txt
- At the exit of the program
  - if the current player already exists updates their rating
  - otherwise appends the rating to the ratings in the file