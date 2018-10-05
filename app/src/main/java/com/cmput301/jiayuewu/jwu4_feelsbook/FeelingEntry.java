package com.cmput301.jiayuewu.jwu4_feelsbook;

import java.util.Calendar;

// class purpose: manages a single feeling entry represents an emotion
// design rationale: simple and minimal, only contains necessary data and methods for an emotion entry
public class FeelingEntry {
    public String FeelingName;
    public String Timestamp;
    public String Comment;
    public Calendar Date;

    public FeelingEntry(String FeelingName, String Timestamp, String Comment, Calendar FeelingDate){
        this.FeelingName = FeelingName;
        this.Timestamp = Timestamp;
        this.Comment = Comment;
        this.Date = FeelingDate;

    }
    public void SetName(String NewName){
        this.FeelingName = NewName;
    }
    public void SetComment(String NewComment){
        this.Comment = NewComment;
    }
    public void SetTimestamp(String NewTimestamp){
        this.Timestamp = NewTimestamp;
    }
    public void SetDate(Calendar NewDate){
        this.Date = NewDate;
    }

    public String GetName(){
        return this.FeelingName;
    }

    public String GetTimestamp(){
        return this.Timestamp;
    }

    public String GetComment(){
        return this.Comment;
    }

    public Calendar GetDate(){
        return this.Date;
    }
}
