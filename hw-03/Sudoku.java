import java.util.*;

/*
 * Encapsulates a Sudoku grid to be solved.
 * CS108 Stanford.
 */
public class Sudoku{
	// Provided grid data for main/testing
	// The instance variable strategy is up to you.

	private class Spot{
		private HashSet<Integer> placements;
		private int x;
		private int y;
		private int value;

		public Spot(int[][] ints, int i, int j){
			placements = new HashSet<>();
			for(int num = 1; num < 10; num++){
				if(isValidPlacement(num, ints, i, j)){
					placements.add(num);
				}
			}
			this.x = i;
			this.y = j;
			this.value = ints[i][j];
		}

		public static boolean isValidPlacement(int value, int[][] ints, int row, int col){
			return !is_in_row(value, ints, row) && !is_in_col(value, ints, col) && !is_in_square(value, ints, row, col);
		}

		private static boolean is_in_row(int value, int[][] ints, int row){
			for(int i = 0; i < 9; i++){
				if(ints[row][i] == value){
					return true;
				}
			}
			return false;
		}

		private static boolean is_in_col(int value, int[][] ints, int col){
			for(int i = 0; i < 9; i++){
				if(ints[i][col] == value){
					return true;
				}
			}
			return false;
		}

		private static boolean is_in_square(int value, int[][] ints, int row, int col){
			int square_row = row - row % 3;
			int square_col = col - col % 3;

			for(int i = square_row; i < square_row + 3; i++){
				for(int j = square_col; j < square_col + 3; j++){
					if(ints[i][j] == value) return true;
				}
			}
			return false;
		}

		public int get_row(){
			return this.x;
		}

		public int get_col(){
			return this.y;
		}

		public int get_set_size(){
			return this.placements.size();
		}
	}

	private int[][] solution = new int[9][9];
	private boolean solution_filled;
	private long elapsed_time;
	private ArrayList<Spot> spots;
	private int[][] ints_clone;
	
	// Provided easy 1 6 grid
	// (can paste this text into the GUI too)
	public static final int[][] easyGrid = Sudoku.stringsToGrid(
	"1 6 4 0 0 0 0 0 2",
	"2 0 0 4 0 3 9 1 0",
	"0 0 5 0 8 0 4 0 7",
	"0 9 0 0 0 6 5 0 0",
	"5 0 0 1 0 2 0 0 8",
	"0 0 8 9 0 0 0 3 0",
	"8 0 9 0 4 0 2 0 0",
	"0 7 3 5 0 9 0 0 1",
	"4 0 0 0 0 0 6 7 9");
	
	
	// Provided medium 5 3 grid
	public static final int[][] mediumGrid = Sudoku.stringsToGrid(
	 "530070000",
	 "600195000",
	 "098000060",
	 "800060003",
	 "400803001",
	 "700020006",
	 "060000280",
	 "000419005",
	 "000080079");
	
	// Provided hard 3 7 grid
	// 1 solution this way, 6 solutions if the 7 is changed to 0
	public static final int[][] hardGrid = Sudoku.stringsToGrid(
	"3 7 0 0 0 0 0 8 0",
	"0 0 1 0 9 3 0 0 0",
	"0 4 0 7 8 0 0 0 3",
	"0 9 3 8 0 0 0 1 2",
	"0 0 0 0 4 0 0 0 0",
	"5 2 0 0 0 6 7 9 0",
	"6 0 0 0 2 1 0 4 0",
	"0 0 0 5 3 0 9 0 0",
	"0 3 0 0 0 0 0 5 1");
	
	public static final int SIZE = 9;  // size of the whole 9x9 puzzle
	public static final int PART = 3;  // size of each 3x3 part
	public static final int MAX_SOLUTIONS = 100;
	
	// Provided various static utility methods to
	// convert data formats to int[][] grid.
	
	/**
	 * Returns a 2-d grid parsed from strings, one string per row.
	 * The "..." is a Java 5 feature that essentially
	 * makes "rows" a String[] array.
	 * (provided utility)
	 * @param rows array of row strings
	 * @return grid
	 */
	public static int[][] stringsToGrid(String... rows) {
		int[][] result = new int[rows.length][];
		for (int row = 0; row<rows.length; row++) {
			result[row] = stringToInts(rows[row]);
		}
		return result;
	}
	
	
	/**
	 * Given a single string containing 81 numbers, returns a 9x9 grid.
	 * Skips all the non-numbers in the text.
	 * (provided utility)
	 * @param text string of 81 numbers
	 * @return grid
	 */
	public static int[][] textToGrid(String text) {
		int[] nums = stringToInts(text);
		if (nums.length != SIZE*SIZE) {
			throw new RuntimeException("Needed 81 numbers, but got:" + nums.length);
		}
		
		int[][] result = new int[SIZE][SIZE];
		int count = 0;
		for (int row = 0; row<SIZE; row++) {
			for (int col=0; col<SIZE; col++) {
				result[row][col] = nums[count];
				count++;
			}
		}
		return result;
	}
	
	
	/**
	 * Given a string containing digits, like "1 23 4",
	 * returns an int[] of those digits {1 2 3 4}.
	 * (provided utility)
	 * @param string string containing ints
	 * @return array of ints
	 */
	public static int[] stringToInts(String string) {
		int[] a = new int[string.length()];
		int found = 0;
		for (int i=0; i<string.length(); i++) {
			if (Character.isDigit(string.charAt(i))) {
				a[found] = Integer.parseInt(string.substring(i, i+1));
				found++;
			}
		}
		int[] result = new int[found];
		System.arraycopy(a, 0, result, 0, found);
		return result;
	}


