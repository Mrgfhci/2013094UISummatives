package Zodiac_Fortune_2;
import Zodiac_Fortune_2.Astronaut;
import Zodiac_Fortune_2.Board;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Frame extends JFrame {
    //Sets the dimensions of the frame and names it.
    

    public Frame() throws Exception {
        add(new Board());
        setTitle("Zodiac Fortune");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(855, 600);
        this.setResizable(true);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        Astronaut astro = new Astronaut();
        JPanel PanFLashLight = new JPanel();
    }
}
