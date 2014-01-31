package Build9;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

//This class loads all of the attributes for the hero
public class Hero {

    private static int dx, nHeroX, nHeroY, nImgNum = 1, nDelay = 1, nState, nImage,
            nHealth = 100;
    public static BufferedImage BImgHero, BImgHeroPortriat;
    private final static BufferedImage[][] arBImgHero = new BufferedImage[7][7];
    public static boolean isAction, isRight = true, isMoving, isBlock, isStrong,
            isWeak, pause, isHitRight, isHitLeft, isHit, isPush, isPush2, isBeam,
            isBlast;
    private Rectangle recHealth;
    private final Main main = new Main();
    private long lastAtkTime, lastBlockTime;
    private final long ZDelay = 1500, XDelay = 600, CDelay = 500;
    private AudioInputStream AISHurt, AISPause, AISStrong, AISWeak, AISShield;

    public Hero() throws Exception {
        //First bracket is for the state of the hero and the second is for the image
        //1 - right at rest, 2 left at rest, 3 right moving, 4 left moving,
        //5 action right, 6 action left
        nState = nImage = 1;
        arBImgHero[1][1] = ImageIO.read(getClass().getResourceAsStream("/heroright.png"));
        arBImgHero[2][1] = ImageIO.read(getClass().getResourceAsStream("/heroleft.png"));
        for (int i = 1; i < arBImgHero.length; i++) {
            arBImgHero[3][i] = ImageIO.read(getClass().getResourceAsStream("/herorightwalk" + i + ".png"));
            arBImgHero[4][i] = ImageIO.read(getClass().getResourceAsStream("/heroleftwalk" + i + ".png"));
        }
        for (int i = 1; i < 4; i++) {
            arBImgHero[5][i] = ImageIO.read(getClass().getResourceAsStream("/herorightaction" + i + ".png"));
            arBImgHero[6][i] = ImageIO.read(getClass().getResourceAsStream("/heroleftaction" + i + ".png"));
        }
        BImgHeroPortriat = ImageIO.read(getClass().getResourceAsStream("/heroportrait.png"));
        isHitRight = isHitLeft = false;
        AISHurt = AudioSystem.getAudioInputStream(getClass().getResource("/Hurt.wav"));
        lastAtkTime = lastBlockTime = System.currentTimeMillis();
        nHeroX = 580;
        nHeroY = 505;
    }

    public int getX() {
        Push();
        return nHeroX;
    }

    public int getY() {
        return nHeroY;
    }

    public int getHealth() {
        return nHealth;
    }

    public int getState() {
        return nState;
    }

    public boolean getAction() {
        return isAction;
    }

    public boolean getStrong() {
        return isStrong;
    }

    public boolean getWeak() {
        return isWeak;
    }

    public boolean getPause() {
        return pause;
    }

    public boolean getRight() {
        return isRight;
    }

    public boolean getBlock() {
        return isBlock;
    }

    public boolean getPush() {
        return isPush;
    }

    public BufferedImage getImage() {
        if (isMoving) {
            Count();
        }
        if (isAction) {
            nState = 5;
        }
        main.Death();
        if (isRight) {
            BImgHero = arBImgHero[nState][nImage];
        } else {
            BImgHero = arBImgHero[nState + 1][nImage];
        }
        return BImgHero;
    }
    //I have so many ifs because I didn't to change the bounds of the hero to fit
    //the current image better.
    public Rectangle getBounds() {
        if (isRight && !isAction) {
            return new Rectangle(nHeroX + 28, nHeroY, BImgHero.getWidth() - 70, BImgHero.getHeight());
        } else if (!isRight && !isAction) {
            return new Rectangle(nHeroX + 42, nHeroY, BImgHero.getWidth() - 73, BImgHero.getHeight());
        } else if (isRight && isBlock) {
            return new Rectangle(nHeroX + 35, nHeroY, BImgHero.getWidth() - 59, BImgHero.getHeight());
        } else if (!isRight && isBlock) {
            return new Rectangle(nHeroX + 22, nHeroY, BImgHero.getWidth() - 58, BImgHero.getHeight());
        } else {
            return new Rectangle(nHeroX + 25, nHeroY, BImgHero.getWidth() - 25, BImgHero.getHeight());
        }
    }

    public void setX(int x) {
        nHeroX = x;
    }

    public void setRight(boolean b) {
        isRight = b;
    }

    public void setHitRight(boolean Hit) {
        isHitRight = Hit;
    }

    public void setHitLeft(boolean Hit) {
        isHitLeft = Hit;
    }

    public void setHit(boolean Hit) {
        isHit = Hit;
    }

    public void setWeak(boolean quick) {
        isWeak = quick;
    }

    public void setStrong(boolean strong) {
        isStrong = strong;
    }

    public void setPush(boolean push) {
        isPush = push;
    }

    public void setBeam(boolean beam) {
        isBeam = beam;
    }

    public void setBlast(boolean blast) {
        isBlast = blast;
    }

    public void setState(int state) {
        nState = nImage = state;
    }
    public void move() {
        nHeroX += dx;
    }

