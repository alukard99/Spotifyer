package com.example.spotyfier.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class HelperBD extends SQLiteOpenHelper { //Sirve para crear y actualizar la base de datos

    public HelperBD(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE usuarios (usuario TEXT, email TEXT, passw TEXT)"); //Estos son los campos que tendrá la BD
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS usuarios"); //Al actualizar se borra la BD
        sqLiteDatabase.execSQL("CREATE TABLE usuarios (usuario TEXT, contra TEXT)"); //Y se vuelve a crear
    }

}
