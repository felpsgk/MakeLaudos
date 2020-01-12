package com.example.felps.calcs.model;

import java.io.Serializable;

public class Aparelho implements Serializable {

    private int id;
    private String nomeAparelho;
    private int nserieAparelho;
    private int fabricanteAparelho;
    private String categoriaAparelho;
    private int modeloAparelho;
    private int empresa;

    public Aparelho() {

    }

    public Aparelho(int id, String nomeAparelho, int nserieAparelho, int fabricanteAparelho, String categoriaAparelho, int modeloAparelho, int empresa) {
        this.id = id;
        this.nomeAparelho = nomeAparelho;
        this.nserieAparelho = nserieAparelho;
        this.fabricanteAparelho = fabricanteAparelho;
        this.categoriaAparelho = categoriaAparelho;
        this.modeloAparelho = modeloAparelho;
        this.empresa = empresa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeAparelho() {
        return nomeAparelho;
    }

    public void setNomeAparelho(String nomeAparelho) {
        this.nomeAparelho = nomeAparelho;
    }

    public int getNserieAparelho() {
        return nserieAparelho;
    }

    public void setNserieAparelho(int nserieAparelho) {
        this.nserieAparelho = nserieAparelho;
    }

    public int getFabricanteAparelho() {
        return fabricanteAparelho;
    }

    public void setFabricanteAparelho(int fabricanteAparelho) {
        this.fabricanteAparelho = fabricanteAparelho;
    }

    public String getCategoriaAparelho() {
        return categoriaAparelho;
    }

    public void setCategoriaAparelho(String categoriaAparelho) {
        this.categoriaAparelho = categoriaAparelho;
    }

    public int getModeloAparelho() {
        return modeloAparelho;
    }

    public void setModeloAparelho(int modeloAparelho) {
        this.modeloAparelho = modeloAparelho;
    }

    public int getEmpresa() {
        return empresa;
    }

    public void setEmpresa(int empresa) {
        this.empresa = empresa;
    }
}
