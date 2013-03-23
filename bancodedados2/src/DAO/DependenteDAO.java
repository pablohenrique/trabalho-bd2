/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Dependente;
import Model.Empregado;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 *
 * @author pablohenrique
 */
public class DependenteDAO implements IObjectDAO{
    private final String SQL_POST = "INSERT INTO dependentes VALUES(?,?,?,?,?);";
    private final String SQL_GET = "SELECT * FROM dependentes WHERE essn = ?;";
    private final String SQL_READ = "SELECT * FROM dependentes WHERE nome = ?;";
    private final String SQL_GETALL = "SELECT * FROM dependentes;";
    private final String SQL_UPDATE = "UPDATE dependentes SET nome_dependente = ?, sexo = ?, datanasc = ?, parentesco = ? WHERE essn = ?;";
    private final String SQL_DELETE = "DELETE FROM dependentes WHERE nome_dependente = ?;";
    private PreparedStatement ps;
    private ResultSet rs;
    
    private Object useObjectTemplate(){
        try {
            Dependente output = new Dependente();
            
            output.setNome(this.rs.getString(1));
            output.setEssn((Empregado) FactoryDAO.getFactory("Empregado").get(this.rs.getString(2)));
            output.setSexo(this.rs.getString(3));
            output.setDataNascimento(this.rs.getDate(4));
            output.setParentesco(this.rs.getString(5));
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
            
            Dependente aux = (Dependente) input;
            this.ps.setString(1,aux.getNome());
            this.ps.setString(2,aux.getEssn().getSsn());
            this.ps.setString(3,aux.getSexo());
            this.ps.setDate(4,aux.getDataNascimento());
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
            this.ps.setDate(3,aux.getDataNascimento());
            this.ps.setString(4,aux.getParentesco());
            this.ps.setString(5,aux.getEssn().getSsn());
            
            if(this.ps.executeUpdate() == 0)
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
                throw new Exception("Dependente nao encontrado.");
            
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
            ArrayList<Dependente> output = new ArrayList<>();
            
            this.rs = this.ps.executeQuery();
            while(this.rs.next()){
                output.add((Dependente) this.useObjectTemplate());
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
    public Object getAll() {
        try {
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_GETALL);
            ArrayList<Dependente> output = new ArrayList<>();
            
            this.rs = this.ps.executeQuery();
            while(this.rs.next()){
                output.add((Dependente) this.useObjectTemplate());
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
            this.ps.setString(1,(String) input);
            
            if(this.ps.executeUpdate() == 0)
                throw new SQLException("Objeto nao foi deletado.");
            
        } catch (Exception e) {
            System.err.println("Erro ao salvar objeto:  " + e.toString() );
        }
    }
}
