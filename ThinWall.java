
/**
 * Represents thin walls that can be FILLED or EMPTY.
 */
public enum ThinWall{
  EMPTY(false),
  FILLED(true);

  private final boolean filled;
  /*
   * A boolean directly corresponding to which state its ThinWall is in, to make logic more 
   * readable and compact.
   */

  /**
   * Sets filled, an internal representation of an ThinWall's state, to the correct value.
   * 
   * @param bool The value that filled will be set to.
   */
  private ThinWall(boolean bool){filled = bool;}

  /**
   * Returns true if a ThinWall is EMPTY. 
   * 
   * @return If a ThinWall is EMPTY.
   */
  public boolean isEMPTY(){return !filled;}

  /**
   * Returns true if a ThinWall is FILLED. 
   * 
   * @return If a ThinWall is FILLED.
   */
  public boolean isFILLED(){return filled;}
}
