package com.example.twmoore.sensorgraphs;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by twmoore on 2/21/17.
 */

public class PlotView extends View {

    private final float POINT_SIZE = 15f;
    private final float STROKE_WIDTH = 8f;
    private String sensorType;
    public List<Float> dataPoints;
    int xUnit;
    int yUnit;
    float scaledY;


    public PlotView(Context context) {
        super(context);
        initializeEmptyDataList();
    }

    public PlotView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeEmptyDataList();
    }

    public PlotView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeEmptyDataList();
    }

    public PlotView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initializeEmptyDataList();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        determineXAndYUnits(canvas);
        determineYScale(canvas);

        drawGrid(canvas);
        plotSensor(canvas);
    }

    public void plotSensor(Canvas c) {
        for (int i = 0; i < 10; i++) {
            if (sensorType.equals("ACCELEROMETER")) {
                plotSensorStatistics(i, c);
            } else {
                plotSensorStatistics(i, c);
            }
        }
    }

    private void scaleYAxis() {
        if (sensorType.equals("ACCELEROMETER")) {

        } else {

        }
    }

    public void plotSensorStatistics(int index, Canvas c) {
        plotDataValues(index, c);
        plotMeans(index, c);
        plotStandardDeviations(index, c);
    }

    public void plotDataValues(int index, Canvas c) {
        Paint point = new Paint();
        point.setColor(Color.GREEN);
        point.setStrokeWidth(STROKE_WIDTH);

        float currentX = index * xUnit;
        float currentY = c.getHeight() -  dataPoints.get(index) * scaledY;
        c.drawCircle(currentX, currentY, POINT_SIZE, point);

        if (index < 9) {
            float nextX = (index + 1) * xUnit;
            float nextY = c.getHeight() - dataPoints.get(index + 1) * scaledY;
            c.drawLine(currentX, currentY, nextX, nextY, point);
        }
    }

    public void plotMeans(int index, Canvas c) {
        Paint pMean = new Paint();
        pMean.setColor(Color.BLUE);
        pMean.setStrokeWidth(STROKE_WIDTH);

        float currentX = index * xUnit;
        float currentY = c.getHeight() - calculateMean(index) * scaledY;
        c.drawCircle(currentX, currentY, POINT_SIZE, pMean);

        if (index < 9) {
            float nextX = (index + 1) * xUnit;
            float nextY = c.getHeight() - calculateMean(index + 1) * scaledY;
            c.drawLine(currentX, currentY, nextX, nextY, pMean);
        }
    }

    public void plotStandardDeviations(int index, Canvas c) {
        Paint pStandardDeviation = new Paint();
        pStandardDeviation.setColor(Color.YELLOW);
        pStandardDeviation.setStrokeWidth(STROKE_WIDTH);

        float currentX = index * xUnit;
        float currentY = c.getHeight() - calculateStandardDeviation(index) * scaledY;
        c.drawCircle(currentX, currentY, POINT_SIZE, pStandardDeviation);

        if (index < 9) {
            float nextX = (index + 1) * xUnit;
            float nextY = c.getHeight() - calculateStandardDeviation(index + 1) * scaledY;
            c.drawLine(currentX, currentY, nextX, nextY, pStandardDeviation);
        }
    }

    public void determineYScale(Canvas canvas) {
        scaledY = 0;
        if (sensorType.equals("ACCELEROMETER")) {
            scaledY = (float) canvas.getHeight() / 40;
        } else {
            scaledY = (float) canvas.getHeight() / 6000;
        }
    }

    public void drawGrid(Canvas c) {
        Paint gridPaint = new Paint();
        gridPaint.setColor(Color.BLACK);

        for (int i = 0; i < 9; i++) {
            //vertical lines
            c.drawLine(i * xUnit * 2, 0, i * xUnit * 2, c.getHeight(), gridPaint);
            //horizontal lines
            c.drawLine(0, i * yUnit * 2, c.getWidth(), i * yUnit * 2, gridPaint);
        }
    }

    public void determineXAndYUnits(Canvas c) {
        xUnit = c.getWidth() / 8;
        yUnit = c.getHeight() / 8;
    }

    public void clearList(List list) {
        list.clear();
    }

    public void initializeEmptyDataList() {
        dataPoints = new ArrayList<>(10);

        for (int i = 0; i < 10; i++) {
            dataPoints.add((float) 0);
        }
    }

    public void addPoint(float newPoint) {
        if (dataPoints.size() >= 10) {
            dataPoints.remove(0);
        }
        dataPoints.add(newPoint);
    }

    private float calculateStandardDeviation(int index) {
        float currentMean = calculateMean(index);
        float sum = 0;
        int count = 0;

        for (int i = index; i >= 0; i--) {
            if (count < 5) {
                sum += Math.pow((dataPoints.get(i) - currentMean), 2);
            } else {
                break;
            }
            count++;
        }
        return (float) (Math.sqrt(sum / count));
    }

    private float calculateMean(int index) {
        float sum = 0;
        int count = 0;

        for (int i = index; i >= 0; i--) {
            if (count < 5) {
                sum += dataPoints.get(i);
            } else {
                break;
            }
            count++;
        }
        return sum / count;
    }

    public void setSensorType(String s) {
        sensorType = s;
    }
}
