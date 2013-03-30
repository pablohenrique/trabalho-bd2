/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Departamento;
import Model.Empregado;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author pablohenrique
 */
public class DepartamentoDAO implements IObjectDAO{
    private final String SQL_POST = "INSERT INTO cia.departamento VALUES(?,?,?,?);";
    private final String SQL_GET = "SELECT * FROM cia.departamento WHERE numero = ?;";
    private final String SQL_READ = "SELECT * FROM cia.departamento WHERE nome = ?;";
    private final String SQL_GETALL = "SELECT * FROM cia.departamento;";
    private final String SQL_UPDATE = "UPDATE cia.departamento SET nome = ?, gerssn = ?, gerdatainicio = ? WHERE numero = ?";
    private final String SQL_DELETE = "DELETE FROM cia.departamento WHERE numero = ?";
    private PreparedStatement ps;
    private ResultSet rs;
    
    public Object useObjectTemplate(String column){
        try {
            Departamento output = new Departamento();
            output.setNumero(this.rs.getInt(rs.findColumn(column+"numero")));
            output.setNome(this.rs.getString(rs.findColumn(column+"nome")));
            //output.setGerenteSsn((Empregado) FactoryDAO.getFactory("Empregado").get(this.rs.getString(3)));
            output.setGerenteSsn(null);
            output.setGerenteDataInicio(this.rs.getDate((rs.findColumn(column+"dataInicio"))));
            
            System.gc();
            
            return output;
            
        } catch (Exception e) {
            System.err.println("Erro useObjectTemplate:  " + e.toString() );
            return null;
        }
    }
    
    public Object createObject(int numero, String nome, Date gerenteInicio, Empregado gerente){
        Departamento dep = new Departamento();
        dep.setNumero(numero);
        dep.setNome(nome);
        dep.setGerenteDataInicio(gerenteInicio);
        dep.setGerenteSsn(gerente);
        gerente.setDepartamento(dep);
        System.gc();
        return dep;
    }
    
    @Override
    public void post(Object input) {
        try {
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_POST);
            
            Departamento aux = (Departamento) input;
            this.ps.setInt(1,aux.getNumero());
            this.ps.setString(2,aux.getNome());
            this.ps.setString(3,aux.getGerenteSsn().getSsn());
            this.ps.setDate(4,aux.getGerenteDataInicio());
            
            System.gc();
            
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
            
            Departamento aux = (Departamento) input;
            this.ps.setString(1,aux.getNome());
            this.ps.setString(2,aux.getGerenteSsn().getSsn());
            this.ps.setDate(3,aux.getGerenteDataInicio());
            this.ps.setInt(4,aux.getNumero());
            
            System.gc();
            
            if(this.ps.executeUpdate() == 0)
                throw new SQLException("Objeto nao foi atualizado.");
            
        } catch (Exception e) {
            System.err.println("Erro ao atualizar o objeto:  " + e.toString() );
        }
    }

    @Override
    public Object get(Object input) {
        try {
            int aux = (int) input;
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_GET);
            this.ps.setInt(1,aux);
            
            this.rs = this.ps.executeQuery();
            if(!this.rs.next())
                throw new Exception("Departamento nao encontrado.");
            
            return this.useObjectTemplate("");
            
        } catch (Exception e) {
            System.err.println("Erro ao buscar [GET] o objeto:  " + e.toString() );
            return null;
        }
    }
    
    @Override
    public Object read(Object input) {
        try {
            String aux = (String) input;
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_READ);
            this.ps.setString(1,aux);
            
            this.rs = this.ps.executeQuery();
            if(!this.rs.next())
                throw new Exception("Departamento nao encontrado.");
            
            return this.useObjectTemplate("");
            
        } catch (Exception e) {
            System.err.println("Erro ao buscar [READ] o objeto:  " + e.toString() );
            return null;
        }
    }

    @Override
    public Object getAll() {
        try {
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_GETALL);
            ArrayList<Departamento> output = new ArrayList<>();
            
            this.rs = this.ps.executeQuery();
            while(this.rs.next()){
                output.add((Departamento) this.useObjectTemplate(""));
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
            System.err.println("Erro ao deletar objeto:  " + e.toString() );
        }
    }
    
}
