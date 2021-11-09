/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.infox.dal;

import java.sql.*;

/**
 *
 * @author hudso
 */
public class ModuloConexao {

    //método responsável por estabelecer a conexão com o banco
    public static Connection conector() {
        Connection conexao = null;
        String driver = "com.mysql.cj.jdbc.Driver";
        //Armazenando informações referente ao banco
        String url = "jdbc:mysql://localhost:3306/db_infox";
        String user = "root";
        String password = "";
        //Estabelecendo a conexão com o banco
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, password);
            return conexao;
        } catch (Exception e) {
            return null;
        }
    }
}
