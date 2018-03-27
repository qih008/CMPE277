package com.example.qih008.mortgagecalculator;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private SharedPreferences mPrefs;

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
        if (mMap != null){
            for (Map.Entry entry : values.entrySet()) {
                String key = (String) entry.getKey();
                String tempStr = mPrefs.getString(key,"");
                String[] tempAry = tempStr.split(":");
                String snippet = "Type: " + tempAry[4] + "\n" + "Street Address:" + tempAry[0] + "\n" + "City: "
                        + tempAry[1] + "\n" + "Loan amount: " + tempAry[5] + "\n" + "APR: " + tempAry[6] + "\n" +
                        "Monthly payment: " + tempAry[7];
                LatLng home = getLocationFromAddress(this, key);
                mMap.addMarker(new MarkerOptions().position(home).title(key).snippet(snippet));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(home));
                mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                    @Override
                    public View getInfoWindow(Marker marker) {
                        return null;
                    }

                    @Override
                    public View getInfoContents(Marker marker) {
                        View info = getLayoutInflater().inflate(R.layout.info_window, null);
                        TextView t1 = info.findViewById(R.id.title);
                        TextView t2 = info.findViewById(R.id.detail);
                        t1.setText(marker.getTitle());
                        t2.setText((marker.getSnippet()));
                        return info;
                    }
                });
            }

        }


    }


    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }
}
