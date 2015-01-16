package com.enrootapp.v2.main;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * Created by sdhaker on 15-01-2015.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private String[] mDataset;
    public  RecyclerView rclView ;
    public Context context ;

    public MyAdapter(RecyclerView rclView , Context context) {

        String[] testData = {"testing card 1 ", "Testing card 2", "testing card 3"};
        mDataset = testData;
        this.rclView = rclView ;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        CardView v = (CardView)LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview, parent, false);
        v.setMinimumHeight(rclView.getHeight());
        ViewHolder vh = new ViewHolder(v );
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // holder.infoText.setText(mDataset[position]);

    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }






    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView infoText;

        public ViewHolder(View v ) {
            super(v);
            infoText = (TextView)v.findViewById(R.id.cardview_impression);
           // infoText.setWidth;
        }
    }
}