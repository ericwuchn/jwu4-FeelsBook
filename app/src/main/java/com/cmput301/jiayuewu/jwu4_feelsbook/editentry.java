package com.cmput301.jiayuewu.jwu4_feelsbook;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

// class purpose: manage the edit entry activity of the app
// design rationale: simple and minimal, make the ui easy to understand
public class editentry extends AppCompatActivity  {
    public String edit_emotion_name;
    public String edit_timestamp;
    public String edit_comment;
    public String currentDateandTime;
    public Calendar currentDate;
    public EditText edit_comment_field;
    public Spinner emotion_select;
    public Button update_entry;
    public Button delete_entry;
    public TextView timestamp_edit;
    public int edit_position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editentry);

        // spinner usage learned from https://stackoverflow.com/questions/24825249/how-to-add-item-in-spinner-android
        emotion_select = findViewById(R.id.emotion_select_edit);
        ArrayAdapter<String> adapter;
        List<String> list;

        update_entry = findViewById(R.id.update_entry);
        delete_entry = findViewById(R.id.delete_entry);

        edit_comment_field = findViewById(R.id.comment_edit);
        int max_length = 100;
        // edit text character limit learned from https://stackoverflow.com/questions/3285412/limit-text-length-of-edittext-in-android
        edit_comment_field.setFilters(new InputFilter[] {new InputFilter.LengthFilter(max_length)});

        list = new ArrayList<String>();
        // emoticon unicode from https://apps.timwhitlock.info/emoji/tables/unicode
        list.add("\uD83D\uDE18 Love");
        list.add("\uD83D\uDE01 Joy");
        list.add("\uD83D\uDE31 Surprise");
        list.add("\uD83D\uDE21 Anger");
        list.add("\uD83D\uDE2D Sadness");
        list.add("\uD83D\uDE28 Fear");
        adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        emotion_select.setAdapter(adapter);

        Intent edit_intent = getIntent();
        edit_emotion_name = edit_intent.getStringExtra("EDITEMOTIONNAME");
        edit_timestamp = edit_intent.getStringExtra("EDITTIMESTAMP");
        edit_comment = edit_intent.getStringExtra("EDITCOMMENT");
        currentDate = (Calendar)edit_intent.getSerializableExtra("EDITDATE");


        currentDateandTime = new SimpleDateFormat("yyyy-MM-dd E HH:mm:ss").format(currentDate.getTime());
        timestamp_edit = findViewById(R.id.timestamp_edit);
        timestamp_edit.setText(currentDateandTime);

        edit_position = edit_intent.getIntExtra("EDITPOSITION", 0);
        //timestamp_edit.setText(edit_timestamp);
        edit_comment_field = findViewById(R.id.comment_edit);
        edit_comment_field.setText(edit_comment);

        if (edit_emotion_name.equals("\uD83D\uDE18 Love")){
            emotion_select.setSelection(0);
            //emotion_select.setEnabled(false);
        }
        if (edit_emotion_name.equals("\uD83D\uDE01 Joy")){
            emotion_select.setSelection(1);
            //emotion_select.setEnabled(false);
        }
        if (edit_emotion_name.equals("\uD83D\uDE31 Surprise")){
            emotion_select.setSelection(2);
            //emotion_select.setEnabled(false);
        }
        if (edit_emotion_name.equals("\uD83D\uDE21 Anger")){
            emotion_select.setSelection(3);
            //emotion_select.setEnabled(false);
        }
        if (edit_emotion_name.equals("\uD83D\uDE2D Sadness")){
            emotion_select.setSelection(4);
            //emotion_select.setEnabled(false);
        }
        if (edit_emotion_name.equals("\uD83D\uDE28 Fear")){
            emotion_select.setSelection(5);
            //emotion_select.setEnabled(false);
        }

        timestamp_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // pickers usage learned from https://stackoverflow.com/questions/14933330/datepicker-how-to-popup-datepicker-when-click-on-edittext
                DatePickerDialog.OnDateSetListener datepicker = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        currentDate.set(year, month, dayOfMonth);
                    }
                };

                TimePickerDialog.OnTimeSetListener timepicker = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        currentDate.set(currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DAY_OF_MONTH),hourOfDay, minute);
                        currentDateandTime = new SimpleDateFormat("yyyy-MM-dd E HH:mm:ss").format(currentDate.getTime());
                        timestamp_edit = findViewById(R.id.timestamp_edit);
                        timestamp_edit.setText(currentDateandTime);
                    }
                };
                new TimePickerDialog(editentry.this,R.style.AppTheme, timepicker, currentDate.get(Calendar.HOUR), currentDate.get(Calendar.MINUTE), true).show();
                new DatePickerDialog(editentry.this, datepicker, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        // if update button is clicked
        update_entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int emotion_spinner_id = emotion_select.getSelectedItemPosition();
                String edit_emotion_name = "\uD83D\uDE18 Love";
                if (emotion_spinner_id == 0) {
                    edit_emotion_name = "\uD83D\uDE18 Love";
                }
                if (emotion_spinner_id == 1) {
                    edit_emotion_name = "\uD83D\uDE01 Joy";
                }
                if (emotion_spinner_id == 2) {
                    edit_emotion_name = "\uD83D\uDE31 Surprise";
                }
                if (emotion_spinner_id == 3) {
                    edit_emotion_name = "\uD83D\uDE21 Anger";
                }
                if (emotion_spinner_id == 4) {
                    edit_emotion_name = "\uD83D\uDE2D Sadness";
                }
                if (emotion_spinner_id == 5) {
                    edit_emotion_name = "\uD83D\uDE28 Fear";
                }
                Intent update_intent = new Intent(editentry.this, homepage.class);
                String deleted = "False";
                update_intent.putExtra("DELETED", deleted);
                update_intent.putExtra("POSITION", edit_position);
                update_intent.putExtra("EDITCOMMENT", edit_comment_field.getText().toString());
                update_intent.putExtra("EDITEMOTIONNAME", edit_emotion_name);
                update_intent.putExtra("EDITTIMESTAMP", currentDateandTime);
                update_intent.putExtra("EDITDATE", currentDate);
                setResult(RESULT_OK, update_intent);
                finish();
            }
        });
        // if delete button is clicked
        delete_entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent delete_intent = new Intent(editentry.this, homepage.class);
                String deleted = "True";
                delete_intent.putExtra("DELETED", deleted);
                delete_intent.putExtra("POSITION", edit_position);
                setResult(RESULT_OK, delete_intent);
                finish();
            }
        });

    }
}
