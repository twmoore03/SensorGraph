package com.example.twmoore.sensorgraphs;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.HashSet;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor accelSensor;
    private Sensor lightSensor;
    private List<Sensor> sensorList;
    private HashSet<Sensor> availableSensors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        availableSensors = new HashSet<>();
        initializeSensors();

        populateAccelSensorStatus();
        populateAccelSensorInfo();
        populateLightSensorStatus();
        populateLightSensorInfo();
    }

    public void initializeSensors() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        populateAvailableSensorsSet(sensorList);

        if (availableSensors.contains(sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER))) {
            accelSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        } else {
            Log.v("ACCEL", "Sensor not available");
        }

        if (availableSensors.contains(sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT))) {
            lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        } else {
            Log.v("LIGHT", "Sensor not available");
        }
    }

    public void populateAvailableSensorsSet(List<Sensor> sensorsList) {
        for (Sensor s : sensorsList) {
            availableSensors.add(s);
        }
    }

    public void populateAccelSensorStatus() {
        TextView accelStatus = (TextView) findViewById(R.id.accelStatus);
        if (accelSensor != null) {
            accelStatus.setText(R.string.accelSensorPresent);
        } else {
            accelStatus.setText(R.string.accelSensorAbsent);
        }
    }

    public void populateAccelSensorInfo() {
        TextView accelInfo = (TextView) findViewById(R.id.accelInfo);
        if (accelSensor != null) {
            accelInfo.setText("Info: Range = " + accelSensor.getMaximumRange() + ", Resolution = " + accelSensor.getResolution() + ", Delay =  " + accelSensor.getMinDelay()
                     + "-" + accelSensor.getMaxDelay() + ", Power: " + accelSensor.getPower());

        }
    }

    public void populateLightSensorStatus() {
        TextView lightStatus = (TextView) findViewById(R.id.lightStatus);
        if (lightSensor != null) {
            lightStatus.setText(R.string.lightSensorPresent);
        } else {
            lightStatus.setText(R.string.lightSensorAbsent);
        }
    }

    public void populateLightSensorInfo() {
        TextView lightInfo = (TextView) findViewById(R.id.lightInfo);
        if (lightSensor != null) {
            lightInfo.setText("Info: Range = " + lightSensor.getMaximumRange() + ", Resolution = " + lightSensor.getResolution() + ", Delay =  " + lightSensor.getMinDelay()
                    + "-" + lightSensor.getMaxDelay() + ", Power: " + lightSensor.getPower());

        }
    }

    public void openGraphActivity(View v) {
        if (v.getId() == R.id.accelerometerButton) {
            Intent accelerometerIntent = new Intent(this, AccelGraph.class);
            startActivity(accelerometerIntent);
        } else if (v.getId() == R.id.lightButton) {
            Intent lightIntent = new Intent(this, LightGraph.class);
            startActivity(lightIntent);
        }
    }
}

