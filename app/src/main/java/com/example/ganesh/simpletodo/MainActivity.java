package com.example.ganesh.simpletodo;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ganesh.simpletodo.db.TaskContract;
import com.example.ganesh.simpletodo.db.TaskDbHelper;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.example.ganesh.simpletodo.R.id.list_todo;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private final int REQUEST_CODE = 20;

    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;

    private TaskDbHelper mHelper;
    private ListView mTaskListView;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHelper = new TaskDbHelper(this);
        mTaskListView = (ListView) findViewById(list_todo);


        ListView lv = (ListView)findViewById(R.id.list_todo);
        System.out.println("onItemClick here before");
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                System.out.println("onItemClick here 1");
                // first parameter is the context, second is the class of the activity to launch
                Intent i = new Intent(MainActivity.this, Main2Activity.class);
                // put "extras" into the bundle for access in the second activity
                i.putExtra("username", "foobar");
                i.putExtra("in_reply_to", "george");
                i.putExtra("code", 400);
                // brings up the second activity
                startActivity(i);
            }
        });

        updateUI();
    }

    private void updateUI() {
        System.out.println("updateUI.....");
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE,
                new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COL_TASK_TITLE},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE);
            taskList.add(cursor.getString(idx));
        }

        if (mAdapter == null) {
            mAdapter = new ArrayAdapter<>(this,
                    R.layout.item_todo,
                    R.id.task_title,
                    taskList);
            mTaskListView.setAdapter(mAdapter);
        } else {
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();
        }

        cursor.close();
        db.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        System.out.println("onCreateOptionsMenu.....");
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        System.out.println("onOptionsItemSelected.....");
        //launchComposeView();
        switch (item.getItemId()) {
            case R.id.action_add_task:
                Log.d(TAG, "Add a new task");
                final EditText taskEditText = new EditText(this);
                 AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Add a new task")
                        //.setMessage("What do you want to do next?")
                        .setView(taskEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //String task = String.valueOf(taskEditText.getText());
                                //Log.d(TAG, "Task to add: " + task);

                                String task = String.valueOf(taskEditText.getText());
                                SQLiteDatabase db = mHelper.getWritableDatabase();
                                ContentValues values = new ContentValues();
                                values.put(TaskContract.TaskEntry.COL_TASK_TITLE, task);
                                db.insertWithOnConflict(TaskContract.TaskEntry.TABLE,
                                        null,
                                        values,
                                        SQLiteDatabase.CONFLICT_REPLACE);
                                db.close();
                                System.out.println("closing db.....");
                                updateUI();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();

                updateUI();
                return true;

            default:
                updateUI();
                return super.onOptionsItemSelected(item);
        }

    }


    public void onAddItem(View v) {
        System.out.println("onAddItem");
        /*EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();*/
    }

    private void readItems() {
        System.out.println("readItems");
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "/HPE/todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));

        }catch (IOException e) {
            items = new ArrayList<String>();
        }
    }

    private void writeItems() {
        System.out.println("writeItems");
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "/HPE/todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
            System.out.println("wrote to file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupListViewListener() {
        System.out.println("setupListViewListener");
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                items.remove(pos);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
    }

    public void deleteTask(View view) {
        System.out.println("Deleting task.....");
        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.task_title);
        String task = String.valueOf(taskTextView.getText());
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(TaskContract.TaskEntry.TABLE,
                TaskContract.TaskEntry.COL_TASK_TITLE + " = ?",
                new String[]{task});
        db.close();
        updateUI();
    }

    public void testTask(View view) {
        System.out.println("testing task.....");
        launchComposeView(view);
        String code = getIntent().getStringExtra("name");
        System.out.println("code....." + code);

    }

    public void editTask(View view) {
        launchComposeView(view);
    }

    public void launchComposeView(View view) {
        System.out.println("launchComposeView.....");
        // first parameter is the context, second is the class of the activity to launch
        Intent i = new Intent(MainActivity.this, Main2Activity.class);
        // put "extras" into the bundle for access in the second activity
        TextView etName = (TextView) findViewById(R.id.task_title);
        System.out.println("extra is....." + etName.getText().toString());

// copy (start)
        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) view.findViewById(R.id.task_title);
        String task = String.valueOf(taskTextView.getText());

        System.out.println("adapter task ....." + task);
// copy (end)

        /* delete copy start*/
        System.out.println("deleting item ....." + task);
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(TaskContract.TaskEntry.TABLE,
                TaskContract.TaskEntry.COL_TASK_TITLE + " = ?",
                new String[]{task});
        db.close();
        //updateUI();
        /* delete copy end*/



        i.putExtra("code", task);
        // brings up the second activity
        startActivityForResult(i, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("onActivityResult.....");
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK /*&& requestCode == REQUEST_CODE*/) {
            // Extract name value from result extras
            String name = data.getExtras().getString("name");
            System.out.println("onActivityResult....." + name);
            //int code = data.getExtras().getInt("code", 0);
            // Toast the name to display temporarily on screen
          /*  Toast.makeText(this, name, Toast.LENGTH_SHORT).show();*/

            SQLiteDatabase db1 = mHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(TaskContract.TaskEntry.COL_TASK_TITLE, name);
            /*db.insertWithOnConflict(TaskContract.TaskEntry.TABLE,
                    null,
                    values,
                    SQLiteDatabase.CONFLICT_REPLACE);*/

            db1.replace(TaskContract.TaskEntry.TABLE,
                    null,
                    values);

            db1.close();
            updateUI();
        }
    }








}

