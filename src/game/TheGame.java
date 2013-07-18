package game;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import utilities.*;


@SuppressWarnings("serial")
public class TheGame extends Frame {

	protected int nPlayers = 2, nSettlements = 16;
	protected static int W, H;
	public static TileBoard board;
	protected ConcurrentLinkedQueue<ConcurrentLinkedQueue<GameTile>> thePlayers = 
			new ConcurrentLinkedQueue<ConcurrentLinkedQueue<GameTile>>();
	protected ConcurrentLinkedQueue<PicTextCard> terrainCards = 
			new ConcurrentLinkedQueue<PicTextCard>();
	protected TileTypeTable<String, Color> terrain = new TileTypeTable<String, Color>();
	
	public TheGame(TileBoard theBoard, int windowW, int windowH){
		board = theBoard;
		H = windowH;
		W = windowW;
		terrain.put("Desert", Color.yellow);
		terrain.put("Pasture", Color.green);
		terrain.put("Flowers", Color.pink);
		terrain.put("Forest", new Color(10,150,20));
		terrain.put("Canyon", new Color(150,100,30));
		Set<String> temp = terrain.keySet();
		for (String t : temp){
			terrain.putFlag(t, "card", true);
		}
		terrain.put("Water", new Color(120, 140, 245));
		terrain.putFlag("Water", "card", false);
		terrain.put("Mountain", new Color(140,130,120));
		terrain.putFlag("Mountain", "card", false);
		
		reset();
	}
	
	public void start(){
		setSize(W,H);
		setVisible(true);
		MyMouseAdapter mouse = new MyMouseAdapter();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
	}
	
	public void reset(){
		setupTerrain();
		setupPlayers();
	}
	
	protected void setupPlayers(){
		for (int j = 0; j < nPlayers; j++){
			ConcurrentLinkedQueue<GameTile> player = new ConcurrentLinkedQueue<GameTile>();
			Color c = Color.blue;
			int shift = 0;
			if (j == 1){
				c = Color.red;
				shift = W-90;
			}
			for (int i = 0; i < nSettlements; i++){
				Polygon settlement = new Polygon(new int[] {20,30,40,40,20},
					new int[] {50,40,50,60,60}, 5);
				settlement.translate((i/(nSettlements/2))*25 + shift, (i%(nSettlements/2))*25);
				GameTile s = new GameTile(settlement);
				s.setColor(c);
				s.setLayer(2);
				player.add(s);	
			}
			thePlayers.add(player);
		}
		
	}
	protected void setupTerrain(){
		Object[] types = terrain.keySet().toArray();
		for (int i = 0; i < types.length; i++){
			if (terrain.getFlag((String) types[i], "card") == 1){
				PicTextCard card = new PicTextCard(70,50, W/2, H-50);
				card.setType((String) types[i], terrain.get(types[i]));
				card.setBackgroundColor(Color.gray);
				terrainCards.add(card);
			}
			Iterator<GameTile> b = board.iterator();
			while (b.hasNext()){
				Color c = terrain.get(types[ (int) (pickTerrain()*types.length) ]);
				b.next().setColor(c);
			}
		}
		
	}
	
	public double pickTerrain(){
		return Math.random();
	}
	
	public void paint(Graphics g){//need to get cards and their text to appear
		PriorityQueue<GamePiece> pieces = new 
				PriorityQueue<GamePiece>(board.size(), new LayerComparator());
		Iterator<GameTile> hexes = board.iterator();
		while (hexes.hasNext()){
			pieces.add(hexes.next());
		}
		for (int i = 0; i < nPlayers; i++){
			ConcurrentLinkedQueue<GameTile> temp = thePlayers.poll();
			pieces.addAll(new ConcurrentLinkedQueue<GameTile>(temp));
			thePlayers.add(temp);
		}
		pieces.addAll(terrainCards.peek().getParts());
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
				if (hex.getColor().equals(terrainCards.peek().getColor())){
					GameTile settlement = thePlayers.peek().poll();
						if (!settlement.used){
							settlement.shape.translate((int) MoreMath.average(hex.shape.xpoints) - (int) MoreMath.average(settlement.shape.xpoints),
									(int) MoreMath.average(hex.shape.ypoints) - (int) MoreMath.average(settlement.shape.ypoints));
							thePlayers.peek().add(settlement);
							hex.used = true;
							settlement.used = true;
							terrainCards.add(terrainCards.poll());
							repaint();		
							thePlayers.add(thePlayers.poll());
						}else{}//Exception for a special power
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
	}

}
