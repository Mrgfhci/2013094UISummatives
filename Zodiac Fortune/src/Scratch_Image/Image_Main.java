package Scratch_Image;
import java.io.IOException;
import javax.swing.JFrame;


public class Image_Main extends JFrame {
    
    public Image_Main() throws IOException {
        add(new PanImage());        
        setTitle("Images");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(846, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        new Image_Main();
    }
}