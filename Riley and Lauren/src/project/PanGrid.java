package project;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

class PanGrid extends JPanel {

    PanStatus panStatus;
    PanGrid1 panGrid1 = new PanGrid1(panStatus);

    public PanGrid(PanStatus _panStatus) {
        add(panGrid1);
    }
}

class PanGrid1 extends JPanel implements ActionListener {

    int nX = 10;
    int nY = 10;
    JButton[][] arsBtns = new JButton[nX][nY];
    static int nCount = 0;
    PanStatus panStatus;
    //int arnGrid[][] = new int[nX][nY];
    //int nDir = 0;
    //PanGrid1 panGrid1 = new PanGrid1(panOptions);
    //int nPosX = (int) (Math.random() * 10), nPosY = (int) (Math.random() * 10);
    //int arnDirX[] = {1, 0, -1, 0}, arnDirY[] = {0, -1, 0, 1};
    //int arnPosX[] = new int[10];
    //int arnPoxY[] = new int[10];
    //int nGrass = 1;
    //int nTree = 2;

    public PanGrid1(PanStatus _panStatus) {
        panStatus = _panStatus;
        ImageIcon grass = new ImageIcon(getClass().getResource("Turf2.jpg"));
        int arsGrid[][] = new int[nX][nY];
        setLayout(new GridLayout(nX, nY));
        for (int x = 0; x < nX; x++) {
            for (int y = 0; y < nY; y++) {
                //arnGrid[x][y] = nGrass;
                arsBtns[x][y] = new JButton(grass);
                arsBtns[x][y].setBorder(BorderFactory.createEmptyBorder());
                arsBtns[x][y].setContentAreaFilled(false);
                arsBtns[x][y].addActionListener(new ActionGrid());
                add(arsBtns[x][y]);
            }
        }
    }
 /*   ActionListener CellMove = new ActionListener() {
        ImageIcon grass = new ImageIcon(getClass().getResource("Turf2.jpg"));
        ImageIcon cell = new ImageIcon(getClass().getResource("Cell.jpg"));

        @Override
        public void actionPerformed(ActionEvent e) {
            arsBtns[nPosX][nPosY].setIcon(grass);
            nDir = (int) (Math.random() * 4);
            String sDir = null;
            String sMove = CheckMove(nDir, nPosX, nPosY);
            if (nDir == 0) {
                sDir = "South";
                if (sMove.equalsIgnoreCase("NoSouth")) {
                    System.out.println(sMove);
                    nDir = 2;
                } else {
                    nPosX += arnDirX[0];
                    nPosY += arnDirY[0];
                }
            }
            if (nDir == 1) {
                sDir = "West";
                if (sMove.equalsIgnoreCase("NoWest")) {
                    System.out.println(sMove);
                    nDir = 3;

                } else {
                    nPosX += arnDirX[1];
                    nPosY += arnDirY[1];
                }
            }
            if (nDir == 2) {
                sDir = "North";
                if (sMove.equalsIgnoreCase("NoNorth")) {
                    System.out.println(sMove);
                    nDir = 0;

                } else {
                    nPosX += arnDirX[2];
                    nPosY += arnDirY[2];
                }
            }
            if (nDir == 3) {
                sDir = "East";
                if (sMove.equalsIgnoreCase("NoEast")) {
                    System.out.println(sMove);
                    nDir = 1;
                } else {
                    nPosX += arnDirX[3];
                    nPosY += arnDirY[3];
                }
            }
            System.out.println("Cell Moving: " + sDir);
            arsBtns[nPosX][nPosY].setIcon(cell);
            revalidate();

        }

        private String CheckMove(int nDir, int nPosX, int nPosY) {
            int nCheckX = nPosX;
            int nCheckY = nPosY;
            if ((nCheckX + 1) > 9) {
                return "NoSouth";
            } else if ((nCheckY + 1) > 9) {
                return "NoEast";
            } else if ((nCheckX - 1) < 0) {
                return "NoNorth";
            } else if ((nCheckY - 1) < 0) {
                return "NoWest";
            }
            return "FreeToMove";
        }
    }; */

    class ActionGrid implements ActionListener {

        public ActionGrid() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            ImageIcon tree = new ImageIcon(getClass().getResource("Turf3.jpg"));
            ImageIcon pond = new ImageIcon(getClass().getResource("water.jpg"));
            ImageIcon bush = new ImageIcon(getClass().getResource("bush.jpg"));
            ImageIcon cave = new ImageIcon(getClass().getResource("cave.jpg"));

//            ImageIcon Pond = new ImageIcon(getClass().getResource("pond.png"));
//            ImageIcon Bush = new ImageIcon(getClass().getResource("DontStarve.png"));
//            ImageIcon Cave = new ImageIcon(getClass().getResource("cave.png"));

            if (e.getSource() instanceof JButton) {
                nCount += 1;

                if (nCount <= 4) {
                    ((JButton) e.getSource()).setIcon(tree);
                    // System.out.println(nCount);
                }
                if (nCount == 5) {

                    ((JButton) e.getSource()).setIcon(tree);

                    // System.out.println(nCount);
                    panStatus.UpdateLabelPond();
                    System.out.println("Pond Calling");
                }
                if (nCount >= 6 && nCount <= 8) {
                    System.out.println("Pond Called");
                    ((JButton) e.getSource()).setIcon(pond);
                    panStatus.UpdateLabelPond();
                    // System.out.println(nCount);
                }
                if (nCount == 9) {
                    ((JButton) e.getSource()).setIcon(pond);
                    panStatus.UpdateLabelBush();
                    // System.out.println(nCount);
                }

                if (nCount >= 11 && nCount <= 12) {
                    ((JButton) e.getSource()).setIcon(bush);
                    panStatus.UpdateLabelBush();
                    // System.out.println(nCount);                   
                }
                if (nCount == 13) {
                    ((JButton) e.getSource()).setIcon(bush);
                    panStatus.UpdateLabelCave();
                    // System.out.println(nCount);                   
                }

                if (nCount >= 14 && nCount <= 15) {
                    ((JButton) e.getSource()).setIcon(cave);
                    panStatus.UpdateLabelCave();
                    // System.out.println(nCount);    

                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
