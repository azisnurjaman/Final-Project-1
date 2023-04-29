package com.example.finalproject1;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDatabaseHandler extends SQLiteOpenHelper {

    public SQLiteDatabaseHandler(Context context){
        super(context, TaskHandler.DATABASE_NAME, null, TaskHandler.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String taskTable = "CREATE TABLE " + TaskHandler.TaskEntry.TABLE_NAME + " ( " + TaskHandler.TaskEntry._ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + TaskHandler.TaskEntry.COLUMN_TASK_TITLE +
                " TEXT NOT NULL);";

        sqLiteDatabase.execSQL(taskTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TaskHandler.TaskEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
