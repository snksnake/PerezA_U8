package org.example.permisosenmarshmallow;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mapa;
    LocationManager locationManager;
    int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;
    private int PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 100;
    MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = findViewById(R.id.toolbar);

        mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            showPermission();
        } else {
            getLocation();
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mapa = map;
        mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        MarkerOptions markerOptions = new MarkerOptions();
        MapsUtils mUtils = new MapsUtils();

        List<Address> add = mUtils.resolveAddress(this, "");
        if ((add != null) && (add.size() != 0)) {
            LatLng address = new LatLng(add.get(0).getLatitude(), add.get(0).getLongitude());

            markerOptions.position(address);
            CameraPosition camPos = new CameraPosition.Builder()
                    //.target(address)   //Centramos el mapa en Madrid
                    .target(address)   //Centramos el mapa en Madrid
                    .zoom(15)         //Establecemos el zoom en 19
                    .bearing(45)      //Establecemos la orientación con el noreste arriba
                    .tilt(70)         //Bajamos el punto de vista de la cámara 70 grados
                    .build();

            CameraUpdate camUpd3 =
                    CameraUpdateFactory.newCameraPosition(camPos);

            mapa.addMarker(markerOptions);
            mapa.getUiSettings().setMapToolbarEnabled(true);
            mapa.getUiSettings().setZoomControlsEnabled(true);
            mapa.animateCamera(camUpd3);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mapa != null) { //prevent crashing if the map doesn't exist yet (eg. on starting activity)
            mapa.clear();
        }
    }

    public void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            // Assume thisActivity is the current activity
            int permissionCheck = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION);
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);
            } else {
                Toast.makeText(this, "Permission in GPS", Toast.LENGTH_SHORT).show();
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        //locationText.setText("Current Location: " + location.getLatitude() + ", " + location.getLongitude());
        MapsUtils mapsUtils = new MapsUtils();
        List<Address> la = mapsUtils.resolveLongLat(this, location.getLatitude(), location.getLongitude());

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Internet not connected", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    private void showPermission() {
        // Should we show an explanation?
        //if (ActivityCompat.shouldShowRequestPermissionRationale(this,
        //        Manifest.permission.ACCESS_FINE_LOCATION)) {
        //} else {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        //}
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mapFragment.getMapAsync(this);
        }
    }

    public void onStop() {
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
        //do your stuff here
        super.onStop();
    }
}
