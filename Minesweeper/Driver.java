
/**
* Ivan Shadis
*
* Driver is the driver for the Minesweeper game.
* It contains the main method that serves as the entry point of the program
* allowing the user to start a new game, select a difficulty, or exit the program
*/
import java.util.*; //import the Scanner class from the java.util package

public class Driver
{
    /**
     * The main method of the program. It creates a Scanner object to read input from the user,
     * displays a menu with two options (New Game and Quit), and starts a game of Minesweeper
     * if the user selects New Game.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args)
    {
        int selection; //variable to store the user's menu selection
        Scanner keyboard = new Scanner(System.in); //create a Scanner object to read input from the user

        int menuReturn = 1; //variable to keep track of whether to display the menu again
        while(menuReturn == 1) //display the menu until the user exits the program
        {
            //display the menu and prompt the user to enter their selection
            selection = 0;
            System.out.println("MINESWEEPER\n1.New Game\n2.Quit");

            //validate the user's input to ensure it is either 1 or 2
            while(!(validateInput(2, selection)))
            {
                try
                {
                    selection = Integer.parseInt(keyboard.next()); //attempt to parse the user's input as an integer
                }
                catch(NumberFormatException e) //if the user's input is not a valid integer, set the selection to 0
                {
                    selection = 0;
                }

                //if the user's input is invalid, display an error message and prompt them to enter a valid selection
                if(!validateInput(2, selection))
                {
                    System.out.println("Please enter 1 or 2");
                }
            }

            //if the user selected "New Game", display the difficulty levels and start a game with the selected difficulty
            if(selection == 1)
            {
                //display the difficulty levels and prompt the user to enter their selection
                System.out.println("Please select difficulty level:\n"+
                                   "1. Beginner - 8x8 grid with 8 mines\n"+
                                   "2. Intermediate - 10x12 grid with 10 mines\n"+
                                   "3. Expert - 16x20 grid with 50 mines\n"+
                                   "4. Exit");
                int difficulty = 0;

                //validate the user's input to ensure it is a number between 1 and 4
                while(!(validateInput(4, difficulty)))
                {
                    try
                    {
                        difficulty = Integer.parseInt(keyboard.next()); //attempt to parse the user's input as an integer
                    }
                    catch(NumberFormatException e) //if the user's input is not a valid integer, set the difficulty to 0
                    {
                        difficulty = 0;
                    }

                    //if the user's input is invalid, display an error message and prompt them to enter a valid difficulty level
                    if(!validateInput(4, difficulty))
                   {
                       System.out.println("Please enter a valid difficulty level (1-3) or 4 to exit.");
                   }
               }
               
               // if the user selected "Exit" display exit message and exit system
               if(difficulty == 4)
               {
                   System.out.println("Exiting MineSweeper");
                   System.exit(1);
               }
               
               // Otherwise start a new game of the chosen diifficulty.
               else
               {
                   MineSweeper game = new MineSweeper(difficulty);
                   game.play();
               }
           }
           if(selection == 2)
           {
              System.out.println("Exiting MineSweeper");
              System.exit(1);
           }
  
       }
    }
    /*
    * validateInput
    *
    * validates that an integer is  within a range\
    * @param int input - the integer to check
    * @param int range - the range to check it against
    * @return boolean - whether the integer is or is not in the range
    */
    public static boolean validateInput(int range, int input)
    {
        return input >= 1 && input <= range;
    }
}