package com.sadish.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;

/**
 * Created byRadje Sadish on 16/01/2016.
 */
public class Signup extends Activity {


    DatabaseHelper helper = new DatabaseHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);







    }

    public Handler mHandler=new Handler()
    {
        public void handleMessage(Message msg)
        {
            switch(msg.what)
            {
                case 1:{
                    Toast pass = Toast.makeText(Signup.this, (String) msg.obj, Toast.LENGTH_SHORT);
                    //Toast pass = Toast.makeText(Signup.this,result, Toast.LENGTH_SHORT);
                    pass.show();
                    break;

                }
                case 0: {
                    Toast notmatching = Toast.makeText(Signup.this, (String) msg.obj, Toast.LENGTH_SHORT);
                    notmatching.show();
                    break;
                }
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

public void onSignUpClick ( View v )

{
    if(v.getId() == R.id.Bsignupbutton)
    {
        EditText name = (EditText)findViewById(R.id.TFname);
        EditText email = (EditText)findViewById(R.id.TFemail);
        EditText uname = (EditText)findViewById(R.id.TFuname);
        EditText pass1 = (EditText)findViewById(R.id.TFpass1);
        EditText pass2 = (EditText)findViewById(R.id.TFpass2);

        String namestr = name.getText().toString();
        final String emailstr = email.getText().toString();
        final String unamestr = uname.getText().toString();
        final String pass1str = pass1.getText().toString();
        final String pass2str = pass2.getText().toString();
        final String uri = "http://78.234.201.70:8080/MyRestTest/spring/user/"+namestr+"/"+pass1str+"/"+unamestr+"/"+emailstr;

        if(!pass1str.equals(pass2str))
        {
            Toast pass = Toast.makeText(Signup.this,"Password don't match",Toast.LENGTH_SHORT);
            pass.show();

        }
        else
        {
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
                    try {
                        con.setRequestMethod("POST");
                    } catch (ProtocolException e) {
                        e.printStackTrace();
                    }
                    con.setDoOutput(true);
                    con.setDoInput(true);
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
                    if(response.toString().equals("create successfully")) state=1;
                    Message message=new Message();
                    message.what=state;
                    message.obj=response.toString();
                    mHandler.sendMessage(message);
                    //print result

                }
            });thread.start();

        //Toast pass = Toast.makeText(Signup.this, "ok",Toast.LENGTH_SHORT);
       // pass.show();

        }
    }




}




}
