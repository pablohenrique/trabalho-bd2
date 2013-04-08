/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Departamento;
import Model.Empregado;
import Model.Localizacao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author pablohenrique
 */
public class LocalizacaoDAO implements IObjectDAO{
    private final String BEFORECOND = 
"SELECT l.dlocalizacao AS l_localizacao, l.departamento_numero AS l_numero,"+
" d.numero AS d_numero, d.nome AS d_nome, d.gerssn AS d_gerssn, d.gerdatainicio AS d_dataInicio"+
" FROM cia.dept_localizacao AS l, cia.departamento AS d";
    private final String AFTERCOND = "l.departamento_numero = d.numero;";
    private final String SQL_POST = "INSERT INTO dept_localizacao VALUES(?,?);";
    private final String SQL_UPDATE = "UPDATE dept_localizacao SET dlocalizacao = ? WHERE departamento_numero = ?;";
    private final String SQL_DELETE = "DELETE FROM dept_localizacao WHERE dlocalizacao = ?;";
    private final String SQL_GET = BEFORECOND + " WHERE d.numero = ? " + AFTERCOND;
    private final String SQL_READ = BEFORECOND + " WHERE l.dlocalizacao LIKE ? " + AFTERCOND;
    private final String SQL_GETALL = BEFORECOND + " WHERE " + AFTERCOND;
    private final String SQL_GETALL_BYDEP = BEFORECOND + " WHERE l.departamento_numero = ? + AFTERCOND";
    private PreparedStatement ps;
    private ResultSet rs;
    
    private Object useObjectTemplate(){
        try {
            DepartamentoDAO depdao = (DepartamentoDAO) FactoryDAO.getFactory("Departamento");
            
            Localizacao output = new Localizacao();
            output.setNome(this.rs.getString("l_localizacao"));
            output.setDepartamento((Departamento) depdao.createObject(this.rs.getInt("d_numero"), this.rs.getString("d_nome"),this.rs.getDate("d_dataInicio"), (Empregado) FactoryDAO.getFactory("Empregado").get(this.rs.getString("d_gerssn"))));
            
            System.gc();
            return output;
            
        } catch (Exception e) {
            System.err.println("Erro [LOCA] useObjectTemplate:  " + e.toString() );
            return null;
        }
    }
    
    private ArrayList<Object> getAllTemplate() throws SQLException{
        ArrayList<Object> output = new ArrayList<>();
            
        while(this.rs.next())
            output.add((Localizacao) this.useObjectTemplate());
        
        return output;
    }
    
    @Override
    public void post(Object input) {
        try {
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_POST);
            
            Localizacao aux = (Localizacao) input;
            this.ps.setInt(1,aux.getDepartamento().getNumero());
            this.ps.setString(2,aux.getNome());
            
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
            
            Localizacao aux = (Localizacao) input;
            
            this.ps.setString(1,aux.getNome());
            this.ps.setInt(2,aux.getDepartamento().getNumero());
            
            System.gc();
            
            if(this.ps.executeUpdate() != 1)
                throw new SQLException("Objeto nao foi gravado.");
            
        } catch (Exception e) {
            System.err.println("Erro ao salvar objeto:  " + e.toString() );
        }
    }

    @Override
    public Object get(Object input) {
        try {
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_GET);
            this.ps.setInt(1,(int) input);
            this.rs = this.ps.executeQuery();
            
            if(!this.rs.next())
                throw new Exception("Departamento nao encontrado.");
            
            return this.useObjectTemplate();
            
        } catch (Exception e) {
            System.err.println("Erro ao buscar [GET] o objeto:  " + e.toString() );
            return null;
        }
    }

    @Override
    public Object read(Object input) {
        try {
            String aux = "'%" + (String) input + "%'";
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_READ);
            this.ps.setString(1,aux);
            this.rs = this.ps.executeQuery();
            
            return this.getAllTemplate();
            
        } catch (Exception e) {
            System.err.println("Erro ao buscar [READ] o objeto:  " + e.toString() );
            return null;
        }
    }

    @Override
    public ArrayList<Object> getAll() {
        try {
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_GETALL);
            this.rs = this.ps.executeQuery();
            
            return this.getAllTemplate();
            
        } catch (Exception e) {
            System.err.println("Erro ao buscar [GETALL] o objeto:  " + e.toString() );
            return null;
        }
    }
    
        public ArrayList<Object> getAllByDept(int deptID) {
        try {
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_GETALL_BYDEP);
            this.ps.setInt(1,deptID);
            this.rs = this.ps.executeQuery();
            
            return this.getAllTemplate();
            
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