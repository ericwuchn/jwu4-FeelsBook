package com.cmput301.jiayuewu.jwu4_feelsbook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

// class purpose: manages the homepage activity of the app
// design rationale: simple and minimal, let users know what to do when they open the app first time
public class homepage extends AppCompatActivity {
    static final int ADD_ENTRY_REQUEST = 1;  // The request code for adding entry
    static final int UPDATE_ENTRY_REQUEST = 2;  // The request code for editing entry
    public FeelsBook feelslist;
    public ListView feels_listview;
    public TextView emotion_count_1;
    public TextView emotion_count_2;
    public ArrayList<FeelingEntry> templist;
    public Button add_button;
    public FloatingActionButton add_floating;

    public int love_count;
    public int joy_count;
    public int surprise_count;
    public int anger_count;
    public int sadness_count;
    public int fear_count;

    /*// examples
        HashMap<String, String> maplist;
        maplist = new HashMap<String, String>();
        maplist.put("line1", "\uD83D\uDE18 Love");
        maplist.put("line2", "asd asd ad 1234569780");
        listview.add(maplist);

        maplist = new HashMap<String, String>();
        maplist.put("line1", "\uD83D\uDE01 Joy");
        maplist.put("line2", "asd asd ad 1234569781");
        listview.add(maplist);
        // refresh listview
        String[] from = { "line1", "line2" };
        int[] to = { android.R.id.text1, android.R.id.text2 };
        SimpleAdapter adapterList = new SimpleAdapter(this, listview, android.R.layout.simple_list_item_2, from, to);
        final ListView listView = findViewById(R.id.feels_listview);
        listView.setAdapter(adapterList);
        */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        // this hidden button is the old one for backup use
        add_button = findViewById(R.id.add_button);
        add_button.setVisibility(View.INVISIBLE);

        // material design add icon from https://materialdesignicons.com/
        // this is the active add button
        add_floating = findViewById(R.id.add_floating);

        // initialize new FeelsBook
        feelslist = new FeelsBook();
        feels_listview = findViewById(R.id.feels_listview);

