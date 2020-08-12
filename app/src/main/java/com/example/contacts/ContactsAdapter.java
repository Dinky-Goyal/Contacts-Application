package com.example.contacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ContactsAdapter extends ArrayAdapter<Contact> { //type of array adapter is our custom class which we have of contacts

    private Context context;
    private List<Contact>contacts;

    public ContactsAdapter(Context context,List<Contact>list) {
        super(context,R.layout.row_layout,list); //we have to send something to the array adapter class
        this.context = context;
        this.contacts  = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //in our case the context would be contact list that wants to use this, so it will basically be referring to the contact list and get the
        //layout inflater service of that specific context....because our layout is on external layout but not on an activity so we need to get a
        //link to this external layout
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.row_layout,parent,false); // now we have got a connection to the layout

        TextView tvChar = convertView.findViewById(R.id.tvChar);
        TextView tvName = convertView.findViewById(R.id.tvName);
        TextView tvNumber = convertView.findViewById(R.id.tvNumber);

        tvChar.setText(contacts.get(position).getName().toUpperCase().charAt(0) + "");
        tvName.setText(contacts.get(position).getName());
        tvNumber.setText(contacts.get(position).getNumber());
        return convertView;
    }
}
