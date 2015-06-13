package game;

import java.awt.Polygon;
import java.util.HashSet;

public class LinkedTile extends GameTile {

	protected HashSet<LinkedTile> adjacent;
	
	public LinkedTile(Polygon tileShape){
		super(tileShape);
		adjacent = new HashSet<LinkedTile>();
	}
	
	/**
	 * 
	 * @param tileShape The Polygon representing this tile
	 * @param adjacentTiles The set of tiles that are to be marked as adjacent to this tile
	 */
	public LinkedTile(Polygon tileShape, HashSet<LinkedTile> adjacentTiles){
		super(tileShape);
		adjacent.addAll(adjacentTiles);
	}
	
	public void addAdjacent(LinkedTile tile){
		adjacent.add(tile);
	}
	
	public boolean removeAdjacent(LinkedTile tile){
		return adjacent.remove(tile);
	}
	
	public HashSet<LinkedTile> getAdjacent(){
		return adjacent;
	}
	
}
