
/**
 * Represents the 3 possible states a tile in a game can be, UNMARKED, CROSSED_OUT, and 
 * FILLED.

 */
public enum Cell{
  UNMARKED((short)0),
  CROSSED_OUT((short)-1),
  FILLED((short)1);
  
  /*
   * An integer directly corresponding to which state its Cell is in, to make logic more readable and 
   * compact. The type short is used to conserve memory, as the enum only uses 3 different single 
   * digit numbers.
   */
  private final short flag;
  
  /**
   * Sets flag, an internal representation of a Cell's state, to the correct value.
   * 
   * @param newFlag The value that flag will be set to.
   */
  private Cell(short newFlag){flag = (short)newFlag;}
  
  /**
   * Returns the value of a Cell's flag.
   * 
   * @return The value of a Cell's flag.
   */
  public int getFlag(){return flag;}

  /**
   * Returns true if both Cells have the same state. This corectly returns false if passed a null 
   * value.
   * 
   * @param other The other Cell to be used in the comparison, which can be null.
   * @return If 2 Cells have the same state or not.
   */
  public boolean equals(Cell other){return (other != null) && (flag == other.getFlag());}

  /**
   * Returns true if both Cells are both filled or both not filled. This is used when determining 
   * if the board is solved, and correctly returns false if passed a null value.
   * 
   * @param other The other Cell to be used in the comparison, which can be null.
   * @return If 2 Cells are both filled or both not filled.
   */
  public boolean solvedEquals(Cell other){
    /*
     * If the Cells are normally equal, they will always be solvedEqual, but UNMARKED and CROSSED_OUT 
     * Cells should register as equal.
     */
    return ((equals(other) || ((!isFilled()) && (!other.isFilled()))));
  }

  /**
   * Returns true if a Cell is UNMARKED. 
   * 
   * @return If a Cell is UNMARKED.
   */
  public boolean isUnmarked(){return flag == 0;}

  /**
   * Returns true if a Cell is CROSSED_OUT.
   * 
   * @return If a Cell is CROSSED_OUT.
   */
  public boolean isCrossedOut(){return flag == -1;}

  /**
   * Returns true if a Cell is FILLED.
   * 
   * @return If a Cell is FILLED.
   */
  public boolean isFilled(){return flag == 1;}

  /**
   * Returns a different Cell object with the same state.
   * 
   * @return A new Cell object with the same state.
   */
  public Cell copy(){
    switch(flag){
      case -1 : return Cell.CROSSED_OUT;
      case 0 : return Cell.UNMARKED;
      case 1 : return Cell.FILLED;
    }
    return null;
  }

  /**
   * Returns a single character meant to be used for representing a Cell and it's state in the console.
   * 
   * @return A single charcter long String representing a Cell's state.
   */
  public String toString(){
    switch(flag){
      case -1 : return "X";
      case 0 : return ".";
      case 1 : return "#";
    }
    return null;
  }
}
