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


public class addentry extends AppCompatActivity {
    public Calendar currentDate;
    public String currentDateandTime;
    public EditText comment_field;
    public Spinner emotion_select;
    public TextView timestamp_text;
    public Button add_entry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addentry);
        // spinner usage learned from https://stackoverflow.com/questions/24825249/how-to-add-item-in-spinner-android
        emotion_select = findViewById(R.id.emotion_select);
        ArrayAdapter<String> adapter;
        List<String> list;
        currentDate = Calendar.getInstance();
        currentDateandTime = new SimpleDateFormat("yyyy-MM-dd E HH:mm:ss").format(currentDate.getTime());
        timestamp_text = findViewById(R.id.timestamp_text);
        timestamp_text.setText(currentDateandTime);
        add_entry = findViewById(R.id.add_entry);
        comment_field = findViewById(R.id.comment_field);
        int max_length = 100;
        // edit text character limit learned from https://stackoverflow.com/questions/3285412/limit-text-length-of-edittext-in-android
        comment_field.setFilters(new InputFilter[] {new InputFilter.LengthFilter(max_length)});

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

        timestamp_text.setOnClickListener(new View.OnClickListener() {
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
                        timestamp_text = findViewById(R.id.timestamp_text);
                        timestamp_text.setText(currentDateandTime);
                    }
                };
                new TimePickerDialog(addentry.this,R.style.AppTheme, timepicker, currentDate.get(Calendar.HOUR), currentDate.get(Calendar.MINUTE), true).show();
                new DatePickerDialog(addentry.this, datepicker, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        add_entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int emotion_spinner_id = emotion_select.getSelectedItemPosition();

                comment_field = findViewById(R.id.comment_field);
                String comment_content = comment_field.getText().toString();
                Intent add_intent = new Intent(addentry.this,homepage.class);
                String emotion_type = "\uD83D\uDE18 Love";
                // check emotion selected
                if (emotion_spinner_id == 0) {
                    emotion_type = "\uD83D\uDE18 Love";
                }
                if (emotion_spinner_id == 1) {
                    emotion_type = "\uD83D\uDE01 Joy";
                }
                if (emotion_spinner_id == 2) {
                    emotion_type = "\uD83D\uDE31 Surprise";
                }
                if (emotion_spinner_id == 3) {
                    emotion_type = "\uD83D\uDE21 Anger";
                }
                if (emotion_spinner_id == 4) {
                    emotion_type = "\uD83D\uDE2D Sadness";
                }
                if (emotion_spinner_id == 5) {
                    emotion_type = "\uD83D\uDE28 Fear";
                }
                add_intent.putExtra("EMOTIONTYPE", emotion_type);
                add_intent.putExtra("TIMESTAMP", currentDateandTime);
                add_intent.putExtra("COMMENTCONTENT", comment_content);
                add_intent.putExtra("CURRENTDATE", currentDate);
                setResult(RESULT_OK, add_intent);
                finish();
            }
        });
    }
}
