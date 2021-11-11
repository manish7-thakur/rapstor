package com.rapstor.io;

import java.io.*;

public class DataIO {
    public static String readFixedString(int size, DataInput in) throws IOException {
        StringBuilder messageStringBuilder = new StringBuilder(size);
        int i = 0;
        boolean more = true;
        while (more && i < size) {
            char msgChar = in.readChar();
            i++;
            if (msgChar == 0) more = false;
            else messageStringBuilder.append(msgChar);
        }
        in.skipBytes(2 * (size - i));
        return messageStringBuilder.toString();
    }

    public static void writeFixedString(String s, int size, DataOutput out) throws IOException {
        for (int i = 0; i < size; i++) {
            char ch = 0;
            if (i < s.length()) ch = s.charAt(i);
            out.writeChar(ch);
        }
    }

    public static void deleteRecord(File readFile, long pointerPosition, int recordSize) {
        RandomAccessFile readRandFile = null;
        RandomAccessFile writeRandFile = null;
        try {
            readRandFile = new RandomAccessFile(readFile, "rw");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        File tempFile = new File(readFile.getAbsolutePath() + ".txt");
        try {
            writeRandFile = new RandomAccessFile(tempFile, "rw");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            while (readRandFile.getFilePointer() != readRandFile.length()) {
                if (readRandFile.getFilePointer() == pointerPosition) {
                    readRandFile.skipBytes(recordSize);
                }
                if (readRandFile.getFilePointer() != readRandFile.length()) {
                    writeRandFile.writeChar(readRandFile.readChar());
                }
            }
            readRandFile.close();
            writeRandFile.close();

            if (!readFile.delete()) {
                System.out.println("Could not delete File");
            } /*readFile.deleteOnExit();*/
            if (!tempFile.renameTo(readFile)) {
                System.out.println("Could not rename File");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
        }
    }
}
