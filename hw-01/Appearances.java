import java.util.*;

public class Appearances {

	/**
	 * Returns the number of elements that appear the same number
	 * of times in both collections. Static method. (see handout).
	 * @return number of same-appearance elements
	 */
	public static <T> int sameCount(Collection<T> a, Collection<T> b) {
		HashMap<T, Integer> map1 = new HashMap<>();
		HashMap<T, Integer> map2 = new HashMap<>();

		for(T item : a){
			if(map1.containsKey(item)){
				map1.put(item, map1.get(item) + 1);
			}else{
				map1.put(item, 1);
			}
		}

		for(T item : b){
			if(map2.containsKey(item)){
				map2.put(item, map2.get(item) + 1);
			}else{
				map2.put(item, 1);
			}
		}

		int result = 0;

		for(T key : map1.keySet()){
			if(map2.containsKey(key) && map1.get(key).equals(map2.get(key))){
				result++;
			}
		}
		return result;
	}

}
