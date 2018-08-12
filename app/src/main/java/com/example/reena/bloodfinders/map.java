package com.example.reena.bloodfinders;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class map extends AppCompatActivity {
TextView name,phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        name=findViewById(R.id.name);
        phone=findViewById(R.id.ph);
        Donorinfo donorinfo=(Donorinfo)getIntent().getSerializableExtra("data");
//        name.setText(donorinfo.fullname);
//        phone.setText(donorinfo.mobilenumber);
    }
}
