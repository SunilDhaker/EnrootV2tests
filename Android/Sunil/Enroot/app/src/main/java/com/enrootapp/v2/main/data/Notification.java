package com.enrootapp.v2.main.data;

import android.graphics.Bitmap;

/**
 * Created by rmuttineni on 15/01/2015.
 */
public class Notification {
    private String type;
    private String notifierName;
    private String notification;
    private Bitmap notifierPic;

    public Notification() {}

    public void setType(String type) {
        this.type = type;
    }

    public void setNotifierName(String notifierName) {
        this.notifierName = notifierName;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public void setNotifierPic(Bitmap notifierPic) {
        this.notifierPic = notifierPic;
    }

    public static class Builder {

        public static Notification notification;

        public Builder() {
            notification = new Notification();
        }

        public Builder setType(String type) {
            notification.type = type;
            return this;
        }

        public Builder setNotifierName(String notifierName) {
            notification.notifierName = notifierName;
            return this;
        }

        public Builder setNotification(String notify) {
            notification.notification = notify;
            return this;
        }

        public Builder setNotifierPic(Bitmap notifierPic) {
            notification.notifierPic = notifierPic;
            return this;
        }

        public Notification create() {
            return notification;
        }
    }
}
