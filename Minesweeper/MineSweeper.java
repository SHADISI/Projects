/*
* Ivan Shadis
* MineSweeper contains the game logic, constructs the gameboard,
* takes user input, makes moves, and checks the win condition.
*/

// Imports
import java.util.*;
public class MineSweeper
{
   private Grid grid; // The game grid
   private int rows;  // The number of rows
   private int columns; // The number of columns
   private int mines; // The number of mines
   private int difficulty; // The difficulty level, 1-3
   
   /*
   *Constructor
   *Given a difficulty level, this constructor
   *assigns values from the accordant preset 
   *and constructs the game grid.
   *@param int d - the difficulty level, 1-3
   */
   public MineSweeper(int d)
   {
      this.difficulty = d;
      switch(d)
      {
         //If difficulty is 1, construct 8 x 8 board with 8 mines
         case 1:
            this.grid = new Grid(8, 8, 8);
            this.rows = 8;
            this.columns = 8;
            this.mines = 8;
            break;
         //If difficulty is 2, construct 12 x 10 board with 10 mines
         case 2: 
            this.grid = new Grid(12, 10, 10);
            this.rows = 10;
            this.columns = 12;
            this.mines = 10;
            break;
         //If difficulty is 3, construct 20 x 16 board with 50 mines
         case 3:
            this.grid = new Grid(20, 16, 50);
            this.rows = 16;
            this.columns = 20;
            this.mines = 50;
      }
   }

   /**
    * play plays a game of minesweeper. 
    * The game continues until the player wins,
    * loses, or chooses to stop playing.
    */
   public void play()
   {
    
      char replay = 'Y'; // Store the player's decision to replay
      
      // Loop until the player decides not to replay the game
      while(replay == 'Y')
      {
         
         String inputs[]; // Array to store the player's input
         
         // Variable to store the result of the player's move
         int result = 0; //Variable to control game looop
         
         // Print the game board
         System.out.print(grid);
         
         // Loop until the player wins or loses the game
         while(result == 0)
            {
               // Prompt the player for their next move
               System.out.println("What's Next?\n" +
                "Options: (U)ncover r c, (F)lag r c, (Q)uit");
                
               // Get the player's input
               inputs = getInput();
               
               // If the player chose to quit the game, determine whether to replay
               if(inputs[0] == "q")
               {
                 replay = exitMenu();
                 
                 // If the player chose not to replay, set the game result to 2 to
                 // end the current game
                 if(replay == 'N')
                    result = 2;
               }
               else
               {
                 // Otherwise, make the player's move and update the game result
                 result = makeMove(inputs, grid);
               }
            }
            
            // If the player loses the game, print a message and the mine locations,
            // then determine whether to replay the game
            if(result == -1)
            {
                System.out.println("YOU LOSE");
                grid.exposeMines();
                System.out.println(grid);
                replay = exitMenu();
            }
            // If the player wins the game, print a message and determine whether
            // to replay the game
            else if(result == 1)
            {
               System.out.println("YOU WIN");
               replay = exitMenu();
            }
      }    
   }
   
