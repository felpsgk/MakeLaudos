package com.example.felps.calcs.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.felps.calcs.model.Empresa;

import java.util.ArrayList;

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
        val.put("nomeEmpresa", e.getNome());
        val.put("endereco", e.getEndereco());
        val.put(oqsalvar, e.getContato());
        val.put("tipoEmpresa",e.getTipo());
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
        Cursor result = sqldb.query("EMPRESA",new String[]{"ID","NOMEEMPRESA","ENDERECO","TELEFONE","EMAIL", "TIPOEMPRESA"},null,null,null,null,null,null);
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
            e.setTipo(result.getInt(5));
            empresas.add(e);
        }
        sqldb.close();
        return empresas;
    }

    public Empresa searchEmpresa(String nome){
        Empresa e = new Empresa();
        sqldb = bdo.getWritableDatabase();
        Cursor result = sqldb.rawQuery("SELECT * FROM EMPRESA WHERE NOMEEMPRESA = ?",new String[]{nome});
        if(result.getCount()>0){
            result.moveToFirst();
            e.setId(result.getInt(result.getColumnIndexOrThrow("id")));
            e.setNome(result.getString(result.getColumnIndexOrThrow("nomeEmpresa")));
            return e;
        }
        result.close();
        return null;
    }
}
