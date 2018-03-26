package com.example.alex.adsandroid1;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.CountDownTimer;
import android.os.Process;
import android.os.Vibrator;
import android.provider.Contacts;
import android.support.constraint.solver.widgets.ConstraintWidget;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends AppCompatActivity {

    SensorManager sensorManager;
<<<<<<< Updated upstream
    NotificationCompat.Builder mBuilder;
    NotificationManager mNotificManag;

=======
    ProxSensor prox;
>>>>>>> Stashed changes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AddNotification();
        SensorMethod();
        prox = new ProxSensor();

    }

    public void SensorMethod()
    {
        //
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);



        // Create a listener
        SensorEventListener gyroscopeSensorListener = new SensorEventListener()
        {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                //
                if(sensorEvent.values[2] > 4f) { // anticlockwise
                    //creates vibrator
                    Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                    //vibrate for 0,5 seconds
                    v.vibrate(500);
                    //Get URL
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.y8.com"));
                    startActivity(browserIntent);
                    //stop the gyroscope sensor
                    sensorManager.unregisterListener(this);
                    //wait for 4 seconds
                    CountdownTimer();

                } else if(sensorEvent.values[2] < -4f) { // clockwise

                    Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(500);
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                    startActivity(browserIntent);
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
        CountDownTimer timer2 = new CountDownTimer(4*1000,4*1000) {
            @Override
            public void onTick(long l)
            {
            }

            @Override
            public void onFinish()
            {
                //Toast.makeText(Main.this, "Timer stopping", Toast.LENGTH_SHORT).show();
                SensorMethod();
            }
        }.start();
    }

    private void AddNotification()
    {
        //notification builder
        //set the output text
        mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.ic_action_name);
        mBuilder.setContentTitle("Cinnamon");
        mBuilder.setContentText("App is still running in the background");


        Intent notificationIntent = new Intent(this, Main.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(contentIntent);

        mNotificManag = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificManag.notify(0, mBuilder.build());

    }

    protected void DestroyApp (View v)
    {
        mNotificManag.cancelAll();
        Process.killProcess(android.os.Process.myPid());
        onDestroy();
        super.onDestroy();
    }
}
