package Game;

import java.awt.*;
import javax.swing.ImageIcon;

public class Bullet {

    int x, y;
    Image img;
    boolean visible;

    public Bullet(int startX, int startY) {
        x = startX;
        y = startY;
        String sBullet = "bullet.jpg";
        ImageIcon newBullet = new ImageIcon(sBullet);
        img = newBullet.getImage();
        visible = true;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean getVisible() {
        return visible;
    }

    public Image getImage() {
        return img;
    }

    public void move() {
        x = x + 2;
        if (x > 700) {
            visible = false;
        }
//        if (getBounds().intersects(PanBoard.Franky.getBounds())) {
//            PanBoard.Franky.setAlive();
//        }

//        for(Enemies enemy: PanBoard.alEnemies){
//            if(getBounds().intersects(enemy.getBounds())){
//                enemy.setAlive();
//                visible=false;
//            }
//        }

        for (int i = 0; i < PanBoard.alEnemies.size(); i++) {
            Enemies enemy = PanBoard.alEnemies.get(i);

            if (getBounds().intersects(enemy.getBounds())) {
//                enemy.setAlive();
                PanBoard.alEnemies.remove(i);
                i--;
                visible = false;
            }
        }
    }

    public Rectangle getBounds() {
        //this creates a rectangle around the player and moves as the luffy moves.
        return new Rectangle(x, y, 30, 30);
    }
}