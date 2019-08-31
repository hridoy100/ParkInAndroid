package com.example.parkin.util;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkin.DB.CommunicateWithPhp;
import com.example.parkin.HomeActivity;
import com.example.parkin.Notification;
import com.example.parkin.R;

import java.util.ArrayList;
import android.os.Handler;

public class NotificationThread  extends FragmentActivity implements Runnable {
    String mobNo;
    private Thread t;
    private Handler handler;
    int min_count;
    public NotificationThread(String mobNo,Handler handler)
    {
        this.mobNo=mobNo;
        this.handler=handler;
    }
    public void run()
    {
        // Intent is triggered if the notification is selected
        // Build notification
        /*android.app.Notification not = new android.app.Notification.Builder(context)
                .setContentTitle("Notification")
                .setContentText("New email").setSmallIcon(R.drawable.common_google_signin_btn_icon_light_focused)
                .setContentIntent(pIntent).build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        not.flags |= android.app.Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, not);*/
        CommunicateWithPhp com=new CommunicateWithPhp();
        while(true)
        {
            ArrayList<Notification>notifications=com.getNotification2(mobNo);
            int notif_size=0;
            for(int i=0;i<notifications.size();i++)
            {
                if(notifications.get(i).getStatus().equals("no"))
                    notif_size++;
            }
            if(notif_size>min_count)
            {
                System.out.println("From thread: "+notif_size);
                Message msg=handler.obtainMessage();
                msg.what=1;
                msg.arg1=notif_size;
                handler.sendMessage(msg);
                break;
            }
        }
    }
    public void start(int min_count)
    {
        this.min_count=min_count;
        t = new Thread (this, "Notif thread");
        t.start ();
    }
}
