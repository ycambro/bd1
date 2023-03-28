package tec.bd.gui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.ImageIcon;

public class MainFrame extends JFrame implements ActionListener {
    JMenuItem getByZipCode;
    JMenuItem getByCity;
    JMenuItem us;

    JFrame window;

    JMenuBar menu;

    JMenu getBy;
    JMenu info;

    ImageIcon background;
    ImageIcon text;

    JLabel label1;
    JLabel label2;

    CodeFrame code;
    CityFrame city;

    Image myIcon;

    public MainFrame() {
        /* Se crea la ventana principal */
        window = new JFrame("Weather Report");
        window.setSize(1024, 525);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);

        menu = new JMenuBar();
        getBy = new JMenu("Get Weather");
        info = new JMenu("Information");
        menu.add(getBy);
        menu.add(info);

        us = new JMenuItem("About us");
        us.addActionListener(this);

        info.add(us);

        getByZipCode = new JMenuItem("Get by Zip Code");
        getByCity = new JMenuItem("Get by City Name");

        getBy.add(getByZipCode);
        getByZipCode.addActionListener(this);
        getBy.add(getByCity);
        getByCity.addActionListener(this);

        window.getContentPane().add(BorderLayout.NORTH, menu);

        background = new ImageIcon(MainFrame.class.getResource("/gif.gif"));
        label1 = new JLabel(" ", background, SwingConstants.CENTER);
        label1.setSize(1024, 525);
        label1.setLocation(new Point(1, 1));

        myIcon = Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/logo.png"));
        window.setIconImage(myIcon);

        text = new ImageIcon(MainFrame.class.getResource("/text.png"));
        label2 = new JLabel(" ", text, SwingConstants.CENTER);
        label2.setSize(1024, 525);
        label2.setLocation(new Point(1, 2));

        window.add(label2);
        window.add(label1);

        window.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == us) {
            JOptionPane.showMessageDialog(window, "Weather Report \n Hecho por: Yurgen Cambronero 2022128005");

        } else if (e.getSource() == getByZipCode) {
            code = new CodeFrame(this, true);
        } else if (e.getSource() == getByCity) {
            city = new CityFrame(this, true);
        }
    }
}
