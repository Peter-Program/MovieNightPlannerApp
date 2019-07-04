package com.example.movienightplanner;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.movienightplanner.Models.Event;
import com.example.movienightplanner.Models.SingletonEventsListModel;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Iterator;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMap.clear();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        setMapMarkersForEvents();
    }

    public void setMapMarkersForEvents() {
        boolean haveAtleastOne = false;
        mMap.clear();
        Log.i("codeRunner", "Find events");
        Iterator<Event> iterator =
                SingletonEventsListModel.getInstance().
                        getNearest3Events(SingletonEventsListModel.getInstance()
                                .getCurrentTime()).iterator();
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        while (iterator.hasNext()) {
            Event e = iterator.next();
            Log.i("codeRunner", "Nearest Event is: " + e.getTitle());

            MarkerOptions options = new MarkerOptions()
                    .title(e.getTitle())
                    .position(new LatLng(e.getLat(),e.getLong()));
            builder.include(options.getPosition());
            mMap.addMarker(options);
            haveAtleastOne = true;
        }
        if (haveAtleastOne) {
            LatLngBounds bounds = builder.build();
            int padding = 50; // offset from edges of the map in pixels
            CameraUpdate camera = CameraUpdateFactory.newLatLngBounds(bounds, padding);
            mMap.moveCamera(camera);
        } else {
            Toast.makeText(this, "No Events Available",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
