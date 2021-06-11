package com.example.myapplications;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.myapplications.data.MyDbHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class S extends Service {
    List<String> arrs = new ArrayList<>();
    MyDbHandler db=new MyDbHandler(this);

    public void onCreate() {
    }

    public int onStartCommand(Intent intent,int flags,int startId){
        Thread thread = new Thread (new Runnable(){
            public void run(){
                GetInfo();
            }
        });

        thread.start();

        try {
            thread.join();
            SaveName(arrs);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return START_NOT_STICKY;
    }

    public void SaveName(List<String> arr) {


        string abc = new string();
        List<string>a=db.GetAllClocks();
        if(a.size()==0)
        {
            for (int i = 0; i < arr.size(); i++) {
                abc.city=arr.get(i);
                db.saveallclock(abc);

            }
        }


    }
    private void GetInfo() {
        try{
            //Toast.makeText(this,"In the zone",Toast.LENGTH_SHORT).show();
            URL url = new URL("https://api.timezonedb.com/v2.1/list-time-zone?key=YDHWWJ4BZ93V&format=json");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            //Toast.makeText(this,"Before Connection",Toast.LENGTH_SHORT).show();
         //   Log.println(Log.DEBUG,"Service**","Connecting");
            connection.setDoInput(true);
            connection.connect();
           // Log.println(Log.DEBUG,"Service**","Connected");
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


                }



        } catch(Exception ex) {
            ex.printStackTrace();
            Log.println(Log.DEBUG,"service ", "Error");
        }
    }



    public IBinder onBind(Intent intent){
        return null;
    }
}
