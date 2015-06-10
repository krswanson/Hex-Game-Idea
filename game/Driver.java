package game;

public class Driver {

	public static void main(String[] args) {
		int W = 980, H = 720;
		int option = 0;
		int rows = 16, cols = 24;
		int[] arr = new int[cols];
		TheGame g;
		if (option == 0){
			for (int i = 0; i < cols; i++) {
				arr[i] = rows;
			}
			g = new TheGame(new HexBoard(arr, 120, 20), W, H);
		}else{
			H = 600;
			W = 800;
			for (int i = 0; i < cols; i++) {
				arr[i] = rows;
			}
			g = new TheGame(new RectBoard(arr, W/8, H/10, 25, 25), W, H);
		}
		g.start();
	}

}
