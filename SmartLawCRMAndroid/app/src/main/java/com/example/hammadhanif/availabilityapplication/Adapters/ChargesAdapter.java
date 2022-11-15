package com.example.hammadhanif.availabilityapplication.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.hammadhanif.availabilityapplication.R;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;
import com.kmincorp.smartlawcrm.Charges;

import java.util.ArrayList;
import java.util.List;

public class ChargesAdapter  extends ArrayAdapter<com.kmincorp.smartlawcrm.Charges> {
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
        Charges charges = getItem(position);
        View rowView = flater.inflate(R.layout.spinner_charges_item,null,true);
        ((TextView)rowView.findViewById(R.id.tvCharges)).setText(charges.getName());
        return rowView;
    }
    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     *                 instantiating views.
     * @param objects  The objects to represent in the ListView.
     */
    public ChargesAdapter(Activity context,int resouceId, int textviewId, List<com.kmincorp.smartlawcrm.Charges> list){

        super(context,resouceId,textviewId, list);
        flater = context.getLayoutInflater();
    }

}
