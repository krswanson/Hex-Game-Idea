import java.util.Comparator;

public class LayerComparator implements Comparator<GamePiece> {

	@Override
	public int compare(GamePiece arg0, GamePiece arg1) {
		if (arg0.getLayer() == arg1.getLayer()) return 0;
		else if (arg0.getLayer() < arg1.getLayer()) return -1;
		else return 1;
	}

}