   /**
   * getInput prompts the player for their next move 
   * and returns the player's input as an array of strings.
   *
   * @return An array of strings containing the player's input
   */            
   public String[] getInput()
   {
      
      int row = 0;   // the row
      int column = 0; // the column
      
      String input; // to hold the initial input
      String inputs [] = new String[3]; // to hold the parsed input
      String option; // to hold the input corresponding to the option U, F, or Q
      Scanner keyboard = new Scanner(System.in); // Scanner to take input
      
      boolean valid_input = false;  // initialize valid input to false
                    
      //Validate input
      while(!(valid_input))
      {  
         try
         {
          // Read the player's input
          input = keyboard.nextLine();
          
          // Split the input into an array of strings
          inputs = input.split(" ");
          
          // Get the player's option (U, F, or Q)
          option = inputs[0];
          
          // If the player chose to quit the game, ask for confirmation
          if(option.charAt(0) == 'q' || option.charAt(0) == 'Q')
           {   
               // String to store the player's response to the confirmation prompt
               String doublecheck = "x";
                
               // Ask the player to confirm their decision to quit the game
               System.out.println("Are you sure you want to end this game? ");
               
               // Read the player's response
               doublecheck = keyboard.nextLine();
               
               // Convert the response to upper case
               doublecheck = doublecheck.toUpperCase();
               
               // Loop until the player enters a valid response
               while(doublecheck.charAt(0) != 'Y' && doublecheck.charAt(0) != 'N')
               {
                  // If the response is invalid, display an error message and prompt
                  // the player to enter a new response
                  System.out.println("Enter yes or no");
                  doublecheck = keyboard.nextLine();
                  doublecheck = doublecheck.toUpperCase();
               }
               
               // If the player chose to quit the game, update the input array
               // and return it
               if(doublecheck.charAt(0) == 'Y')
               {
                  inputs[0] = "q";
                  return inputs;
               }
               else
               {
                  // Otherwise, the input is still invalid, so set the flag to
                  // false and display a prompt for the player to enter a new move
                  valid_input = false;
                  System.out.println("What's Next?\n" +
                  "Options: (U)ncover r c, (F)lag r c, (Q)uit");
               }
                  
            }
          // If the input array has fewer than 3 elements, 
          // display an error message
          else if(inputs.length < 3)               
            System.out.println("Invalid Entry Format. Enter option letter, row, and column each seperated by a space, e.g F 0 0, or Q to quit.");
          else
          {             
            // Parse the row and column coordinates from the input array
            row = Integer.parseInt(inputs[1]);
            column = Integer.parseInt(inputs[2]);        
         
            // If the player's option is not U, F, or Q, display an error message
            if(option.charAt(0) != 'u' && input.charAt(0) != 'U' && input.charAt(0) != 'f' && input.charAt(0) != 'F')
            {
              System.out.println("Invalid option input. Must be U, or F followed by row and column coordinates, or Q to quit.");
            }
            // If the row coordinate is out of bounds, display an error message
            else if(row > rows-1 || row < 0)
            {
              System.out.println("Row " + row + " out of bounds.");
            }
            // If the column coordinate is out of bounds, display an error message
            else if(column > columns-1 || column < 0)
            {
              System.out.println("Column " + column + " out of bounds.");
            }
            else
            {
              // If the input is valid, set the valid_input to true and exit the loop
              valid_input = true;
            }
          }
         // Catch any exceptions thrown by parsing the row and column coordinates
         // as integers, and display an error message
        }catch(NumberFormatException e)
         { System.out.println("Row and Col must be integers.");
           valid_input = false;}
             
       } 
     // return inputs
     return inputs;
   }
   /**
    * makeMove processes the player's move.
    *
    * @param inputs - An array of strings containing the player's input
    * @param grid   - the game grid    
    * @return int - indicating the result of the move 
    */
   public int makeMove(String[] inputs, Grid grid)
   {
      // String to store the player's option (U, F, or Q)
      String option = inputs[0];
      
      // Variables to store the row and column coordinates of the player's move
      int row = Integer.parseInt(inputs[1]);
      int column = Integer.parseInt(inputs[2]);
      
      // If the player's option is to uncover a square, try to uncover the square
      // on the game grid
      if(option.charAt(0) == 'u' || option.charAt(0) == 'U')
      {
         try
         {
            // Call the uncoverSquare() method on the game grid object and store
            // the result.
            int result = grid.uncoverSquare(row, column);
            
            // Print the updated game grid
            System.out.print(grid);
            
            // Return the result of the move
            return result;
         }
         catch(UncoverFlagException e)
         {
            // If the uncoverSquare() method throws an exception, print an error message
            System.out.println("Invalid Move: Cannot uncover flagged square");
         }
      }
      
      // If the player's option is to flag a square, call the flagSquare()
      // method on the game grid object
      if(option.charAt(0) == 'f' || option.charAt(0) == 'F')
      {
         grid.flagSquare(row, column);
         
         // Print the updated game grid
         System.out.print(grid);
      }
      
      // Return 0 to indicate that the move was successful
      return 0;
   }
   
      
   /**    
   * exitMune prompts the player to decide whether they want to replay the game
   * at the same difficulty level or quit the game.
   *
   * @return A char indicating the player's response 
   * ('Y' if the player wants to replay the game, or 'N' if the player wants to quit)
   */
   public char exitMenu()
   {  
      // Scanner to read the player's input from the keyboard
      Scanner keyboard = new Scanner(System.in);
      
      // Prompt the player to decide whether they want to replay the game
      System.out.println("Would you like to restart at the same difficulty?");
      
      // Read the player's response
      String replay = keyboard.nextLine();
      
      // Convert the response to upper case
      replay = replay.toUpperCase();
      
      // Loop until the player enters a valid response
      while(replay.charAt(0) != 'Y' & replay.charAt(0) != 'N')
      {
         // If the response is invalid, display an error message and prompt
         // the player to enter a new response
         System.out.println("Would you like to play again? Enter yes or no.");
         replay = keyboard.nextLine();
         replay = replay.toUpperCase();
      }
      
      // If the player chose not to replay the game, return 'N'
      if(replay.charAt(0) == 'N')
      {
         System.out.println("");
         return 'N';
      }
      
      // Otherwise, the player chose to replay the game, so create a new
      // Grid object with the same dimensions as the previous game and
      // return 'Y'
      grid = new Grid(columns, rows, mines);
      return 'Y';
   }
}

  

     

 