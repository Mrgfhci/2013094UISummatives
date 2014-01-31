package Test_Flashlight_Combo;
import java.awt.*;
import java.io.*;
import javax.swing.*;

public class Zodiac_Main extends JFrame {

    public Zodiac_Main() throws IOException{//Because of the next two comments the unlisted problem must have to do with PanFlashlight
        add(new PanAstroMonster());//Removal of PanAstroMonster keeps with the blank screen
        add(new PanFlashlight());//Removal of PanFlashlight allows the PanAstroMonster program to work 
        setTitle("Zodiac Fortune");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(846, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) throws IOException{
        new Zodiac_Main();
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }
            }
        });
    }
}
