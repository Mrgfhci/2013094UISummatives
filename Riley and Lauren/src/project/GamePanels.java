package project;

import java.awt.BorderLayout;

import javax.swing.JPanel;
//import javax.swing.JPanel;

public class GamePanels extends JPanel {

    PanStatus panStatus = new PanStatus();
    //PanStatus panStatus;
    PanGrid panGrid = new PanGrid(panStatus);

    public GamePanels() {

        setLayout(new BorderLayout());
        add(panStatus, BorderLayout.NORTH);
        add(new PanInventory(), BorderLayout.SOUTH);
        add(panGrid, BorderLayout.CENTER);
        setVisible(true);



    }
}
