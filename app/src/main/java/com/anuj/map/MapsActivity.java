package com.anuj.map;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import static com.anuj.map.R.id.map;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener {

    private GoogleMap mMap;
    private TrackGPS gps;
    double longitude, newLat, newLng;
    double latitude;
    Button lct;

    public LatLng newlatlng,latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel: 9742706888"));
                if (ActivityCompat.checkSelfPermission(MapsActivity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);


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

        newlatlng = new LatLng(13.119053, 77.578741);
        lct = (Button) findViewById(R.id.lcote);
        lct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOldMark(13.119053, 77.578741);
            }
        });


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
        String addressLine1 = add.getAddressLine(1);
        String addressLine2 = add.getAddressLine(2);
        /*marker.setTitle(add.getAddressLine(1));
        marker.setSnippet(add.getAddressLine(2));
        marker.showInfoWindow();
        */// Add a marker in Sydney and move the camera
        latLng= new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(latLng).title(addressLine1).snippet(addressLine2)).setVisible(true);

        //new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.mark));


        // Move the camera instantly to location with a zoom of 15.
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 22));

        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16), 2000, null);

        //mMap.addMarker(new MarkerOptions().position(latLng).title("Current Location"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));


    }

    private void addOldMark(double lat, double lng) {
        LatLng ll = new LatLng(lat, lng);

        Geocoder gc = new Geocoder(MapsActivity.this);

        List<android.location.Address> list = null;

        //LatLng latLng = marker.getPosition();



        try {
            list = gc.getFromLocation(13.119053, 77.578741,1);

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Toast.makeText(MapsActivity.this,newLat + " , " + newLng, Toast.LENGTH_SHORT).show();

        android.location.Address add =   list.get(0);
        String addressLine1 = add.getAddressLine(0);
        String addressLine2 = add.getAddressLine(2);
        mMap.addMarker(new MarkerOptions().position(ll).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)).title("Child's location ").snippet(addressLine1));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newlatlng, 22));

        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);


        float[] results = new float[1];
        Location.distanceBetween(latitude, longitude, 13.119053, 77.578741, results);
        float distance = results[0];

        double dist = (double) distance/1000;
        DecimalFormat f = new DecimalFormat("##.00");

        Toast.makeText(getApplicationContext(), "Distance to Travel is: "+f.format(dist)+" km",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
