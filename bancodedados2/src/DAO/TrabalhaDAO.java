/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Empregado;
import Model.Projeto;
import Model.Trabalha;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author pablohenrique
 */
public class TrabalhaDAO implements IObjectDAO{
    private final String SQL_POST = "INSERT INTO trabalha_em VALUES(?,?,?);";
    private final String SQL_GET = "SELECT * FROM trabalha_em WHERE essn = ?;";
    private final String SQL_READ = "SELECT * FROM trabalha_em WHERE trabalha_em_pnumero = ?;";
    private final String SQL_GETALL = "SELECT * FROM trabalha_em;";
    private final String SQL_UPDATE = "UPDATE trabalha_em SET trabalha_em_pnumero = ?, horas = ? WHERE essn = ?;";
    private final String SQL_DELETE = "DELETE FROM trabalha_em WHERE essn = ?;";
    private PreparedStatement ps;
    private ResultSet rs;
    
    private Object useObjectTemplate(){
        try {
            Trabalha output = new Trabalha();
            output.setEssn((Empregado) FactoryDAO.getFactory("Empregado").get(this.rs.getString(1)));
            output.setProjeto((Projeto) FactoryDAO.getFactory("Projeto").get(this.rs.getInt(2)));
            output.setHoras(this.rs.getFloat(3));
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
            
            Trabalha aux = (Trabalha) input;
            this.ps.setString(1,aux.getEssn().getSsn());
            this.ps.setInt(2,aux.getProjeto().getNumero());
            this.ps.setFloat(3,aux.getHoras());
            
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
            
            Trabalha aux = (Trabalha) input;
            this.ps.setInt(1,aux.getProjeto().getNumero());
            this.ps.setFloat(2,aux.getHoras());
            this.ps.setString(3,aux.getEssn().getSsn());
            
            if(this.ps.executeUpdate() != 1)
                throw new SQLException("Objeto nao foi gravado.");
            
        } catch (Exception e) {
            System.err.println("Erro ao atualizar objeto:  " + e.toString() );
        }
    }

    @Override
    public Object get(Object input) {
        try {
            String aux = (String) input;
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_GET);
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
            System.err.println("Erro ao buscar [GET] o objeto:  " + e.toString() );
            return null;
        }
    }

    @Override
    public Object read(Object input) {
        try {
            int aux = (int) input;
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_READ);
            this.ps.setInt(1,aux);
            
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
            System.err.println("Erro ao buscar [GETALL] o objeto:  " + e.toString() );
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
            System.err.println("Erro ao deletar objeto:  " + e.toString() );
        }
    }
    
}
