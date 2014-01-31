package Zodiac_Fortune_2;

import java.awt.Image;
import java.awt.event.KeyEvent;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class Astronaut {

    private Image img;
    int x, y, dx, dy, backgroundX, OriginY, L_and_R_Counter = 0, PlayerX = 350, speedCounter = 0;
    long time;
    private final int SPEED = 8;
    private final Clip clipShip;
    private final AudioInputStream AISShip;
    private AudioInputStream AISFootsteps;
    boolean left, right, releaseRight, releaseLeft;
    private int Xmin, Xmax, Ymin, Ymax;
    private ImageIcon  i13, i14;
    private ImageIcon[] arILeft = new ImageIcon[7], arIRight = new ImageIcon[7];
    private Image[] arnWalk_L, arnWalk_R;
    private long before = 0, Delay = 1000;
    Clip clipFootsteps = AudioSystem.getClip();

    public Astronaut() throws Exception {
        AISShip = AudioSystem.getAudioInputStream(getClass().getResource("/Inside Ship.wav"));
        clipShip = AudioSystem.getClip();
        clipShip.open(AISShip);
        clipShip.loop(Clip.LOOP_CONTINUOUSLY);
        //images for the running action
        
        
        for (int i = 1; i < arIRight.length; i++) {
            arIRight[i] = new ImageIcon(getClass().getResource("/AstroWalkRight" + i + ".png"));
            arILeft[i] = new ImageIcon(getClass().getResource("/AstroWalkLeft" + i + ".png"));
        }
        // images for the standing left and right actions  
        i13 = new ImageIcon(getClass().getResource("/AstroStandRight.png"));
        i14 = new ImageIcon(getClass().getResource("/AstroStandLeft.png"));
        // Array to hold the images
        arnWalk_R = new Image[6];
        arnWalk_L = new Image[6];

        x = 16;
        y = 439;
        dx = 0;
        dy = 0;
        backgroundX = 0;
        left = false;
        right = false;
        Xmin = 350;
        Xmax = 450;
        releaseRight = false;
        releaseLeft = false;


        // images are set to running right array
        for (int i = 1; i <= 6; i++) {
            arnWalk_R[i - 1] = arIRight[i].getImage();
            arnWalk_L[i - 1] = arILeft[i].getImage();
        }
    }

    //moves background image, creates illusion of movement
    public void move() {
        x += dx;
        backgroundX = (backgroundX - dx);

    }
    // checks the current state of the X (background)

    public int getX() {
        return x;
    }
    // checks the current state of Y (background)

    public int getY() {
        return y;
    }

    public Image getImage() {
//Display image of static astronaut
        img = i13.getImage();
// moves through an array of right-facing images when right arrow key is pressed
        if (right == true) {
            releaseLeft = false;
            img = arnWalk_R[L_and_R_Counter];
            L_and_R_Counter++;
            if (L_and_R_Counter == 6) {
                L_and_R_Counter = 0;
            }
// moves through an array of left-facing images when left arrow key is pressed
            //passed to paint function in Board
        } else if (left == true) {
            releaseRight = false;
            img = arnWalk_L[L_and_R_Counter];
            L_and_R_Counter++;
            if (L_and_R_Counter == 6) {
                L_and_R_Counter = 0;
            }
        } else if (releaseLeft == true) {
            img = i14.getImage();
        }
// returns img of astronaut based on the state he is currently in.
        return img;
    }
   
    // checks for key press.
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        try {
            long now = System.currentTimeMillis();
            clipFootsteps = AudioSystem.getClip();
            if (now - before > Delay) {
                AISFootsteps = AudioSystem.getAudioInputStream(getClass().getResource("/Footsteps.wav"));
                clipFootsteps.open(AISFootsteps);
                clipFootsteps.start();
                before = now;
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //if left key is pressed Astronaut will move left
        if (code == KeyEvent.VK_LEFT) {
            left = true;
            dx = -SPEED;
            //if Right key is pressed Astronaut will move Right
        } else if (code == KeyEvent.VK_RIGHT) {
            right = true;
            dx = SPEED;
        }

    }

    //Checks for key release
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_LEFT) {
            left = false;
            clipFootsteps.drain();
            releaseLeft = true;
            if (right) {
                dx = SPEED;
            } else {
                dx = 0;
            }
            // code to make character (Stage) to the right
        } else if (code == KeyEvent.VK_RIGHT) {
            right = false;

            releaseRight = true;
            clipFootsteps.stop();
            if (left) {
                dx = -SPEED;
            } else {
                dx = 0;
            }
        }
    }
}