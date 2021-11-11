package com.rapstor.gui;

import com.rapstor.io.DataIO;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.RandomAccessFile;


public class ScreenDisplayFrame extends JFrame {
    public ScreenDisplayFrame() {
        System.out.println("icon-bulb");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int screenHeight = (int) (0.12 * screenSize.getHeight());
        int screenWidth = (int) (0.20 * screenSize.getHeight() + screenHeight/2);
        setSize(screenWidth, screenHeight);
        setUndecorated(true);
        setAlwaysOnTop(true);
        setFocusableWindowState(false);
        setIconImage(Toolkit.getDefaultToolkit().getImage("Launch.jpeg"));
        screenDisplayPanel = new JPanel();
        shortMessageField = new JLabel("No Messages Stored");
        int imageBound = shortMessageField.getPreferredSize().height;
        Image image = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("icon-bulb.png"))
                .getScaledInstance(imageBound, imageBound,  java.awt.Image.SCALE_SMOOTH);
        shortMessageField.setIcon(new ImageIcon(image));
        longDescArea = new JTextArea();
        longDescArea.setEditable(false);
        longDescArea.setLineWrap(true);
        longDescArea.setText("To add a note click on tray icon and choose \"Open UI\"");
        screenDisplayPanel.setDoubleBuffered(true);
        screenDisplayPanel.setOpaque(false);
        screenDisplayPanel.setLayout(new GridBagLayout());
        screenDisplayPanel.add(shortMessageField, new GBC(0, 0).setAnchor(GBC.EAST).setSpan(1, 1).setWeight(100, 0).setFill(GBC.HORIZONTAL));
        screenDisplayPanel.add(longDescArea, new GBC(0, 1).setSpan(1, 1).setWeight(100, 100).setAnchor(GBC.EAST).setFill(GBC.BOTH));
        add(screenDisplayPanel);
    }

//Override setvisible true

    @Override
    public void setVisible(boolean b) {
        if (b) {
            super.setVisible(b);  /*update(getGraphics());*/
            for (int i = 0; i <= this.getWidth(); i++) {
                setLocation(Toolkit.getDefaultToolkit().getScreenSize().width - i, YOFFSET);
            }

        } else {
            for (int i = 0; i <= this.getWidth(); i++) {
                setLocation(this.getX() + i, YOFFSET);
            }
            super.setVisible(false);
        }
    }

    //Reads Data
    public void readData(RandomAccessFile file) throws IOException {
        shortMessageField.setText(DataIO.readFixedString(SHORT_MESSAGE_LENGTH, file));
        longDescArea.setText(DataIO.readFixedString(LONG_MESSAGE_LENGTH, file));
    }

    public void readData(String shortDescrition, String longDescrition) {
        shortMessageField.setText(shortDescrition);
        longDescArea.setText(longDescrition);
    }

    private JLabel shortMessageField; // don't hard Code
    private JTextArea longDescArea;
    private JPanel screenDisplayPanel;
    private static final int SHORT_MESSAGE_LENGTH = 30;
    private static final int LONG_MESSAGE_LENGTH = 250;
    private static final int YOFFSET = 30;
}