package com.example.expressrailwaynew;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expressrailwaynew.Database.DbHandler;

import java.util.List;

public class ViewHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history);

        // Initialize ListView
        ListView historyListView = findViewById(R.id.historyListView);

        // Retrieve history data from the database
        DbHandler dbHandler = new DbHandler(getApplicationContext());
        List<String> historyData = dbHandler.getAllHistoryData(); // Implement this method in DbHandler

        // Display history data in ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, historyData);
        historyListView.setAdapter(adapter);
    }
}
