package Scratch_Image;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
//No images are displayed but the loop has been successfully fixed, see the outcome in Test_Image_Loop

public class PanImage extends JPanel implements ActionListener {
    final int nASTROIMGMAX = 6;
    final int nASTROANIMIMGCOUNT = 6;
    int nAstroImgCount = nASTROIMGMAX;
    int nAstroImgDir = 1;
    int nAstroAnimPos = 0;
    int nAstronautX, nAstronautY;//These X and Ys seem to be with the animation and get incremented through the animation
    int nViewDX;
    int[] arnDX, arnDY;
    int[] arnMonsterX, arnMonsterY, arnMonsterDX, arnMonsterDY, arnMonsterSpeed;
    BufferedImage biImages;
    BufferedImage biBackground1, biBackground2;
    BufferedImage biMonster;
    BufferedImage[] arbiAstroWalkLeft = new BufferedImage[7];//Changing the way I have declared my arrays has change from nullpointerexception to array index out of bounds
    BufferedImage[] arbiAstroWalkRight = new BufferedImage[7];//The Array index is now out of bounds because nASTROIMGMAX is telling how many variables go into the array
    BufferedImage biAstroStandLeft;
    BufferedImage biAstroStandRight;
    Timer tTimer;

    public PanImage() throws IOException {
        GetImages();
        tTimer = new Timer(40, this);
        tTimer.start();
    }

    public void PlayGame(Graphics2D g2d) {
            DrawAstronaut(g2d);
    }

    public void GetImages() throws IOException {
        for (int i = 1; i <= nASTROIMGMAX; i++){//NullPointerException, I think "i" is null, the null pointer was see line 22, 23 for comment
            arbiAstroWalkLeft[i] = ImageIO.read (new File("Assets\\AstroWalkLeft" + Integer.toString(i) + ".png"));
            System.out.println(arbiAstroWalkLeft[i]);
            arbiAstroWalkRight[i] = ImageIO.read (new File("Assets\\AstroWalkRight" + Integer.toString(i) + ".png"));
            System.out.println(arbiAstroWalkRight[i]);
        }
        biAstroStandLeft = ImageIO.read (new File("Assets\\AstroStandLeft.png"));
        biAstroStandRight = ImageIO.read (new File("Assets\\AstroStandRight.png"));
        biBackground1 = ImageIO.read (new File("Assets\\Hallway.png"));
        biBackground2 = ImageIO.read (new File("Assets\\Observation Room.png"));
    }

    @Override
    public void paint(Graphics g) {//Paint component where STUFF MUST BE IN THE RIGHT ORDER
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g.drawImage(biBackground1, 0, 0, null);//Put background's first
        DoAnimation();
        g.drawImage(biImages, 0, 0, this);//Drawing the images?        
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    public void DoAnimation() {
        nAstroImgCount--;
        if (nAstroImgCount <= 0) {
            nAstroImgCount = nASTROIMGMAX;
            nAstroAnimPos = nAstroAnimPos + nAstroImgDir;
            if (nAstroAnimPos == (nASTROANIMIMGCOUNT - 1) || nAstroAnimPos == 0) {
                nAstroImgDir = -nAstroImgDir;
            }
        }
    }

    public void DrawAstronaut(Graphics2D g2d) {
        if (nViewDX == -1) {//Changing the view on his direction, yet he's gone when he's not moving
            DrawAstronautLeft(g2d);
        } else if (nViewDX == 1) {
            DrawAstronautRight(g2d);
        } else {
            DrawAstronautStand(g2d);
        }
    }

    public void DrawAstronautLeft(Graphics2D g2d) {
        for (int i = 0; i <= nAstroAnimPos; i++) {
            if (i == 0) { //Start with stand position       
                g2d.drawImage(biAstroStandLeft, nAstronautX + 1, nAstronautY + 1, this);
            } else { //Run the sequence from 1 to 6
                g2d.drawImage(arbiAstroWalkLeft[i], nAstronautX + 1, nAstronautY + 1, this);
            }
        }
    }

    public void DrawAstronautRight(Graphics2D g2d) {//Identical to the left but in the right direction
        for (int i = 0; i <= nAstroAnimPos; i++) {
            if (i == 0) {       
                g2d.drawImage(biAstroStandRight, nAstronautX + 1, nAstronautY + 1, this);
            } else {         
                g2d.drawImage(arbiAstroWalkRight[i], nAstronautX + 1, nAstronautY + 1, this);
            }
        }
    }

    public void DrawAstronautStand(Graphics2D g2d) {//This was taken out before and that is why we lost the stand still image
        switch (nAstroAnimPos) {
            default:
                g2d.drawImage(biAstroStandLeft, nAstronautX, nAstronautY, this);
                break;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}