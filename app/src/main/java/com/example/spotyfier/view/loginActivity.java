package com.example.spotyfier.view;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spotyfier.R;
import com.example.spotyfier.controller.HelperBD;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

public class loginActivity extends AppCompatActivity {

    private CircularProgressButton botonLogin;
    private CircularProgressButton botonRegister;
    private HelperBD helperBD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        helperBD = new HelperBD(this, "usuarios", null, 1); //Se declara el helper de la BD

        botonLogin = findViewById(R.id.botonLogin);
        botonLogin.setOnClickListener(new View.OnClickListener() { //Se asigna el metodo para iniciar sesion al boton
            @Override
            public void onClick(View view) {
                EditText editUser = findViewById(R.id.campoLoginNombre);
                EditText editPass = findViewById(R.id.campoLoginPass);
                String user = editUser.getText().toString();
                String pass = editPass.getText().toString();
                if(user.equals("") || pass.equals("")){ //Se comprueba que los campos no esten vacios
                    mostrarToast("Rellena los campos");
                }
                else {
                    botonLogin.startAnimation(); //Inicia la animacion del boton
                    esperarYAbrirLogin(user, pass); //Espera unos segundos para que se vea la animacion. Pasamos el usuario y contraseña para comprobarlos
                }
            }
        });

        botonRegister = (CircularProgressButton) findViewById(R.id.botonRegistro);

        botonRegister.setOnClickListener(new View.OnClickListener() { //Mismo procedimiento para el boton de registro
            @Override
            public void onClick(View view) {
                botonRegister.startAnimation();
                esperarYAbrirRegister();
            }
        });
    }
    public void esperarYAbrirLogin(String user, String pass) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                indentLogin(user, pass); //Pasamos el usuario y contraseña al intent
                botonLogin.revertAnimation(); //Revertimos para que quede como al inicio
            }
        }, 3000); //Espera 3 segundos
    }
    public void esperarYAbrirRegister() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                indentRegistro();
                botonRegister.revertAnimation();
            }
        }, 2000);
    }

    public void indentRegistro(){
        Intent registerIntent = new Intent(this, RegisterActivity.class);
        startActivity(registerIntent); //Crea la actividad de registro
    }

    private void indentLogin(String user, String pass){ //Crea la actividad principal si el usuario y contraseña son correctos
        SQLiteDatabase bd = helperBD.getWritableDatabase();
        Cursor cursor = bd.rawQuery("SELECT * FROM usuarios WHERE usuario = ? ", new String[]{user}); //Seleccionamos los registros que concuerden con el usuario
        if (cursor.moveToFirst()) {
            String contra = cursor.getString(2);
            do {
                if (pass.equals(contra)) { //Si la contraseña es correcta inicia sesion y pasa a la actividad principal
                    mostrarToast("Sesion iniciada");
                    Intent alMain = new Intent(this, MainActivity.class);
                    startActivity(alMain);
                }
            } while (cursor.moveToNext());
        }
        else{
            mostrarToast("Datos erroneos.");
        }
    }

    public void mostrarToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }



}

