package game;
import java.awt.Polygon;

public class HexBoard extends TileBoard {

	public HexBoard(int[] perCol, int upperLeftX, int upperLeftY){
		this(perCol, upperLeftX, upperLeftY, 40, 32);
	}
	
	public HexBoard(int[] perCol, int upperLeftX, int upperLeftY, int hexW, int hexH){
		columns = perCol.length;
		board = new LinkedTile[columns][];
		h = hexH;
		w = hexW;
		int[] xpoints = {w/4,w-w/4,w,w-w/4,w/4,0};
		int[] ypoints = {0,0,h/2,h,h,h/2};
		p = new Polygon(xpoints, ypoints, 6);
		p.translate(upperLeftX, upperLeftY);
		
		//Each row array has its own length
		int i, correction = 0;
		for (int j = 0; j < columns; j++){
			if (perCol[j] < 1)perCol[j] = 1;
			LinkedTile[] thisCol = new LinkedTile[perCol[j]];
			for (i = 0; i < perCol[j]; i++){
				p.translate(0, h);
				thisCol[i] = new LinkedTile(new Polygon(p.xpoints, p.ypoints, p.npoints));
				tiles++;
			}
			board[j] = thisCol;
			setupAdjacent(j == 0, j);
			
			if (j == columns-1) break;
			else if (perCol[j] < perCol[j+1]) correction = -h/2;
			else {// column is == or >, if it is ==, then do the opposite of what was just done
				if (perCol[j] == perCol[j+1] && correction == h/2) correction = -h/2;
				else correction = h/2;
			}
			p.translate(w*3/4, -i*h + correction);
		}
	}
	
	/**
	 * Sets the adjacent LinkedTiles of this column and the previous column to be adjacent
	 * @param firstCol true if this is the very first column in the board
	 * @param col the current column to set up adjacency on its tiles
	 */
	private void setupAdjacent(boolean firstCol, int col) {
		int rows = board[col].length;
		if (!firstCol) { // Get adjacency with previous column
			// Get the number of rows of the smaller column (if not equal)
			int doPrevRows = board[col-1].length;
			if (board[col].length < doPrevRows) doPrevRows = board[col].length;
			
			for (int i = 0; i < doPrevRows; i++) {
				// Always do row of previous column that is the same row index of this one
				board[col - 1][i].addAdjacent(board[col][i]);
				board[col][i].addAdjacent(board[col - 1][i]);
				
				if (i == 0 && (col % 2) == 0) continue; // Skip top of upshifted column
				if (i == doPrevRows - 1 && (col % 2) == 1) continue; // Skip bottom of downshifted colunm
		
				// Get the second hex from the previous column
				if ((col % 2) == 1) { // Downshifted row is adjacent to previous column's next row down
					board[col - 1][i + 1].addAdjacent(board[col][i]);
					board[col][i].addAdjacent(board[col - 1][i + 1]);				
				} else { // Upshifted row is adjacent to previous column's next row up
					board[col - 1][i - 1].addAdjacent(board[col][i]);
					board[col][i].addAdjacent(board[col - 1][i - 1]);
				}
			}
		} 
		// Get adjacency within current column
		for (int i = 1; i < rows; i++) {
			board[col][i].addAdjacent(board[col][i - 1]);
			board[col][i - 1].addAdjacent(board[col][i]);
		}

	}
	
	@Override 
	public int getRow(int guess, int rows, int col, int y){
		if (rows <= 0) return -1;
		else if (guess >= board[col].length) return -1;
		else if (guess < 0) return -1;
		else{
			Polygon current = board[col][guess].shape;
			if (current.ypoints[0] < y){
				if (current.ypoints[3] > y) return guess;
				else return getRow(guess + (rows+2)/4, rows/2, col, y);
			}else{
				return getRow(guess - (rows+2)/4, rows/2, col, y);
			}
		}
	}
	
	@Override 
	public int getCol(int guess, int cols, int x){
		if (cols <= 0) return -1;
		else if (guess >= board.length) return -1;
		else if (guess < 0) return -1;
		else{
			Polygon current = board[guess][0].shape;
			if (current.xpoints[5] < x){
				if (current.xpoints[2] > x) return guess;
				else return getCol(guess+(cols+2)/4, cols/2, x);
			}else{
				return getCol(guess - (cols+2)/4, cols/2, x);
			}
		}
	}
	
	@Override
	public boolean removeTile(int row, int col) {
		if (row >= board[col].length) return false;
		LinkedTile[] temp = (LinkedTile[]) board[col];
		board[col] = new LinkedTile[board[col].length-1];
		int t = 0, b = 0;
		while (b < board[col].length){
			if (t == row){
				t++;
				continue;
			}
			board[col][b] = temp[t];
			t++;
			b++;
		}
		tiles--;
		return true;
	}
	

	public void compress(){
		//TODO
		System.out.println("The method compress current does nothing.");
	}
	
}
