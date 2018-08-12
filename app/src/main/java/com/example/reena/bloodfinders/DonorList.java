package com.example.reena.bloodfinders;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class DonorList extends AppCompatActivity {

    ArrayList<String> donorList;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    //    public static ArrayList<Donor> donorInfo;
    Button buttonMap;
    String fetchUrl = "http://192.168.0.105/a/select.php";
    AQuery aQuery;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_list);
        aQuery = new AQuery(this);
        listView = findViewById(R.id.listview);
        buttonMap = findViewById(R.id.mapShow);
     String bloodgroup=getIntent().getStringExtra("group");
   String city=getIntent().getStringExtra("city");
      //  fetchData();
    fetchData(bloodgroup,city);
    }
//    String bloodgroup,String city
    public void fetchData(String bloodgroup,String city ) {
//String bloodgroup="Apositive";
//String city="godawari";
      Log.i("checking",""+bloodgroup+city);
        aQuery.ajax(Dataholder.FetchUrl + "?Blood_Group=" + bloodgroup + "&City=" + city, JSONArray.class, new AjaxCallback<JSONArray>() {
            @Override
            public void callback(String url, JSONArray array, AjaxStatus status) {
                super.callback(url, array, status);
                Log.i("list", url + "list" + array);
                Toast.makeText(DonorList.this, "User !!!"+array, Toast.LENGTH_SHORT).show();
                try {
                    final ArrayList<Donorinfo> list = new ArrayList<>();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = null;
                        object = array.getJSONObject(i);
                        Donorinfo info = new Donorinfo();
                        info.id = object.getString("id");
                        info.fullname = object.getString("Full_Name");
                        info.city = object.getString("City");
                        info.bloodgroup = object.getString("Blood_Group");
                        info.mobilenumber = object.getString("Mobile_Number");
                        info.age = object.getString("Age");
                        info.weight = object.getString("Weight");
                        info.gender = object.getString("Gender");
                        info.lat = object.getString("lat");
                        info.lang = object.getString("lang");
                        Log.i("heeeee",""+info.lat);
                        list.add(info);
                    }
                    DonorListAdapter adapter = new DonorListAdapter(getApplicationContext(), list);
                    listView.setAdapter(adapter);
                    buttonMap.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                            intent.putExtra("data", list);
                            startActivity(intent);

                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

//
//
//        donorList = new ArrayList<>();
//       donorInfo = new ArrayList<>();
//        listView = (ListView) findViewById(R.id.list_donor);
//        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, donorList);
//        listView.setAdapter(arrayAdapter);
//
//        buttonMap = (Button) findViewById(R.id.Button_mapShow);
//        buttonMap.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(DonorList.this, DonorList.class));
//            }
//        });


//        DatabaseReference myRef = database.getReference("donors");
//        myRef.child(city).child(group).addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                Donor donor = dataSnapshot.getValue(Donor.class);
//                donorInfo.add(donor);
//                String donorInfo = donor.name + "   \n" + donor.contuctNumber;
//                donorList.add(donorInfo);
//                arrayAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }
}
