import junit.framework.TestCase;

import java.util.Arrays;
import java.util.Locale;


public class BoardTest extends TestCase {
	Board b;
	Piece pyr1, pyr2, pyr3, pyr4, s, sRotated;

	// This shows how to build things in setUp() to re-use
	// across tests.
	
	// In this case, setUp() makes shapes,
	// and also a 3X6 board, with pyr placed at the bottom,
	// ready to be used by tests.
	
	protected void setUp() throws Exception {
		b = new Board(3, 6);
		
		pyr1 = new Piece(Piece.PYRAMID_STR);
		pyr2 = pyr1.computeNextRotation();
		pyr3 = pyr2.computeNextRotation();
		pyr4 = pyr3.computeNextRotation();
		
		s = new Piece(Piece.S1_STR);
		sRotated = s.computeNextRotation();
		
		b.place(pyr1, 0, 0);
	}
	
	// Check the basic width/height/max after the one placement
	public void testSample1() {
		assertEquals(1, b.getColumnHeight(0));
		assertEquals(2, b.getColumnHeight(1));
		assertEquals(2, b.getMaxHeight());
		assertEquals(3, b.getRowWidth(0));
		assertEquals(1, b.getRowWidth(1));
		assertEquals(0, b.getRowWidth(2));
		assertEquals(3, b.getWidth());
		assertEquals(6, b.getHeight());
	}
	
	// Place sRotated into the board, then check some measures
	public void testSample2() {
		assertEquals(3, b.getWidth());
		assertEquals(6, b.getHeight());
		b.commit();
		int result = b.place(sRotated, 1, 1);
		assertEquals(Board.PLACE_OK, result);
		assertEquals(1, b.getColumnHeight(0));
		assertEquals(4, b.getColumnHeight(1));
		assertEquals(3, b.getColumnHeight(2));
		assertEquals(4, b.getMaxHeight());
		b.undo();
		assertEquals(1, b.getColumnHeight(0));
		assertEquals(2, b.getColumnHeight(1));
		assertEquals(1, b.getColumnHeight(2));
		assertEquals(3, b.getRowWidth(0));
		b.clearRows();
	}

	public void testPlace(){
		Board board = new Board(4, 5);

		assertEquals(4, board.getWidth());
		assertEquals(5, board.getHeight());

		Piece l1 = new Piece(Piece.L1_STR);
		int l1_place = board.place(l1,0, 0);
		assertEquals(Board.PLACE_OK, l1_place);
		assertEquals(3, board.getColumnHeight(0));
		assertEquals(1, board.getColumnHeight(1));
		assertEquals(0, board.getColumnHeight(2));
		assertEquals(0, board.getColumnHeight(3));
		assertEquals(2, board.getRowWidth(0));
		assertEquals(1, board.getRowWidth(1));
		assertEquals(1, board.getRowWidth(2));
		assertEquals(3, board.getMaxHeight());

		board.commit();
		Piece pyr = new Piece(Piece.PYRAMID_STR);
		int pyr_place = board.place(pyr, 1, 0);

		assertEquals(Board.PLACE_BAD, pyr_place);
		assertEquals(3, board.getColumnHeight(0));
		assertEquals(1, board.getColumnHeight(1));
		assertEquals(2, board.getColumnHeight(2));
		assertEquals(1, board.getColumnHeight(3));
		assertEquals(4, board.getRowWidth(0));
		assertEquals(2, board.getRowWidth(1));
		assertEquals(1, board.getRowWidth(2));
		assertEquals(3, board.getMaxHeight());

		board.commit();
		Piece square = new Piece(Piece.SQUARE_STR);
		//Testing on out bounds to right
		int square_place = board.place(square, 3, 1);
		assertEquals(Board.PLACE_OUT_BOUNDS, square_place);
		assertEquals(3, board.getColumnHeight(3));

		board.undo();
		//Testing on out bounds to left
		int square_place_2 = board.place(square, -1, 3);
		assertEquals(Board.PLACE_OUT_BOUNDS, square_place_2);
		assertEquals(5, board.getColumnHeight(0));
		assertEquals(5, board.getMaxHeight());
		assertEquals(1, board.getRowWidth(3));
		assertEquals(1, board.getRowWidth(4));

		board.undo();
		//Testing on out bounds upwards
		int square_place_3 = board.place(square, 0, 4);
		assertEquals(Board.PLACE_OUT_BOUNDS, square_place_3);
		assertEquals(5, board.getColumnHeight(0));
		assertEquals(5, board.getColumnHeight(1));
		assertEquals(5, board.getMaxHeight());
		assertEquals(2, board.getRowWidth(4));

		board.undo();
		int pyramid = board.place(new Piece(Piece.PYRAMID_STR), 0, 1);
		assertEquals(Board.PLACE_BAD, pyramid);
	}

