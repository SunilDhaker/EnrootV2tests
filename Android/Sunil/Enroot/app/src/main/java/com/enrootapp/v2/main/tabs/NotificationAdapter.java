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
import com.enrootapp.v2.main.data.Notification;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by sdhaker on 15-01-2015.
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private ArrayList<Notification> mDataset;

    public NotificationAdapter() {
        mDataset = new ArrayList<Notification>();
        Random r = new Random(System.currentTimeMillis());
        for (int i = 0; i < 10; i++) {
            String[] names = {"Sunil Dhaker", "Rahul Muttineni"};
            String[] locations = {"Guwahati", "Delhi", "Mumbai", "Bangalore"};
            Notification.Builder builder = new Notification.Builder()
                    .setType((r.nextInt(1000) % 2 == 0) ? "Geofence" : "Private")
                    .setNotification("Notification " + r.nextInt(1000))
                    .setNotifierName(names[r.nextInt(2)])
                    .setLocation(locations[r.nextInt(4)]);
            mDataset.add(builder.create());
        }
    }

    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {

        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_card, parent, false), parent.getContext());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.render(mDataset.get(position));
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
        private TextView location;
        private ImageView notifierPic;

        public ViewHolder(View v, Context context) {
            super(v);
            this.context = context;

            notifierName = (TextView) v.findViewById(R.id.notifier_name);
            notification = (TextView) v.findViewById(R.id.notification);
            timestamp = (TextView) v.findViewById(R.id.timestamp);
            location = (TextView) v.findViewById(R.id.impression_location);
            notifierPic = (ImageView) v.findViewById(R.id.notifier_pic);
            notifierName.setVisibility(View.GONE);
        }

        public void render(Notification notification) {
            if (!notification.getType().equals("Geofence")) {
                notifierName.setVisibility(View.VISIBLE);
                notifierName.setText(notification.getNotifierName());
            }
            this.notification.setText(notification.getNotification());
            location.setText(notification.getLocation());
        }
    }
}