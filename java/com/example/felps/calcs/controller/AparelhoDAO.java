package com.example.felps.calcs.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.felps.calcs.model.Aparelho;

import java.util.ArrayList;

public class AparelhoDAO {

    static SQLiteDatabase sqldb;
    private static bdOpenHelper bdo;

    public AparelhoDAO(Context c) {
        bdo = new bdOpenHelper(c);
    }

    public Boolean newAparelho (Aparelho a){
        ContentValues val;
        long result;
        sqldb = bdo.getWritableDatabase();
        val = new ContentValues();
        val.put("nome", a.getNome());
        val.put("nserie", a.getNserie());
        result = sqldb.insert("aparelho",null,val);
        sqldb.close();
        if (result == -1) {
            return false;
        } else{
            return true;
        }
    }

    public static ArrayList<Aparelho> searchAll() {
        ArrayList<Aparelho> aparelhos = new ArrayList<Aparelho>();
        sqldb = bdo.getWritableDatabase();
        Aparelho a = null;
        Cursor result = sqldb.query("APARELHO",new String[]{"ID","NOME","NSERIE"},null,null,null,null,null,null);
        while (result.moveToNext()){
            a = new Aparelho();
            a.setId(result.getInt(0));
            a.setNome(result.getString(1));
            a.setNserie(result.getInt(2));
            aparelhos.add(a);
        }
        sqldb.close();
        return aparelhos;
    }

    public Aparelho searchAparelho(String nome){
        Aparelho a = new Aparelho();
        sqldb = bdo.getWritableDatabase();
        Cursor result = sqldb.rawQuery("SELECT * FROM APARELHO WHERE NOME = ?",new String[]{nome});
        if(result.getCount()>0){
            result.moveToFirst();
            a.setId(result.getInt(result.getColumnIndexOrThrow("id")));
            a.setNome(result.getString(result.getColumnIndexOrThrow("nome")));
            return a;
        }
        result.close();
        return null;
    }

}
