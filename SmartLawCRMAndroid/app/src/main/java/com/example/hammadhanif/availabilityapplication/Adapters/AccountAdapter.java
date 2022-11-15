package com.example.hammadhanif.availabilityapplication.Adapters;

import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.hammadhanif.availabilityapplication.R;
import com.example.hammadhanif.availabilityapplication.Utilities.Common;
import com.kmincorp.smartlawcrm.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountAdapter  extends RecyclerView.Adapter<AccountAdapter.AccountViewHolder>{

    private final ArrayList<Account> accounts;
    private final TextView.OnClickListener clickListener;

    public AccountAdapter(ArrayList<Account> accounts,Button.OnClickListener buttonClickListener) {
        this.accounts = accounts;
        this.clickListener = buttonClickListener;
    }

    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v =  LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.accounts_recycler_view_item, viewGroup, false);
        return new AccountViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder accountViewHolder, int position) {
        accountViewHolder.tvName.setText(Common.fromHtml(this.accounts.get(position).getName()));
        accountViewHolder.tvName.setTag(R.string.accountIdKey,this.accounts.get(position).getId());
        accountViewHolder.tvName.setOnClickListener(this.clickListener);
        accountViewHolder.tvPhone.setText(this.accounts.get(position).getPhoneWork());
        Linkify.addLinks(accountViewHolder.tvPhone, Linkify.ALL);
        accountViewHolder.tvMatterType.setText(this.accounts.get(position).getPhoneOffice());
        Linkify.addLinks(accountViewHolder.tvMatterType, Linkify.ALL);
        //accountViewHolder.tvCallBackTime.setText(this.accounts.get(position).getCallBackTime());
    }

    @Override
    public int getItemCount() {
        return this.accounts.size();
    }
    class AccountViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvName;
        private final TextView tvPhone;
        private final TextView tvMatterType;
        private final TextView tvCallBackTime;
        public AccountViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvName.setPaintFlags( tvName.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvMatterType = itemView.findViewById(R.id.tvMatterType);
            tvCallBackTime = itemView.findViewById(R.id.tvCallBackTime);
        }
    }
}
