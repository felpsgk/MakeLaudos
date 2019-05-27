package com.example.felps.calcs.model;

public class Aparelho {

    private int id;
    private String nome;
    private int nserie;

    public Aparelho() {

    }

    public Aparelho(int id, String nome, int nserie) {
        this.id = id;
        this.nome = nome;
        this.nserie = nserie;
    }

    @Override
    public String toString() {
        return nome;
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

    public int getNserie() {
        return nserie;
    }

    public void setNserie(int nserie) {
        this.nserie = nserie;
    }
}
