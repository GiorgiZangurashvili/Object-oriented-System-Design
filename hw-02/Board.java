// Board.java

import java.util.Arrays;
import java.util.HashSet;

/**
 CS108 Tetris Board.
 Represents a Tetris board -- essentially a 2-d grid
 of booleans. Supports tetris pieces and row clearing.
 Has an "undo" feature that allows clients to add and remove pieces efficiently.
 Does not do any drawing or have any idea of pixels. Instead,
 just represents the abstract 2-d board.
*/
public class Board	{
	// Some ivars are stubbed out for you:
	private int width;
	private int height;
	private boolean[][] grid;
	private boolean DEBUG = true;
	boolean committed;
	private int[] widths;
	private int[] heights;
	private int maxHeight;

	//backups
	private boolean[][] backup_grid;
	private int[] xWidths;
	private int[] yHeights;
	private int backupMaxHeight;
	
	
	// Here a few trivial methods are provided:
	
	/**
	 Creates an empty board of the given width and height
	 measured in blocks.
	*/
	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		grid = new boolean[width][height];
		committed = true;
		this.widths = new int[this.height];
		this.heights = new int[this.width];
		this.maxHeight = 0;
		this.backup_grid = new boolean[this.width][this.height];
		this.backupMaxHeight = 0;
		this.xWidths = new int[this.height];
		this.yHeights = new int[this.width];
	}
	
	
	/**
	 Returns the width of the board in blocks.
	*/
	public int getWidth() {
		return width;
	}
	
	
	/**
	 Returns the height of the board in blocks.
	*/
	public int getHeight() {
		return height;
	}
	
	
	/**
	 Returns the max column height present in the board.
	 For an empty board this is 0.
	*/
	public int getMaxHeight() {	 
		return this.maxHeight;
	}
	
	
	/**
	 Checks the board for internal consistency -- used
	 for debugging.
	*/
	public void sanityCheck() {
		if(!DEBUG) return;

		int max = Integer.MIN_VALUE;
		for(int i = 0; i < this.height; i++){
			if(computeWidth(i) != getRowWidth(i)){
				throw new RuntimeException("Row widths does not match!");
			}
		}

		for(int i = 0; i < this.width; i++){
			int height = computeHeight(i);
			if(height > max){
				max = height;
			}
			if(height != getColumnHeight(i)){
				throw new RuntimeException("Column heights does not match");
			}
		}

		if(max != getMaxHeight()){
			throw new RuntimeException("Max heights does not match");
		}
	}

	private int computeWidth(int y){
		int result = 0;

		for(int i = 0; i < this.width; i++){
			if(this.grid[i][y]){
				result++;
			}
		}

		return result;
	}

	private int computeHeight(int x){
		int result = 0;

		for(int i = 0; i < this.height; i++){
			if(this.grid[x][i]){
				result = i + 1;
			}
		}

		return result;
	}
	
	/**
	 Given a piece and an x, returns the y
	 value where the piece would come to rest
	 if it were dropped straight down at that x.
	 
	 <p>
	 Implementation: use the skirt and the col heights
	 to compute this fast -- O(skirt length).
	*/
	public int dropHeight(Piece piece, int x) {
		int max = Integer.MIN_VALUE;

		for(int i = 0; i < piece.getWidth(); i++){
			if(x + i < this.heights.length && this.heights[x + i] - piece.getSkirt()[i]> max){
				max = this.heights[x + i] - piece.getSkirt()[i];
			}
		}

		return max;
	}
	
	
	/**
	 Returns the height of the given column --
	 i.e. the y value of the highest block + 1.
	 The height is 0 if the column contains no blocks.
	*/
	public int getColumnHeight(int x) {
		return this.heights[x];
	}
	
	
	/**
	 Returns the number of filled blocks in
	 the given row.
	*/
	public int getRowWidth(int y) {
		return this.widths[y];
	}
	
	
	/**
	 Returns true if the given block is filled in the board.
	 Blocks outside of the valid width/height area
	 always return true.
	*/
	public boolean getGrid(int x, int y) {
		if(x < 0 || x >= this.width || y < 0 || y >= this.height || this.grid[x][y]){
			return true;
		}else{
			return false;
		}
	}
	
	
	public static final int PLACE_OK = 0;
	public static final int PLACE_ROW_FILLED = 1;
	public static final int PLACE_OUT_BOUNDS = 2;
	public static final int PLACE_BAD = 3;
	
	/**
	 Attempts to add the body of a piece to the board.
	 Copies the piece blocks into the board grid.
	 Returns PLACE_OK for a regular placement, or PLACE_ROW_FILLED
	 for a regular placement that causes at least one row to be filled.
	 
	 <p>Error cases:
	 A placement may fail in two ways. First, if part of the piece may falls out
	 of bounds of the board, PLACE_OUT_BOUNDS is returned.
	 Or the placement may collide with existing blocks in the grid
	 in which case PLACE_BAD is returned.
	 In both error cases, the board may be left in an invalid
	 state. The client can use undo(), to recover the valid, pre-place state.
	*/
	public int place(Piece piece, int x, int y) {
		// flag !committed problem
		if (!committed) throw new RuntimeException("place commit problem");

		backupData();
		//copyArray();

		int result = PLACE_OK;
		boolean bad_place = false;
		boolean out_bounds = false;

		for(int i = 0; i < piece.getBody().length; i++){
			int x_coor = piece.getBody()[i].x;
			int y_coor = piece.getBody()[i].y;
			if(x + x_coor < 0 || x + x_coor >= this.width || y + y_coor < 0 || y + y_coor >= this.height){
				result = PLACE_OUT_BOUNDS;
				out_bounds = true;
			}else {
				if (this.grid[x + x_coor][y + y_coor]) {
					result = PLACE_BAD;
					bad_place = true;
				} else {
					if(y + y_coor + 1 > this.heights[x + x_coor]){
						this.heights[x + x_coor] = y + y_coor + 1;
					}
					this.widths[y + y_coor]++;
					if(this.widths[y + y_coor] == this.width && !bad_place && !out_bounds) {
						result = PLACE_ROW_FILLED;
					}
					if (this.heights[x + x_coor] > this.maxHeight) {
						this.maxHeight = this.heights[x + x_coor];
					}
				}
				this.grid[x + x_coor][y + y_coor] = true;
			}
		}

		this.committed = false;

		if(!bad_place){
			sanityCheck();
		}

		return result;
	}

	private void backupData(){
		copyBoard();
		this.xWidths = this.widths.clone();
		this.yHeights = this.heights.clone();
		this.backupMaxHeight = this.maxHeight;
	}

	private void copyBoard(){
		for(int i = 0; i < this.width; i++){
			for(int j = 0; j < this.height; j++){
				this.backup_grid[i][j] = this.grid[i][j];
			}
		}
	}
	
	
	/**
	 Deletes rows that are filled all the way across, moving
	 things above down. Returns the number of rows cleared.
	*/
	public int clearRows() {
		if(this.committed) backupData();

		int rowsCleared = 0;
		boolean[][] new_grid = new boolean[this.width][this.height];
		int[] new_widths = new int[this.height];
		HashSet<Integer> indexes = new HashSet<>();
		int index_y = 0;

		for(int i = 0; i < this.height; i++){
			if(getRowWidth(i) != this.width){
				copyInNewGrid(new_grid, index_y, i);
				index_y++;
			}else{
				rowsCleared++;
				indexes.add(i);
			}
		}

		reconstructWidths(new_widths, indexes);
		this.widths = new_widths;
		this.grid = new_grid;
		decrementHeights();

		this.committed = false;

		sanityCheck();
		return rowsCleared;
	}

	private void copyInNewGrid(boolean[][] new_grid, int index_y, int y){
		for(int i = 0; i < this.width; i++){
			new_grid[i][index_y] = this.grid[i][y];
		}
	}

	private void decrementHeights(){
		for(int i = 0; i < this.width; i++){
			this.heights[i] = computeHeight(i);
		}
		this.maxHeight = Integer.MIN_VALUE;

		for(int i = 0; i < this.heights.length; i++){
			if(this.heights[i] > this.maxHeight){
				this.maxHeight = this.heights[i];
			}
		}
	}

	private void reconstructWidths(int[] new_widths, HashSet<Integer> indexes){
		int index = 0;
		for(int i = 0; i < this.height; i++){
			if(!indexes.contains(i)){
				new_widths[index] = getRowWidth(i);
				index++;
			}
		}
	}

	/**
	 Reverts the board to its state before up to one place
	 and one clearRows();
	 If the conditions for undo() are not met, such as
	 calling undo() twice in a row, then the second undo() does nothing.
	 See the overview docs.
	*/
	public void undo() {
		if(this.committed) return;

		boolean[][] temp = this.grid;
		this.grid = backup_grid;
		backup_grid = temp;

		int[] temp_1 = this.widths;
		this.widths = this.xWidths;
		this.xWidths = temp_1;

		int[] temp_2 = this.heights;
		this.heights = this.yHeights;
		this.yHeights = temp_2;

		int max_height = this.maxHeight;
		this.maxHeight = this.backupMaxHeight;
		this.backupMaxHeight = max_height;

		this.committed = true;

		sanityCheck();
	}
	
	/**
	 Puts the board in the committed state.
	*/
	public void commit() {
		committed = true;
	}


	
	/*
	 Renders the board state as a big String, suitable for printing.
	 This is the sort of print-obj-state utility that can help see complex
	 state change over time.
	 (provided debugging utility) 
	 */
	public String toString() {
		StringBuilder buff = new StringBuilder();
		for (int y = height-1; y>=0; y--) {
			buff.append('|');
			for (int x=0; x<width; x++) {
				if (getGrid(x,y)) buff.append('+');
				else buff.append(' ');
			}
			buff.append("|\n");
		}
		for (int x=0; x<width+2; x++) buff.append('-');
		return(buff.toString());
	}
}


