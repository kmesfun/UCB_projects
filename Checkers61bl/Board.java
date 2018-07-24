/*Board.java*/

/**
 * Represents a Board configuration of a game of Checkers61bl
 * 
 * @author Kennedy Mesfun
 */

public class Board {

	/**
	 * Define any variables associated with a Board object here. These variables
	 * MUST be private.
	 */
	private Piece selectPiece;
	private int selx = 0;
	private int sely = 0;
	private static Piece[][] pieces = new Piece[8][8];
	private boolean select = false;
	private boolean move;
	private boolean isKing;
	private boolean WaterTurn;
	private static String type;
	private boolean boolCaptured = false;

	/**
	 * Constructs a new Board
	 * 
	 * @param shouldBeEmpty
	 *            if true, add no pieces
	 */

	public Board(boolean shouldBeEmpty) {
		for (int m = 0; m < 8; m++) {
			for (int n = 0; n < 8; n++) {
				pieces[m][n] = null;
			}
		}
		if (shouldBeEmpty == false) {
			for (int i = 0; i < 8; i += 2) {
				// shield
				pieces[i][6] = new ShieldPiece(0, this);
				// bomb
				pieces[i][2] = new BombPiece(1, this);
				// pawn
				pieces[i][0] = new Piece(1, this);

			}

			for (int i = 1; i < 8; i += 2) {
				// pawn
				pieces[i][7] = new Piece(0, this);
				// bomb
				pieces[i][5] = new BombPiece(0, this);
				// shield
				pieces[i][1] = new ShieldPiece(1, this);
			}
		}
	}

	/**
	 * gets the Piece at coordinates (x, y)
	 * 
	 * @param x
	 *            X-coordinate of Piece to get
	 * @param y
	 *            Y-coordinate of Piece to get
	 * @return the Piece at (x, y)
	 */
	public Piece pieceAt(int x, int y) {
		if (x > 7 || y > 7 || x < 0 || y < 0) {
			return (null);
		}
		return pieces[x][y];
	}

	/**
	 * Places a Piece at coordinate (x, y)
	 * 
	 * @param p
	 *            Piece to place
	 * @param x
	 *            X coordinate of Piece to place
	 * @param y
	 *            Y coordinate of Piece to place
	 */
	public void place(Piece p, int x, int y) {
		if (x < 8 && y < 8 && x >= 0 && y >= 0 && p != null) {
			pieces[x][y] = p;
		}
	}

	/**
	 * Removes a Piece at coordinate (x, y)
	 * 
	 * @param x
	 *            X coordinate of Piece to remove
	 * @param y
	 *            Y coordinate of Piece to remove
	 * @return Piece that was removed
	 */
	public Piece remove(int x, int y) {
		Piece returned = pieces[x][y];
		if (x > 7 || y > 7 || x < 0 || y < 0) {
			return null;
		}
		if (pieces[x][y] == null) {
			return null;
		} else {
			pieces[x][y] = null;
			return returned;
		}

	}
	
	public boolean diagMove(int xint, int yint, int xfin, int yfin){
		if((xint == xfin + 1 || xint == xfin - 1) && (yint == yfin + 1 || yint == yfin - 1)){
				return true;
		}
		return false;
	}
	
	public boolean diagMove2(int xint, int yint, int xfin, int yfin){
		if(((xint == xfin + 2 || xint == xfin - 2) && (yint == yfin + 2 || yint == yfin - 2))){
			return true;
		}
		return false;
	}
	
	public boolean canMove(int xint, int yint, int xfin, int yfin){
	if((pieces[(xfin + xint) / 2][(yfin + yint) / 2].side() == 0 && WaterTurn)){
		return true;
		}
	return false;
	
	}
	
	public boolean canMove2(int xint, int yint, int xfin, int yfin){
		if((pieces[(xfin + xint) / 2][(yfin + yint) / 2]) != null){
			if((pieces[(xfin + xint) / 2][(yfin + yint) / 2].side() == 1 && !WaterTurn)){
				return true;
			}
		}
	return false;
	}
	
	public boolean capMove(int xint, int yint, int xfin, int yfin){
		if(pieces[(xfin + xint) / 2][(yfin + yint) / 2]!=null && (canMove(xint, yint, xfin, yfin) || canMove2(xint, yint, xfin, yfin))){
			selectPiece.setCaptured(true);
			selectPiece.getCaptured();
			return true;
		}
		return false;

	}
	
	public boolean helperMove(int xint, int yint, int xfin, int yfin) {
		if (pieces[xfin][yfin] == null && pieces[xint][yint]!=null && xfin < 8 && yfin < 8 && xfin >= 0 && yfin >= 0) {
			if ((diagMove(xint,  yint,  xfin,  yfin) || diagMove2(xint, yint,  xfin,  yfin) && capMove(xint,yint, xfin,yfin) && (canMove(xint,yint,xfin,yfin) || canMove2(xint,  yint,  xfin,  yfin)))) {
				
				if (pieces[xint][yint].isKing()) {
					return true;
				}
				if ((pieces[xint][yint].side() == 0) && yfin > yint) {
						return true;
						
				}
				if (!(pieces[xint][yint].side() == 0) && yint > yfin) {
					return true;
				
				}
				
			}
		}
		return false;
	}


