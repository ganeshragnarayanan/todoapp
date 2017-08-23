package com.example.ganesh.simpletodo.db;

/**
 * Created by GANESH on 8/18/17.
 */

import android.provider.BaseColumns;

public class TaskContract {
    public static final String DB_NAME = "com.example.ganesh.simpletodo.db";
    public static final int DB_VERSION = 1;

    public class TaskEntry implements BaseColumns {
        public static final String TABLE = "tasks";

        public static final String COL_TASK_NAME = "task_name";
        public static final String COL_PRIORITY_LEVEL = "task_priority";
        public static final String COL_TASK_NOTES = "task_notes";
        public static final String COL_DATE = "task_date";
        public static final String COL_STATUS = "task_status";
    }
}