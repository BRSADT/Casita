package com.example.samantha.androidclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

public class MenuPrincipal extends AppCompatActivity   implements Serializable {
TextView txtNombre;
datosUsuario us;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        txtNombre=(TextView) findViewById(R.id.txtNombre);
        us= (datosUsuario) getIntent().getSerializableExtra("intUsuarios"); //OBTIENES datos del usuario;
        txtNombre.setText(us.nombre);






    }
    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "rEGRESO", Toast.LENGTH_SHORT).show();

        Intent i = new Intent(MenuPrincipal.this, accederApp.class);

        startActivity(i);
    }
}
