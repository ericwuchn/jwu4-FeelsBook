package com.cmput301.jiayuewu.jwu4_feelsbook;

import java.util.ArrayList;
import java.util.Collection;

// class purpose: the collection of a list of feeling entries
// design rationale: simple and minimal, only contains necessary data and methods for a feel's book
public class FeelsBook {
    protected ArrayList<FeelingEntry> FeelsList = null;

    public FeelsBook() {
        FeelsList = new ArrayList<FeelingEntry>();
    }

    public void addFeeling(FeelingEntry newentry){
        FeelsList.add(newentry);
    }
    public void removeFeeling(int position){
        FeelsList.remove(position);
    }
    public void importFeeling(ArrayList<FeelingEntry> newlist){
        FeelsList = newlist;
    }
    public ArrayList<FeelingEntry> getFeels() {
        return FeelsList;
    }

}
