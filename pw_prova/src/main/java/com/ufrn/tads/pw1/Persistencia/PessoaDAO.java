package com.ufrn.tads.pw1.Persistencia;

import com.ufrn.tads.pw1.Dominio.Pessoa;
import com.ufrn.tads.pw1.Dominio.Produto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class PessoaDAO {

    private Conexao minhaConexao;

    private final String selectGeral = "select * from Pessoa where categoria = ?";
    private final String SelectPorEmail = "select * from Pessoa where email = ?";
    private final String inserir = "INSERT INTO Pessoa (nome, email, senha, categoria) values (?, ?, ?,?) ";
    private final String alterar = "update Pessoa set nome = ?, email = ? , senha = ?, categoria = ? where id = ? ";

    public PessoaDAO() {
        minhaConexao = new Conexao ("jdbc:postgresql://localhost:5432/projProgWeb1", "postgres", "abc123");
    }


    public void adicionar (Pessoa p){
        try{
            minhaConexao.conectar();
            PreparedStatement instrucao  = minhaConexao.getConexao().prepareStatement(inserir);
            instrucao.setString(1, p.getNome());
            instrucao. setString(2, p.getEmail());
            instrucao.setString(3, p.getSenha());
            instrucao.setBoolean(4, p.getCategoria());
            instrucao.execute();
            minhaConexao.desconectar();
        }
        catch (Exception e){
            System.out.println("Erro PessoaDAO.adicionar: " + e.getMessage());
        }
    }

    public void alterar (Pessoa p){
        try {
            minhaConexao.conectar();
            PreparedStatement instrucao = minhaConexao.getConexao().prepareStatement(alterar);
            instrucao.setString(1, p.getNome());
            instrucao.setString(2, p.getEmail());
            instrucao.setString(3, p.getSenha());
            instrucao.setBoolean(4, p.getCategoria());
            instrucao.execute();
            minhaConexao.desconectar();

        }catch (Exception e){
            System.out.println("Error PessoaDAO.alterar: " + e.getMessage());
        }
    }

    public ArrayList<Pessoa> listar(Boolean categoria){
        ArrayList<Pessoa> resultado = new ArrayList();

        try{
            minhaConexao.conectar();
            PreparedStatement instrucao = minhaConexao.getConexao().prepareStatement(selectGeral);
            instrucao.setBoolean(1, categoria);
            ResultSet resultSet = instrucao.executeQuery();
            while (resultSet.next()){
                Pessoa pessoas = new Pessoa(resultSet.getInt("id_pessoa"), resultSet.getString("nome"), resultSet.getString("email"), resultSet.getString("senha"), resultSet.getBoolean("categoria"));
                resultado.add(pessoas);
            }
            minhaConexao.desconectar();

        }catch (Exception e){
            System.out.println("Erro PessoaDAO.listar: " + e.getMessage());
        }
        return resultado;
    }

    public Pessoa consultarPorEmail (String email){
        Pessoa resultado = null;

        try {
            minhaConexao.conectar();
            PreparedStatement instrucao = minhaConexao.getConexao().prepareStatement(SelectPorEmail);
            instrucao.setString(1, email);
            ResultSet resultSet = instrucao.executeQuery();
            if(resultSet.next()) {
                resultado = new Pessoa(resultSet.getInt("id_pessoa"), resultSet.getString("nome"), resultSet.getString("email"), resultSet.getString("senha"), resultSet.getBoolean("categoria"));
            }
            minhaConexao.desconectar();
        }catch (Exception e) {
            System.out.println("Erro PessoaDAO.consultarPorEmail: " + e.getMessage());
        }
        return resultado;
    }

}