	/**
	 * Determines if a Piece can be selected
	 * 
	 * @param x
	 *            X coordinate of Piece
	 * @param y
	 *            Y coordinate of Piece to select
	 * @return true if the Piece can be selected
	 */
	public boolean canSelect(int x, int y) {
		if (x >= 0 && y >= 0 && x < 8 && y < 8) {
			if (pieces[x][y] != null && ((pieces[x][y].side() == 1 && WaterTurn) || (!(pieces[x][y].side() == 1) && !WaterTurn))) {
				if (!select || (select && !move)) {
					return true;
				}
			}
			if (selectPiece != null && pieces[x][y] == null && ((selectPiece.side() == 1 && WaterTurn) || (!(selectPiece.side() == 1) && !WaterTurn))) {
				
				if (select && !move && helperMove(selx,sely,x,y)) {
					return true;
				}
				if (select && selectPiece.hasCaptured() && capMove(selx, sely, x, y) && helperMove(selx, sely, x, y))  {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Selects a square. If no Piece is active, selects the Piece and makes it
	 * active. If a Piece is active, performs a move if an empty place is
	 * selected. Else, allows you to reselect Pieces
	 * 
	 * @param x
	 *            X coordinate of place to select
	 * @param y
	 *            Y coordinate of place to select
	 */
	public void select(int x, int y) {
		if (pieces[x][y] != null) {
			select = true;
			selx = x;
			sely = y;
			selectPiece = pieces[x][y];
		} else {
			if (selectPiece != null) {
				move = true;
				move(selx, sely, x, y);
				selx = x;
				sely = y;
			}
		}
	}

	/**
	 * Moves the active piece to coordinate (x, y)
	 * 
	 * @param x1
	 *            Original X coordinate of p
	 * @param y1
	 *            Origin Y coordinate of p
	 * @param x
	 *            X coordinate to move to
	 * @param y
	 *            Y coordinate to move to
	 */
	public void move(int x1, int y1, int x2, int y2) {
		selectPiece = pieceAt(x1, y1);
		remove(x1, y1);
		place(selectPiece, x2, y2);
		if (Math.abs(x2 - x1) == 2){
			boolCaptured = true;
			remove((x2 + x1) / 2, (y2 + y1) / 2);
			if (selectPiece.isBomb()) {
				for (int i=-1; i<2; i++){
					for (int j=-1; j<2; j++){
						Piece cur = pieceAt(x2 + i, y2 + j);
						if (cur!= null && !cur.isShield()) {
							remove(x2 + i, y2 + j);
							}
					}
				}
				return ;
			}
		}

		if (y2 == (1-selectPiece.side()) * 7) {
			selectPiece.setKing(true);
		}
		place(selectPiece, x2, y2);
		x1 = x2;
		y1 = y2;

	}

	/**
	 * Determines if the turn can end
	 * 
	 * @return true if the turn can end
	 */
	public boolean canEndTurn() {
		if (move) {
			return true;
		}
		return false;
	}

	/**
	 * Ends the current turn. Changes the player.
	 */
	public void endTurn() {
		if (canEndTurn()) {
			WaterTurn = !WaterTurn;
			move = false;
			select = false;
			selectPiece = null;
		}
		

	}

	/**
	 * Returns the winner of the game
	 * 
	 * @return The winner of this game
	 */
	public String winner() {
		int firepiece = 0;
		int waterpiece = 0;
		Piece cur;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				cur = pieceAt(i, j);
				if (cur != null){
					if (cur.side() == 0) {
						firepiece++;
						}
					else {
						waterpiece++;
						}
				}
			}
		}
		if (firepiece == 0 && waterpiece != 0){ 
			return "Water ";
			}
		if (firepiece != 0 && waterpiece == 0){ 
			return "Fire ";
			}
		if (firepiece == 0 && waterpiece == 0){ 
			return "No one ";
			}
		return null;
	}

	private void drawBoard(int xval, int yval) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if ((i + j) % 2 == 0) {
					StdDrawPlus.setPenColor(StdDrawPlus.GRAY);
				} else {
					StdDrawPlus.setPenColor(StdDrawPlus.RED);

				}

				if (selectPiece != null && selectPiece == pieces[i][j]) {
					StdDrawPlus.setPenColor(StdDrawPlus.WHITE);
				}
				StdDrawPlus.filledSquare(i + .5, j + .5, .5);
				if (pieces[i][j] != null) {
					String name = "";

					if (pieces[i][j].isBomb()) {
						name += "bomb";
					}

					else if (pieces[i][j].isShield()) {
						name += "shield";
					} else {
						name += "pawn";
					}
					if (pieces[i][j].side() == 0) {
						name += "-fire";

					} else {
						name += "-water";
					}
					if (pieces[i][j].isKing()) {
						name += "-crowned";
					}
					StdDrawPlus.picture(i + .5, j + .5, "img/" + name + ".png",
							1, 1);
				}
			}
		}
	}

	/**
	 * Starts a game
	 */
	public static void main(String[] args) {
		StdDrawPlus.setXscale(0, 8);
		StdDrawPlus.setYscale(0, 8);
		Board b = new Board(false);
		b.drawBoard(-1, -1);	

		while (b.winner() == null){
			StdDrawPlus.show(10);

			if(StdDrawPlus.isSpacePressed()){
				if(b.canEndTurn()){ 
					b.endTurn();
				}
				b.drawBoard(-1,-1);
			}


			if (StdDrawPlus.mousePressed()) {
				int curx = (int) StdDrawPlus.mouseX();
				int cury = (int) StdDrawPlus.mouseY();

				if(b.canSelect(curx, cury)) {
					b.select(curx, cury);
					b.drawBoard(b.selx,b.sely);
				}
			}
		}
		StdDrawPlus.show(10);
		b.drawBoard(-1,-1);
		System.out.println(b.winner() + " wins");
	}

}       