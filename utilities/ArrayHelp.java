package utilities;

public class ArrayHelp {

	public static int[] shiftAll(int[] toShift, int shift){
		int[] shifted = new int[toShift.length];
		for (int i = 0; i < toShift.length; i++){
			shifted[i] = toShift[i]+shift;
		}
		return shifted;
	}
	
	public static boolean equals(double[] a, double[] b){
		if (a.length != b.length) return false;
		else{
			for (int i = 0; i < a.length; i++){
				if (a[i] != b[i]) return false;
			}
			return true;
		}
	}
	
	public static boolean equals(int[] a, int[] b){
		if (a.length != b.length) return false;
		else{
			for (int i = 0; i < a.length; i++){
				if (a[i] != b[i]) return false;
			}
			return true;
		}
	}
}
