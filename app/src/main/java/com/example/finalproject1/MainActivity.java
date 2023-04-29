package com.example.finalproject1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private SQLiteDatabaseHandler sqLiteDatabaseHandler;
    private ListView ListTugas;
    private ArrayAdapter<String> arrAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sqLiteDatabaseHandler = new SQLiteDatabaseHandler(this);
        ListTugas = (ListView) findViewById(R.id.list_tugas);
        updateUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_tambah_tugas:
                final EditText taskEdit = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Add a new task").setMessage("What do you want to do next?").setView(taskEdit)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String task = String.valueOf(taskEdit.getText());
                                SQLiteDatabase db = sqLiteDatabaseHandler.getWritableDatabase();
                                ContentValues values = new ContentValues();
                                values.put(TaskHandler.TaskEntry.COLUMN_TASK_TITLE, task);
                                db.insertWithOnConflict(TaskHandler.TaskEntry.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                                db.close();
                                updateUI();
                            }
                        })
                        .setNegativeButton("Cancel", null).create();
                dialog.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void hapusTugas(View view) {
        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.judul_tugas);
        String task = String.valueOf(taskTextView.getText());
        SQLiteDatabase db = sqLiteDatabaseHandler.getWritableDatabase();
        db.delete(TaskHandler.TaskEntry.TABLE_NAME, TaskHandler.TaskEntry.COLUMN_TASK_TITLE + " = ?", new String[]{task});
        db.close();
        updateUI();
    }

    private void updateUI() {
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db = sqLiteDatabaseHandler.getReadableDatabase();
        Cursor cursor = db.query(TaskHandler.TaskEntry.TABLE_NAME,
                new String[]{TaskHandler.TaskEntry._ID, TaskHandler.TaskEntry.COLUMN_TASK_TITLE},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(TaskHandler.TaskEntry.COLUMN_TASK_TITLE);
            taskList.add(cursor.getString(idx));
        }

        if (arrAdapter == null) {
            arrAdapter = new ArrayAdapter<>(this, R.layout.todo_list, R.id.judul_tugas, taskList);
            ListTugas.setAdapter(arrAdapter);
        } else {
            arrAdapter.clear();
            arrAdapter.addAll(taskList);
            arrAdapter.notifyDataSetChanged();
            if (arrAdapter == null) {
                arrAdapter = new ArrayAdapter<>(this, R.layout.todo_list, R.id.judul_tugas, taskList);
                ListTugas.setAdapter(arrAdapter);
            } else {
                arrAdapter.clear();
                arrAdapter.addAll(taskList);
                arrAdapter.notifyDataSetChanged();
            }

            cursor.close();
            db.close();
        }
    }
}