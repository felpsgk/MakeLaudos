package com.example.felps.calcs.controller;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.felps.calcs.model.Empresa;

import java.util.ArrayList;
import java.util.List;

public class EmpresaDAO {
    static SQLiteDatabase sqldb;
    private static bdOpenHelper bdo;

    public EmpresaDAO(Context c) {
        bdo = new bdOpenHelper(c);
    }

    public Boolean newEmpresa (Empresa e, String oqsalvar){
        ContentValues val;
        long result;
        sqldb = bdo.getWritableDatabase();
        val = new ContentValues();
        val.put("nome", e.getNome());
        val.put("endereco", e.getEndereco());
        val.put(oqsalvar, e.getContato());
        result = sqldb.insert("empresa",null,val);
        sqldb.close();
        if (result == -1) {
            return false;
        } else{
            return true;
        }
    }

    public static ArrayList<Empresa> searchAll() {
        ArrayList<Empresa> empresas = new ArrayList<Empresa>();
        sqldb = bdo.getWritableDatabase();
        Empresa e = null;
        Cursor result = sqldb.query("EMPRESA",new String[]{"ID","NOME","ENDERECO","TELEFONE","EMAIL"},null,null,null,null,null,null);
        while (result.moveToNext()){
            e = new Empresa();
            e.setId(result.getInt(0));
            e.setNome(result.getString(1));
            e.setEndereco(result.getString(2));
            if(result.getString(3)==null){
                e.setContato(result.getString(4));
            } else {
                e.setContato(result.getString(3));
            }
            empresas.add(e);
        }
        sqldb.close();
        return empresas;
    }

    public Empresa searchEmpresa(String nome){
        Empresa e = new Empresa();
        sqldb = bdo.getWritableDatabase();
        Cursor result = sqldb.rawQuery("SELECT * FROM EMPRESA WHERE NOME = ?",new String[]{nome});
        if(result.getCount()>0){
            result.moveToFirst();
            e.setId(result.getInt(result.getColumnIndexOrThrow("id")));
            e.setNome(result.getString(result.getColumnIndexOrThrow("nome")));
            return e;
        }
        result.close();
        return null;
    }
}
