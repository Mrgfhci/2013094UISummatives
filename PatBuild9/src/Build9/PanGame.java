package Build9;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import javax.imageio.ImageIO;
import java.io.IOException;
import javax.swing.JPanel;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;
//This is the main panel of the game
public class PanGame extends JPanel implements Runnable {

    private int nSunX, nSunY, nChange = 0, d, c, nCloud1 = 230, nCloud2 = 400,
            nCloud3 = 650, nCloud4 = 1045;
    private BufferedImage background;
    private final BufferedImage sun, cloud, pause;
    private final Hero hero;
    private final Sorcerer1 sor1;
    private final Sorcerer2 sor2;
    private final Fireball1 fireball1;
    private final Fireball2 fireball2;
    private final Knight knight;
    private final Boss boss;
    private boolean isCol1, isCol2, isCol, isSorcerer = true, isKnight, isBoss;
    private final Main main = new Main();
    private final int DELAY = 9;
    private Thread thread;
    private final Clip clipBreach;
    private final AudioInputStream AISBreach;
    private AudioInputStream AISFireball, AISShield;    
    private long before = 0, delay = 480;

    public PanGame() throws Exception {
        //KeyBindings: http://stackoverflow.com/questions/15753551/java-keybindings-how-does-it-work
        //I used KeyBindings instead of KeyEvents because the game loses focus when the
        //start button is pressed with the latter.
        InputMap im = this.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = this.getActionMap();
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "RightArrow");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "rlRightArrow");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "LeftArrow");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "rlLeftArrow");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_C, 0, false), "C");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_C, 0, true), "rlC");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_X, 0, false), "X");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_X, 0, true), "rlX");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, 0, false), "Z");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, 0, true), "rlZ");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0, false), "P");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0, true), "rlP");
        am.put("RightArrow", new KeyAction("RightArrow"));
        am.put("rlRightArrow", new KeyAction("rlRightArrow"));
        am.put("LeftArrow", new KeyAction("LeftArrow"));
        am.put("rlLeftArrow", new KeyAction("rlLeftArrow"));
        am.put("C", new KeyAction("C"));
        am.put("rlC", new KeyAction("rlC"));
        am.put("X", new KeyAction("X"));
        am.put("rlX", new KeyAction("rlX"));
        am.put("Z", new KeyAction("Z"));
        am.put("rlZ", new KeyAction("rlZ"));
        am.put("P", new KeyAction("P"));
        am.put("rlP", new KeyAction("rlP"));
        setFocusable(true);
        setDoubleBuffered(true);
        background = ImageIO.read(getClass().getResourceAsStream("/background1.png"));
        sun = ImageIO.read(getClass().getResourceAsStream("/sun.png"));
        cloud = ImageIO.read(getClass().getResourceAsStream("/cloud.png"));
        pause = ImageIO.read(getClass().getResourceAsStream("/pause.png"));
        AISBreach = AudioSystem.getAudioInputStream(getClass().getResource("/TheBreach.wav"));
        clipBreach = AudioSystem.getClip();
        hero = new Hero();
        sor1 = new Sorcerer1();
        sor2 = new Sorcerer2();
        fireball1 = new Fireball1();
        fireball2 = new Fireball2();
        knight = new Knight();
        boss = new Boss();
    }

    @Override
    public void addNotify() {
        super.addNotify();
        thread = new Thread(this);
        thread.start();
    }
    //Threads run more consistentantly than timers.
    @Override
    public void run() {
        long beforeTime, timeDiff, sleep;
        beforeTime = System.currentTimeMillis();
        while (true) {
            if (!hero.getPause()) {
                if (!hero.getAction() && !isCol2 && !isCol1 && !isCol) {
                    if (isBoss & hero.getX() > 750) {//Quick fix for collision not working well on boss.
                        hero.setX(hero.getX() - 1);
                    }
                    hero.move();
                }
                if (fireball1.isVisible()) {
                    fireball1.move();
                }
                if (fireball2.isVisible()) {
                    fireball2.move();
                }
                if (!isBoss) {
                    clipBreach.close();
                }
            }
            repaint();
            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;
            if (sleep < 0) {
                sleep = 9;
            }
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                System.out.println("interrupted");
            }
            beforeTime = System.currentTimeMillis();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (hero.getPause()) {
            g.drawImage(background, 0, 0, null);
            g.drawImage(pause, 0, 0, null);
            clipBreach.close();
            return;
        }
        g.drawImage(background, 0, 0, null);
        
        g.drawImage(sun, nSunX, nSunY, null);
        if (hero.getHealth() <= 0) {
            clipBreach.close();
        }
        g.drawImage(hero.getImage(), hero.getX(), hero.getY(), this);
        //Loads the sorcerer level
        if (isSorcerer) {
            sor1.Sor1Health(g);
            sor2.Sor2Health(g);
            nSunX = 1068;
            nSunY = 55;
            int[] nCloudX = {nCloud1, nCloud2, nCloud3, nCloud4};
            int[] nCloudY = {40, 85, 5, 165};
            for (int i = 0; i < nCloudX.length; i++) {
                g.drawImage(cloud, nCloudX[i], nCloudY[i], null);
            }
            if (sor1.getHealth() >= 0) {
                g.drawImage(sor1.getImage(), sor1.getX(), sor1.getY(), this);
            }
            if (sor2.getHealth() >= 0) {
                g.drawImage(sor2.getImage(), sor2.getX(), sor2.getY(), this);
            }
            if (sor1.getAttack()) {
                fireball1.setVisible(true);
            }
            if (fireball1.isVisible()) {
                g.drawImage(fireball1.getImage(), fireball1.getX(), fireball1.getY(), this);
            }
            if (sor2.getAttack()) {
                fireball2.setVisible(true);
            }
            if (fireball2.isVisible()) {
                g.drawImage(fireball2.getImage(), fireball2.getX(), fireball2.getY(), this);
            }
            if (sor1.getAttack() || sor2.getAttack()) {
                try {
                    Clip clipFireball = AudioSystem.getClip();
                    AISFireball = AudioSystem.getAudioInputStream(getClass().getResource("/Fireball.wav"));
                    clipFireball.open(AISFireball);
                    clipFireball.start();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            checkCollisionsSor();
        }
        //Loads the knight level
        if (isKnight) {
            knight.KnightHealth(g);
            nSunX = 600;
            nSunY = 5;
            int[] nCloudX = {nCloud1, nCloud2, nCloud3, nCloud4};
            int[] nCloudY = {40, 85, 5, 130};
            for (int i = 0; i < nCloudX.length; i++) {
                g.drawImage(cloud, nCloudX[i], nCloudY[i], null);
            }
            if (knight.getHealth() >= 0) {
                g.drawImage(knight.getImage(), knight.getX(), knight.getY(), this);
            }
            checkCollisionsKnight();
        }
        //Loads the boss level
        if (isBoss) {
            if (hero.getX() < 619) {
                boss.Beam(g);
            } else if (hero.getX() > 700) {
                boss.Blast();
            }
            nSunX = 50;
            nSunY = 100;
            int[] nCloudX = {nCloud1, nCloud2, nCloud3, nCloud4};
            int[] nCloudY = {40, 85, 2, 130};
            for (int i = 0; i < nCloudX.length; i++) {
                g.drawImage(cloud, nCloudX[i], nCloudY[i], null);
            }
            if (boss.getHealth() >= 0) {
                g.drawImage(boss.getImage(), boss.getX(), boss.getY(), this);
            }
            checkCollisionsBoss();
            boss.BossHealth(g);
        }
        hero.HeroHealth(g);
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
        Clouds();
    }
    //Moves clouds
    public void Clouds() {
        if (d < 20) {
            c = 0;
        } else if (d > 20) {
            c = 1;
            d = 0;
            nCloud1 += c;
            nCloud2 += c;
            nCloud3 += c;
            nCloud4 += c;
        }
        d++;
        if (nCloud1 > 1280) {
            nCloud1 = -120;
        }
        if (nCloud2 > 1280) {
            nCloud2 = -120;
        }
        if (nCloud3 > 1280) {
            nCloud3 = -120;
        }
        if (nCloud4 > 1280) {
            nCloud4 = -120;
        }
    }

    //http://zetcode.com/tutorials/javagamestutorial/collision/
    public void checkCollisionsSor() {
        Rectangle RecHero = hero.getBounds(), RecFireball1 = fireball1.getBounds(),
                RecFireball2 = fireball2.getBounds(), RecSor1 = sor1.getBounds(),
                RecSor2 = sor2.getBounds();
        //checks collision of fireballs and hero
        if (fireball1.isVisible() && RecHero.intersects(RecFireball1)) {
            fireball1.setVisible(false);
            fireball1.setX(1120);
            hero.setHitRight(true);
        }
        if (fireball2.isVisible() && RecHero.intersects(RecFireball2)) {
            fireball2.setVisible(false);
            fireball2.setX(115);
            hero.setHitLeft(true);
        }
        //checks collision of the sorcerers and hero
        if (RecHero.intersects(RecSor1)) {
            if (hero.getRight() && sor1.getHealth() >= 0) {
                isCol1 = true;
                if (hero.getWeak()) {
                    sor1.setHealth(10);
                    hero.setWeak(false);
                } else if (hero.getStrong()) {
                    sor1.setHealth(30);
                    hero.setStrong(false);
                }
            } else if (!hero.getRight()) {
                isCol1 = false;
            }
        }
        if (RecHero.intersects(RecSor2)) {
            if (!hero.getRight() && sor2.getHealth() >= 0) {
                isCol2 = true;
                if (hero.getWeak()) {
                    sor2.setHealth(10);
                    hero.setWeak(false);
                } else if (hero.getStrong()) {
                    sor2.setHealth(30);
                    hero.setStrong(false);
                }
            } else if (hero.getRight()) {
                isCol2 = false;
            }
        }
        //if both fireballs collide
        if (RecFireball1.intersects(RecFireball2)) {
            fireball1.setVisible(false);
            fireball1.setX(1120);
            fireball2.setVisible(false);
            fireball2.setX(115);
        }
        if (!RecHero.intersects(RecSor1)) {
            isCol1 = false;
        }
        if (!RecHero.intersects(RecSor2)) {
            isCol2 = false;
        }
        //Switch to kngiht level
        if (sor1.getHealth() <= 0 && sor2.getHealth() <= 0 && nChange == 0) {
            isSorcerer = false;
            isKnight = true;
            try {
                background = ImageIO.read(getClass().getResourceAsStream("/background2.png"));
            } catch (IOException e) {
                System.out.println("IOException!");
            }
            nChange++;
            hero.setX(300);
            hero.setRight(true);
            hero.setState(1);
            isCol1 = isCol2 = false;
            nCloud1 = 450;
            nCloud2 = 1000;
            nCloud3 = 200;
            nCloud4 = 400;
        }
    }

    public void checkCollisionsKnight() {
        Rectangle RecHero = hero.getBounds(), RecKnight = knight.getBounds();
        //checks collision of the knight and hero
        if (RecHero.intersects(RecKnight)) {
            if (hero.getRight() && knight.getHealth() >= 0) {
                isCol = true;
                if (hero.getWeak() && !knight.getBlock()) {
                    knight.setHealth(10);
                    hero.setWeak(false);
                } else if (hero.getStrong() && !knight.getBlock()) {
                    knight.setHealth(30);
                    hero.setStrong(false);
                }
                if (hero.getWeak() || hero.getStrong() && knight.getBlock()) {
                    try {
                         long now = System.currentTimeMillis();
                    if (now - before > delay) {
                        Clip clipShield = AudioSystem.getClip();
                        AISShield = AudioSystem.getAudioInputStream(getClass().getResource("/Shield.wav"));
                        clipShield.open(AISShield);
                        clipShield.start();
                    }
                    before = now;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            } else if (!hero.getRight()) {
                isCol = false;
            }
            if (knight.getAttack()) {
                knight.setAttack(false);
                hero.setHit(true);
            }
        }
        if (!RecHero.intersects(RecKnight)) {
            isCol = false;
        }
        //Switch to boss level
        if (knight.getHealth() <= 0 && nChange == 1) {
            isKnight = false;
            isBoss = true;
            try {
                background = ImageIO.read(getClass().getResourceAsStream("/background3.png"));
                clipBreach.open(AISBreach);
                clipBreach.loop(Clip.LOOP_CONTINUOUSLY);
            } catch (Exception e) {
                System.out.println("Exception!");
            }
            nChange++;
            hero.setX(50);
            hero.setRight(true);
            hero.setState(1);
            isCol = false;
            nCloud1 = 700;
            nCloud2 = 720;
            nCloud3 = 1100;
            nCloud4 = 100;
        }
    }

    public void checkCollisionsBoss() {
        Rectangle RecHero = hero.getBounds(), RecBoss = boss.getBounds(),
                RecBeam = boss.recBeam.getBounds();
        //checks collision of the boss and hero
        if (RecHero.intersects(RecBoss) && boss.getHealth() >= 0) {
            if (hero.getRight()) {
                isCol = true;
                if (hero.getWeak()) {
                    boss.setHealth(5);
                    hero.setWeak(false);
                } else if (hero.getStrong()) {
                    boss.setHealth(15);
                    hero.setStrong(false);
                }
            } else if (!hero.getRight()) {
                isCol = false;
            }
            if (boss.getBlast() && hero.getX() > 700) {
                hero.setBlast(true);
                boss.setBlast(false);
            }
        } else if (RecHero.intersects(RecBeam) && boss.getHealth() >= 0 && boss.getDelay() == 1 && hero.getX() < 619) {
            if (hero.getRight()) {
                isCol = true;
            } else if (!hero.getRight()) {
                isCol = false;
            }
            if (boss.getBeam()) {
                boss.setBeam(false);
                boss.setGrow(false);
                hero.setBeam(true);
            }
        } else if (!RecHero.intersects(RecBeam)) {
            boss.setBeam(true);
            boss.setGrow(true);
            isCol = false;
        }
        if (boss.getDelay() != 1) {
            isCol = false;
        }
        //You win!
        if (boss.getHealth() <= 0) {
            clipBreach.close();
            main.Win();
        }
    }

    public class KeyAction extends AbstractAction {

        private String cmd;

        public KeyAction(String cmd) {
            this.cmd = cmd;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (cmd.equalsIgnoreCase("LeftArrow")) {
                hero.keyPressed("left");
            } else if (cmd.equalsIgnoreCase("rlLeftArrow")) {
                hero.keyReleased("rlleft");
            } else if (cmd.equalsIgnoreCase("RightArrow")) {
                hero.keyPressed("right");
            } else if (cmd.equalsIgnoreCase("rlRightArrow")) {
                hero.keyReleased("rlright");
            } else if (cmd.equalsIgnoreCase("C")) {
                hero.keyPressed("C");
            } else if (cmd.equalsIgnoreCase("rlC")) {
                hero.keyReleased("rlC");
            } else if (cmd.equalsIgnoreCase("X")) {
                hero.keyPressed("X");
            } else if (cmd.equalsIgnoreCase("rlX")) {
                hero.keyReleased("rlX");
            } else if (cmd.equalsIgnoreCase("Z")) {
                hero.keyPressed("Z");
            } else if (cmd.equalsIgnoreCase("rlZ")) {
                hero.keyReleased("rlZ");
            } else if (cmd.equalsIgnoreCase("P")) {
                hero.keyPressed("P");
            } else if (cmd.equalsIgnoreCase("rlP")) {
                hero.keyReleased("rlP");
            }
        }
    }
}
