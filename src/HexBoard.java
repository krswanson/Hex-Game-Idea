import java.awt.Polygon;
import java.util.Iterator;

public class HexBoard {

	protected Polygon p;
	//The board is setup [column][row] but just use the get method
	protected Polygon[][] board;
	protected int h, w, columns;
	
	public HexBoard(int[] perCol, int winW, int winH){
		this(perCol, winW, winH, 40, 32);
	}
	
	public HexBoard(int[] perCol, int winW, int winH, int hexW, int hexH){
		columns = perCol.length;
		//hexes = new Vector<Polygon>(columns*4);
		board = new Polygon[columns][];
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
			Polygon[] thisCol = new Polygon[perCol[j]];
			for (i = 0; i < perCol[j]; i++){
				p.translate(0, h);
				thisCol[i] = new Polygon(p.xpoints, p.ypoints, p.npoints);
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
	
	public Polygon get(int row, int col){
		return board[col][row];
	}
	
	public boolean removeHex(int row, int col){
		if (row >= board[col].length) return false;
		Polygon[] temp = board[col];
		board[col] = new Polygon[board[col].length-1];
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
		return board[col].length < temp.length;
	}
	
	public void compress(){
		System.out.println("The method compress current does nothing.");
	}
	
	public Iterator<Polygon> iterator(){
		return new BoardIterator();
	}
	
	protected class BoardIterator implements Iterator<Polygon>{
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
		public Polygon next() {
			ensureRow();
			Polygon current = board[col][row];	
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
		
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	
}
