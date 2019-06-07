package com.example.samantha.androidclient;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.content.Context;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

import static android.app.NotificationChannel.DEFAULT_CHANNEL_ID;

public class ServicioPrueba extends Service {
    private static final String TAG = ServicioPrueba.class.getSimpleName();
    public static final int NOTIFICATION_ID = 234;
        private Thread workerThread = null;


    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("Servicio", "Creating service");
    }



        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            Log.i("Servicio", "Entro A Hilo");
            super.onStartCommand(intent, flags, startId);
         /*   if(workerThread == null || !workerThread.isAlive()){
                workerThread = new Thread(new Runnable() {
                    public void run() {

                        try {
                            Log.i("Servicio", "Espera");
                            workerThread.wait(10000);
createNotification();
                            Log.i("Servicio", "Hola");


                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                workerThread.start();
                    }*/
        return START_STICKY;
                }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void createNotification(){

        Intent intent = new Intent(this, MainActivity.class);

        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
        taskStackBuilder.addParentStack(MainActivity.class);
        taskStackBuilder.addNextIntent(intent);

        PendingIntent pendingIntent = taskStackBuilder.
                getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("Sample Notification")
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(pendingIntent)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification);

    }


}
/*


 class KKMultiServerThread extends Thread {

    public  void not(){


    }

     private Socket socket = null;

     public KKMultiServerThread(Socket socket) {
         super("KKMultiServerThread");
         this.socket = socket;

     }

     public void run() {

         try (

                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                 BufferedReader in = new BufferedReader(
                         new InputStreamReader(
                                 socket.getInputStream()));) {
             String inputLine;
             Notification notificacion = new Notification(

                     R.drawable.ic_launcher_background,

                     "Creando Servicio de Música",

                     System.currentTimeMillis() );

             while ((inputLine = in.readLine()) != null) {




             }


         } catch (IOException e) {
             e.printStackTrace();
         }


     }
 }*/
