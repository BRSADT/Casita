package com.example.samantha.androidclient;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.content.Context;
import android.view.View;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static android.app.NotificationChannel.DEFAULT_CHANNEL_ID;
import static com.example.samantha.androidclient.APP.CHANNEL_1_ID;
import static com.example.samantha.androidclient.APP.CHANNEL_2_ID;

public class ServicioPrueba extends Service {
    private static final int PORT = 6666;
    private ServerSocket server = null;
    int portNumber = 5234;

    boolean listening = true;

    private ExecutorService mExecutorService = null; // thread pool
    private static final String TAG = ServicioPrueba.class.getSimpleName();

        private Thread workerThread = null;
    private NotificationManagerCompat notificationManager;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("Servicio", "Creating service");
        if (Build.VERSION.SDK_INT >= 26) {
            String CHANNEL_ID = "my_channel_01";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);

            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("")
                    .setContentText("").build();

            startForeground(1, notification);
        }


    }





    @Override
        public int onStartCommand(Intent intent, int flags, int startId) {

            Log.i("Servicio", "Entro A Hilo");


            super.onStartCommand(intent, flags, startId);
        new Thread(new Runnable() {

            @Override

            public void run() {

                try (ServerSocket serverSocket = new ServerSocket(portNumber))
                {
                    while (listening) {


                        new KKMultiServerThread(serverSocket.accept()).start();
                        Log.i("Servicio","entro un cliente");
                        notificationManager = NotificationManagerCompat.from(ServicioPrueba.this);
                        sendOnChannel1();
                    }
                } catch (IOException e) {
                    System.err.println("Could not listen on port " + portNumber);

                }

            }

        }).start();






        return START_STICKY;
                }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    public void sendOnChannel1() {
        String title = "AAHHHHH";
        String message = "El cielo se cae";

        Notification notification = new NotificationCompat.Builder(ServicioPrueba.this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.firee)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(30, notification);
    }

}


 class KKMultiServerThread extends Thread {



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
 }







    /**

     * Send message to clients

     */




