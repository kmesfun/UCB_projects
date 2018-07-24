/*BombPiece.java*/

/**
 *  Represents a BombPiece ins Checkers61bl
 * @author 
 */

public class BombPiece extends Piece {
 
  /**
   *  Define any variables associated with a BombPiece object here.  These
   *  variables MUST be private or package private.
   */

  /**
   * Constructs a new BombPiece
   * @param  side what side this BombPiece is on
   * @param  b    Board that this BombPiece belongs to
   */
  public BombPiece(int side, Board b) {
	  super(side, b);
	
  }
  
  @Override
  public boolean isBomb(){
	  
	  return true;
  }
  
  
}
