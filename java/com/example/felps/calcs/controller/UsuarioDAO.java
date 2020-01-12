package com.example.felps.calcs.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.felps.calcs.model.Usuario;

import java.util.ArrayList;

public class UsuarioDAO {
    static SQLiteDatabase sqldb;
    private static bdOpenHelper bdo;

    public UsuarioDAO(Context c) {
        bdo = new bdOpenHelper(c);
    }

    public Boolean newUsuario (Usuario u, String oqsalvar){
        ContentValues val;
        long result;
        sqldb = bdo.getWritableDatabase();
        val = new ContentValues();
        val.put("nomeUsuario", u.getNome());
        val.put("documento", u.getDocumento());
        val.put(oqsalvar, u.getContato());
        val.put("Usuario_idEmpresa",u.getEmpresa());
        result = sqldb.insert("usuario",null,val);
        sqldb.close();
        if (result == -1) {
            return false;
        } else{
            return true;
        }
    }

    public static ArrayList<Usuario> searchAll() {
        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
        sqldb = bdo.getWritableDatabase();
        Usuario u = null;
        Cursor result = sqldb.query("USUARIO",new String[]{"ID","NOMEUSUARIO","documento","telefone","EMAIL"},null,null,null,null,null,null);
        while (result.moveToNext()){
            u = new Usuario();
            u.setId(result.getInt(0));
            u.setNome(result.getString(1));
            u.setDocumento(result.getString(2));
            if(result.getString(3)==null){
                u.setContato(result.getString(4));
            } else {
                u.setContato(result.getString(3));
            }
            usuarios.add(u);
        }
        sqldb.close();
        return usuarios;
    }

    public Usuario searchUsuario(String nome){
        Usuario u = new Usuario();
        sqldb = bdo.getWritableDatabase();
        Cursor result = sqldb.rawQuery("SELECT * FROM USUARIO WHERE NOMEUSUARIO = ?",new String[]{nome});
        if(result.getCount()>0){
            result.moveToFirst();
            u.setId(result.getInt(result.getColumnIndexOrThrow("id")));
            u.setNome(result.getString(result.getColumnIndexOrThrow("nomeUsuario")));
            return u;
        }
        result.close();
        return null;
    }

}
