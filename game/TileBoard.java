package game;

import java.awt.Polygon;
import java.util.Iterator;

public abstract class TileBoard {

	protected Polygon p;
	protected GameTile[][] board;
	protected int h;
	protected int w;
	protected int columns;
	protected int tiles;

	public GameTile get(int row, int col) {
		return board[col][row];
	}

	/*
	 * @Return the tile at the x-y location on screen
	 */
	public GameTile getAt(int x, int y) {
		int col = getCol(columns/2,columns,x);
		if (col == -1) return null;
		int row = getRow(board[col].length/2, board[col].length, col, y);
		if (row == -1) return null;
		return get(row, col);
	}

	public int getRow(int guess, int rows, int col, int y){
		// TODO Auto-generated method stub
		return 0;
	}
	public int getCol(int guess, int cols, int x) {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean removeTile(int row, int col) {
		return false;
	}

	public int size() {
		return tiles;
	}

	public Iterator<GameTile> iterator() {
		return new BoardIterator();
	}
	protected class BoardIterator implements Iterator<GameTile>{
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
		public GameTile next() {
			ensureRow();
			GameTile current = board[col][row];	
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