	public void testDropHeight1(){
		Board board = new Board(4, 6);

		assertEquals(4, board.getWidth());
		assertEquals(6, board.getHeight());

		int pyr_rot_place = board.place(new Piece(Piece.PYRAMID_STR).computeNextRotation(), 2, 1);
		int drop = board.dropHeight(new Piece(Piece.PYRAMID_STR).computeNextRotation().computeNextRotation(), 1);
		assertEquals(3, drop);
		int drop_2 = board.dropHeight(new Piece(Piece.STICK_STR).computeNextRotation(), 0);
		assertEquals(4, drop_2);

		board.undo();
		int pyr_rot_place_2 = board.place(new Piece(Piece.PYRAMID_STR).computeNextRotation().computeNextRotation().computeNextRotation(), 0, 1);
		int drop_3 = board.dropHeight(new Piece(Piece.PYRAMID_STR).computeNextRotation().computeNextRotation(), 0);
		assertEquals(3, drop_3);
		int drop_4 = board.dropHeight(new Piece(Piece.SQUARE_STR), 1);
		assertEquals(3, drop_4);
	}

	public void testDropHeight2(){
		Board board = new Board(5, 5);

		assertEquals(5, board.getWidth());
		assertEquals(5, board.getHeight());

		board.place(new Piece(Piece.STICK_STR), 0, 0);
		board.commit();

		board.place(new Piece(Piece.L1_STR), 1, 0);
		board.commit();

		board.place(new Piece(Piece.L1_STR), 2, 1);
		board.commit();

		board.place(new Piece(Piece.STICK_STR), 4, 0);

		assertEquals(4, board.dropHeight(new Piece(Piece.SQUARE_STR), 0));
		assertEquals(2, board.dropHeight(new Piece(Piece.STICK_STR), 3));
		assertEquals(4, board.dropHeight(new Piece(Piece.STICK_STR).computeNextRotation(), 0));

		try{
			board.dropHeight(new Piece(Piece.STICK_STR).computeNextRotation(), 3);
		}catch(Exception e){
			System.out.println("Out of bounds Exception");
		}
	}

	public void testClearRows1(){
		Board board = new Board(4, 5);

		assertEquals(4, board.getWidth());
		assertEquals(5, board.getHeight());

		board.place(new Piece(Piece.STICK_STR).computeNextRotation(), 0, 0);
		board.commit();

		board.place(new Piece(Piece.STICK_STR), 0, 1);
		board.commit();

		board.place(new Piece(Piece.PYRAMID_STR), 1, 1);
		//System.out.println(board.toString());
		assertEquals(3, board.dropHeight(new Piece(Piece.L1_STR).computeNextRotation().computeNextRotation(), 0));

		int rowsCleared = board.clearRows();
		assertEquals(2, rowsCleared);
		assertEquals(1, board.dropHeight(new Piece(Piece.L1_STR).computeNextRotation().computeNextRotation(), 0));
		assertEquals(2, board.getRowWidth(0));
		assertEquals(1, board.getRowWidth(1));
		assertEquals(1, board.getRowWidth(2));
		assertEquals(0, board.getRowWidth(3));
		assertEquals(0, board.getRowWidth(4));
		assertEquals(3, board.getColumnHeight(0));
		assertEquals(0, board.getColumnHeight(1));
		assertEquals(1, board.getColumnHeight(2));
		assertEquals(0, board.getColumnHeight(3));
		assertEquals(3, board.getMaxHeight());
	}

