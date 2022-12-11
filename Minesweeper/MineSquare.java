/**
*  Ivan Shadis
*
* MineSquare represents a mine square in the game.
* It extends the Square class and overrides the uncover and isMine methods.
*/
public class MineSquare extends Square
{
   /**
    * Uncovers the square, unless it is flagged.
    * If the square is not flagged, it sets the square as uncovered and
    * sets its element to "*" to represent a mine.
    */
   public void uncover()
   {
      if(!(super.isFlagged()))
      {
         setUncovered();
         setElement("*");
      }
   }

   /**
   * Returns true, since this is a mine
   * @return true - that this is a mine
   */
   public boolean isMine()
   {
      return true;
   }
}
