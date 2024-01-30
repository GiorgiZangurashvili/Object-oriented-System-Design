// HW1 2-d array Problems
// CharGrid encapsulates a 2-d grid of chars and supports
// a few operations on the grid.

public class CharGrid {
	private char[][] grid;

	/**
	 * Constructs a new CharGrid with the given grid.
	 * Does not make a copy.
	 * @param grid
	 */
	public CharGrid(char[][] grid) {
		this.grid = grid;
	}
	
	/**
	 * Returns the area for the given char in the grid. (see handout).
	 * @param ch char to look for
	 * @return area for given char
	 */
	public int charArea(char ch) {
		if(grid.length == 0) return 0;

		int num_char = evalNumChar(ch, 0, 0, this.grid.length, this.grid[0].length);

		if(num_char == 0) return 0;

		int upper_y = Integer.MAX_VALUE;
		int down_y = Integer.MIN_VALUE;
		int left_x = Integer.MAX_VALUE;
		int right_x = Integer.MIN_VALUE;

		for(int i = 0; i < this.grid.length; i++){
			for(int j = 0; j < this.grid[i].length; j++){
				if(this.grid[i][j] == ch){
					if(j < upper_y){
						upper_y = j;
					}
					if(j > down_y){
						down_y = j;
					}
					if(i < left_x){
						left_x = i;
					}
					if(i > right_x){
						right_x = i;
					}
				}
			}
		}

		return evalArea(left_x, upper_y, right_x, down_y);
	}

	private int evalNumChar(char ch, int startX, int startY, int endX, int endY){
		int result = 0;

		for(int i = startX; i < endX; i++){
			for(int j = startY; j < endY; j++){
				if(this.grid[i][j] == ch){
					result++;
				}
			}
		}

		return result;
	}

	private int evalArea(int startX, int startY, int endX, int endY){
		return (endX - startX + 1) * (endY - startY + 1);
	}
	
	/**
	 * Returns the count of '+' figures in the grid (see handout).
	 * @return number of + in grid
	 */
	public int countPlus() {
		int result = 0;

		for(int i = 0; i < this.grid.length; i++) {
			for (int j = 0; j < this.grid[i].length; j++) {
				if (is_plus(i, j)) {
					result++;
				}
			}
		}

		return result;
	}

	private boolean is_plus(int i, int j){
		int left_arm = arm_len(i, j, -1, 0);

		if(left_arm < 2){
			return false;
		}

		int right_arm = arm_len(i, j, 1, 0);

		if(left_arm != right_arm){
			return false;
		}

		int up_arm = arm_len(i, j, 0, -1);

		if(right_arm != up_arm){
			return false;
		}

		int down_arm = arm_len(i, j, 0, 1);

		if(up_arm != down_arm){
			return false;
		}else{
			return true;
		}
	}

	private int arm_len(int x, int y, int dx, int dy){
		int result = 0;

		int i = x;
		int j = y;

		while(i < this.grid.length && i >= 0 && j < this.grid[i].length && j >= 0){
			if(this.grid[i][j] == this.grid[x][y]){
				result++;
			}else{
				break;
			}

			i += dx;
			j += dy;
		}

		return result;
	}

}
