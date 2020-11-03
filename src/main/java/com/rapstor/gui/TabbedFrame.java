package com.rapstor.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.prefs.Preferences;

public class TabbedFrame extends JFrame implements WindowListener {
    public TabbedFrame() {
        //setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setResizable(false);
        setTitle("CL Rapstor 1.0");
        //URL iconUrl = RapsterApplication.class.getResource("Images/Birla.jpeg");
        ClassLoader cl = this.getClass().getClassLoader();
        setIconImage(Toolkit.getDefaultToolkit().getImage(cl.getResource("Launch.jpeg")));
        root = Preferences.userRoot();
        node = root.node("/com/rapstor/gui");
        String theme = node.get("Theme", "javax.swing.plaf.metal.MetalLookAndFeel");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int screenHeight = (int) (0.52 * screenSize.getHeight());
        int screenWidth = screenHeight + 50;
        setSize(screenWidth, screenHeight);
        try {
            UIManager.setLookAndFeel(theme);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JTabbedPane rapstorTabbedPane = new JTabbedPane();
        NewMessagePanel newMessagePanel = new NewMessagePanel();
        rapstorTabbedPane.addTab("New Message", newMessagePanel);
        StoredMessagePanel storedMesPanel = new StoredMessagePanel();
        rapstorTabbedPane.addTab("Stored Messages", storedMesPanel);
		/*rapstorTabbedPane.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent evt)
				{
					if(rapstorTabbedPane.getSelectedComponent() ==  storedMesPanel);
					RandomAccessFile msgDataFile = new RandomAccessFile("Data/MesData.txt","r");// use File.seperator
					storedMesPanel.readData();
					storedMesPanel.displayData();
					msgDataFile.close();
				}
			
		});*/
        SettingPanel setPanel = new SettingPanel(this);
        rapstorTabbedPane.addTab("Settings", setPanel);
        AboutPanel aboutPanel = new AboutPanel("Birlasoft");
        rapstorTabbedPane.add("About", aboutPanel);
        add(rapstorTabbedPane);
        //setResizable(false);
        setLocation(node.getInt("X", 20), node.getInt("Y", 20));
        addWindowListener(this);
    }

    public void windowClosing(WindowEvent e) {
        node.putInt("X", getX());
        node.putInt("Y", getY());
    }

    public void windowOpened(WindowEvent e) {
    }

    public void windowClosed(WindowEvent e) {
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowDeactivated(WindowEvent e) {
    }

    Preferences root;
    Preferences node;
    private static final int SCREEN_WIDTH = 450;
    private static final int SCREEN_HEIGHT = 400;
}
