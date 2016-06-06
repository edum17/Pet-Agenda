package com.example.german.controlmascotas;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.content.Intent;

/**
 * Created by German on 25/05/2016.
 */
public class NotificationActivity extends Activity {
    public static final int NOTIFICACION_ID=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_nueva_cita);
    }

    public void onClick(View v){
        displayNotification();
    }

    protected void displayNotification(){
        //Construccion de la accion del intent implicito
        Intent intent= new Intent(this, MainActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,0);

        //Construccion de la notificacion;
        NotificationCompat.Builder builder= new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.ic_notifications);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_notifications));
        builder.setContentTitle("Notificacion Basica");
        builder.setContentText("Momento para aprender mas sobre Android!");
        builder.setSubText("Toca para ver la documentacion acerca de Anndroid.");

        //Enviar la notificacion
        NotificationManager notificationManager= (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICACION_ID,builder.build());
    }

}
