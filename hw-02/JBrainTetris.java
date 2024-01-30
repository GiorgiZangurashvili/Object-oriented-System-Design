import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class JBrainTetris extends JTetris{
    private DefaultBrain def_brain;
    private JCheckBox brain_mode;
    private int count;
    private Brain.Move move;
    private JPanel little;
    private JSlider adversary;
    private JLabel status;

    /**
     * Creates a new JTetris where each tetris square
     * is drawn with the given number of pixels.
     *
     * @param pixels
     */
    JBrainTetris(int pixels) {
        super(pixels);
        def_brain = new DefaultBrain();
        this.count = 0;
    }

    @Override
    public JComponent createControlPanel(){
        JPanel j_panel = (JPanel)super.createControlPanel();

        j_panel.add(new JLabel("Brain: "));
        brain_mode = new JCheckBox("Brain active");
        brain_mode.setSelected(false);
        j_panel.add(brain_mode);

        little = new JPanel();
        little.add(new JLabel("Adversary: "));
        adversary = new JSlider(0, 100, 0);
        adversary.setPreferredSize(new Dimension(100, 15));
        little.add(adversary);
        status = new JLabel("ok");
        little.add(status);
        j_panel.add(little);


        return j_panel;
    }

    @Override
    public void tick(int verb){
       if(brain_mode.isSelected() && verb == JTetris.DOWN){
           if(super.count != this.count){
               this.count = super.count;
               super.board.undo();
               move = def_brain.bestMove(super.board, super.currentPiece, super.board.getHeight(), move);
           }

           //if move exists brain moves piece left/right and rotates
           // it according to move components
           if(move != null){
               if(super.currentX > move.x){
                   super.tick(JTetris.LEFT);
               }else if(super.currentX < move.x){
                   super.tick(JTetris.RIGHT);
               }

               if(!move.piece.equals(super.currentPiece)){
                   super.tick(JTetris.ROTATE);
               }
           }
       }
       super.tick(verb);
    }

    @Override
    public Piece pickNextPiece(){
        if(super.random.nextInt(99) >= adversary.getValue()){
            status.setText(" ok ");
            return super.pickNextPiece();
        }else{
            status.setText("*ok*");
            double max = Double.MIN_VALUE;
            Piece result = null;
            for(int i = 0; i < super.pieces.length; i++){
                board.undo();
                Brain.Move move = def_brain.bestMove(super.board, super.pieces[i], super.getHeight(), null);
                if(move != null && move.score > max) {
                    max = move.score;
                    result = super.pieces[i];
                }
            }
            return result;
        }
    }

    public static void main(String[] args){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) { }

        JBrainTetris j_brain_tetris = new JBrainTetris(16);
        JFrame frame = JTetris.createFrame(j_brain_tetris);
        frame.setVisible(true);
    }
}
