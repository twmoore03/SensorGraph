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
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by twmoore on 2/26/2017.
 */
public class AccelGraph extends AppCompatActivity implements SensorEventListener {
    private SensorManager sm;
    private Sensor s;
    private PlotView plotview;
    private float currentValue = 0;
    long lastTimeStamp;
    long timePassed = 0;
    int count = 1;

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
        plotview.setSensorType("ACCELEROMETER");
        displayDataEveryTenthOfASecond();
    }

    public void setupAndRegisterSensor() {
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        s = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this, s, 1000);
    }

    public void displayDataEveryTenthOfASecond() {
        TimerTask displayDataTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        count++;
                        if (count % 2 == 0) {
                            updateTimeLabel();
                        }
                        plotview.addPoint(currentValue);
                        plotview.invalidate();
                    }
                });
            }
        };
        Timer timer = new Timer();
        timer.schedule(displayDataTask, 0, 100);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        timePassed = sensorEvent.timestamp - lastTimeStamp;
        if (timePassed >  100000000) {
            lastTimeStamp = sensorEvent.timestamp;


            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            currentValue = (float) (Math.sqrt(x * x + y * y + z * z));
            //Log.v("ACCEL", Float.toString(currentValue));
        }
    }

    public void updateTimeLabel() {
        TextView labelZero = (TextView) findViewById(R.id.timeLabel0);
        labelZero.setText(Integer.toString(count));

        TextView labelTwo = (TextView) findViewById(R.id.timeLabel2);
        labelTwo.setText(Integer.toString(count + 2));

        TextView labelFour = (TextView) findViewById(R.id.timeLabel4);
        labelFour.setText(Integer.toString(count + 4));

        TextView labelSix = (TextView) findViewById(R.id.timeLabel6);
        labelSix.setText(Integer.toString(count + 6));

        TextView labelEight = (TextView) findViewById(R.id.timeLabel8);
        labelEight.setText(Integer.toString(count + 8));
    }

    public void backButton(View v) {
        Intent mainActivityIntent = new Intent(this, MainActivity.class);
        sm.unregisterListener(this);
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
