package com.example.alex.adsandroid1;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Main extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        // Create a listener
            SensorEventListener gyroscopeSensorListener = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent sensorEvent) {
                    // More code goes here
                    if(sensorEvent.values[2] > 0.5f) { // anticlockwise
                        getWindow().getDecorView().setBackgroundColor(Color.BLUE);
                    } else if(sensorEvent.values[2] < -0.5f) { // clockwise
                        getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
                    }
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int i) {
                }
        };


// Register the listener
        sensorManager.registerListener(gyroscopeSensorListener,
                gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }




}
