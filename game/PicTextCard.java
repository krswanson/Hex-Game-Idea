package game;

import java.awt.Color;
import java.awt.Polygon;
import java.util.HashSet;

public class PicTextCard {

	private GameTile cardRect, picRect;
	String text;
	
	public PicTextCard(int h, int w, int centerX, int centerY, String text){
		picRect = new GameTile(new Polygon(new int[]
				{centerX-w/2+w/8, centerX+w/2-w/8, centerX+w/2-w/8, centerX-w/2+w/8},
				new int[] {centerY-h/2+h/8, centerY-h/2+h/8, centerY, centerY}, 4));
		cardRect = new GameTile(new Polygon(new int[] {centerX-w/2, centerX+w/2, centerX+w/2, centerX-w/2},
				new int[] {centerY-h/2, centerY-h/2, centerY+h/2, centerY+h/2}, 4));
		cardRect.setLayer(1);
		picRect.setLayer(2);
		this.text = text;
	}
	
	
	public void setType(String text, Color picture){
		picRect.setColor(picture);
		cardRect.setText(text);
	}
	
	public Color getColor(){
		return picRect.color;
	}
	
	public void setColor(Color picture){
		picRect.setColor(picture);
	}
	
	public Color getBackgroundColor(){
		return cardRect.getColor();
	}
	
	public void setBackgroundColor(Color background){
		cardRect.setColor(background);
	}

	public String getText() {
		return text;
	}
	
	public void setText(String theText) {
		text = theText;
	}
	
	public int getLayer() {
		return cardRect.getLayer();
	}

	public void setLayer(int layer) {
		cardRect.setLayer(layer);
		picRect.setLayer(layer + 1);

	}

	public HashSet<GameTile> getParts(){
		HashSet<GameTile> p = new HashSet<GameTile>();
		p.add(picRect);
		p.add(cardRect);
		return p;
	}
}
