/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uricer.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author renan
 */
public class Database {
    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/BancoVendas", "usuario", "teste");
        return conn;
}
}
