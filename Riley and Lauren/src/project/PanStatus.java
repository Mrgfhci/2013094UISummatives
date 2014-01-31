package project;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class PanStatus extends JPanel {

    // int nCount = 0;
    JLabel lblCells = new JLabel("0");
    JLabel lblTime = new JLabel("0:00");
    JLabel lblCount = new JLabel();
    public JLabel lblIcon = new JLabel();
    PanGrid panGrid;
    PanGrid1 panGrid1;
    public JLabel lblDir = new JLabel("Add 5 Trees");

    public PanStatus() {
        ImageIcon Tree = new ImageIcon(getClass().getResource("tree.jpg"));
        
        //ImageIcon Bush = new ImageIcon(getClass().getResource("DontStarve.png"));
       // ImageIcon Cave = new ImageIcon(getClass().getResource("cave.png"));
        lblIcon.setIcon(Tree);

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 4;
        c.ipady = 10;
        c.ipadx = 10;
        add(lblCells, c);
        add(lblTime, c);
        add(lblIcon, c);
        add(lblDir, c);
        //add(lblCount, c);
        Timer timer = new Timer(1000, null);
        timer.addActionListener(new ActionTimer());
        timer.start();

        revalidate();

    }

    public void UpdateLabelPond() {
        System.out.println("Pond Called");
        ImageIcon Pond = new ImageIcon(getClass().getResource("pond.png"));
        lblIcon.setIcon(Pond);
        lblDir.setText("Place 4 ponds");
        revalidate();
        

    }
    public void UpdateLabelBush() {
        ImageIcon Bush = new ImageIcon(getClass().getResource("DontStarve.png"));
        lblIcon.setIcon(Bush);
        lblDir.setText("Place 3 bushes");
        revalidate();

    }
    public void UpdateLabelCave() {
        ImageIcon Cave = new ImageIcon(getClass().getResource("cave.png"));
        lblIcon.setIcon(Cave);
        lblDir.setText("Place 2 caves");
        revalidate();

    }

    class ActionTimer implements ActionListener {

        int nCount1 = 0;
        int nCount2 = 0;
        int nCount3 = 0;

        public ActionTimer() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String sCount1, sCount2, sCount3;
            nCount1 += 1;
            if (nCount1 == 10) {
                nCount1 = 0;
                nCount2 += 1;
            } else if (nCount2 == 10) {
                nCount2 = 0;
                nCount3 += 1;
            }
            sCount1 = Integer.toString(nCount1);
            sCount2 = Integer.toString(nCount2);
            sCount3 = Integer.toString(nCount3);
            lblTime.setText(sCount3 + ":" + sCount2 + "" + sCount1);
            //System.out.println(nCount1);
            revalidate();

        }
    }
}
