package game;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import utilities.*;


@SuppressWarnings("serial")
public class TheGame extends Frame {

	protected int nPlayers = 2, nSettlements = 16;
	protected static int W, H;
	protected static int CARD_OFFSET = 60, CARD_H = 70, CARD_W = 50;
	public static TileBoard board;
	protected ConcurrentLinkedQueue<ConcurrentLinkedQueue<GameTile>> thePlayers = 
			new ConcurrentLinkedQueue<ConcurrentLinkedQueue<GameTile>>();
	protected ConcurrentLinkedQueue<PicTextCard> terrainCards = 
			new ConcurrentLinkedQueue<PicTextCard>();
	protected TileTypeTable<String, Color> terrain = new TileTypeTable<String, Color>();
	protected GameTile playerTurnMarker;
	protected boolean firstTurn;
	
	public TheGame(TileBoard theBoard, int windowW, int windowH){
		board = theBoard;
		H = windowH;
		W = windowW;
		terrain.put("Desert", Color.yellow);
		terrain.put("Pasture", Color.green);
		terrain.put("Flowers", Color.pink);
		terrain.put("Forest", new Color(10,150,20));
		terrain.put("Canyon", new Color(180,130,30));
		Set<String> temp = terrain.keySet();
		for (String t : temp){
			terrain.putFlag(t, "card", true);
		}
		terrain.put("Water", new Color(140, 165, 255));
		terrain.putFlag("Water", "card", false);
		terrain.put("Mountain", new Color(100,100,80));
		terrain.putFlag("Mountain", "card", false);
		
		reset();
	}
	
	public void start(){
		setSize(W,H);
		setVisible(true);
		MyMouseAdapter mouse = new MyMouseAdapter();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);	
		
		// Seem to need both this and processWindowEvent() for it to exit when the X is clicked 
		// Source: http://java-demos.blogspot.com/2012/10/close-awt-frame-in-java-using-windowlistener.html
		addWindowListener(new WindowAdapter() {
			@SuppressWarnings("unused")
			public void WindowClosing(WindowEvent e) {
				dispose();
				System.exit(0);
			}
		});
	}
	
	// End program when X button is clicked
	// Source: https://www.clear.rice.edu/comp310/JavaResources/frame_close.html
    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if(e.getID() == WindowEvent.WINDOW_CLOSING) {
            System.exit(0);
        }
    }
	    
	public void reset(){
		setupTerrain();
		setupPlayers();
	}
	
	protected void setupPlayers(){
		for (int j = 0; j < nPlayers; j++){
			firstTurn = true;
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
			Circlegon circle = new Circlegon(CARD_W, W/2, H - CARD_OFFSET);
			playerTurnMarker = new GameTile(circle);	
		}
		
	}
	
	protected void setupTerrain(){
		Object[] types = terrain.keySet().toArray();
		for (int i = 0; i < types.length; i++){
			if (terrain.getFlag((String) types[i], "card") == 1){
				PicTextCard card = new PicTextCard(CARD_H, CARD_W, W/2, H - CARD_OFFSET, (String) types[i]);
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
	
	/**
	 * Draws the board tiles, the players' settlements, the current card, and the 
	 * marker for whose turn it is.  It does this by putting each item in a queue, then
	 * drawing each item with its stored color and a black border.
	 */
	public void paint(Graphics g){//need to get cards and their text to appear
		PriorityQueue<GamePiece> pieces = new 
				PriorityQueue<GamePiece>(board.size(), new LayerComparator());
		Iterator<GameTile> hexes = board.iterator();
		while (hexes.hasNext()){
			pieces.add(hexes.next());
		}
		for (int i = 0; i < nPlayers; i++){
			ConcurrentLinkedQueue<GameTile> temp = thePlayers.poll();
			if (i == 0) { // Get current player color
				playerTurnMarker.setColor(temp.peek().getColor());
				pieces.add(playerTurnMarker);
			}
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
		 * If the clicked tile is unused, their are still pieces to be played, and the clicked hex
		 * matches the current card, a settlement of the current player is placed on the clicked hex.
		 */
		public void mouseClicked(MouseEvent e) {
			x = e.getX();
			y = e.getY();
			int col = board.getCol(x);
			int row = board.getRow(col, y);
			GameTile hex = TheGame.board.get(row, col);
			if (isLegalMove(hex, row, col)){
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
							if (firstTurn) updateFirstTurn();
						}else{}//Exception for a special power
					}
				}
			}
		
		/**
		 * Checks whether there was a switch from the first turn to the second turn (each player gets a first turn).
		 * Updates the firstTurn marker to false if it is no longer the first turn.
		 * @return false if nothing changed, true if firstTurn was changed from true to false
		 */
		public boolean updateFirstTurn() {
			if (!firstTurn) return false;
			int playedCount = 0;
			GameTile s;
			for (int i = 0; i < thePlayers.peek().size(); i++) {
				s = thePlayers.peek().poll();
				if (s.used) playedCount++;
				thePlayers.peek().add(s);
			}
			if (playedCount >= 1) {
				firstTurn = false;
				return true;
			}
			return false;
		}
		
		private boolean isLegalMove(GameTile hex, int row, int col) {
			if (hex != null && firstTurn) {
				return true;
			} else if (!hex.used) {
				Set<GameTile> adjacent = board.getAdjacent(row, col);
				if (adjacent == null) return true; //TODO actually check!
			}
			return false;
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
	
}
