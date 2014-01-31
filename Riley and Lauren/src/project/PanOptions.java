
package project;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

class PanOptions extends JPanel {

    JLabel lblInstructions;
    PanOptions1 panOptions1 = new PanOptions1();

    public PanOptions() {

        setLayout(new BorderLayout());
        lblInstructions = new JLabel("<html> Instructions: "
                + " <br /> Start off the game by placing materials on the map. "
                + "<br/>At the beginning you are able to place 5 of each of the"
                + "<br/> following:trees, ponds, caves, and bushes. In this game "
                + "<br/> of life there are 'cells' that will move around "
                + "<br/>simultaneously and work with thematerials you have left "
                + "<br/>for them. </html>");
        add(lblInstructions, BorderLayout.NORTH);
        add(panOptions1, BorderLayout.SOUTH);


        //btnReturn = new JButton("Back");
        //add(btnReturn);
    }
}

class PanOptions1 extends JPanel implements ActionListener {

    JButton  btnReturn, btnSmall, btnLarge;
    JLabel lblSize;

    public PanOptions1() {
        btnSmall = new JButton("Small");
        btnLarge = new JButton("Large");
        btnReturn = new JButton("Main Menu");

        add(btnSmall);
        add(btnLarge);
        add(btnReturn);

        btnSmall.addActionListener(this);
        btnLarge.addActionListener(this);
        btnReturn.addActionListener(this);
    }
    PanGrid1 panGrid;

    public void actionPerformed(ActionEvent e) {

        TitleMenu title = new TitleMenu();
       if (e.getSource() == btnSmall) {
            panGrid.nX = 10;
            panGrid.nY = 10;
            lblSize.setText("Small");
        }
        if (e.getSource() == btnLarge) {
            panGrid.nX = 25;
            panGrid.nY = 25;
            lblSize.setText("Large");
        }
        if (e.getSource() == btnReturn) {
            title.cl.first(title.panMaster);
        }

    }
}
