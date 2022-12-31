package com.learn.ytbplayer;

public class chapter {
    String indexNumber,title;

    public chapter() {
    }

    public chapter(String indexNumber, String title) {
        this.indexNumber = indexNumber;
        this.title = title;
    }

    public String getIndexNumber() {
        return indexNumber;
    }

    public void setIndexNumber(String indexNumber) {
        this.indexNumber = indexNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
