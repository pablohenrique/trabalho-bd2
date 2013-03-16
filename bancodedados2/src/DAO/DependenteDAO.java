/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.util.ArrayList;
import Model.Dependente;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 *
 * @author pablohenrique
 */
public class DependenteDAO implements IObjectDAO{
    private final String SQL_POST = "INSERT INTO dependentes VALUES(?,?,?,?,?);";
    private final String SQL_GET = "SELECT * FROM dependentes WHERE essn = ?;";
    private final String SQL_GETALL = "SELECT * FROM dependentes;";
    private final String SQL_UPDATE = "UPDATE dependentes SET nome_dependente = ?, sexo = ?, datanasc = ?, parentesco = ? WHERE essn = ?;";
    private final String SQL_DELETE = "DELETE FROM dependentes WHERE essn = ?;";
    private PreparedStatement ps;

    @Override
    public void post(Object input) {
        try {
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_POST);
            Dependente aux = (Dependente) input;
            
            this.ps.setString(1,aux.getNome());
            this.ps.setString(2,aux.getEssn());
            this.ps.setString(3,aux.getSexo());
            this.ps.setDate(4,aux.getDatanascimento());
            this.ps.setString(5,aux.getParentesco());
            
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
            Dependente aux = (Dependente) input;
            
            this.ps.setString(1,aux.getNome());
            this.ps.setString(2,aux.getSexo());
            this.ps.setDate(3,aux.getDatanascimento());
            this.ps.setString(4,aux.getParentesco());
            this.ps.setString(5,aux.getEssn());
            
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
            
            Dependente output = new Dependente();
            output.setNumero(rs.getInt(1));
            
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
            
            Dependente output = new Dependente();
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
                Dependente obj = new Dependente();
                
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
