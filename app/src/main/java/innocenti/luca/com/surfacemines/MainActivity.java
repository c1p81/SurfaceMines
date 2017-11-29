package innocenti.luca.com.surfacemines;

import android.Manifest;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
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


import com.vistrav.ask.Ask;
import com.vistrav.ask.annotations.AskDenied;
import com.vistrav.ask.annotations.AskGranted;

import java.io.File;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Ask.on(this)
                .id(11)
                .forPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .withRationales("Permission request", "In order to use this app  you will need to grant the requested permissions")
                .go();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        path = "/storage/emulated/0/Photonotation/";
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
        //cancella i file
        /*lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                Log.d("Photonotation","Cancella");
                String filename = (String) getListAdapter().getItem(position);

                String senzaestensione = filename.substring(0,filename.lastIndexOf('.'));
                Log.d("filemanager", senzaestensione);

                String fj =  senzaestensione + ".json";
                String fp =  senzaestensione + ".jpg";

                if (path.endsWith(File.separator)) {
                    fj = path + fj;
                    fp = path + fp;
                } else {
                    fj = path + File.separator + fj;
                    fp = path + File.separator + fp;
                }

                file_json = new File(fj);
                file_jpg = new File (fp);

                Log.d("filemanager", fj);
                Log.d("filemanager", fp);



                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Delete this file?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                file_json.delete();
                                file_jpg.delete();
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
                return true;
            }
        });

        if (conta == 0) {
            Toast.makeText(this, "No files", Toast.LENGTH_LONG).show();
        }*/

    }



    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle(filename);
        menu.add(0, v.getId(), 0, "Report");
        menu.add(0, v.getId(), 0, "Delete");
    }
    @Override
    public boolean onContextItemSelected(MenuItem item){
        if(item.getTitle()=="Report"){
            Toast.makeText(getApplicationContext(),"Report",Toast.LENGTH_LONG).show();
        }
        else if(item.getTitle()=="Delete"){
            //Toast.makeText(getApplicationContext(),"sending sms code",Toast.LENGTH_LONG).show();
        }else{
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
}
