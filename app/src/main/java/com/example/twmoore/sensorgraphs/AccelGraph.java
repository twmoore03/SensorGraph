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

/**
 * Created by twmoore on 2/26/2017.
 */
public class AccelGraph extends AppCompatActivity implements SensorEventListener {
    private SensorManager sm;
    private Sensor s;
    private PlotView plotview;
    private float currentValue = 0;

    //needed for the animation portion
    private AnimationDrawable animation;
    //private Handler handler = new Handler();
    private int frameCount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plotview);

        setupAndRegisterSensor();

        plotview = (PlotView) findViewById(R.id.plotView);
        plotview.setSensorType("ACCELEROMETER");
    }

    public void setupAndRegisterSensor() {
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        s = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this, s, 1000000);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];
        float z = sensorEvent.values[2];

        currentValue = (float) (Math.sqrt(x * x + y * y + z * z));
        plotview.addPoint(currentValue);
        plotview.invalidate();
    }

    public void backButton(View v) {
        Intent mainActivityIntent = new Intent(this, MainActivity.class);
        sm.unregisterListener(this);
        startActivity(mainActivityIntent);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
