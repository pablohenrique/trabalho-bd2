/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.*;

/**
 *
 * @author pablohenrique
 */
public class Conexao {
    private static final String url = "jdbc:postgresql://localhost:5432/BD2?searchpath=cia";
    private static final String user = "postgres";
    private static final String pass = "postgres";
    private static Connection connection;
    
    private Conexao(){
        try {
            Class.forName( "org.postgresql.Driver" );
            connection = DriverManager.getConnection(url, user, pass);
        } 
        catch ( ClassNotFoundException cnfex ) {
            System.err.println("Failed to load JDBC/ODBC driver. erro" + cnfex.toString() );
        }
        catch ( SQLException sqlex ) {
            System.err.println( "Unable to connect" + sqlex.toString() );
        }
    }
    
    public static Connection getConexao() throws SQLException{
        if(connection != null)
            return connection;
        else
            throw new SQLException("Conexao nao foi criada");
    }
}
