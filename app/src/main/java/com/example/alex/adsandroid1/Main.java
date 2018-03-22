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
import android.widget.Toast;

public class Main extends AppCompatActivity {

    Boolean timer = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CountdownTimer();

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        // Create a listener
            SensorEventListener gyroscopeSensorListener = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent sensorEvent) {
                    if (timer = true)
                    {
                        if(sensorEvent.values[2] > 3f) { // anticlockwise
                            startActivity(new Intent(Main.this, Browser.class));
                            Toast.makeText(Main.this, "Pornhub starting", Toast.LENGTH_SHORT).show();
                            CountdownTimer();
                            timer = false;
                            Toast.makeText(Main.this, "Pornhub stopping", Toast.LENGTH_SHORT).show();
                            //getWindow().getDecorView().setBackgroundColor(Color.BLUE);
                        } else if(sensorEvent.values[2] < -3f) { // clockwise
                            startActivity(new Intent(Main.this, Browser2.class));
                            Toast.makeText(Main.this, "Google starting", Toast.LENGTH_SHORT).show();
                            CountdownTimer();
                            timer = false;
                            Toast.makeText(Main.this, "Google stopping", Toast.LENGTH_SHORT).show();
                            //getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
                        }

                    }

                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int i) {
                }
        };


// Register the listener
        sensorManager.registerListener(gyroscopeSensorListener, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void OpenBrowser(View v)
    {
        startActivity(new Intent(Main.this, Browser.class));
    }

    public void CountdownTimer()
    {
        CountDownTimer timer2 = new CountDownTimer(100000,10000) {
            @Override
            public void onTick(long l)
            {

            }

            @Override
            public void onFinish()
            {
                timer = true;
            }
        }.start();
    }
}
