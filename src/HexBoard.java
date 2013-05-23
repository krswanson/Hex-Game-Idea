import java.awt.Polygon;
import java.util.Iterator;

public class HexBoard {

	protected Polygon p;
	//The board is setup [column][row] but just use the get method
	protected GameTile[][] board;
	protected int h, w, columns, tiles = 0;
	
	public HexBoard(int[] perCol, int winW, int winH){
		this(perCol, winW, winH, 40, 32);
	}
	
	public HexBoard(int[] perCol, int winW, int winH, int hexW, int hexH){
		columns = perCol.length;
		board = new GameTile[columns][];
		h = hexH;
		w = hexW;
		int[] xpoints = {w/4,w-w/4,w,w-w/4,w/4,0};
		int[] ypoints = {0,0,h/2,h,h,h/2};
		p = new Polygon(xpoints, ypoints, 6);
		p.translate((int)(winW/2-w*columns/2+.5*w)/1, 
				(int)(winH/2-h*perCol[(int)(columns/2)]/2+.5*h));
		
		//Each row array has its own length
		int i, correction = 0;
		for (int j = 0; j < columns; j++){
			GameTile[] thisCol = new GameTile[perCol[j]];
			for (i = 0; i < perCol[j]; i++){
				p.translate(0, h);
				thisCol[i] = new GameTile(new Polygon(p.xpoints, p.ypoints, p.npoints));
				tiles++;
			}
			board[j] = thisCol;
			
			if (j == columns-1) break;
			else if (perCol[j] < perCol[j+1]) correction = -h/2;
			else {// column is == or >, if it is ==, then do the opposite of what was just done
				if (perCol[j] == perCol[j+1] && correction == h/2) correction = -h/2;
				else correction = h/2;
			}
			p.translate(w*3/4, -i*h + correction);
		}
	}
	
	public GameTile get(int row, int col){
		return board[col][row];
	}
	
	public boolean removeHex(int row, int col){
		if (row >= board[col].length) return false;
		GameTile[] temp = board[col];
		board[col] = new GameTile[board[col].length-1];
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
	
	public int size(){
		return tiles;
	}
	public Iterator<GameTile> iterator(){
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

		@Override
		public void remove() {
			ensureRow();
			removeHex(row, col);
		}
		
		private void ensureRow(){
			if (row == board[col].length){
				row = 0;
				col++;
			}
		}
	}
}
