This is a small personal project about a Tic Tac Toe Game. I have a chance to study while making a real hands-on experience, applying what i have learnt about OOP Programming
with Java and SWE. 

**Here is the required from my Professional:**
Write a Java program to play Tic-Tac-Toe against the computer. Make sure you follow the principles of the Object-Oriented Paradigm (OOP).

The game master (a human) decides which player will start by passing either 1 (he/she starts) or 2 (the computer starts) as an argument when executing the Java program.

The board will be displayed as a table, consisting of 9 cells. Each cell is identified with a  number. The cells are numbered from top to bottom, and from left to right.

| 1 | 2 | 3 |

| 4 | 5 | 6 |

| 7 | 8 | 9 |

 At the beginning of the game and after each of the players’ moves, the (current) board will be displayed in the terminal. At the beginning, all of the cells will display  0. If the (human) user occupies a cell,the cell will display 1. If the computer occupies a cell, the cell will display 2.

The (human) user inputs its moves by typing the number of the chosen cell [1-9] followed by ENTER (end-of-line).

The computer follows a basic  strategy: it will choose the first available cell, from 1 to 9.


Time Stone:


25.03.2026: Changing the Player to an abstract class, the same with Board class
Player -> Human.java, Computer.java
Board -> Board_1D.java, Board_2D.java
=> Successfully make it abstract + encapsulation, since i do not have to make any changes in the App.java (which is my **main class**) => Understand about the Abstraction + Encapsulation
