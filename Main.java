import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This main contains the methods for all of our Canoe Rental implementations
 */

/**
 * @author Tarik Merzouk / Luis Solis Bruno / Nina Gizorpazorp
 *@version The Final Cut
 */
public class Main {

	/**
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {
		/*test models of the canoe price matix for our algorithm*/
		int[][] model1 = {{0, 2, 3, 7}, 			//solution = 5
									  {0, 0, 2, 4}, 
									  {0, 0, 0, 2},
									  {0, 0, 0, 0}};					
		
		int[][] model2 = {{0, 4, 5, 7, 8}, 		//solution = 7
									   {0, 0, 1, 2, 9},
									   {0, 0, 0, 4, 5},
									   {0, 0, 0, 0, 1} , 
									   {0, 0, 0, 0, 0}};	
		
		int[][] model3 = {{0, 7, 8, 9, 10, 12,}, 		//solution = 12
									   {0, 0, 3, 4, 7, 16}, 
									   {0, 0, 0, 3, 4, 5}, 
									   {0, 0, 0, 0, 6, 7}, 
									   {0, 0, 0, 0, 0, 5}, 
									   {0, 0, 0, 0, 0, 0}};
		
		/*Test brute force algorithm*/
		int solution = BruteForceCanoe(model1);
		System.out.println(solution);
		solution = BruteForceCanoe(model2);
		System.out.println(solution);
		solution = BruteForceCanoe(model3);
		System.out.println(solution);
		
		System.out.println();
		
		/*Test Divide and conquer algorithm*/
		solution = DivideRentals(model1, 0, model1.length - 1);
		System.out.println(solution);
		solution = DivideRentals(model2, 0, model2.length - 1);
		System.out.println(solution);
		solution = DivideRentals(model3, 0, model3.length - 1);
		System.out.println(solution);
		
		System.out.println();
		
		/*Test Dynamic algorithm*/
		solution = CanoeRental(model1);
		System.out.println(solution);
		solution = CanoeRental(model2);
		System.out.println(solution);
		solution = CanoeRental(model3);
		System.out.println(solution);

	}
	
	/**
	 * Canoe Set accepts a number of post downstream and produces
	 * Every possible combination of boats possible to get to post n.
	 * For use in Brute force algorithm.
	 * 
	 * The time and space complexity of this algorithm is 2^n, with 
	 * the basic measure for space being one set.
	 * 
	 * @param n the number of posts.
	 * @return T the set of all possible canoe paths from the first post.
	 */
	public static List<ArrayList<Integer>> CanoeSet(int n) {
		int b = n - 1;
		List<ArrayList<Integer>> T = new ArrayList<ArrayList<Integer>>();
		T.add(new ArrayList<Integer>(Arrays.asList(0)));
		
		for(int i = 1; i < b; i++) {
			int xLen = T.size() - 1;
			for(int j = 0; j <= xLen; j++) {
				ArrayList<Integer> temp = new ArrayList<Integer>(T.get(j));
				temp.add(i);
				T.add(temp);
			}
		}
		return T;
	}
	
	/**
	 * BruteForceCanoe generates every possible set of canoes
	 * selectable and finds the set with minimum cost through
	 * Exhaustion.
	 * 
	 * The time complexity for this is n^2 * 2^n, space complexity is 
	 * 2^n, because of the call to CanoeSet().
	 * 
	 * @param A	The 2d array of costs per post.
	 * @return	toReturn the minimum cost to get to post n.
	 */
	public static int BruteForceCanoe(int A[][]) {
		int toReturn = A[0][A.length - 1] ;			//get the min cost with just boat 0 - to post n
		ArrayList<Integer> solutionPath = new ArrayList<Integer>();				//list for storing solution path
		solutionPath.add(1);				//set the inital solution to boat 1 only
		
		List<ArrayList<Integer>> possPaths	= CanoeSet(A.length);		//get all the possible paths 
		int tempCost;
		
		for(int i = 0; i < possPaths.size();  i++) {
			tempCost = 0;
			ArrayList<Integer> path = possPaths.get(i);
			for(int j = 0; j < path.size() - 1; j++) {
				tempCost += A[path.get(j)][path.get(j + 1)];
			}
			
			tempCost += A[path.get(path.size() - 1)][A.length - 1];			//add the last trip from our last boat in path to the end
			
			if (tempCost < toReturn) {		//	update the minimum cost
				toReturn = tempCost;
				solutionPath = possPaths.get(i);
				
			}
		}
		//System.out.println("Solution Path = " + solutionPath);
		return toReturn;
	}
	
	/**
	 * DivideRentals is the divide and conquer solution for the
	 * Canoe Rental problem.
	 * 
	 * 
	 * 
	 * @param A the cost array for this problem.
	 * @param n	the post we are at.
	 * @param m the post we are going to.
	 * @return	 The cheapest cost from n to m.
	 */
	public static int DivideRentals(int A[][], int n, int m){
		if (n==m)
			return 0;
		ArrayList<Integer> paths = new ArrayList<Integer>();
		for(int b =n+1; b <=m; b++)
			paths.add(A[n][b]+DivideRentals(A,b,m));
		
		int minVal = Collections.min(paths);
		System.out.println(paths);
		//int path = paths.indexOf(minVal);
 		return minVal;
	}
	
	
	/**
	 * CanoeRental() is the dynamic solution for the Canoe Rental problem.
	 * it sets the best solution to get to every post with canoe i, then calculates 
	 * the best solution for canoe i + 1 to n based on these solutions.
	 * 
	 * The space and time complexity for this algorithm is n^2, with n being
	 * the number of posts downstream.
	 * 
	 * @param A the cost matrix for the canoe rental problem
	 * @return the minimum cost.
	 */
	public static int CanoeRental(int A[][]) {
		/*the solutions matrix*/
		int[][] T = new int[A.length][A[0].length];
		
		/*Let the first row of T be the first row of A[][]*/
		for(int i = 0; i < A.length; i++) {
			T[0][i] = A[0][i];
		}
		
		/*Dynamically incur solution into T*/
		for(int i = 1; i < A.length; i++) {
			for(int j = 0; j < A.length; j++) {
				if (j <= i) {
					T[i][j] = T[i - 1][j];
				} else {
					T[i][j] = Math.min(T[i - 1][j], T[i - 1][i] + A[i][j]);
				}
			}
		}
		return T[T.length - 1][T.length - 1];
	}
	
	
	/**
	 * PsudoRandom Generator 
	 */

}
