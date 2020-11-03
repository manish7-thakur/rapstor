package com.rapstor.gui;

import com.rapstor.io.DataIO;
import com.rapstor.io.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;


public class StoredMessagePanel extends JPanel {
    public StoredMessagePanel() {
        userDir = System.getProperty("user.home");
        try {
            msgDataFile = new RandomAccessFile(new File(userDir + "/.Rapstor/MesData.rap"), "rw");// use File.seperator
            pointerPosition = msgDataFile.getFilePointer();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        stMessage = new Message();
        shortDescLabel = new JLabel("Short Description");
        shortDescLabel.setBorder(BorderFactory.createEtchedBorder());
        shortDescField = new JTextField(SHORT_MESSAGE_LENGTH);
        shortDescField.setEditable(false);
        shortDescField.setBorder(BorderFactory.createLoweredBevelBorder());
        longDescLabel = new JLabel("Long Description");
        longDescLabel.setBorder(BorderFactory.createEtchedBorder());
        longDescArea = new JTextArea();
        longDescArea.setEditable(false);
        longDescArea.setBorder(BorderFactory.createLoweredBevelBorder());
        longDescArea.setLineWrap(true);
        nextButton = new JButton("Next");
        previousButton = new JButton("Previous");
        deleteButton = new JButton("Delete");
        setLayout(new GridBagLayout());
        add(shortDescLabel, new GBC(0, 0).setAnchor(GBC.EAST).setWeight(0, 0).setFill(GBC.HORIZONTAL).setInsets(1));
        add(shortDescField, new GBC(1, 0).setSpan(2, 1).setWeight(100, 0).setFill(GBC.HORIZONTAL).setInsets(1));
        add(longDescLabel, new GBC(0, 1).setSpan(3, 1).setWeight(100, 0).setAnchor(GBC.EAST).setFill(GBC.HORIZONTAL).setInsets(1));
        add(longDescArea, new GBC(0, 2).setSpan(3, 1).setWeight(100, 100).setAnchor(GBC.CENTER).setFill(GBC.BOTH).setInsets(1));
        add(previousButton, new GBC(0, 3).setSpan(1, 1).setWeight(100, 0).setFill(GBC.HORIZONTAL).setInsets(1));
        add(nextButton, new GBC(1, 3).setSpan(1, 1).setWeight(100, 0).setFill(GBC.HORIZONTAL).setInsets(1));
        add(deleteButton, new GBC(2, 3).setSpan(1, 1).setWeight(100, 0).setFill(GBC.HORIZONTAL).setInsets(1));

//Event Handling
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try {
                    if (msgDataFile.getFilePointer() == msgDataFile.length()) {
                        JOptionPane.showMessageDialog(null, "Reached to the End");
                        return;
                    }
//System.out.println(msgDataFile.getFilePointer());
//msgDataFile.seek(msgDataFile.getFilePointer() + RECORD_SIZE);
                    pointerPosition = msgDataFile.getFilePointer();
                    readData(msgDataFile);
                    displayData();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        previousButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try {
                    if (pointerPosition == 0) {
                        JOptionPane.showMessageDialog(null, "This is the first message in DataBase");
                        return;
                    }
                    pointerPosition -= RECORD_SIZE;
                    msgDataFile.seek(pointerPosition);
                    readData(msgDataFile);
                    displayData();
//msgDataFile.seek(msgDataFile.getFilePointer() - RECORD_SIZE);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

// Delete button Event handling

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
//JOptionPane.showMessageDialog(null,"This option is not available.Please wait for the next release");
                int selectedValue = JOptionPane.showConfirmDialog(null, "Are you sure you want delete the selected Message?", "Conform File Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (selectedValue == JOptionPane.YES_OPTION) {
                    try {
                        //msgDataFile.getChannel().lock();
                        msgDataFile.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    DataIO.deleteRecord(userDir + "/.Rapstor/MesData.rap", pointerPosition, RECORD_SIZE);
                    shortDescField.setText("");
                    longDescArea.setText("");
                    try {
                        msgDataFile = new RandomAccessFile(new File(userDir + "/.Rapstor/MesData.rap"), "rw");// use File.seperator
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }


    /**
     * Read the data from the input Stream
     *
     * @param in the input stream to read from
     */

    public void readData(RandomAccessFile in) throws IOException {
        String msg = DataIO.readFixedString(SHORT_MESSAGE_LENGTH, in);
        stMessage.setShortDescription(msg);
//System.out.println(stMessage.getShortDescription());
//in.seek(in.getFilePointer() + SHORT_MESSAGE_LENGTH * 2);
        msg = DataIO.readFixedString(LONG_MESSAGE_LENGTH, in);
        stMessage.setLongDescription(msg);
//System.out.println(stMessage.getLongDescription());
//in.seek(in.getFilePointer()-SHORT_MESSAGE_LENGTH);
    }


/**
 Move the Pointer to the specified Record
 */

/*
public void moveToRecord(int recordLength,RandomAccessFile in)
{
in.seek(in.getFilePointer() + recordLength);
}
*/

    /**
     * Displays the data in the required component
     *
     * @param cmp textComponent to display the data
     * @param msg the Message object
     */

    public void displayData() {
        shortDescField.setText(stMessage.getShortDescription());
        longDescArea.setText(stMessage.getLongDescription());
    }

    private JLabel shortDescLabel;
    private JTextField shortDescField;
    private JLabel longDescLabel;
    private JTextArea longDescArea;
    private JButton nextButton;
    private JButton previousButton;
    private JButton deleteButton;
    private String userDir;
    private RandomAccessFile msgDataFile = null;
    private Message stMessage = null;
    private static long pointerPosition;
    private static final int SHORT_MESSAGE_LENGTH = 30;
    private static final int LONG_MESSAGE_LENGTH = 250;
    private static final int RECORD_SIZE = (SHORT_MESSAGE_LENGTH * 2) + (LONG_MESSAGE_LENGTH * 2);

}