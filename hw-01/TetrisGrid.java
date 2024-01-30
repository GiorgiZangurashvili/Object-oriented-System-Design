//
// TetrisGrid encapsulates a tetris board and has
// a clearRows() capability.

public class TetrisGrid {

	private boolean[][] grid;

	/**
	 * Constructs a new instance with the given grid.
	 * Does not make a copy.
	 * @param grid
	 */
	public TetrisGrid(boolean[][] grid) {
		this.grid = grid;
	}


	/**
	 * Does row-clearing on the grid (see handout).
	 */
	public void clearRows() {
		boolean[][] new_grid = new boolean[this.grid.length][this.grid[0].length];
		fill_grid(new_grid);
		int add_index = 0;

		for(int i = 0; i < this.grid[0].length; i++){
			if(!is_full(i)){
				copy(new_grid, i, add_index);
				add_index++;
			}
		}

		this.grid = new_grid;
	}

	private void fill_grid(boolean[][] grid){
		for(int i = 0; i < grid.length; i++){
			for(int j = 0; j < grid[i].length; j++){
				grid[i][j] = false;
			}
		}
	}

	private boolean is_full(int index){
		for(int i = 0; i < this.grid.length; i++){
			if(!this.grid[i][index]){
				return false;
			}
		}
		return true;
	}

	private void copy(boolean[][] new_grid, int index, int add_index){
		for(int i = 0; i < this.grid.length; i++){
			new_grid[i][add_index] = this.grid[i][index];
		}
	}

	/**
	 * Returns the internal 2d grid array.
	 * @return 2d grid array
	 */
	boolean[][] getGrid() {
		return this.grid;
	}
}
