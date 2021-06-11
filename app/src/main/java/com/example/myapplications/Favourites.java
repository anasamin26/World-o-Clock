package com.example.myapplications;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplications.data.MyDbHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class Favourites extends AppCompatActivity {
    List<string> result = new ArrayList<>();
    RecyclerView rcv;
    myadapter recyclerAdapter;
    LayoutInflater layoutInflater;
    private FloatingActionButton button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        rcv = (RecyclerView) findViewById(R.id.rclvie);
        rcv.setLayoutManager(new LinearLayoutManager((this)));
        button = (FloatingActionButton) findViewById(R.id.b0);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Intent inten = new Intent(this,S.class);
        startService(inten);

        MyDbHandler db=new MyDbHandler(this);


        final Handler handler = new Handler();
        final int delay = 1000 ; //1000 milliseconds = 1 sec

        handler.postDelayed(new Runnable(){
            public void run(){
                result=db.getAllClocks();
                List<string> shh=new ArrayList<>();
                for(int i=0;i<result.size();i++)
                {
                    string abc = new string();
                    TimeZone.setDefault(TimeZone.getTimeZone(result.get(i).city));
                    SimpleDateFormat date_format = new SimpleDateFormat("hh:mm:ss a");
                    Date date = new Date();
                    abc.city = result.get(i).city;
                    abc.time = date_format.format(date);
                    shh.add(abc);

                    //db.Saveallclock(abc);

                }
                for (int i = 0; i < result.size(); i++) {
                    shh.get(i).check = true;
                }

                //MainRecyclerView.timer(); // call our adapter method here
                rcv.setAdapter(new myadapter(shh,Favourites.this.getApplicationContext()));
                handler.postDelayed(this, delay);
            }
        }, delay);



                                rcv.setAdapter(recyclerAdapter);




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), WorldClock.class);
                intent.putExtra("results2", (Serializable) result);
                startActivityForResult(intent, 1);

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){

            if(resultCode==RESULT_OK)
            {

                List<string> temp = new ArrayList<>((List<string>) data.getSerializableExtra("results1"));
                {
                    for(int i=0;i<temp.size();i++)
                    {
                        for(int j=0;j<result.size();j++)
                        {
                            if(temp.get(i).city.equals(result.get(j).city))
                            {
                                temp.remove(i);
                            }
                        }
                    }

                }
                List<string> alldata=new ArrayList<>((List<string>) data.getSerializableExtra("wholedata"));
                //removing the item if it is unselected in world clock
                for(int i=0;i<alldata.size();i++)
                {
                    for(int j=0;j<result.size();j++)
                    {
                        if(alldata.get(i).city.equals(result.get(j).city)&&(!alldata.get(i).isSelected()))
                        {
                            result.remove(j);

                        }
                    }
                }

                result.addAll(temp);
                for (int i = 0; i < result.size(); i++) {
                    result.get(i).check = true;
                }





                recyclerAdapter = new myadapter(result,this);
                rcv.setAdapter(recyclerAdapter);

            }
        }
    }
}