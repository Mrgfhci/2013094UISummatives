package Game;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public class PanBoard extends JPanel implements ActionListener {

    public static Character luffy;
    public Image Background;
    Timer time;
    public static Healthbar h = new Healthbar();
    public static ArrayList<Enemies> alEnemies;

    public PanBoard() throws IOException {
        luffy = new Character();
        String sBack = "background.jpg";
        addKeyListener(new PanBoard.AL());
        setFocusable(true);
        ImageIcon background = new ImageIcon(sBack);
        Background = background.getImage();
        time = new Timer(5, this);
        time.start();
        alEnemies = new ArrayList<>();

        for (int i = 0; i < 80; i++) {
            alEnemies.add(new Enemies(500 + i * 100, 380));
        }       
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        luffy.move();
        // if (luffy.x > 500) {
        for (Enemies enemy : alEnemies) {
            enemy.move(luffy.getdx());
        }
        // }

        repaint();
        ArrayList bullets = Character.getBullets();
        for (int w = 0; w < bullets.size(); w++) {
            Bullet m = (Bullet) bullets.get(w);
            if (m.getVisible() == true) {
                m.move();
            } else {
                bullets.remove(w);
            }
        }

        if (Healthbar.nHealth == 0) {
            CardLayout cl = (CardLayout) (Main.panMain.getLayout());
            cl.show(Main.panMain, "3");
        }


    }

    @Override
    public void paint(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        if ((luffy.getX() - 210) % 1800 == 0) {
            luffy.nX = 0;
        }
        if ((luffy.getX() - 1110) % 1800 == 0) {
            luffy.nX2 = 0;
        }
        //using the module method to repaint the background. Based on the size of the background.
        g2d.drawImage(Background, 685 - luffy.nX2, 0, null);
        if (luffy.getX() >= 210) {
            g2d.drawImage(Background, 685 - luffy.nX, 0, null);
        }
        //Drawing the character and also making the character not able to move beyond the left edge of the screen.
        g2d.drawImage(luffy.getImage(), luffy.movingleft, luffy.getY(), null);

        //Drawing the enemies.
        for (Enemies enemy : alEnemies) {
            if (enemy.isAlive) {
                g2d.drawImage(enemy.getImage(), enemy.getEnemyX(), enemy.getEnemyY(), null);
            }
        }

        h.paint(g);

        ArrayList bullets = Character.getBullets();
        for (int w = 0; w < bullets.size(); w++) {
            Bullet m = (Bullet) bullets.get(w);
            //g2d.drawImage(m.getImage(), m.getX(), m.getY(), null);
            g2d.drawImage(m.getImage(), m.getX(), m.getY(), 20, 5, null);
        }
    }

    private class AL extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            luffy.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            luffy.keyPressed(e);
        }
    }
}