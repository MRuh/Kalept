package com.sadish.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URISyntaxException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, " Contact : Sadish.radje@efrei.net", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


            }
        });


    }
    public Handler mHandler=new Handler()
    {
        public void handleMessage(Message msg)
        {
            switch(msg.what)
            {
                case 1:{
                        Intent i = new Intent(MainActivity.this,MapsActivity.class);
                        startActivity(i);
                        i.putExtra("Username","admin");
                        setContentView(R.layout.activity_maps);
                        break;

                    }
                case 0: {
                    Toast notmatching = Toast.makeText(MainActivity.this, "Password or username are not matching", Toast.LENGTH_SHORT);
                    notmatching.show();
                    break;
                }
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };
    public void onButtonClick(View view) throws URISyntaxException {

        if (view.getId() == R.id.Blogin) {

            EditText a = ((EditText) findViewById(R.id.TFusername));
            final String str = a.getText().toString();
            EditText b = ((EditText)findViewById(R.id.TFpassword));
            String pass = b.getText().toString();
            final String uri= "http://78.234.201.70:8080/MyRestTest/spring/user/"+str+"/"+pass;
            Thread thread=new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    URL obj = null;
                    try {
                        obj = new URL(uri);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    HttpURLConnection con = null;
                    try {
                        con = (HttpURLConnection) obj.openConnection();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // optional default is GET
                    try {
                        con.setRequestMethod("GET");
                    } catch (ProtocolException e) {
                        e.printStackTrace();
                    }

                    //add request header
                    BufferedReader in = null;
                    try {
                        in = new BufferedReader(
                                new InputStreamReader(con.getInputStream()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    try {
                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    int state=0;
                    if(response.toString().equals("true")) state=1;
                    Message message=new Message();
                    message.what=state;
                    message.obj=response.toString();
                    mHandler.sendMessage(message);
                    //print result

                }
            });thread.start();


        }


        if(view.getId() == R.id.Bsignup)

        {
            Intent i = new Intent(MainActivity.this,Signup.class);
            startActivity(i);


        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
