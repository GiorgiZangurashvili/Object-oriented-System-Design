// JCount.java

/*
 Basic GUI/Threading exercise.
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JCount extends JPanel {
	private JTextField num_field;
	private JLabel label;
	private JButton start_button;
	private JButton stop_button;
	private static final int FIELD_SIZE = 10;
	private Worker worker;

	public JCount() {
		// Set the JCount to use Box layout
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        worker = null;
		num_field = new JTextField(FIELD_SIZE);
		add(num_field);
		label = new JLabel("0");
		add(label);
		start_button = new JButton("Start");
		start_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(worker != null) worker.interrupt();

				worker = new Worker(label, num_field);
				worker.start();
			}
		});
		add(start_button);
		stop_button = new JButton("Stop");
		stop_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(worker != null) {
					worker.interrupt();
					worker = null;
				}
			}
		});
		add(stop_button);

		add(Box.createRigidArea(new Dimension(0, 40)));
	}
	
	static public void main(String[] args)  {
		// Creates a frame with 4 JCounts in it.
		// (provided)
		JFrame frame = new JFrame("The Count");
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

		frame.add(new JCount());
		frame.add(new JCount());
		frame.add(new JCount());
		frame.add(new JCount());

		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	private class Worker extends Thread{
		private int count = 0;
		private static final int PAUSE_TIME = 10000;
		private JLabel label;
		private JTextField tField;

		public Worker(JLabel label, JTextField tField){
			this.label = label;
			this.tField = tField;
		}

		@Override
		public void run(){
			String txt = "0";

			long num = Long.parseLong(tField.getText());
			num = Long.min((long)Integer.MAX_VALUE, num);
			for(int i = 0; i <= num; i++){
				count = i;
				if(count % PAUSE_TIME == 0){
					try {
						txt = "" + count;
                        final String Txt = txt;
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                label.setText(Txt);
                            }
                        });
						Thread.sleep(100);
					} catch (InterruptedException e) {
						break;
					}
				}

				if(isInterrupted()){
					break;
				}
			}
		}
	}
}

