package br.g3.piapp2;

import java.util.ArrayList;

public class Aluno {
    private int id;
    private String nome;
    private String ra;
    private String senha;
    private ArrayList<Falta> faltas;

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

    public String getRa() {
        return ra;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public ArrayList<Falta> getFaltas() {
        return faltas;
    }

    public void setFaltas(ArrayList<Falta> faltas) {
        this.faltas = faltas;
    }
}
