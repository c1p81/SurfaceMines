package innocenti.luca.com.surfacemines;

/**
 * Created by lucainnocenti on 20/11/17.
 */
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import innocenti.luca.com.surfacemines.variabili;

import static android.content.Context.MODE_PRIVATE;
import static java.lang.System.out;

public class page3 extends Fragment {

    private ImageView foto1;
    private ImageView foto2;
    private ImageView foto3;

    private Bitmap ff1;
    private Bitmap ff2;
    private Bitmap ff3;

    private Bitmap bm1;
    private Bitmap bm2;
    private Bitmap bm3;



    private View rootView;

    private static final int CAMERA_REQUEST1 = 1887;
    private static final int CAMERA_REQUEST2 = 1888;
    private static final int CAMERA_REQUEST3 = 1889;
    private File card;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.page3, container, false);


        return rootView;
    }

    public void onViewCreated(View rootView, Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);
        final View v = rootView;
        foto1 = (ImageView) v.findViewById(R.id.imageView);
        foto2 = (ImageView) v.findViewById(R.id.imageView2);
        foto3 = (ImageView) v.findViewById(R.id.imageView3);


        SharedPreferences prefs = this.getActivity().getSharedPreferences("variabili", MODE_PRIVATE);
        int modifica = prefs.getInt("Modifica",0);
        String mine = prefs.getString("Mine", "");
        String stop = prefs.getString("Stop", "");

        card = Environment.getExternalStorageDirectory();
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "OpenRisk");
        if (!file.exists()) {
            file.mkdirs();
        }
        //String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String percorso = file.getAbsolutePath() + "/" + mine + "_" + stop;

        if (modifica == 1)
        {
            File img1 = new File(percorso+"_1.png");
            if (img1.exists())
            {
                Bitmap img1bmp = BitmapFactory.decodeFile(img1.getAbsolutePath());
                foto1.setImageBitmap(img1bmp);
            }
            File img2 = new File(percorso+"_2.png");
            if (img2.exists())
            {
                Bitmap img2bmp = BitmapFactory.decodeFile(img2.getAbsolutePath());
                foto2.setImageBitmap(img2bmp);
            }
            File img3 = new File(percorso+"_3.png");
            if (img3.exists())
            {
                Bitmap img3bmp = BitmapFactory.decodeFile(img3.getAbsolutePath());
                foto3.setImageBitmap(img3bmp);
            }

        }


        //ff1 = get_foto1();

        foto1.setOnClickListener(new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View v)
                                    {
                                        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                        startActivityForResult(camera, CAMERA_REQUEST1);
                                    }
                                }
        );

        foto2.setOnClickListener(new View.OnClickListener()
                                 {
                                     @Override
                                     public void onClick(View v)
                                     {
                                         Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                         startActivityForResult(camera, CAMERA_REQUEST2);
                                     }
                                 }
        );

        foto3.setOnClickListener(new View.OnClickListener()
                                 {
                                     @Override
                                     public void onClick(View v)
                                     {
                                         Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                         startActivityForResult(camera, CAMERA_REQUEST3);
                                     }
                                 }
        );
    }

    public void onActivityResult(int requestCode,int resultCode, Intent data)
    {


        SharedPreferences prefs = this.getActivity().getSharedPreferences("variabili", MODE_PRIVATE);

        String mine = prefs.getString("Mine", "");
        String stop = prefs.getString("Stop", "");


        card = Environment.getExternalStorageDirectory();
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "OpenRisk");
        if (!file.exists()) {
            file.mkdirs();
        }
        //String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String percorso = file.getAbsolutePath() + "/" + mine + "_" + stop;
        String uriStringjpeg = percorso;

        if (requestCode== CAMERA_REQUEST1 && resultCode == Activity.RESULT_OK){
            ff1 = (Bitmap) data.getExtras().get("data");
            foto1.setImageBitmap(ff1);
            foto1.setImageURI(data.getData());
            bm1=((BitmapDrawable)foto1.getDrawable()).getBitmap();
            FileOutputStream out1 = null;
            try {
                out1 = new FileOutputStream(uriStringjpeg + "_1"+".png");
                bm1.compress(Bitmap.CompressFormat.PNG, 100, out1); // bmp is your Bitmap instance
                // PNG is a lossless format, the compression factor (100) is ignored
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out1 != null) {
                        out1.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (requestCode== CAMERA_REQUEST2 && resultCode == Activity.RESULT_OK){
            ff2 = (Bitmap) data.getExtras().get("data");
            foto2.setImageBitmap(ff2);
            foto2.setImageURI(data.getData());
            bm2=((BitmapDrawable)foto2.getDrawable()).getBitmap();
            FileOutputStream out2 = null;
            try {
                out2 = new FileOutputStream(uriStringjpeg + "_2"+".png");
                bm2.compress(Bitmap.CompressFormat.PNG, 100, out2); // bmp is your Bitmap instance
                // PNG is a lossless format, the compression factor (100) is ignored
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out2 != null) {
                        out2.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }

        if (requestCode== CAMERA_REQUEST3 && resultCode == Activity.RESULT_OK){
            ff3 = (Bitmap) data.getExtras().get("data");
            foto3.setImageBitmap(ff3);
            foto3.setImageURI(data.getData());
            bm3=((BitmapDrawable)foto1.getDrawable()).getBitmap();
            FileOutputStream out3 = null;
            try {
                out3 = new FileOutputStream(uriStringjpeg + "_3"+".png");
                bm3.compress(Bitmap.CompressFormat.PNG, 100, out3); // bmp is your Bitmap instance
                // PNG is a lossless format, the compression factor (100) is ignored
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out3 != null) {
                        out3.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}