package com.rapstor.gui;

import com.rapstor.io.DataIO;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.RandomAccessFile;


public class ScreenDisplayFrame extends JFrame {
    public ScreenDisplayFrame() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int screenHeight = (int) (0.23 * screenSize.getHeight());
        int screenWidth = screenHeight + 70;
        setSize(screenWidth, screenHeight);
        setUndecorated(true);
        setAlwaysOnTop(true);
        setFocusableWindowState(false);
        setIconImage(Toolkit.getDefaultToolkit().getImage("Launch.jpeg"));
        screenDisplayPanel = new JPanel();
        screenDisplayPanel.setBorder(BorderFactory.createEtchedBorder());
        shortMessageField = new JTextField(SHORT_MESSAGE_LENGTH);
        shortMessageField.setBorder(BorderFactory.createRaisedBevelBorder());
        shortMessageField.setEditable(false);
        shortMessageField.setText("No Messages Stored");
        longDescArea = new JTextArea();
        longDescArea.setBorder(BorderFactory.createRaisedBevelBorder());
        longDescArea.setEditable(false);
        longDescArea.setLineWrap(true);
        longDescArea.setText("To add a note click on tray icon and choose \"Open UI\"");
        screenDisplayPanel.setLayout(new GridBagLayout());
        screenDisplayPanel.add(shortMessageField, new GBC(0, 0).setAnchor(GBC.EAST).setSpan(1, 1).setWeight(100, 0).setFill(GBC.HORIZONTAL));
        screenDisplayPanel.add(longDescArea, new GBC(0, 1).setSpan(1, 1).setWeight(100, 100).setAnchor(GBC.EAST).setFill(GBC.BOTH));
        add(screenDisplayPanel);
    }

//Override setvisible true

    @Override
    public void setVisible(boolean b) {
        if (b == true) {
            super.setVisible(b);  /*update(getGraphics());*/
            for (int i = 0; i <= this.getWidth(); i++) {
                setLocation(Toolkit.getDefaultToolkit().getScreenSize().width - i, YOFFSET);
				
				/*try 
					{
				Thread.sleep(0);
					}
				catch(InterruptedException ex)
				{
					ex.printStackTrace();
				}*/
            }

        } else {
            for (int i = 0; i <= this.getWidth(); i++) {
                setLocation(this.getX() + i, YOFFSET);
				
				/*try 
					{
				Thread.sleep(0);
					}
				catch(InterruptedException ex)
				{
					ex.printStackTrace();
				}*/

            }
            super.setVisible(b);
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

    private JTextField shortMessageField; // don't hard Code
    private JTextArea longDescArea;
    private JPanel screenDisplayPanel;
    private static final int SHORT_MESSAGE_LENGTH = 30;
    private static final int LONG_MESSAGE_LENGTH = 250;
    private static final int SCREEN_WIDTH = 300;
    private static final int SCREEN_HEIGHT = 200;
    private static final int YOFFSET = 30;
}