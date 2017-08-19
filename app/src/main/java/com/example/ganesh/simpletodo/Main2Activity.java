package com.example.ganesh.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        String username = getIntent().getStringExtra("username");
        String inReplyTo = getIntent().getStringExtra("in_reply_to");
        String code = getIntent().getStringExtra("code");
        EditText etName = (EditText) findViewById(R.id.list_todo1);
        System.out.println("code " + code);
        etName.setText(code);

    }
    public void onSubmit(View v) {
        //EditText etName = (EditText) findViewById(R.id.list_todo1);
        // Prepare data intent
        //Intent data = new Intent();
        // Pass relevant data back as a result
        //data.putExtra("name", etName.getText().toString());
        //data.putExtra("code", 200); // ints work too
        // Activity finished ok, return the data
        //setResult(RESULT_OK, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent
    }

    public void editTask(View view) {
       System.out.println("editTask from 2nd screen");
        EditText etName = (EditText) findViewById(R.id.list_todo1);
        // Prepare data intent
        Intent data = new Intent();
        // Pass relevant data back as a result
        data.putExtra("name", etName.getText().toString());
        //data.putExtra("code", 200); // ints work too
        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent

    }
}

