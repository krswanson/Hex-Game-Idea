package game;
import java.awt.Polygon;


public class Circlegon extends Polygon {

	private static final long serialVersionUID = 1L;

	public Circlegon() {
		super(calCircleX(100, Math.PI/180), calCircleY(100, Math.PI/180), 360);
	}

	public Circlegon(int radius) {
		super(calCircleX(radius, Math.PI/180), calCircleY(radius, Math.PI/180), 360);
	}
	
	public Circlegon(int radius, int shiftX,int shiftY) {
		super(calCircleX(radius, Math.PI/180), calCircleY(radius, Math.PI/180), 360);
		this.translate(shiftX, shiftY);
	}
	
	/**
	 * 
	 * @param radius the radius of the circle
	 * @param shiftX how far to shift the cirlce to the left
	 * @param shiftY how far to shift the circle down
	 * @param npoints how many "sides" the circle will have (how finely grained it looks),
	 * so higher numbers look more circular (default in other constructors is 180)
	 */
	public Circlegon(int radius, int shiftX, int shiftY, int npoints) {
		super(calCircleX(radius, 2*Math.PI/npoints), calCircleY(radius, 
				2*Math.PI/npoints), npoints);
		this.translate(shiftX, shiftY);
	}
	
	/**
	 * 
	 * @param r radius of the circle
	 * @param deltaRadians number of radians to move along a circle of 2*PI radians to
	 * calculate each point
	 * @return the x positions around the circle
	 */
	public static int[] calCircleX(int r, double deltaRadians){
		int index = 0;
		int[] points = new int[(int) (2*Math.PI/deltaRadians+1)];
		for (double i = 0; i < 2*Math.PI; i+=deltaRadians){
			points[index] = (int) (Math.cos(i)*r);
			index++;
		}
		return points;
	}
	
	/**
	 * 
	 * @param r radius of the circle
	 * @param deltaRadians number of radians to move along a circle of 2*PI radians to
	 * calculate each point
	 * @return the y positions around the circle
	 */
	public static int[] calCircleY(int r, double deltaRadians){
		int index = 0;
		int[] points = new int[(int) (2*Math.PI/deltaRadians+1)];
		for (double i = 0; i < 2*Math.PI; i+=deltaRadians){
			points[index] = (int) (Math.sin(i)*r);
			index++;
		}
		return points;
	}

	public static void main(String[] args){
		Polygon p = new Circlegon(100, 0, 0, 50);
		for (int i = 0; i < p.npoints; i++){
			System.out.println(i + " " + p.xpoints[i] + " " + p.ypoints[i]);
		}
	}

}
