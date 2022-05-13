package com.ufrn.tads.pw1.Dominio;

public class Pessoa {
    private int id_pessoa ;
    private String nome;
    private String email;
    private String senha;
    private Boolean categoria;

    public Pessoa(int id_pessoa, String nome, String email, String senha, Boolean categoria){
        this.id_pessoa = id_pessoa;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.categoria = categoria;
    }

    public Pessoa(String nome, String email, String senha, Boolean categoria){
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.categoria = categoria;
    }

    public Pessoa(){

    }

    public int getId_pessoa() {

        return id_pessoa;
    }

    public String getNome() {

        return nome;
    }

    public void setNome(String nome) {

        this.nome = nome;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public String getSenha() {

        return senha;
    }

    public void setSenha(String senha) {

        this.senha = senha;
    }

    public Boolean getCategoria() {

        return categoria;
    }
    public void setCategoria(Boolean categoria) {

        this.categoria = categoria;
    }
}
