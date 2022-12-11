/*
* Ivan Shadis
* 
* The Grid class takes a width, height, and number of mines 
* to construct a MineSweeper game board populated with NumberSquares
* and MineSwuares. It provides methods for analyzing and updating the board.
*/

// Imports
import java.util.*;
public class Grid
{
   // Set class variables
   private Square [][] grid; // To hold game grid
   private int width;        // To hold number of columns
   private int height;       // To hold number of rows
   private int numMines;     // To hold number of mines
   private int numSquaresUncovered = 0;
   
   // Set class constants
   public final int OK = 0;   // Continue condition
   public final int WIN = 1;  // Win condition
   public final int MINE = -1;// Lose condition
   
   /*
   *Grid constructor
   *
   *@param int w - the number of columns in the grid
   *@param int h - the number of rows in the grid
   *@param int m - the number of mines to place on the grid
   */
   public Grid(int w, int h, int m)
   {
      this.width = w;
      this.height = h;
      this.numMines = m;
      
      // Create grid of given height and width
      this.grid = new Square[height][width];
      Random rand = new Random();       
      
      // Randomly populate grid with appropriate number of mines
      int mcount = 0;
      // While the number of mines is less than that expected
      while(mcount < numMines)
      {  
         // Select a random grid coordinate
         int r = rand.nextInt(0, height);
         int c = rand.nextInt(0, width);
         Square sq = grid[r][c];
         // If that grid coordinate is empty
         if(grid[r][c] == null)
         {
            // populate it with a new MineSquare
            grid[r][c] = new MineSquare();
            mcount ++;
         }
      }
     
      // Populate the remaining grid coordinates with NumberSquares
      int nscount = 0;
      // While empy grid coordinates remain
      while(nscount < (width*height-numMines))
      {
         // Iterate over each grid coordinate
         for(int r = 0; r < height; r++)
            for(int c = 0; c < width; c++)
            {
              // If it is empty
              if(grid[r][c] == null)
              {
               // Check for adjacent mines
               int nm = getNeighbors(r,c);
               // And populate the coordinate with a new NumberSquare
               grid[r][c] = new NumberSquare(nm,r,c);
               nscount ++;
              }
            }
       }
    }

   /*
   *getNeighbors
   *
   *Given row and column coordinates, returns the number of
   *adjacent Squares that contain mines.
   *@param int r - The row coordinate to check
   *@param int c - The column coordinate to check
   *@return int - nm The number of adjacent mines
   */
   public int getNeighbors(int r, int c)
   {
      int nm = 0; // Initialize mine count to 0
      // Iterate over the squares adjacent to that given by the passed coords
      for(int mr = 0; mr < height; mr++)
         for(int mc = 0; mc < width; mc++)
         {
            if(mr >= r-1 && mr <= r+1 && mc >= c-1 && mc <= c+1)
            {
               if(grid[mr][mc] != null)
               {
                  Square sq = grid[mr][mc];
                  // If there is a mine
                  if(sq.isMine() == true)
                     {
                        // Iterate the mine count by one
                        nm ++;
                     }
               }
            }
         }
         // Return the total number of mines counted among adjacent squares
         return nm;       
   }
   
