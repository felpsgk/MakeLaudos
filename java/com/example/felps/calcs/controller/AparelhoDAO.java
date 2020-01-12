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
        val.put("nomeAparelho", a.getNomeAparelho());
        val.put("nserieAparelho", a.getNserieAparelho());
        val.put("fabricanteAparelho", a.getFabricanteAparelho());
        val.put("categoriaAparelho", a.getCategoriaAparelho());
        val.put("modeloAparelho", a.getModeloAparelho());
        val.put("Aparelho_idEmpresa", a.getEmpresa());
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
        Cursor result = sqldb.query("APARELHO",new String[]{"ID","nomeAparelho","nserieAparelho"},null,null,null,null,null,null);
        while (result.moveToNext()){
            a = new Aparelho();
            a.setId(result.getInt(0));
            a.setNomeAparelho(result.getString(1));
            a.setNserieAparelho(result.getInt(2));
            aparelhos.add(a);
        }
        sqldb.close();
        return aparelhos;
    }

    public Aparelho searchAparelho(String nome){
        Aparelho a = new Aparelho();
        sqldb = bdo.getWritableDatabase();
        Cursor result = sqldb.rawQuery("SELECT * FROM APARELHO WHERE nomeAparelho = ?",new String[]{nome});
        if(result.getCount()>0){
            result.moveToFirst();
            a.setId(result.getInt(result.getColumnIndexOrThrow("id")));
            a.setNomeAparelho(result.getString(result.getColumnIndexOrThrow("nomeAparelho")));
            return a;
        }
        result.close();
        return null;
    }

}
