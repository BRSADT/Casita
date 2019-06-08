package com.example.samantha.androidclient;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
import android.content.Context;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.NotificationManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import static com.example.samantha.androidclient.APP.CHANNEL_1_ID;
import static com.example.samantha.androidclient.APP.CHANNEL_2_ID;

public class accederApp extends AppCompatActivity   implements Serializable  {
    private Socket socket;
    StringBuilder texto= new StringBuilder();
 //   String hostName = "192.168.43.183";
   //String hostName = "192.168.1.66";
    String hostName = "192.168.1.65";
    datosUsuario us;
    int portNumber = 4444;
    EditText inputUsuario,inputPassword;
    String textof="";
    Button btnacceder;
    String StrUsuario="",StrPassword="",Instruccion="",ip="",enviar="",res="";
    TextView prueba;
 Intent intent = new Intent(this,ServicioPrueba.class);
    ServicioPrueba serv;

    private NotificationManagerCompat notificationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceder_app);
        notificationManager = NotificationManagerCompat.from(this);
        startService(new Intent(accederApp.this, ServicioPrueba.class));
            us = new datosUsuario();
        btnacceder=(Button) findViewById(R.id.btnAcceder);
        inputUsuario=(EditText)findViewById(R.id.InputUsuario);
        inputPassword=(EditText)findViewById(R.id.InputPass);
        prueba=(TextView) findViewById(R.id.textView3);

        //Cuando el usuario da click
         btnacceder.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            Log.i("Servicio", "despues");

            StrUsuario=inputUsuario.getText().toString();
            StrPassword=inputPassword.getText().toString();
            if(StrUsuario.length()>0&&StrPassword.length()>0) { //se ingresara a la BDD Para verificar los campos
                new Hilito().execute();
            }
        }
    });





    }
    public void sendOnChannel1(View v) {
        String title = "AAHHHHH";
        String message = "El cielo se cae";

        Notification notification = new NotificationCompat.Builder(accederApp.this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.firee)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(1, notification);
    }


    private class Hilito extends AsyncTask<Void, Void, String> implements Serializable  {
        @Override
        protected String doInBackground(Void... params) {
            Conexion con=new Conexion();
             Log.i("inicio", "inicio" );
            try {
                Socket kkSocket = new Socket(con.getHostName(),con.getPortNumber());
                PrintWriter out =
                        new PrintWriter(kkSocket.getOutputStream(), true);
                BufferedReader in =
                        new BufferedReader(
                                new InputStreamReader(kkSocket.getInputStream()));
                Instruccion="Verificacion";
                ip=kkSocket.getLocalAddress().toString();
                enviar=Instruccion+";"+ip+";"+StrUsuario+";"+StrPassword;
                out.println(enviar);
                Log.i("aqui1", textof );
                while((res = in.readLine()) != null) { //Llega la informacion
                    textof=textof+res;
                    kkSocket.close();
                }
                Log.i("aqui2", textof );
            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return textof;
        }

        @Override
        protected void onPostExecute(String textof) {
            Log.i("cadena", textof);
            if (textof.length() <= 4) {
                Toast.makeText(getApplicationContext(), "Datos incorrectos", Toast.LENGTH_SHORT).show();

            } else {
                String[] datos = textof.split(";");
                us.setAdministrador(datos[3]);
                us.setUsuario(datos[0]);
                us.setNombre(datos[1]);
                us.setApellido(datos[2]);
                if (us.getAdministrador().equals("1")) {//aqui se ira al menu administrador

                    Intent i = new Intent(accederApp.this, menuPAdministrador.class);
                    i.putExtra("intUsuarios", (Serializable) us);
                    startActivity(i);
                } else {
                    Intent i = new Intent(accederApp.this, MenuPrincipal.class);
                    i.putExtra("intUsuarios", (Serializable) us);
                    startActivity(i);

                }
              }
        }
    }

}

