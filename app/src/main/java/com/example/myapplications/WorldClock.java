package com.example.myapplications;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class WorldClock extends AppCompatActivity {


    RecyclerView rcv;
    string a;
    myadapter recyclerAdapter;
    List<string> arrs = new ArrayList<>();
    LayoutInflater layoutInflater;
    List<string> results=new ArrayList<>();
    CheckBox importanceCheck;
    private FloatingActionButton button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("World Clock");
        rcv=(RecyclerView)findViewById(R.id.rclview);
        rcv.setLayoutManager(new LinearLayoutManager((this)));

        String[] arr = TimeZone.getAvailableIDs();
        string abc = new string();
        for (int i = 0; i < arr.length; i++) {
            abc = new string();
            TimeZone.setDefault(TimeZone.getTimeZone(arr[i]));
            SimpleDateFormat date_format = new SimpleDateFormat("hh:mm:ss a");
            Date date = new Date();
            abc.city = arr[i];
            abc.time = date_format.format(date);
            arrs.add(i, abc);

        }
        Intent intent = getIntent();
        List<string> checkedlist = new ArrayList<>((List<string>) intent.getSerializableExtra("results2"));
        for (int i = 0; i < arrs.size(); i++) {
            for (int j = 0; j < checkedlist.size(); j++) {
                if (arrs.get(i).city.equals(checkedlist.get(j).city)) {
                    arrs.get(i).setSelected(true);
                }

            }

        }
        recyclerAdapter = new myadapter(arrs, this);
        rcv.setAdapter(recyclerAdapter);

        button = (FloatingActionButton) findViewById(R.id.b0);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData(v);
                int count = 0;
                Intent resultintent = new Intent(WorldClock.this, Favourites.class);
                resultintent.putExtra("results1", (Serializable) results);
                resultintent.putExtra("wholedata", (Serializable) arrs);
                setResult(RESULT_OK, resultintent);
                finish();
            }
        });




    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
      getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem item=menu.findItem(R.id.action_search);
        SearchView searchview= (SearchView) item.getActionView();
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recyclerAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    public void getData(View V)
    {

        results=recyclerAdapter.listofselectedvalues();

    }
}