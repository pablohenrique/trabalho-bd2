/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Departamento;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import Model.Localizacao;
import Model.Projeto;
import java.sql.SQLException;

/**
 *
 * @author pablohenrique
 */
public class LocalizacaoDAO implements IObjectDAO{
    private final String SQL_POST = "INSERT INTO dept_localizacao VALUES(?,?);";
    private final String SQL_GET = "SELECT * FROM dept_localizacao WHERE departamento_numero = ?;";
    private final String SQL_READ = "SELECT * FROM dept_localizacao WHERE dlocalizacao = ?;";
    private final String SQL_GETALL = "SELECT * FROM dept_localizacao;";
    private final String SQL_UPDATE = "UPDATE dept_localizacao SET dlocalizacao = ? WHERE departamento_numero = ?;";
    private final String SQL_DELETE = "DELETE FROM dept_localizacao WHERE departamento_numero = ?;";
    private PreparedStatement ps;
    private ResultSet rs;
    
    private Object useObjectTemplate(){
        try {
            Localizacao output = new Localizacao();
            output.setDepartamento((Departamento) FactoryDAO.getFactory("Departamento").get(this.rs.getString(1)));
            output.setNome(this.rs.getString(2));
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
            
            Localizacao aux = (Localizacao) input;
            this.ps.setInt(1,aux.getDepartamento().getNumero());
            this.ps.setString(2,aux.getNome());
            
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
            
            Localizacao aux = (Localizacao) input;
            
            this.ps.setString(1,aux.getNome());
            this.ps.setInt(2,aux.getDepartamento().getNumero());
            
            if(this.ps.executeUpdate() != 1)
                throw new SQLException("Objeto nao foi gravado.");
            
        } catch (Exception e) {
            System.err.println("Erro ao salvar objeto:  " + e.toString() );
        }
    }

    @Override
    public Object get(Object input) {
        try {
            int aux = (int) input;
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_GET);
            this.ps.setInt(1,aux);
            
            this.rs = this.ps.executeQuery();
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
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_READ);
            this.ps.setString(1,aux);
            
            this.rs = this.ps.executeQuery();
            return this.useObjectTemplate();
            
        } catch (Exception e) {
            System.err.println("Erro ao buscar [READ] o objeto:  " + e.toString() );
            return null;
        }
    }

    @Override
    public Object getAll() {
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
            System.err.println("Erro ao buscar [GETALL] o objeto:  " + e.toString() );
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