	public void testClearRows2(){
		Board board = new Board(3, 6);

		assertEquals(3, board.getWidth());
		assertEquals(6, board.getHeight());

		for(int i = 0; i < board.getWidth(); i++){
			board.place(new Piece(Piece.STICK_STR), i, 0);
			board.commit();
		}

		assertEquals(4, board.dropHeight(new Piece(Piece.PYRAMID_STR).computeNextRotation().computeNextRotation(), 0));
		assertEquals(4, board.dropHeight(new Piece(Piece.PYRAMID_STR), 0));

		int rowsCleared = board.clearRows();

		assertEquals(4, rowsCleared);
		assertEquals(0, board.dropHeight(new Piece(Piece.PYRAMID_STR).computeNextRotation().computeNextRotation(), 0));
		assertEquals(0, board.dropHeight(new Piece(Piece.PYRAMID_STR), 0));

		for(int i = 0; i < board.getHeight(); i++){
			assertEquals(0, board.getRowWidth(i));
		}
		for(int i = 0; i < board.getWidth(); i++){
			assertEquals(0, board.getColumnHeight(i));
		}
		assertEquals(0, board.getMaxHeight());
	}

	public void testUndo1(){
		Board board = new Board(5, 5);

		board.place(new Piece(Piece.PYRAMID_STR), 0, 1);
		board.commit();

		board.place(new Piece(Piece.STICK_STR).computeNextRotation(), 0, 0);
		board.commit();

		boolean[][] grid = new boolean[board.getWidth()][board.getHeight()];
		int max_height = board.getMaxHeight();
		int[] widths = new int[board.getHeight()];
		int[] heights = new int[board.getWidth()];
		preparation(board, grid, widths, heights);

		board.place(new Piece(Piece.L2_STR).computeNextRotation(), 0, 0);

		board.undo();

		for(int i = 0; i < board.getWidth(); i++){
			for(int j = 0; j < board.getHeight(); j++){
				assertTrue(board.getGrid(i, j) == grid[i][j]);
			}
		}

		assertEquals(max_height, board.getMaxHeight());

		for(int i = 0; i < board.getHeight(); i++){
			assertTrue(widths[i] == board.getRowWidth(i));
		}

		for(int i = 0; i < board.getWidth(); i++){
			assertTrue(heights[i] == board.getColumnHeight(i));
		}
	}

	public void testUndo2(){
		Board board = new Board(3, 6);

		board.place(new Piece(Piece.PYRAMID_STR).computeNextRotation().computeNextRotation(), 0, 0);
		board.commit();

		board.place(new Piece(Piece.PYRAMID_STR), 0, 4);
		board.commit();

		boolean[][] grid = new boolean[board.getWidth()][board.getHeight()];
		int max_height = board.getMaxHeight();
		int[] widths = new int[board.getHeight()];
		int[] heights = new int[board.getWidth()];
		preparation(board, grid, widths, heights);

		board.place(new Piece(Piece.SQUARE_STR), 0, 2);
		board.undo();

		for(int i = 0; i < board.getWidth(); i++){
			for(int j = 0; j < board.getHeight(); j++){
				assertTrue(board.getGrid(i, j) == grid[i][j]);
			}
		}

		assertEquals(max_height, board.getMaxHeight());

		for(int i = 0; i < board.getHeight(); i++){
			assertTrue(widths[i] == board.getRowWidth(i));
		}

		for(int i = 0; i < board.getWidth(); i++){
			assertTrue(heights[i] == board.getColumnHeight(i));
		}
	}

