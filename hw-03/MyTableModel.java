import javax.swing.table.AbstractTableModel;
import java.sql.*;
import java.util.Properties;

public class MyTableModel extends AbstractTableModel {
    private String[] col_names = {"Metropolis", "Continent", "Population"};
    private int row;
    private ResultSet rs;

    public MyTableModel() throws ClassNotFoundException, SQLException {
        this.row = 0;
        this.rs = null;
    }

    public void set_ResultSet(ResultSet rs) throws SQLException {
        this.rs = rs;
        if(!rs.next()) this.rs = null;
        try{
            update_row(rs);
        }catch(SQLException ex){}
        fireTableDataChanged();
    }

    private void update_row(ResultSet rs) throws SQLException {
        rs.last();
        this.row = rs.getRow();
    }

    @Override
    public String getColumnName(int column) {
        return col_names[column];
    }

    @Override
    public int getRowCount() {
        return this.row;
    }

    @Override
    public int getColumnCount() {
        return col_names.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex){
       if(this.rs == null){
           return null;
       }
       Object value = null;

        try {
            this.rs.absolute(rowIndex + 1);
            value = this.rs.getObject(columnIndex + 1);
        } catch (SQLException e) {}

        return value;
    }
}
