import junit.framework.TestCase;

import java.util.*;

public class AppearancesTest extends TestCase {
	// utility -- converts a string to a list with one
	// elem for each char.
	private List<String> stringToList(String s) {
		List<String> list = new ArrayList<String>();
		for (int i=0; i<s.length(); i++) {
			list.add(String.valueOf(s.charAt(i)));
			// note: String.valueOf() converts lots of things to string form
		}
		return list;
	}

	public void testSameCount1() {
		List<String> a = stringToList("abbccc");
		List<String> b = stringToList("cccbba");
		assertEquals(3, Appearances.sameCount(a, b));
	}

	public void testSameCount2() {
		// basic List<Integer> cases
		List<Integer> a = Arrays.asList(1, 2, 3, 1, 2, 3, 5);
		assertEquals(1, Appearances.sameCount(a, Arrays.asList(1, 9, 9, 1)));
		assertEquals(2, Appearances.sameCount(a, Arrays.asList(1, 3, 3, 1)));
		assertEquals(1, Appearances.sameCount(a, Arrays.asList(1, 3, 3, 1, 1)));
	}

	public void testSameCount3(){
		assertEquals(0, Appearances.sameCount(Arrays.asList(1, 3, 5, 7), Arrays.asList(2, 4, 6, 8)));
		assertEquals(0, Appearances.sameCount(Arrays.asList(), Arrays.asList("hello", "world", "!")));
		assertEquals(0, Appearances.sameCount(Arrays.asList('c', 'h', 'e', 's', 's'), Arrays.asList()));
		assertEquals(0, Appearances.sameCount(Arrays.asList(), Arrays.asList()));
	}

	public void testSameCount4(){
		assertEquals(3, Appearances.sameCount(Arrays.asList(5, 4, 7, 5, 5, 4), Arrays.asList(7, 5, 4, 5, 4, 5)));
		assertEquals(1, Appearances.sameCount(Arrays.asList("koniaki", "chemi", "siyvarulia", "!", "!", "!"), Arrays.asList("aseve", "ludic", "miyvars", "!", "!", "!")));
	}

	public void testSameCount5(){
		assertEquals(0, Appearances.sameCount(Arrays.asList(5, 5, 3, 3, 2, 2, 2), Arrays.asList(2, 3, 2, 5, 4, 4)));
		assertEquals(5, Appearances.sameCount(Arrays.asList(1, 2, 3, 4, 5), Arrays.asList(5, 4, 3, 2, 1)));
	}
}
