package com.example.localnotification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button send;

    public final String CHANNEL_ID ="1";

    int counter =0;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        send = findViewById(R.id.button);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter+=1;
                send.setText(""+counter);
                if(counter==8)
                {
                    sendLocalNotification();
                }

            }
        });
    }

    @SuppressLint({"NewApi", "MissingPermission", "ResourceAsColor"})
    public void sendLocalNotification()
    {

        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent =PendingIntent.getActivity(this,0,intent,0);


        //this Intent for to get toast message
        Intent actionIntent = new Intent(this,Receivers.class);
        actionIntent.putExtra("toast","this is a notification message");
        PendingIntent actionPending = PendingIntent.getBroadcast(this,0,actionIntent,0);
        Notification.Action action = new Notification.Action.Builder(Icon.createWithResource(this
                ,R.drawable.baseline_notifications_active_24),"Toast message",actionPending).build();


        //this intent for dismiss the notification
        Intent dismissIntent = new Intent(this,ReceiversDismiss.class);
        PendingIntent dismissPending = PendingIntent.getBroadcast(this,0,dismissIntent,0);
        Notification.Action dismissaction = new Notification.Action.Builder(Icon.createWithResource(this
                ,R.drawable.baseline_notifications_active_24),"Dismiss",dismissPending).build();

        Bitmap icon = BitmapFactory.decodeResource(getResources(),R.drawable.tour);
        String text = getResources().getString(R.string.big_Text);

        @SuppressLint({"NewApi", "LocalSuppress"}) NotificationChannel channel = new NotificationChannel(CHANNEL_ID,"1"
        , NotificationManager.IMPORTANCE_DEFAULT);

        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);

        Notification.Builder builder = new Notification.Builder(MainActivity.this,CHANNEL_ID);
        builder.setSmallIcon(R.drawable.baseline_notifications_active_24)
                .setContentTitle("Hello USER")
                .setContentText("Thank you for seeing these notification \n you will daily update acitivities.")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .addAction(action)
                .addAction(dismissaction)
                .setColor(R.color.black)
                .setLargeIcon(icon)
                .setStyle(new Notification.BigTextStyle().bigText(text));
                //The below statement is used for showing image in the notification section with big image view
               // .setStyle(new Notification.BigPictureStyle().bigPicture(icon));

        NotificationManagerCompat compat = NotificationManagerCompat.from(MainActivity.this);
        compat.notify(1,builder.build());

    }
}