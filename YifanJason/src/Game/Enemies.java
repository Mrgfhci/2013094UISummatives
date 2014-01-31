package Game;

import java.awt.Image;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import javax.swing.ImageIcon;
import java.io.IOException;

public class Enemies {

    Image imgEnemy;
    int EnemyX, EnemyY;
    boolean isAlive = true;
    private final Scanner enemyload;
    private final String sFranky;
    private ImageIcon franky;
  

    public Enemies(int StartX, int StartY) throws FileNotFoundException {
        
        enemyload = new Scanner(new FileReader("enemyload.txt"));
        sFranky = enemyload.nextLine();
        franky = new ImageIcon(sFranky);
        EnemyX = StartX;
        EnemyY = StartY;
        imgEnemy = franky.getImage();
    }

    public int getEnemyX() {
        return EnemyX;
    }

    public int getEnemyY() {
        return EnemyY;
    }

    public void setAlive() {
        isAlive = false;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public Image getImage() {
        return imgEnemy;
    }

    public void move(int dx) {
        EnemyX = EnemyX - dx;

        if (PanBoard.luffy.getBounds().intersects(getBounds())) {
            Healthbar.setHealth(PanBoard.h.nHealth - 1);
        }
    }

    public Rectangle getBounds() {
        //this creates a rectangle around the enemies.
        return new Rectangle(EnemyX, EnemyY, 50, 50);
    }
}
