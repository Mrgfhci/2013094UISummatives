package Zodiac_Fortune_2;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class Board extends JPanel implements ActionListener {
    static boolean drawn = false;
    
    private Astronaut p;
    private Timer timer;
    private Image background;
    // load in the background image and set the movement of the background 
    public Board() throws Exception{
        super();
        p = new Astronaut();
        addKeyListener(new ActionListener());
        setFocusable(true);
        ImageIcon i1 = new ImageIcon(getClass().getResource("/Long Background.png"));      
        background = i1.getImage();     
        setBackground(Color.black);
        timer = new Timer(80, this);
        timer.start();
    }

    public void actionPerformed(ActionEvent arg0) {
        p.move();
        repaint();
    }

    public void paint(Graphics g) {

        // draw the background image to the stage
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(background, p.backgroundX, 0, null);
        g2d.drawImage(p.getImage(), 350, p.getY(), null);   
    }

    private class ActionListener extends KeyAdapter {
        //Determine key press and release actions 

        public void keyReleased(KeyEvent e) {
            p.keyReleased(e);
        }

        public void keyPressed(KeyEvent e) {
            p.keyPressed(e);
        }
    }
}