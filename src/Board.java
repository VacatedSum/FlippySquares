/**
 * Board - A Board for the game "Lights Out"
 * 
 * @author Steve Cina
 * @since April 2020
 *
 */
public class Board {
	//This keeps track of what Lights are currently turned on.
	private Boolean[][] board;
	private int boardLength;
	
	/**
	 * Create a board of size x*x
	 * @param x The length/width of the square board to be created.
	 */
	public Board(int x) {
		boardLength = x;
		board = new Boolean[x][x];
		
		//Start with all of the lights in the on position
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				board[i][j] = true;
			}
		}
	}
	/**
	 * Push the button at the given x/y coordinates.
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @return True if button was actually pressed. <p>
	 * 			False if button was already off, and thus could not be pressed.
	 */
	public Boolean pushButton(int x, int y) {
		//If light is off, can't press the button.
		if (board[x][y]==false) {
			return false;
		}
		else {
			board[x][y] = false;
			//Deal with the left
			if (x>0) {
				if (board[x-1][y]==false) {
					board[x-1][y] = true;
				}
				else {
					board[x-1][y] = false;
				}
			}
			
			if (x<boardLength-1) {
				if (board[x+1][y] == false) {
					board[x+1][y] = true;
				}
				else {
					board[x+1][y] = false;
				}
			}
			//Deal with the top
			if (y>0) {
				if (board[x][y-1]==false) {
					board[x][y-1]=true;
				}
				else {
					board[x][y-1]=false;
				}
			}
			
			if (y<boardLength-1) {
				if (board[x][y+1]==false) {
					board[x][y+1]=true;
				}
				else {
					board[x][y+1] = false;
				}
			}
		}
		return true;
	}

	/**
	 * Check to see if player won.
	 * @return True if all lights are now turned off.<p>
	 * 			False if there are still lgihts on.
	 * 	
	 */
	public Boolean checkFinished() {
		
		for (int i = 0; i < boardLength; i++) {
			for (int j = 0; j < boardLength; j++) {
				if (board[i][j]==true) {
					return false;
				}
			}
		}
		return true;
	}
	/**
	 * Returns the state of the Button at the selected coordinates
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @return True if button is currently "on"<p>
	 * 			False if button is currently "off"
	 */
	public Boolean getButton(int x, int y) {
		return board[x][y];
	}
	
	/**
	 * Set all board Button values to "on"
	 */
	public void resetBoard() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				board[i][j] = true;
			}
		}
	}
}
