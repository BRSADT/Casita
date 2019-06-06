package com.example.samantha.androidclient;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Habitantes extends AppCompatActivity implements Serializable  {
    ArrayList<LinearLayout> arrayLayout = new ArrayList<LinearLayout>();
    ArrayList<LinearLayout> espacio = new ArrayList<LinearLayout>();
    ArrayList<TextView> arrayPalabra = new ArrayList<TextView>();
    ArrayList<TextView> arrayDescripion = new ArrayList<TextView>();
    ArrayList<Button> arrayBotones = new ArrayList<Button>();
    ArrayList<HilitoAgregar> hilitooos = new ArrayList<HilitoAgregar>();
    ArrayList<ScrollView> arrayScroll = new ArrayList<ScrollView>();
    ArrayList<String> arrayCodigo=new ArrayList<>();
    EditText tpista,tpalabra;
    datosUsuario us;
    String enviar="",Instruccion="",ip="",regresar="",res="";
    boolean eliminado;

    private View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//eliminar
           int idFila=(int)view.getId();

            eliminado=true;
enviar="EliminarHabitante"+";"+String.valueOf(arrayCodigo.get(idFila));
Log.i("mensaje",String.valueOf(arrayCodigo.get(idFila)));
            new Habitantes.HilitoAgregar().execute();
        }
    };

    int  contador=0;


    Button agregar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habitantes);
        agregar= (Button) findViewById(R.id.buttonAg);
        us= (datosUsuario) getIntent().getSerializableExtra("intUsuarios"); //OBTIENES datos del usuario actual;
        eliminado=false;
        Instruccion="Habitantes";
        enviar=Instruccion+";"+ip;
        new Habitantes.HilitoAgregar().execute();





        agregar.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                    Intent i = new Intent(Habitantes.this,AgregarUsuarios.class);
                    i.putExtra("intUsuarios", (Serializable) us);
                startActivity(i);



            }});



    }

    @Override
    public void onBackPressed() {
if (us.getAdministrador().equals("1")){
    Intent i = new Intent(Habitantes.this, menuPAdministrador.class);
    i.putExtra("intUsuarios", (Serializable) us);
    startActivity(i);
}else{
    Intent i = new Intent(Habitantes.this, MenuPrincipal.class);
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
          String[]  TodosUser=HabYApe[0].split("-");
            String[]  TodosApe=HabYApe[1].split("-");
            String[]  TodosCodigos=HabYApe[2].split("-");
            LinearLayout ll = (LinearLayout)findViewById(R.id.layoutpalabras);
            LinearLayout.LayoutParams o = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0, 1.0f);
            Log.i("mensajeEl",String.valueOf(eliminado));
            if(eliminado){
                Intent i = new Intent(Habitantes.this, Habitantes.class);
                i.putExtra("intUsuarios", (Serializable) us);
                startActivity(i);


            }else {
                for (String x : TodosUser) {  //para cada usuario de la bdd
                    arrayLayout.add(new LinearLayout(Habitantes.this));
                    arrayLayout.get(contador).setLayoutParams(o);
                    arrayLayout.get(contador).setOrientation(LinearLayout.HORIZONTAL);

                    espacio.add(new LinearLayout(Habitantes.this));
                    espacio.get(contador).setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));


                    arrayPalabra.add(new TextView(Habitantes.this));
                    arrayPalabra.get(contador).setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 2.0f));
                    arrayPalabra.get(contador).setText(x);


                    arrayScroll.add(new ScrollView(Habitantes.this));
                    arrayScroll.get(contador).setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 4.0f));

                    arrayDescripion.add(new TextView(Habitantes.this));
                    arrayDescripion.get(contador).setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
                    arrayDescripion.get(contador).setText(String.valueOf(TodosApe[contador]));

                    arrayScroll.get(contador).addView(arrayDescripion.get(contador));

                    arrayBotones.add(new Button(Habitantes.this));
                    arrayBotones.get(contador).setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
                    arrayBotones.get(contador).setText("-");
                    arrayBotones.get(contador).setId(contador);
                    arrayBotones.get(contador).setOnClickListener(buttonClickListener);
                    arrayLayout.get(contador).addView(espacio.get(contador));
                    arrayLayout.get(contador).addView(arrayPalabra.get(contador));
                    arrayLayout.get(contador).addView(arrayScroll.get(contador));
                    arrayCodigo.add(TodosCodigos[contador]);

                    //   arrayLayout.get(contador).addView(arrayDescripion.get(contador));
                    arrayLayout.get(contador).addView(arrayBotones.get(contador));


                    ll.addView(arrayLayout.get(contador));


                    contador++;
                }

            }


        }
    }


}

