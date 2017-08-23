package com.example.ganesh.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    Spinner dropdownPriority;
    Spinner dropdownStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        dropdownPriority = (Spinner)findViewById(R.id.task_priority1);
        String[] priorityItems = new String[]{"low", "medium", "high"};
        ArrayAdapter<String> adapterPriority = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, priorityItems);
        dropdownPriority.setAdapter(adapterPriority);

        dropdownStatus = (Spinner)findViewById(R.id.task_status1);
        String[] statusItems = new String[]{"done", "todo"};
        ArrayAdapter<String> adapterStatusItems =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, statusItems);
        dropdownStatus.setAdapter(adapterStatusItems);
    }
    public void onSubmit(View v) {
        finish();
    }

    public void addTask(View view) {
        EditText taskTitle = (EditText) findViewById(R.id.task_title1);

        TextView textViewPriority = (TextView)dropdownPriority.getSelectedView();
        TextView textViewStatus = (TextView)dropdownStatus.getSelectedView();
        String taskPriority = textViewPriority.getText().toString();
        String taskStatus = textViewStatus.getText().toString();

        EditText taskNotes = (EditText) findViewById(R.id.task_notes1);
        EditText taskDate = (EditText) findViewById(R.id.editText);

        Intent data = new Intent();

        data.putExtra("title", taskTitle.getText().toString());
        data.putExtra("priority", taskPriority);
        data.putExtra("status", taskStatus);
        data.putExtra("notes", taskNotes.getText().toString());
        data.putExtra("date", taskDate.getText().toString());
        setResult(RESULT_OK, data);
        finish();

    }

    public void cancelTask(View view) {
        Intent data = new Intent();
        setResult(RESULT_CANCELED, data);
        finish();

    }
}

