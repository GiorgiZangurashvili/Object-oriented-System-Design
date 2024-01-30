import junit.framework.TestCase;

import java.util.*;

/*
  Unit test for Piece class -- starter shell.
 */
public class PieceTest extends TestCase {
	// You can create data to be used in the your
	// test cases like this. For each run of a test method,
	// a new PieceTest object is created and setUp() is called
	// automatically by JUnit.
	// For example, the code below sets up some
	// pyramid and s pieces in instance variables
	// that can be used in tests.
	private Piece pyr1, pyr2, pyr3, pyr4;
	private Piece s, sRotated;

	protected void setUp() throws Exception {
		super.setUp();
		
		pyr1 = new Piece(Piece.PYRAMID_STR);
		pyr2 = pyr1.computeNextRotation();
		pyr3 = pyr2.computeNextRotation();
		pyr4 = pyr3.computeNextRotation();
		
		s = new Piece(Piece.S1_STR);
		sRotated = s.computeNextRotation();
	}
	
	// Here are some sample tests to get you started
	
	public void testSampleSize() {
		// Check size of pyr piece
		assertEquals(3, pyr1.getWidth());
		assertEquals(2, pyr1.getHeight());
		
		// Now try after rotation
		// Effectively we're testing size and rotation code here
		assertEquals(2, pyr2.getWidth());
		assertEquals(3, pyr2.getHeight());
		
		// Now try with some other piece, made a different way
		Piece l = new Piece(Piece.STICK_STR);
		assertEquals(1, l.getWidth());
		assertEquals(4, l.getHeight());
	}
	
	
	// Test the skirt returned by a few pieces
	public void testSampleSkirt() {
		// Note must use assertTrue(Arrays.equals(... as plain .equals does not work
		// right for arrays.
		assertTrue(Arrays.equals(new int[] {0, 0, 0}, pyr1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0, 1}, pyr3.getSkirt()));

		assertTrue(Arrays.equals(new int[] {0, 0, 1}, s.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0}, sRotated.getSkirt()));
	}
	
	public void testConstructor(){
		Piece stick = new Piece(Piece.STICK_STR);
		Piece l1 = new Piece(Piece.L1_STR);
		Piece l2 = new Piece(Piece.L2_STR);
		Piece s1 = new Piece(Piece.S1_STR);
		Piece s2 = new Piece(Piece.S2_STR);
		Piece square = new Piece(Piece.SQUARE_STR);

		//getWidth() tests
		assertEquals(1, stick.getWidth());
		assertEquals(2, l1.getWidth());
		assertEquals(2, l2.getWidth());
		assertEquals(3, s1.getWidth());
		assertEquals(3, s2.getWidth());
		assertEquals(2, square.getWidth());

		//getHeight() tests
		assertEquals(4, stick.getHeight());
		assertEquals(3, l1.getHeight());
		assertEquals(3, l2.getHeight());
		assertEquals(2, s1.getHeight());
		assertEquals(2, s2.getHeight());
		assertEquals(2, square.getHeight());

		//getSkirt() tests
		assertTrue(Arrays.equals(new int[] {0}, stick.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 0}, l1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 0}, l2.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 0, 1}, s1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0, 0}, s2.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 0}, square.getSkirt()));
	}

	public void testEquals(){
		//Equals should return true
		assertEquals(true, new Piece("0 1  0 2  1 0  1 1").equals(new Piece("0 1  0 2  1 0  1 1")));
		assertEquals(true, new Piece("0 0  0 1  1 0  1 1").equals(new Piece("0 0  0 1  1 0  1 1")));
		//in different order
		assertEquals(true, new Piece("0 1  0 0  1 1  1 0").equals(new Piece("0 0  0 1  1 0  1 1")));

		//Equals should return false
		assertEquals(false, new Piece("0 1  0 2  1 0  1 1").equals(new Piece("0 1  0 2  1 0  0 3")));
		assertEquals(false, new Piece("0 0  0 2  0 1  1 1").equals(new Piece("0 1  0 2  1 0  0 3")));
		assertEquals(false, new Piece("0 1  0 2  1 0  1 1").equals(null));
	}

	public void testRotation1(){
		//simple test
		assertTrue(new Piece("0 0  1 0  2 0  3 0").equals(new Piece(Piece.STICK_STR).computeNextRotation()));
		assertTrue(new Piece("0 0  1 0  2 0  2 1").equals(new Piece(Piece.L1_STR).computeNextRotation()));
		assertTrue(new Piece("0 1  1 1  2 0  2 1").equals(new Piece(Piece.L2_STR).computeNextRotation()));
		assertTrue(new Piece("0 1  0 2  1 0  1 1").equals(new Piece(Piece.S1_STR).computeNextRotation()));
		assertTrue(new Piece("0 0  0 1  1 1  1 2").equals(new Piece(Piece.S2_STR).computeNextRotation()));
		assertTrue(new Piece("0 0  0 1  1 0  1 1").equals(new Piece(Piece.SQUARE_STR).computeNextRotation()));
	}

	public void testRotation2(){
		Piece piece = new Piece(Piece.L1_STR);
		//if makeFastRotations() is not called before calling
		// fastRotation() it should return null
		assertEquals(null, piece.fastRotation());

		Piece[] pieces = Piece.getPieces();

		fastRotations(pieces, Piece.STICK);
		fastRotations(pieces, Piece.L1);
		fastRotations(pieces, Piece.L2);
		fastRotations(pieces, Piece.S1);
		fastRotations(pieces, Piece.S2);
		fastRotations(pieces, Piece.SQUARE);
	}

	private void fastRotations(Piece[] pieces, int index){
		Piece current = pieces[index];
		while(true){
			assertTrue(current.computeNextRotation().equals(current.fastRotation()));

			current = current.fastRotation();

			if(current.equals(pieces[index])){
				break;
			}
		}
	}
}
