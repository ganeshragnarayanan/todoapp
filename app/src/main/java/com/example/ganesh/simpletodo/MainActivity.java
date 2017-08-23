package com.example.ganesh.simpletodo;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.ganesh.simpletodo.db.CustomAdapter;
import com.example.ganesh.simpletodo.db.DataModel;
import com.example.ganesh.simpletodo.db.TaskContract;
import com.example.ganesh.simpletodo.db.TaskDbHelper;

import java.util.ArrayList;

import static com.example.ganesh.simpletodo.R.id.list_todo;

public class MainActivity extends AppCompatActivity {

    ArrayList<DataModel> dataModels;
    private static final String TAG = "MainActivity";
    private final int REQUEST_CODE = 20;

    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;
    private static CustomAdapter adapter;


    private TaskDbHelper mHelper;
    private ListView mTaskListView;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHelper = new TaskDbHelper(this);
        mTaskListView = (ListView) findViewById(list_todo);


        ListView lv = (ListView)findViewById(R.id.list_todo);
        dataModels= new ArrayList<>();

        dataModels.add(new DataModel("Task Name", "Task Notes", "2008/1/1", "high", "done"));
        adapter= new CustomAdapter(dataModels,MainActivity.this);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent i = new Intent(MainActivity.this, Main2Activity.class);

                i.putExtra("username", "foobar");
                i.putExtra("in_reply_to", "george");
                i.putExtra("code", 400);

                startActivity(i);
            }
        });

        updateUI();
    }

    public void updateUI() {
        ArrayList<String> taskName = new ArrayList<>();
        ArrayList<String> taskNotes = new ArrayList<>();
        ArrayList<String> taskDate = new ArrayList<>();
        ArrayList<String> taskPriority = new ArrayList<>();
        ArrayList<String> taskStatus = new ArrayList<>();


        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE,
                new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COL_TASK_NAME,
                        TaskContract.TaskEntry.COL_TASK_NOTES, TaskContract.TaskEntry.COL_DATE,
                        TaskContract.TaskEntry.COL_PRIORITY_LEVEL, TaskContract.TaskEntry.COL_STATUS},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            int idx_task_name = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_NAME);
            int idx_task_notes = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_NOTES);
            int idx_date = cursor.getColumnIndex(TaskContract.TaskEntry.COL_DATE);
            int idx_priority = cursor.getColumnIndex(TaskContract.TaskEntry.COL_PRIORITY_LEVEL);
            int idx_status = cursor.getColumnIndex(TaskContract.TaskEntry.COL_STATUS);

            taskName.add(cursor.getString(idx_task_name));
            taskNotes.add(cursor.getString(idx_task_notes));
            taskDate.add(cursor.getString(idx_date));
            taskPriority.add(cursor.getString(idx_priority));
            taskStatus.add(cursor.getString(idx_status));
        }

        if (mAdapter == null) {
            dataModels= new ArrayList<>();

            for (int i=0;i<taskName.size();i++) {
                dataModels.add(new DataModel(taskName.get(i), taskPriority.get(i), taskNotes.get(i), taskDate.get(i),
                        taskStatus.get(i)));
            }

            adapter= new CustomAdapter(dataModels,MainActivity.this);
            mTaskListView.setAdapter(adapter);

        } else {
            mAdapter.clear();
            mAdapter.addAll(taskName);
            dataModels= new ArrayList<>();

            mAdapter.notifyDataSetChanged();
        }

        cursor.close();
        db.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = new Intent(MainActivity.this, Main2Activity.class);
        startActivityForResult(i, REQUEST_CODE);
        return true;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_CANCELED ) {
            updateUI();
        }

        if (resultCode == RESULT_OK ) {
            String title = data.getExtras().getString("title");
            String notes = data.getExtras().getString("notes");
            String priority = data.getExtras().getString("priority");
            String status = data.getExtras().getString("status");
            String date = data.getExtras().getString("date");

            SQLiteDatabase db1 = mHelper.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(TaskContract.TaskEntry.COL_TASK_NAME, title);
            values.put(TaskContract.TaskEntry.COL_TASK_NOTES, notes);
            values.put(TaskContract.TaskEntry.COL_DATE, date);
            values.put(TaskContract.TaskEntry.COL_PRIORITY_LEVEL, priority);
            values.put(TaskContract.TaskEntry.COL_STATUS, status);

            db1.replace(TaskContract.TaskEntry.TABLE,
                    null,
                    values);

            db1.close();
            updateUI();
        }
    }
}
