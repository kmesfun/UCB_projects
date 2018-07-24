/*Piece.java*/

/**
 *  Represents a Normal Piece in Checkers61bl
 * @author 
 */

public class Piece {
  
/**
   *  Define any variables associated with a Piece object here.  These
   *  variables MUST be private or package private.
   */

	private boolean captured = false;
	private int x;
	private int y;
	private boolean isCaptured;
	private boolean isFire = false;
	private Board b;
	private boolean isKing = false;
  /**
   * Returns the side that the piece is on
   * @return 0 if the piece is fire and 1 if the piece is water
   */
  public int side() {
    if(isFire){
    	return 0;
    }else{
    	return 1;
    }  
  }


  /**
   * Initializes a Piece
   * @param  side The side of the Piece
   * @param  b    The Board the Piece is on
   */
  public Piece(int side, Board b) {
   this.b = b;
   isFire = false;
   isKing = false;
   isCaptured = false;
   if (side == 1){
	   isFire = true;
   }
  }
  
  
  
  /**
   * checks if it is a King
   * @return
   */
    
    public boolean isKing() {
      return isKing;
    }
    
    public void setKing(boolean s){
        isKing= s;
    }

    
    public boolean isBomb(){
  	  return false;
    }
    
    public boolean isShield(){
  	  return false;
    }
    
    
    public boolean getCaptured(){
    	return isCaptured;
    			
    }
    
    public void setCaptured(boolean s){
        isCaptured = s;
    }


  /**
   * Destroys the piece at x, y. ShieldPieces do not blow up
   * @param x The x position of Piece to destroy
   * @param y The y position of Piece to destroy
   */
  public void getBlownUp(int x, int y) {
	  isCaptured = true;
	  b.remove(x, y);
  }

  /**
   * Does nothing. For bombs, destroys pieces adjacent to it
   * @param x The x position of the Piece that will explode
   * @param y The y position of the Piece that will explode
   */
  public void explode(int x, int y) {
   if(!isShield()){
    b.remove(x+1, y+1);
    b.remove(x+1, y-1);
    b.remove(x-1, y+1);
    b.remove(x-1, y-1);
    b.remove(x+1, y);
    b.remove(x-1, y);
    b.remove(x, y-1);
    b.remove(x, y+1);
   }
  }

  /**
   * Signals that this Piece has begun to capture (as in it captured a Piece)
   */
  public void startCapturing() {
	  boolean captured = Math.abs(y-this.y) == 2;
	  if(captured){
		  isCaptured = true;
	  }

  }

  /**
   * Returns whether or not this piece has captured this turn
   * @return true if the Piece has captured
   */
  public boolean hasCaptured() {
    return isCaptured;
  }

  /**
   * Resets the Piece for future turns
   */
  public void finishCapturing() {
	  isCaptured = false;
	  
	 
  }
}