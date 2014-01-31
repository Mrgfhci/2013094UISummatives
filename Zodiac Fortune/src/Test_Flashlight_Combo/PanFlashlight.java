package Test_Flashlight_Combo;

import Flashlight.*;
import java.awt.*;

import java.awt.RadialGradientPaint;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class PanFlashlight extends JPanel{

   /* public static void main(String[] args) {
        new Flashlight();
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }
                JFrame frame = new JFrame("Mouse Over");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                frame.add(new PanFlashLight());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }*/

    public static class PanFlashLight extends JPanel {

        public static final int nRADIUS = 150;//size of your sight
        private Point ptMousePoint = null;
        private BufferedImage biBackground;

        public PanFlashLight() {

            //http://docs.oracle.com/javase/tutorial/uiswing/events/mousemotionlistener.html
            MouseAdapter mouseHandler = new MouseAdapter() {//http://stackoverflow.com/questions/2668718/java-mouselistener
                @Override
                public void mouseMoved(MouseEvent e) {
                    ptMousePoint = e.getPoint();//keeps trak of mouse and paints the radius
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    ptMousePoint = null;
                    repaint();//when the mouse leaves screen, repaints the glimpse in the darkness
                }
            };
            addMouseMotionListener(mouseHandler);//http://docs.oracle.com/javase/tutorial/uiswing/events/mousemotionlistener.html
            addMouseListener(mouseHandler);//http://stackoverflow.com/questions/8931669/how-to-add-a-mouse-up-event
            try {
                biBackground = ImageIO.read(new File("Assets\\Hallway.png"));//loads background
            } catch (IOException ex) {
                ex.printStackTrace();//prints the stack trace, the execution path of the program up until the exception.
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return biBackground == null ? new Dimension(0, 0) : new Dimension(biBackground.getWidth(), biBackground.getHeight());//sets the window to the size of the background image
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            if (biBackground != null) {
                int x = (getWidth() - biBackground.getWidth()) / 2;
                int y = (getHeight() - biBackground.getHeight()) / 2;
                g2d.drawImage(biBackground, x, y, this);
            }

            Paint paint = Color.BLACK;//Makes screen black when your mouse moves off the screen
            if (ptMousePoint != null) {
                paint = new RadialGradientPaint(
                        ptMousePoint,
                        nRADIUS,
                        new float[]{0, 1f},
                        new Color[]{new Color(0, 0, 0, 0), new Color(0, 0, 0, 255)});//first New colour is the clear circle where the mouse is, the second is the darkness on top
            }
            g2d.setPaint(paint);
            g2d.fillRect(0, 0, getWidth(), getHeight());//the rectangle of the image, shifts rectangle

            g2d.dispose();
        }
    }
}
