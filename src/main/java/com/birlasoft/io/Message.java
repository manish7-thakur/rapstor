package com.birlasoft.io;

public class Message {
    public Message(String sDesc, String lDesc) {
        this.shortDescription = sDesc;
        this.longDescription = lDesc;
    }

    public Message(String lDesc) {
        this.shortDescription = "";
        this.longDescription = lDesc;
    }

    public Message() {
        shortDescription = null;
        longDescription = null;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setShortDescription(String sDesc) {
        shortDescription = sDesc;
    }

    public void setLongDescription(String lDesc) {
        longDescription = lDesc;
    }

    private String shortDescription = null;
    private String longDescription = null;
}
