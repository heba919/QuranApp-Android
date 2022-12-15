package com.example.ahmedtawfik.lab05android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.TextView;


import java.util.ArrayList;


public class SuraAdapter extends ArrayAdapter<SuraInfo>{

    public SuraAdapter(Context context, ArrayList<SuraInfo> userArrayList){

    super(context,R.layout.sura_custom_item,userArrayList);

    }


    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {

        SuraInfo suraInfo = getItem(position);

        if (convertView == null){

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.sura_custom_item,parent,false);

        }

        TextView aya_number = convertView.findViewById(R.id.surah_number);
        TextView SuraName = convertView.findViewById(R.id.SuraName);
        TextView suratype = convertView.findViewById(R.id.suratype);
        TextView VersesNumder = convertView.findViewById(R.id.VersesNumder);

        aya_number.setText( String.valueOf(suraInfo.get_id()));
        SuraName.setText(suraInfo.getName());
        suratype.setText(suraInfo.getType());
        VersesNumder.setText( String.valueOf(suraInfo.getNum()));


        return convertView;
    }
}
