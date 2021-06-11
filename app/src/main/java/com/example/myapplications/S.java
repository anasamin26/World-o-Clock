package com.example.myapplications;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.myapplications.data.MyDbHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Provider;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class S extends Service {
    List<String> arrs = new ArrayList<>();
    MyDbHandler db=new MyDbHandler(this);

    public void onCreate() {
    }

    public int onStartCommand(Intent intent,int flags,int startId){
        Toast.makeText(this,"Service starting",Toast.LENGTH_SHORT).show();
        Thread thread = new Thread (new Runnable(){
            public void run(){
                gettimezoneinfo();
            }
        });

        thread.start();

        try {
            thread.join();
            Toast.makeText(this,"Joined",Toast.LENGTH_SHORT).show();
            gettime(arrs);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return START_NOT_STICKY;
    }

    public void gettime(List<String> arr) {

        List<string> arrs=new ArrayList<>();
        string abc = new string();
        List<string>a=db.GetAllClocks();
        if(a.size()==0)
        {
            for (int i = 0; i < arr.size(); i++) {
                abc = new string();
                TimeZone.setDefault(TimeZone.getTimeZone(arr.get(i)));
                SimpleDateFormat date_format = new SimpleDateFormat("hh:mm:ss a");
                Date date = new Date();
                abc.city = arr.get(i);
                abc.time = date_format.format(date);
                arrs.add(i, abc);
                db.saveallclock(abc);

            }
        }
        else
        {
            for(int i=0;i<arr.size();i++)
            {
                db.deleteclock(arr.get(i));

            }
            for (int i = 0; i < arr.size(); i++) {
                abc = new string();
                TimeZone.setDefault(TimeZone.getTimeZone(arr.get(i)));
                SimpleDateFormat date_format = new SimpleDateFormat("hh:mm:ss a");
                Date date = new Date();
                abc.city = arr.get(i);
                abc.time = date_format.format(date);
                arrs.add(i, abc);
                db.saveallclock(abc);

            }

        }

    }
    private void gettimezoneinfo() {
        try{
            //Toast.makeText(this,"In the zone",Toast.LENGTH_SHORT).show();
            URL url = new URL("https://api.timezonedb.com/v2.1/list-time-zone?key=YDHWWJ4BZ93V&format=json");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            //Toast.makeText(this,"Before Connection",Toast.LENGTH_SHORT).show();
            Log.println(Log.DEBUG,"Service**","Connecting");
            connection.setDoInput(true);
            connection.connect();
            Log.println(Log.DEBUG,"Service**","Connected");
            //Toast.makeText(this,"Connected",Toast.LENGTH_SHORT).show();
            StringBuilder content = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader( connection.getInputStream() ) );
            String line;
            while((line = reader.readLine()) != null ){
                content.append(line);
            }
            line = content.toString();
            JSONObject obj = new JSONObject(line);




            JSONArray jsonarr = obj.getJSONArray("zones");
                for(int i=0;i<jsonarr.length();i++) {
                    JSONObject objs = jsonarr.getJSONObject(i);
                    String name = objs.getString("zoneName");
                    arrs.add(name);
                    Log.println(Log.DEBUG, "Service**", name);

                }


            // Toast.makeText(this,jsonarr.toString(),Toast.LENGTH_SHORT).show();
        } catch(Exception ex) {
            ex.printStackTrace();
            Log.println(Log.DEBUG,"service ", "Error");
        }
    }













    private void getinfo()
    {
        String line=null;
        try{
            Toast.makeText(this,"GetInfo",Toast.LENGTH_SHORT).show();
            URL url = new URL("http://api.timezonedb.com/v2.1/list-time-zone?key=YOUR_API_KEY&format=json");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            Toast.makeText(this,"CONNECTING",Toast.LENGTH_SHORT).show();
            connection.setDoInput(true);
            connection.connect();
            Toast.makeText(this,"CONNECTED",Toast.LENGTH_SHORT).show();
            StringBuilder content = new StringBuilder();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader( connection.getInputStream() ) );

            while( (line = reader.readLine()) != null ){
                content.append(line);
            }
            line = content.toString();
            String in = null;
            JSONObject reade = new JSONObject(line);
            JSONArray a=reade.getJSONArray("zoneName");
            JSONObject obj =a.getJSONObject(0);
            Log.d("data",a.toString());
            //Toast.makeText(this,obj.getString("zoneName"),Toast.LENGTH_SHORT).show();
            System.out.println(a.toString());
        } catch(Exception ex) {
            ex.printStackTrace();
        }

    }
    public IBinder onBind(Intent intent){
        return null;
    }
}
