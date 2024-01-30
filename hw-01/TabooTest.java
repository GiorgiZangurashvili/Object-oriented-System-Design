// TabooTest.java
// Taboo class tests -- nothing provided.

import java.util.*;

import junit.framework.TestCase;

public class TabooTest extends TestCase {

    private boolean listEquals(List<String> a, List<String> b){
        if(a.size() != b.size()){
            return false;
        }
        for(int i = 0; i < a.size(); i++){
            if(a.get(i) != b.get(i)){
                return false;
            }
        }
        return true;
    }

    public void testTaboo1(){
        String[] arr = {"a", "c", "a", "b"};
        Taboo taboo = new Taboo(Arrays.asList(arr));

        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("c");
        list.add("b");
        list.add("x");
        list.add("c");
        list.add("a");
        taboo.reduce(list);
        ArrayList<String> res = new ArrayList<>();
        res.add("a");
        res.add("x");
        res.add("c");

        //handout test
        assertTrue(listEquals(list, res));
    }

    public void testTaboo2(){
        String[] arr = {"a", "b", null, "c", "d"};
        Taboo taboo = new Taboo(Arrays.asList(arr));

        //No Follow tests with null in rules
        assertEquals(Collections.emptySet(), taboo.noFollow("b"));
        String[] expected_1 = {"b"};
        assertTrue(Arrays.equals(taboo.noFollow("a").toArray(), expected_1));
        String[] expected_2 = {"d"};
        assertTrue(Arrays.equals(taboo.noFollow("c").toArray(), expected_2));
        assertEquals(Collections.emptySet(), taboo.noFollow("d"));
    }

    public void testTaboo3(){
        String[] arr = {"a", null, "b", null, "c", null, "d"};
        Taboo taboo = new Taboo(Arrays.asList(arr));

        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("x");
        list.add("d");
        ArrayList<String> list_2 = list;
        taboo.reduce(list);

        //Testing on rules with no restrictions because of null values
        assertEquals(list_2, list);
    }

    public void testTaboo4(){
        Taboo taboo = new Taboo(Collections.emptyList());

        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("x");
        list.add("d");
        ArrayList<String> list_2 = list;
        taboo.reduce(list);

        //Testing on rules represented by empty list
        assertEquals(list_2, list);
    }

    public void testTaboo5(){
        Integer[] arr = {1, 3, 1, 12, 1, 15};

        Taboo taboo = new Taboo(Arrays.asList(arr));

        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(15);
        list.add(12);
        list.add(3);
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(1);
        taboo.reduce(list);

        //tricky one. reduce method should delete everything but 1
        assertEquals(expected, list);
    }

    public void testTaboo6(){
        Integer[] arr = {1, 4, 5, 3, 12};

        Taboo taboo = new Taboo(Arrays.asList(arr));

        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(4);
        list.add(5);
        list.add(3);
        list.add(12);
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(5);
        expected.add(12);
        taboo.reduce(list);

        //also a tricky one. rules list equals list which should be reduced
        assertEquals(expected, list);
    }

    public void testTaboo7(){
        Integer[] arr = {1, 2, 3, 2, 1};

        Taboo taboo = new Taboo(Arrays.asList(arr));

        ArrayList<Integer> list = new ArrayList<>();
        list.add(4);
        list.add(3);
        list.add(11);
        list.add(2);
        list.add(3);
        list.add(1);
        list.add(2);
        list.add(3);
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(4);
        expected.add(3);
        expected.add(11);
        expected.add(2);
        expected.add(2);
        taboo.reduce(list);

        //Testing on list of rules with palindromic sequence
        assertEquals(expected, list);
    }
}
