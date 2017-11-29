package innocenti.luca.com.surfacemines;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.JsonWriter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.WindowManager;
import android.widget.TextView;

import android.preference.PreferenceManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CalcoloActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private JsonWriter writer;
    private File card;
    private FileOutputStream out;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //lascia la tastiera abbassata
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());


        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(4);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences prefs = getSharedPreferences("variabili", MODE_PRIVATE);


                int g11 = prefs.getInt("G11",0);

                String mine = prefs.getString("Mine", "");
                String stop = prefs.getString("Stop", "");

                Log.d("Photonotation", stop);

                //INIZIA LA SCRITTURA DEL JSON
                card = Environment.getExternalStorageDirectory();
                File file = new File(Environment.getExternalStorageDirectory().getPath(), "OpenRisk");
                if (!file.exists()) {
                    file.mkdirs();
                }
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String percorso = file.getAbsolutePath() + "/" + timeStamp;
                String uriStringjson = percorso + ".json";
                //String uriStringjpeg = percorso + ".jpg";
                try {
                    out = new FileOutputStream(uriStringjson);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    writer = new JsonWriter(new OutputStreamWriter(out, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                try {
                    writer.beginObject();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {

                    writer.name("G11").value(Integer.toString(g11));
                    /*writer.name("G12").value(Integer.toString(g12));
                    writer.name("G13").value(Integer.toString(g13));
                    writer.name("G14").value(Integer.toString(g14));
                    writer.name("G15").value(Integer.toString(g15));
                    writer.name("G16").value(Integer.toString(g16));
                    writer.name("G17").value(Integer.toString(g17));
                    writer.name("G18").value(Integer.toString(g18));
                    writer.name("G19").value(Integer.toString(g19));
                    writer.name("M21").value(Integer.toString(m21));
                    writer.name("M22").value(Integer.toString(m22));
                    writer.name("M23").value(Integer.toString(m23));
                    writer.name("M24").value(Integer.toString(m24));
                    writer.name("B31").value(Integer.toString(b31));
                    writer.name("B32").value(Integer.toString(b32));
                    writer.name("B33").value(Integer.toString(b33));
                    writer.name("B34").value(Integer.toString(b34));*/


                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    writer.endObject();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Snackbar.make(view, "Saving data", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
//---------------------------------------

    //---------------------------------------


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    page1 page1 = new page1();
                    Log.d("OpenRisk","Pagina 1");
                    return page1;
                case 1:
                    page2 page2 = new page2();
                    Log.d("OpenRisk","Pagina  2");
                    return page2;
                case 2:
                    page3 page3 = new page3();
                    Log.d("OpenRisk","Pagina  3");
                    return page3;
                case 3:
                    page4 page4 = new page4();
                    Log.d("OpenRisk","Pagina  4");
                    return page4;
                default:
                    return null;
            }
            //return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
