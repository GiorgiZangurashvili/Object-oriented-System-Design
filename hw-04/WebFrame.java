import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class WebFrame extends JFrame {

    /* Constants */
    private static final int SCROLL_PANE_WIDTH = 600;
    private static final int SCROLL_PANE_HEIGHT = 300;
    private static final int FIELD_WIDTH = 150;
    private static final int FIELD_HEIGHT = 50;

    private JTable table;
    private JButton singleThread;
    private JButton concurrentFetch;
    private JButton stop;
    private JTextField numThreads;
    private JLabel running;
    private JLabel completed;
    private JLabel elapsed;
    private JProgressBar progressBar;

    private Semaphore limit;
    private AtomicInteger runningThreadCount;
    private AtomicInteger completedThreadCount;

    private Launcher launcher;
    private WebWorker[] workers;
    private WebFrame wf;

    public WebFrame() {
        super("WebLoader");
        createInterface();
        acionPerformed();
    }

    private void createInterface(){
        setLayout(new BorderLayout(4, 4));

        JPanel panel = new JPanel();
        this.wf = this;

        TableModel model = new DefaultTableModel(new String[]{"url", "status"}, 0);
        this.table = new JTable(model);
        this.table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane scrollPane = new JScrollPane(this.table);
        scrollPane.setPreferredSize(new Dimension(SCROLL_PANE_WIDTH, SCROLL_PANE_HEIGHT));
        panel.add(scrollPane);
        readFile();

        Box box = Box.createVerticalBox();

        this.launcher = null;
        this.workers = new WebWorker[model.getRowCount()];
        this.runningThreadCount = new AtomicInteger(0);
        this.completedThreadCount = new AtomicInteger(0);
        this.singleThread = new JButton("Single Thread Fetch");
        this.singleThread.setEnabled(true);
        this.concurrentFetch = new JButton("Concurrent Fetch");
        this.concurrentFetch.setEnabled(true);
        this.numThreads = new JTextField();
        this.numThreads.setMaximumSize(new Dimension(FIELD_WIDTH, FIELD_HEIGHT));
        this.running = new JLabel("Running: 0");
        this.completed = new JLabel("Completed: 0");
        this.elapsed = new JLabel("Elapsed: ");
        this.progressBar = new JProgressBar(0, model.getRowCount());
        this.stop = new JButton("Stop");
        this.stop.setEnabled(false);

        box.add(this.singleThread);
        box.add(this.concurrentFetch);
        box.add(this.numThreads);
        box.add(this.running);
        box.add(this.completed);
        box.add(this.elapsed);
        box.add(this.progressBar);
        box.add(this.stop);
        add(box, BorderLayout.SOUTH);

        add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private void acionPerformed(){
        this.singleThread.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setEnabledButtons(false, false, true);
                resetStatusStrings();
                progressBar.setMaximum(table.getModel().getRowCount());
                completedThreadCount.set(0);
                runningThreadCount.set(0);

                launcher = new Launcher(1);
                launcher.start();
            }
        });

        this.concurrentFetch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setEnabledButtons(false, false, true);
                resetStatusStrings();
                progressBar.setMaximum(table.getModel().getRowCount());
                completedThreadCount.set(0);
                runningThreadCount.set(0);
                try{
                    launcher = new Launcher(Integer.parseInt(numThreads.getText()));
                }catch(NumberFormatException exception){
                    launcher = new Launcher(1);
                }
                launcher.start();
            }
        });

        this.stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                launcher.interrupt();
            }
        });
    }

    private void resetStatusStrings(){
        for(int i = 0; i < this.table.getModel().getRowCount(); i++){
            this.table.getModel().setValueAt("", i, 1);
        }
    }

    public void threadDone(String message, int row){
        runningThreadCount.decrementAndGet();
        completedThreadCount.incrementAndGet();
        limit.release();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                running.setText("running: " + runningThreadCount.get());
                completed.setText("completed: " + completedThreadCount.get());
                progressBar.setValue(completedThreadCount.get());
                table.getModel().setValueAt(message, row, 1);
            }
        });
    }

    public static void main(String[] args){
        // GUI Look And Feel
        // Do this incantation at the start of main() to tell Swing
        // to use the GUI LookAndFeel of the native platform. It's ok
        // to ignore the exception.
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) { }

        WebFrame wf = new WebFrame();
    }

    private void readFile() {
        DefaultTableModel model = (DefaultTableModel) this.table.getModel();
        try{
            BufferedReader br = new BufferedReader(new FileReader("links2.txt"));
            while(true){
                String line = br.readLine();
                if(line == null){
                    break;
                }
                model.addRow(new String[]{line, ""});
            }
        }catch(IOException exception){
            exception.printStackTrace();
        }
    }

    public void increment(){
        runningThreadCount.incrementAndGet();
    }

    private void setEnabledButtons(boolean singleThread, boolean concurrentThread, boolean stop){
        this.singleThread.setEnabled(singleThread);
        this.concurrentFetch.setEnabled(concurrentThread);
        this.stop.setEnabled(stop);
    }

    private class Launcher extends Thread{

        public Launcher(int numWorkers){
            limit = new Semaphore(numWorkers);
        }

        @Override
        public void run(){
            long elapsedStart = System.currentTimeMillis();
            runningThreadCount.incrementAndGet();

            for(int i = 0; i < table.getModel().getRowCount(); i++){
                try {
                    limit.acquire();
                    workers[i] = new WebWorker((String)table.getModel().getValueAt(i, 0), i, wf);
                    runningThreadCount.incrementAndGet();
                    workers[i].start();
                    if(isInterrupted()){
                        interruptWorkers();
                        runningThreadCount.decrementAndGet();
                        updateGUI(elapsedStart);
                        return;
                    }
                } catch (InterruptedException e) {
                    interruptWorkers();
                    runningThreadCount.decrementAndGet();
                    updateGUI(elapsedStart);
                    return;
                }
            }

            for(int i = 0; i < table.getModel().getRowCount(); i++){
                try {
                    workers[i].join();
                } catch (InterruptedException e) {
                    interruptWorkers();
                    runningThreadCount.decrementAndGet();
                    updateGUI(elapsedStart);
                    return;
                }
            }

            runningThreadCount.decrementAndGet();
            updateGUI(elapsedStart);
        }

        private void interruptWorkers(){
            for(int i = 0; i < workers.length; i++){
                if(workers[i] != null) workers[i].interrupt();
            }
        }

        private void updateGUI(long elapsedStart){
            long elapsedEnd = System.currentTimeMillis();
            long elapsedTime = elapsedEnd - elapsedStart;
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    setEnabledButtons(true, true, false);
                    progressBar.setValue(0);
                    elapsed.setText("elapsed: " + elapsedTime);
                    running.setText("running: " + runningThreadCount.get());
                }
            });
        }
    }
}
