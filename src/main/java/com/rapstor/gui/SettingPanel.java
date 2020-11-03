package com.rapstor.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.Preferences;

public class SettingPanel extends JPanel {
    public SettingPanel(JFrame frame) {
        this.frame = frame;
        root = Preferences.userRoot();
        node = root.node("/com/rapstor/gui");
        OKButton = new JButton("OK");
        displayFashionLabel = new JLabel("Message Display Fashion");
        fashionCombo = new JComboBox(new String[]{"Orderly", "Random"});
        fashionCombo.setSelectedItem(node.get("Fashion", "Orderly"));
        messageIntervalLabel = new JLabel("Time interval between two messages(in Seconds)");
        messageIntervalCombo = new JComboBox(new Integer[]{10, 15, 30, 60, 300});
        messageIntervalCombo.setSelectedItem(new Integer(node.getInt("IntervalTime", 10)));
        messageStayIntervalLabel = new JLabel("Time span for which message remains on Screen(in Seconds)");
        messageStayIntervalCombo = new JComboBox(new Integer[]{5, 10, 15, 20, 30});
        messageStayIntervalCombo.setSelectedItem(new Integer(node.getInt("ShowTime", 5)));
        setLayout(new GridBagLayout());
        add(displayFashionLabel, new GBC(0, 0).setAnchor(GBC.EAST).setFill(GBC.HORIZONTAL).setWeight(100, 0));
        add(fashionCombo, new GBC(1, 0).setFill(GBC.HORIZONTAL).setWeight(100, 0));
        add(messageIntervalLabel, new GBC(0, 1).setAnchor(GBC.EAST).setWeight(100, 0).setFill(GBC.HORIZONTAL));
        add(messageIntervalCombo, new GBC(1, 1).setFill(GBC.HORIZONTAL).setWeight(100, 0));
        add(messageStayIntervalLabel, new GBC(0, 2).setAnchor(GBC.EAST).setWeight(100, 0).setFill(GBC.HORIZONTAL));
        add(messageStayIntervalCombo, new GBC(1, 2).setFill(GBC.HORIZONTAL).setWeight(100, 0));


// Themes
        JPanel themeButtonPanel = new JPanel();
        themeButtonPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Themes"));
//themeButtonPanel.setLayout(new GridLayout(1,4));
        UIManager.LookAndFeelInfo[] infos = UIManager.getInstalledLookAndFeels();
        for (UIManager.LookAndFeelInfo info : infos)
            makeButton(info.getName(), info.getClassName(), themeButtonPanel);
        add(themeButtonPanel, new GBC(0, 3).setFill(GBC.BOTH).setWeight(100, 100).setAnchor(GBC.EAST).setSpan(2, 1));
        add(OKButton, new GBC(0, 4).setFill(GBC.NONE).setWeight(0, 0).setAnchor(GBC.CENTER));
//Event handling
        OKButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                node.putInt("IntervalTime", ((Integer) messageIntervalCombo.getSelectedItem()).intValue());
                node.putInt("ShowTime", ((Integer) messageStayIntervalCombo.getSelectedItem()).intValue());
                node.put("Fashion", (String) fashionCombo.getSelectedItem());
                int selectedValue = JOptionPane.showConfirmDialog(null, "Application needs to Restarted for new changes to take effect.Restart?", "Restart", JOptionPane.OK_CANCEL_OPTION);
                if (selectedValue == JOptionPane.OK_OPTION) {
                    try {
                        //new File("restart").createNewFile();
                        System.exit(0);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    //Theme Button creater function
    void makeButton(String name, final String plafName, JPanel panel) {
        JButton button = new JButton(name);
        panel.add(button);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                try {
                    UIManager.setLookAndFeel(plafName);
                    SwingUtilities.updateComponentTreeUI(frame);
                    node.put("Theme", plafName);
//System.out.println(plafName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private JLabel displayFashionLabel;
    private JComboBox fashionCombo;
    private JLabel messageIntervalLabel;
    private JComboBox messageIntervalCombo;
    private JLabel messageStayIntervalLabel;
    private JComboBox messageStayIntervalCombo;
    private Preferences root;
    private final Preferences node;
    private JButton OKButton;
    private JFrame frame;
}