/*
*Ivan Shadis
*
*UncoverFlagException extends Exception and is used
*to throw a warning when a user attempts to uncoer
*a flagged square
*/ 
public class UncoverFlagException extends Exception
{
   public UncoverFlagException(String str)
   {
      super(str);
   }
}
      