package game;

public class Driver {

	public static void main(String[] args) {
		int W = 750, H = 720;
		int option = 0;
		int rows = 16, cols = 16;
		int[] arr = new int[cols];
		TheGame g;
		if (option == 0){
			for (int i = 0; i < cols; i++) {
				arr[i] = rows;
			}
			g = new TheGame(new HexBoard(arr, W/6, H/16), W, H);
		}else{
			H = 600;
			g = new TheGame(new SquareBoard(new int[] {20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20},
					W/8, H/10, 20, 20), W, H);
		}
		g.start();
	}

}