	private void preparation(Board board, boolean[][] grid, int[] widths, int[] heights){
		for(int i = 0; i < board.getWidth(); i++){
			for(int j = 0; j < board.getHeight(); j++){
				grid[i][j] = board.getGrid(i, j);
			}
		}
		for(int i = 0; i < board.getHeight(); i++){
			widths[i] = board.getRowWidth(i);
		}
		for(int i = 0; i < board.getWidth(); i++){
			heights[i] = board.getColumnHeight(i);
		}
	}

	public void testEverything(){
		Board board = new Board(4, 5);

		//Testing clearRows() on board with no place()
		String before = board.toString();
		int rowsCleared = board.clearRows();
		assertEquals(0, rowsCleared);
		assertTrue(before.equals(board.toString()));
		for(int i = 0; i < board.getWidth(); i++){
			assertEquals(0, board.getColumnHeight(i));
		}

		for(int i = 0; i < board.getHeight(); i++){
			assertEquals(0, board.getRowWidth(i));
		}
		assertEquals(0, board.getMaxHeight());

		board.commit();
		board.place(new Piece(Piece.PYRAMID_STR).computeNextRotation().computeNextRotation().computeNextRotation(), 0, 0);
		board.commit();

		board.place(new Piece(Piece.SQUARE_STR), 0, 3);
		board.commit();

		String old_board_0 = board.toString();
		board.place(new Piece(Piece.SQUARE_STR), 2, 3);
		//No commit() between place() and clearRows()

		int rowsCleared_1 = board.clearRows();
		assertEquals(2, rowsCleared_1);
		assertEquals(1, board.getRowWidth(0));
		assertEquals(2, board.getRowWidth(1));
		assertEquals(1, board.getRowWidth(2));
		assertEquals(0, board.getRowWidth(3));
		assertEquals(0, board.getRowWidth(4));
		assertEquals(3, board.getColumnHeight(0));
		assertEquals(2, board.getColumnHeight(1));
		for(int i = 2; i < board.getWidth(); i++){
			assertEquals(0, board.getColumnHeight(i));
		}
		assertEquals(3, board.getMaxHeight());

		board.undo();

		assertTrue(board.toString().equals(old_board_0));
		assertEquals(1, board.getRowWidth(0));
		assertEquals(2, board.getRowWidth(1));
		assertEquals(1, board.getRowWidth(2));
		assertEquals(2, board.getRowWidth(3));
		assertEquals(2, board.getRowWidth(4));

		assertEquals(5, board.getColumnHeight(0));
		assertEquals(5, board.getColumnHeight(1));
		assertEquals(0, board.getColumnHeight(2));
		assertEquals(0, board.getColumnHeight(3));
		assertEquals(5, board.getMaxHeight());

		board.place(new Piece(Piece.PYRAMID_STR).computeNextRotation(), 2, 0);
		board.commit();

		String old_board_1 = board.toString();
		int rowsCleared_2 = board.clearRows();

		assertEquals(1, rowsCleared_2);
		assertEquals(2, board.getRowWidth(0));
		assertEquals(2, board.getRowWidth(1));
		assertEquals(2, board.getRowWidth(2));
		assertEquals(2, board.getRowWidth(3));
		assertEquals(0, board.getRowWidth(4));
		assertEquals(4, board.getColumnHeight(0));
		assertEquals(4, board.getColumnHeight(1));
		assertEquals(0, board.getColumnHeight(2));
		assertEquals(2, board.getColumnHeight(3));
		assertEquals(4, board.getMaxHeight());

		board.undo();
		String undo_board = board.toString();

		assertTrue(old_board_1.equals(undo_board));
		assertEquals(2, board.getRowWidth(0));
		assertEquals(4, board.getRowWidth(1));
		assertEquals(2, board.getRowWidth(2));
		assertEquals(2, board.getRowWidth(3));
		assertEquals(2, board.getRowWidth(4));
		assertEquals(5, board.getColumnHeight(0));
		assertEquals(5, board.getColumnHeight(1));
		assertEquals(2, board.getColumnHeight(2));
		assertEquals(3, board.getColumnHeight(3));
		assertEquals(5, board.getMaxHeight());
	}
}
