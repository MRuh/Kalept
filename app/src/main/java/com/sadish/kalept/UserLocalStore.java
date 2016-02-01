package com.sadish.kalept;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Dr Radje Sadish on 29/01/2016.
 */
public class UserLocalStore {

    public static final String SP_NAME ="userDetails";
    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context)
    {
        userLocalDatabase= context.getSharedPreferences(SP_NAME,0);
    }

    public void storeUserData(User user){

        SharedPreferences.Editor spEditor=userLocalDatabase.edit();
        spEditor.putString("name",user.name);
        spEditor.putString("username",user.username);
        spEditor.putString("password",user.password);
        spEditor.putString("age", String.valueOf(user.age));

    }

    public User getLoggedInUser(){

        String name = userLocalDatabase.getString("name", "");
        String username = userLocalDatabase.getString("username","");
        String password = userLocalDatabase.getString("password","");
        int age = userLocalDatabase.getInt("age",-1);

        User storeUser = new User(name,age,username,password);

        return storeUser;


    }

    public void setUserLoggedIn(boolean LoggedIn)
    {
        SharedPreferences.Editor spEditor=userLocalDatabase.edit();
        spEditor.putBoolean("LoggedIn",LoggedIn);
        spEditor.commit();

    }
    public void clearUserData(){

        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();

    }
    public boolean getUserLoggedIn(){
        if(userLocalDatabase.getBoolean("loggedIn",false)==true){
            return true;
        }
        else {
            return false;
        }

    }



}
