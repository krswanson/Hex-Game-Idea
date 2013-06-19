package game;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import utilities.*;


@SuppressWarnings("serial")
public class TheGame extends Frame {

	protected static int W = 600, H = 400;
	public static HexBoard board = new HexBoard(new int[] {2,3,3,3,4,5,4,3,3,3,2}, W, H);
	protected ConcurrentLinkedQueue<GameTile> player = 
			new ConcurrentLinkedQueue<GameTile>();
	public TheGame(){
		
		int[] sx = {20,30,40,40,20};
		int[] sy = {50,40,50,60,60};
		for (int i = 0; i < 20; i++){
			Polygon settlement = new Polygon(sx, sy, 5);
			settlement.translate((i/10)*25,(i%10)*25);
			GameTile s = new GameTile(settlement);
			s.setColor(Color.BLUE);
			s.setLayer(2);
			player.add(s);	
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
		pieces.addAll(player);
		
		while (!pieces.isEmpty()){
			GamePiece current = pieces.poll();
			Polygon p = current.getShape();
			g.setColor(current.getColor());
			g.fillPolygon(p);
			g.setColor(Color.BLACK);
			g.drawPolygon(p);
		}
	}
	
	protected class MyMouseAdapter extends MouseAdapter {

		long pressTime, releaseTime;  	// system clock time when mouse was pressed and released
		int x, y, i = 0;	// counter for mouseDragged events
		
		/**
		 * Prints the x and y position of the mouse click to the console
		 */
		public void mouseClicked(MouseEvent e) {
			x = e.getX();
			y = e.getY();
			GameTile hex = TheGame.board.getAt(x,y);
			if (hex != null && !hex.used){
				GameTile settlement = player.poll();
					if (!settlement.used){
						settlement.shape.translate((int) MoreMath.average(hex.shape.xpoints) - (int) MoreMath.average(settlement.shape.xpoints),
								(int) MoreMath.average(hex.shape.ypoints) - (int) MoreMath.average(settlement.shape.ypoints));
						player.add(settlement);
						hex.used = true;
						settlement.used = true;
						repaint();		
					}else{
						System.out.println("Out of settlements.  The game is finished.");
					}
				
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
		}
	}
	
	public static void main(String[] args) {
		TheGame g = new TheGame();
	}

}
