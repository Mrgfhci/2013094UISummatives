package Build9;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
//This class loads all the atributes for one of the sorcerers.
public class Sorcerer1 {

    private final int nSor1X, nSor1Y;
    private int nAttack = 1299, nHealth = 50, nChange = 0, nState = 1, nImage = 1;
    private final int nAtkSpeed = 1300;
    private static boolean isAttack = false;
    private BufferedImage BImgSor1;
    private final BufferedImage BImgSor1Portrait;
    private final static BufferedImage[][] arBImgSor = new BufferedImage[3][2];
    private Rectangle recHealth;

    public Sorcerer1() throws IOException {
        arBImgSor[1][1] = ImageIO.read(getClass().getResourceAsStream("/sorcerer1.png"));
        arBImgSor[2][1] = ImageIO.read(getClass().getResourceAsStream("/sorcereratk1.png"));
        BImgSor1 = arBImgSor[nState][nImage];
        BImgSor1Portrait = ImageIO.read(getClass().getResourceAsStream("/sorcerer1portrait.png"));
        nSor1X = 1140;
        nSor1Y = 508;
    }

    public int getX() {
        return nSor1X;
    }

    public int getY() {
        return nSor1Y;
    }

    public BufferedImage getImage() {
        Attack();
        BImgSor1 = arBImgSor[nState][nImage];
        return BImgSor1;
    }

    public boolean getAttack() {
        return isAttack;
    }

    public Rectangle getBounds() {
        return new Rectangle(nSor1X + 15, nSor1Y, BImgSor1.getWidth() - 10, BImgSor1.getHeight());
    }

    public void Sor1Health(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g.drawImage(BImgSor1Portrait, 0, 50, null);
        recHealth = new Rectangle(50, 65, nHealth, 20);
        g.setColor(Color.red);
        g2.fill(recHealth);
        g.setColor(Color.black);
        g2.draw(recHealth);
    }

    public void setHealth(int health) {
        nHealth -= health;
    }

    public int getHealth() {
        return nHealth;
    }

    public void Attack() {
        nAttack++;
        isAttack = false;
        if (nAttack > (int) (Math.random() * nAtkSpeed * 2 + nAtkSpeed)) {
            nState = 2;
            nImage = 1;
            isAttack = true;
            nAttack = 0;
        }
        if (nChange == 150) {
            nState = nImage = 1;
            nChange = 0;
        }
        nChange++;
    }
}
