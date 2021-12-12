package com.example.spotyfier.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotyfier.R;
import com.example.spotyfier.controller.ConnectApi;
import com.example.spotyfier.controller.RecyclerAdapter;
import com.example.spotyfier.controller.SettingsActivity;
import com.example.spotyfier.model.Personaje;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    private ArrayList<Personaje> listaPersonajes = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbarId); //Instanciamos la barra de action
        setSupportActionBar(myToolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recView); //Instanciamos el recyclerView
        taskConnections tsk = new taskConnections();
        tsk.execute("GET","/api/v2/Characters");
        recyclerAdapter = new RecyclerAdapter(listaPersonajes, this); //Instanciamos el adaptador del recyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this); //Instanciamos el linearLayout


        recyclerView.setAdapter(recyclerAdapter); //Aplicamos el adaptador al recyclerview
        recyclerView.setLayoutManager(layoutManager); //Aplicamos el linearlayout


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);        //Inflamos el actionBar
        return super.onCreateOptionsMenu(menu);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Dependiendo de a que boton del actionBar pulsemos
        switch (item.getItemId()) {
            case R.id.action_settings: //Abrira la actividad de preferencias
                Intent preferences = new Intent(this, SettingsActivity.class);
                startActivity(preferences);
                return true;
            case R.id.delete_settings: //o activa el modo borrar
                if(RecyclerAdapter.getModoBorrar()==0){ //Se activa
                    mostrarToast("Selecciona el item a borrar");
                    RecyclerAdapter.setModoBorrar(1);
                }
                else if(RecyclerAdapter.getModoBorrar()==1){ //Se desactiva
                    mostrarToast("Cancelado.");
                    RecyclerAdapter.setModoBorrar(0);
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private class taskConnections extends AsyncTask<String,Void,String> { //Se encarga de ir cargando los items del recyclerView
        @Override
        protected String doInBackground(String... strings) {
            String result = null;
            switch (strings[0]){
                case "GET":
                    result = ConnectApi.getRequest(strings[1]); //Devuelve un string con toodo el contenido de la api
                    break;
            }
            Log.d("result", result);
            return result;
        }

        @Override
        protected void onPostExecute(String s) { //Pasa el string de la API a formato JSONArray
            Log.d("entra", "entra");
            try {
                if(s != null){ //Si el string no esta vacio
                    Log.d("D","DATOS: "+s);

                    JSONArray jsonArray = new JSONArray(s);

                    String nombre = "";
                    String titulo = "";
                    String familia = "";
                    String urlImg = "";

                    for(int i=0; i<jsonArray.length(); i++){
                        nombre = jsonArray.getJSONObject(i).getString("fullName");
                        titulo = jsonArray.getJSONObject(i).getString("title");
                        familia = jsonArray.getJSONObject(i).getString("family");
                        urlImg = jsonArray.getJSONObject(i).getString("imageUrl");
                        listaPersonajes.add(new Personaje(i,nombre,titulo,familia,urlImg)); //Crea un personaje con el item JSONObject
                    }

                    recyclerAdapter.notifyDataSetChanged();

                }else{
                    Toast.makeText(MainActivity.this, "Problema al cargar los datos", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    public void mostrarToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

}


