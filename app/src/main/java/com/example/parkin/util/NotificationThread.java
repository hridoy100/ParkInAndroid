package com.example.parkin.util;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkin.HomeActivity;
import com.example.parkin.R;

public class NotificationThread  extends FragmentActivity implements Runnable {
    Context context;
    private Thread t;
    public NotificationThread(Context c)
    {
        this.context = c;
    }
    public void run()
    {
        // Intent is triggered if the notification is selected
        Intent intent = new Intent(context, HomeActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), intent, 0);

        // Build notification
        android.app.Notification not = new android.app.Notification.Builder(context)
                .setContentTitle("Notification")
                .setContentText("New email").setSmallIcon(R.drawable.common_google_signin_btn_icon_light_focused)
                .setContentIntent(pIntent).build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        not.flags |= android.app.Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, not);
        Toast.makeText(context, "Please enter correct details", Toast.LENGTH_SHORT).show();
    }
    public void start()
    {
        t = new Thread (this, "Notif thread");
        t.start ();
    }
}
