import java.util.ArrayList;

/**
 * 
 */

/**
 * @author luiss3
 *
 */
public class MinCostAndPath {
	
	int minCost;
	ArrayList<Integer> solution = new ArrayList<Integer>();
	
	
	public MinCostAndPath(final int cost, final int lastChoice){
		solution.add(lastChoice);
		minCost = cost;
	}
	public String toString() {
		
		return String.format("%d ", minCost) + solution.toString();
	}

}
