package project;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

class PanInventory extends JPanel {

    JLabel lblMoney;
    JButton btn1;
    JButton btn2;
    JButton btn3;
    JButton btn4;
    JButton btn5;
    JButton btn6;
    JButton btnCraft;

    public PanInventory() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        // c.gridx = 0;
        c.gridy = 8;
        c.ipady = 20;
        c.ipadx = 20;
        //c.gridheight = 0;


        lblMoney = new JLabel("$0");
        lblMoney.setFont(new Font("Arial", Font.BOLD, 20));
        add(lblMoney, c);

        
        ImageIcon wood = new ImageIcon(getClass().getResource("tree.jpg"));
        btn1 = new JButton(wood);
        btn1.setBorder(BorderFactory.createEmptyBorder());
        btn1.setContentAreaFilled(false);
        add(btn1,c);

        ImageIcon gold = new ImageIcon(getClass().getResource("gold.jpg"));
        btn2 = new JButton(gold);
        btn2.setBorder(BorderFactory.createEmptyBorder());
        btn2.setContentAreaFilled(false);
        add(btn2, c);

        btn3 = new JButton("3");
        add(btn3, c);

        btn4 = new JButton("4");
        add(btn4, c);

        btn5 = new JButton("5");
        add(btn5, c);

        btn6 = new JButton("6");
        add(btn6, c);


        ImageIcon craft = new ImageIcon(getClass().getResource("craft.png"));
        btnCraft = new JButton(craft);
        btnCraft.setBorder(BorderFactory.createEmptyBorder());
        btnCraft.setContentAreaFilled(false);
        add(btnCraft, c);


    }
}
