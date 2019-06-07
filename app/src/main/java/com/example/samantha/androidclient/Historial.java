package com.example.samantha.androidclient;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Historial extends AppCompatActivity implements Serializable {
    ArrayList<LinearLayout> arrayLayout = new ArrayList<LinearLayout>();
    ArrayList<LinearLayout> espacio = new ArrayList<LinearLayout>();
    ArrayList<TextView> arrayNombre = new ArrayList<TextView>();
    ArrayList<TextView> arrayApellido = new ArrayList<TextView>();
    ArrayList<TextView> arrayfecha = new ArrayList<TextView>();
    ArrayList<TextView> arrayHoras = new ArrayList<TextView>();
    ArrayList<Button> arrayBotones = new ArrayList<Button>();

    ArrayList<ScrollView> arrayScroll = new ArrayList<ScrollView>();
    ArrayList<String> arrayCodigo=new ArrayList<>();
    EditText tpista,tpalabra;
    int  contador=0;
    datosUsuario us;
    String enviar="",Instruccion="",ip="",regresar="",res="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);
        Instruccion="Historial";
        enviar=Instruccion+";"+ip;
        new Historial.HilitoAgregar().execute();


    }
    @Override
    public void onBackPressed() {
        if (us.getAdministrador().equals("1")){
            Intent i = new Intent(Historial.this, menuPAdministrador.class);
            i.putExtra("intUsuarios", (Serializable) us);
            startActivity(i);
        }else{
            Intent i = new Intent(Historial.this, MenuPrincipal.class);
            i.putExtra("intUsuarios", (Serializable) us);
            startActivity(i);

        }


    }


    private class HilitoAgregar extends AsyncTask<Void, Void, String> implements Serializable {
        Conexion con=new Conexion();
        @Override
        protected String doInBackground(Void... params) {

            try {
                Socket kkSocket = new Socket(con.getHostName(),con.getPortNumber());
                PrintWriter out =
                        new PrintWriter(kkSocket.getOutputStream(), true);
                BufferedReader in =
                        new BufferedReader(
                                new InputStreamReader(kkSocket.getInputStream()));
                ip=kkSocket.getLocalAddress().toString();
                out.println(enviar);  //aqui es donde enviar

                while((res = in.readLine()) != null) { //asi recibes
                    regresar=res;
                    kkSocket.close();
                }
                Log.i("aqui2", regresar ); //regresar sera la cadena resultado
            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return regresar;
        }

        @Override
        protected void onPostExecute(String textof) {

            String[] HabYApe=regresar.split(";");
            String[]  TodosNombre=HabYApe[0].split("@");
            String[]  TodosApe=HabYApe[1].split("@");
            String[]  TodosCodigos=HabYApe[2].split("@");
            String[]  Todosfechas=HabYApe[3].split("@");
            String[]  TodosHoras=HabYApe[4].split("@");

            LinearLayout ll = (LinearLayout)findViewById(R.id.layoutpalabras);
            LinearLayout.LayoutParams o = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0, 1.0f);

                for (String x : TodosCodigos) {  //para cada usuario de la bdd
                    arrayLayout.add(new LinearLayout(Historial.this));
                    arrayLayout.get(contador).setLayoutParams(o);
                    arrayLayout.get(contador).setOrientation(LinearLayout.HORIZONTAL);

                    espacio.add(new LinearLayout(Historial.this));
                    espacio.get(contador).setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));


                    arrayNombre.add(new TextView(Historial.this));
                    arrayNombre.get(contador).setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 2.0f));
                    arrayNombre.get(contador).setText(TodosNombre[contador]);


                    arrayScroll.add(new ScrollView(Historial.this));
                    arrayScroll.get(contador).setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 2.0f));

                    arrayApellido.add(new TextView(Historial.this));
                    arrayApellido.get(contador).setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
                    arrayApellido.get(contador).setText(String.valueOf(TodosApe[contador]));

                    arrayScroll.get(contador).addView(arrayApellido.get(contador));

                    arrayfecha.add(new TextView(Historial.this));
                    arrayfecha.get(contador).setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 2.0f));
                    arrayfecha.get(contador).setText(Todosfechas[contador]);

                    arrayHoras.add(new TextView(Historial.this));
                    arrayHoras.get(contador).setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 2.0f));
                    arrayHoras.get(contador).setText(TodosHoras[contador]);

                 /*   arrayBotones.add(new Button(Historial.this));
                    arrayBotones.get(contador).setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
                    arrayBotones.get(contador).setText("-");
                    arrayBotones.get(contador).setId(contador);
                    arrayBotones.get(contador).setOnClickListener(buttonClickListener);
                   */

                    arrayLayout.get(contador).addView(espacio.get(contador));
                    arrayLayout.get(contador).addView(arrayNombre.get(contador));
                    arrayLayout.get(contador).addView(arrayScroll.get(contador));
                    arrayLayout.get(contador).addView(arrayfecha.get(contador));
                    arrayLayout.get(contador).addView(arrayHoras.get(contador));

                    arrayCodigo.add(x);

                    //   arrayLayout.get(contador).addView(arrayDescripion.get(contador));
                 //   arrayLayout.get(contador).addView(arrayBotones.get(contador));


                    ll.addView(arrayLayout.get(contador));


                    contador++;
                }




        }
    }

}
