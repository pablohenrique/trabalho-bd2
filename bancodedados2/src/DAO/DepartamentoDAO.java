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
    private final String BEFORECOND = 
" SELECT d.numero AS d_numero, d.nome AS d_nome, d.gerssn AS d_gerssn, d.gerdatainicio AS d_dataInicio, " +
" e.ssn AS e_ssn, e.nome AS e_nome, cia.sexo(e.sexo) AS e_sexo, e.endereco AS e_endereco, e.salario AS e_salario, e.datanasc AS e_datanasc, e.dno AS e_dno, e.superssn AS e_superssn, e.senha AS e_senha "+
" FROM cia.empregado AS e, cia.departamento AS d ";
    
        private final String BEFORECOND3 = 
"SELECT d.numero AS d_numero, d.nome AS d_nome, d.gerssn AS d_gerssn, d.gerdatainicio AS d_dataInicio, "+
" e.ssn AS e_ssn, e.nome AS e_nome, cia.sexo(e.sexo) AS e_sexo, e.endereco AS e_endereco, e.salario AS e_salario, e.datanasc AS e_datanasc, e.dno AS e_dno, e.superssn AS e_superssn, e.senha AS e_senha "+
" FROM ((cia.empregado AS e LEFT JOIN cia.departamento AS d ON d.gerssn = e.ssn)) ";
          
    private final String SQL_POST = "INSERT INTO cia.departamento(nome, gerssn, gerdatainicio) VALUES(?,?,?);";
    private final String SQL_UPDATE = "UPDATE cia.departamento SET nome = ?, gerssn = ?, gerdatainicio = ? WHERE numero = ?";
    private final String SQL_DELETE = "DELETE FROM cia.departamento WHERE numero = ?";
    private final String SQL_GET = BEFORECOND + " WHERE d.numero = ? AND d.gerssn = e.ssn;";
    private final String SQL_READ = BEFORECOND + " WHERE d.nome LIKE UPPER(?) AND d.gerssn = e.ssn;";
    private final String SQL_GETGER = BEFORECOND3 + " WHERE d.gerssn = ? AND d.gerssn = e.ssn;";
    private final String SQL_GETALL = BEFORECOND3 + " WHERE d.gerssn = e.ssn;";
    private final String SQL_EDITAR = "SELECT d.numero AS d_numero, d.nome AS d_nome, d.gerssn AS d_gerssn, d.gerdatainicio AS d_dataInicio FROM departamento;";
    
    private PreparedStatement ps;
    private ResultSet rs;
    
    public Object criarObjetoTemplate(){
        try {
            String column = "d_";
            Departamento output = new Departamento();
            output.setNumero(this.rs.getInt(column+"numero"));
            output.setNome(this.rs.getString(column+"nome"));
            output.setGerenteDataInicio(this.rs.getDate(column+"dataInicio"));
            
            EmpregadoDAO dao = (EmpregadoDAO) FactoryDAO.getFactory("Empregado");
            Empregado emp = (Empregado) dao.gerarObjeto(this.rs.getString("e_ssn"), this.rs.getString("e_nome"), this.rs.getString("e_sexo"), this.rs.getString("e_endereco"), this.rs.getFloat("e_salario"), this.rs.getDate("e_datanasc"), this.rs.getString("e_senha"), null, null);
            
            //output.setGerenteSsn((Empregado) FactoryDAO.getFactory("Empregado").get(this.rs.getString("e_superssn")));
            output.setGerenteSsn(emp);
            System.gc();
            return output;
            
        } catch (Exception e) {
            System.err.println("Erro [DEPA] useObjectTemplate:  " + e.toString() );
            return null;
        }
    }
    
    public Object gerarObjeto(int numero, String nome, Date gerenteInicio, Empregado gerente){
        Departamento dep = new Departamento();
        dep.setNumero(numero);
        dep.setNome(nome);
        dep.setGerenteDataInicio(gerenteInicio);
        dep.setGerenteSsn(gerente);
        //gerente.setDepartamento(dep);
        
        System.gc();
        
        return dep;
    }
    
    public ArrayList<Object> buscarVariosObjetosTemplate() throws SQLException{
        ArrayList<Object> output = new ArrayList<>();
        while(this.rs.next())
            output.add((Departamento) this.criarObjetoTemplate());
        
        return output;
    }
    
    @Override
    public void post(Object input) {
        try {
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_POST);
            
            Departamento aux = (Departamento) input;
            this.ps.setString(1,aux.getNome());
            this.ps.setString(2,aux.getGerenteSsn().getSsn());
            this.ps.setDate(3,aux.getGerenteDataInicio());
            
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
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_GET);
            this.ps.setInt(1,(int) input);
            this.rs = this.ps.executeQuery();
            
            if(!this.rs.next())
                throw new Exception("Departamento nao encontrado.");
            
            return this.criarObjetoTemplate();
            
        } catch (Exception e) {
            System.err.println("Erro ao buscar [GET] o objeto:  " + e.toString() );
            return null;
        }
    }
    
    @Override
    public Object read(Object input) {
        try {
            String aux = "%" + (String) input + "%";
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_READ);
            this.ps.setString(1,aux);
            this.rs = this.ps.executeQuery();
            
            if(this.rs.isLast())
                return this.criarObjetoTemplate();
            else
                return this.buscarVariosObjetosTemplate();
            
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
            
            return this.buscarVariosObjetosTemplate();
            
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
    
    public ArrayList<Object> buscarGerente(String gerssn) {
        try {
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_GETGER);
            this.ps.setString(1,gerssn);
            this.rs = this.ps.executeQuery();
            
            return this.buscarVariosObjetosTemplate();
            
        } catch (Exception e) {
            System.err.println("Erro ao buscar [GET] o objeto:  " + e.toString() );
            return null;
        }
    }
    
    public ArrayList<Object> buscarEmpregados(){
        try {
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_EDITAR);
            this.rs = this.ps.executeQuery();
            
            return this.buscarVariosObjetosTemplate();
            
        } catch (Exception e) {
            System.err.println("Erro ao buscar [GET] o objeto:  " + e.toString() );
            return null;
        }
    }
    
}
