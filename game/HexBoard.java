package game;
import java.awt.Polygon;

public class HexBoard extends TileBoard {

	public HexBoard(int[] perCol, int upperLeftX, int upperLeftY){
		this(perCol, upperLeftX, upperLeftY, 40, 32);
	}
	
	public HexBoard(int[] perCol, int upperLeftX, int upperLeftY, int hexW, int hexH){
		columns = perCol.length;
		board = new GameTile[columns][];
		h = hexH;
		w = hexW;
		int[] xpoints = {w/4,w-w/4,w,w-w/4,w/4,0};
		int[] ypoints = {0,0,h/2,h,h,h/2};
		p = new Polygon(xpoints, ypoints, 6);
		p.translate(upperLeftX, upperLeftY);
		
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
	
}
