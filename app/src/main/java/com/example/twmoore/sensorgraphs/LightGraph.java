package com.example.twmoore.sensorgraphs;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by twmoore on 2/26/2017.
 */
public class LightGraph extends AppCompatActivity implements SensorEventListener {
    private SensorManager sm;
    private Sensor s;
    private PlotView plotview;
    private float currentValue = 0;
    long lastTimeStamp;
    private final long OneSecondInNanoSeconds = 1000000000;

    //needed for the animation portion
    private AnimationDrawable animation;
    //private Handler handler = new Handler();
    private int frameCount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plotview);

        setupAndRegisterSensor();
        lastTimeStamp = 0;

        plotview = (PlotView) findViewById(R.id.plotView);
        plotview.setSensorType("LIGHT");
    }

    public void setupAndRegisterSensor() {
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        s = sm.getDefaultSensor(Sensor.TYPE_LIGHT);
        sm.registerListener(this, s, 1000);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.timestamp - lastTimeStamp >  OneSecondInNanoSeconds) {
            lastTimeStamp = sensorEvent.timestamp;

            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            currentValue = (float) (Math.sqrt(x * x + y * y + z * z));
            plotview.addPoint(currentValue);
            plotview.invalidate();
        }
    }

    public void backButton(View v) {
        Intent mainActivityIntent = new Intent(this, MainActivity.class);
        startActivity(mainActivityIntent);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sm.registerListener(this, s, 1000);
    }
}
