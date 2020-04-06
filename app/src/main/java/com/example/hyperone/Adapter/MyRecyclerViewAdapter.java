package com.example.hyperone.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hyperone.Model.UserData;
import com.example.hyperone.R;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
    private ArrayList<UserData> mData = new ArrayList<>();
    private LayoutInflater mInflater;

    // data is passed into the constructor
    public MyRecyclerViewAdapter(Context context, ArrayList<UserData> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.myTextView.setText(mData.get(position).getData());

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setList(ArrayList<UserData> recipeModels) {
        mData = recipeModels;
        notifyDataSetChanged();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.data);
        }


    }

    // convenience method for getting data at click position


    // allows clicks events to be caught

}
