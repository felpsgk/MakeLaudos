package com.example.felps.calcs.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class bdOpenHelper extends SQLiteOpenHelper {

    public bdOpenHelper(Context context) {
        super(context, "teste1", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS empresa " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "nome VARCHAR(256), " +
                "endereco VARCHAR(256), " +
                "telefone VARCHAR(11), " +
                "email VARCHAR(256));");
        db.execSQL("CREATE TABLE IF NOT EXISTS usuario " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "nome VARCHAR(256), " +
                "endereco VARCHAR(256), " +
                "telefone VARCHAR(11), " +
                "email VARCHAR(256));");
        db.execSQL("CREATE TABLE IF NOT EXISTS aparelho " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "nome VARCHAR(256), " +
                "nserie INTEGER(255));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
