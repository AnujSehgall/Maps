package com.anuj.map;

import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import static com.anuj.map.R.id.map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private TrackGPS gps;
    double longitude,newLat,newLng;
    double latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);

        gps = new TrackGPS(MapsActivity.this);


        if(gps.canGetLocation()){


            longitude = gps.getLongitude();
            latitude = gps .getLatitude();

            Toast.makeText(getApplicationContext(),"Longitude:"+Double.toString(longitude)+"\nLatitude:"+Double.toString(latitude),Toast.LENGTH_SHORT).show();
        }
        else
        {

            gps.showSettingsAlert();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        /*SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    */

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

        Geocoder gc = new Geocoder(MapsActivity.this);

        List<android.location.Address> list = null;

        //LatLng latLng = marker.getPosition();



        try {
            list = gc.getFromLocation(latitude,longitude,1);
            newLat = latitude;
            newLng = longitude;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Toast.makeText(MapsActivity.this,newLat + " , " + newLng, Toast.LENGTH_SHORT).show();

        android.location.Address add =   list.get(0);
        String add1 = add.getAddressLine(1);
        String add2 = add.getAddressLine(2);
        /*marker.setTitle(add.getAddressLine(1));
        marker.setSnippet(add.getAddressLine(2));
        marker.showInfoWindow();
        */// Add a marker in Sydney and move the camera
        LatLng latLng= new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(latLng).title(add1).snippet(add2)).setVisible(true);

        //new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.mark));


        // Move the camera instantly to location with a zoom of 15.
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 22));

        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16), 2000, null);

        //mMap.addMarker(new MarkerOptions().position(latLng).title("Current Location"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }
}
