
/**
 * Represents the states and functions an tile can be or do in the game Aquarium. 
 */
public class AquariumCell{

  /*
   * tank: Each AquariumCell is a part of an AquariumTank that makes water flood to adjacent or lower 
   *   AquariumCells.
   * cell: A Cell to represent the state of a AquariumCell
   * x: The x coordinate of an AquariumCell, that ranges from 0 to width - 1 (inclusive).
   * y: The y coordinate of an AquariumCell, that ranges from 0 to height - 1 (inclusive).
   */
  private AquariumTank tank = null;
  private Cell cell;
  private int x; // 0 to width - 1 (inclusive)
  private int y; // 0 to height - 1 (inclusive)
  
  /**
   * Provides an AquariumCell with it's x and y. Precondition: 0 <= x <= width - 1, 
   * 0 <= y <= height - 1.
   * 
   * @param newx The new x coordinate of an AquariumCell. Precondition: 0 <= x <= width - 1.
   * @param newy The new y coordinate of an AquariumCell. Precondition: 0 <= y <= height - 1.
   */
  public AquariumCell(int newx, int newy){
    cell = Cell.UNMARKED;
    x = newx;
    y = newy;
  }

  /**
   * Returns an AquariumCell's x coordinate.
   * 
   * @return The x coordinate of an AquariumCell.
   */
  public int getX(){return x;}

  /**
   * Returns an AquariumCell's y coordinate.
   * 
   * @return The y coordinate of an AquariumCell.
   */
  public int getY(){return y;}

  /**
   * Returns an AquariumCell's Cell.
   * 
   * @return The Cell of an AquariumCell.
   */
  public Cell getCell(){return cell;}

  /**
   * Changes an AquariumCell's state, and possibly triggers the flooding of other AquariumCells in 
   * it's AquariumTank.
   * 
   * @param newCell The new state of the AquariumCell.
   * @param flood Whether to flood other AquarquimCells in an AquariumCells's AquariumTank.
   */
  public void setCell(Cell newCell, boolean flood){
    cell = newCell.copy();
    if(flood){tank.updateFrom(this);}
  }
  
  /**
   * Returns an AquariumCell's AquariumTank.
   * 
   * @return An AquariumCell's AquariumTank
   */
  public AquariumTank getTank(){return tank;}

  /**
   * Returns a single character meant to be used for representing an AquariumCell and it's state in 
   * the console.
   * 
   * @return A single charcter long String representing an AquariumCell's state.
   */
  public String toString(){
    return (cell.isUnmarked()) ? " " : cell.toString();
  }

  /**
   * A recursive method initiated by an AquariumTank to fill it with all the AquariumCells it should 
   * have by making each Cell in it add their neighbors as appropriate, and making each new neighbor 
   * repeat the process.
   * 
   * @param newTank The AquariumTank the AquariumCells will be a part of.
   */
  public void setTank(AquariumTank newTank){
    if(tank == null){
        // if this cell is not already part of a tank, get added to the tank and add it's neighbors if they exists and are nto blocked by a wall, this does not work
      tank = newTank;
      tank.add(this);
      AquariumBoard aq = AquariumBoard.getInstance();

      if(aq.isValidCoordinate(x, y-1) && aq.isHWallEmpty(y,x)){
        aq.setAquariumCellsTank(x, y-1, tank);
      }
      if(aq.isValidCoordinate(x, y+1) && aq.isHWallEmpty(y+1,x)){
        aq.setAquariumCellsTank(x, y+1, tank);
      }
      if(aq.isValidCoordinate(x+1, y) && aq.isVWallEmpty(y,x+1)){
        aq.setAquariumCellsTank(x+1, y, tank);
      }
      if(aq.isValidCoordinate(x-1, y) && aq.isVWallEmpty(y,x)){
        aq.setAquariumCellsTank(x-1, y, tank);
      }
    }
  }
}