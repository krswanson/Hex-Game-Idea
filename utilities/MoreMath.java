package utilities;

public class MoreMath {

	public static double average(int[] set){
		double sum = 0;
		for (int i = 0; i < set.length; i++) sum+=set[i];
		return sum/set.length;
	}
}
