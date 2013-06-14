package game;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;
import utilities.*;


@SuppressWarnings("serial")
public class TheGame extends Frame {

	protected static int W = 500, H = 250;
	public static HexBoard board = new HexBoard(new int[] {2,3,3,3,4,5,4,3,3,3,2}, W, H);
	protected Collection<GameTile> chips = new HashSet<GameTile>(board.size()); 
	
	public TheGame(){
		Iterator<GameTile> tiles = board.iterator();
		Polygon cir = new Circlegon(5,120);
		while (tiles.hasNext()){
			Polygon p = tiles.next().getShape();
			chips.add( new GameTile( new Polygon( 
					ArrayHelp.shiftAll(cir.xpoints,(int) MoreMath.average(p.xpoints)), 
					ArrayHelp.shiftAll(cir.ypoints, (int) MoreMath.average(p.ypoints)), 
					cir.npoints)));
		}
		for (GameTile chip : chips){
			chip.setColor(Color.red);
			chip.setLayer(1);
		}
		setSize(W,H);
		setVisible(true);
		MyMouseAdapter mouse = new MyMouseAdapter();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
	}
	
	public void paint(Graphics g){
		PriorityQueue<GamePiece> pieces = new 
				PriorityQueue<GamePiece>(board.size(), new LayerComparator());
		Iterator<GameTile> hexes = board.iterator();
		while (hexes.hasNext()){
			pieces.add(hexes.next());
		}
		pieces.addAll(chips);
		/*
		g.setColor(t.color);
		g.fillPolygon(t.getShape());
		g.setColor(Color.BLACK);
		g.drawPolygon(t.getShape());
		Iterator<GameTile> hexes = b.iterator();
		*/
		//int i = 0;
		
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
	
	public class MyMouseAdapter extends MouseAdapter {

		long pressTime, releaseTime;  	// system clock time when mouse was pressed and released
		int x, y, i = 0;	// counter for mouseDragged events
		
		/**
		 * Prints the x and y position of the mouse click to the console
		 */
		public void mouseClicked(MouseEvent e) {
			x = e.getX();
			y = e.getY();
			GameTile t = TheGame.board.getAt(x,y);
			if (t != null){
				t.setColor(Color.YELLOW);
				repaint();
			}
		}
		
		/**
		 * Sets pressTime to the current system time when the mouse is pressed
		 */
		public void mousePressed(MouseEvent e) {
			pressTime = System.currentTimeMillis();
		}
		
		/**
		 * Sets the releaseTime to the current system time when the mouse is released, and
		 * prints to the console the length of time the mouse was pressed
		 */
		public void mouseReleased(MouseEvent e) {
			releaseTime = System.currentTimeMillis();
			//System.out.println("Mouse pressed for " + (releaseTime - pressTime) + " milliseconds");
		}
		
		/**
		 * Sets the x and y position of the mouse using a counter for every event, so
		 * that not every event updates the coordinates (too often) but rather every third event
		 * updates the coordinates
		 */
	/**	public void mouseDragged(MouseEvent e) {
			Xposn = e.getX();
			Yposn = e.getY();
			i++;
			if (i % 3 == 0) {repaint();}
		}
	}*/
	
	/**
	 * Customized paint method: gives instructions to the user, then
	 * prints the current location of the mouse into the window
	 * at the mouse location
	 */
	
}
	public static void main(String[] args) {
		TheGame g = new TheGame();
	}

}
