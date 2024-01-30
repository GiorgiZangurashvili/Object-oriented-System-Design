
/*
 HW1 Taboo problem class.
 Taboo encapsulates some rules about what objects
 may not follow other objects.
 (See handout).
*/

import java.util.*;

public class Taboo<T> {

	private HashMap<T, HashSet<T>> rules;

	/**
	 * Constructs a new Taboo using the given rules (see handout.)
	 * @param rules rules for new Taboo
	 */
	public Taboo(List<T> rules) {
		this.rules = new HashMap<>();
		build_rules(this.rules, rules);
	}

	private void build_rules(HashMap<T, HashSet<T>> map, List<T> rules){
		for(int i = 1; i < rules.size(); i++){
			T first_elem = rules.get(i - 1);
			T second_elem = rules.get(i);
			if(first_elem != null && second_elem != null){
				if(map.containsKey(first_elem)){
					map.get(first_elem).add(second_elem);
				}else{
					HashSet<T> set = new HashSet<>();
					set.add(second_elem);
					map.put(first_elem, set);
				}
			}
		}
	}
	
	/**
	 * Returns the set of elements which should not follow
	 * the given element.
	 * @param elem
	 * @return elements which should not follow the given element
	 */
	public Set<T> noFollow(T elem) {
		if(this.rules.containsKey(elem)){
			return this.rules.get(elem);
		}else{
			return Collections.emptySet();
		}
	}
	
	/**
	 * Removes elements from the given list that
	 * violate the rules (see handout).
	 * @param list collection to reduce
	 */
	public void reduce(List<T> list) {
		for(int i = 1; i < list.size(); i++){
			T first_elem = list.get(i - 1);
			T second_elem = list.get(i);
			if(this.rules.containsKey(first_elem)){
				if(this.rules.get(first_elem).contains(second_elem)){
					list.remove(i);
					i--;
				}
			}
		}
	}
}
