import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.sql.*;

public class DataBase{

    private Connection conn;
    private static final String DEFAULT_QUERY = "SELECT * FROM metropolises;";
    private static final String EXACT_MATCH = "Exact Match";
    private static final String POP_LARGER = "Population Larger Than";

    public DataBase() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/metropolises", "root", "");
    }

    public void close_conn() throws SQLException {
        this.conn.close();
    }

    public Connection get_connection(){
        return conn;
    }

    public ResultSet add(String metropolis, String continent, String population) throws SQLException {
        String insert = "";
        if(!population.isEmpty()) {
            insert = "INSERT INTO metropolises (metropolis, continent, population) VALUES ('" +
                    metropolis + "', '" + continent + "', '" + population + "');";
        }else{
            insert = "INSERT INTO metropolises (metropolis, continent, population) VALUES ('" +
                    metropolis + "', '" + continent + "', '" + 0 + "');";
        }
        Statement st = this.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        st.executeUpdate(insert);


        String query = get_query(metropolis, continent, population, POP_LARGER, EXACT_MATCH);

        ResultSet rs = st.executeQuery(query);

        return rs;
    }

    public ResultSet search(String metropolis, String continent, String population, String op_1, String op_2) throws SQLException {
        ResultSet rs = null;
        Statement st = this.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        if(metropolis.isEmpty() && continent.isEmpty() && population.isEmpty()){
            rs = st.executeQuery(DEFAULT_QUERY);
            return rs;
        }

        String query = get_query(metropolis, continent, population, op_1, op_2);

        rs = st.executeQuery(query);

        return rs;
    }

    private String get_query(String metropolis, String continent, String population, String op_1, String op_2){
        String query = "SELECT * FROM metropolises m WHERE";

        if(op_1.equals(POP_LARGER)){
            if(op_2.equals(EXACT_MATCH)){
                if(!metropolis.isEmpty()) query += " m.metropolis = '" + metropolis + "' ";
                if(!continent.isEmpty()){
                    if(!metropolis.isEmpty()) query += "AND";
                    query += " m.continent = '" + continent + "' ";
                }
            }else {
                if(!metropolis.isEmpty()) query += " m.metropolis LIKE '%" + metropolis + "%' ";
                if(!continent.isEmpty()){
                    if(!metropolis.isEmpty()) query += "AND";
                    query += " m.continent LIKE '%" + continent + "%' ";
                }
            }
            if(!(metropolis.isEmpty() && continent.isEmpty()) && !population.isEmpty()){
                query += "AND";
                query += " m.population >= " + population + "";
            }else if(!population.isEmpty()){
                query += " m.population >= " + population + "";
            }
        }else{
            if(op_2.equals(EXACT_MATCH)){
                if(!metropolis.isEmpty()) query += " m.metropolis = '" + metropolis + "' ";
                if(!continent.isEmpty()){
                    if(!metropolis.isEmpty()) query += "AND";
                    query += " m.continent = '" + continent + "' ";
                }
            }else {
                if(!metropolis.isEmpty()) query += " m.metropolis LIKE '%" + metropolis + "%' ";
                if(!continent.isEmpty()){
                    if(!metropolis.isEmpty()) query += "AND";
                    query += " m.continent LIKE '%" + continent + "%' ";
                }
            }
            if(!(metropolis.isEmpty() && continent.isEmpty()) && !population.isEmpty()){
                query += "AND";
                query += " m.population <= " + population + "";
            }else if(!population.isEmpty()){
                query += " m.population <= " + population + "";
            }
        }

        query += ";";

        return query;
    }
}
