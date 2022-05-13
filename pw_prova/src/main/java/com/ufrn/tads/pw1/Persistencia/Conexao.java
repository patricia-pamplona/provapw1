package com.ufrn.tads.pw1.Persistencia;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {

    private String caminho;
    private String usuario;
    private String senha;
    private Connection minhaConexao;

    public Conexao (String c, String u, String s) {
        caminho = c;
        usuario = u;
        senha = s;
    }

    public void conectar () {
        try {
            Class.forName("org.postgresql.Driver");
            minhaConexao = DriverManager.getConnection(caminho, usuario, senha);
        }catch (Exception e) {
            System.out.println ("Erro na conexão: " + e.getMessage());
        }
    }

    public void desconectar() {
        try {
            minhaConexao.close ();
        }catch (Exception e) {
            System.out.println("Erro na desconexão: " + e.getMessage());
        }
    }

    public Connection getConexao() {
        return minhaConexao;
    }

}
