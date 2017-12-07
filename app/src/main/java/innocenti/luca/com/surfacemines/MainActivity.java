package innocenti.luca.com.surfacemines;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.SyncStateContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfAnnotation;
import com.itextpdf.text.pdf.PdfArray;
import com.itextpdf.text.pdf.PdfBorderArray;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfDestination;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.xmp.impl.Utils;
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
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
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
    private File card;
    private String mine,stop,datetime,latj,lngj;
    private ProgressBar spinner;

    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;
    private boolean doubleBackToExitPressedOnce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);

        //copyAssets();


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

                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY HH:mm");

                    card = Environment.getExternalStorageDirectory();
                    File f = new File(Environment.getExternalStorageDirectory().getPath(), "OpenRisk");

                    String percorso = f.getAbsolutePath() + "/" + file;

                    File mm = new File(percorso);
                    Log.d("OpenRisk Ora" , mm.toString() );

                    String modifica = sdf.format(mm.lastModified());

                    String mostra =file.substring(0,file.lastIndexOf('.'));
                    mostra = mostra.toUpperCase();
                    mostra = mostra.replaceAll("_","   ");
                    mostra = mostra + "\n" + modifica;
                    values.add(mostra);
                    Log.d("OpenRisk",file);
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
                filename = filename.toLowerCase();
                filename = filename.replaceAll("   ","_");
                filename =filename.substring(0,filename.lastIndexOf('\n'));

                filename = filename + ".json";
                Log.d("OpenRisk", "Luca "+ filename.toString());



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
            new MyTask().execute(1);

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
        else if(item.getTitle()=="Share") {
            new MyTaskShare().execute(1);
        }
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

        spinner.setVisibility(View.VISIBLE);



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
            mine = jObject.getString("MINE");
            stop = jObject.getString("STOP");
            datetime = jObject.getString("DATETIME");
            latj = jObject.getString("LAT");
            lngj = jObject.getString("LNG");


            //Log.d("OpenRisk","G11 : " + Integer.toString(g11));
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
        writer.setPdfVersion(PdfWriter.PDF_VERSION_1_5);


        //prende il modello del report da /res/raw
        PdfReader reader = new PdfReader(getResources().openRawResource(R.raw.report));
        reader.removeFields();
        reader.removeUnusedObjects();



        PdfImportedPage page = writer.getImportedPage(reader, 1);
        document.newPage();
        cb.addTemplate(page, 0, 0);


        // AGGIUNGI LE IMMAGINI

        // DIMENSIONI DI UN FOGLIO A4 595 x 842


        String path_img1 = pdfFolder.getAbsolutePath() + "/" + filename.substring(0,filename.lastIndexOf('.'))+ "_1.png";
        String path_img2 = pdfFolder.getAbsolutePath() + "/" + filename.substring(0,filename.lastIndexOf('.'))+ "_2.png";
        String path_img3 = pdfFolder.getAbsolutePath() + "/" + filename.substring(0,filename.lastIndexOf('.'))+ "_3.png";

        Log.d("OpenRisk","Pdf IMG1 Report  "+ path_img1);


        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(pdfFolder.getAbsolutePath() + "/" + filename.substring(0,filename.lastIndexOf('.'))+"a.pdf"),PdfWriter.VERSION_1_5);
        stamper.setRotateContents(false);
        // ABILITA LA COMPRESSIONE DEL PDF
        stamper.setFullCompression();
        PdfContentByte content = stamper.getOverContent(1);

        File f = new File(path_img1);
        if (f.exists()) {
            Bitmap bmp = BitmapFactory.decodeFile(f.getAbsolutePath());
            ByteArrayOutputStream stream3 = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 50, stream3);
            Image img1 = Image.getInstance(stream3.toByteArray());
            //img1.scaleAbsoluteHeight(100);
            //img1.scaleAbsoluteWidth(100);
            img1.scalePercent(50);
            img1.setAbsolutePosition(50, 20);
            content.addImage(img1);
        }

        File f2 = new File(path_img2);
        if (f2.exists()) {
            Bitmap bmp2 = BitmapFactory.decodeFile(f2.getAbsolutePath());
            ByteArrayOutputStream stream4 = new ByteArrayOutputStream();
            bmp2.compress(Bitmap.CompressFormat.PNG, 50, stream4);
            Image img2 = Image.getInstance(stream4.toByteArray());
            //img2.scaleAbsoluteHeight(100);
            //img2.scaleAbsoluteWidth(100);
            img2.scalePercent(50);
            img2.setAbsolutePosition(250, 20);
            content.addImage(img2);

        }


        File f3 = new File(path_img3);
        if (f3.exists()) {
            Bitmap bmp3 = BitmapFactory.decodeFile(f3.getAbsolutePath());
            ByteArrayOutputStream stream5 = new ByteArrayOutputStream();
            bmp3.compress(Bitmap.CompressFormat.PNG, 50, stream5);
            Image img3 = Image.getInstance(stream5.toByteArray());
            //img3.scaleAbsoluteHeight(100);
            //img3.scaleAbsoluteWidth(100);
            img3.scalePercent(50);
            img3.setAbsolutePosition(450, 20);
            content.addImage(img3);
        }

        Bitmap mappa = BitmapFactory.decodeResource(getBaseContext().getResources(),R.drawable.mappa);
        ByteArrayOutputStream stream7 = new ByteArrayOutputStream();

        mappa.compress(Bitmap.CompressFormat.PNG,50,stream7);
        Image map = Image.getInstance(stream7.toByteArray());
        //ind.scaleAbsoluteHeight(70);
        //ind.scaleAbsoluteWidth(100);
        map.scalePercent(10);
        map.setAbsolutePosition(150, 170);
        content.addImage(map);

        Bitmap indicatore = BitmapFactory.decodeResource(getBaseContext().getResources(),R.drawable.indicatore_verde);
        ByteArrayOutputStream stream6 = new ByteArrayOutputStream();

        indicatore.compress(Bitmap.CompressFormat.PNG,100,stream6);
        Image ind = Image.getInstance(stream6.toByteArray());
        ind.scalePercent(9,9);
        //ind.scaleAbsoluteHeight(70);
        //ind.scaleAbsoluteWidth(100);
        ind.setAbsolutePosition(190, 441);
        content.addImage(ind);


        // AGGIUNGE IL PUNTO ROSSO SULLA MAPPA
        Rectangle rect = new Rectangle(240,240,250,250);
        PdfAnnotation annotation = PdfAnnotation.createSquareCircle(stamper.getWriter(),rect,"Circle",false);
        annotation.setColor(BaseColor.RED);
        annotation.put(PdfName.IC,new PdfArray(new int[]{1,0,0}));
        stamper.addAnnotation(annotation,1);

        // AGGIUNGI IL TESTO
        ColumnText.showTextAligned(content,Element.ALIGN_LEFT,new Phrase(mine),210,783,0);


        ColumnText.showTextAligned(content,Element.ALIGN_LEFT,new Phrase("Low"),120,630,0);
        stamper.setFullCompression();

        //writer.setFullCompression();
        stamper.close();


        document.close();

        if(Build.VERSION.SDK_INT>=24){
            try{
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            }catch(Exception e){
                e.printStackTrace();
            }
        }


        String finale = pdfFolder.getAbsolutePath() + "/" + filename.substring(0,filename.lastIndexOf('.'))+"a.pdf";
        File report_finale = new File(finale);

        report.delete();


        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(report_finale), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);




    }



    private void report_share() throws FileNotFoundException,IOException, DocumentException {

        File card = Environment.getExternalStorageDirectory();
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "OpenRisk");
        String percorso2 = file.getAbsolutePath() + "/" +filename;
        String uriStringjson = percorso2 ;

        spinner.setVisibility(View.VISIBLE);



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
            mine = jObject.getString("MINE");
            stop = jObject.getString("STOP");
            datetime = jObject.getString("DATETIME");
            latj = jObject.getString("LAT");
            lngj = jObject.getString("LNG");


            //Log.d("OpenRisk","G11 : " + Integer.toString(g11));
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


        // AGGIUNGI LE IMMAGINI

        // DIMENSIONI DI UN FOGLIO A4 595 x 842


        String path_img1 = pdfFolder.getAbsolutePath() + "/" + filename.substring(0,filename.lastIndexOf('.'))+ "_1.png";
        String path_img2 = pdfFolder.getAbsolutePath() + "/" + filename.substring(0,filename.lastIndexOf('.'))+ "_2.png";
        String path_img3 = pdfFolder.getAbsolutePath() + "/" + filename.substring(0,filename.lastIndexOf('.'))+ "_3.png";

        Log.d("OpenRisk","Pdf IMG1 Report  "+ path_img1);


        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(pdfFolder.getAbsolutePath() + "/" + filename.substring(0,filename.lastIndexOf('.'))+"a.pdf"));
        stamper.setRotateContents(false);
        PdfContentByte content = stamper.getOverContent(1);

        File f = new File(path_img1);
        if (f.exists()) {
            Bitmap bmp = BitmapFactory.decodeFile(f.getAbsolutePath());
            ByteArrayOutputStream stream3 = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream3);
            Image img1 = Image.getInstance(stream3.toByteArray());
            //img1.scaleAbsoluteHeight(100);
            //img1.scaleAbsoluteWidth(100);
            img1.scalePercent(50);
            img1.setAbsolutePosition(50, 20);
            content.addImage(img1);
        }

        File f2 = new File(path_img2);
        if (f2.exists()) {
            Bitmap bmp2 = BitmapFactory.decodeFile(f2.getAbsolutePath());
            ByteArrayOutputStream stream4 = new ByteArrayOutputStream();
            bmp2.compress(Bitmap.CompressFormat.PNG, 100, stream4);
            Image img2 = Image.getInstance(stream4.toByteArray());
            //img2.scaleAbsoluteHeight(100);
            //img2.scaleAbsoluteWidth(100);
            img2.scalePercent(50);
            img2.setAbsolutePosition(250, 20);
            content.addImage(img2);

        }


        File f3 = new File(path_img3);
        if (f3.exists()) {
            Bitmap bmp3 = BitmapFactory.decodeFile(f3.getAbsolutePath());
            ByteArrayOutputStream stream5 = new ByteArrayOutputStream();
            bmp3.compress(Bitmap.CompressFormat.PNG, 100, stream5);
            Image img3 = Image.getInstance(stream5.toByteArray());
            //img3.scaleAbsoluteHeight(100);
            //img3.scaleAbsoluteWidth(100);
            img3.scalePercent(50);
            img3.setAbsolutePosition(450, 20);
            content.addImage(img3);
        }

        Bitmap mappa = BitmapFactory.decodeResource(getBaseContext().getResources(),R.drawable.mappa);
        ByteArrayOutputStream stream7 = new ByteArrayOutputStream();

        mappa.compress(Bitmap.CompressFormat.PNG,100,stream7);
        Image map = Image.getInstance(stream7.toByteArray());
        //ind.scaleAbsoluteHeight(70);
        //ind.scaleAbsoluteWidth(100);
        map.scalePercent(10);
        map.setAbsolutePosition(150, 170);
        content.addImage(map);

        Bitmap indicatore = BitmapFactory.decodeResource(getBaseContext().getResources(),R.drawable.indicatore_verde);
        ByteArrayOutputStream stream6 = new ByteArrayOutputStream();

        indicatore.compress(Bitmap.CompressFormat.PNG,100,stream6);
        Image ind = Image.getInstance(stream6.toByteArray());
        ind.scalePercent(9,9);
        //ind.scaleAbsoluteHeight(70);
        //ind.scaleAbsoluteWidth(100);
        ind.setAbsolutePosition(190, 441);
        content.addImage(ind);


        // AGGIUNGE IL PUNTO ROSSO SULLA MAPPA
        Rectangle rect = new Rectangle(240,240,250,250);
        PdfAnnotation annotation = PdfAnnotation.createSquareCircle(stamper.getWriter(),rect,"Circle",false);
        annotation.setColor(BaseColor.RED);
        annotation.put(PdfName.IC,new PdfArray(new int[]{1,0,0}));
        stamper.addAnnotation(annotation,1);

        // AGGIUNGI IL TESTO
        ColumnText.showTextAligned(content,Element.ALIGN_LEFT,new Phrase(mine),210,783,0);


        ColumnText.showTextAligned(content,Element.ALIGN_LEFT,new Phrase("Low"),120,630,0);
        stamper.close();


        document.close();

        if(Build.VERSION.SDK_INT>=24){
            try{
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            }catch(Exception e){
                e.printStackTrace();
            }
        }


        String finale = pdfFolder.getAbsolutePath() + "/" + filename.substring(0,filename.lastIndexOf('.'))+"a.pdf";
        File report_finale = new File(finale);

        report.delete();

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        Uri uri = Uri.fromFile(report_finale);
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
                Log.d("OpenRisk", "Cancella file :"+fj);
                //fj =  filename.substring(0,filename.lastIndexOf('.')) + ".json";
                String fp1 =  filename.substring(0,filename.lastIndexOf('.')) + "_1.png";
                String fp2 =  filename.substring(0,filename.lastIndexOf('.')) + "_2.png";
                String fp3 =  filename.substring(0,filename.lastIndexOf('.')) + "_3.png";
                String pdf =  filename.substring(0,filename.lastIndexOf('.')) + "a.pdf";


        Log.d("OpenRisk", "Nome file  FP1 "+ fp1 );

                if (path.endsWith(File.separator)) {
                    fj = path + fj;
                    fp1 = path + fp1;
                    fp2 = path + fp2;
                    fp3 = path + fp3;
                    pdf = path + pdf;

                } else {
                    fj = path + File.separator + fj;
                    fp1 = path + File.separator + fp1;
                    fp2 = path + File.separator + fp2;
                    pdf = path + File.separator + pdf;


                }

                Log.d("OpenRisk", "Nome file  FP1 "+ fp1 );

                file_json = new File(fj);
                file_jpg_1 = new File (fp1);
                file_jpg_2 = new File(fp2);
                file_jpg_3 = new File (fp3);
                final File file_pdf = new File(pdf);

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
                                file_pdf.delete();

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


    class MyTask extends AsyncTask<Integer, Integer, String> {
        @Override
        protected String doInBackground(Integer... params) {
            try {
                report();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (DocumentException e) {
                e.printStackTrace();
            }
            return "Task Completed.";
        }
        @Override
        protected void onPostExecute(String result) {
            spinner.setVisibility(View.GONE);
        }
        @Override
        protected void onPreExecute() {
            spinner.setVisibility(View.VISIBLE);
        }
        @Override
        protected void onProgressUpdate(Integer... values) {

        }
    }

    class MyTaskShare extends AsyncTask<Integer, Integer, String> {
        @Override
        protected String doInBackground(Integer... params) {
            try {
                report_share();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (DocumentException e) {
                e.printStackTrace();
            }
            return "Task Completed.";
        }
        @Override
        protected void onPostExecute(String result) {
            spinner.setVisibility(View.GONE);
        }
        @Override
        protected void onPreExecute() {
            spinner.setVisibility(View.VISIBLE);
        }
        @Override
        protected void onProgressUpdate(Integer... values) {

        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    //------------- COPY ASSETS TO SD

    private void copyAssets() {
        Log.d("OpenRisk","ASSETS");
        AssetManager assetManager = getAssets();
        String[] files = null;
        try {
            files = assetManager.list("");
        } catch (IOException e) {
            Log.e("tag", "Failed to get asset file list.", e);
        }
        if (files != null) for (String filename : files) {
            InputStream in = null;
            OutputStream out = null;
            try {
                in = assetManager.open(filename);
                card = Environment.getExternalStorageDirectory();
                File file = new File(Environment.getExternalStorageDirectory().getPath(), "OpenRisk/data/");
                if (!file.exists()) {
                    file.mkdirs();
                }
                File outFile = new File(file , filename);
                Log.d("OpenRisk", "Assets " +outFile);
                out = new FileOutputStream(outFile);
                copyFile(in, out);
            } catch(IOException e) {
                Log.e("tag", "Failed to copy asset file: " + filename, e);
            }
            finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        // NOOP
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        // NOOP
                    }
                }
            }
        }
    }
    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }
    // ------------------------
 
}
