package com.sadish.myapplication;

/**
 * Created by Dr Radje Sadish on 17/01/2016.
 */
public class Contact {

    int id;
    String name,email,uname,pass;


    public void setId(int id)
    {
        this.id=id;

    }

    public int getId()
    {

        return this.id;

    }

    public void setName(String name)
    {

        this.name=name;
    }

    public String getName()
    {
        return this.name;
    }
    public void setEmail(String email)
    {

        this.email=email;
    }
    public String getEmail(){

        return this.email;
    }
    public void setPass(String pass)
    {

        this.pass=pass;
    }
    public String getPass()
    {

        return this.pass;
    }
    public void setUname(String uname)
    {

        this.uname=uname;
    }
    public String getUname()
    {

        return this.uname;

    }
}
