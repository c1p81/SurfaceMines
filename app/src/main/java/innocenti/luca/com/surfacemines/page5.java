package innocenti.luca.com.surfacemines;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlayOptions;

import android.util.Log;


public class page5 extends SupportMapFragment implements OnMapReadyCallback {

    private static final String ARG_SECTION_NUMBER = "section_number";
    Context ctx;

    private GoogleMap mMap;
    LatLng punto = new LatLng(43.83333, 11.33333);


    @Override
    public void onResume() {
        super.onResume();

        Log.d("MyMap", "onResume");
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

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setMapToolbarEnabled(false);

        mMap.setMaxZoomPreference(17);
        mMap.setMinZoomPreference(7);

        mMap.getUiSettings().setMapToolbarEnabled(true);
        Log.d("OpenRisk", "ASSETS "+getContext().getAssets());

        mMap.addTileOverlay(new TileOverlayOptions().tileProvider(new CustomMapTileProvider()));


        mMap.addMarker(new MarkerOptions().position(punto).title("Firenze"));


        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(punto, 7));

        mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
    }
}

