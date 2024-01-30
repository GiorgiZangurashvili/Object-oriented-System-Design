// StringCodeTest
// Some test code is provided for the early HW1 problems,
// and much is left for you to add.

import junit.framework.TestCase;

public class StringCodeTest extends TestCase {
	//
	// blowup
	//
	public void testBlowup1() {
		// basic cases
		assertEquals("xxaaaabb", StringCode.blowup("xx3abb"));
		assertEquals("xxxZZZZ", StringCode.blowup("2x3Z"));
	}

	public void testBlowup2() {
		// things with digits

		// digit at end
		assertEquals("axxx", StringCode.blowup("a2x3"));

		// digits next to each other
		assertEquals("a33111", StringCode.blowup("a231"));

		// try a 0
		assertEquals("aabb", StringCode.blowup("aa0bb"));
	}

	public void testBlowup3() {
		// weird chars, empty string
		assertEquals("AB&&,- ab", StringCode.blowup("AB&&,- ab"));
		assertEquals("", StringCode.blowup(""));

		// string with only digits
		assertEquals("", StringCode.blowup("2"));
		assertEquals("33", StringCode.blowup("23"));
		assertEquals("0001", StringCode.blowup("3011"));
	}

	public void testBlowup4(){
		//0s cases
		assertEquals("", StringCode.blowup("01"));
		assertEquals("a330001", StringCode.blowup("0a023011"));
		assertEquals("0", StringCode.blowup("00000010"));
		assertEquals("", StringCode.blowup("00000000"));
	}


	//
	// maxRun
	//
	public void testRun1() {
		assertEquals(2, StringCode.maxRun("hoopla"));
		assertEquals(3, StringCode.maxRun("hoopllla"));
	}

	public void testRun2() {
		assertEquals(3, StringCode.maxRun("abbcccddbbbxx"));
		assertEquals(0, StringCode.maxRun(""));
		assertEquals(3, StringCode.maxRun("hhhooppoo"));
	}

	public void testRun3() {
		// "evolve" technique -- make a series of test cases
		// where each is change from the one above.
		assertEquals(1, StringCode.maxRun("123"));
		assertEquals(2, StringCode.maxRun("1223"));
		assertEquals(2, StringCode.maxRun("112233"));
		assertEquals(3, StringCode.maxRun("1112233"));
	}

	public void testRun4(){
		//testing on repeating characters in the end of strings
		assertEquals(6, StringCode.maxRun("555555666666"));
		assertEquals(4, StringCode.maxRun("1223334444"));
		assertEquals(3, StringCode.maxRun("aaabbb"));
	}

	// Need test cases for stringIntersect
	public void testIntersect1(){
		assertEquals(false, StringCode.stringIntersect("ab", "", 2));
		assertEquals(true, StringCode.stringIntersect("ab", "", 0));
		assertEquals(false, StringCode.stringIntersect("", "ab", 2));
		assertEquals(true, StringCode.stringIntersect("", "ab", 0));
	}

	public void testIntersect2(){
		//testing on substring lengths which are also lengths of strings
		assertEquals(true, StringCode.stringIntersect("bzxttay", "aaaaabbzxttay", 7));
		assertEquals(true, StringCode.stringIntersect("aaaaabbzxttay", "bzxttay", 7));
		assertEquals(true, StringCode.stringIntersect("bzxttay", "bzxttay", 7));
	}

	public void testIntersect3(){
		//testing on strings with length less than given len (substring length)
		assertEquals(false, StringCode.stringIntersect("abc", "zbcd", 5));
		assertEquals(false,StringCode.stringIntersect("abc", "zbcdytad", 5));
		assertEquals(false,StringCode.stringIntersect("zbcdytad", "abc", 5));
	}

	public void testIntersect4(){
		assertEquals(false, StringCode.stringIntersect("abc", "zbcd", 3));
		assertEquals(false, StringCode.stringIntersect("das is fantastish", "anksunamun", 5));
		assertEquals(false, StringCode.stringIntersect("messi", "ronaldo", 2));
		assertEquals(true, StringCode.stringIntersect("rickroll", "troll", 4));
	}

	public void testIntersect5(){
		//empty string is a substring of all strings, so it is important that our method returns true
		//after we pass 0 as len parameter
		assertEquals(true, StringCode.stringIntersect("kagnakjntai", "kangajgi", 0));
		assertEquals(true, StringCode.stringIntersect("", "", 0));
		assertEquals(true, StringCode.stringIntersect("", "a", 0));
		assertEquals(true, StringCode.stringIntersect("a", "", 0));
	}
}
