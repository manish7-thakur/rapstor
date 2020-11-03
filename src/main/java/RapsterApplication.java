import com.rapstor.gui.TabbedFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RapsterApplication {

    /**
     * @param args
     */
    public static void main(String[] args) {
        final JFrame rapsterFrame = new TabbedFrame();
        rapsterFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		/*JFrame displayFrame = new ScreenDisplayFrame();
		displayFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		displayFrame.setVisible(true);*/
        Runnable displayThreadRunnable = new DisplayThread();
        Thread displayThread = new Thread(displayThreadRunnable);
        displayThread.start();
		/*ActionListener listener = new DisplayAction();
		Timer intervalTimer = new Timer(5000,listener);
		intervalTimer.start();*/
        final TrayIcon icon;
        if (!SystemTray.isSupported()) {
            System.err.println("System tray not supported");
            return;
        }
        SystemTray tray = SystemTray.getSystemTray();
        PopupMenu popup = new PopupMenu();
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                System.exit(0);
            }
        });

        MenuItem UIItem = new MenuItem("Open UI");
        UIItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                rapsterFrame.setVisible(true);
            }
        });
        popup.add(UIItem);
        popup.add(exitItem);
//URL url = RapsterApplication.class.getResource("Images/Birla.Jpeg");
        ClassLoader cl = RapsterApplication.class.getClassLoader();
        icon = new TrayIcon(Toolkit.getDefaultToolkit().getImage(cl.getResource("Launch.jpeg")), "CL Rapstor", popup);
        icon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                icon.displayMessage("What the hell you want?", "Right click for the list of options", TrayIcon.MessageType.INFO);
            }
        });
        icon.setImageAutoSize(true);
        try {
            tray.add(icon);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

}
