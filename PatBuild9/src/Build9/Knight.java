package Build9;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
//This class loads all of the attributes for the knight

public class Knight {

    private final int nKnightX, nKnightY;
    private int nHealth = 150, nAttack = 0, nBlock, nState = 1, nImage = 1;
    private static boolean isAttack = false, bHeroAtk = false, isBlock = false;
    private BufferedImage BImgKnight;
    private final BufferedImage BImgKnightPortrait;
    private final static BufferedImage[][] arBImgKnight = new BufferedImage[3][3];
    private Rectangle recHealth;
    private final Hero hero;
    private final static Timer tmrDelay = new Timer();
    private DelayTask delayTask;
    private AudioInputStream AISAttack;

    public Knight() throws Exception {
        hero = new Hero();
        arBImgKnight[1][1] = ImageIO.read(getClass().getResourceAsStream("/knightleft.png"));
        arBImgKnight[2][1] = ImageIO.read(getClass().getResourceAsStream("/knightleftattack.png"));
        arBImgKnight[2][2] = ImageIO.read(getClass().getResourceAsStream("/knightleftblock.png"));
        BImgKnight = arBImgKnight[nState][nImage];
        BImgKnightPortrait = ImageIO.read(getClass().getResourceAsStream("/knightportrait.png"));
        nKnightX = 1000;
        nKnightY = 451;
    }

    public int getX() {
        return nKnightX;
    }

    public int getY() {
        return nKnightY;
    }

    public int getHealth() {
        return nHealth;
    }

    public BufferedImage getImage() {
        Action();
        if (bHeroAtk) {
            nBlock++;
            if (nBlock == 30) {
                nBlock = 0;
                isBlock = true;
                nState = nImage = 2;
            }
        }
        BImgKnight = arBImgKnight[nState][nImage];
        return BImgKnight;
    }

    public boolean getAttack() {
        return isAttack;
    }

    public boolean getBlock() {
        return isBlock;
    }

    public Rectangle getBounds() {
        if (nState == 1 && nImage == 1) {
            return new Rectangle(nKnightX + 50, nKnightY, BImgKnight.getWidth() - 50, BImgKnight.getHeight());
        } else if (nState == 2 && nImage == 1) {
            return new Rectangle(nKnightX, nKnightY, BImgKnight.getWidth(), BImgKnight.getHeight());
        } else {
            return new Rectangle(nKnightX + 58, nKnightY, BImgKnight.getWidth() - 50, BImgKnight.getHeight());
        }
    }

    public void setHealth(int health) {
        nHealth -= health;
        Block();
    }

    public void setAttack(boolean b) {
        isAttack = b;
    }

    public void KnightHealth(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g.drawImage(BImgKnightPortrait, 0, 50, null);
        recHealth = new Rectangle(50, 65, nHealth, 20);
        g.setColor(Color.red);
        g2.fill(recHealth);
        g.setColor(Color.black);
        g2.draw(recHealth);
    }

    public void Action() {
        if (hero.getX() > 600) {
            if (nAttack == 650) {
                nState = 2;
                nImage = 1;
                isAttack = true;
                delayTask = new DelayTask();
                tmrDelay.schedule(delayTask, 0, 2500);
                nAttack = 0;
                try {
                    Clip clipAttack = AudioSystem.getClip();
                    AISAttack = AudioSystem.getAudioInputStream(getClass().getResource("/StrongKnight.wav"));
                    clipAttack.open(AISAttack);
                    clipAttack.start();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            nAttack++;
        }
    }

    public void Block() {

        bHeroAtk = true;
        delayTask = new DelayTask();
        tmrDelay.schedule(delayTask, 0, 2500);
    }

    //http://www.javaprogrammingforums.com/java-se-api-tutorials/883-how-use-tmrDelay-java.html
    class DelayTask extends TimerTask {

        public int nTimes = 0;

        @Override
        public void run() {
            nTimes++;
            if (nTimes == 2) {
                nState = nImage = 1;
                isAttack = isBlock = bHeroAtk = false;
                hero.setPush(false);
            }
        }
    }
}
