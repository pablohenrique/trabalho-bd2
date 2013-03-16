/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Empregado;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author pablohenrique
 */
public class EmpregadoDAO implements IObjectDAO{
    private final String SQL_POST = "INSERT INTO empregado VALUES(?,?,?,?,?,?,?,?,?);";
    private final String SQL_GET = "SELECT * FROM empregado WHERE ssn = ?;";
    private final String SQL_READ = "SELECT * FROM empregado WHERE nome LIKE ?;";
    private final String SQL_GETALL = "SELECT * FROM empregado;";
    private final String SQL_UPDATE = "UPDATE empregado SET nome = ?, sexo = ?, endereco = ?, salario = ?, datanascimento = ?, dno = ?, superssn = ?, senha = ? WHERE ssn = ?;";
    private final String SQL_DELETE = "DELETE empregado WHERE ssn = ?;";
    private PreparedStatement ps;
    
    public void createObjectTemplate(Object input) throws SQLException{
        try {
            Empregado aux = (Empregado) input;
            this.ps.setString(1,aux.getSsn());
            this.ps.setString(2,aux.getNome());
            this.ps.setString(3,aux.getSexo());
            this.ps.setString(4,aux.getEndereco());
            this.ps.setFloat(5,aux.getSalario());
            this.ps.setDate(6,aux.getDataNascimento());
            this.ps.setInt(7,aux.getDepartamento());
            this.ps.setString(8,aux.getSuperSsn());
            this.ps.setString(9,aux.getSenha());
        } catch (Exception e) {
            System.err.println("Erro createObjectTemplate:  " + e.toString());
        }
    }
    
    @Override
    public void post(Object input) {
        try {
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_POST);
            Empregado aux = (Empregado) input;
            
            this.ps.setString(1,aux.getSsn());
            this.ps.setString(2,aux.getNome());
            this.ps.setString(3,aux.getSexo());
            this.ps.setString(4,aux.getEndereco());
            this.ps.setFloat(5,aux.getSalario());
            this.ps.setDate(6,aux.getDataNascimento());
            this.ps.setInt(7,aux.getDepartamento());
            this.ps.setString(8,aux.getSuperSsn());
            this.ps.setString(9,aux.getSenha());
            
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
            Empregado aux = (Empregado) input;
            
            this.ps.setString(1,aux.getNome());
            this.ps.setString(2,aux.getSexo());
            this.ps.setString(3,aux.getEndereco());
            this.ps.setFloat(4,aux.getSalario());
            this.ps.setDate(5,aux.getDataNascimento());
            this.ps.setInt(6,aux.getDepartamento());
            this.ps.setString(7,aux.getSuperSsn());
            this.ps.setString(8,aux.getSenha());
            this.ps.setString(9,aux.getSsn());
            
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
            
            Empregado output = new Empregado();
            output.setSsn(rs.getString(1));
            output.setNome(rs.getString(2));
            output.setSexo(rs.getString(3));
            output.setEndereco(rs.getString(4));
            output.setSalario(rs.getFloat(5));
            output.setDataNascimento(rs.getDate(6));
            output.setDepartamento(rs.getInt(7));
            output.setSuperSsn(rs.getString(8));
            output.setSenha(rs.getString(9));
            
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
            input = "'%"+input+"%'";
            this.ps.setString(1,input);
            ResultSet rs = this.ps.executeQuery();
            
            Empregado output = new Empregado();
            output.setSsn(rs.getString(1));
            output.setNome(rs.getString(2));
            output.setSexo(rs.getString(3));
            output.setEndereco(rs.getString(4));
            output.setSalario(rs.getFloat(5));
            output.setDataNascimento(rs.getDate(6));
            output.setDepartamento(rs.getInt(7));
            output.setSuperSsn(rs.getString(8));
            output.setSenha(rs.getString(9));
            
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
                Empregado obj = new Empregado();
                obj.setSsn(rs.getString(1));
                obj.setNome(rs.getString(2));
                obj.setSexo(rs.getString(3));
                obj.setEndereco(rs.getString(4));
                obj.setSalario(rs.getFloat(5));
                obj.setDataNascimento(rs.getDate(6));
                obj.setDepartamento(rs.getInt(7));
                obj.setSuperSsn(rs.getString(8));
                obj.setSenha(rs.getString(9));
                
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
