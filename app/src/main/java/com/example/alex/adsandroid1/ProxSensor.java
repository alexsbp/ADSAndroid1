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

public class ProxSensor extends AppCompatActivity implements SensorEventListener {
        private SensorManager mSensorManager;
        private Sensor mProximity;
    private static final int SENSOR_SENSITIVITY = 4;
        @Override
        public final void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);


            // Get an instance of the sensor service, and use that to get an instance of
            // a particular sensor.



        }

    public void SensMeth() {

        mSensorManager = (SensorManager) getSystemService(this.SENSOR_SERVICE);
        mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        @Override
        public final void onSensorChanged (SensorEvent event){
            // Do something with this sensor data.
            if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                if (event.values[0] >= -SENSOR_SENSITIVITY && event.values[0] <= SENSOR_SENSITIVITY) {
                    //near
                    Toast.makeText(getApplicationContext(), "near", Toast.LENGTH_SHORT).show();
                } else {
                    //far
                    Toast.makeText(getApplicationContext(), "far", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        protected void onResume () {
            // Register a listener for the sensor.
            super.onResume();
            mSensorManager.registerListener(this, mProximity, SensorManager.SENSOR_DELAY_NORMAL);
        }


        @Override
        public final void onAccuracyChanged (Sensor sensor,int accuracy){
            // Do something here if sensor accuracy changes.
        }
    }
}
