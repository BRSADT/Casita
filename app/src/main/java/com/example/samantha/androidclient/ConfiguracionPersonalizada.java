package com.example.samantha.androidclient;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class ConfiguracionPersonalizada extends AppCompatActivity implements Serializable  {
Switch ventilador,cocina,sala,foco1,foco2,focoCuarto2;
Button guardar;
    String enviar = "", Instruccion = "", ip = "", regresar = "", res = "";

    Spinner spin;
    ArrayAdapter adapterTono;
    List listaTonos;
    datosUsuario us;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion_personalizada);

        us = (datosUsuario) getIntent().getSerializableExtra("intUsuarios"); //OBTIENES datos del usuario;
       spin = (Spinner) findViewById(R.id.spinnerTonos);
        listaTonos = new ArrayList();
        listaTonos.add("Tono1");
        listaTonos.add("Tono2");
        listaTonos.add("Tono3");
        adapterTono = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaTonos);
        spin.setAdapter(adapterTono);
        spin.setSelection(0);
        Log.i("aquillego","Hola1");
        ventilador=(Switch)findViewById(R.id.swVentilador);
        cocina=(Switch)findViewById(R.id.swCocina);
        sala=(Switch)findViewById(R.id.swSala);
        Log.i("aquillego","Hola2");
        foco1=(Switch)findViewById(R.id.swFoco1);
        foco2=(Switch)findViewById(R.id.swFoco2);
        focoCuarto2=(Switch)findViewById(R.id.swFocoCuarto2);
        guardar=(Button) findViewById(R.id.btnGuardarConf);
Log.i("aquillego","Hola");
guardar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        enviar="Configuracion"+";"+String.valueOf(us.usuario)+";"+String.valueOf(ventilador.isChecked())+";"+String.valueOf(cocina.isChecked())+";"+String.valueOf(sala.isChecked())+";"+String.valueOf(foco1.isChecked()+";"+String.valueOf(foco2.isChecked()+";"+String.valueOf(focoCuarto2.isChecked()))+";"+String.valueOf(spin.getSelectedItem()));

        new ConfiguracionPersonalizada.HilitoAgregar().execute();

    }});

    }

    private class HilitoAgregar extends AsyncTask<Void, Void, String> implements Serializable {
        Conexion con = new Conexion();

        @Override
        protected String doInBackground(Void... params) {

            try {
                Socket kkSocket = new Socket(con.getHostName(), con.getPortNumber());
                PrintWriter out =
                        new PrintWriter(kkSocket.getOutputStream(), true);
                BufferedReader in =
                        new BufferedReader(
                                new InputStreamReader(kkSocket.getInputStream()));

                out.println(enviar);  //aqui es donde enviar

                while ((res = in.readLine()) != null) { //asi recibes
                    regresar = res;
                    kkSocket.close();
                }
                Log.i("aqui2", regresar); //regresar sera la cadena resultado
            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return regresar;
        }

        @Override
        protected void onPostExecute(String textof) {

        }
    }


    @Override
    public void onBackPressed() {



        if (us.getAdministrador().equals("1")){
            Intent i = new Intent(ConfiguracionPersonalizada.this, menuPAdministrador.class);
            i.putExtra("intUsuarios", (Serializable) us);
            startActivity(i);
        }else{
            Intent i = new Intent(ConfiguracionPersonalizada.this, MenuPrincipal.class);
            i.putExtra("intUsuarios", (Serializable) us);
            startActivity(i);

        }
    }
}

