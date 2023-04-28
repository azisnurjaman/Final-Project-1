package com.example.finalproject1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;


public class MainActivity extends AppCompatActivity {

    private SQLiteDatabaseHandler sqLiteDatabaseHandler;

    private ListView ListTugas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sqLiteDatabaseHandler = new SQLiteDatabaseHandler(this);
        ListTugas = (ListView) findViewById(R.id.list_tugas);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}