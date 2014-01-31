package Build9;

import java.awt.CardLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;

public class Main {

    public static int FRAME_WIDTH = 1280, FRAME_HEIGHT = 720;
    private static final String sDeath = "Death", sGame = "Game", sWin = "Win",
            sMenu1 = "Menu1", sMenu2 = "Menu2", sMenu3 = "Menu3";
    private static JFrame frame;
    private static PanGame panGame;
    private static Hero hero;
    private static Sorcerer1 sor1;
    private static Sorcerer2 sor2;
    private static Knight knight;
    private static Boss boss;
    private static JLabel lblDeath, lblWin, lblTitle, lblControls, lblC1, lblC2;
    private final static JPanel cards = new JPanel(new CardLayout());
    private static JPanel panDeath, panWin, panMenu1, panMenu2, panMenu3;
    private static CardLayout cl;
    private static final JButton btnPlay = new JButton("PLAY"),
            btnInst = new JButton("HOW TO PLAY"), btnBack1 = new JButton("BACK"),
            btnBack2 = new JButton("BACK"), btnCredits = new JButton("CREDITS");
    private static Clip clipForever;
    private static AudioInputStream AISForever;

    //Card Layout - http://www.zentut.com/java-swing/java-swing-cardlayout/
    public static void main(String[] args) throws Exception {
        frame = new JFrame();
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setResizable(false);
        frame.setTitle("Roman Fury");
        frame.setIconImage(ImageIO.read(BufferedImage.class.getResourceAsStream("/Icon.png")));
        hero = new Hero();
        sor1 = new Sorcerer1();
        sor2 = new Sorcerer2();
        knight = new Knight();
        boss = new Boss();
        panGame = new PanGame();
        panDeath = new JPanel();
        panWin = new JPanel();
        panMenu1 = new JPanel();
        panMenu2 = new JPanel();
        panMenu3 = new JPanel();
        panDeath.setLayout(new BorderLayout());
        panWin.setLayout(new BorderLayout());
        panMenu1.setLayout(new GridLayout(4, 1, 0, 5));
        panMenu2.setLayout(new GridLayout(3, 1, 0, 9));
        panMenu3.setLayout(new GridLayout(3, 1, 0, 5));
        AISForever = AudioSystem.getAudioInputStream(AudioSystem.class.getResource("/Forever.wav"));
        clipForever = AudioSystem.getClip();
        Clicked click = new Clicked();
        btnPlay.addActionListener(click);
        btnPlay.setBackground(Color.yellow);
        btnPlay.setFont(btnPlay.getFont().deriveFont(Font.BOLD, 60.0f));
        btnInst.addActionListener(click);
        btnInst.setBackground(Color.yellow);
        btnInst.setFont(btnInst.getFont().deriveFont(Font.BOLD, 60.0f));
        btnCredits.addActionListener(click);
        btnCredits.setBackground(Color.yellow);
        btnCredits.setFont(btnCredits.getFont().deriveFont(Font.BOLD, 60.0f));
        btnBack1.addActionListener(click);
        btnBack1.setBackground(Color.yellow);
        btnBack1.setFont(btnBack1.getFont().deriveFont(Font.BOLD, 60.0f));
        btnBack2.addActionListener(click);
        btnBack2.setBackground(Color.yellow);
        btnBack2.setFont(btnBack2.getFont().deriveFont(Font.BOLD, 60.0f));
        //Death Screen
        lblDeath = new JLabel("YOU HAVE DIED.");
        lblDeath.setFont(lblDeath.getFont().deriveFont(Font.BOLD, 155.0f));
        lblDeath.setForeground(Color.red);
        panDeath.add(lblDeath, BorderLayout.CENTER);
        panDeath.setBackground(Color.BLACK);
        //Win Screen
        lblWin = new JLabel("YOU HAVE DEFEATED THE HOSTIS, GLORY FOR ROME!");
        lblWin.setFont(lblDeath.getFont().deriveFont(Font.BOLD, 46.2f));
        lblWin.setForeground(Color.yellow);
        panWin.add(lblWin, BorderLayout.CENTER);
        panWin.setBackground(Color.red);
        //Starting menu
        lblTitle = new JLabel("ROMAN FURY");
        lblTitle.setFont((lblDeath.getFont().deriveFont(Font.BOLD, 186.0f)));
        lblTitle.setForeground(Color.yellow);
        panMenu1.add(lblTitle);
        panMenu1.add(btnPlay);
        panMenu1.add(btnInst);
        panMenu1.add(btnCredits);
        panMenu1.setBackground(Color.red);
        clipForever.open(AISForever);
        clipForever.loop(Clip.LOOP_CONTINUOUSLY);
        //Controls menu
        //Table - http://docs.oracle.com/javase/tutorial/uiswing/components/table.html#simple
        String[] columnNames = {"KEY", "ACTION"};
        Object[][] data = {
            {"KEYS", "ACTION"},
            {"→", "MOVES RIGHT"},
            {"←", "MOVES LEFT"},
            {"C", "BLOCK"},
            {"X", "WEAK ATTACK"},
            {"Z", "STRONG ATTACK"},
            {"P", "PAUSE"},
            {"All Keys", "Hold a key if no action happens."}
        };
        lblControls = new JLabel("CONTROLS");
        lblControls.setFont((lblControls.getFont().deriveFont(Font.BOLD, 227.0f)));
        lblControls.setForeground(Color.yellow);
        JTable table = new JTable(data, columnNames);
        table.setRowHeight(28);
        table.setBackground(Color.yellow);
        table.setFont((table.getFont().deriveFont(Font.BOLD, 21.0f)));
        table.setEnabled(false);
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) table.getCellRenderer(1, 1);
        renderer.setHorizontalAlignment(JTextField.CENTER);
        panMenu2.add(lblControls);
        panMenu2.add(table);
        panMenu2.add(btnBack1);
        panMenu2.setBackground(Color.red);
        //Credits menu
        lblC1 = new JLabel("EVERYTHING: Patrick Duncan");
        lblC2 = new JLabel("Music from Rome Total War, Pacific Rim and The Legend of Zelda: Ocarina of Time");
        lblC1.setFont((lblDeath.getFont().deriveFont(Font.BOLD, 88.0f)));
        lblC2.setFont((lblDeath.getFont().deriveFont(Font.BOLD, 32.0f)));
        lblC1.setForeground(Color.yellow);
        lblC2.setForeground(Color.yellow);
        panMenu3.add(lblC1);
        panMenu3.add(lblC2);
        panMenu3.add(btnBack2);
        panMenu3.setBackground(Color.red);
        //adds them all to the cards panel for the cardlayout
        cards.add(sMenu1, panMenu1);
        cards.add(sMenu2, panMenu2);
        cards.add(sMenu3, panMenu3);
        cards.add(sGame, panGame);
        cards.add(sDeath, panDeath);
        cards.add(sWin, panWin);
        frame.add(cards);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        cl = (CardLayout) (cards.getLayout());
    }

    public void Death() {
        if (hero.getHealth() <= 0) {
            cl.show(cards, sDeath);
        }
    }

    public void Win() {
        cl.show(cards, sWin);
    }
    //Button listener
    private static class Clicked implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            if (btnPlay.isFocusOwner()) {
                clipForever.close();
                cl.show(cards, sGame);
            } else if (btnInst.isFocusOwner()) {
                cl.show(cards, sMenu2);
            } else if (btnBack1.isFocusOwner()) {
                cl.show(cards, sMenu1);
            } else if (btnBack2.isFocusOwner()) {
                cl.show(cards, sMenu1);
            } else if (btnCredits.isFocusOwner()) {
                cl.show(cards, sMenu3);
            }
        }
    }
}
