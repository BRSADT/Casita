package com.example.samantha.androidclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

public class menuPAdministrador extends AppCompatActivity   implements Serializable {
    TextView NombreUs;
    Button btnAgregar,btnHistorial;
    datosUsuario us;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_padministrador);
        NombreUs=(TextView) findViewById(R.id.NombreUS);
        us= (datosUsuario) getIntent().getSerializableExtra("intUsuarios"); //OBTIENES datos del usuario actual;
        NombreUs.setText(us.nombre);
        btnAgregar=(Button)findViewById(R.id.btnAgregar);
        btnHistorial=(Button) findViewById(R.id.btnHistorial);
        btnAgregar=(Button)findViewById(R.id.btnAgregar);
        btnAgregar.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                //se ira a agregar usuarios
                Intent i = new Intent(menuPAdministrador.this, Habitantes.class);
                i.putExtra("intUsuarios", (Serializable) us);
                startActivity(i);

            }
        });

        btnHistorial.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                //se ira a agregar usuarios
                Intent i = new Intent(menuPAdministrador.this, Historial.class);
                i.putExtra("intUsuarios", (Serializable) us);
                startActivity(i);

            }
        });

    }
    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "rEGRESO", Toast.LENGTH_SHORT).show();

        Intent i = new Intent(menuPAdministrador.this, accederApp.class);

        startActivity(i);
    }
}
