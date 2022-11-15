package com.example.hammadhanif.availabilityapplication.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.hammadhanif.availabilityapplication.R;
import com.kmincorp.smartlawcrm.Charges;
import com.kmincorp.smartlawcrm.County;

import java.util.List;

public class CountyAdapter  extends ArrayAdapter<County> {
    LayoutInflater flater;
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        return getView(position);
    }
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position);
    }
    private View getView(int position)
    {
        County county = getItem(position);
        View rowView = flater.inflate(R.layout.spinner_county_item,null,true);
        ((TextView)rowView.findViewById(R.id.tvCounty)).setText(county.getName());
        return rowView;
    }
    public CountyAdapter(Activity context, int resouceId, int textviewId, List<County> list){

        super(context,resouceId,textviewId, list);
        flater = context.getLayoutInflater();
    }
}
