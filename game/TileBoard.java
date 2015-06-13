package game;

import java.awt.Polygon;
import java.util.HashSet;
import java.util.Iterator;

public abstract class TileBoard {

	/**
	 * @p The polygon shape that is used for each tile in the board
	 * @board Contains all the LinkedTiles.  Note: it must be arranged columns first, board[columns][rows]
	 * @h height of tile (used for anything besides constructor?)
	 * @w width of tile (used for anything besides constructor?)
	 * @columns The number of columns in the board (the row lengths can vary, but must be at least 1)
	 * @titles The total number of tiles in the board  
	 */
	protected Polygon p;
	protected LinkedTile[][] board;
	protected int h;
	protected int w;
	protected int columns;
	protected int tiles;

	public LinkedTile get(int row, int col) {
		return board[col][row];
	}

	/**
	 * @Return The tile at the x-y location on screen
	 */
	public LinkedTile getAt(int x, int y) {
		int col = getCol(columns/2,columns,x);
		if (col < 0) return null;
		int row = getRow(board[col].length/2, board[col].length, col, y);
		if (row < 0) return null;
		return get(row, col);
	}

	/**
	 * @param col the column to look in for this row (needed for non-square-tile implementations)
	 * @param yLocation The y-axis location on the screen
	 * @return The row index of the given column and y-axis location, or -1 if no such row exists
	 */
	public int getRow(int col, int yLocation) {
		if (col < 0) return -1;
		return getRow(board[col].length/2, board[col].length, col, yLocation);
	}
	/**
	 * 
	 * @param guess The row index to try first (linear-search-like?)
	 * @param rows The number of rows under consideration in this column
	 * @param col The column to look in for the tile
	 * @param y The y-axis location on the screen
	 * @return The row index of the given y location on the screen
	 */
	public int getRow(int guess, int rows, int col, int y){
		return 0;
	}
	
	/**
	 * @param xLocation the x-axis location on the screen
	 * @return the column index of the board at this location
	 */
	public int getCol(int xLocation) {
		return getCol(columns/2, columns, xLocation);
	}
	/**
	 * @param guess The column index to try (linear-search-like?)
	 * @param cols The number of columns under consideration
	 * @param x The x-axis location on the screen
	 * @return The column index of the given x location on the screen
	 */
	public int getCol(int guess, int cols, int x) {
		return 0;
	}

	public boolean removeTile(int row, int col) {
		return false;
	}

	public int size() {
		return tiles;
	}

	/**
	 * @param row The row index of the tile to find adjacent tiles for
	 * @param col The column index of the tile to find adjacent tiles for
	 * @return
	 */
	public HashSet<LinkedTile> getAdjacent(int row, int col) {
		return get(row, col).getAdjacent();
	}
	
	public Iterator<LinkedTile> iterator() {
		return new BoardIterator();
	}
	protected class BoardIterator implements Iterator<LinkedTile>{
		int row, col;
		
		public BoardIterator(){
			reset();
		}
		
		public void reset(){
			row = 0;
			col = 0;
		}
		
		@Override
		public boolean hasNext() {
			if (row == board[columns-1].length && col == columns-1)
				return false;
			else return true;
		}

		@Override
		public LinkedTile next() {
			ensureRow();
			LinkedTile current = board[col][row];	
			row++;
			return current;
		}

		public void remove(int row, int col) {
			ensureRow();
			removeTile(row, col);
		}
		
		private void ensureRow(){
			if (row == board[col].length){
				row = 0;
				col++;
			}
		}
	}
	

}