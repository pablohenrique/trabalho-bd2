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
    //private static final String url = "jdbc:postgresql://177.71.252.132:5432/bd";
    //private static final String url = "jdbc:postgresql://www.caiothomas.com:5432/bd";
    private static final String url = "jdbc:postgresql://localhost:5432/BD2";
    //private static final String url = "jdbc:postgresql://localhost:5432/bd";
    private static final String user = "postgres";
    private static final String pass = "postgres";
    //private static final String pass = "25081992";
    private static Conexao instance = null;
    private static Connection connection;
    
    private Conexao() throws ClassNotFoundException, SQLException, Exception{
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
    
    public static Conexao getInstance() {
        try{
        if(instance != null)
            return instance;
        else
            return new Conexao();
        }
        catch(Exception e){
            return null;
        }
    }
    
    public static Connection getConexao() throws SQLException{
        return connection;
    }
}
