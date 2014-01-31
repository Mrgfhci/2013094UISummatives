package Zodiac_Fortune;
import java.io.IOException;
import javax.swing.JFrame;

public class Zodiac_Main extends JFrame {
    
    public Zodiac_Main() throws IOException {
        add(new PanAstroMonster());
        setTitle("Zodiac Fortune");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(846, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        new Zodiac_Main();
    }
}
