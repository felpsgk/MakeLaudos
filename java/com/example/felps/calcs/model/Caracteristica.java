package com.example.felps.calcs.model;
import java.io.Serializable;


public class Caracteristica implements Serializable {

    private int id;
    private String descricao;
    private float val_padrao;
    private float val_obtido;
    private float val_esperado;
    private int idAparelho;

    public Caracteristica() {

    }

    public Caracteristica(int id, String descricao, int val_padrao, int val_obtido, int val_esperado, int idAparelho) {
        this.id = id;
        this.descricao = descricao;
        this.val_padrao = val_padrao;
        this.val_obtido = val_obtido;
        this.val_esperado = val_esperado;
        this.idAparelho = idAparelho;
    }

    public float getVal_padrao() {
        return val_padrao;
    }

    public void setVal_padrao(float val_padrao) {
        this.val_padrao = val_padrao;
    }

    public float getVal_obtido() {
        return val_obtido;
    }

    public void setVal_obtido(float val_obtido) {
        this.val_obtido = val_obtido;
    }

    public float getVal_esperado() {
        return val_esperado;
    }

    public void setVal_esperado(float val_esperado) {
        this.val_esperado = val_esperado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getIdAparelho() {
        return idAparelho;
    }

    public void setIdAparelho(int idAparelho) {
        this.idAparelho = idAparelho;
    }
}
