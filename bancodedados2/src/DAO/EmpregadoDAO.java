/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.util.ArrayList;
import Model.Empregado;
import java.sql.PreparedStatement;

/**
 *
 * @author pablohenrique
 */
public class EmpregadoDAO implements IObjectDAO{
    private final String SQL_POST = "";
    private final String SQL_GET = "";
    private final String SQL_GETALL = "";
    private final String SQL_UPDATE = "";
    private final String SQL_DELETE = "";
    private PreparedStatement ps;
    
    @Override
    public void post(Object input) {
        try {
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_POST);
            MUDARAQUI aux = (MUDARAQUI) input;
            
            //this.ps.setInt(1,aux.getNumero());
            
            if(this.ps.executeUpdate() != 1)
                throw new SQLException("Objeto nao foi gravado.");
            
        } catch (Exception e) {
            System.err.println("Erro ao salvar objeto:  " + e.toString() );
        }
    }

    @Override
    public void update(Object input) {
        try {
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_UPDATE);
            MUDARAQUI aux = (MUDARAQUI) input;
            
            //this.ps.setString(1,aux.getNome());
            
            if(this.ps.executeUpdate() != 1)
                throw new SQLException("Objeto nao foi atualizado.");
            
        } catch (Exception e) {
            System.err.println("Erro ao atualizar o objeto:  " + e.toString() );
        }
    }

    @Override
    public Object get(int input) {
        try {
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_GET);
            this.ps.setInt(1,input);
            ResultSet rs = this.ps.executeQuery();
            
            MUDARAQUI output = new MUDARAQUI();
            //output.setNumero(rs.getInt(1));
            
            return output;
        } catch (Exception e) {
            System.err.println("Erro ao buscar [GET] o objeto:  " + e.toString() );
            return null;
        }
    }
    
    @Override
    public Object read(String input) {
        try {
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_READ);
            this.ps.setString(1,input);
            ResultSet rs = this.ps.executeQuery();
            
            MUDARAQUI output = new MUDARAQUI();
            //output.setNumero(rs.getInt(1));
            
            return output;
        } catch (Exception e) {
            System.err.println("Erro ao buscar [READ] o objeto:  " + e.toString() );
            return null;
        }
    }

    @Override
    public ArrayList<Object> getAll() {
        try {
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_GETALL);
            ArrayList<Object> output = new ArrayList<>();
            
            ResultSet rs = this.ps.executeQuery();
            while(rs.next()){
                MUDARAQUI obj = new MUDARAQUI();
                
                //obj.setNumero(rs.getInt(1));
                
                output.add(obj);
            }
            
            if(output.isEmpty())
                throw new ArrayStoreException("Nao houve objetos encontrados.");
            return output;
            
        } catch (Exception e) {
            System.err.println("Erro ao recuperar todos os objeto:  " + e.toString() );
            return null;
        }
    }

    @Override
    public void delete(int input) {
        try {
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_DELETE);
            this.ps.setInt(1,input);
            
            if(this.ps.executeUpdate() != 1)
                throw new SQLException("Objeto nao foi deletado.");
            
        } catch (Exception e) {
            System.err.println("Erro ao salvar objeto:  " + e.toString() );
        }
    }
}
