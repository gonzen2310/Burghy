package com.burghy.burghy;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

/**
 * Created by gonza on 10/10/2017.
 */

public class Maps extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {
    private static final String TAG = Maps.class.getSimpleName();
    GoogleMap mGoogleMap;
    GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceSate) {
        super.onCreate(savedInstanceSate);
        setContentView(R.layout.activity_maps);
        initMap();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFragment);
        ;
        mapFragment.getMapAsync(this);

    }

    public void HallMarker() {
        //MacDonough Hall
        MarkerOptions MacDonough = new MarkerOptions()
                .title("MacDonough Hall")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.markerlogo))
                .position(new LatLng(44.693861, -73.462579));
        mGoogleMap.addMarker(MacDonough);
        //Harrington Hall
        MarkerOptions Harrington = new MarkerOptions()
                .title("Harrington Hall")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.markerlogo))
                .position(new LatLng(44.693693, -73.463668));
        mGoogleMap.addMarker(Harrington);
        //Macomb Hall
        MarkerOptions Macomb = new MarkerOptions()
                .title("MacDonough Hall")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.markerlogo))
                .position(new LatLng(44.6918342, -73.466248));
        mGoogleMap.addMarker(Macomb);

        //Kent Hall
        MarkerOptions Kent = new MarkerOptions()
                .title("Kent Hall")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.markerlogo))
                .position(new LatLng(44.691035, -73.466822));
        mGoogleMap.addMarker(Kent);
        //Mason Hall
        MarkerOptions Mason = new MarkerOptions()
                .title("Mason Hall")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.markerlogo))
                .position(new LatLng(44.690607, -73.467904));
        mGoogleMap.addMarker(Mason);
        //Hood Hall
        MarkerOptions Hood = new MarkerOptions()
                .title("Hood Hall")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.markerlogo))
                .position(new LatLng(44.690642, -73.468430));
        mGoogleMap.addMarker(Hood);
        //DeFredenburgh Hall
        MarkerOptions DeFredenburgh = new MarkerOptions()
                .title("DeFredenburgh Hall")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.markerlogo))
                .position(new LatLng(44.690157, -73.468602));
        mGoogleMap.addMarker(DeFredenburgh);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_json));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }
        mGoogleMap = googleMap;
        goToLocationZoom(44.6934,-73.467462, 15);

    }

    private void goToLocationZoom (double latitude, double longitude, float zoom) {
        LatLng ll = new LatLng(latitude, longitude);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        mGoogleMap.moveCamera(update);
        HallMarker();
    }

    public void geoLocate(View view) throws IOException {
        EditText place = (EditText) findViewById(R.id.editText);
        String location = place.getText().toString();

        Geocoder geo = new Geocoder(this);
        List<Address> list = geo.getFromLocationName(location, 1);
        Address address = list.get(0);
        String locality = address.getLocality();

        Toast.makeText(this, locality, Toast.LENGTH_LONG).show();

        double lat = address.getLatitude();
        double lng = address.getLongitude();
        goToLocationZoom(lat, lng, 18);
    }


    @Override
    public void onBackPressed(){
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.nav_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.action_settings){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onNavigationItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.home){
            Intent searchIntent = new Intent(Maps.this, Home.class);
            startActivity(searchIntent);
            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        }
        else if(id == R.id.campus){
            Intent searchIntent = new Intent(Maps.this, Campus.class);
            startActivity(searchIntent);
            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        }
        else if(id == R.id.maps){
            Intent searchIntent = new Intent(Maps.this, Maps.class);
            startActivity(searchIntent);
            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        }
        else if(id == R.id.facts) {
            Intent searchIntent = new Intent(Maps.this, Facts.class);
            startActivity(searchIntent);
            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        }
        else if(id == R.id.tips) {
            Intent searchIntent = new Intent(Maps.this, Tips.class);
            startActivity(searchIntent);
            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
