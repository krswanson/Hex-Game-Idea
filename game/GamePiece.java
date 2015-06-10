package game;
import java.awt.Color;
import java.awt.Polygon;


public interface GamePiece {

	public Color getColor();
	
	/**
	 * @return the layer this piece should be draw in.  
	 */
	
	public int getLayer();
	
	/**
	 * @param layer the layer this piece will be set to.  Layers start at 0 (the first
	 * to be drawn, the back layer) and higher layers will have increasingly higher 
	 * numbers.
	 */
	public void setLayer(int layer);

	/**
	 * @return The Polygon that outlines this piece
	 */
	public Polygon getShape();
	
	/**
	 * @return The text associated with or on this piece
	 */
	public String getText();
}
