package com.example.samantha.androidclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private Socket socket;
Button bo;
String nombre="";

String ip="";
String Instruccion="";
String contrase;
    String res;
String enviar="";
    TextView contra;
    TextView texto;
    String hostName = "192.168.43.183";
    int portNumber = 4444;
EditText nom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
bo=(Button) findViewById(R.id.button);
nom=(EditText)findViewById(R.id.Nombre);
        contra=(EditText)findViewById(R.id.contra);
texto=(TextView) findViewById(R.id.txt);
bo.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        nombre=nom.getText().toString();
        contrase=contra.getText().toString();
        try {
            new Thread(new ClientThread()).start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    //    Toast.makeText(getApplicationContext(),"boton" , Toast.LENGTH_SHORT).show();

    }});
    }
    class ClientThread implements Runnable {


        ClientThread() throws IOException {
        }

        @Override
        public void run() {

            try {
                Socket kkSocket = new Socket(hostName, portNumber);
                PrintWriter out =
                       new PrintWriter(kkSocket.getOutputStream(), true);
               BufferedReader in =
                        new BufferedReader(
                                new InputStreamReader(kkSocket.getInputStream()));
               Instruccion="Verificacion";
               ip=kkSocket.getLocalAddress().toString();
enviar=Instruccion+";-;"+ip+";-;"+nombre+";-;"+contrase;
              out.println(enviar);
                while ((res = in.readLine()) != null) {
                    texto.setText(res.toString());
                }
kkSocket.close();

            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }

    }
    }











