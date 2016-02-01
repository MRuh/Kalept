package com.sadish.kalept;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Dr Radje Sadish on 29/01/2016.
 */
public class ServerRequests {

    ProgressDialog progressDialog;
    public static final int CONNECTION_TIMOUT=1000*15;
    public static final String SERVER_ADDRESS="http://kalept.16mb.com/";


    public ServerRequests(Context context)
    {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("PLease wait..");
        progressDialog.setTitle("Processing");
    }
    public void storeUserDataInBackground(User user,GetUserCallback userCallback)
    {
        progressDialog.show();
        new StoreUserDataAsyncTask(user,userCallback).execute();
    }
    public void fetchUserDataInBackground(User user, GetUserCallback callBack)
    {
        progressDialog.show();
    }

    public class StoreUserDataAsyncTask extends AsyncTask<Void, Void, Void>{
        User user;
        GetUserCallback userCallback;

        public StoreUserDataAsyncTask(User user, GetUserCallback userCallback){
            this.user = user;
            this.userCallback = userCallback;
        }


        @Override
        protected Void doInBackground(Void... params) {


            HashMap<String, String> postDataParams;
            postDataParams = new HashMap<>();
            postDataParams.put("name", user.name);
            postDataParams.put("age", String.valueOf(user.age));
            postDataParams.put("username", user.username);
            postDataParams.put("password", user.password);

            try{

                String resposta =  performPostCall(SERVER_ADDRESS + "Register.php", postDataParams );

            }catch (Exception e){

                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            userCallback.done(null);
            super.onPostExecute(aVoid);
        }
    }



    public class fetchUserDataAsyncTask extends AsyncTask<Void, Void, User> {
        User user;
        GetUserCallback userCallback;

        public fetchUserDataAsyncTask(User user, GetUserCallback userCallback) {
            this.user = user;
            this.userCallback = userCallback;
        }


        @Override
        protected User doInBackground(Void... params) {

            HashMap<String, String> postDataParams;
            postDataParams = new HashMap<>();

            postDataParams.put("username", user.username);
            postDataParams.put("password", user.password);

            String resposta =  performPostCall(SERVER_ADDRESS + "FetchUserData.php", postDataParams );

            User returnedUser = null;
            try {
                JSONObject jsonObject =  new JSONObject(resposta);

                if(jsonObject.length() == 0){

                    returnedUser = null;
                }else {

                    String name = jsonObject.getString("name");
                    int age = jsonObject.getInt("age");

                    returnedUser = new User(name,age, user.username, user.password);

                }

                Log.d("Http Response:", resposta);
            }catch (Exception e){
                e.printStackTrace();

            }

            return returnedUser;
        }


        @Override
        protected void onPostExecute(User returnedUser) {
            progressDialog.dismiss();
            userCallback.done(returnedUser);
            super.onPostExecute(returnedUser);
        }


    }

    public String  performPostCall(String requestURL, HashMap<String, String> postDataParams) {

        URL url;
        String response = "";
        try {
            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);


            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();
            int responseCode=conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }
            }
            else {
                response="";


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }


    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();


    }

}




