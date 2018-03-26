package com.example.alex.adsandroid1;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends AppCompatActivity {
    //Gyroscope stuff
    SensorEventListener gyroscopeSensorListener;
    Sensor gyroscopeSensor;
    SensorManager sensorManager;
    NotificationCompat.Builder mBuilder;
    NotificationManager mNotificManag;

    EditText editText;
    EditText editText2;
    Button button;
    TextView textView;



    //Proximity stuff
    SensorManager mSensorManager;
    Sensor mProximity;
    int SENSOR_SENSITIVITY = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText)findViewById(R.id.websiteTextLeft);
        editText2 = (EditText) findViewById(R.id.websiteTextRight);




        AddNotification();
        SensorMethodGyroscope();
        SensorMethodProximity();

    }
    public void SensorMethodProximity()
    {
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        // create a listener
        SensorEventListener proximitySensorListener = new SensorEventListener() {
            public final void onSensorChanged(SensorEvent event) {
                // Do something with this sensor data.
                if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                    if (event.values[0] >= -SENSOR_SENSITIVITY && event.values[0] <= SENSOR_SENSITIVITY) {
                        //near

                        sensorManager.unregisterListener(gyroscopeSensorListener);
                        //Toast.makeText(getApplicationContext(), "near", Toast.LENGTH_SHORT).show();
                    } else {
                        //far
                        sensorManager.registerListener(gyroscopeSensorListener, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);
                        //Toast.makeText(getApplicationContext(), "far", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            public final void onAccuracyChanged(Sensor sensor, int accuracy) {
                // Do something here if sensor accuracy changes.
            }
        };
        // register the listener
        mSensorManager.registerListener(proximitySensorListener, mProximity, SensorManager.SENSOR_DELAY_NORMAL);

    }

    public void SensorMethodGyroscope()
    {

        //creates sensormanager to get gyroscope
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        // Create a listener
        gyroscopeSensorListener = new SensorEventListener()


        {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                String text = editText.getText().toString();
                String text2 = editText2.getText().toString();
                //asks about the z-axe
                if(sensorEvent.values[2] > 4f) { // anticlockwise
                    //creates vibrator
                    Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                    //vibrate for 0,5 seconds
                    v.vibrate(500);
                    //Get URL
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://" + text));
                    MuteAudio();
                    startActivity(browserIntent);
                    //stop the gyroscope sensor
                    sensorManager.unregisterListener(this);
                    // timer, wait 4 seconds before enabling tilt again
                    CountdownTimer();

                } else if(sensorEvent.values[2] < -4f) { // clockwise

                    Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(500);
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://" + text2));
                    MuteAudio();
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
                SensorMethodGyroscope();
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
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,PendingIntent.FLAG_CANCEL_CURRENT);
        mBuilder.setContentIntent(contentIntent);

        mNotificManag = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificManag.notify(0, mBuilder.build());
    }

    protected void DestroyApp (View v)
    {
        mNotificManag.cancelAll();
        Process.killProcess(android.os.Process.myPid());
        super.onDestroy();
    }

    public void MuteAudio(){
        AudioManager amanager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
        //set music volume to zero (STREAM_MUSIC = Media)
        amanager.setStreamVolume(AudioManager.STREAM_MUSIC, 0,0);

    }
}
