package innocenti.luca.com.surfacemines;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.vistrav.ask.Ask;
import com.vistrav.ask.annotations.AskDenied;
import com.vistrav.ask.annotations.AskGranted;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends ListActivity {


    private String path;
    private File file_jpg;
    private File file_json;
    private ListView lista;
    private String loc;
    private String lat;
    private String lng;
    private String rsk;
    private Activity activity;

    private static final String TAG = MainActivity.class.getSimpleName();
    private String filename;
    private File file_jpg_1;
    private File file_jpg_2;
    private File file_jpg_3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Ask.on(this)
                .id(11)
                .forPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA,Manifest.permission.ACCESS_FINE_LOCATION)
                .withRationales("Permission request", "In order to use this app  you will need to grant the requested permissions")
                .go();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences  sharedPref =  getSharedPreferences("variabili", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("Modifica", 0);
                editor.commit();

                Intent calcolo = new Intent(MainActivity.this,CalcoloActivity.class);
                startActivity(calcolo);
                //return false;
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        refresh_list();


    }

    private void setSupportActionBar(Toolbar toolbar) {
    }

    //optional
    @AskGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void fileAccessGranted(int id) {
        Log.i(TAG, "FILE  GRANTED");
    }

    //optional
    @AskDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void fileAccessDenied(int id) {
        Log.i(TAG, "FILE  DENiED");
    }


    @AskGranted(Manifest.permission.READ_EXTERNAL_STORAGE)
    private void refresh_list()
    {
        // Use the current directory as title
        path = "/storage/emulated/0/OpenRisk/";
        if (getIntent().hasExtra("path")) {
            path = getIntent().getStringExtra("path");
        }
        setTitle(path);

        // Read all files sorted into the values-array
        List values = new ArrayList();
        File dir = new File(path);
        if (!dir.canRead()) {
            setTitle(getTitle() + " (inaccessible)");
        }
        String[] list = dir.list();
        int conta = 0;
        if (list != null) {
            for (String file : list) {
                if (file.endsWith(".json"))
                {
                    conta = conta + 1;
                    values.add(file);
                }
            }
        }
        Collections.sort(values);

        // Put the data into the list
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_2, android.R.id.text1, values);
        setListAdapter(adapter);

        lista = getListView();

        registerForContextMenu(lista);
        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                filename = (String) getListAdapter().getItem(position);
                variabili var = new variabili();
                var.set_sito(filename);
                // questo e' per passare la variabile alle altre activity

                return false;
            }
            });


    }



    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle(filename);
        menu.add(0, v.getId(), 0, "Report");
        menu.add(0, v.getId(), 0, "Share");
        menu.add(0, v.getId(), 0, "Modify");
        menu.add(0, v.getId(), 0, "Delete");

    }
    @Override
    public boolean onContextItemSelected(MenuItem item){
        if(item.getTitle()=="Report"){
            try {
                try {
                    report();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(item.getTitle()=="Delete"){
            cancella();
        }
        else if(item.getTitle()=="Modify"){
            SharedPreferences  sharedPref =  getSharedPreferences("variabili", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("Modifica", 1);
            editor.commit();

            Intent calcolo = new Intent(MainActivity.this,CalcoloActivity.class);
            startActivity(calcolo);
        }
        else if(item.getTitle()=="Share"){
            try {
                try {
                    report_share();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }        }
        else{
            return false;
        }
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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


    private void report() throws FileNotFoundException,IOException, DocumentException {
        File card = Environment.getExternalStorageDirectory();
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "OpenRisk");
        String percorso2 = file.getAbsolutePath() + "/" +filename;
        String uriStringjson = percorso2 ;



        // LEGGE IL JSON
        File dati = new File(uriStringjson);
        FileInputStream stream = null;
        stream = new FileInputStream(dati);
        String jString = null;
        try {
            FileChannel fc = stream.getChannel();
            MappedByteBuffer bb = null;
            try {
                bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
            } catch (IOException e) {
                e.printStackTrace();
            }
            /* Instead of using default, pass in a decoder. */
            jString = Charset.defaultCharset().decode(bb).toString();
            Log.d("OpenRisk",jString);
        }
        finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // LEGGE IL CONTENUTO DEL FILE JSON
        Log.d("OpenRisk","Lettura JSON");
        JSONObject jObject = null;
        try {
            jObject = new JSONObject(jString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            int g11 = jObject.getInt("G11");
            int g12 = jObject.getInt("G12");
            int g13 = jObject.getInt("G13");
            int g14 = jObject.getInt("G14");
            int g15 = jObject.getInt("G15");

            Log.d("OpenRisk","G11 : " + Integer.toString(g11));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        // GENERA IL PDF
        File pdfFolder = new File(Environment.getExternalStorageDirectory().getPath(), "OpenRisk");
        if (!pdfFolder.exists()) {
            pdfFolder.mkdir();
        }

        String percorso = pdfFolder.getAbsolutePath() + "/" + filename.substring(0,filename.lastIndexOf('.'))+".pdf";
        File report = new File(percorso);
        FileOutputStream output = new FileOutputStream(percorso);


        Document document = new Document(PageSize.A4);
        PdfWriter writer =  PdfWriter.getInstance(document, output);
        document.open();
        PdfContentByte cb = writer.getDirectContent();

        //prende il modello del report da /res/raw
        PdfReader reader = new PdfReader(getResources().openRawResource(R.raw.report));
        PdfImportedPage page = writer.getImportedPage(reader, 1);
        document.newPage();
        cb.addTemplate(page, 0, 0);


        /*document.add(new Paragraph("Luca"));

        BaseFont bf = BaseFont.createFont(BaseFont.COURIER, BaseFont.CP1257, BaseFont.EMBEDDED);
        cb.saveState();
        cb.beginText();
        cb.moveText(100, 100);
        cb.setFontAndSize(bf, 12);
        cb.showText("Luca");
        cb.endText();
        cb.restoreState();*/

        String path_img1 = pdfFolder.getAbsolutePath() + "/" + filename.substring(0,filename.lastIndexOf('.'))+ "_1.png";
        Log.d("OpenRisk", "Pdf img1 " + path_img1);
        ///storage/emulated/0/OpenRisk/computer_mac_1.jpg
        InputStream inputStream = new BufferedInputStream(new FileInputStream(path_img1));

        Bitmap img1bmp = BitmapFactory.decodeStream(inputStream);

        ByteArrayOutputStream stream1 = new ByteArrayOutputStream();

        img1bmp.compress(Bitmap.CompressFormat.PNG,100,stream1);

        Image image = Image.getInstance(stream1.toByteArray());
        image.scaleToFit(100f,100f);
        image.setAbsolutePosition(10f,10f);
        document.add(image);

        document.close();

        if(Build.VERSION.SDK_INT>=24){
            try{
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(report), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);

        /*Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        Uri uri = Uri.fromFile(report);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.setType("application/pdf");
        startActivity(Intent.createChooser(shareIntent, "Share the report"));*/


    }



    private void report_share() throws FileNotFoundException,IOException, DocumentException {
        File card = Environment.getExternalStorageDirectory();
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "OpenRisk");
        String percorso2 = file.getAbsolutePath() + "/" +filename;
        String uriStringjson = percorso2 ;



        // LEGGE IL JSON
        File dati = new File(uriStringjson);
        FileInputStream stream = null;
        stream = new FileInputStream(dati);
        String jString = null;
        try {
            FileChannel fc = stream.getChannel();
            MappedByteBuffer bb = null;
            try {
                bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
            } catch (IOException e) {
                e.printStackTrace();
            }
            /* Instead of using default, pass in a decoder. */
            jString = Charset.defaultCharset().decode(bb).toString();
            Log.d("OpenRisk",jString);
        }
        finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // LEGGE IL CONTENUTO DEL FILE JSON
        Log.d("OpenRisk","Lettura JSON");
        JSONObject jObject = null;
        try {
            jObject = new JSONObject(jString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            int g11 = jObject.getInt("G11");
            int g12 = jObject.getInt("G12");
            int g13 = jObject.getInt("G13");
            int g14 = jObject.getInt("G14");
            int g15 = jObject.getInt("G15");


            Log.d("OpenRisk","G11 : " + Integer.toString(g11));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        // GENERA IL PDF
        File pdfFolder = new File(Environment.getExternalStorageDirectory().getPath(), "OpenRisk");
        if (!pdfFolder.exists()) {
            pdfFolder.mkdir();
        }

        String percorso = pdfFolder.getAbsolutePath() + "/" + filename.substring(0,filename.lastIndexOf('.'))+ ".pdf";
        File report = new File(percorso);
        FileOutputStream output = new FileOutputStream(percorso);


        Document document = new Document(PageSize.A4);
        PdfWriter writer =  PdfWriter.getInstance(document, output);
        document.open();
        PdfContentByte cb = writer.getDirectContent();

        //prende il modello del report da /res/raw
        PdfReader reader = new PdfReader(getResources().openRawResource(R.raw.report));
        PdfImportedPage page = writer.getImportedPage(reader, 1);
        document.newPage();
        cb.addTemplate(page, 0, 0);


        document.add(new Paragraph("Luca"));

        BaseFont bf = BaseFont.createFont(BaseFont.COURIER, BaseFont.CP1257, BaseFont.EMBEDDED);
        cb.saveState();
        cb.beginText();
        cb.moveText(100, 100);
        cb.setFontAndSize(bf, 12);
        cb.showText("Luca");
        cb.endText();
        cb.restoreState();


        String path_img1 = pdfFolder.getAbsolutePath() + "/" + filename.substring(0,filename.lastIndexOf('.'))+ "_1.jpg";

        Image image = Image.getInstance(path_img1);
        document.add(image);

        document.close();

        if(Build.VERSION.SDK_INT>=24){
            try{
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            }catch(Exception e){
                e.printStackTrace();
            }
        }


        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        Uri uri = Uri.fromFile(report);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.setType("application/pdf");
        startActivity(Intent.createChooser(shareIntent, "Share the report"));


    }

    @Override
    public void onResume()
    {
        super.onResume();
        refresh_list();
    }


    private void cancella()
    {


                String fj = filename;
                //String fj =  filename.substring(0,filename.lastIndexOf('.')) + ".json";
                String fp1 =  filename.substring(0,filename.lastIndexOf('.')) + "_1.jpg";
                String fp2 =  filename.substring(0,filename.lastIndexOf('.')) + "_2.jpg";
                String fp3 =  filename.substring(0,filename.lastIndexOf('.')) + "_3.jpg";
                Log.d("OpenRisk", "Nome file "+ fj + " " + fp1 );

                if (path.endsWith(File.separator)) {
                    fj = path + fj;
                    fp1 = path + fp1;
                } else {
                    fj = path + File.separator + fj;
                    fp1 = path + File.separator + fp1;
                    fp2 = path + File.separator + fp1;
                    fp3 = path + File.separator + fp1;
                }

                file_json = new File(fj);
                file_jpg_1 = new File (fp1);
                file_jpg_2 = new File(fp2);
                file_jpg_3 = new File (fp3);

                Log.d("filemanager", fj);
                Log.d("filemanager", fp1);



                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Delete "+filename+ "?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                file_json.delete();
                                file_jpg_1.delete();
                                file_jpg_2.delete();
                                file_jpg_3.delete();

                                refresh_list();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });
                builder.show();
            }
}
