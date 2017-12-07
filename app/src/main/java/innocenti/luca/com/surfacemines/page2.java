package innocenti.luca.com.surfacemines;

/**
 * Created by lucainnocenti on 20/11/17.
 */
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.github.anastr.speedviewlib.SpeedView;
import com.github.anastr.speedviewlib.Speedometer;

public class page2 extends Fragment {

    private View rootView;
    private SpeedView speedometer;
    private Spinner g112;
    private Spinner g122;
    private Spinner g132;
    private Spinner g142;
    private Spinner g152;
    private Spinner g162;
    private Spinner g172;
    private Spinner g182;
    private Spinner g192;
    private Spinner g202;
    private Spinner w21;
    private Spinner w22;
    private Spinner w23;
    private Spinner w24;
    private Spinner w25;
    private Spinner s31;
    private Spinner s32;
    private Spinner d41;

    private int g112_score;
    private int g122_score;
    private int g132_score;
    private int g142_score;
    private int g152_score;
    private int g162_score;
    private int g172_score;
    private int g182_score;
    private int g192_score;
    private int g202_score;
    private int w21_score;
    private int w22_score;
    private int w23_score;
    private int w24_score;
    private int w25_score;
    private int s31_score;
    private int s32_score;
    private int d41_score;
    private String risk;


    @Override
    public void setMenuVisibility(final boolean visible)
    {
        super.setMenuVisibility(visible);
        if (visible)
        {
            Log.d("OpenRisk", "La 2 e' visibile");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.page2, container, false);
        return rootView;
    }

    public void onViewCreated(View rootView, Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);
        Log.d("Open Risk", "OnViewCreated 2");
        final View v = rootView;

        g112 = (Spinner) v.findViewById(R.id.spinner_g112);
        g122 = (Spinner) v.findViewById(R.id.spinner_g122);
        g132 = (Spinner) v.findViewById(R.id.spinner_g132);
        g142 = (Spinner) v.findViewById(R.id.spinner_g142);
        g152 = (Spinner) v.findViewById(R.id.spinner_g152);
        g162 = (Spinner) v.findViewById(R.id.spinner_g162);
        g172 = (Spinner) v.findViewById(R.id.spinner_g172);
        g182 = (Spinner) v.findViewById(R.id.spinner_g182);
        g192 = (Spinner) v.findViewById(R.id.spinner_g192);
        g202 = (Spinner) v.findViewById(R.id.spinner_g202);
        w21 = (Spinner) v.findViewById(R.id.spinner_w21);
        w22 = (Spinner) v.findViewById(R.id.spinner_w22);
        w23 = (Spinner) v.findViewById(R.id.spinner_w23);
        w24 = (Spinner) v.findViewById(R.id.spinner_w24);
        w25 = (Spinner) v.findViewById(R.id.spinner_w25);
        s31 = (Spinner) v.findViewById(R.id.spinner_s31);
        s32 = (Spinner) v.findViewById(R.id.spinner_s32);
        d41 = (Spinner) v.findViewById(R.id.spinner_d41);



        speedometer = (SpeedView) v.findViewById(R.id.speedView);

        //speedometer.setWithTremble(false);
        speedometer.setLowSpeedPercent(33);
        speedometer.setMediumSpeedPercent(66);
        speedometer.setSpeedometerMode(Speedometer.Mode.TOP);
        speedometer.setUnit(" ");
        speedometer.setUnitTextColor(Color.WHITE);
        speedometer.setSpeedTextColor(Color.WHITE);
        speedometer.setSpeedAt(50);

