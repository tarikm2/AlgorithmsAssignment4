

public final class FieldAndStones {
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		final boolean S = true; //STONE
		final boolean F	= false; //FIELD: no stone
		
		final boolean field[][] = {	{F,F,F,F,F,F,F,F},
								   	{F,F,S,F,F,F,F,F},
								   	{F,F,F,F,F,F,S,F},
								   	{F,F,F,F,F,F,F,F},
								   	{F,F,F,F,F,S,F,F},
								   	{F,F,F,F,F,F,F,F},
								   	{F,F,F,F,F,F,F,F},
								   	{F,F,F,S,F,F,F,F}};
	
		System.out.println(DyanmicFieldStones(field));
	}
	/**
	 * 
	 * @param theField
	 * @return
	 */
	private static SquareSolution DyanmicFieldStones(final boolean[][] theField) {
		final int fieldSize = theField.length;
		int maxSqrLen =  0;
		int cornerX = 0;
		int cornerY = 0;

		final int[][] biggestSquareAt = new int[fieldSize][fieldSize];
		for(int i = 0; i < theField.length; i++) {
			//fill last row and last column with 0 if stone at the spot. 1 otherwise.
			
			if(theField[fieldSize-1][i]) {
				biggestSquareAt[fieldSize-1][i] = 0;
			} else{
				cornerX = i;
				cornerY = fieldSize-1;
				maxSqrLen = biggestSquareAt[cornerY][cornerX] = 1;
			}
			
			if(theField[i][fieldSize-1]) {
				biggestSquareAt[i][fieldSize-1] = 0;
			} else{
				cornerX = fieldSize-1;
				cornerY = i;
				maxSqrLen = biggestSquareAt[cornerY][cornerX] = 1;
			}
		}
		for(int i = fieldSize-2; i > -1; i--) {
			for(int j = fieldSize - 2; j > -1; j--) {
				if(theField[i][j]){
					biggestSquareAt[i][j] = 0;
				} else{
				biggestSquareAt[i][j] = 1 + Math.min(Math.min(biggestSquareAt[i+1][j],
															  biggestSquareAt[i][j+1]), 
													 biggestSquareAt[i+1][j+1]);
				}
				if(biggestSquareAt[i][j] > maxSqrLen){
					maxSqrLen = biggestSquareAt[i][j];
					cornerX = j;
					cornerY = i;
				}
			}
		}
		for(int i = 0;i<biggestSquareAt.length; i++ ){
			for(int j = 0;j<biggestSquareAt.length; j++ ){
				System.out.printf("%5d",biggestSquareAt[i][j]);
			}
			System.out.println();
		}
		final int x = cornerX;
		final int y = cornerY;
		final int size = maxSqrLen;
		return  new SquareSolution(){
			@Override
			public int getSize() { return size;}
			@Override
			public int getX() {	return x;}
			@Override
			public int getY() {return y;}
			@Override
			public String toString(){
				return String.format("Size: %d  Point: (%d, %d)", size,x,y);
			}
		};
	}
}
