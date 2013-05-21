import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.Iterator;



@SuppressWarnings("serial")
public class TheGame extends Frame {

	protected int W = 500, H = 250;
	protected HexBoard b = new HexBoard(new int[] {2,3,3,3,4,5,4,3,3,3,2}, W, H);
	
	public TheGame(){
		
		setSize(W,H);
		setVisible(true);
		
	}
	
	public void paint(Graphics g){
		Iterator<GameTile> hexes = b.iterator();
		int i = 0;
		while (hexes.hasNext()){
			if (i%3==0) g.setColor(Color.GREEN);
			else if (i%3 ==1) g.setColor(Color.YELLOW);
			else g.setColor(Color.RED);
			Polygon p = hexes.next().shape;
			g.fillPolygon(p);
			g.setColor(Color.BLACK);
			g.drawPolygon(p);
			i++;
		}
		
	}
	
	public static void main(String[] args) {
		TheGame g = new TheGame();
		System.out.println(g.b.removeHex(1,1));
		System.out.println(g.b.removeHex(1,5));
		System.out.println(g.b.removeHex(1,5));
		System.out.println(g.b.removeHex(3,1));
		System.out.println("In Main " + g.b.board[1].length + " " + g.b.board[5].length);
		
	}

}
