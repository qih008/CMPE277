package com.example.qih008.mortgagecalculator;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private DrawerLayout mDrawerLayout;

    private GoogleMap mMap;
    private SharedPreferences mPrefs;
    private Map<String, String> markers = new HashMap<>();
    private String key;
    private String[] tempAry;
    private String snippet;
    private Marker m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mPrefs = getSharedPreferences("my_prefs", MODE_PRIVATE);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        Map<String, ?> values = mPrefs.getAll();
        if (mMap != null) {
            for (Map.Entry entry : values.entrySet()) {
                key = (String) entry.getKey();
                Log.wtf("myWTF", key);
                String tempStr = mPrefs.getString(key, "");
                tempAry = tempStr.split(":");
                snippet = "Type: " + tempAry[4] + "\n" + "Street Address:" + tempAry[0] + "\n" + "City: "
                        + tempAry[1] + "\n" + "Loan amount: " + tempAry[5] + "\n" + "APR: " + tempAry[6] + "\n" +
                        "Monthly payment: " + tempAry[7];
                //LatLng home = getLocationFromAddress(this, key);
                LatLng home = getLocationFromAddress(this, tempAry[0]+", "+tempAry[1]+", "+tempAry[2]+", "+tempAry[3]);
//                Marker m = mMap.addMarker(new MarkerOptions().position(home).title(key).snippet(snippet));
                m = mMap.addMarker(new MarkerOptions().position(home));
                markers.put(m.getId(), key);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(home));


            }
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(final Marker marker) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MapsActivity.this);
                    key = markers.get(marker.getId());
                    String tempStr = mPrefs.getString(key, "");
                    tempAry = tempStr.split(":");
                    snippet = "Type: " + tempAry[4] + "\n" + "Street Address:" + tempAry[0] + "\n" + "City: "
                            + tempAry[1] + "\n" + "Loan amount: " + tempAry[5] + "\n" + "APR: " + tempAry[6] + "\n" +
                            "Monthly payment: " + tempAry[7];
                    dialog.setTitle(key)
                            .setMessage(snippet)
                            .setCancelable(false)
                            .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    marker.setVisible(false);
                                    markers.remove(marker.getId());
                                    mPrefs.edit().remove(key).commit();

                                    dialog.dismiss();
                                    Intent intent = new Intent(MapsActivity.this, MainActivity.class);

                                    intent.putExtra("PropertyPrice",tempAry[8]);
                                    intent.putExtra("Apr",tempAry[6]);
                                    intent.putExtra("DownPayment",tempAry[9]);
                                    intent.putExtra("MonthlyPayment",tempAry[7]);
                                    intent.putExtra("Type",tempAry[4]);
                                    intent.putExtra("Address",tempAry[0]);
                                    intent.putExtra("City",tempAry[1]);
                                    intent.putExtra("State",tempAry[2]);
                                    intent.putExtra("Zip",tempAry[3]);
                                    startActivity(intent);

                                }
                            })
                            .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    marker.setVisible(false);
                                    markers.remove(marker.getId());
                                    mPrefs.edit().remove(key).commit();
                                    dialog.dismiss();
                                }
                            })
                            .setNeutralButton("Cancel",
                                    new DialogInterface.OnClickListener()
                                    {
                                        public void onClick(DialogInterface dialog, int id)
                                        {
                                            dialog.dismiss();
                                        }
                                    })
                            .show();
                    return true;
                }
            });
        }


    }


    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context, Locale.US);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }

}
