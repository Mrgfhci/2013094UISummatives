package Build9;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import javax.sound.sampled.AudioSystem;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
//This class loads all of the attributes for the boss
public class Boss {

    private final int nBossX, nBossY;
    private int nHealth = 200, nBeam1 = 860, nBeam2 = 0, nState, nImage, nVisible,
            nDelay, nBlast, n;
    private static boolean isBeam = true, bGrow = true, isBlast;
    private BufferedImage BImgBoss;
    private final BufferedImage BImgBossPortrait;
    private final static BufferedImage[][] arBImgBoss = new BufferedImage[3][3];
    public Rectangle recHealth, recBeam;
    private final Hero hero;
    private AudioInputStream AISBeam;

    public Boss() throws Exception {
        hero = new Hero();
        nState = nImage = nVisible = nDelay = 1;
        arBImgBoss[1][1] = ImageIO.read(getClass().getResourceAsStream("/bossleft.png"));
        arBImgBoss[2][1] = ImageIO.read(getClass().getResourceAsStream("/bossleftatk1.png"));
        arBImgBoss[2][2] = ImageIO.read(getClass().getResourceAsStream("/bossleftatk2.png"));
        BImgBoss = arBImgBoss[nState][nImage];
        BImgBossPortrait = ImageIO.read(getClass().getResourceAsStream("/bossportrait.png"));
        nBossX = 770;
        nBossY = 479;
    }

    public int getX() {
        if (hero.getX() > 619 && hero.getX() < 700) {
            nState = nImage = 1;
            nBlast = 0;
        }
        return nBossX;
    }

    public int getY() {
        return nBossY;
    }

    public int getHealth() {
        return nHealth;
    }

    public int getDelay() {
        return nDelay;
    }

    public int getVisible() {
        return nVisible;
    }

    public BufferedImage getImage() {
        try {
            Clip clipBeam = AudioSystem.getClip();
            AISBeam = AudioSystem.getAudioInputStream(getClass().getResource("/Fireball.wav"));
            if (nBeam2 == 1 && nState == 2 && nImage == 1) {
                clipBeam.open(AISBeam);
                clipBeam.start();
            } else {
                clipBeam.stop();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (n != 0) {
            nState = nImage = 2;
        }
        if (nState == 2 && nImage == 2) {
            n++;
            if (n > 100) {
                n = 0;
            }
        }
        BImgBoss = arBImgBoss[nState][nImage];
        return BImgBoss;
    }

    public boolean getBeam() {
        return isBeam;
    }

    public boolean getBlast() {
        return isBlast;
    }

    public Rectangle getBounds() {
        if (nState == 1 && nImage == 1) {
            return new Rectangle(nBossX + 40, nBossY, BImgBoss.getWidth(), BImgBoss.getHeight());
        } else if (nState == 2 && nImage == 1) {
            return new Rectangle(nBossX + 40, nBossY, BImgBoss.getWidth(), BImgBoss.getHeight());
        } else {
            return new Rectangle(nBossX, nBossY, BImgBoss.getWidth(), BImgBoss.getHeight());
        }
    }

    public void setHealth(int health) {
        nHealth -= health;
    }

    public void setBeam(boolean b) {
        isBeam = b;
    }

    public void setGrow(boolean grow) {
        bGrow = grow;
    }

    public void setBlast(boolean blast) {
        isBlast = blast;
    }

    public void BossHealth(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g.drawImage(BImgBossPortrait, 0, 50, null);
        recHealth = new Rectangle(50, 65, nHealth, 20);
        g.setColor(Color.red);
        g2.fill(recHealth);
        g.setColor(Color.black);
        g2.draw(recHealth);
    }

    public void Beam(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if (nVisible < 860) {
            recBeam = new Rectangle(nBeam1, 562, nBeam2, 23);
            g.setColor(Color.yellow);
            g2.fill(recBeam);
            g.setColor(Color.black);
            g2.draw(recBeam);
            if (bGrow ) {
                nBeam1--;
                nBeam2++;
            }
            nVisible++;
            nState = 2;
            nImage = 1;
        } else {
            nDelay++;
            if (nDelay >= 350) {
                nVisible = nDelay = 1;
                nBeam1 = 860;
                nBeam2 = 1;
            }
            nState = nImage = 1;
            isBeam = false;
        }
    }

    public void Blast() {
        if (nBlast == 300) {
            isBlast = true;
            nState = nImage = 2;
            nBlast = 0;
            try {
                Clip clipBeam = AudioSystem.getClip();
                AISBeam = AudioSystem.getAudioInputStream(getClass().getResource("/Blast.wav"));
                clipBeam.open(AISBeam);
                clipBeam.start();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            nState = nImage = 1;
        }
        nBlast++;
    }
}
