package com.example.felps.calcs.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class bdOpenHelper extends SQLiteOpenHelper {

    public bdOpenHelper(Context context) {
        super(context, "teste9", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS empresa " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "nomeEmpresa VARCHAR(255) NOT NULL, " +
                "endereco VARCHAR(255), " +
                "telefone VARCHAR(11), " +
                "email VARCHAR(255)," +
                "tipoEmpresa INTEGER NOT NULL);");
        db.execSQL("CREATE TABLE IF NOT EXISTS usuario " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "nomeUsuario VARCHAR(255) NOT NULL, " +
                "documento VARCHAR(255) NOT NULL, " +
                "telefone VARCHAR(11), " +
                "email VARCHAR(255)," +
                "Usuario_idEmpresa INT NOT NULL," +
                "FOREIGN KEY (Usuario_idEmpresa)" +
                "REFERENCES empresa (id));");
        db.execSQL("CREATE TABLE IF NOT EXISTS aparelho " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "nomeAparelho VARCHAR(255) NOT NULL," +
                "nserieAparelho INT(255) NOT NULL," +
                "fabricanteAparelho INT(255) NOT NULL," +
                "categoriaAparelho VARCHAR(255) NOT NULL," +
                "modeloAparelho INT(255) NOT NULL," +
                "Aparelho_idEmpresa INT NOT NULL," +
                "FOREIGN KEY (Aparelho_idEmpresa)" +
                "REFERENCES empresa (id));");
        db.execSQL("CREATE TABLE IF NOT EXISTS calibracao " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "dataCalibracao DATE NOT NULL," +
                "dataEmissao DATE NOT NULL," +
                "dataValidade DATE NOT NULL," +
                "Calibracao_idAparelho INT NOT NULL," +
                "FOREIGN KEY (Calibracao_idAparelho)" +
                "REFERENCES aparelho (id));");
        db.execSQL("CREATE TABLE IF NOT EXISTS certificado " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "Certificado_idUsuario INT NOT NULL," +
                "Certificado_idAparelho INT NOT NULL," +
                "Certificado_idCalibracao INT NOT NULL," +
                "FOREIGN KEY (Certificado_idUsuario)" +
                "REFERENCES usuario (id)," +
                "FOREIGN KEY (Certificado_idAparelho)" +
                "REFERENCES aparelho (id)," +
                "FOREIGN KEY (Certificado_idCalibracao)" +
                "REFERENCES calibracao (id))");
        db.execSQL("CREATE TABLE IF NOT EXISTS caracteristica " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "descricao VARCHAR(256), " +
                "val_padrao REAL(256), " +
                "val_obtido REAL(256), " +
                "val_esperado REAL(256), " +
                "idaparelho INTEGER(255));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
