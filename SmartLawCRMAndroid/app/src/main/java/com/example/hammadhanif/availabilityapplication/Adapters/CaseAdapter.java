package com.example.hammadhanif.availabilityapplication.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.hammadhanif.availabilityapplication.R;
import com.example.hammadhanif.availabilityapplication.Utilities.Common;
import com.kmincorp.smartlawcrm.Account;
import com.kmincorp.smartlawcrm.CRMCase;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CaseAdapter extends RecyclerView.Adapter<CaseAdapter.CaseViewHolder>{
    private final ArrayList<CRMCase> cases;
    private final Button.OnClickListener buttonClickListener;
    private String buttonCaption;
    public CaseAdapter(ArrayList<CRMCase> cases,Button.OnClickListener buttonClickListener,String buttonCaption) {
        this.cases = cases;
        this.buttonClickListener = buttonClickListener;
        this.buttonCaption = buttonCaption;
    }

    @Override
    public int getItemCount() {
        return this.cases.size();
    }


    @NonNull
    @Override
    public CaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View v =  LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.case_recycler_view_item, viewGroup, false);
        return new CaseAdapter.CaseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CaseViewHolder caseViewHolder, int position) {

        caseViewHolder.tvName.setText(Common.fromHtml(this.cases.get(position).getAccount().getName()));
        caseViewHolder.tvPhone.setText(this.cases.get(position).getAccount().getPhoneWork());
        Linkify.addLinks(caseViewHolder.tvPhone, Linkify.ALL);
        caseViewHolder.tvNextCourtAppearence.setText(this.cases.get(position).getNextCourtAppearence());
        caseViewHolder.tvMatterType.setText(this.cases.get(position).getTypeOfCase());
        caseViewHolder.tvTypeOfCase.setText(this.cases.get(position).getMatterType());
        caseViewHolder.tvCounty.setText(this.cases.get(position).getCounty());
        caseViewHolder.tvCharges.setText( Common.fromHtml(   this.cases.get(position).getCharges()));
        caseViewHolder.tvConsultationNotes.setText( Common.fromHtml(   this.cases.get(position).getConsultationNotes()));
        caseViewHolder.tvCaseSynopsis.setText(Common.fromHtml(this.cases.get(position).getCaseSynopsis()));
        caseViewHolder.btnAssignToMe.setTag(R.string.caseIdKey,this.cases.get(position).getId());
        caseViewHolder.btnAssignToMe.setOnClickListener(buttonClickListener);
    }

    class CaseViewHolder extends RecyclerView.ViewHolder{

        private final TextView tvName;
        private final TextView tvPhone;
                private final TextView tvNextCourtAppearence;
        private final TextView tvMatterType;
        private final TextView tvTypeOfCase;
        private final TextView tvCounty;
        private final TextView tvCharges;

        private final TextView tvCaseSynopsis;
        private final TextView tvConsultationNotes;

        private final Button btnAssignToMe;

        public CaseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPhone = itemView.findViewById(R.id.tvPhoneNumber);
            //tvCaseMatterType = itemView.findViewById(R.id.tvCaseMatterType);
            tvConsultationNotes = itemView.findViewById(R.id.tvConsultationNotes);

            tvNextCourtAppearence = itemView.findViewById(R.id.tvNextCourtAppearence);
            tvMatterType = itemView.findViewById(R.id.tvMatterType);
            tvTypeOfCase = itemView.findViewById(R.id.tvTypeOfCase);
            tvCounty = itemView.findViewById(R.id.tvCounty);
            tvCharges = itemView.findViewById(R.id.tvCharges);

            tvCaseSynopsis = itemView.findViewById(R.id.tvCaseSynopsis);
            btnAssignToMe = itemView.findViewById(R.id.btnAssignToMe);
            btnAssignToMe.setText(buttonCaption);
        }
    }
}
