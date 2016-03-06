
import java.util.ArrayList;
import java.util.Collections;

	
public class Canoes {
	

	public static void main(String[] args) {
		final int[][] a ={ { 0, 2, 3, 7}, { -1, 0, 2, 4}, { -1, -1, -0, 2}, { -1, -1, -1, 0}}; 
		//{ { 0, 2, 3, 7, 14 }, { -1, 0, 2, 4, 8 }, { -1, -1, -0, 2, 4 }, { -1, -1, -1, 0, 2 } };
		System.out.println(canoeRentalDivide(a, 0, 3));
	}

	public static MinCostAndPath canoeRentalDivide(final int a[][], final int n, final int m) {
		MinCostAndPath last = new MinCostAndPath(0, n);
		if (n == m) {
 			return last;
		}
		MinCostAndPath tempObject;
		ArrayList<Integer> paths = new ArrayList<Integer>();
		ArrayList<MinCostAndPath> possSol = new ArrayList<MinCostAndPath>();
		for (int b = n + 1; b <= m; b++) {
			tempObject = canoeRentalDivide(a, b, m);
			possSol.add(tempObject);
			paths.add(a[n][b] + tempObject.minCost);
		}
		final int minFutureCost = Collections.min(paths);
		final int loc = paths.indexOf(minFutureCost);

		last.minCost = minFutureCost;
		last.solution.addAll(possSol.get(loc).solution);
		return last;
	}
		
}
