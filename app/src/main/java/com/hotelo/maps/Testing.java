package com.hotelo.maps;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.service.voice.AlwaysOnHotwordDetector;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.security.KeyStore;
import java.security.spec.PSSParameterSpec;

public class Testing extends AppCompatActivity {

    private static final int PERMISSION_FINE_LOCATION =9 ;
    private TextView longitude_view;
    private TextView latitude_view;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationServices locationServices;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);
        longitude_view = findViewById(R.id.tv_longiude);
        latitude_view = findViewById(R.id.tv_latitude);

        //settings of location
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(1000 * 10); //10 seconds
        locationRequest.setFastestInterval(1000 * 5);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        //getting last location
        for(int i=0 ; i<5; i++){
            getUserLocation();
        }





    }// onCreate Method ends

    //getting last location
    private void getUserLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            //permission has been granted
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    //location could be null in some rare cases
                    if (location != null) {
                        latitude_view.setText(String.valueOf(location.getLatitude()));
                        longitude_view.setText(String.valueOf(location.getLongitude()));

                    }else{
                        //location is null
                        latitude_view.setText("Not found");
                        longitude_view.setText("Not found");
                    }
                }
            });
        }else{
            //Request the permission
            //build.version.sdk_int : mobile device api
            //>=
            //build.version_codes.m : 23
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                //ask for permissions
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 12);
            }

        }
    }

}
