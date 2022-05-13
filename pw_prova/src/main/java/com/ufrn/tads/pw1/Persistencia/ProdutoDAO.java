package com.ufrn.tads.pw1.Persistencia;

import com.ufrn.tads.pw1.Dominio.Pessoa;
import com.ufrn.tads.pw1.Dominio.Produto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ProdutoDAO {

    private Conexao minhaConexao;

    private final String selectGeral = "select * from produtos";
    private final String selectPorId = "select * from produtos where id_produtos = ?";
    private final String inserir = "INSERT INTO produtos (nome, descricao, preco, estoque) values (?, ?, ?, ?) ";
    private final String alterar = "update produtos set nome = ?, descricao = ? , preco = ? , estoque = ? where id_produtos = ? ";
    private final String excluir = "Delete from produtos where id_produtos=? ";

    public ProdutoDAO() {
        minhaConexao = new Conexao ("jdbc:postgresql://localhost:5432/projProgWeb1", "postgres", "abc123");
    }

    public Produto getById(int id_produto){
        Produto resultado = null;
        try{
            minhaConexao.conectar();
            PreparedStatement instrucao = minhaConexao.getConexao().prepareStatement(selectPorId);
            instrucao.setInt(1, id_produto);
            ResultSet resultSet = instrucao.executeQuery();
            if(resultSet.next()) {
                resultado = new Produto(resultSet.getInt("id_produtos"), resultSet.getString("nome"), resultSet.getDouble("preco"), resultSet.getString("descricao"),  resultSet.getInt("estoque"));
            }
            minhaConexao.desconectar();
        } catch (SQLException e) {
            System.out.println("Erro ProdutoDAO.consultarPorId: " + e.getMessage());
        }
        return resultado;
    }

    public void adicionar (Produto pro){
        try{
            minhaConexao.conectar();
            PreparedStatement instrucao  = minhaConexao.getConexao().prepareStatement(inserir);
            instrucao.setString(1, pro.getNome());
            instrucao.setString(2, pro.getDescricao());
            instrucao.setDouble(3, pro.getPreco());
            instrucao.setInt(4, pro.getEstoque());
            instrucao.execute();
            minhaConexao.desconectar();
        }
        catch (Exception e){
            System.out.println("Erro ProdutoDAO.adicionar: " + e.getMessage());
        }
    }

    public void alterar (Produto pro){
        try {
            minhaConexao.conectar();
            PreparedStatement instrucao = minhaConexao.getConexao().prepareStatement(alterar);
            instrucao.setString(1, pro.getNome());
            instrucao. setString(2, pro.getDescricao());
            instrucao.setDouble(3, pro.getPreco());
            instrucao.setInt(4, pro.getEstoque());
            instrucao.execute();
            minhaConexao.desconectar();

        }catch (Exception e){
            System.out.println("Error ProdutoDAO.alterar: " + e.getMessage());
        }
    }

    public void Excluir (int id_produto) {
        try {
            minhaConexao.conectar();
            PreparedStatement instrucao = minhaConexao.getConexao().prepareStatement(excluir);
            instrucao.setInt(1, id_produto);
            instrucao.execute();
            minhaConexao.desconectar();
        }catch (Exception e) {
            System.out.println("Erro ProdutoDAO.Excluir: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public ArrayList<Produto> listar(){
        ArrayList<Produto> resultado = new ArrayList();

        try{
            minhaConexao.conectar();
            Statement instrucao = minhaConexao.getConexao().createStatement();
            ResultSet resultSet = instrucao.executeQuery(selectGeral);
            while (resultSet.next()){
                Produto produtos = new Produto(resultSet.getInt("id_produtos"), resultSet.getString("nome"), resultSet.getDouble("preco"), resultSet.getString("descricao"),  resultSet.getInt("estoque"));
                resultado.add(produtos);
            }
            minhaConexao.desconectar();

        }catch (Exception e){
            System.out.println("Erro ProdutoDAO.listar: " + e.getMessage());
        }
        return resultado;
    }



}
