/**
* Ivan Shadis
*
* NumberSquare represents an unmined square in the game.
* It extends the Square class and overrides the uncover and isMine methods.
*/
public class NumberSquare extends Square
{
   //instance variables
   private int neighborMines; //the number of neighboring mines
   private int myRow; //the row coordinate
   private int myCol; //the column coordinate

   /**
   * Constructor
   *
   * @param int nm - the number of neighboring mines
   * @param int r - the row coordinate
   * @param int c - the column coordinate
   */
   public NumberSquare(int nm, int r, int c)
   {
      super(); //call the Square class' constructor
      this.neighborMines = nm;
      this.myRow = r;
      this.myCol = c;
   }

   /**
   * uncover
   *
   * Uncovers the square, unless it is flagged.
   * If the square is not flagged, it sets the square as uncovered and
   * sets its element to either an underscore ("_") if the number of
   * neighboring mines is 0, or to the number of neighboring mines.
   */
   public void uncover()
   {
      if(!(isFlagged()))
      {
         setUncovered();
         if(neighborMines == 0)
            setElement("_");
         else
            setElement(Integer.toString(neighborMines));
      }
   }

   /**
    * Returns false, since this is an unmined square.
    * @return false - it is not mined
    */
   public boolean isMine()
   {
      return false;
   }
}
