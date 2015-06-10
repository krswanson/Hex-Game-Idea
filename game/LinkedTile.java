package game;

import java.awt.Polygon;
import java.util.HashSet;

public class LinkedTile extends GameTile {

	protected HashSet<GameTile> adjacent;
	
	public LinkedTile(Polygon tileShape){
		super(tileShape);
		adjacent = new HashSet<GameTile>();
	}
	
	/**
	 * 
	 * @param tileShape The Polygon representing this tile
	 * @param adjacentTiles The set of tiles that are to be marked as adjacent to this tile
	 */
	public LinkedTile(Polygon tileShape, HashSet<GameTile> adjacentTiles){
		super(tileShape);
		adjacent.addAll(adjacentTiles);
	}
	
	public void addAdjacent(GameTile tile){
		adjacent.add(tile);
	}
	
	public boolean removeAdjacent(GameTile tile){
		return adjacent.remove(tile);
	}
	
	public HashSet<GameTile> getAdjacent(){
		return adjacent;
	}
	
}
