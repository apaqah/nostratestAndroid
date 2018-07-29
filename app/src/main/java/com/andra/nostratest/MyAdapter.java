package com.andra.nostratest;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andra on 7/28/2018.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.DataViewHolder> {

    private List<Contact> contactList = new ArrayList<>();
    private PersonView.OnActionListener mOnActionListener;

    public void setOnActionListener(PersonView.OnActionListener onActionListener) {
        mOnActionListener = onActionListener;
    }

    public void setData(List<Contact> data) {
        this.contactList = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PersonView personView = PersonView_.build(parent.getContext());
        personView.setOnActionListener(mOnActionListener);
        return new DataViewHolder(personView);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        holder.getView().setDatas(contactList.get(position));
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class DataViewHolder extends RecyclerView.ViewHolder {

        private PersonView personView;

        public DataViewHolder(PersonView view) {
            super(view);
            personView = view;
        }

        public PersonView getView() {
            return personView;
        }
    }
}
