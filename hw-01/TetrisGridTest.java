import junit.framework.TestCase;
import java.util.*;

public class TetrisGridTest extends TestCase {
	
	// Provided simple clearRows() test
	// width 2, height 3 grid
	public void testClear1() {
		boolean[][] before =
		{	
			{true, true, false, },
			{false, true, true, }
		};
		
		boolean[][] after =
		{	
			{true, false, false},
			{false, true, false}
		};
		
		TetrisGrid tetris = new TetrisGrid(before);
		tetris.clearRows();

		assertTrue( Arrays.deepEquals(after, tetris.getGrid()) );
	}

	public void testClear2(){
		boolean[][] before_1 = {
				{true, true, true},
				{true, true, true}
		};

		boolean[][] after_1 = {
				{false, false, false},
				{false, false, false}
		};

		TetrisGrid tetris_1 = new TetrisGrid(before_1);
		tetris_1.clearRows();

		//Testing on grid containing only full rows
		assertTrue(Arrays.deepEquals(tetris_1.getGrid(), after_1));

		boolean[][] before_2 = {
				{false, false, false},
				{false, false, false}
		};

		boolean[][] after_2 = {
				{false, false, false},
				{false, false, false}
		};

		TetrisGrid tetris_2 = new TetrisGrid(before_2);
		tetris_2.clearRows();

		//Testing on a grid without a single full row
		assertTrue(Arrays.deepEquals(tetris_2.getGrid(), after_2));
	}

	public void testClear3(){
		boolean[][] before = {
				{true}
		};

		boolean[][] after = {
				{false}
		};

		TetrisGrid tetris = new TetrisGrid(before);
		tetris.clearRows();

		//Testing on a grid with 1x1 size
		assertTrue(Arrays.deepEquals(tetris.getGrid(), after));
	}

	public void testClear4(){
		boolean[][] before =
				{
						{true, true, false, true, true, true},
						{false, true, true, true, false, true},
						{true, true, false, true, false, true}
				};

		boolean[][] after =
				{
						{true, false, true, false, false, false},
						{false, true, false, false, false, false},
						{true, false, false, false, false, false}
				};

		TetrisGrid tetris = new TetrisGrid(before);
		tetris.clearRows();

		//Testing on a grid with multiple full rows but not all of them
		assertTrue(Arrays.deepEquals(tetris.getGrid(), after));
	}

	public void testClear5(){
		boolean[][] before_1 =
				{
						{true},
						{true},
						{true},
						{true},
						{true}
				};

		boolean[][] after_1 =
				{
						{false},
						{false},
						{false},
						{false},
						{false}
				};

		TetrisGrid tetris_1 = new TetrisGrid(before_1);
		tetris_1.clearRows();

		//Testing on a grid with a single full column
		assertTrue(Arrays.deepEquals(tetris_1.getGrid(), after_1));


		boolean[][] before_2 =
				{
						{false},
						{true},
						{false},
						{true},
						{false}
				};

		boolean[][] after_2 =
				{
						{false},
						{true},
						{false},
						{true},
						{false}
				};

		TetrisGrid tetris_2 = new TetrisGrid(before_2);
		tetris_2.clearRows();

		//Testing on a grid with a single non-full column
		assertTrue(Arrays.deepEquals(tetris_2.getGrid(), after_2));
	}

	public void testClear6(){
		boolean[][] before_1 =
				{
						{true, true, true, true, true}
				};

		boolean[][] after_1 =
				{
						{false, false, false, false, false}
				};

		TetrisGrid tetris_1 = new TetrisGrid(before_1);
		tetris_1.clearRows();

		//Testing on a grid with single row with every full column
		assertTrue(Arrays.deepEquals(tetris_1.getGrid(), after_1));

		boolean[][] before_2 =
				{
						{false, true, false, true, false}
				};

		boolean[][] after_2 =
				{
						{false, false, false, false, false}
				};

		TetrisGrid tetris_2 = new TetrisGrid(before_2);
		tetris_2.clearRows();

		//Testing on a grid with single row with some full columns
		assertTrue(Arrays.deepEquals(tetris_2.getGrid(), after_2));
	}

}
