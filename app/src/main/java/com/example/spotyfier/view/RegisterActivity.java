package com.example.spotyfier.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.spotyfier.R;
import com.example.spotyfier.controller.HelperBD;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

public class RegisterActivity extends AppCompatActivity {
private HelperBD helperBD;
private CircularProgressButton botonRegistro;
    @Override
    protected void onCreate(Bundle savedInstanceState) { //Actividad que se lanza al darle al boton de registrarse
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        helperBD = new HelperBD(this, "usuarios", null, 1);
        botonRegistro = (findViewById(R.id.botonRegistrarse));
        botonRegistro.setOnClickListener(new View.OnClickListener() { //Declaramos el boton y creamos un listener:
            @Override
            public void onClick(View view) {
                EditText usuario = findViewById(R.id.campoRegisterUsuario);
                EditText contra = findViewById(R.id.campoRegisterPassw);
                EditText correo = findViewById(R.id.campoRegisterEmail);

                String user = usuario.getText().toString();
                String passw = contra.getText().toString();
                String email = correo.getText().toString();

                if(user.equals("") || passw.equals("") || email.equals("")){ //Si hay algun campo vacio
                    mostrarToast("Rellena todos los campos");
                }
                else{ //Si no se lanza la animacion del boton y se realiza el registro
                    botonRegistro.startAnimation();
                    esperarYAbrirRegistro(user, passw, email);
                }
            }
        });
    }

    public void esperarYAbrirRegistro(String user, String passw, String email) { //Espera 2 segundos y realiza el registro
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                registrarse(user, passw, email); //Pasamos el valor de los campos como parametro
                botonRegistro.revertAnimation();
            }
        }, 2000);
    }

    private void registrarse(String user, String passw, String email) { //

        SQLiteDatabase bd = helperBD.getWritableDatabase();
        if (user.equals("") || passw.equals("") || email.equals("")) {
            mostrarToast("Rellena todos los campos");
        }
        else{
            if (bd.isOpen()) {
                ContentValues valoresRegistro = new ContentValues();
                valoresRegistro.put("usuario", user);
                valoresRegistro.put("email", email);
                valoresRegistro.put("passw", passw);
                bd.insert("usuarios", null, valoresRegistro); //Colocamos los valores de los campos en el contentValues y lo insertamos en la bd
                mostrarToast("Usuario registrado. Procede a iniciar sesion.");
                bd.close();
                finish();
            }
        }
    }
    public void mostrarToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

}

