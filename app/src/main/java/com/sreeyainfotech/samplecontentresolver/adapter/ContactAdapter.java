package com.sreeyainfotech.samplecontentresolver.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sreeyainfotech.samplecontentresolver.R;
import com.sreeyainfotech.samplecontentresolver.model.ContactsModel;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {

    private List<ContactsModel> contactsList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.name);
            genre = (TextView) view.findViewById(R.id.number);
        }
    }


    public ContactAdapter(List<ContactsModel> moviesList) {
        this.contactsList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ContactsModel contactsModel = contactsList.get(position);
        holder.title.setText(contactsModel.getName());
        holder.genre.setText(contactsModel.getNumber());
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }
}