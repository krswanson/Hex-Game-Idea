import java.awt.Color;
import java.awt.Polygon;


public class GameTile {

	protected Polygon shape;
	protected Color color = Color.white;
	protected String typeName = "";
	
	public GameTile(Polygon theShape){
		this(theShape, theShape.xpoints[0], theShape.ypoints[0]);
	}
	
	public GameTile(Polygon theShape, int xFirst, int yFirst){
		shape = theShape;
		shape.xpoints = ArrayHelp.shiftAll(shape.xpoints, xFirst - shape.xpoints[0]);
		shape.ypoints = ArrayHelp.shiftAll(shape.ypoints, yFirst - shape.ypoints[0]);	
	}
	
	public GameTile(GameTile tile){
		this.shape = new Polygon(ArrayHelp.shiftAll(tile.shape.xpoints, 0), 
				ArrayHelp.shiftAll(tile.shape.ypoints, 0), tile.shape.npoints);
		setName(tile.getName());
		setColor(tile.getColor());
	}
	
	public boolean equals(GameTile other){
		return this.shape.equals(other.shape) && sameType(other);
	}
	
	public boolean sameShape(GameTile other){
		if (this.shape.npoints != other.shape.npoints) return false;
		else {
			int[] thisShiftX = ArrayHelp.shiftAll(this.shape.xpoints, -this.shape.xpoints[0]);
			int[] thisShiftY = ArrayHelp.shiftAll(this.shape.ypoints, -this.shape.ypoints[0]);
			int[] otherShiftX = ArrayHelp.shiftAll(other.shape.xpoints, -other.shape.xpoints[0]);
			int[] otherShiftY = ArrayHelp.shiftAll(other.shape.ypoints, -other.shape.ypoints[0]);
			return ArrayHelp.equals(thisShiftX, otherShiftX) &&
				ArrayHelp.equals(thisShiftY, otherShiftY);
		}
	}
	
	public boolean sameType(GameTile other){
		return this.color.equals(other.getColor()) && 
			this.typeName.compareTo(other.getName()) == 0 &&
			sameShape(other);
	}
	
	public Color getColor(){
		return color;
	}
	
	public void setColor(Color c){
		color = c;
	}
	
	public String getName(){
		return typeName;
	}
	
	public void setName(String tileType){
		typeName = tileType;
	}
	
	public static void main(String[] a){
		GameTile t = new GameTile(new Polygon(new int[] {1,2,0}, new int[] {0,1,1}, 3),4, 4);
		GameTile u = new GameTile(new Polygon(new int[] {1,2,0}, new int[] {0,1,1}, 3),3, 4);
		t.setName("red");
		u.setName("red");
		GameTile v = new GameTile(u);
		v.shape.xpoints = ArrayHelp.shiftAll(v.shape.xpoints,2);
		System.out.println(u.equals(t));
		System.out.println(u.sameType(t));
		System.out.println(u.equals(v));
	}
}
