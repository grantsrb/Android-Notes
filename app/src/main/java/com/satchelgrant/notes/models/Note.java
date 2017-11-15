package com.satchelgrant.notes.models;

/**
 * Created by satchelgrant on 5/30/17.
 */

public class Note {
    static int current_id;
    String title;
    String content;
    long id;

    public Note(){

    }

    public Note(long id, String title) {
        this.title = title;
        this.id = id;
    }

    public Note(long id, String title, String content) {
        this.title = title;
        this.id = id;
        this.content = content;
    }

    public Note(String title, String content){
        this.title = title;
        this.content = content;
        this.id = -1;
    }

    public Note(String title){
        this.title = title;
        this.id = -1;
    }

    public String getTitle(){
        return this.title;
    }

    public String getContent(){
        return this.content;
    }

    public long getId(){
        return this.id;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setContent(String content){
        this.content = content;
    }

    public void setId(long id){
        this.id = id;
    }
}
