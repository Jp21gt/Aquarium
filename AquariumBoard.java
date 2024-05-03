import java.util.*;

/**
 * Represents the board in an Aquarium game.
 */
public class AquariumBoard extends GenericBoard{
  private final ThinWall[][] hwalls;
  private final ThinWall[][] vwalls;
  private final NumFilledLine[] rowRuns;
  private final NumFilledLine[] colRuns;
  private final AquariumCell[][] board;
  private static AquariumBoard instance;

  public static void startGame(){
    new AquariumBoard();
  }
  
  private AquariumBoard(){
    instance = this;
    {
      Cell a = Cell.FILLED;
      Cell b = Cell.UNMARKED;
      solvedBoard = new Cell[][]{
        {b,b,a,a,a,a},
        {b,b,b,b,b,a},
        {b,b,b,b,a,a},
        {a,a,b,b,b,b},
        {a,a,b,b,b,b},
        {a,b,b,b,b,a}
      };
    
      ThinWall c = ThinWall.FILLED;
      ThinWall d = ThinWall.EMPTY;
      hwalls = new ThinWall[][]{
        {c,c,c,c,c,c},
        {c,d,c,c,c,d},
        {d,d,d,d,c,c},
        {d,d,c,c,c,c},
        {d,d,c,c,c,d},
        {d,c,d,d,d,d},
        {c,c,c,c,c,c}
      };
      /*
       * include both the top and bottom line of horizontal walls.
       */
      vwalls = new ThinWall[][]{
        {c,d,c,d,d,d,c},
        {c,c,d,d,d,c,c},
        {c,c,d,d,c,d,c},
        {c,c,c,d,d,d,c},
        {c,c,c,d,d,c,c},
        {c,c,d,d,d,c,c}
      };
      /*
       * include both side walls.
       */
    }

    height = solvedBoard.length;
    width = solvedBoard[0].length;

    board = new AquariumCell[height][width];
    for(int row  = 0; row < height; row++){
      for(int col = 0; col < width; col++){
        board[row][col] = new AquariumCell(col, row);
      }
    }

    rowRuns = new NumFilledLine[height];
    for(int row = 0; row < height; row++){
      rowRuns[row] = new NumFilledLine(solvedBoard[row]);
      for(int col = 0; col < width; col++){
        board[row][col] = new AquariumCell(col, row);
      }
    }

    colRuns = new NumFilledLine[width];
    for(int col = 0; col < width; col++){
      Cell[] tempCol = new Cell[height];
      for(int row = 0; row < height; row++){
        tempCol[row] = solvedBoard[row][col];
      }
      colRuns[col] = new NumFilledLine(tempCol);
    }

    int i = 1;
    for(int row = 0; row < height; row++){
      for(int col = 0; col < width; col++){
        new AquariumTank(col, row);
        i++;
      }
    }

    start();
    instance = null;
  }
  
  public static AquariumBoard getInstance(){
    return instance;
  }

  public void setAquariumCellsTank(int x, int y, AquariumTank tank){
    board[y][x].setTank(tank);
  }

  public boolean isHWallEmpty(int x, int y){
    return (hwalls[y][x] == null) ? false : hwalls[y][x].isEMPTY();
  }

  public boolean isVWallEmpty(int x, int y){
    return (vwalls[y][x] == null) ? false : vwalls[y][x].isEMPTY();
  }

  public boolean isValidCoordinate(int x, int y){
    if(x >= 0){
      if(x < width){
        if(y >= 0){
          if(y < height){
            return true;
          }
        }
      }
    }
    return false;
  }

  protected void print(){
    System.out.print(" ");
    for(int col = 0; col < height; col++){
      System.out.print(" " + colRuns[col].getNumFilled());
    }
    System.out.println(" ");

    for(int row = 0; row < width; row++){
      System.out.print(" ");
      for(ThinWall hwall : hwalls[row]){
        String str = hwall.isEMPTY() ? " " : "-";
        System.out.print("+" + str);
      }
      
      System.out.println("+");

      System.out.print(rowRuns[row].getNumFilled());
      for(int col = 0; col < height; col++){
        String str = vwalls[row][col].isEMPTY() ? " " : "|";
        System.out.print(str);
        System.out.print(board[row][col]);
      }

      {
        String str = vwalls[row][height].isEMPTY() ? " " : "|";
        System.out.println(str);
      }
    }
    
    System.out.print(" ");
    for(ThinWall hwall : hwalls[height - 1]){
      String str = hwall.isEMPTY() ? " " : "-";
      System.out.print("+" + str);
    }
    System.out.println("+");
  }

  protected void endingMessage(){
    System.out.println("You Solved The Aquarium!");
  }
  
  protected boolean isFinished(){
    for(int row = 0; row < board.length; row++){
      for(int col = 0; col < board[0].length; col++){
        if(!solvedBoard[row][col].solvedEquals(board[row][col].getCell())){
          return false;
        }
      }
    }
    return true;
  }
  
  protected void userInput(){
    /*
      user can input coordinates in the following format
      x y empty
      x y cross
      x y fill
    */
    System.out.println("Please type in the coordinates of the point and what you want to change it to.");
    System.out.println("  X Y Type eg. 1 1 fill or 5 5 cross or 2 3 empty");
    
    String str = in.nextLine();
    System.out.println();
    String cellType = "";
    int x = -1;
    int y = -1;
    try{
      cellType = str.substring(4, str.length());
      x = Integer.parseInt(str.substring(0,1)) - 1;
      y = Integer.parseInt(str.substring(2,3)) - 1;
    }
    catch(Exception e){
      System.out.println("Invalid input, please try again.");
    }
    
    if(y >= 0 && x >= 0 && y < height && x < width){
      Cell c = board[y][x].getCell();
      switch (cellType) {
        case "empty":
          c = Cell.UNMARKED;
          break;
        case "cross":
          c = Cell.CROSSED_OUT;
          break;
        case "fill":
          c = Cell.FILLED;
          break;
      }
      
      board[y][x].setCell(c, true);
    }
  }
}
