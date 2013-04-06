/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Departamento;
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
    private final String BEFORECOND = 
"SELECT d.nome_dependente AS d_nomedependente, d.essn AS d_essn, cia.sexo(d.sexo) AS d_sexo, d.datanasc AS d_datanascimento, d.parentesco AS d_parentesco,"+
" e.ssn AS e_ssn, e.nome AS e_nome, cia.sexo(e.sexo) AS e_sexo, e.endereco AS e_endereco, e.salario AS e_salario, e.datanasc AS e_datanasc, e.dno AS e_dno, e.superssn AS e_superssn, e.senha AS e_senha"+
" FROM cia.dependentes AS d, cia.empregado AS e ";
    private final String AFTERCOND = " AND d.essn = e.ssn ORDER BY d.nome_dependente ASC";
    
    private final String SQL_POST = "INSERT INTO cia.dependentes VALUES(?,?,cia.sexoToBd(?),?,?);";
    private final String SQL_UPDATE = "UPDATE cia.dependentes SET sexo = cia.sexoToBd(?), datanasc = ?, parentesco = ? WHERE essn = ? AND nome_dependente = ? ;";
    private final String SQL_DELETE = "DELETE FROM cia.dependentes WHERE essn = ?;";
    private final String SQL_DELETEFULL = "DELETE FROM cia.dependentes WHERE nome_dependente = ? AND essn = ?;";
    private final String SQL_GET_DEPENDENTE = BEFORECOND + " WHERE d.essn = ? AND d.nome_dependente = ?" + AFTERCOND;
    private final String SQL_GET = BEFORECOND + " WHERE d.essn = ? " + AFTERCOND;
    private final String SQL_READ = BEFORECOND +  " WHERE d.nome LIKE ?" + AFTERCOND;
    private final String SQL_GETALL = BEFORECOND + " WHERE d.essn = e.ssn ORDER BY d.nome_dependente ASC";
    private PreparedStatement ps;
    private ResultSet rs;
    
    private Object useObjectTemplate(){
        try {
            String column = "d_";
                        
            EmpregadoDAO empdao = (EmpregadoDAO) FactoryDAO.getFactory("Empregado");
            DepartamentoDAO depdao = (DepartamentoDAO) FactoryDAO.getFactory("Departamento");
            
            Departamento dep = (Departamento) depdao.get(this.rs.getInt("e_dno"));
            
            Empregado supervisor = (Empregado) empdao.get(this.rs.getString("e_superssn"));
            Empregado emp = (Empregado) empdao.createObject(this.rs.getString("e_ssn"), this.rs.getString("e_nome"), this.rs.getString("e_sexo"), this.rs.getString("e_endereco"), this.rs.getFloat("e_salario"), this.rs.getDate("e_datanasc"), this.rs.getString("e_senha"), supervisor, dep);
            
            Dependente output = new Dependente();
            output.setNome(this.rs.getString(column+"nomedependente"));
            output.setSexo(this.rs.getString(column+"sexo"));
            output.setDataNascimento(this.rs.getDate(column+"datanascimento"));
            output.setParentesco(this.rs.getString(column+"parentesco"));
            output.setEssn(emp);
            
            System.gc();
            return output;
            
        } catch (Exception e) {
            System.err.println("Erro [DEPE] useObjectTemplate:  " + e.toString() );
            return null;
        }
    }
    
    private ArrayList<Object> getAllTemplate() throws SQLException{
        ArrayList<Object> output = new ArrayList<>();
        while(this.rs.next())
            output.add((Dependente) this.useObjectTemplate());

        //if(output.isEmpty())
        //    throw new ArrayStoreException("Nao houve objetos encontrados.");
        
        return output;
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
            Dependente aux = (Dependente) input;
            
            this.ps.setString(1,aux.getSexo());
            this.ps.setDate(2,aux.getDataNascimento());
            this.ps.setString(3,aux.getParentesco());
            this.ps.setString(4,aux.getEssn().getSsn());
            this.ps.setString(5,aux.getNome());

            
            System.gc();
            
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
            
            return this.getAllTemplate();
            
        } catch (Exception e) {
            System.err.println("Erro ao buscar [GET] o objeto:  " + e.toString() );
            return null;
        }
    }
    
        public Object getDependente(String essn, String nome) {
        try {
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_GET_DEPENDENTE);
            this.ps.setString(1,essn);
            this.ps.setString(2,nome);
            this.rs = this.ps.executeQuery();
            
            
            return this.getAllTemplate().get(0);
            
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
            
            return this.getAllTemplate();
            
        } catch (Exception e) {
            System.err.println("Erro ao buscar [READ] o objeto:  " + e.toString() );
            return null;
        }
    }

    @Override
    public ArrayList<Object> getAll() throws Exception {
        try {
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_GETALL);
            this.rs = this.ps.executeQuery();
            
            return this.getAllTemplate();
            
        } catch (Exception e) {
            throw new Exception("Erro ao recuperar todos os objeto:  " + e.toString() );
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
    
    public void deleteDep(String essn, String nomedependente) throws Exception {
        try {
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_DELETEFULL);
            this.ps.setString(1, nomedependente);
            this.ps.setString(2, essn);
            if (this.ps.executeUpdate() == 0) {
                throw new SQLException("Restricao de integridade de empregado.");
            }

        } catch (Exception e) {
           throw new SQLException("Erro ao apagar dependente:  " + e.toString());
        }
    }
    
    
}
