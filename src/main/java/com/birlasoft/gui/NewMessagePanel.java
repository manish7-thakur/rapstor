package com.birlasoft.gui;

import com.birlasoft.io.DataIO;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class NewMessagePanel extends JPanel {
    public NewMessagePanel() {
        setLayout(new GridBagLayout());
        shortDescLabel = new JLabel("Short Description (30 Characters)");
        shortDescLabel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        shortDescField = new JTextField(SHORT_MESSAGE_LENGTH);
        shortDescField.setBorder(BorderFactory.createLoweredBevelBorder());
        longDescLabel = new JLabel("Long description not more than 250 characters");
        longDescLabel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        longDescArea = new JTextArea();
        longDescArea.setBorder(BorderFactory.createLoweredBevelBorder());
        longDescArea.setLineWrap(true);
        messTypeLabel = new JLabel("Select Message type");
        //messTypeLabel.setBorder(BorderFactory.createEtchedBorder());
        messTypeBox = new JComboBox(new String[]{"Select", "Fact", "Vocab", "Task"});
        //messTypeBox.setBorder(BorderFactory.createLoweredBevelBorder());
        addButton = new JButton("Add");
        cancelButton = new JButton("Cancel");
        add(shortDescLabel, new GBC(0, 0).setFill(GBC.HORIZONTAL).setAnchor(GBC.EAST).setInsets(1));
        add(shortDescField, new GBC(1, 0).setFill(GBC.HORIZONTAL).setInsets(1).setWeight(100, 0).setSpan(3, 1));
        add(longDescLabel, new GBC(0, 1).setFill(GBC.HORIZONTAL).setWeight(100, 0).setSpan(4, 1).setInsets(1));
        add(longDescArea, new GBC(0, 2).setWeight(100, 100).setSpan(4, 1).setFill(GBC.BOTH).setAnchor(GBC.CENTER).setInsets(1));
        add(messTypeLabel, new GBC(0, 3).setFill(GBC.NONE).setInsets(2, 1, 2, 1).setWeight(0, 0));
        add(messTypeBox, new GBC(1, 3).setFill(GBC.HORIZONTAL).setInsets(1).setWeight(100, 0));
        add(addButton, new GBC(2, 3).setFill(GBC.NONE).setInsets(1).setWeight(0, 0));
        add(cancelButton, new GBC(3, 3).setFill(GBC.NONE).setInsets(1).setWeight(0, 0));

// Event Handling
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                shortDescField.setText("");
                longDescArea.setText("");
                messTypeBox.setSelectedItem("Select");
            }
        });
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (shortDescField.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Short Description should not be empty.");
                    return;
                }
                if (longDescArea.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Long Description should not be empty.");
                    return;
                }
                if ((String) messTypeBox.getSelectedItem() == "Select") {
                    JOptionPane.showMessageDialog(null, "Please select the message type.");
                    return;
                }
                String userDir = System.getProperty("user.home");
//File fileDir = new File(userDir, ".Rapstor");
//if(!fileDir.exists()) fileDir.mkdir();
                RandomAccessFile msgDataFile = null;
                try {
                    System.out.println("Hello");
                    msgDataFile = new RandomAccessFile(new File(userDir + "/.Rapstor", "MesData.rap"), "rw");// use File.seperator
                    System.out.println("Hello2");
                    msgDataFile.seek(msgDataFile.length());
                    writeData(msgDataFile);

                    JOptionPane.showMessageDialog(null, "Data in the Record Set Added");
                    shortDescField.setText("");
                    longDescArea.setText("");
                    messTypeBox.setSelectedItem("Select");
                    msgDataFile.close();
                } catch (FileNotFoundException ex) {
                    ex.getMessage();
                } catch (IOException ex) {
                    ex.getMessage();
                }
            }
        });
    }

    /**
     * Write the data to the Output Stream
     *
     * @param out the Output Stream to write the data out to
     */
    public void writeData(DataOutput out) throws IOException {
        String shortMsg = shortDescField.getText();
        String longMsg = longDescArea.getText();
        DataIO.writeFixedString(shortMsg, SHORT_MESSAGE_LENGTH, out);
        DataIO.writeFixedString(longMsg, Long_MESSAGE_LENGTH, out);
    }

    private JLabel shortDescLabel;
    private JTextField shortDescField;
    private JLabel longDescLabel;
    private JTextArea longDescArea;
    private JComboBox messTypeBox;
    private JLabel messTypeLabel;
    private JButton addButton;
    private JButton cancelButton;

    private static final int SHORT_MESSAGE_LENGTH = 30;
    private static final int Long_MESSAGE_LENGTH = 250;
}
