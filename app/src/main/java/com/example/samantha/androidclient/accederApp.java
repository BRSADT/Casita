package com.example.samantha.androidclient;

import android.app.Activity;
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
import java.net.Socket;
import java.net.UnknownHostException;

public class accederApp extends AppCompatActivity {
    private Socket socket;
    StringBuilder texto= new StringBuilder();
 //   String hostName = "192.168.43.183";
   //String hostName = "192.168.1.66";
   String hostName = "192.168.1.66";
    int portNumber = 4444;
    EditText inputUsuario,inputPassword;
    String textof="";
    Button btnacceder;
    String StrUsuario="",StrPassword="",Instruccion="",ip="",enviar="",res="";
    TextView prueba;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceder_app);
        btnacceder=(Button) findViewById(R.id.btnAcceder);
        inputUsuario=(EditText)findViewById(R.id.InputUsuario);
        inputPassword=(EditText)findViewById(R.id.InputPass);
        prueba=(TextView) findViewById(R.id.textView3);

        //Cuando el usuario da click
btnacceder.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        StrUsuario=inputUsuario.getText().toString();
        StrPassword=inputPassword.getText().toString();

        if(StrUsuario.length()>0&&StrPassword.length()>0) { //se ingresara a la BDD Para verificar los campos
            Toast.makeText(getApplicationContext(), "dio click", Toast.LENGTH_SHORT).show();
        /*    try {
                new Thread(new ClientThread()).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
*/
            new Thread(new Runnable()
            {
                public void run()
                {
                    try {
                        Socket kkSocket = new Socket(hostName, portNumber);
                        PrintWriter out =
                                new PrintWriter(kkSocket.getOutputStream(), true);
                        BufferedReader in =
                                new BufferedReader(
                                        new InputStreamReader(kkSocket.getInputStream()));




                        Instruccion="Verificacion";
                        ip=kkSocket.getLocalAddress().toString();
                        enviar=Instruccion+";-;"+ip+";-;"+StrUsuario+";-;"+StrPassword;

                        out.println(enviar);

                        while((res = in.readLine()) != null) {


                            textof=textof+res;
                            kkSocket.close();
                        }





                    } catch (UnknownHostException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    prueba.post(new Runnable()
                    {

                        public void run()
                        {  Log.i("mensaje", "entro aqui");
                            prueba.setText(textof);

                        }
                    });
                }
            }).start();





        } //FIN CONDICION
    } //fin ONCLIC
});//fin clic



    }

    class ClientThread implements Runnable {


        ClientThread() throws IOException {
        }

        @Override
        public void run() {
String x=".";
            try {
                Socket kkSocket = new Socket(hostName, portNumber);
                PrintWriter out =
                        new PrintWriter(kkSocket.getOutputStream(), true);
                BufferedReader in =
                        new BufferedReader(
                                new InputStreamReader(kkSocket.getInputStream()));




                Instruccion="Verificacion";
                ip=kkSocket.getLocalAddress().toString();
                enviar=Instruccion+";-;"+ip+";-;"+StrUsuario+";-;"+StrPassword;

                out.println(enviar);
              /*  while ((res = in.readLine()) != null) {
                  x+=res;
                    prueba.setText(x);
                    kkSocket.close();
                  }
*/
                while((res = in.readLine()) != null) {

                    Log.i("mensaje1", res);
                    textof=textof+res;

                }
                Log.i("mensaje", textof);
               // prueba.setText(x);



                kkSocket.close();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        prueba.setText(textof);
                        //Do something on UiThread
                    }
                });


            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }//fin run



    }
}
