/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.util.ArrayList;
import Model.Projeto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author pablohenrique
 */
public class ProjetoDAO implements IObjectDAO{
    private final String SQL_POST = "INSERT INTO projeto VALUES(?,?,?,?);";
    private final String SQL_GET = "SELECT * FROM projeto WHERE pnumero = ?;";
    private final String SQL_READ = "SELECT * FROM projeto WHERE pjnome = ?;";
    private final String SQL_GETALL = "SELECT * FROM projeto;";
    private final String SQL_UPDATE = "UPDATE projeto SET pjnome = ?, plocalizacao = ?, dnum = ? WHERE pnumero = ?;";
    private final String SQL_DELETE = "DELETE FROM projeto WHERE pnumero = ?;";
    private PreparedStatement ps;

    @Override
    public void post(Object input) {
        try {
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_POST);
            Projeto aux = (Projeto) input;
            
            this.ps.setInt(1,aux.getNumero());
            this.ps.setString(2,aux.getNome());
            this.ps.setString(3,aux.getLocalizacao());
            this.ps.setInt(4,aux.getDepartamento());
            //this.ps.executeQuery se retornar um ResultSet
            //this.ps.execute se retornar mais de um ResultSet
            //this.ps.executeUpdate se nao ha retorno de ResultSet
            
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
            Projeto aux = (Projeto) input;
            
            this.ps.setString(1,aux.getNome());
            this.ps.setString(2,aux.getLocalizacao());
            this.ps.setInt(3,aux.getDepartamento());
            this.ps.setInt(4,aux.getNumero());
            
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
            
            Projeto output = new Projeto();
            output.setNumero(rs.getInt(1));
            output.setNome(rs.getString(2));
            output.setLocalizacao(rs.getString(3));
            output.setDepartamento(rs.getInt(4));
            
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
            
            Projeto output = new Projeto();
            output.setNumero(rs.getInt(1));
            output.setNome(rs.getString(2));
            output.setLocalizacao(rs.getString(3));
            output.setDepartamento(rs.getInt(4));
            
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
                Projeto obj = new Projeto();
                
                obj.setNumero(rs.getInt(1));
                obj.setNome(rs.getString(2));
                obj.setLocalizacao(rs.getString(3));
                obj.setDepartamento(rs.getInt(4));
                
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
