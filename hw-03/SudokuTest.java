import junit.framework.TestCase;

import java.util.*;

public class SudokuTest extends TestCase{

    public void testSudoku1(){
        //Testing on sudokus with 0 solutions
        int[][] zero_solution_1 = Sudoku.stringsToGrid(
                "3 7 0 0 0 0 0 8 0",
                "0 0 1 0 9 3 0 0 0",
                "0 4 0 7 8 0 0 0 3",
                "0 9 3 8 0 1 0 1 2",
                "0 0 0 0 4 0 0 0 0",
                "5 2 0 0 0 6 7 9 0",
                "6 0 0 0 2 1 0 4 0",
                "0 0 0 5 3 0 9 0 0",
                "0 3 0 0 0 0 0 5 1");

        Sudoku sudoku = new Sudoku(zero_solution_1);

        assertEquals(0, sudoku.solve());

        int[][] zero_solution_2 = Sudoku.textToGrid(
                "295743861431865900876192543387459216612387495549216738763534189928671354154938600"
        );

        Sudoku s = new Sudoku(zero_solution_2);


        assertEquals(0, s.solve());
    }

    public void testSudoku2(){
        //Testing on sudokus with unique solution (Provided test)
        Sudoku s1 = new Sudoku(Sudoku.easyGrid);

        assertEquals(1, s1.solve());
        assertTrue(s1.getSolutionText().equals(
                "164795382\n" +
                        "287463915\n" +
                        "935281467\n" +
                        "391876524\n" +
                        "546132798\n" +
                        "728954136\n" +
                        "819647253\n" +
                        "673529841\n" +
                        "452318679\n"
        ));

        Sudoku s2 = new Sudoku(Sudoku.mediumGrid);

        assertEquals(1, s2.solve());
        assertTrue(s2.getSolutionText().equals(
               "534678912\n" +
                       "672195348\n" +
                       "198342567\n" +
                       "859761423\n" +
                       "426853791\n" +
                       "713924856\n" +
                       "961537284\n" +
                       "287419635\n" +
                       "345286179\n"
        ));

        Sudoku s3 = new Sudoku(Sudoku.hardGrid);

        assertEquals(1, s3.solve());
        assertTrue(s3.getSolutionText().equals(
            "375162489\n" +
                    "861493527\n" +
                    "249785163\n" +
                    "493857612\n" +
                    "716249835\n" +
                    "528316794\n" +
                    "657921348\n" +
                    "182534976\n" +
                    "934678251\n"
        ));
    }

    public void testSudoku3(){
        int[][] two_solutions = Sudoku.stringsToGrid(
                "295743861",
                "431865900",
                "876192543",
                "387459216",
                "612387495",
                "549216738",
                "763524189",
                "928671354",
                "154938600"
        );

        Sudoku s1 = new Sudoku(two_solutions);

        assertEquals(2, s1.solve());
        assertTrue(s1.getSolutionText().equals(
            "295743861\n" +
                    "431865927\n" +
                    "876192543\n" +
                    "387459216\n" +
                    "612387495\n" +
                    "549216738\n" +
                    "763524189\n" +
                    "928671354\n" +
                    "154938672\n"
        ) || s1.getSolutionText().equals(
                "295743861\n" +
                        "431865972\n" +
                        "876192543\n" +
                        "387459216\n" +
                        "612387495\n" +
                        "549216738\n" +
                        "763524189\n" +
                        "928671354\n" +
                        "154938627\n"
        ));
    }

    public void testSudoku4(){
        //Testing sudoku with multiple solutions (More than two)

        int[][] six_solutions = Sudoku.stringsToGrid(
                "3 0 0 0 0 0 0 8 0",
                "0 0 1 0 9 3 0 0 0",
                "0 4 0 7 8 0 0 0 3",
                "0 9 3 8 0 0 0 1 2",
                "0 0 0 0 4 0 0 0 0",
                "5 2 0 0 0 6 7 9 0",
                "6 0 0 0 2 1 0 4 0",
                "0 0 0 5 3 0 9 0 0",
                "0 3 0 0 0 0 0 5 1");
        Sudoku s1 = new Sudoku(six_solutions);

        assertEquals(6, s1.solve());
        assertTrue(Sudoku.is_solved(Sudoku.textToGrid(s1.getSolutionText())));
    }

    public void testSudoku5(){
        //Testing on sudoku with more than 100 solutions

        int[][] hundred_solutions = Sudoku.stringsToGrid(
                "000000000",
                "000000000",
                "000000000",
                "000000000",
                "000000000",
                "000000000",
                "000000000",
                "000000000",
                "000000000"
        );

        Sudoku s1 = new Sudoku(hundred_solutions);

        assertTrue(s1.solve() >= 100);
        assertTrue(Sudoku.is_solved(Sudoku.textToGrid(s1.getSolutionText())));
    }
}
