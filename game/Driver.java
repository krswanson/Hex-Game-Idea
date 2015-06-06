package game;

public class Driver {

	public static void main(String[] args) {
		int W = 600, H = 400;
		int option = 0;
		TheGame g;
		if (option == 0){
			g = new TheGame(new HexBoard(new int[] {6,6,6,6,6,6,6,6,6,6,6,6}, W/6, H/6), W, H);
		}else{
			H = 600;
			g = new TheGame(new SquareBoard(new int[] {20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20},
					W/8, H/10, 20, 20), W, H);
		}
		g.start();
	}

}
