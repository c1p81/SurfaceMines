package innocenti.luca.com.surfacemines;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;


import com.github.anastr.speedviewlib.SpeedView;
import com.github.anastr.speedviewlib.Speedometer;

import innocenti.luca.com.surfacemines.variabili;



public class page1 extends Fragment implements LocationListener{

    private ScrollView scrolla;
    private int scattato;
    private Spinner g11;
    private Spinner g12;
    private Spinner g13;
    private Spinner g14;
    private Spinner g15;
    private Spinner g16;
    private Spinner g17;
    private Spinner g18;
    private Spinner g19;
    private Spinner m21;
    private Spinner m22;
    private Spinner m23;
    private Spinner m24;
    private Spinner b31;
    private Spinner b32;
    private Spinner b33;
    private Spinner b34;



    private int g11_score;
    private int g12_score;
    private int g13_score;
    private int g14_score;
    private int g15_score;
    private int g16_score;
    private int g17_score;
    private int g18_score;
    private int g19_score;
    private int m21_score;
    private int m22_score;
    private int m23_score;
    private int m24_score;
    private int b31_score;
    private int b32_score;
    private int b33_score;
    private int b34_score;


    private int g11_index;
    private int g12_index;
    private int g13_index;
    private int g14_index;
    private int g15_index;
    private int g16_index;
    private int g17_index;
    private int g18_index;
    private int g19_index;
    private int m21_index;
    private int m22_index;
    private int m23_index;
    private int m24_index;
    private int b31_index;
    private int b32_index;
    private int b33_index;
    private int b34_index;

    private EditText note;
    private EditText rischio;
    private EditText locationtxt;

    private String risk;
    boolean doubleBackToExitPressedOnce = false;
    private View rootView;
    private SpeedView speedometer;
    private EditText stoptxt;
    private double lat;
    private double lng;
    private EditText coordinatetxt;
    private LocationManager locationManager ;
    private Location location;


