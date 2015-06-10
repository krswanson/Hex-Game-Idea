package game;
import java.awt.Polygon;

public class RectBoard extends TileBoard {

	public RectBoard(int[] perCol, int upperLeftX, int upperLeftY){
		this(perCol, upperLeftX, upperLeftY, 40, 32);
	}
	
	public RectBoard(int[] perCol, int upperLeftX, int upperLeftY, int tileW, int tileH){
		columns = perCol.length;
		board = new GameTile[columns][];
		h = tileH;
		w = tileW;
		int[] xpoints = {0,w,w,0};
		int[] ypoints = {0,0,h,h};
		p = new Polygon(xpoints, ypoints, 4);
		p.translate(upperLeftX, upperLeftY);
		
		//Each row array has its own length
		int i;
		for (int j = 0; j < columns; j++){
			GameTile[] thisCol = new GameTile[perCol[j]];
			for (i = 0; i < perCol[j]; i++){
				p.translate(0, h);
				thisCol[i] = new GameTile(new Polygon(p.xpoints, p.ypoints, p.npoints));
				tiles++;
			}
			board[j] = thisCol;
			p.translate(w, -i*h);
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
		//System.out.println("zero "+ guess + " " + cols);
		
		if (cols <= 0) return -1;
		else if (guess >= board.length) return -1;
		else if (guess < 0) return -1;
		else{
			Polygon current = board[guess][0].shape;
			if (current.xpoints[0] < x){
			//	System.out.println("one " + guess + " " + cols);
				if (current.xpoints[1] > x) return guess;
				else {
				//	System.out.println("two "+ guess + " " + cols);
					return getCol(guess+(cols+2)/4, cols/2, x);
				}
			}else{
				return getCol(guess - (cols+2)/4, cols/2, x);
			}
		}
	}
	
	public void compress(){
		//TODO
		System.out.println("The method compress current does nothing.");
	}
	
}
