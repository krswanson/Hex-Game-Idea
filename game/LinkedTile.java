package game;

import java.awt.Color;
import java.awt.Polygon;
import java.util.HashSet;

public class LinkedTile implements GamePiece {

	protected Color color = Color.WHITE;
	protected int layer = 0;
	protected Polygon shape;
	protected HashSet<LinkedTile> adjacent;
	
	public LinkedTile(){
		adjacent = new HashSet<LinkedTile>();
	}
	
	public LinkedTile(Polygon tileShape, HashSet<LinkedTile> adjacentTiles){
		shape = tileShape;
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
	@Override
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color c){
		color = c;
	}

	@Override
	public int getLayer() {
		return layer;
	}

	@Override
	public void setLayer(int layer) {
		this.layer = layer;
	}

	@Override
	public Polygon getShape() {
		return shape;
	}

	public void setShape(Polygon p){
		shape = p;
	}

	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}
}
