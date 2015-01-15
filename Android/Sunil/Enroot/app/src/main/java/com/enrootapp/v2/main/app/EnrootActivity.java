package com.enrootapp.v2.main.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

/**
 * Created by rmuttineni on 15/01/2015.
 */
public class EnrootActivity extends ActionBarActivity {
    protected EnrootApp mApp;
    public String TAG;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApp = (EnrootApp) getApplication();
        TAG = getClass().getName();
    }

    protected void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
