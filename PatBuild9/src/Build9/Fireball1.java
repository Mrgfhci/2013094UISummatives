package Build9;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
//This class loads all of the attributes for one of the fireballs
public class Fireball1 {

    private int nFireball1X;
    private final int nFireball1Y;
    private final BufferedImage BImgFireball1;
    private boolean visible = false;

    public Fireball1() throws IOException {
        BImgFireball1 = ImageIO.read(getClass().getResourceAsStream("/fireball1.png"));
        nFireball1X = 1120;
        nFireball1Y = 565;
    }

    public int getX() {
        return nFireball1X;
    }

    public int getY() {
        return nFireball1Y;
    }

    public Rectangle getBounds() {
        return new Rectangle(nFireball1X, nFireball1Y, BImgFireball1.getWidth(), BImgFireball1.getHeight());
    }

    public BufferedImage getImage() {
        if (nFireball1X < -30 || nFireball1X > 1250) {
            visible = false;
            nFireball1X = 1120;
        }
        return BImgFireball1;
    }
    
    public void setVisible(boolean vis) {
        visible = vis;
    }
    
    public boolean isVisible(){
        return visible;
    }
    
    public void setX(int x){
        nFireball1X = x;
    }
    
    public void move() {
        nFireball1X -= 1;
    }
}
