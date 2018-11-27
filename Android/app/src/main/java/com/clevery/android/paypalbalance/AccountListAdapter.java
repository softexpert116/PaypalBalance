package com.clevery.android.paypalbalance;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.clevery.android.paypalbalance.App.arrayList;

/**
 * Created by Acer on 01/12/2016.
 */

public class AccountListAdapter extends BaseAdapter
{
//    ArrayList<AccountModel> arrayList;
    MainActivity activity;

    AccountListAdapter() {
        arrayList = null;
        activity = null;
    }
    public AccountListAdapter(MainActivity _activity, ArrayList<AccountModel> _arrayList) {
        arrayList = _arrayList;
        activity = _activity;
    }
    @Override
    public int getCount() {

        if (arrayList == null)
            return 0;
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final AccountModel model = arrayList.get(i);
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(activity);
            view = inflater.inflate(R.layout.cell_account, null);
        }
        TextView txt_username = view.findViewById(R.id.txt_username);
        TextView txt_balance = view.findViewById(R.id.txt_balance);
        Button btn_remove = view.findViewById(R.id.btn_remove);

        txt_username.setText(model.username);
        if (model.balance.equals("Error!")) {
            txt_balance.setText(model.balance);
            txt_balance.setTextColor(activity.getColor(R.color.colorAccent));
        } else {
            txt_balance.setText(model.balance + " " + model.currency);
            txt_balance.setTextColor(Color.WHITE);
        }
        btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage("Do you want to remove this account?");
                builder.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        arrayList.remove(i);
                        App.setPreference_account_array(arrayList);
                        activity.refreshAccounts();
                        App.notifyWidgetUpdate();
                    }
                });
                builder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        return view;
    }
}