   /*
   * uncoverSquare
   *
   * Uncovers a square given row and column coordinates
   * @param int r - The row coordinate
   * @param int c - The column coordinate
   * @throws UncoverFlagException - if the square is flagged
   * @return int OK - continue condition
   * @return int WIN - win condition
   * @return int MINE - lose contion
   */
   public int uncoverSquare(int r, int c) throws UncoverFlagException   {
      Square sq = grid[r][c]; // to hold the square under consideration
      int column_span = 0;    // the span of columns determining the area to be uncovered
      int row_span = 0;       // that span of rows
      int numUncovered = 0;   // the number of squares that have been uncovered
      
      // If the square is flagged, throw Exception
      if(sq.isFlagged())
      {
        throw new UncoverFlagException("Invalid Move: Cannot uncover flagged square");
      }
      
      // During the first round - ensure that the first selected square is not a mine
      while(numSquaresUncovered == 0)
      {
         Square replaceSq; // To hold square mine will be moved to
         
         // If the square under consideration is a mine
         if(sq.isMine())
         {
            //Place a new mine on an unmined square
            int replaceMine = 0;
            // Search for a random unmined grid square
            while(replaceMine < 1)
            {
               Random rand = new Random();
               int repRow = rand.nextInt(0, height);
               int repCol = rand.nextInt(0, width);
               replaceSq = grid[repRow][repCol];
               // When one is found
               if(!(replaceSq.isMine()))
               {
                  // Replace the square with a mine
                  grid[repRow][repCol] = new MineSquare();
                  System.out.println("The new mine is at: "+repRow+repCol);
                  // Update the neighborMines of NumberSquares adjacent to the new square
                  for(int nr = 0; nr < height; nr++)
                  {
                     for(int nc = 0; nc < width; nc++)
                     {
                        if(nr >= repRow-1 && nr <= repRow+1 && nc >= repCol-1 && nc <= repCol+1)
                        {
                           Square updateSq = grid[nr][nc];
                           if(!(updateSq.isMine()))
                           {
                              int nuMin = getNeighbors(nr, nc);
                              grid[nr][nc] = new NumberSquare(nr, nc, nuMin);
                           }
                        }
                     }
                  }
                  replaceMine ++;
               }
             }

            // Count the number of mines now adjacent to the original square
            int nm = getNeighbors(r, c);
            // Replace that original mined square with a NumberSquare
            grid[2][2] = new NumberSquare(nm, r, c);
            // Update the sq variable
            sq = grid[r][c];
            
            // Update the neighborMines of  NumberSquares adjacent to the original square
            for(int nr = 0; nr < height; nr++)
               for(int nc = 0; nc < width; nc++)
               {
                  if(nr >= r-1 && nr <= r+1 && nc >= r-1 && nc <= r+1)
                  {
                     Square updateSq = grid[nr][nc];
                     if(!(updateSq.isMine()))
                     {
                        int nuMin = getNeighbors(nr, nc);
                        grid[nr][nc] = new NumberSquare(nuMin, nr, nc);
                     }
                   }       

                 }
           }
           // Uncover the square
           sq.uncover();
           // Iterate the respective count by 1
           numSquaresUncovered ++;    
        }
      // For all rounds after the first:
      // If square is a mine, return MINE
      if(sq.isMine())
      {
         sq.uncover();
         return MINE;
      }
      // else if num neighbors is 0
      else if(!(sq.isFlagged()))
      {
         System.out.println("Line 185");
         if(getNeighbors(r,c) == 0)
         {
            column_span = 2;
            row_span = 2;
         }
         else if(getNeighbors(r,c) == 1)
         {
            column_span = 1;
            row_span = 1;
         }
         
         // Expose neighborhod.Don't expose mines. Don't go off end of grid.
         for(int nr = 0; nr < height; nr++)
            for(int nc = 0; nc < width; nc++)
            {
               if(nr >= r-row_span && nr <= r+row_span && nc >= c-column_span && nc <= c+column_span)
               {
                  Square nsq = grid[nr][nc];
                  if(!(nsq.isMine()) && !(nsq.isUncovered()))
                  {  
                     //System.out.println("Square: " + nr + nc + " uncovered with " + getNeighbors(nr, nc) + "adjacent mines");
                     nsq.uncover();
                     numUncovered ++;
                     System.out.println(numUncovered);
                  }
               }
            }
         // Uncover the square
         sq.uncover();
         //update numSquaresUncovered
         numSquaresUncovered += numUncovered;

         //Check if user won, if yes -> return WIN
         if(numSquaresUncovered >= (width*height-numMines))
            return WIN;
         // else return OK
         else
         {
            return OK; 
         }  
      }
      return OK; 
     }
   
   /*
   * exposeMines()
   * 
   * Uncovers all mines
   */
   public void exposeMines()
   {
      // Iterate over game board
      for(int r = 0; r < height; r++)
               for(int c = 0; c < width; c++)
               {
                  // And uncover any square that is a mine
                  Square sqr = grid[r][c];
                  if(sqr.isMine())
                     sqr.uncover();
               }
   }
   
   /*
   * flagSquare
   *
   * Flags or unflags a given square
   * @param int r - The row coordinate
   * @param int c - The column coordinate
   */
   public void flagSquare (int r, int c)
   {
      Square sqr = grid[r][c];
      sqr.flagSquare();
   }
   
   /*
   * toString
   * Prints the updated gameboard
   * @return String str - the updated gameboard
   */
   public String toString()
   {
      String str = " ";
      for(int i=0; i < width; i++)
      {
         if(i < 10)
          str += "  "+i;
         else
           str += " "+i;
      }
      str += "\n";
      for(int r = 0; r < height; r++)
      {  
         if(r < 10)
            str += r + "  ";
         else
            str += r + " ";
         for(int c = 0; c < width; c++)
         {
            Square sq = grid[r][c];
            str += sq.toString() + "  ";
               
         }
         
         str += "\n";
      }
      return str;
  
   }
   
}