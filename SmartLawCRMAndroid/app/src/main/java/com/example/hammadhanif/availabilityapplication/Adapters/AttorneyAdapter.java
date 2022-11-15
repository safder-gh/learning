package com.example.hammadhanif.availabilityapplication.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.hammadhanif.availabilityapplication.R;
import com.kmincorp.smartlawcrm.Session;
import com.kmincorp.smartlawcrm.Attorney;

import java.util.ArrayList;

public class AttorneyAdapter  extends RecyclerView.Adapter<AttorneyAdapter.AttorneyViewHolder>  {

    private final ArrayList<Attorney> attorneys;
    private final CompoundButton.OnCheckedChangeListener availabilityCheckedChangeListener;
    private final CompoundButton.OnCheckedChangeListener availabilityInOfficeCheckedChangeListener;
    private Session session;


    class AttorneyViewHolder extends RecyclerView.ViewHolder /*implements CompoundButton.OnCheckedChangeListener*/ {
        // each data item is just a string in this case
        private final TextView tvName;
        final Switch availabilitySwitch;
        final Switch availabilityInOfficeSwitch;
        private  AttorneyViewHolder(View v) {
            super(v);
            tvName = v.findViewById(R.id.tvName);
            availabilitySwitch = v.findViewById(R.id.availabilitySwitch);
            availabilityInOfficeSwitch = v.findViewById(R.id.availabilityInOfficeSwitch);
        }
    }

    public AttorneyAdapter(ArrayList<Attorney> attorneys,Session session, CompoundButton.OnCheckedChangeListener availabilityCheckedChangeListener, CompoundButton.OnCheckedChangeListener availabilityInOfficeCheckedChangeListener) {
        this.attorneys = attorneys;
        this.availabilityCheckedChangeListener = availabilityCheckedChangeListener;
        this.availabilityInOfficeCheckedChangeListener = availabilityInOfficeCheckedChangeListener;
        this.session = session;

    }

    @Override
    public AttorneyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.attorney_recycler_view_item, parent, false);
        return new AttorneyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AttorneyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.tvName.setText(this.attorneys.get(position).getName());
        holder.availabilitySwitch.setChecked(this.attorneys.get(position).getAvailable());

        holder.availabilityInOfficeSwitch.setChecked(this.attorneys.get(position).getAvailableInOffice());
        if(!this.session.getAdmin()){
            if(this.session.getUserId().equalsIgnoreCase(this.attorneys.get(position).getUserId())){
                holder.availabilitySwitch.setTag(R.string.attorneyIdKey,this.attorneys.get(position).getId());
                holder.availabilitySwitch.setOnCheckedChangeListener(availabilityCheckedChangeListener);

                holder.availabilityInOfficeSwitch.setTag(R.string.attorneyIdKey,this.attorneys.get(position).getId());
                holder.availabilityInOfficeSwitch.setOnCheckedChangeListener(availabilityInOfficeCheckedChangeListener);
            }
            else {
                holder.availabilitySwitch.setEnabled(false);
                holder.availabilityInOfficeSwitch.setEnabled(false);
            }
        }
        else{
            holder.availabilitySwitch.setTag(R.string.attorneyIdKey,this.attorneys.get(position).getId());
            holder.availabilitySwitch.setOnCheckedChangeListener(availabilityCheckedChangeListener);

            holder.availabilityInOfficeSwitch.setTag(R.string.attorneyIdKey,this.attorneys.get(position).getId());
            holder.availabilityInOfficeSwitch.setOnCheckedChangeListener(availabilityInOfficeCheckedChangeListener);
        }

    }
    @Override
    public int getItemCount() {
        return this.attorneys.size();
    }
}
