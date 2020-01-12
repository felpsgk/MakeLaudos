package com.example.felps.calcs.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.felps.calcs.model.Caracteristica;

import java.util.ArrayList;


public class caracteristicaDAO {

        static SQLiteDatabase sqldb;
        private static bdOpenHelper bdo;

        public caracteristicaDAO(Context c) {
            bdo = new bdOpenHelper(c);
        }

        public Boolean newCaracteristica (Caracteristica c){
            ContentValues val;
            long result;
            sqldb = bdo.getWritableDatabase();
            val = new ContentValues();
            val.put("idaparelho", c.getIdAparelho());
            val.put("descricao", c.getDescricao());
            val.put("val_padrao", c.getVal_padrao());
            val.put("idaparelho", c.getIdAparelho());
            result = sqldb.insert("caracteristica",null,val);
            sqldb.close();
            if (result == -1) {
                return false;
            } else{
                return true;
            }
        }

        public boolean update(Caracteristica c) {

            ContentValues values = new ContentValues();

            values.put("id", c.getId());
            values.put("descricao", c.getDescricao());
            values.put("val_padrao", c.getVal_padrao());
            values.put("val_obtido", c.getVal_obtido());
            values.put("idaparelho", c.getIdAparelho());

            bdo.getWritableDatabase().update("caracteristica",values,"id=?",new String[]{String.valueOf(c.getId())});

            bdo.close();
            return true;
        }


        public static ArrayList<Caracteristica> searchAll() {
            ArrayList<Caracteristica> caracteristicas = new ArrayList<Caracteristica>();
            sqldb = bdo.getWritableDatabase();
            Caracteristica c = null;
            Cursor result = sqldb.query("CARACTERISTICA",new String[]{"ID","DESCRICAO","VAL_PADRAO","VAL_OBTIDO","VAL_ESPERADO","IDAPARELHO"},null,
                    null,null,null,null,null);
            while (result.moveToNext()){
                c = new Caracteristica();
                c.setId(result.getInt(0));
                c.setDescricao(result.getString(1));
                c.setVal_padrao(result.getInt(2));
                c.setVal_obtido(result.getInt(3));
                c.setVal_esperado(result.getInt(4));
                c.setIdAparelho(result.getInt(5));
                caracteristicas.add(c);
            }
            sqldb.close();
            return caracteristicas;
        }

        public static ArrayList<Caracteristica> searchAparelho(String idAparelho) {
            ArrayList<Caracteristica> caracteristicas = new ArrayList<Caracteristica>();
            sqldb = bdo.getWritableDatabase();
            Caracteristica c = null;
            Cursor result = sqldb.rawQuery("SELECT * FROM CARACTERISTICA WHERE IDAPARELHO = ?",new String[]{idAparelho});
            while (result.moveToNext()){
                c = new Caracteristica();
                c.setId(result.getInt(0));
                c.setDescricao(result.getString(1));
                c.setVal_padrao(result.getInt(2));
                c.setVal_obtido(result.getInt(3));
                c.setVal_esperado(result.getInt(4));
                c.setIdAparelho(result.getInt(5));
                caracteristicas.add(c);
            }
            sqldb.close();
            return caracteristicas;
        }

        public Caracteristica searchCaracteristica(String desc){
            Caracteristica c = new Caracteristica();
            sqldb = bdo.getWritableDatabase();
            Cursor result = sqldb.rawQuery("SELECT * FROM CARACTERISTICA WHERE DESCRICAO = ?",new String[]{desc});
            if(result.getCount()>0){
                result.moveToFirst();
                c.setId(result.getInt(result.getColumnIndexOrThrow("id")));
                c.setDescricao(result.getString(result.getColumnIndexOrThrow("descricao")));
                c.setVal_padrao(result.getInt(result.getColumnIndexOrThrow("val_padrao")));
                c.setVal_obtido(result.getInt(result.getColumnIndexOrThrow("val_obtido")));
                c.setVal_esperado(result.getInt(result.getColumnIndexOrThrow("val_esperado")));
                c.setIdAparelho(result.getInt(result.getColumnIndexOrThrow("idaparelho")));
                return c;
            }
            result.close();
            return null;
        }



}


