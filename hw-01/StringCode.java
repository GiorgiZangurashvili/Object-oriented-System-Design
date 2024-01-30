import java.util.HashSet;
import java.util.Set;

// CS108 HW1 -- String static methods

public class StringCode {

	/**
	 * Given a string, returns the length of the largest run.
	 * A a run is a series of adajcent chars that are the same.
	 * @param str
	 * @return max run length
	 */
	public static int maxRun(String str) {
		int max = Integer.MIN_VALUE;
		int count = 1;

		for(int i = 1; i < str.length(); i++){
			char prev = str.charAt(i - 1);
			char present = str.charAt(i);
			if(prev == present && i != str.length() - 1){
				count++;
			}else if(prev == present && i == str.length() - 1){
				count++;
				if(count > max) {
					max = count;
				}
			}else{
				if(count > max){
					max = count;
				}

				count = 1;
			}
		}

		if(max == Integer.MIN_VALUE){
			return 0;
		}
		return max;
	}

	
	/**
	 * Given a string, for each digit in the original string,
	 * replaces the digit with that many occurrences of the character
	 * following. So the string "a3tx2z" yields "attttxzzz".
	 * @param str
	 * @return blown up string
	 */
	public static String blowup(String str) {
		String result = "";

		for(int i = 0; i < str.length(); i++){
			if(Character.isDigit(str.charAt(i)) && i != str.length() - 1){
				int count = str.charAt(i) - '0';
				for(int j = 0; j < count; j++){
					result += str.charAt(i + 1);
				}
			}else if(Character.isDigit(str.charAt(i)) && i == str.length() - 1){
				return result;
			}else{
				result += str.charAt(i);
			}
		}

		return result;
	}
	
	/**
	 * Given 2 strings, consider all the substrings within them
	 * of length len. Returns true if there are any such substrings
	 * which appear in both strings.
	 * Compute this in linear time using a HashSet. Len will be 1 or more.
	 */
	public static boolean stringIntersect(String a, String b, int len) {
		HashSet<String> substrings = new HashSet<>();

		for(int i = 0; i <= a.length(); i++){
			if(i + len <= a.length()){
				substrings.add(a.substring(i, i + len));
			}
		}

		for(int i = 0; i <= b.length(); i++){
			if(i + len <= b.length() && substrings.contains(b.substring(i, i + len))){
				return true;
			}
		}

		return false;
	}
}
