package com.example.reena.bloodfinders;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;


public class DonorForm extends AppCompatActivity {

    FusedLocationProviderClient fusedLocationProviderClient;
    LocationCallback locationCallback;
    LocationRequest locationRequest;


    boolean req = false;
    Spinner cityChoice;
    Spinner groupChoice;
    EditText fullname, age, weight;
    EditText mobilenumber;
    TextView loc;
    RadioGroup group;
    CheckBox checkBox;
    Button Save, Cancel;
    ToggleButton GetLoc;
    ProgressBar progressBar;
    String fetchUrl = "http://192.168.0.105/a/update.php";

    String latitude, longitude;
    AQuery aQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_form);
        final GPS_service gps = new GPS_service();
        aQuery = new AQuery(this);
        cityChoice = (Spinner) findViewById(R.id.dropdownCity);
        String[] citis = new String[]{"godawari", "gwarko", "balkhu", "kumaripati", "koteshwor", "baisepati", "sadobato", "phulchowk", "kupondol", "dhobighat", "nakhu"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, citis);
        cityChoice.setAdapter(adapter);
        groupChoice = (Spinner) findViewById(R.id.dropdownGroup);
        String[] group = new String[]{"Opositive", "Onegative", "Apositive", "Bpositive", "Anegative", "Bnegative", "ABpositive", "ABnegative"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, group);
        groupChoice.setAdapter(adapter1);

        fullname = (EditText) findViewById(R.id.edt_name);
        mobilenumber = (EditText) findViewById(R.id.edt_mobileNumber);
        age = (EditText) findViewById(R.id.age);
        weight = (EditText) findViewById(R.id.weight);
        Cancel = (Button) findViewById(R.id.cancel);
        Save = (Button) findViewById(R.id.btn_saveDonor);
        loc = (TextView) findViewById(R.id.Loc);
        GetLoc = findViewById(R.id.getLoc);

        locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(8000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(DonorForm.this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {

                                  latitude = String.valueOf(location.getLatitude());
                                  longitude = String.valueOf(location.getLongitude());
                                loc.setText(latitude+" "+longitude);
                                Log.i("lol", latitude + " " + longitude);

                            }
                        }
                    });
        }


        locationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {

                if(locationResult == null){
                    return;
                }else{
                    for(Location location : locationResult.getLocations()){
                        String latitude = String.valueOf(location.getLatitude());
                        String longitude = String.valueOf(location.getLongitude());
                        loc.setText(latitude+" "+longitude);
                        Log.i("lol2",latitude + " " + longitude);
                    }
                }
            }
        };

        GetLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(GetLoc.isChecked()){
                    locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
                    req = true;
                    startlocationUpdate();
                }else{
                    locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
                    req = true;
                    stopLocationUpdate();
                }

            }
        });


        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = fullname.getText().toString();
                String city = cityChoice.getSelectedItem().toString();
                String group = groupChoice.getSelectedItem().toString();
                String mobile = mobilenumber.getText().toString();
                String ageno = age.getText().toString();
                String weightno = weight.getText().toString();
                String location1 = loc.getText().toString();
                Log.i("lol3",location1);



                RadioGroup gender = (RadioGroup) findViewById(R.id.group);
                final String gendervalue =
                        ((RadioButton) findViewById(gender.getCheckedRadioButtonId()))
                                .getText().toString();

//                "?Full_Name=" + name + "&City=" + city+ "&Blood_Group=" + group+ "&Mobile_Number=" + mobile+ "&Age=" + ageno+ "&Weight=" + weightno+
//                        "&Gender=" + gendervalue+ "&lat=" + latitude+ "&lang=" + longitude
//                final CheckBox checkBox = (CheckBox) findViewById(R.id.checkbox);
//                if (checkBox.isChecked()) {
//                    checkBox.setChecked(false);
//                }
                String lat = MainActivity.lat.toString();
                String lng = MainActivity.lng.toString();
                Map<String, Object> param = new HashMap<>();
                param.put("Full_Name", name);
                param.put("City", city);
                param.put("Blood_Group", group);
                param.put("Mobile_Number", mobile);
                param.put("Age", ageno);
                param.put("Weight", weightno);
                param.put("Gender", gendervalue);
                param.put("lat", latitude);
                param.put("lang", longitude);
                Log.i("testiiiiiiiiii", "" + name + city + group + mobile + ageno + weightno + gendervalue);
                aQuery.ajax(Dataholder.UpdateUrl+  "?Full_Name=" + name + "&City=" + city+ "&Blood_Group=" + group+ "&Mobile_Number=" + mobile+ "&Age=" + ageno+ "&Weight=" + weightno+
                        "&Gender=" + gendervalue+ "&lat=" + latitude+ "&lang=" + longitude, JSONArray.class, new AjaxCallback<JSONArray>() {
                    @Override
                    public void callback(String url, JSONArray array, AjaxStatus status) {
                        super.callback(url, array, status);
                        Log.i("response", "response:" + url);
                        Toast.makeText(DonorForm.this, "User Registered!!!" + array, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

            }
        });
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DonorForm.this, MainActivity.class);
                startActivity(intent);
            }

        });
    }
    public void startlocationUpdate() {
        Log.i("lol4","inside");
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();
        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        settingsClient.checkLocationSettings(locationSettingsRequest);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.i("lol5","inside");
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
        }
    }
    public void stopLocationUpdate() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(req) {
            startlocationUpdate();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdate();
    }
}