    public void HeroHealth(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        int health = nHealth;
        //Allows you to block attacks and if you're blocking the opposite way you'll get hurt
        if (isRight) {
            if (isHitRight && !isBlock) {
                nHealth -= 10;
            }
            if (isHitLeft) {
                nHealth -= 10;
            } else if (isHit) {
                if (!isBlock) {
                    nHealth -= 20;
                }
                isPush = true;
            }
            if (isBeam && !isBlock) {
                nHealth -= 10;
            } else if (isBlast) {
                if (!isBlock) {
                    nHealth -= 10;
                }
                isPush2 = true;
            }
        } else if (!isRight) {
            if (isHitRight) {
                nHealth -= 10;
            }
            if (isHitLeft && !isBlock) {
                nHealth -= 10;
            } else if (isHit) {
                nHealth -= 20;
                isPush = true;
            }
            if (isBeam) {
                nHealth -= 10;
            } else if (isBlast) {
                nHealth -= 10;
                isPush2 = true;
            }
        }
        if (health != nHealth) {
            try {
                Clip clipHurt = AudioSystem.getClip();
                AISHurt = AudioSystem.getAudioInputStream(getClass().getResource("/Hurt.wav"));
                clipHurt.open(AISHurt);
                clipHurt.start();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (isBlock && isBeam || isBlast || isHitLeft || isHitRight || isHit) {
            try {
                Clip clipShield = AudioSystem.getClip();
                AISShield = AudioSystem.getAudioInputStream(getClass().getResource("/Shield.wav"));
                clipShield.open(AISShield);
                clipShield.start();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        isHitRight = isHitLeft = isHit = isBeam = isBlast = false;
        recHealth = new Rectangle(50, 15, nHealth, 20);
        g.drawImage(BImgHeroPortriat, 0, 0, null);
        g.setColor(Color.red);
        g2.fill(recHealth);
        g.setColor(Color.black);
        g2.draw(recHealth);
    }

    private void Animation() {
        nState = 3;
        nImage = nImgNum;
    }

    //This method is part of the animation.  It's the counter that cycles
    //through the animation images.
    private void Count() {
        nDelay++;
        if (nDelay >= 24) {
            nImgNum++;
            if (nImgNum >= 7) {
                nImgNum = 1;
            }
            nDelay = 1;
        }
        Animation();
    }
    //This pushes back the hero when certain enemies do certain attacks.
    private void Push() {
        if (isPush && nHeroX > 910) {
            nHeroX -= 1;
        } else {
            isPush = false;
        }
        if (isPush2 && nHeroX > 620) {
            nHeroX -= 1;
        } else {
            isPush2 = false;
        }
    }

    //http://zetcode.com/tutorials/javagamestutorial/movingsprites/
    public void keyPressed(String s) {
        if (!isAction) {
            if (s.equals("left")) {
                dx = -1;
                if (nHeroX < -10) {
                    dx = 0;
                }
                isRight = false;
                isMoving = true;
            } else if (s.equals("right")) {
                dx = 1;
                if (nHeroX > 1200) {
                    dx = 0;
                }
                isRight = true;
                isMoving = true;

            } else if (s.equals("C")) {
                long timeNow = System.currentTimeMillis();
                if (timeNow - lastBlockTime < CDelay) {
                    return;
                }
                dx = 0;
                isAction = true;
                isMoving = false;
                isBlock = true;
                nImage = 1;
                lastBlockTime = timeNow;
            } else if (s.equals("X")) {
                long timeNow = System.currentTimeMillis();
                if (timeNow - lastAtkTime < XDelay) {
                    return;
                }
                try {
                    Clip clipWeak = AudioSystem.getClip();
                    AISWeak = AudioSystem.getAudioInputStream(getClass().getResource("/Weak.wav"));
                    clipWeak.open(AISWeak);
                    clipWeak.start();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                dx = 0;
                isAction = true;
                isMoving = false;
                isWeak = true;
                nImage = 2;
                lastAtkTime = timeNow;
            } else if (s.equals("Z")) {
                long timeNow = System.currentTimeMillis();
                if (timeNow - lastAtkTime < ZDelay) {
                    return;
                }
                try {
                    Clip clipStrong = AudioSystem.getClip();
                    AISStrong = AudioSystem.getAudioInputStream(getClass().getResource("/Strong.wav"));
                    clipStrong.open(AISStrong);
                    clipStrong.start();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                dx = 0;
                isAction = true;
                isMoving = false;
                isStrong = true;
                nImage = 3;
                lastAtkTime = timeNow;
            } else if (s.equals("P")) {
                if (!pause) {
                    pause = true;
                } else {
                    pause = false;
                }
                try {
                    Clip clipPause = AudioSystem.getClip();
                    if (pause) {
                        AISPause = AudioSystem.getAudioInputStream(getClass().getResource("/Pause.wav"));
                    } else {
                        AISPause = AudioSystem.getAudioInputStream(getClass().getResource("/Unpause.wav"));
                    }
                    clipPause.open(AISPause);
                    clipPause.start();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void keyReleased(String s) {
        //int key = e.getKeyCode();
        if (s.equals("rlright") || s.equals("rlleft") && !isAction) {
            dx = 0;
            isMoving = false;
            nState = nImage = 1;
        } else if (s.equals("rlC") && !isWeak && !isStrong) {
            isAction = isBlock = false;
            nState = nImage = 1;
        } else if (s.equals("rlX") && !isBlock && !isStrong) {
            isAction = isWeak = false;
            nState = nImage = 1;
        } else if (s.equals("rlZ") && !isBlock && !isWeak) {
            isAction = isStrong = false;
            nState = nImage = 1;
        }
        nImgNum = (int) (Math.random() * 6 + 1);
        nDelay = 0;
    }
}