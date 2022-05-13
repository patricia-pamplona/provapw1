package com.ufrn.tads.pw1.Dominio;

public class Produto {

    private int id_produto;
    private Double preco;
    private String nome;
    private String Descricao;
    private int estoque;

    public Produto(int id_produto, String nome, Double preco,  String descricao, int estoque) {
        super();
        this.id_produto = id_produto;
        this.nome = nome;
        this.preco = preco;
        this.Descricao = descricao;
        this.estoque = estoque;
    }

    public Produto(){

    }

    public int getId_produto() {
        return id_produto;
    }
    public void setId_produto(int id_produto) {
        this.id_produto = id_produto;
    }
    public Double getPreco() {
        return preco;
    }
    public void setPreco(Double preco) {
        this.preco = preco;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getDescricao() {
        return Descricao;
    }
    public void setDescricao(String descricao) {
        Descricao = descricao;
    }
    public int getEstoque() {
        return estoque;
    }
    public void setEstoque(int estoque){
        this.estoque = estoque;
    }
    public void incrementaEstoque() {
        this.estoque++;
    }
    public void diminuiEstoque() {
        this.estoque--;
    }
}
