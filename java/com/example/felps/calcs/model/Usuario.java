package com.example.felps.calcs.model;

import java.io.Serializable;

public class Usuario implements Serializable {
    private int id;
    private String nome;
    private String documento;
    private String contato;

    public Usuario() {
    }

    public Usuario(int id, String nome, String documento, String contato) {
        this.id = id;
        this.nome = nome;
        this.documento = documento;
        this.contato = contato;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }
}
