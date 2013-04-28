import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Polygon;


public class TheGame extends Frame {

	protected int W = 500, H = 400, w = 40, h = 32;
	protected HexBoard b = new HexBoard(w,h, new int[] {3,4,5,4,3}, W, H);
	
	public TheGame(){
		
		setSize(W,H);
		setVisible(true);
		
	}
	
	public void paint(Graphics g){
		
		for (int i = 0; i < b.hexes.size(); i++){
			if (i%3==0) g.setColor(Color.GREEN);
			else if (i%3 ==1) g.setColor(Color.YELLOW);
			else g.setColor(Color.RED);
			
			g.fillPolygon(b.hexes.get(i));
			g.setColor(Color.BLACK);
			g.drawPolygon(b.hexes.get(i));
			
		}
	}
	
	public static void main(String[] args) {
		TheGame g = new TheGame();
	}

}
