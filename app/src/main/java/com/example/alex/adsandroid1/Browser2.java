package com.example.alex.adsandroid1;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

public class Browser2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser2);

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.Google.com"));
        //
        startActivity(browserIntent);
    }
}
