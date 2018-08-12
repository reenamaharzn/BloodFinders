package com.example.reena.bloodfinders;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    String fetchUrl = "http://1192.168.0.105/a/update.php";

    private GoogleMap mMap;
    AQuery aQuery;
    Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        aQuery = new AQuery(this);


//        builder.setItems(R.array.list, new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                // The 'which' argument contains the index position
//                // of the selected item
//            }
//        });
//        return builder.create();


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        Intent intent = getIntent();
        ArrayList<Donorinfo> list = (ArrayList<Donorinfo>) intent.getSerializableExtra("data");
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        for (int i = 0; i < list.size(); i++) {

            Donorinfo info = list.get(i);
            LatLng loc = new LatLng(Double.parseDouble(list.get(i).lat), Double.parseDouble(list.get(i).lang));
//            LatLng Donarinfo = new LatLng(loc);
//            String donorName = Donorinfo.name+ " " + Donorinfo.contuctNumber;
//            mMap.addMarker(new MarkerOptions().position(loc).title(donorName));
            mMap.addMarker(new MarkerOptions().position(loc).title(info.fullname).snippet(info.mobilenumber));


        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }
        mMap.setMyLocationEnabled(true);

//        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(Marker marker) {
//
//
//                return true;
//            }
//        });

//        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(LatLng latLng) {
//                fetchData(latLng.latitude+"",latLng.longitude+"");
//            }
//        });
        mMap.setMaxZoomPreference(14);
    }

    public void fetchData(String lat, String lang) {
        Log.i("chhhhhh", "");
//        String lat = "" + marker.getPosition().latitude;
//        String lang = "" + marker.getPosition().longitude;
        Map<String, Object> param = new HashMap<>();
        param.put("lat", "" + lat);
        param.put("lang", "" + lang);
        aQuery.ajax(Dataholder.getdataurl + "?lat=" + lat + "&lang=" + lang, JSONArray.class, new AjaxCallback<JSONArray>() {
            @Override
            public void callback(String url, JSONArray array, AjaxStatus status) {
                super.callback(url, array, status);
                try {
                    JSONObject object = array.getJSONObject(0);
                    Donorinfo info = new Donorinfo();
                    info.id = object.getString("id");
                    info.fullname = object.getString("Full_Name");
                    info.city = object.getString("City");
                    info.bloodgroup = object.getString("Blood_Group");
                    info.mobilenumber = object.getString("Mobile_Number");
                    info.age = object.getString("Age");
                    info.weight = object.getString("Weight");
                    info.gender = object.getString("Gender");
                    info.lat = object.getString("latitude");
                    info.lang = object.getString("longitude");

//                            final AlertDialog.Builder dialog = new AlertDialog.Builder(getApplicationContext());
//                            dialog.setMessage("name" + info.fullname);
//                            dialog.setMessage("contactno" + info.mobilenumber);
//                            Log.i("dialog", "response:" + array);
//                            dialog.show();


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }
}
