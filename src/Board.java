
public class Board {
	private Boolean[][] board;
	private int boardLength;
	public Board(int x) {
		boardLength = x;
		board = new Boolean[x][x];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				board[i][j] = true;
			}
		}
	}
	
	public Boolean pushButton(int x, int y) {
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

	public Boolean getButton(int x, int y) {
		return board[x][y];
	}
	public Boolean setButton(int x, int y) {
		return true;
	}

	public void resetBoard() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				board[i][j] = true;
			}
		}
	}
}
