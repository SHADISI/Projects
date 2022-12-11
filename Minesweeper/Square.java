/*
* Ivan Shadis
*
* Square is an abstract class that provides
* the foundation for MineSquares and NumberSquares
*/

abstract class Square
{
   private String element; // the symbol that depicts this square on the board
   private boolean flagged; // whether the square is flagged
   private boolean uncovered; // whether the square is uncovered
   
   /*
   * Constructor
   */
   public Square()
   {
      this.element = "x";     // set element to default x
      this.flagged = false;   // set flagged to false
      this.uncovered = false; // set uncovered to false
   }
   
   /*
   * Constructor
   * @param String e - the element
   * @param boolean f - whether flagged
   * @param boolean u - whether uncovered
   */
   public Square(String e, boolean f, boolean u)
   {
      this.element = e; // set element to e
      this.flagged = f; // set elemnet to f
      this.uncovered = u; // set uncovered to u
   }
   
   /*
   * isFlagged
   * @return boolean flaggged - whether square is flagged
   */
   public boolean isFlagged()
   {
      return flagged;
   }
   /*
   * isUncovered
   * @return boolean uncovered - whether square is uncovered
   */
   public boolean isUncovered()
   {
      return uncovered;
   }
   
   /**
   *flagSquare flags a square if it is not already flagged or uncovered. 
   *If the square is already flagged, it unflags it.
   *If the square is uncovered, it prints an error message.
   */
   public void flagSquare()
   {
      if(isFlagged())
      {
         this.flagged = false;
         this.element = "x";
      }
      else if(isUncovered())
      {
         System.out.println("Invalid move: cannot flag uncovered square");
      }
      else if(!(isUncovered()))
      {
         this.flagged = true;
         this.element = "f";
      }
   }
   
   public void setUncovered() 
   {
      if(!(isFlagged()))
      {
         this.uncovered = true;
      }  
   }
   public void setElement(String e)
   {
      this.element = e;
   }
   public String toString()
   {
      String str = element;
      return str;
   }
   public abstract void uncover();
 
   public abstract boolean isMine();

}