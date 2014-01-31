package Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Healthbar extends JPanel {

    private static Rectangle recHealth;
    public static int nHealth = 100;

    public Healthbar() {
        recHealth = new Rectangle(10, 10, nHealth, 20);
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g.setColor(Color.red);
        g2.fill(recHealth);
        g.setColor(Color.black);
        g2.draw(recHealth);
    }

    public static void setHealth(int health) {
        nHealth = health;
        recHealth.setRect(10, 10, nHealth, 20);
    }
}
