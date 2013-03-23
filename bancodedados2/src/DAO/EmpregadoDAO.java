/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Departamento;
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
    private final String SQL_LOGIN = "SELECT login(?,?);";
    private PreparedStatement ps;
    private ResultSet rs;
    
    private Object useObjectTemplate(){
        try {
            Empregado output = new Empregado();
            output.setSsn(this.rs.getString(1));
            output.setNome(this.rs.getString(2));
            output.setSexo(this.rs.getString(3));
            output.setEndereco(this.rs.getString(4));
            output.setSalario(this.rs.getFloat(5));
            output.setDataNascimento(this.rs.getDate(6));
            output.setDepartamento( (Departamento) FactoryDAO.getFactory("Departamento").get(this.rs.getInt(7)) );
            if(this.rs.getString(8) != this.rs.getString(1))
                output.setSuperSsn((Empregado) FactoryDAO.getFactory("Empregado").get(this.rs.getString(8)));
            else
                output.setSuperSsn(null);
            output.setSenha(this.rs.getString(9));
            return output;
            
        } catch (Exception e) {
            System.err.println("Erro useObjectTemplate:  " + e.toString() );
            return null;
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
            this.ps.setInt(7,aux.getDepartamento().getNumero());
            this.ps.setString(8,aux.getSuperSsn().getSsn());
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
            this.ps.setString(9,aux.getSsn());
            this.ps.setString(1,aux.getNome());
            this.ps.setString(2,aux.getSexo());
            this.ps.setString(3,aux.getEndereco());
            this.ps.setFloat(4,aux.getSalario());
            this.ps.setDate(5,aux.getDataNascimento());
            this.ps.setInt(6,aux.getDepartamento().getNumero());
            this.ps.setString(7,aux.getSuperSsn().getSsn());
            this.ps.setString(8,aux.getSenha());
            
            if(this.ps.executeUpdate() != 1)
                throw new SQLException("Objeto nao foi atualizado.");
            
        } catch (Exception e) {
            System.err.println("Erro ao atualizar o objeto:  " + e.toString() );
        }
    }

    @Override
    public Object get(Object input) {
        try {
            String aux = (String) input;
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_GET);
            this.ps.setString(1,aux);
            
            this.rs = this.ps.executeQuery();
            if(!this.rs.next())
                throw new Exception("Empregado nao encontrado.");
            
            return this.useObjectTemplate();
            
        } catch (Exception e) {
            System.err.println("Erro ao buscar [GET] o objeto:  " + e.toString() );
            return null;
        }
    }
    
    @Override
    public Object read(Object input) {
        try {
            String aux = (String) input;
            aux = "'%"+aux+"%'";
            
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_READ);
            this.ps.setString(1,aux);
            
            ArrayList<Object> output = new ArrayList<>();
            
            this.rs = this.ps.executeQuery();
            while(rs.next()){
                output.add(this.useObjectTemplate());
            }
            
            if(output.isEmpty())
                throw new ArrayStoreException("Nao houve objetos encontrados.");
            
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
            
            this.rs = this.ps.executeQuery();
            while(rs.next()){
                output.add(this.useObjectTemplate());
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
    public void delete(Object input) {
        try {
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_DELETE);
            this.ps.setInt(1,(int) input);
            
            if(this.ps.executeUpdate() == 0)
                throw new SQLException("Objeto nao foi deletado.");
            
        } catch (Exception e) {
            System.err.println("Erro ao salvar objeto:  " + e.toString() );
        }
    }
    
    public int access(String user, String password)
    {
        try
        {
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_LOGIN);
            this.ps.setString(1, user);
            this.ps.setString(2, password);
            
            this.rs = this.ps.executeQuery();
            
            if(!this.rs.next())
                throw new SQLException("Login nao pode ser encontrado.");
            
            return this.rs.getInt(1);
            
        }
        catch (Exception e)
        {
            System.err.println("Erro ao logar usuario:  " + e.toString() );
            return -1;
        }
    }
}
