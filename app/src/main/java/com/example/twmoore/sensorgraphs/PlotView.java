package com.example.twmoore.sensorgraphs;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by twmoore on 2/21/17.
 */

public class PlotView extends View {

    public List<Float> dataPoints = new ArrayList<>(10);

    public PlotView(Context context) {
        super(context);
    }

    public PlotView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlotView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PlotView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onDraw(Canvas canvas) {
        int canvasHeight = this.getHeight();
        int canvasWidth = this.getWidth();
        //need to properly utilize the height and width to scale where the dataPoints are drawn
        super.onDraw(canvas);
        Paint dataPointPaint = new Paint();
        dataPointPaint.setColor(Color.BLUE);
        dataPointPaint.setStrokeWidth(10);

        for (int i = 0; i < dataPoints.size(); i++) {
            canvas.drawPoint(i * canvasWidth, dataPoints.get(i)/canvasHeight, dataPointPaint);
        }
    }

    public void clearList(List list) {
        list.clear();
    }

    public void addPoint(float newPoint) {
        if (dataPoints.size() >= 10) {
            dataPoints.remove(0);
        }
        dataPoints.add(newPoint);
    }
}
