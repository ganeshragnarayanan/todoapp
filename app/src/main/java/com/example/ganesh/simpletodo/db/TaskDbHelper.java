package com.example.ganesh.simpletodo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by GANESH on 8/18/17.
 */


public class TaskDbHelper extends SQLiteOpenHelper {

    public TaskDbHelper(Context context) {

        super(context, TaskContract.DB_NAME, null, TaskContract.DB_VERSION);
        System.out.println("TaskDbHelper ...");
        /*SQLiteDatabase db;
        db = getWritableDatabase();
        onCreate(db);*/
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TaskContract.TaskEntry.TABLE);

        String createTable = "CREATE TABLE " + TaskContract.TaskEntry.TABLE + " ( " +
                TaskContract.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TaskContract.TaskEntry.COL_TASK_NAME + " TEXT NOT NULL, " +
                TaskContract.TaskEntry.COL_PRIORITY_LEVEL + " TEXT NOT NULL, " +
                TaskContract.TaskEntry.COL_TASK_NOTES + " TEXT NOT NULL, " +
                TaskContract.TaskEntry.COL_DATE + " TEXT NOT NULL, " +
                TaskContract.TaskEntry.COL_STATUS + " TEXT NOT NULL);";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TaskContract.TaskEntry.TABLE);
        onCreate(db);
    }
}