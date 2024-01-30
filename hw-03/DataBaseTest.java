import junit.framework.TestCase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseTest extends TestCase {
    private static final int COL_COUNT = 3;
    private static final String CREATE_TABLE = "CREATE TABLE metropolises (metropolis CHAR(64), continent CHAR(64), population BIGINT);";
    private static final String INITIAL_INSERT = "INSERT INTO metropolises VALUES" +
            "('Mumbai', 'Asia', 20400000)," +
            "('New York', 'North America', 5780000)," +
            "('San Francisco', 'North America', 5780000)," +
            "('London', 'Europe', 8580000)," +
            "('Rome', 'Europe', 2715000)," +
            "('Melbourne', 'Australia', 3900000)," +
            "('San Jose', 'North America', 7354555)," +
            "('Rostov-on-Don', 'Europe', 1052000)";

    private void delete_and_create_db(DataBase db) throws SQLException {
        Statement st = db.get_connection().createStatement();
        st.execute("DROP TABLE IF EXISTS metropolises;");
        st.execute(CREATE_TABLE);
        st.executeUpdate(INITIAL_INSERT);
    }

    public void testDataBase1() throws SQLException, ClassNotFoundException {
        DataBase db = new DataBase();
        MyTableModel tm = new MyTableModel();
        delete_and_create_db(db);

        //Starting off with simple tests
        assertEquals(0, tm.getRowCount());
        assertEquals(COL_COUNT, tm.getColumnCount());
        assertNull(tm.getValueAt(1, 2));

        ResultSet rs = db.search("", "", "", "", "");
        tm.set_ResultSet(rs);

        //Testing on default query
        assertEquals(8, tm.getRowCount());
        assertEquals(COL_COUNT, tm.getColumnCount());
        assertTrue("Asia".equals(tm.getValueAt(0, 1).toString()));

        db.close_conn();
    }

    public void testDataBase2() throws SQLException, ClassNotFoundException {
        DataBase db = new DataBase();
        MyTableModel tm = new MyTableModel();
        delete_and_create_db(db);

        ResultSet rs = db.add("Kutaisi", "Europe", "150000");
        tm.set_ResultSet(rs);

        assertEquals(1, tm.getRowCount());
        assertEquals(COL_COUNT, tm.getColumnCount());
        assertTrue("Kutaisi".equals(tm.getValueAt(0, 0).toString()));

        rs = db.search("", "", "", "", "");
        tm.set_ResultSet(rs);

        assertEquals(9, tm.getRowCount());
        assertEquals(COL_COUNT, tm.getColumnCount());

        //Testing on Population Larger Than, Exact Match
        rs = db.search("Kutaisi", "", "", "Population Larger Than", "Exact Match");
        tm.set_ResultSet(rs);

        assertEquals(1, tm.getRowCount());
        assertEquals(COL_COUNT, tm.getColumnCount());
        assertTrue("Europe".equals(tm.getValueAt(0, 1).toString()));

        //Testing on Population Larger Than, Partial Match
        rs = db.search("tais", "Euro", "", "Population Larger Than", "Partial Match");
        tm.set_ResultSet(rs);

        assertEquals(1, tm.getRowCount());
        assertEquals(COL_COUNT, tm.getColumnCount());
        assertTrue("Kutaisi".equals(tm.getValueAt(0, 0).toString()));

        //Testing on Population Smaller Than, Exact Match
        rs = db.search("utai", "Europe", "", "Population Smaller Than", "Exact Match");
        tm.set_ResultSet(rs);

        assertEquals(0, tm.getRowCount());
        assertEquals(COL_COUNT, tm.getColumnCount());
        assertNull(tm.getValueAt(0, 0));

        //Testing on Population Smaller Than, Partial Match
        rs = db.search("tais", "rope", "150000", "Population Smaller Than", "Partial Match");
        tm.set_ResultSet(rs);

        assertEquals(1, tm.getRowCount());
        assertEquals(COL_COUNT, tm.getColumnCount());
        assertTrue("Kutaisi".equals(tm.getValueAt(0, 0).toString()));

        db.close_conn();
    }

    public void testDataBase3() throws SQLException, ClassNotFoundException {
        DataBase db = new DataBase();
        MyTableModel tm = new MyTableModel();
        delete_and_create_db(db);

        //Larger
        ResultSet rs = db.search("", "", "2715000", "Population Larger Than","Exact Match");
        tm.set_ResultSet(rs);

        assertEquals(7, tm.getRowCount());
        assertEquals(COL_COUNT, tm.getColumnCount());
        assertTrue("Mumbai".equals(tm.getValueAt(0, 0).toString()));

        //Smaller
        rs = db.search("", "", "2715000", "Population Smaller Than","Exact Match");
        tm.set_ResultSet(rs);

        assertEquals(2, tm.getRowCount());
        assertEquals(COL_COUNT, tm.getColumnCount());
        assertTrue("Rome".equals(tm.getValueAt(0, 0).toString()));
    }

    public void testDataBase4() throws SQLException, ClassNotFoundException {
        DataBase db = new DataBase();
        MyTableModel tm = new MyTableModel();
        delete_and_create_db(db);

        for(char i = 'a'; i < 'a' + 5; i++){
            ResultSet rs = db.add("" + i, "", "");
            tm.set_ResultSet(rs);
            assertEquals(1, tm.getRowCount());
            assertEquals(COL_COUNT, tm.getColumnCount());
            assertTrue(("" + i).equals(tm.getValueAt(0, 0).toString()));
        }

        ResultSet rs = db.search("", "", "", "", "");
        tm.set_ResultSet(rs);
        assertEquals(13, tm.getRowCount());
        assertEquals(COL_COUNT, tm.getColumnCount());
    }
}