        // shared preferences usage learned from https://www.youtube.com/watch?v=jcliHGR3CHo
        // load data from shared preference
        SharedPreferences prefs = getSharedPreferences("data", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString("feelslist", null);
        Type type = new TypeToken<ArrayList<FeelingEntry>>(){}.getType();
        templist = gson.fromJson(json, type);

        if (templist==null){
            // do nothing
        } else {
            // load feels list from shared preference
            feelslist.importFeeling(templist);
            // initialize array lists
            ArrayList<HashMap<String, String>> listview = new ArrayList<>(2);
            ArrayList<FeelingEntry> currentfeelslist = feelslist.getFeels();
            // iterate through feels list for displaying
            for (int i=0; i<currentfeelslist.size(); i++) {
                FeelingEntry currententry = currentfeelslist.get(i);
                // two-line list item learned from https://stackoverflow.com/questions/9647711/using-simple-list-item-2-and-cant-figure-out-how-to-use-setonitemclicklistener
                HashMap<String, String> maplist;
                maplist = new HashMap<>();
                maplist.put("line1", currententry.GetName());
                maplist.put("line2", currententry.GetTimestamp());
                listview.add(maplist);
                // calculates emotion counts
                if (currententry.GetName().equals("\uD83D\uDE18 Love")) {
                    love_count += 1;
                }
                if (currententry.GetName().equals("\uD83D\uDE01 Joy")) {
                    joy_count += 1;
                }
                if (currententry.GetName().equals("\uD83D\uDE31 Surprise")) {
                    surprise_count += 1;
                }
                if (currententry.GetName().equals("\uD83D\uDE21 Anger")) {
                    anger_count += 1;
                }
                if (currententry.GetName().equals("\uD83D\uDE2D Sadness")) {
                    sadness_count += 1;
                }
                if (currententry.GetName().equals("\uD83D\uDE28 Fear")) {
                    fear_count += 1;
                }
            }
            // display feels list
            String[] from = { "line1", "line2" };
            int[] to = { android.R.id.text1, android.R.id.text2 };
            SimpleAdapter adapterList = new SimpleAdapter(homepage.this, listview, android.R.layout.simple_list_item_2, from, to);
            ListView feels_listview = findViewById(R.id.feels_listview);
            feels_listview.setAdapter(adapterList);
        }
        // display emotion counts
        // first row
        emotion_count_1 = findViewById(R.id.emotion_count_1);
        emotion_count_1.setText("\uD83D\uDE18 " + love_count + " \uD83D\uDE01 " + joy_count + " \uD83D\uDE31 "+ surprise_count);
        // second row
        emotion_count_2 = findViewById(R.id.emotion_count_2);
        emotion_count_2.setText("\uD83D\uDE21 " + anger_count + " \uD83D\uDE2D " + sadness_count + " \uD83D\uDE28 "+ fear_count);

        // backup add button
        add_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addentry = new Intent(homepage.this, com.cmput301.jiayuewu.jwu4_feelsbook.addentry.class);
                startActivityForResult(addentry, ADD_ENTRY_REQUEST);
            }
        });

        // active add button
        add_floating.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addentry = new Intent(homepage.this, com.cmput301.jiayuewu.jwu4_feelsbook.addentry.class);
                startActivityForResult(addentry, ADD_ENTRY_REQUEST);
            }
        });

        // list view item click learned from https://stackoverflow.com/questions/21295328/android-listview-with-onclick-items
        feels_listview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int current_position = position;
                ArrayList<FeelingEntry> currentfeelslist = feelslist.getFeels();
                FeelingEntry currententry = currentfeelslist.get(current_position);
                String currententry_name = currententry.GetName();
                String currententry_timestamp = currententry.GetTimestamp();
                String currententry_comment = currententry.GetComment();
                Calendar currentDate = currententry.GetDate();
                Intent editentry = new Intent(homepage.this, com.cmput301.jiayuewu.jwu4_feelsbook.editentry.class);
                editentry.putExtra("EDITEMOTIONNAME", currententry_name);
                editentry.putExtra("EDITTIMESTAMP", currententry_timestamp);
                editentry.putExtra("EDITCOMMENT", currententry_comment);
                editentry.putExtra("EDITPOSITION", current_position);
                editentry.putExtra("EDITDATE", currentDate);
                startActivityForResult(editentry, UPDATE_ENTRY_REQUEST);
            }
        });

    }
    // intent result usage learned from https://stackoverflow.com/questions/920306/sending-data-back-to-the-main-activity-in-android
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // if added emotion
        if (requestCode == ADD_ENTRY_REQUEST && resultCode == RESULT_OK && data != null) {
            String emotion_name = data.getStringExtra("EMOTIONTYPE");
            String timestamp = data.getStringExtra("TIMESTAMP");
            String comment_content = data.getStringExtra("COMMENTCONTENT");

            Calendar currentDate = (Calendar) data.getSerializableExtra("CURRENTDATE");

            love_count = 0;
            joy_count = 0;
            surprise_count = 0;
            anger_count = 0;
            sadness_count = 0;
            fear_count = 0;

            FeelingEntry newfeel = new FeelingEntry(emotion_name,timestamp,comment_content,currentDate);
            feelslist.addFeeling(newfeel);
            ArrayList<HashMap<String, String>> listview = new ArrayList<>(2);
            ArrayList<FeelingEntry> currentfeelslist = feelslist.getFeels();
            for (int i=0; i<currentfeelslist.size(); i++){
                FeelingEntry currententry = currentfeelslist.get(i);
                HashMap<String, String> maplist;
                maplist = new HashMap<>();
                maplist.put("line1", currententry.GetName());
                maplist.put("line2", currententry.GetTimestamp());
                listview.add(maplist);
                if (currententry.GetName().equals("\uD83D\uDE18 Love")){
                    love_count += 1;
                }
                if (currententry.GetName().equals("\uD83D\uDE01 Joy")){
                    joy_count += 1;
                }
                if (currententry.GetName().equals("\uD83D\uDE31 Surprise")){
                    surprise_count += 1;
                }
                if (currententry.GetName().equals("\uD83D\uDE21 Anger")){
                    anger_count += 1;
                }
                if (currententry.GetName().equals("\uD83D\uDE2D Sadness")){
                    sadness_count += 1;
                }
                if (currententry.GetName().equals("\uD83D\uDE28 Fear")){
                    fear_count += 1;
                }
                emotion_count_1 = findViewById(R.id.emotion_count_1);
                emotion_count_1.setText("\uD83D\uDE18 " + love_count + " \uD83D\uDE01 " + joy_count + " \uD83D\uDE31 "+ surprise_count);
                emotion_count_2 = findViewById(R.id.emotion_count_2);
                emotion_count_2.setText("\uD83D\uDE21 " + anger_count + " \uD83D\uDE2D " + sadness_count + " \uD83D\uDE28 "+ fear_count);

            }
            String[] from = { "line1", "line2" };
            int[] to = { android.R.id.text1, android.R.id.text2 };
            SimpleAdapter adapterList = new SimpleAdapter(homepage.this, listview, android.R.layout.simple_list_item_2, from, to);
            feels_listview = findViewById(R.id.feels_listview);
            feels_listview.setAdapter(adapterList);
            feels_listview.setSelection(adapterList.getCount()-1);

            // shared preferences usage learned from https://www.youtube.com/watch?v=jcliHGR3CHo
            // save data to shared preference
            SharedPreferences prefs = getSharedPreferences("data", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            Gson gson = new Gson();
            String json = gson.toJson(currentfeelslist);
            editor.putString("feelslist", json);
            editor.apply();
        }
        //if edited emotion
        if (requestCode == UPDATE_ENTRY_REQUEST && resultCode == RESULT_OK && data != null) {
            ArrayList<HashMap<String, String>> listview = new ArrayList<>(2);
            String deleted = data.getStringExtra("DELETED");
            String edit_emotion_name = data.getStringExtra("EDITEMOTIONNAME");
            String edit_comment = data.getStringExtra("EDITCOMMENT");
            String edit_timestamp = data.getStringExtra("EDITTIMESTAMP");
            Calendar currentDate = (Calendar)data.getSerializableExtra("EDITDATE");
            int edit_position = data.getIntExtra("POSITION",0);

            love_count = 0;
            joy_count = 0;
            surprise_count = 0;
            anger_count = 0;
            sadness_count = 0;
            fear_count = 0;

            if (deleted.equals("True")){

                feelslist.removeFeeling(edit_position);
                ArrayList<FeelingEntry> currentfeelslist = feelslist.getFeels();
                for (int i=0; i<currentfeelslist.size(); i++){
                    FeelingEntry currententry = currentfeelslist.get(i);
                    HashMap<String, String> maplist;
                    maplist = new HashMap<>();
                    maplist.put("line1", currententry.GetName());
                    maplist.put("line2", currententry.GetTimestamp());
                    listview.add(maplist);
                    if (currententry.GetName().equals("\uD83D\uDE18 Love")){
                        love_count += 1;
                    }
                    if (currententry.GetName().equals("\uD83D\uDE01 Joy")){
                        joy_count += 1;
                    }
                    if (currententry.GetName().equals("\uD83D\uDE31 Surprise")){
                        surprise_count += 1;
                    }
                    if (currententry.GetName().equals("\uD83D\uDE21 Anger")){
                        anger_count += 1;
                    }
                    if (currententry.GetName().equals("\uD83D\uDE2D Sadness")){
                        sadness_count += 1;
                    }
                    if (currententry.GetName().equals("\uD83D\uDE28 Fear")){
                        fear_count += 1;
                    }
                }
                emotion_count_1 = findViewById(R.id.emotion_count_1);
                emotion_count_1.setText("\uD83D\uDE18 " + love_count + " \uD83D\uDE01 " + joy_count + " \uD83D\uDE31 "+ surprise_count);
                emotion_count_2 = findViewById(R.id.emotion_count_2);
                emotion_count_2.setText("\uD83D\uDE21 " + anger_count + " \uD83D\uDE2D " + sadness_count + " \uD83D\uDE28 "+ fear_count);

                String[] from = { "line1", "line2" };
                int[] to = { android.R.id.text1, android.R.id.text2 };
                SimpleAdapter adapterList = new SimpleAdapter(homepage.this, listview, android.R.layout.simple_list_item_2, from, to);
                feels_listview = findViewById(R.id.feels_listview);
                feels_listview.setAdapter(adapterList);
                adapterList.notifyDataSetChanged();

                // shared preferences usage learned from https://www.youtube.com/watch?v=jcliHGR3CHo
                // save data to shared preference
                SharedPreferences prefs = getSharedPreferences("data", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                Gson gson = new Gson();
                String json = gson.toJson(currentfeelslist);
                editor.putString("feelslist", json);
                editor.apply();
            }
            if (deleted.equals("False")){
                ArrayList<FeelingEntry> currentfeelslist = feelslist.getFeels();
                FeelingEntry currententry = currentfeelslist.get(edit_position);
                currententry.SetName(edit_emotion_name);
                currententry.SetComment(edit_comment);
                currententry.SetTimestamp(edit_timestamp);
                currententry.SetDate(currentDate);

                // sort array list by date learned from https://stackoverflow.com/questions/44563175/sort-arraylist-of-calendars
                Collections.sort(currentfeelslist, new Comparator<FeelingEntry>() {
                    public int compare(FeelingEntry item1, FeelingEntry item2) {
                        Calendar item1date = item1.GetDate();
                        Calendar item2date = item2.GetDate();
                        return item1date.compareTo(item2date);
                    }
                });

                // refresh list view
                for (int i=0; i<currentfeelslist.size(); i++){
                    currententry = currentfeelslist.get(i);
                    HashMap<String, String> maplist;
                    maplist = new HashMap<>();
                    maplist.put("line1", currententry.GetName());
                    maplist.put("line2", currententry.GetTimestamp());
                    listview.add(maplist);
                    if (currententry.GetName().equals("\uD83D\uDE18 Love")){
                        love_count += 1;
                    }
                    if (currententry.GetName().equals("\uD83D\uDE01 Joy")){
                        joy_count += 1;
                    }
                    if (currententry.GetName().equals("\uD83D\uDE31 Surprise")){
                        surprise_count += 1;
                    }
                    if (currententry.GetName().equals("\uD83D\uDE21 Anger")){
                        anger_count += 1;
                    }
                    if (currententry.GetName().equals("\uD83D\uDE2D Sadness")){
                        sadness_count += 1;
                    }
                    if (currententry.GetName().equals("\uD83D\uDE28 Fear")){
                        fear_count += 1;
                    }

                }

                emotion_count_1 = findViewById(R.id.emotion_count_1);
                emotion_count_1.setText("\uD83D\uDE18 " + love_count + " \uD83D\uDE01 " + joy_count + " \uD83D\uDE31 "+ surprise_count);
                emotion_count_2 = findViewById(R.id.emotion_count_2);
                emotion_count_2.setText("\uD83D\uDE21 " + anger_count + " \uD83D\uDE2D " + sadness_count + " \uD83D\uDE28 "+ fear_count);

                String[] from = { "line1", "line2" };
                int[] to = { android.R.id.text1, android.R.id.text2 };
                SimpleAdapter adapterList = new SimpleAdapter(homepage.this, listview, android.R.layout.simple_list_item_2, from, to);
                ListView feels_listview = findViewById(R.id.feels_listview);
                feels_listview.setAdapter(adapterList);
                feels_listview.setSelection(edit_position);

                // shared preferences usage learned from https://www.youtube.com/watch?v=jcliHGR3CHo
                // save data to shared preference
                SharedPreferences prefs = getSharedPreferences("data", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                Gson gson = new Gson();
                String json = gson.toJson(currentfeelslist);
                editor.putString("feelslist", json);
                editor.apply();
            }
        }

    }
}
