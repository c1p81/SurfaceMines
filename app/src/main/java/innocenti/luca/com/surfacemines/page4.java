package innocenti.luca.com.surfacemines;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.Arrays;

/**
 * Created by lucainnocenti on 28/11/17.
 */

public class page4 extends Fragment
{
    private View rootView;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.page4, container, false);
        return rootView;
    }

    public void onViewCreated(View rootView, Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);
        GraphView graph = (GraphView) rootView.findViewById(R.id.graph);

        graph.setTitle("Overall Risk");
        graph.setTitleTextSize(50);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0.0);
        graph.getViewport().setMaxX(1.0);

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0.0);
        graph.getViewport().setMaxY(1.0);

        GridLabelRenderer gridLabel = graph.getGridLabelRenderer();
        gridLabel.setHorizontalAxisTitle("Likelihood");
        gridLabel.setVerticalAxisTitle("Severity");
        gridLabel.setHorizontalAxisTitleTextSize(10);
        graph.getGridLabelRenderer().setHorizontalAxisTitleTextSize(40);
        graph.getGridLabelRenderer().setVerticalAxisTitleTextSize(40);
        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.BOTH);
        graph.getGridLabelRenderer().reloadStyles();







        LineGraphSeries<DataPoint> series0 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0,0),
                new DataPoint(0,1),
                new DataPoint(0.1, 1),
                new DataPoint(0.53, 0),
        });
        series0.setThickness(8);
        series0.setColor(Color.WHITE);
        series0.setDrawBackground(true);
        series0.setBackgroundColor(Color.argb( 255, 0, 255, 0));
        graph.addSeries(series0);


        LineGraphSeries<DataPoint> series01 = new LineGraphSeries<>(new DataPoint[] {

                new DataPoint(0.1, 1),
                new DataPoint(0.53, 0),
        });
        series01.setThickness(8);
        series01.setColor(Color.YELLOW);
        graph.addSeries(series01);




        LineGraphSeries<DataPoint> series1 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0.24, 1),
                new DataPoint(0.68, 0),
        });

        series1.setThickness(8);
        series1.setColor(Color.RED);
        graph.addSeries(series1);


        LineGraphSeries<DataPoint> series11 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0,0),
                new DataPoint(0,1),
                new DataPoint(0.24, 1),
                new DataPoint(0.68, 0),
        });
        series11.setThickness(8);
        series11.setColor(Color.WHITE);
        series11.setDrawBackground(true);
        series11.setBackgroundColor(Color.argb( 100, 255, 255, 0));
        graph.addSeries(series11);


        PointsGraphSeries<DataPoint> series2 = new PointsGraphSeries<>(new DataPoint[] {
                new DataPoint(0.5, 0.5),

        });
        graph.addSeries(series2);
        //series2.setShape(PointsGraphSeries.Shape.POINT);
        series2.setColor(Color.BLUE);

        series2.setCustomShape(new PointsGraphSeries.CustomShape() {
            @Override
            public void draw(Canvas canvas, Paint paint, float x, float y, DataPointInterface dataPoint) {
                paint.setStrokeWidth(10);
                canvas.drawLine(x-5, y-5, x+5, y+5, paint);
                canvas.drawLine(x+5, y-5, x-5, y+5, paint);
            }
        });

        LineGraphSeries<DataPoint> series21 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0,0),
                new DataPoint(0,1),
                new DataPoint(1, 1),
        });
        series21.setThickness(8);
        series21.setColor(Color.WHITE);
        series21.setDrawBackground(true);
        series21.setBackgroundColor(Color.argb( 50, 255, 0, 0));
        graph.addSeries(series21);



    }
}
