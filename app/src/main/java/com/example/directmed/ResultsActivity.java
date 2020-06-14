package com.example.directmed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.directmed.data.ResultAdapter;
import com.example.directmed.data.ResultItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ResultsActivity extends AppCompatActivity {

     ArrayList<ResultItem> resultItems = new ArrayList<>();
     ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        resultItems = getIntent().getParcelableArrayListExtra("results");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            resultItems.sort(Comparator.comparing(ResultItem::getDistance));
        }
        ResultAdapter adapter = new ResultAdapter(this,R.layout.adapter_results,resultItems);
        listView = findViewById(R.id.listViewResults);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Intent intent  = new Intent(getBaseContext(),MapsActivity.class);
                //intent.putExtra("result",   resultItems.get(position));
                //intent.putExtra("lat",getIntent().getExtras().getDouble("lat"));
                //intent.putExtra("lng",getIntent().getExtras().getDouble("lng"));
                //startActivity(intent);
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?&daddr="+resultItems.get(position).getLat() + "," +resultItems.get(position).getLng() + '"' ));
                startActivity(intent);
            }
        });



}

}

//https://maps.googleapis.com/maps/api/place/textsearch/json?key=AIzaSyDt4IhKiNRziVzTfjHPxa-dDEfWrS4ITMY&input=catena&inputtype=textquery&fields=formatted_address adrese catena