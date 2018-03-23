package com.example.alex.adsandroid1;

import android.content.Intent;
import android.net.Uri;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends AppCompatActivity {

    SensorManager sensorManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SensorMethod();

    }

    public void OpenBrowser(View v)
    {
        startActivity(new Intent(Main.this, Browser.class));
    }
    public void SensorMethod()
    {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        // Create a listener
        SensorEventListener gyroscopeSensorListener = new SensorEventListener()
        {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if(sensorEvent.values[2] > 3f) { // anticlockwise

                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.y8.com"));
                    startActivity(browserIntent);
                    //Toast.makeText(Main.this, "Pornhub starting", Toast.LENGTH_SHORT).show();
                    sensorManager.unregisterListener(this);
                    CountdownTimer();

                } else if(sensorEvent.values[2] < -3f) { // clockwise

                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                    startActivity(browserIntent);
                    //Toast.makeText(Main.this, "Google starting", Toast.LENGTH_SHORT).show();
                    sensorManager.unregisterListener(this);
                    CountdownTimer();
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i)
            {
            }

        };
        // Register the listener
        sensorManager.registerListener(gyroscopeSensorListener, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

    public void CountdownTimer()
    {
        CountDownTimer timer2 = new CountDownTimer(3*1000,3*1000) {
            @Override
            public void onTick(long l)
            {
            }

            @Override
            public void onFinish()
            {
                Toast.makeText(Main.this, "Timer stopping", Toast.LENGTH_SHORT).show();
                SensorMethod();
            }
        }.start();
    }
}
