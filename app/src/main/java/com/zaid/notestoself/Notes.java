package com.zaid.notestoself;

/**
 * Created by zaid on 26/9/18.
 */

public class Notes {
    int id;
    String note;
    String title;


    public Notes(int id, String title, String note){
        this.id = id;
        this.title=title;
        this.note = note;
    }
    public Notes(String title){
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
