package innocenti.luca.com.surfacemines;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Environment;

import android.os.Bundle;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlayOptions;

import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;


import static android.content.Context.MODE_PRIVATE;


public class page5 extends SupportMapFragment implements OnMapReadyCallback,LocationListener {

    private static final String ARG_SECTION_NUMBER = "section_number";
    Context ctx;

    private GoogleMap mMap;
    LatLng punto = new LatLng(43.83333, 11.33333);
    private String stop;
    private String mine;
    private File card;
    private double lat;
    private double lng;
    private LatLng punto_dinamico;
    private LocationManager locationManager;

    @SuppressLint("MissingPermission")
    public void onStart() {
        super.onStart();
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,1,this);

        SharedPreferences prefs = this.getActivity().getSharedPreferences("variabili", MODE_PRIVATE);


        mine = prefs.getString("Mine","");
        stop = prefs.getString("Stop","");

        Log.d("OpenRisk", "Miniera " + mine);
    }


    @Override
    public void onResume() {
        super.onResume();

        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {

        if (mMap == null) {

            Log.d("MyMap", "setUpMapIfNeeded");

            getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d("MyMap", "onMapReady");


        mMap = googleMap;
        setUpMap();
    }

    private void setUpMap() {


        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        // disabilita la toolbar
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.setMaxZoomPreference(18);
        mMap.setMinZoomPreference(2);
        // disabilita la rotazione della mappa

        mMap.getUiSettings().setRotateGesturesEnabled(false);

        mMap.getUiSettings().setMapToolbarEnabled(true);
        Log.d("OpenRisk", "ASSETS " + getContext().getAssets());

        mMap.addTileOverlay(new TileOverlayOptions().tileProvider(new CustomMapTileProvider()));


        mMap.addMarker(new MarkerOptions().position(punto).title("Firenze").icon(BitmapDescriptorFactory.fromResource(R.drawable.foto)).rotation((float) 45.0));

        punto_dinamico = new LatLng(lat,lng);
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(punto_dinamico),7);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(punto_dinamico));
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(punto_dinamico, 7));

        mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Log.d("OpenRisk", "Click sulla mappa");
                CaptureMapScreen();


            }
        });


    }

    @Override
    public void onLocationChanged(Location location)
    {
        lat = location.getLatitude();
        lng = location.getLongitude();
        setUpMap();
        //Place current location marker
        //LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        Log.d("OpenRisk", "Onlocationchanged " + Double.toString(lat) + "/" + Double.toString(lng));
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

    public void CaptureMapScreen() {
        GoogleMap.SnapshotReadyCallback callback = new GoogleMap.SnapshotReadyCallback() {
            Bitmap bitmap;

            @Override
            public void onSnapshotReady(Bitmap snapshot) {
                // TODO Auto-generated method stub
                bitmap = snapshot;
                try {
                    card = Environment.getExternalStorageDirectory();
                    File file = new File(Environment.getExternalStorageDirectory().getPath(), "OpenRisk");
                    String percorso = file.getAbsolutePath() + "/" + mine + "_" + stop + "_map.png";
                    Log.d("OpenRisk", percorso);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    FileOutputStream out = new FileOutputStream(percorso);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);

                    Toast.makeText(getActivity(),"Map saved", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        mMap.snapshot(callback);


    }
}

