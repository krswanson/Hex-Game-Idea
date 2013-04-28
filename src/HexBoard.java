import java.awt.Polygon;
import java.util.Vector;

public class HexBoard {

	protected Polygon p;
	protected Vector<Polygon> hexes;
	protected Polygon[][] board;
	protected int h, w, columns;
	
	public HexBoard(int hexW, int hexH, int[] perCol, int winW, int winH){
		columns = perCol.length;
		hexes = new Vector<Polygon>(columns*4);
		//board = new Polygon[columns][];
		h = hexH;
		w = hexW;
		int[] xpoints = {w/4,w-w/4,w,w-w/4,w/4,0};
		int[] ypoints = {0,0,h/2,h,h,h/2};
		p = new Polygon(xpoints, ypoints, 6);
		p.translate(winW/2-w*columns/2, winH/2-h*perCol[(int)(columns/2)]/2);
		
		//find largest
		int i, correction;
		for (int j = 0; j < columns; j++){
			for (i = 0; i < perCol[j]; i++){
				p.translate(0, h);
				hexes.add(new Polygon(p.xpoints, p.ypoints, p.npoints));
				
			}
			if (j == columns-1) break;
			else if (perCol[j] <= perCol[j+1]) correction = -h/2;
			else correction = h/2;
			p.translate(w*3/4, -i*h + correction);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
