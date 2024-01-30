import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseGUI extends JFrame {
    private JTextField metropolis;
    private JTextField continent;
    private JTextField population;
    private JTable table;
    private JButton add;
    private JButton search;
    private JComboBox<String> search_op_1;
    private JComboBox<String> search_op_2;
    private static final int TEXT_SIZE = 15;
    private MyTableModel my_table_model;
    private DataBase db;
    private static final int D_WIDTH = 500;
    private static final int D_HEIGHT = 550;

    public DatabaseGUI() throws SQLException, ClassNotFoundException {
        super("Metropolis");
        setLayout(new BorderLayout(4, 4));

        initialize_variables();
        add_boxes();
        add_jscroll();

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ResultSet rs = db.add(metropolis.getText(), continent.getText(), population.getText());
                    my_table_model.set_ResultSet(rs);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ResultSet rs = db.search(metropolis.getText(), continent.getText(), population.getText(), (String)search_op_1.getSelectedItem(),
                            (String)search_op_2.getSelectedItem());
                    my_table_model.set_ResultSet(rs);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private void initialize_variables() throws SQLException, ClassNotFoundException {
        this.metropolis = new JTextField(TEXT_SIZE);
        this.continent = new JTextField(TEXT_SIZE);
        this.population = new JTextField(TEXT_SIZE);
        this.add = new JButton("Add");
        this.search = new JButton("Search");
        this.search_op_1 = new JComboBox<>();
        this.search_op_1.addItem("Population Larger Than");
        this.search_op_1.addItem("Population Smaller Than");
        this.search_op_2 = new JComboBox<>();
        this.search_op_2.addItem("Exact Match");
        this.search_op_2.addItem("Partial Match");
        db = new DataBase();
        my_table_model = new MyTableModel();
        table = new JTable(my_table_model);
    }

    private void add_boxes(){
        Box upper_box = Box.createHorizontalBox();

        upper_box.add(new JLabel(" Metropolis: "));
        upper_box.add(this.metropolis);
        upper_box.add(new JLabel(" Continent: "));
        upper_box.add(this.continent);
        upper_box.add(new JLabel(" Population: "));
        upper_box.add(this.population);

        add(upper_box, BorderLayout.NORTH);


        Box right_box = Box.createVerticalBox();

        right_box.add(this.add);
        right_box.add(this.search);

        Box matrioshka = Box.createVerticalBox();
        matrioshka.setBorder(new TitledBorder("Search Options"));
        matrioshka.add(this.search_op_1);
        matrioshka.add(this.search_op_2);
        right_box.add(matrioshka);

        add(right_box, BorderLayout.EAST);
    }

    private void add_jscroll(){
        JScrollPane j_scroll = new JScrollPane(table);
        j_scroll.setPreferredSize(new Dimension(D_WIDTH, D_HEIGHT));
        add(j_scroll, BorderLayout.CENTER);
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        // GUI Look And Feel
        // Do this incantation at the start of main() to tell Swing
        // to use the GUI LookAndFeel of the native platform. It's ok
        // to ignore the exception.
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) { }

        DatabaseGUI dbGUI = new DatabaseGUI();
    }
}
