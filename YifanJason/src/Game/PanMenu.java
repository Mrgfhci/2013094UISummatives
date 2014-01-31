package Game;

import static Game.Main.frame;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanMenu extends JPanel {

    Main main = new Main();
    JLabel lblTitle = new JLabel("AWESOME GAME");
    JButton btn = new JButton("Start");

    public PanMenu() {
        add(lblTitle);
        add(btn);

        class MovePlayerListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent event) {
                CardLayout cl = (CardLayout) (Main.panMain.getLayout());

                if (event.getActionCommand().equals("Start")) {
                      Main.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                Main.frame.setSize(700, 636);
                Main.frame.setVisible(true);
                    cl.show(Main.panMain, "2");
                    System.out.println("CLICKED START");
                }
                revalidate();

            }
        }
        ActionListener moveplayerListener = new MovePlayerListener();
        btn.addActionListener(moveplayerListener);
    }
}
