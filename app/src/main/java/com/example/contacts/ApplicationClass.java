package com.example.contacts;

import android.app.Application;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;

import java.util.List;

public class ApplicationClass extends Application {

    public static final String APPLICATION_ID = "E7094755-42BF-8DA2-FFF9-E2EE1FFFDE00";
    public static final String API_KEY = "43D87789-072E-42AA-A6D1-6DB7A1C68DAE";
    static final String SERVER_URL = "https://api.backendless.com";

    public static BackendlessUser user; // so it's in the application glossary, to refer to it also use ApplicationClass.user
    public static List<Contact>contacts; // this list will have link to all the contacts in the database
    @Override
    public void onCreate() {
        super.onCreate();
        Backendless.setUrl( SERVER_URL );  //for initialization
        Backendless.initApp( getApplicationContext(),
                APPLICATION_ID,
                API_KEY );
    }
}