	// Provided -- the deliverable main().
	// You can edit to do easier cases, but turn in
	// solving hardGrid.
	public static void main(String[] args) {
		Sudoku sudoku;
		sudoku = new Sudoku(hardGrid);
		
		System.out.println(sudoku); // print the raw problem
		int count = sudoku.solve();
		System.out.println("solutions:" + count);
		System.out.println("elapsed:" + sudoku.getElapsed() + "ms");
		System.out.println(sudoku.getSolutionText());
	}
	
	
	

	/**
	 * Sets up based on the given ints.
	 */
	public Sudoku(int[][] ints) {
		this.elapsed_time = 0;
		this.spots = new ArrayList<>();
		this.ints_clone = new int[ints.length][ints[0].length];
		this.solution_filled = false;
		for(int i = 0; i < ints.length; i++){
			for(int j = 0; j < ints[i].length; j++){
				this.solution[i][j] = ints[i][j];
				if(ints[i][j] == 0){
					Spot spot = new Spot(ints, i, j);
					this.spots.add(spot);
				}
				this.ints_clone[i][j] = ints[i][j];
			}
		}
		Collections.sort(spots, new Comparator<Spot>() {
			@Override
			public int compare(Spot spot_1, Spot spot_2) {
				int size_1 = spot_1.get_set_size();
				int size_2 = spot_2.get_set_size();

				if(size_1 > size_2){
					return 1;
				}else if(size_1 == size_2){
					return 0;
				}else{
					return -1;
				}
			}
		});
	}
	
	/**
	 * Solves the puzzle, invoking the underlying recursive search.
	 */
	public int solve() {
		long start = System.nanoTime();
		int result = wrapper(0);
		long end = System.nanoTime();
		this.elapsed_time = end - start;
		return result;
	}

	private void copy_to_solution(){
		for(int i = 0; i < SIZE; i++){
			for(int j = 0; j < SIZE; j++){
				this.solution[i][j] = this.ints_clone[i][j];
			}
		}
	}

	private int wrapper(int index){
		if(is_solved(this.ints_clone)){
			if(!this.solution_filled){
				copy_to_solution();
				this.solution_filled = true;
			}
			return 1;
		}

		if(index == this.spots.size()) return 0;

		int result = 0;
		for(int i = 1; i <= SIZE; i++) {
			if (isValidPlacement(i, this.ints_clone, this.spots.get(index).get_row(), this.spots.get(index).get_col())) {
				this.ints_clone[this.spots.get(index).get_row()][this.spots.get(index).get_col()] = i;
				result += wrapper(index + 1);
				if(result >= MAX_SOLUTIONS) return result;
				this.ints_clone[this.spots.get(index).get_row()][this.spots.get(index).get_col()] = 0;
			}
		}

		return result;
	}

	private static boolean isValidPlacement(int value, int[][] ints, int row, int col){
		return !is_in_row(value, ints, row) && !is_in_col(value, ints, col) && !is_in_square(value, ints, row, col);
	}

	private static boolean is_in_row(int value, int[][] ints, int row){
		for(int i = 0; i < SIZE; i++){
			if(ints[row][i] == value){
				return true;
			}
		}
		return false;
	}

	private static boolean is_in_col(int value, int[][] ints, int col){
		for(int i = 0; i < SIZE; i++){
			if(ints[i][col] == value){
				return true;
			}
		}
		return false;
	}

	private static boolean is_in_square(int value, int[][] ints, int row, int col){
		int square_row = row - row % PART;
		int square_col = col - col % PART;

		for(int i = square_row; i < square_row + PART; i++){
			for(int j = square_col; j < square_col + PART; j++){
				if(ints[i][j] == value) return true;
			}
		}
		return false;
	}

	public static boolean is_solved(int[][] ints){
		return check(ints);
	}

	private static boolean check(int[][] ints){
		for(int i = 0; i < SIZE; i++){
			if(!isValidColumn(ints, i) || !isValidRow(ints, i)){
				return false;
			}
		}

		for(int i = 0; i < SIZE; i++){
			for(int j = 0; j < SIZE; j++){
				if(!isValidSquare(ints, i, j)){
					return false;
				}
			}
		}
		return true;
	}

	private static boolean isValidColumn(int[][] ints, int col){
		HashSet<Integer> integers = new HashSet<>();
		for(int i = 0; i < SIZE; i++){
			if(ints[i][col] == 0 || integers.contains(ints[i][col])){
				return false;
			}
			integers.add(ints[i][col]);
		}
		return true;
	}

	private static boolean isValidRow(int[][] ints, int row){
		HashSet<Integer> integers = new HashSet<>();
		for(int i = 0; i < SIZE; i++){
			if(ints[row][i] == 0 || integers.contains(ints[row][i])){
				return false;
			}
			integers.add(ints[row][i]);
		}
		return true;
	}

	private static boolean isValidSquare(int[][] ints, int row, int col){
		int start_row = row - row % PART;
		int start_col = col - col % PART;
		HashSet<Integer> integers = new HashSet<>();

		for(int i = start_row; i < start_row + PART; i++){
			for(int j = start_col; j < start_col + PART; j++){
				if(ints[i][j] == 0 || integers.contains(ints[i][j])){
					return false;
				}
				integers.add(ints[i][j]);
			}
		}
		return true;
	}
	
	public String getSolutionText() {
		String result = "";
		int num_chars = 0;

		for(int i = 0; i < this.solution.length; i++){
			for(int j = 0; j < this.solution[i].length; j++){
				result += this.solution[i][j];
				num_chars++;
				if(num_chars % 9 == 0) result += "\n";
			}
		}

		return result;
	}
	
	public long getElapsed() {
		return this.elapsed_time;
	}

}
