package com.example.finalproject1;

import android.provider.BaseColumns;

public class TaskHandler {
    // Versi Database

    public static final int DATABASE_VERSION = 1;

    // Nama Database

    public static final String DATABASE_NAME = "ToDoListData";

    // Nama Tabel dan Kolom Tabel

    public class TaskEntry implements BaseColumns {

        public static final String TABLE_NAME = "TaskTable";

        public static final String COLUMN_TASK_TITLE = "ColumnTaskTitle";
    }
}
