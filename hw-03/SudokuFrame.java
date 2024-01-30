import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;
import javax.swing.text.Document;

import java.awt.*;
import java.awt.event.*;


 public class SudokuFrame extends JFrame {

	private JTextArea source;
	private JTextArea result;
	private JButton check;
	private JCheckBox auto_check;

	public SudokuFrame() {
		super("Sudoku Solver");

        BorderLayout bl = new BorderLayout(4, 4);
		setLayout(bl);

		this.source = new JTextArea(15, 20);
		this.source.setBorder(new TitledBorder("Puzzle"));
		this.source.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
                if(auto_check.isSelected()) do_sudoku();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
                if(auto_check.isSelected()) do_sudoku();
			}

			@Override
			public void changedUpdate(DocumentEvent e) { }
		});
		add(this.source, bl.CENTER);

		this.result = new JTextArea(15, 20);
		this.result.setBorder(new TitledBorder("Solution"));
		add(this.result, bl.EAST);

		Box control_box = new Box(BoxLayout.LINE_AXIS);
		this.check = new JButton("Check");
		this.check.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				do_sudoku();
			}
		});
		this.auto_check = new JCheckBox("Auto Check");
		this.auto_check.setSelected(true);
		control_box.add(this.check);
		control_box.add(this.auto_check);
		add(control_box, bl.SOUTH);

		// Could do this:
		// setLocationByPlatform(true);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	
	public static void main(String[] args) {
		// GUI Look And Feel
		// Do this incantation at the start of main() to tell Swing
		// to use the GUI LookAndFeel of the native platform. It's ok
		// to ignore the exception.
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignored) { }

		SudokuFrame frame = new SudokuFrame();
	}

	private void do_sudoku(){
		try {
			Sudoku sudoku = new Sudoku(Sudoku.textToGrid(this.source.getText()));
			int num_solutions = sudoku.solve();
			if(num_solutions >= 0){
				String solution = sudoku.getSolutionText() + "\n" + "solutions: " + num_solutions + "\n" + "elapsed: " + sudoku.getElapsed();
				this.result.setText(solution);
			}
		}catch (Exception exception){
			this.result.setText("Parsing problem");
		}
	}
}