    @SuppressLint("MissingPermission")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.page1, container, false);
        int openRisk = Log.d("OpenRisk", "Page 1 create");
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,2000,2,this);



        return rootView;
    }



    private void getSystemService(String locationService) {
    }

    public void onViewCreated(View rootView, Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);
        final View v = rootView;

        Log.d("Open Risk", "OnViewCreated 1");


        note = (EditText) rootView.findViewById(R.id.editText2);
        rischio = (EditText) rootView.findViewById(R.id.editrisk);
        locationtxt = (EditText) rootView.findViewById(R.id.editmine);
        stoptxt = (EditText) rootView.findViewById(R.id.editstop);
        coordinatetxt = (EditText) rootView.findViewById(R.id.edits3);


        scrolla = (ScrollView) v.findViewById(R.id.scroller);


        g11 =  v.findViewById(R.id.spinner_g11);
        g12 =  rootView.findViewById(R.id.spinner_g12);
        g13 = (Spinner) rootView.findViewById(R.id.spinner_g13);
        g14 = (Spinner) rootView.findViewById(R.id.spinner_g14);
        g15 = (Spinner) rootView.findViewById(R.id.spinner_g15);
        g16 = (Spinner) rootView.findViewById(R.id.spinner_g16);
        g17 = (Spinner) rootView.findViewById(R.id.spinner_g17);
        g18 = (Spinner) rootView.findViewById(R.id.spinner_g18);
        g19 = (Spinner) rootView.findViewById(R.id.spinner_g19);
        m21 = (Spinner) rootView.findViewById(R.id.spinner_m21);
        m22 = (Spinner) rootView.findViewById(R.id.spinner_m22);
        m23 = (Spinner) rootView.findViewById(R.id.spinner_m23);
        m24 = (Spinner) rootView.findViewById(R.id.spinner_m24);
        b31 = (Spinner) rootView.findViewById(R.id.spinner_b31);
        b32 = (Spinner) rootView.findViewById(R.id.spinner_b32);
        b33 = (Spinner) rootView.findViewById(R.id.spinner_b33);
        b34 = (Spinner) rootView.findViewById(R.id.spinner_b34);


        speedometer = (SpeedView) rootView.findViewById(R.id.speedView);

        //speedometer.setWithTremble(false);
        speedometer.setLowSpeedPercent(33);
        speedometer.setMediumSpeedPercent(66);
        speedometer.setSpeedometerMode(Speedometer.Mode.TOP);
        speedometer.setUnit(" ");
        speedometer.setUnitTextColor(Color.WHITE);
        speedometer.setSpeedTextColor(Color.WHITE);
        speedometer.setSpeedAt(20);

        locationtxt.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                    salva_location();
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {


                Log.d("Photonotation", locationtxt.getText().toString());
            }
        });

        stoptxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                salva_location();
            }
        });

        // G11

        g11.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Photonotation","G11 " + Integer.toString(i));
                g11_index = i;
                switch(i){
                    case 0:
                        g11_score = 1;
                        break;
                    case 1:
                        g11_score = 10;
                        break;
                    case 2:
                        g11_score = 20;
                        break;
                }
                calcola();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // G12
        g12.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Photonotation","G12 "+ Integer.toString(i));
                g12_index = i;

                switch(i){
                    case 0:
                        g12_score = 1;
                        break;
                    case 1:
                        g12_score = 5;
                        break;
                    case 2:
                        g12_score = 10;
                        break;
                    case 3:
                        g12_score = 20;
                        break;
                }
                calcola();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // FINE G12

        // G13
        g13.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Photonotation","G13 "+ Integer.toString(i));
                g13_index = i;

                switch(i){
                    case 0:
                        g13_score = 1;
                        break;
                    case 1:
                        g13_score = 5;
                        break;
                    case 2:
                        g13_score = 10;
                        break;
                }
                calcola();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // FINE G13

        // G14
        g14.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Photonotation","G14 "+ Integer.toString(i));
                g14_index = i;

                switch(i){
                    case 0:
                        g14_score = 1;
                        break;
                    case 1:
                        g14_score = 5;
                        break;
                    case 2:
                        g14_score = 10;
                        break;
                }
                calcola();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // FINE G14

        // G15
        g15.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Photonotation","G15 "+ Integer.toString(i));
                g15_index = i;

                switch(i){
                    case 0:
                        g15_score = 0;
                        break;
                    case 1:
                        g15_score = 1;
                        break;
                    case 2:
                        g15_score = 3;
                        break;
                    case 3:
                        g15_score = 5;
                        break;
                    case 4:
                        g15_score = 10;
                        break;

                }
                calcola();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // FINE G15


        // G16
        g16.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Photonotation","G16 "+ Integer.toString(i));
                g16_index = i;

                switch(i){
                    case 0:
                        g16_score = 0;
                        break;
                    case 1:
                        g16_score = 1;
                        break;
                    case 2:
                        g16_score = 5;
                        break;
                    case 3:
                        g16_score = 10;
                        break;
                }
                calcola();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // FINE G16


        // G17
        g17.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Photonotation","G17 "+ Integer.toString(i));
                g17_index = i;

                switch(i){
                    case 0:
                        g17_score = 1;
                        break;
                    case 1:
                        g17_score = 10;
                        break;
                }
                calcola();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // FINE G17


        // G18
        g18.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Photonotation","G18 "+ Integer.toString(i));
                g18_index = i;

                switch(i){
                    case 0:
                        g18_score = 1;
                        break;
                    case 1:
                        g18_score = 20;
                        break;
                }
                calcola();
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // FINE G18


        // G19
        g19.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Photonotation","G19 "+ Integer.toString(i));
                g19_index = i;

                switch(i){
                    case 0:
                        g19_score = 1;
                        break;
                    case 1:
                        g19_score = 20;
                        break;
                }
                calcola();
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // FINE G19

        // M21
        m21.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Photonotation","M21 "+ Integer.toString(i));
                m21_index = i;

                switch(i){
                    case 0:
                        m21_score = 1;
                        break;
                    case 1:
                        m21_score = 20;
                        break;
                }
                calcola();
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // FINE M21

        // M22
        m22.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Photonotation","M22 "+ Integer.toString(i));
                m22_index = i;

                switch(i){
                    case 0:
                        m22_score = 1;
                        break;
                    case 1:
                        m22_score = 20;
                        break;
                }
                calcola();
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // FINE M22

        // M23
        m23.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Photonotation","M23 "+ Integer.toString(i));
                m23_index = i;

                switch(i){
                    case 0:
                        m23_score = 1;
                        break;
                    case 1:
                        m23_score = 10;
                        break;
                }
                calcola();
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // FINE M23

        // M24
        m24.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Photonotation","M24 "+ Integer.toString(i));
                m24_index = i;

                switch(i){
                    case 0:
                        m24_score = 1;
                        break;
                    case 1:
                        m24_score = 10;
                        break;
                }
                calcola();
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // FINE M24


        // B31
        b31.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Photonotation","B31 "+ Integer.toString(i));
                b31_index = i;

                switch(i){
                    case 0:
                        b31_score = 1;
                        break;
                    case 1:
                        b31_score = 10;
                        break;
                }
                calcola();
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // FINE B31


        // B32
        b32.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Photonotation","B32 "+ Integer.toString(i));
                b32_index = i;

                switch(i){
                    case 0:
                        b32_score = 1;
                        break;
                    case 1:
                        b32_score = 5;
                        break;
                    case 2:
                        b32_score = 10;
                        break;

                }
                calcola();
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // FINE B32

        // B33
        b33.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Photonotation","B33 "+ Integer.toString(i));
                b33_index = i;

                switch(i){
                    case 0:
                        b33_score = 0;
                        break;
                    case 1:
                        b33_score = 1;
                        break;
                    case 2:
                        b33_score = 10;
                        break;

                }
                calcola();
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // FINE B33

        // B33
        b34.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Photonotation","B34 "+ Integer.toString(i));
                b34_index = i;

                switch(i){
                    case 0:
                        b34_score = 1;
                        break;
                    case 1:
                        b34_score = 10;
                        break;
                }
                calcola();
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // FINE B33
    }

    protected  void salva_location()
    {
        SharedPreferences  sharedPref =  this.getActivity().getSharedPreferences("variabili",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("Mine", locationtxt.getText().toString());
        editor.putString("Stop", stoptxt.getText().toString());

        editor.commit();
    }

    protected void calcola()
    {
        ///calcola
        int w_g11;
        int w_g12;
        int w_g13;
        int w_g14;
        int w_g15;
        int w_g16;
        int w_g17;
        int w_g18;
        int w_g19;
        int w_m21;
        int w_m22;
        int w_m23;
        int w_m24;
        int w_b31;
        int w_b32;
        int w_b33;
        int w_b34;

        int max_g11;
        int max_g12;
        int max_g13;
        int max_g14;
        int max_g15;
        int max_g16;
        int max_g17;
        int max_g18;
        int max_g19;
        int max_m21;
        int max_m22;
        int max_m23;
        int max_m24;
        int max_b31;
        int max_b32;
        int max_b33;
        int max_b34;




        w_g11 = 20;
        w_g12 = 10;
        w_g13 = 10;
        w_g14 = 10;
        w_g15 = 1;
        w_g16 = 5;
        w_g17 = 1;
        w_g18 = 10;
        w_g19 = 10;
        w_m21 = 20;
        w_m22 = 20;
        w_m23 = 1;
        w_m24 = 1;
        w_b31 = 1;
        w_b32 = 1;
        w_b33 = 1;
        w_b34 = 10;

        max_g11 = 20;
        max_g12 = 20;
        max_g13 = 10;
        max_g14 = 10;
        max_g15 = 10;
        max_g16 = 10;
        max_g17 = 10;
        max_g18 = 20;
        max_g19 = 20;
        max_m21 = 20;
        max_m22 = 20;
        max_m23 = 10;
        max_m24 = 10;
        max_b31 = 10;
        max_b32 = 10;
        max_b33 = 10;
        max_b34 = 10;

        Log.d("Photonotation", "G11 "+ g11_score);
        Log.d("Photonotation", "G12 "+ g12_score);
        Log.d("Photonotation", "G13 "+ g13_score);
        Log.d("Photonotation", "G14 "+ g14_score);
        Log.d("Photonotation", "G15 "+ g15_score);
        Log.d("Photonotation", "G16 "+ g16_score);
        Log.d("Photonotation", "G17 "+ g17_score);
        Log.d("Photonotation", "G18 "+ g18_score);
        Log.d("Photonotation", "G19 "+ g19_score);

        Log.d("Photonotation", "M21 "+ m21_score);
        Log.d("Photonotation", "M22 "+ m22_score);
        Log.d("Photonotation", "M23 "+ m23_score);
        Log.d("Photonotation", "M24 "+ m24_score);

        Log.d("Photonotation", "B31 "+ b31_score);
        Log.d("Photonotation", "B32 "+ b32_score);
        Log.d("Photonotation", "B33 "+ b33_score);
        Log.d("Photonotation", "B34 "+ b34_score);

        SharedPreferences  sharedPref =  this.getActivity().getSharedPreferences("variabili",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("G11", g11_index);
        editor.putInt("G12", g12_index);
        editor.putInt("G13", g13_index);
        editor.putInt("G14", g14_index);
        editor.putInt("G15", g15_index);
        editor.putInt("G16", g16_index);
        editor.putInt("G17", g17_index);
        editor.putInt("G18", g18_index);
        editor.putInt("G19", g19_index);
        editor.putInt("M21", m21_index);
        editor.putInt("M22", m22_index);
        editor.putInt("M23", m23_index);
        editor.putInt("M24", m24_index);
        editor.putInt("B31", b31_index);
        editor.putInt("B32", b32_index);
        editor.putInt("B33", b33_index);
        editor.putInt("B34", b34_index);
        editor.putString("LAT",Double.toString(lat));
        editor.putString("LNG",Double.toString(lng));


        //editor.commit();


        int somma_c1 = (max_g11*w_g11)+(max_g12*w_g12)+(max_g13*w_g13)+(max_g14*w_g14)+(max_g15*w_g15)+(max_g16*w_g16)+(max_g17*w_g17)+(max_g18*w_g18)+(max_g19*w_g19)+(max_m21*w_m21)+(max_m22*w_m22)+(max_m23*w_m23)+(max_m24*w_m24)+(max_b31*w_b31)+(max_b32*w_b32)+(max_b33*w_b33)+(max_b34*w_b34);
        double somma_c2 = (w_g11*g11_score)+(w_g12*g12_score)+(w_g13*g13_score)+(w_g14*g14_score)+(w_g15*g15_score)+(w_g16*g16_score)+(w_g17*g17_score)+(w_g18*g18_score)+(w_g19*g19_score)+(w_m21*m21_score)+(w_m22*m22_score)+(w_m23*m23_score)+(w_m24*m24_score)+ (w_b31*b31_score)+(w_b32*b32_score)+(w_b32*b32_score)+(w_b33*b33_score)+(w_b34*b34_score);
        double Consequenses = somma_c2/somma_c1;
        Log.d("Photonotation", "Somma C1 "+ somma_c1);
        Log.d("Photonotation", "Somma C2 "+ somma_c2);

        Log.d("Photonotation", "Consequences "+ Consequenses);

        int somma_score = g11_score+g12_score+g13_score+g14_score+g15_score+g16_score+g17_score+g18_score+g19_score+m21_score+m22_score+m23_score+m24_score+b31_score+b32_score+b33_score+b34_score;
        double somma_max_score = max_g11+max_g12+max_g13+max_g14+max_g15+max_g16+max_g17+max_g18+max_g19+max_m21+max_m22+max_m23+max_m24+max_b31+max_b32+max_b33+max_b34;
        double Pof = somma_score/somma_max_score;
        Log.d("Photonotation", "Somma Score "+ somma_score);
        Log.d("Photonotation", "Somma Max Score "+ somma_max_score);



        Log.d("Photonotation", "Pof "+ Pof);



        Double y_low  = (-2.27*Pof)+1.23;
        Double y_high = (-2.38*Pof)+1.595;

        if (Consequenses < y_low)
        {
            risk = "Low Risk";
            rischio.setBackgroundColor(Color.parseColor("#00FF00"));
            speedometer.setSpeedAt(20);



        }
        if ((Consequenses>= y_low) && (Consequenses< y_high))
        {
            risk = "Medium Risk";
            rischio.setBackgroundColor(Color.parseColor("#FFFF00"));
            speedometer.setSpeedAt(50);


        }
        if (Consequenses >= y_high)
        {
            risk = "High Risk";
            rischio.setBackgroundColor(Color.parseColor("#FF0000"));
            speedometer.setSpeedAt(80);


        }
        Log.d("Photonotation", risk);
        rischio.setText(risk);

        editor.putString("risk1", risk);
        editor.commit();

    }


    private void clear_all()
    {
        note.setText("");
        rischio.setText("");
        locationtxt.setText("");
        g11.setSelection(0);
        g12.setSelection(0);
        g13.setSelection(0);
        g14.setSelection(0);
        g15.setSelection(0);
        g16.setSelection(0);
        g17.setSelection(0);
        g18.setSelection(0);
        g19.setSelection(0);
        m21.setSelection(0);
        m22.setSelection(0);
        m23.setSelection(0);
        m24.setSelection(0);
        b31.setSelection(0);
        b32.setSelection(0);
        b33.setSelection(0);
        b34.setSelection(0);

        scrolla.fullScroll(ScrollView.FOCUS_UP);


    }



    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lng = location.getLongitude();
        coordinatetxt.setText("A "+Double.toString(lng)+"/"+Double.toString(lat));

        Log.i("Location info: Lat", Double.toString(lat));
        Log.i("Location info: Lng", Double.toString(lng));
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
