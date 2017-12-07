package innocenti.luca.com.surfacemines;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


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

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by lucainnocenti on 28/11/17.
 */

public class page4 extends Fragment
{
    private View rootView;
    private String risk;
    private EditText rischio;

    private float Pof_mining;
    private float Con_mining;
    private float Pof_geotech;
    private float Con_geotech;
    private float overall_Pof;
    private float overall_Con;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.page4, container, false);
        return rootView;
    }

    public void onViewCreated(View rootView, Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);

        GraphView graph = (GraphView) rootView.findViewById(R.id.graph);
        rischio = (EditText) rootView.findViewById(R.id.overall_risk);


        // CALCOLA OVERALL
        SharedPreferences prefs = this.getActivity().getSharedPreferences("variabili", MODE_PRIVATE);


        Pof_mining = prefs.getFloat("Pof_mining",0);
        Con_mining = prefs.getFloat("Con_mining",0);

        Pof_geotech = prefs.getFloat("Pof_geotech",0);
        Con_geotech = prefs.getFloat("Con_geotech",0);

        overall_Pof = (Pof_geotech + Pof_mining) / 2;
        overall_Con = (Con_geotech+Con_mining)/2;

        if ((overall_Pof+(2.278*overall_Con)) < 1.23)
        {
            rischio.setText("Low");
        }

        if (((overall_Pof+(2.278*overall_Con)) >= 1.23) && ((overall_Pof+(2.278*overall_Con)) <1.55))
        {
            rischio.setText("Medium");
        }

        if ((overall_Pof+(2.278*overall_Con)) >= 1.55)
        {
            rischio.setText("High");
        }



        graph.setTitle("Overall Risk");
        graph.setTitleTextSize(50);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0.0);
        graph.getViewport().setMaxX(1.0);

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0.0);
        graph.getViewport().setMaxY(1.0);

        GridLabelRenderer gridLabel = graph.getGridLabelRenderer();
        gridLabel.setHorizontalAxisTitle("Pof");
        gridLabel.setVerticalAxisTitle("Consequences");
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

        //PLOTTA IL PUNTO
        PointsGraphSeries<DataPoint> series2 = new PointsGraphSeries<>(new DataPoint[] {
                new DataPoint(overall_Pof, overall_Con),

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
