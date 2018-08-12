package com.example.reena.bloodfinders;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;

import java.util.ArrayList;

public class DonorListAdapter extends ArrayAdapter<Donorinfo> {
    Context context;
    AQuery aQuery;

    public DonorListAdapter(@NonNull Context context, ArrayList<Donorinfo> list) {
        super(context, 0, list);
        this.context = context;
        aQuery = new AQuery(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.listview, null);
        TextView fullname = view.findViewById(R.id.name);
        TextView mobilenumber = view.findViewById(R.id.ph);
        final Donorinfo info = getItem(position);
        fullname.setText(info.fullname);
        Toast.makeText(context, "fullname"+info.fullname, Toast.LENGTH_SHORT).show();
        fullname.setTextColor(Color.RED);
        mobilenumber.setText(info.mobilenumber);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DonorList.class);
                intent.putExtra("id", info.id);
                context.startActivity(intent);

            }
        });
        return view;
    }
}
