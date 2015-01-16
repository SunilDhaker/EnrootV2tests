package com.enrootapp.v2.main.tabs;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.enrootapp.v2.main.R;
import com.enrootapp.v2.main.data.Impression;
import com.enrootapp.v2.main.data.Notification;

import java.util.ArrayList;

/**
 * Created by sdhaker on 15-01-2015.
 */
public class TrailsAdapter extends RecyclerView.Adapter<TrailsAdapter.ViewHolder> {

    private ArrayList<Impression> mDataset;
     public TrailsAdapter() {
        mDataset = new ArrayList<Impression>();
        /*for (int i = 0; i < 10; i++) {
            int random = Math.random();
            Notification notification = new Notification.Builder()
                    .setType("Geofence")
                    .setNotification("");
            mDataset = new Noti
        }
        String[] testData = {"testing card 1 ", "Testing card 2", "testing card 3"};
        mDataset = testData;*/
    }

    @Override
    public TrailsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {

        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_card, parent, false), parent.getContext());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private Context context;
        private TextView notifierName;
        private TextView notification;
        private TextView timestamp;
        private ImageView notifierPic;

        public ViewHolder(View v, Context context) {
            super(v);
            this.context = context;

            notifierName = (TextView) v.findViewById(R.id.notifier_name);
            notification = (TextView) v.findViewById(R.id.notification);
            timestamp = (TextView) v.findViewById(R.id.timestamp);
            notifierPic = (ImageView) v.findViewById(R.id.notifier_pic);
            notifierName.setVisibility(View.GONE);
        }

        public void setNotifierName(String name) {
            notifierName.setVisibility(View.VISIBLE);
            notifierName.setText(name);
        }

        public void setNotification(String notify) {
            notification.setText(notify);
        }

        public void setTimestamp(String ts) {
            timestamp.setText(ts);
        }

        public void setNotifierPic(Bitmap pic) {
            notifierPic.setImageBitmap(pic);
        }
    }
}