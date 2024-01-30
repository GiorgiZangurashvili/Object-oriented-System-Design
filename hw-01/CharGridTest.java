
// Test cases for CharGrid -- a few basic tests are provided.

import junit.framework.TestCase;

public class CharGridTest extends TestCase {

	public void testCharArea1() {
		char[][] grid = new char[][] {
				{'a', 'y', ' '},
				{'x', 'a', 'z'},
		};


		CharGrid cg = new CharGrid(grid);

		assertEquals(4, cg.charArea('a'));
		assertEquals(1, cg.charArea('z'));
	}


	public void testCharArea2() {
		char[][] grid = new char[][] {
				{'c', 'a', ' '},
				{'b', ' ', 'b'},
				{' ', ' ', 'a'}
		};

		CharGrid cg = new CharGrid(grid);

		assertEquals(6, cg.charArea('a'));
		assertEquals(3, cg.charArea('b'));
		assertEquals(1, cg.charArea('c'));
	}

	public void testCharArea3(){
		char[][] grid = new char[][] {
				{'t', 'b', 'c'},
				{'c', 'b', 'a'},
				{' ', ' ', 't'}
		};

		CharGrid cg = new CharGrid(grid);

		//testing with character which doesn't appear in grid
		assertEquals(0, cg.charArea('z'));
		//testing for space (ik this test sucks but i couldn't come up with better)
		assertEquals(2, cg.charArea(' '));

		assertEquals(9, cg.charArea('t'));
	}

	public void testCharArea4(){
		char[][] grid = new char[][] {

		};

		CharGrid cg = new CharGrid(grid);

		//testing on empty grid
		assertEquals(0, cg.charArea('7'));
	}

	public void testCharArea5(){
		char[][] grid_1 = new char[][] {
				{'a', 'b', 'a', 'c'}
		};

		CharGrid cg_1 = new CharGrid(grid_1);

		//testing on grid with single row
		assertEquals(3, cg_1.charArea('a'));
		assertEquals(1, cg_1.charArea('b'));
		assertEquals(1, cg_1.charArea('c'));
		assertEquals(0, cg_1.charArea('$'));

		char[][] grid_2 = new char[][] {
				{'a'},
				{'a'},
				{'y'},
				{'a'},
		};

		CharGrid cg_2 = new CharGrid(grid_2);

		//testing on grid with single column
		assertEquals(4, cg_2.charArea('a'));
		assertEquals(1, cg_2.charArea('y'));
		assertEquals(0, cg_2.charArea('%'));

		char [][] grid_3 = new char[][] {
				{'a'}
		};

		//grid with single element
		CharGrid cg_3 = new CharGrid(grid_3);
		assertEquals(1, cg_3.charArea('a'));
	}

	public void testCharArea6(){
		char[][] grid = new char[][] {
				{'a', 't', 'b', 'b'},
				{'z', 'b', 't', 'z'},
				{'m', 't', 't', 'b'}
		};

		CharGrid cg = new CharGrid(grid);

		//testing on different patterns
		assertEquals(9, cg.charArea('b'));
		assertEquals(6, cg.charArea('t'));
		assertEquals(4, cg.charArea('z'));
	}

	public void testCountPlus1(){
		char[][] grid_1 = new char[][] {
				{'a', 'p', 'b', 'c', 'x', 'd', 'e', 'f'},
				{'p', 'p', 'p', 'g', 'x', 'h', 'i', 'j'},
				{'k', 'p', 'l', 'm', 'x', 'n', 'o', 'p'},
				{'q', 'x', 'x', 'x', 'x', 'x', 'x', 'x'},
				{'s', 't', 'u', 'v', 'x', 'w', 'x', 'y'},
				{'z', 'a', 'b', 'c', 'x', 'd', 'e', 'f'},
				{'g', 'h', 'i', 'j', 'x', 'j', 'l', 'm'}
		};

		char[][] grid_2 = new char[][] {
				{' ', 'x', ' '},
				{'x', 'x', 'x'},
				{' ', 'x', ' '}
		};

		CharGrid cg_1 = new CharGrid(grid_1);
		assertEquals(2, cg_1.countPlus());

		CharGrid cg_2 = new CharGrid(grid_2);
		assertEquals(1, cg_2.countPlus());
	}

	public void testCountPlus2(){
		char [][] grid = new char[][] {

		};

		//empty grid test
		CharGrid cg = new CharGrid(grid);
		assertEquals(0, cg.countPlus());
	}

	public void testCountPlus3(){
		char [][] grid = new char[][] {
				{'a', 'a', 'a', 'a'},
				{'a', 'a', 'a', 'a'},
				{'a', 'a', 'a', 'a'},
				{'a', 'a', 'a', 'a'}
		};

		//grid full of repetitive (single) character
		CharGrid cg = new CharGrid(grid);
		assertEquals(0, cg.countPlus());
	}

	public void testCountPlus4(){
		char [][] grid = new char[][] {
				{'b', 'c', 'a', 'd'},
				{'e', 'a', 'a', 'a'},
				{'a', 'a', 'a', 'f'},
				{'x', 'a', 'y', 'a'}
		};

		//intersecting pluses test
		CharGrid cg = new CharGrid(grid);
		assertEquals(2, cg.countPlus());
	}

	public void testCountPlus5(){
		char [][] grid_1 = new char[][] {
				{'a', 'a', 'a'}
		};

		//grid with single row
		CharGrid cg_1 = new CharGrid(grid_1);
		assertEquals(0, cg_1.countPlus());

		char [][] grid_2 = new char[][] {
				{'a'},
				{'a'},
				{'a'}
		};

		//grid with single column
		CharGrid cg_2 = new CharGrid(grid_2);
		assertEquals(0, cg_2.countPlus());

		char [][] grid_3 = new char[][] {
				{'a'}
		};

		//grid with single element
		CharGrid cg_3 = new CharGrid(grid_3);
		assertEquals(0, cg_3.countPlus());
	}
}
