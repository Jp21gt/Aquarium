import java.util.*;

// a class to mamange things most boards like the ones found in aquarium or nonograms have
public class GenericBoard{
  protected Cell[][] solvedBoard; // the solution to the game
  protected Cell[][] board; // the playing board that the player can change
  protected int height;
  protected int width;
  protected final Scanner in = new Scanner(System.in);
  
  protected void start(){
    print();
    play();
  }
  
  protected void print(){ // overriden for the aquarium game
    for(Cell[] cellArr : board){
      for(Cell c : cellArr){
        System.out.print(c);
      }
      System.out.println();
    }
  }
  
  private void play(){ // lets the player change things, than prints the updated board. if the baord is solved, then end the game
    boolean loop = true;
    while(loop){
      userInput();
      print();
      if(isFinished()){
        endingMessage();
        printSolvedBoard();
        System.out.println();
        loop = false;
        break;
      }
    }
  }
  
  private void printSolvedBoard(){
    for(int row = 0; row < height; row++){
      for(int col = 0; col < width; col++){
        String s = (solvedBoard[row][col].isFilled()) ? "#" : " ";
        System.out.print(s);
      }
      System.out.println();
    }
  }

  protected void endingMessage(){ // overiden for the aquarium game
    System.out.println("You Solved It!");
  }
  
  protected void userInput(){ // overiden for the aquarium game, but inputs go from 1 to width or height (inclusive)
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
      switch (cellType) {
        case "empty":
          board[y][x] = Cell.UNMARKED;
          break;
        case "cross":
          board[y][x] = Cell.CROSSED_OUT;
          break;
        case "fill":
          board[y][x] = Cell.FILLED;
          break;
      }
    }
  }

  protected boolean isFinished(){ // overiden for Aquarium game
    for(int row = 0; row < board.length; row++){
      for(int col = 0; col < board[0].length; col++){
        if(!solvedBoard[row][col].solvedEquals(board[row][col])){
          return false;
        }
      }
    }
    return true;
  }
}
