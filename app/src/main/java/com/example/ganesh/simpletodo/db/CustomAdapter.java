package com.example.ganesh.simpletodo.db;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ganesh.simpletodo.MainActivity;
import com.example.ganesh.simpletodo.R;

import java.util.ArrayList;


/**
 * Created by GANESH on 8/21/17.
 */

public class CustomAdapter extends ArrayAdapter<DataModel> implements View.OnClickListener{

    private ArrayList<DataModel> dataSet;
    ArrayList<DataModel> dataModels;
    Context mContext;
    private TaskDbHelper mHelper;
    private ArrayAdapter<String> mAdapter;
    private ListView mTaskListView;

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtType;
        TextView txtVersion;
        ImageView info;
    }

    public CustomAdapter(ArrayList<DataModel> data, Context context) {
        super(context, R.layout.item_todo, data);
        this.dataSet = data;
        this.mContext=context;
        mHelper = new TaskDbHelper(context);
    }

    @Override
    public void onClick(View v) {
        int position=(Integer) v.getTag();
        Object object= getItem(position);
        DataModel dataModel=(DataModel)object;

        switch (v.getId())
        {
            case R.id.task_title:
                final EditText taskEditText = new EditText(v.getContext());

                LayoutInflater factory = LayoutInflater.from(mContext);

                final View textEntryView = factory.inflate(R.layout.fragment_edit_name, null);

                final EditText task_name = (EditText) textEntryView.findViewById(R.id.txt_task_name);
                final EditText task_notes = (EditText) textEntryView.findViewById(R.id.txt_task_notes);
                final EditText task_date = (EditText) textEntryView.findViewById(R.id.txt_task_date);
                final EditText task_priority_level = (EditText) textEntryView.findViewById(R.id.txt_task_priority_level);
                final EditText task_status = (EditText) textEntryView.findViewById(R.id.txt_task_status);

                task_name.setText(dataModel.getTask_name(), TextView.BufferType.EDITABLE);
                task_notes.setText(dataModel.getTask_notes(), TextView.BufferType.EDITABLE);
                task_priority_level.setText(dataModel.getPriority_level(), TextView.BufferType.EDITABLE);
                task_status.setText(dataModel.getStatus(), TextView.BufferType.EDITABLE);
                task_date.setText(dataModel.getDate(), TextView.BufferType.EDITABLE);

                final String to_delete = dataModel.getTask_name();

                AlertDialog dialog = new AlertDialog.Builder(mContext)
                        .setTitle("Edit task")
                        .setView(textEntryView)
                        .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String task_name_edited = String.valueOf(task_name.getText());
                                String task_notes_edited = String.valueOf(task_notes.getText());
                                String task_priority_edited = String.valueOf(task_priority_level.getText());
                                String task_status_edited = String.valueOf(task_status.getText());
                                String task_date_edited = String.valueOf(task_date.getText());

                                SQLiteDatabase db_delete = mHelper.getWritableDatabase();
                                db_delete.delete(TaskContract.TaskEntry.TABLE,
                                        TaskContract.TaskEntry.COL_TASK_NAME + " = ?",
                                        new String[]{to_delete});
                                db_delete.close();

                                SQLiteDatabase db = mHelper.getWritableDatabase();
                                ContentValues values = new ContentValues();

                                values.put(TaskContract.TaskEntry.COL_TASK_NAME, task_name_edited);
                                values.put(TaskContract.TaskEntry.COL_TASK_NOTES, task_notes_edited);
                                values.put(TaskContract.TaskEntry.COL_DATE, task_date_edited);
                                values.put(TaskContract.TaskEntry.COL_PRIORITY_LEVEL, task_priority_edited);
                                values.put(TaskContract.TaskEntry.COL_STATUS, task_status_edited);
                                db.insertWithOnConflict(TaskContract.TaskEntry.TABLE,
                                        null,
                                        values,
                                        SQLiteDatabase.CONFLICT_REPLACE);
                                db.close();
                                ((MainActivity)mContext).updateUI();
                            }
                        })
                        .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                SQLiteDatabase db1 = mHelper.getWritableDatabase();
                                db1.delete(TaskContract.TaskEntry.TABLE,
                                        TaskContract.TaskEntry.COL_TASK_NAME + " = ?",
                                        new String[]{to_delete});
                                db1.close();

                                ((MainActivity)mContext).updateUI();
                            }
                        })
                        .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ((MainActivity)mContext).updateUI();
                            }
                        })
                        .create();
                dialog.show();
                break;
        }
    }



    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DataModel dataModel = getItem(position);
        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_todo, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.task_title);
            viewHolder.txtType = (TextView) convertView.findViewById(R.id.version);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        lastPosition = position;

        viewHolder.txtName.setText(dataModel.getTask_name());
        viewHolder.txtType.setText(dataModel.getPriority_level());
        viewHolder.txtName.setOnClickListener(this);
        viewHolder.txtName.setTag(position);

        return convertView;
    }
}