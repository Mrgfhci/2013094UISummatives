package Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.FileReader;
import java.util.Scanner;
import javax.swing.ImageIcon;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Character {

    int x, dx, y, dy, nX, nX2, movingleft;
    Image Imgluffy;
    boolean isGrounded;
    private final Scanner gifload = new Scanner(new FileReader("gifload.txt"));
    private final String sluffyStandDown = gifload.nextLine(), sluffyStandup = gifload.nextLine(),
            sluffyStandRight = gifload.nextLine(), sluffyStandLeft = gifload.nextLine(),
            sluffyRunRight = gifload.nextLine(), sluffyRunLeft = gifload.nextLine();
    private ImageIcon standDown = new ImageIcon(sluffyStandDown), standUp = new ImageIcon(sluffyStandup),
            standRight = new ImageIcon(sluffyStandRight), standLeft = new ImageIcon(sluffyStandLeft),
            runRight = new ImageIcon(sluffyRunRight), runLeft = new ImageIcon(sluffyRunLeft);
    // private BufferedImage standDown = ImageIO.read(getClass().getResourceAsStream("/StandRight.gif")),standUp = ImageIO.read(getClass().getResourceAsStream("/StandRight.gif")),
    //        standRight = ImageIO.read(getClass().getResourceAsStream("/StandRight.gif")),standLeft = ImageIO.read(getClass().getResourceAsStream("/StandLeft.gif")),
    //         runRight = ImageIO.read(getClass().getResourceAsStream("/RunRight.gif")),runLeft = ImageIO.read(getClass().getResourceAsStream("/RunLeft.gif"));
    //We tried to use buffered image instead of ImageIcon but we still got the blurs between the 2nd and 3rd background.
    static ArrayList bullet;
    private long delay = 0;

    public Character() throws IOException {
        Imgluffy = standRight.getImage();
        movingleft = 250;
        nX2 = 685;
        nX = 0;
        x = 0;
        y = 400;
        bullet = new ArrayList();
    }

    public static ArrayList getBullets() {
        return bullet;
    }

    public void firebullet() {
        delay = System.currentTimeMillis();
        Bullet z = new Bullet((movingleft + 30), (y + 50 / 2));
        bullet.add(z);
    }

    public void move() {
        if (dx != -1) {
            if (movingleft + dx <= 150) {
                movingleft = movingleft + dx;
            } else {
                nX = nX + dx;
                nX2 = nX2 + dx;
                x += dx;
            }
        } else {
            if (movingleft + dx > 0) {
                movingleft = movingleft + dx;
            }
        }
        y += dy;
        if (y == 400 && dy == 1) {
            dy = 0;
        }
        isGrounded = (y == 400);
    }

    public int getX() {
        return x;
    }

    public int getdx() {
        return dx;
    }

    public int getY() {
        return y;
    }

    public Image getImage() {
        return Imgluffy;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            dx = -1;
            Imgluffy = runLeft.getImage();
        }
        if (key == KeyEvent.VK_RIGHT) {
            dx = 1;
            Imgluffy = runRight.getImage();
        }
        if (key == KeyEvent.VK_SPACE) {
            if (isGrounded) {
                dy = -1;
            } else {
                dy = 1;
            }
        }
        if (key == KeyEvent.VK_Z && System.currentTimeMillis() - delay > 500) {
            firebullet();
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
            Imgluffy = standLeft.getImage();
        }
        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
            Imgluffy = standRight.getImage();
        }
        if (key == KeyEvent.VK_SPACE) {
            if (!isGrounded) {
                dy = 1;
            } else {
                dy = 0;
            }
        }
    }

    public Rectangle getBounds() {
        //this creates a rectangle around the player and moves as the luffy moves.
        return new Rectangle(movingleft, y, 50, 50);
    }
}