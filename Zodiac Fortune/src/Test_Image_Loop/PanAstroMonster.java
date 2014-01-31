package Test_Image_Loop;
//http://zetcode.com/tutorials/javagamestutorial/pacman/
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class PanAstroMonster extends JPanel implements ActionListener {
    //2d array of moving directions and animation images [direction][image]
    Dimension d;
    Font smallfont = new Font("OCR A Std", Font.BOLD, 14);
    FontMetrics fmSmall, fmLarge;
    boolean bInGame = false;
    boolean bDying = false;
    boolean bMoving = true;
    final int nBLOCKSIZE = 24;
    //Dimensions of the Space
    final int nLEVELHEIGHT = 35;
    final int nLEVELWIDTH = 20;
    final int nSCREENSIZE = nLEVELHEIGHT * nBLOCKSIZE;
    final int nASTROIMGMAX = 6;
    final int nASTROANIMIMGCOUNT = 6;
    final int nMONSTERMAX = 12;
    final int nASTRONAUTSPEED = 4;
    int nAstroImgCount = nASTROIMGMAX;
    int nAstroImgDir = 1;
    int nAstroAnimPos = 0;
    int nMonsterNum = 1;
    int nLife, nDeathCounter;
    int nAstronautX, nAstronautY;//The x and y that set up his spawn.  Adding values here changes nothing but to change his spawn you want to go to lines, 256 & 257.
    int nAstronautDX, nAstronautDY;//nAstronautDX and DY are used in the MoveAstronaut method where as the AstronautX and Y are mostly used in the animation
    int nReqDX, nViewDX;//View is changing the direction the Astronaut is viewing while nReqdx and dy are used for his movement direction.
    int[] arnDX, arnDY;
    int[] arnMonsterX, arnMonsterY, arnMonsterDX, arnMonsterDY, arnMonsterSpeed;
    BufferedImage biImages;
    BufferedImage biBackground1, biBackground2;
    BufferedImage biMonster;
    BufferedImage[] arbiAstroWalkLeft = new BufferedImage[7];//See Scratch_Image for the comments on Buffered Arrays
    BufferedImage[] arbiAstroWalkRight = new BufferedImage[7];
    //Array of Images for Left and Right
    BufferedImage biAstroStandLeft;
    BufferedImage biAstroStandRight;
    //This is the level data, borders are numbers and empty space is 0
    final short arLEVELDATA[] = {
        19, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 62,
        1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 60,
        1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 60,
        1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 60,
        1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 60,
        1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 60,
        1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 60,
        1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 60,
        1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 60,
        1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 60,
        1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 60,
        1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 60,
        1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 60,
        1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 60,
        1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 60,
        1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 60,
        1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 60,
        1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 60,
        1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 60,
        18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 68};
    final int arnVALIDSPEEDS[] = {1, 2, 3, 4, 6, 8};
    final int nMAXSPEED = 6;
    int nCurrentSpeed = 3;
    short[] arScreenData;
    Timer tTimer;

    public PanAstroMonster() throws IOException {
        GetImages();
        addKeyListener(new TAdapter());
        arScreenData = new short[nLEVELHEIGHT * nLEVELWIDTH];//Length * Width for the area
        setFocusable(true);
        d = new Dimension(960, 640);
        setDoubleBuffered(true);
        arnMonsterX = new int[nMONSTERMAX];//Adding these values to the number of all monsters
        arnMonsterDX = new int[nMONSTERMAX];//Like the Astronaut the Monster's DX vs X is similar to how one is more for moving and the other is the moving of the image
        arnMonsterY = new int[nMONSTERMAX];
        arnMonsterDY = new int[nMONSTERMAX];
        arnMonsterSpeed = new int[nMONSTERMAX];
        arnDX = new int[4];
        arnDY = new int[4];
        tTimer = new Timer(40, this);
        tTimer.start();
    }

    public void PlayGame(Graphics2D g2d) {//Checking for your death and if not dead let the game start
        if (bDying) {
            Death();
        } else {
            MoveAstronaut();
            DrawAstronaut(g2d);
            MoveMonster(g2d);
            CheckLevel();
        }
    }

    public void CheckLevel() {
        short i = 0;
        boolean bFinished = true;
        while (i < nLEVELHEIGHT * nLEVELWIDTH && bFinished) {
            if ((arScreenData[i] & 48) != 0) {
                bFinished = false;
            }
            i++;
        }
        if (bFinished) {
            if (nMonsterNum < nMONSTERMAX) {
                nMonsterNum++;
            }
            if (nCurrentSpeed < nMAXSPEED) {
                nCurrentSpeed++;
            }
            LevelInit();
        }
    }

    public void Death() {//If you run of of lives, the boolean sets to false and you're no longer in the game and if not just continue
        nLife--;
        if (nLife == 0) {
            bInGame = false;
        }
        LevelContinue();
    }

    public void MoveMonster(Graphics2D g2d) {//Moves the monster but I need to figure out how
        short i;
        int nPosition;
        int nCount;
        for (i = 0; i < nMonsterNum; i++) {
            if (arnMonsterX[i] % nBLOCKSIZE == 0 && arnMonsterY[i] % nBLOCKSIZE == 0) {//Continue only if you have finished moving one square
                nPosition = arnMonsterX[i] / nBLOCKSIZE + nLEVELHEIGHT * (int) (arnMonsterY[i] / nBLOCKSIZE);//This line determines, where the monster is situated. In which position/square
                nCount = 0;
                if ((arScreenData[nPosition] & 1) == 0 && arnMonsterDX[i] != 1) {//If there is no obstacle on the left and the monster is not already moving to the right, he will move to the left. Moving of Monster is partly random
                    arnDX[nCount] = -1;//The monster's x and y get inverted when when he hits the wall so that flips him around
                    arnDY[nCount] = 0;
                    nCount++;
                }
                if ((arScreenData[nPosition] & 2) == 0 && arnMonsterDY[i] != 1) {
                    arnDX[nCount] = 0;
                    arnDY[nCount] = -1;
                    nCount++;
                }
                if ((arScreenData[nPosition] & 4) == 0 && arnMonsterDX[i] != -1) {
                    arnDX[nCount] = 1;
                    arnDY[nCount] = 0;
                    nCount++;
                }
                if ((arScreenData[nPosition] & 8) == 0 && arnMonsterDY[i] != -1) {
                    arnDX[nCount] = 0;
                    arnDY[nCount] = 1;
                    nCount++;
                }
                if (nCount == 0) {
                    if ((arScreenData[nPosition] & 15) == 15) {
                        arnMonsterDX[i] = 0;
                        arnMonsterDY[i] = 0;
                    } else {
                        arnMonsterDX[i] = -arnMonsterDX[i];
                        arnMonsterDY[i] = -arnMonsterDY[i];
                    }
                } else {
                    nCount = (int) (Math.random() * nCount);
                    if (nCount > 3) {
                        nCount = 3;
                    }
                    arnMonsterDX[i] = arnDX[nCount];
                    arnMonsterDY[i] = arnDY[nCount];
                }

            }
            arnMonsterX[i] = arnMonsterX[i] + (arnMonsterDX[i] * arnMonsterSpeed[i]);
            arnMonsterY[i] = arnMonsterY[i] + (arnMonsterDY[i] * arnMonsterSpeed[i]);
            DrawMonster(g2d, arnMonsterX[i] + 1, arnMonsterY[i] + 1);
            if (nAstronautX > (arnMonsterX[i] - 12) && nAstronautX < (arnMonsterX[i] + 12)//If there is a collision between the monster and the Astronaut, the Astronaut dies
                    && nAstronautY > (arnMonsterY[i] - 12) && nAstronautY < (arnMonsterY[i] + 12)
                    && bInGame) {
                bDying = true;
                nDeathCounter = 64;
            }
        }
    }

    public void MoveAstronaut() {//In here somewhere I think that is where the original x and y are
        int nPosition;
        short Character;
        if (nReqDX == -nAstronautDX) {
            nAstronautDX = nReqDX;
            nViewDX = nAstronautDX;
        }
        if (nAstronautX % nBLOCKSIZE == 0) {
            nPosition = nAstronautX / nBLOCKSIZE + nLEVELHEIGHT * (int) (nAstronautY / nBLOCKSIZE);
            Character = arScreenData[nPosition];

            if ((Character & 16) != 0) {
                arScreenData[nPosition] = (short) (Character & 15);
            }
            if (nReqDX != 0) {//Required to move
                if (!((nReqDX == -1 && (Character & 1) != 0)
                        || (nReqDX == 1 && (Character & 4) != 0)
                        || (nReqDX == 0 && (Character & 2) != 0)
                        || (nReqDX == 0 && (Character & 8) != 0))) {
                    nAstronautDX = nReqDX;
                    nViewDX = nAstronautDX;
                }
            }
            // Check for standstill
            if ((nAstronautDX == -1 && (Character & 1) != 0)//If the Astronaut cannot move further in his current direction, there is a standstill.
                    || (nAstronautDX == 1 && (Character & 4) != 0)
                    || (nAstronautDX == 0 && (Character & 2) != 0)
                    || (nAstronautDX == 0 && (Character & 8) != 0)) {
                nAstronautDX = 0;
            }
        }
        nAstronautX = nAstronautX + nASTRONAUTSPEED * nAstronautDX;
    }

    public void GameRun() {//Running your game 
        nLife = 1;//Here is your life
        LevelInit();
        nMonsterNum = 1;
        nCurrentSpeed = 3;
    }

    public void LevelInit() {//Checking if you're within the level boundaries.
        int i;
        for (i = 0; i < nLEVELHEIGHT * nLEVELWIDTH; i++) {//Looping through the Screen info and level data
            arScreenData[i] = arLEVELDATA[i];
        }
        LevelContinue();//Continuing if you are
    }

    public void LevelContinue() {
        short i;
        int nDX = 1;
        int random;

        for (i = 0; i < nMonsterNum; i++) {
            arnMonsterY[i] = 4 * nBLOCKSIZE;
            arnMonsterX[i] = 4 * nBLOCKSIZE;
            arnMonsterDY[i] = 0;
            arnMonsterDX[i] = nDX;
            nDX = -nDX;
            random = (int) (Math.random() * (nCurrentSpeed + 1));
            if (random > nCurrentSpeed) {
                random = nCurrentSpeed;
            }
            arnMonsterSpeed[i] = arnVALIDSPEEDS[random];//Takes one number at random from the arnVALIDSPEEDS for the monster
        }

        nAstronautX = 30 * nBLOCKSIZE;//Here are the AstronautX and Y and the given a value will set the Astronaut Spawn
        nAstronautY = 16 * nBLOCKSIZE;//The reason these numbers were crashing before is because I was making the Astronaut's x and y bigger than the boundaries of the level, when kept within the right dimension location is changed
        nAstronautDX = 0;
        nReqDX = 0;
        nViewDX = -1;
        bDying = false;
    }

    public void GetImages() throws IOException {
        //Loop through an array of images imgAstroWalkLeft"i"
        biMonster = ImageIO.read (new File("Assets\\MonsterSingle.png"));
        for (int i = 1; i <= nASTROIMGMAX; i++) {
            arbiAstroWalkLeft[i] = ImageIO.read (new File("Assets\\AstroWalkLeft" + Integer.toString(i) + ".png"));
            arbiAstroWalkRight[i] = ImageIO.read (new File("Assets\\AstroWalkRight" + Integer.toString(i) + ".png"));
        }
        biAstroStandLeft = ImageIO.read (new File("Assets\\AstroStandLeft.png"));
        biAstroStandRight = ImageIO.read (new File("Assets\\AstroStandRight.png"));
        biBackground1 = ImageIO.read (new File("Assets\\Hallway.png"));
        biBackground2 = ImageIO.read (new File("Assets\\Observation Room.png"));
    }

    public void ShowIntroScreen(Graphics2D g2d) {//Setting up the intro screen of Press S to start
        g2d.setColor(new Color(0, 32, 48));
        g2d.fillRect(50, nSCREENSIZE / 2 - 30, nSCREENSIZE - 100, 50);
        g2d.setColor(Color.white);
        g2d.drawRect(50, nSCREENSIZE / 2 - 30, nSCREENSIZE - 100, 50);

        String s = "Press s to begin";
        Font small = new Font("OCR A Std", Font.BOLD, 14);
        FontMetrics metr = this.getFontMetrics(small);

        g2d.setColor(Color.white);
        g2d.setFont(small);
        g2d.drawString(s, (nSCREENSIZE - metr.stringWidth(s)) / 2, nSCREENSIZE / 2);
    }

    @Override
    public void paint(Graphics g) {//Paint component where STUFF MUST BE IN THE RIGHT ORDER
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g.drawImage(biBackground1, 0, 0, null);//Put background's first
        DoAnimation();
        if (bInGame) {//Doing things only if you're in the game
            PlayGame(g2d);
        } else {//If you're not in the game it's showing you the intro screen
            ShowIntroScreen(g2d);
        }
        g.drawImage(biImages, 0, 0, this);
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    public void DrawMonster(Graphics2D g2d, int x, int y) {
        g2d.drawImage(biMonster, x, y, this);//Setting x and y
        g2d.drawImage(biAstroStandLeft, 30, 16, this);
    }

    public void DoAnimation() {//counts the nAstroAnimPos variable, which determines which Astronaut image is drawn. There is also a nASTROIMGMAX variable, which makes the animation a bit slower. Otherwise the Astronaut would walk too fast. nASTROIMGMAX if set for 6 for the 6 images so when it reaches the end it resets to six
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

    class TAdapter extends KeyAdapter {//Setting up the keys

        boolean bMoving = false;

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();//getting keycodes
            if (bInGame) {//if you're in game you can press these keys               
                if (key == KeyEvent.VK_LEFT) {
                    nReqDX = -1;
                } else if (key == KeyEvent.VK_RIGHT) {
                    nReqDX = 1;
                } else if (key == KeyEvent.VK_SPACE) {//Interaction
                    //Yet to be filled
                } else if (key == KeyEvent.VK_ESCAPE && tTimer.isRunning()) {
                    bInGame = false;
                } else if (key == KeyEvent.VK_PAUSE) {
                    if (tTimer.isRunning()) {
                        tTimer.stop();
                    } else {
                        tTimer.start();
                    }
                }
            } else {//if you're not in game only allow the s key to be pressed then start the game
                if (key == 's' || key == 'S') {
                    bInGame = true;
                    GameRun();
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {//releasing the keys should stop him but it doesn't for some reason
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
                nReqDX = 0;//Req is his required direction or something along those lines
                System.out.println(nReqDX);
                nCurrentSpeed = 0;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}