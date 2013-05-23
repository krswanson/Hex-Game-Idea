import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.Iterator;
import java.util.PriorityQueue;



@SuppressWarnings("serial")
public class TheGame extends Frame {

	protected int W = 500, H = 250;
	protected HexBoard b = new HexBoard(new int[] {2,3,3,3,4,5,4,3,3,3,2}, W, H);
	protected GameTile t = new GameTile(new Polygon(new int[] {220,330,110}, new int[] {110,220,220}, 3));
	public TheGame(){
		
		setSize(W,H);
		setVisible(true);
		t.setLayer(1);
		
	}
	
	public void paint(Graphics g){
		PriorityQueue<GamePiece> pieces = new 
				PriorityQueue<GamePiece>(b.size(), new LayerComparator());
		Iterator<GameTile> hexes = b.iterator();
		while (hexes.hasNext()){
			pieces.add(hexes.next());
		}
		pieces.add(t);
		/*
		g.setColor(t.color);
		g.fillPolygon(t.getShape());
		g.setColor(Color.BLACK);
		g.drawPolygon(t.getShape());
		Iterator<GameTile> hexes = b.iterator();
		*/
		//int i = 0;
		t.color = Color.blue;
		while (!pieces.isEmpty()){
			//if (i%3==0) g.setColor(Color.GREEN);
			//else if (i%3 ==1) g.setColor(Color.YELLOW);
			//else g.setColor(Color.RED);
			GamePiece current = pieces.poll();
			Polygon p = current.getShape();
			g.setColor(current.getColor());
			g.fillPolygon(p);
			g.setColor(Color.BLACK);
			g.drawPolygon(p);
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
