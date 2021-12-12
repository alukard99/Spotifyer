package com.example.spotyfier.controller;

import java.net.HttpURLConnection;
import android.net.Uri;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectApi {
    private static String URL_BASE = "https://thronesapi.com";
    public static String getRequest(String strUrl){ //Realiza las peticiones a la API
        HttpURLConnection http = null;
        String content = null;
        try{
            URL url = new URL(URL_BASE + strUrl); //Se junta la url con la parte que le pasemos.
            http = (HttpURLConnection)url.openConnection();
            http.setRequestProperty("Content-Type", "application/json");
            http.setRequestProperty("Accept" , "application/json");

            if(http.getResponseCode() == HttpURLConnection.HTTP_OK){ //Si conecta:
                StringBuilder sb = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(http.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                content = sb.toString();
                reader.close();
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {

            if( http != null ) http.disconnect();
        }
        return content; //Devuelve un String con toodo el contenido de la API
    }
}
