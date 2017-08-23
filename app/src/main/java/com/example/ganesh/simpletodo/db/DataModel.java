package com.example.ganesh.simpletodo.db;

/**
 * Created by GANESH on 8/21/17.
 */

public class DataModel {
    String task_name;
    String priority_level;
    String task_notes;
    String status;
    String date;

    public DataModel(String task_name, String priority_level, String task_notes, String date,
                     String status ) {
        this.task_name=task_name;
        this.task_notes=task_notes;
        this.date = date;
        this.priority_level=priority_level;
        this.status=status;

    }

    public String getTask_name() {
        return task_name;
    }
    public String getTask_notes() {
        return task_notes;
    }

    public String getDate() {
        return date;
    }

    public String getPriority_level() {
        return priority_level;
    }
    public String getStatus() {
        return status;
    }
}