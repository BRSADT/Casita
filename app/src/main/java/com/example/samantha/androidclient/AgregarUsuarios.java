package com.example.samantha.androidclient;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;

public class AgregarUsuarios extends AppCompatActivity {
EditText usuario,nombre,apellido,pass;
Button agregar;
datosUsuario us,usuarioAgregar;
String enviar="",Instruccion="",ip="",regresar="",res="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_usuarios);
        us= (datosUsuario) getIntent().getSerializableExtra("intUsuarios"); //OBTIENES datos del usuario;
        usuario=(EditText)findViewById(R.id.inUsuario);
        nombre=(EditText)findViewById(R.id.inNombre);
        apellido=(EditText)findViewById(R.id.inApellido);
        pass=(EditText)findViewById(R.id.inApellido);
        agregar=(Button)findViewById(R.id.btnAgregar);
        agregar.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {
               Instruccion="AgregarUsuarios";
            enviar=Instruccion+";"+ip+";"+usuario.getText()+";"+nombre.getText()+";"+apellido.getText()+";"+pass.getText();
               if(usuario.getText().length()>0&&pass.getText().length()>0&&nombre.getText().length()>0) { //se ingresara a la BDD Para verificar los campos
                   new AgregarUsuarios.HilitoAgregar().execute();
               }
            }
        });
    }

    private class HilitoAgregar extends AsyncTask<Void, Void, String> implements Serializable {
        Conexion con=new Conexion();
        @Override
        protected String doInBackground(Void... params) {
            Log.i("inicio", "inicio" );
            try {
                Socket kkSocket = new Socket(con.getHostName(),con.getPortNumber());
                PrintWriter out =
                        new PrintWriter(kkSocket.getOutputStream(), true);
                BufferedReader in =
                        new BufferedReader(
                                new InputStreamReader(kkSocket.getInputStream()));
                ip=kkSocket.getLocalAddress().toString();
                out.println(enviar);
                Log.i("aqui1", regresar);
                while((res = in.readLine()) != null) {
                    regresar=res;
                    kkSocket.close();
                }
                Log.i("aqui2", regresar );
            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return regresar;
        }

        @Override
        protected void onPostExecute(String textof) {
            Log.i("cadena", regresar);
            Toast.makeText(getApplicationContext(), regresar, Toast.LENGTH_SHORT).show();
        }
    }






}