        g112.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Photonotation", "G112 " + Integer.toString(i));
                switch (i) {
                    case 0:
                        g112_score = 1;
                        break;
                    case 1:
                        g112_score = 10;
                        break;
                    case 2:
                        g112_score = 20;
                        break;
                    case 3:
                        g112_score = 20;
                        break;

                }
                calcola();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        g122.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Photonotation", "G122 " + Integer.toString(i));
                switch (i) {
                    case 0:
                        g122_score = 1;
                        break;
                    case 1:
                        g122_score = 10;
                        break;
                    case 2:
                        g122_score = 10;
                        break;
                    case 3:
                        g122_score = 20;
                        break;

                }
                calcola();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        g132.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Photonotation", "G132 " + Integer.toString(i));
                switch (i) {
                    case 0:
                        g132_score = 1;
                        break;
                    case 1:
                        g132_score = 10;
                        break;
                    case 2:
                        g132_score = 20;
                        break;
                }
                calcola();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        g142.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Photonotation", "G142 " + Integer.toString(i));
                switch (i) {
                    case 0:
                        g142_score = 1;
                        break;
                    case 1:
                        g142_score = 20;
                        break;
                }
                calcola();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        g152.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Photonotation", "G152 " + Integer.toString(i));
                switch (i) {
                    case 0:
                        g152_score = 1;
                        break;
                    case 1:
                        g152_score = 10;
                        break;
                }
                calcola();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        g162.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Photonotation", "G162 " + Integer.toString(i));
                switch (i) {
                    case 0:
                        g162_score = 1;
                        break;
                    case 1:
                        g162_score = 10;
                        break;
                    case 2:
                        g162_score = 20;
                        break;

                }
                calcola();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        g172.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Photonotation", "G172 " + Integer.toString(i));
                switch (i) {
                    case 0:
                        g172_score = 1;
                        break;
                    case 1:
                        g172_score = 10;
                        break;
                }
                calcola();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        g182.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Photonotation", "G182 " + Integer.toString(i));
                switch (i) {
                    case 0:
                        g182_score = 1;
                        break;
                    case 1:
                        g182_score = 10;
                        break;
                }
                calcola();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        g192.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Photonotation", "G192 " + Integer.toString(i));
                switch (i) {
                    case 0:
                        g192_score = 1;
                        break;
                    case 1:
                        g192_score = 20;
                        break;
                }
                calcola();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        g202.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Photonotation", "G202 " + Integer.toString(i));
                switch (i) {
                    case 0:
                        g202_score = 1;
                        break;
                    case 1:
                        g202_score = 5;
                        break;
                    case 2:
                        g202_score = 10;
                        break;
                    case 3:
                        g202_score = 20;
                        break;

                }
                calcola();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        w21.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Photonotation", "W21 " + Integer.toString(i));
                switch (i) {
                    case 0:
                        w21_score = 1;
                        break;
                    case 1:
                        w21_score = 10;
                        break;
                }
                calcola();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        w22.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Photonotation", "W22 " + Integer.toString(i));
                switch (i) {
                    case 0:
                        w22_score = 1;
                        break;
                    case 1:
                        w22_score = 10;
                        break;
                }
                calcola();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        w23.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Photonotation", "W23 " + Integer.toString(i));
                switch (i) {
                    case 0:
                        w23_score = 1;
                        break;
                    case 1:
                        w23_score = 10;
                        break;
                }
                calcola();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        w24.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Photonotation", "W24 " + Integer.toString(i));
                switch (i) {
                    case 0:
                        w24_score = 1;
                        break;
                    case 1:
                        w24_score = 5;
                        break;
                    case 2:
                        w24_score = 10;
                        break;
                }
                calcola();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        w25.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Photonotation", "W25 " + Integer.toString(i));
                switch (i) {
                    case 0:
                        w25_score = 1;
                        break;
                    case 1:
                        w25_score = 5;
                        break;
                    case 2:
                        w25_score = 10;
                        break;
                }
                calcola();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        s31.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Photonotation", "S31 " + Integer.toString(i));
                switch (i) {
                    case 0:
                        s31_score = 1;
                        break;
                    case 1:
                        s31_score = 10;
                        break;

                }
                calcola();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        s32.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Photonotation", "S32 " + Integer.toString(i));
                switch (i) {
                    case 0:
                        s32_score = 1;
                        break;
                    case 1:
                        s32_score = 10;
                        break;

                }
                calcola();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        d41.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Photonotation", "D41 " + Integer.toString(i));
                switch (i) {
                    case 0:
                        d41_score = 0;
                        break;
                    case 1:
                        d41_score = 1;
                        break;
                    case 2:
                        d41_score = 5;
                        break;
                    case 3:
                        d41_score = 10;
                        break;

                }
                calcola();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //------------------------------------
    }

    protected void calcola()
    {

        int w_g112;
        int w_g122;
        int w_g132;
        int w_g142;
        int w_g152;
        int w_g162;
        int w_g172;
        int w_g182;
        int w_g192;
        int w_g202;
        int w_w21;
        int w_w22;
        int w_w23;
        int w_w24;
        int w_s31;
        int w_s32;
        int w_d41;

        int max_g112;
        int max_g122;
        int max_g132;
        int max_g142;
        int max_g152;
        int max_g162;
        int max_g172;
        int max_g182;
        int max_g192;
        int max_g202;
        int max_w21;
        int max_w22;
        int max_w23;
        int max_w24;
        int max_s31;
        int max_s32;
        int max_d41;


        w_g112 = 5;
        w_g122 = 20;
        w_g132 = 20;
        w_g142 = 20;
        w_g152 = 1;
        w_g162 = 20;
        w_g172 = 1;
        w_g182 = 10;
        w_g192 = 20;
        w_g202 = 10;
        w_w21 = 10;
        w_w22 = 1;
        w_w23 = 1;
        w_w24 = 5;
        w_s31 = 1;
        w_s32 = 1;
        w_d41 = 10;

        max_g112 = 20;
        max_g122 = 20;
        max_g132 = 20;
        max_g142 = 20;
        max_g152 = 10;
        max_g162 = 20;
        max_g172 = 10;
        max_g182 = 20;
        max_g192 = 10;
        max_g202 = 20;
        max_w21 = 10;
        max_w22 = 10;
        max_w23 = 10;
        max_w24 = 10;
        max_s31 = 10;
        max_s32 = 10;
        max_d41 = 10;

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("G112", g112_score);
        editor.putInt("G122", g122_score);
        editor.putInt("G132", g132_score);
        editor.putInt("G142", g142_score);
        editor.putInt("G152", g152_score);
        editor.putInt("G162", g162_score);
        editor.putInt("G172", g172_score);
        editor.putInt("G182", g182_score);
        editor.putInt("G192", g192_score);
        editor.putInt("G202", g202_score);

        editor.putInt("W21", w21_score);
        editor.putInt("W22", w22_score);
        editor.putInt("W23", w23_score);
        editor.putInt("W24", w24_score);
        editor.putInt("S31", s31_score);
        editor.putInt("S32", s32_score);
        editor.putInt("D41", d41_score);

        int somma_c1 = (max_g112*w_g112)+(max_g122*w_g122)+(max_g132*w_g132)+(max_g142*w_g142)+(max_g152*w_g152)+(max_g162*w_g162)+(max_g172*w_g172)+(max_g182*w_g182)+(max_g192*w_g192)+(max_g202*w_g202)+(max_w21*w_w21)+(max_w22*w_w22)+(max_w23*w_w23)+(max_w24*w_w24)+(max_s31*w_s31)+(max_s32*w_s32)+(max_d41*w_d41);
        double somma_c2 = (w_g112*g112_score)+(w_g122*g122_score)+(w_g132*g132_score)+(w_g142*g142_score)+(w_g152*g152_score)+(w_g162*g162_score)+(w_g172*g172_score)+(w_g182*g182_score)+(w_g192*g192_score)+(w_g202*g202_score)+(w_w21*w21_score)+(w_w22*w22_score)+(w_w23*w23_score)+(w_w24*w24_score)+ (w_s31*s31_score)+(w_s32*s32_score)+(w_d41*d41_score);
        double Consequenses = somma_c2/somma_c1;

        Log.d("Photonotation", "Somma C1 "+ somma_c1);
        Log.d("Photonotation", "Somma C2 "+ somma_c2);

        Log.d("Photonotation", "Consequences "+ Consequenses);

        int somma_score = g112_score+g122_score+g132_score+g142_score+g152_score+g162_score+g172_score+g182_score+g192_score+g202_score+w21_score+w22_score+w23_score+w24_score+s31_score+s32_score+d41_score;
        double somma_max_score = max_g112+max_g122+max_g132+max_g142+max_g152+max_g162+max_g172+max_g182+max_g192+max_g202+max_w21+max_w22+max_w23+max_w24+max_s31+max_s32+max_d41;
        double Pof = somma_score/somma_max_score;
        Log.d("Photonotation", "Somma Score "+ somma_score);
        Log.d("Photonotation", "Somma Max Score "+ somma_max_score);



        Log.d("Photonotation", "Pof "+ Pof);


        if ((Pof+(2.278*Consequenses)) < 1.23)
        {
            risk = "Good";
            speedometer.setSpeedAt(20);
        }

        if (((Pof+(2.278*Consequenses)) >= 1.23) && ((Pof+(2.278*Consequenses)) <1.55))
        {
            risk = "Moderate";
            speedometer.setSpeedAt(50);
        }

        if ((Pof+(2.278*Consequenses)) >= 1.55)
        {
            risk = "Poor";
            speedometer.setSpeedAt(80);
        }

        editor.putFloat("Pof_geotech", (float) Pof);
        editor.putFloat("Con_geotech", (float) Consequenses);



        editor.putString("risk2", risk);
        editor.commit();
    }
}
