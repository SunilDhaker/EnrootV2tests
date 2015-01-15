package com.enrootapp.v2.cameratest.tabs;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.enrootapp.v2.cameratest.R;

/**
 * Created by sdhaker on 15-01-2015.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private String[] mDataset;

    public MyAdapter() {

        String[] testData = {"testing card 1 ", "Testing card 2", "testing card 3"};
        mDataset = testData;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview, parent, false);

        ViewHolder vh = new ViewHolder(v);
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

        public ViewHolder(View v) {
            super(v);
            //infoText = (TextView)v.findViewById(R.id.info_text);
        }
    }
}