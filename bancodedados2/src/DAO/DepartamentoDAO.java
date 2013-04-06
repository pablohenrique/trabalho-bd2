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
"SELECT d.numero AS d_numero, d.nome AS d_nome, d.gerssn AS d_gerssn, d.gerdatainicio AS d_dataInicio, "+
" e.ssn AS e_ssn, e.nome AS e_nome, cia.sexo(e.sexo) AS e_sexo, e.endereco AS e_endereco, e.salario AS e_salario, e.datanasc AS e_datanasc, e.dno AS e_dno, e.superssn AS e_superssn, e.senha AS e_senha, "+
" l.dlocalizacao AS l_localizacao, l.departamento_numero AS l_numero "+
" FROM ((cia.empregado AS e LEFT JOIN cia.departamento AS d ON d.gerssn = e.ssn) LEFT JOIN cia.dept_localizacao AS l ON l.departamento_numero = d.numero) ";
    private final String AFTERCOND = " AND d.gerssn = e.ssn;";
          
    private final String SQL_POST = "INSERT INTO cia.departamento(nome, gerssn, gerdatainicio) VALUES(?,?,?);";
    private final String SQL_UPDATE = "UPDATE cia.departamento SET nome = ?, gerssn = ?, gerdatainicio = ? WHERE numero = ?";
    private final String SQL_DELETE = "DELETE FROM cia.departamento WHERE numero = ?";
    private final String SQL_GET = BEFORECOND + " WHERE d.numero = ?";//+ AFTERCOND;
    private final String SQL_READ = BEFORECOND + " WHERE d.nome = ?";// + AFTERCOND;
    private final String SQL_GETGER = BEFORECOND + " WHERE d.gerssn = ?";// + AFTERCOND;
    private final String SQL_GETALL = BEFORECOND + " WHERE d.gerssn = e.ssn;";
    
    private PreparedStatement ps;
    private ResultSet rs;
    
    public Object useObjectTemplate(){
        try {
            String column = "d_";
            Departamento output = new Departamento();
            output.setNumero(this.rs.getInt(rs.findColumn(column+"numero")));
            output.setNome(this.rs.getString(rs.findColumn(column+"nome")));
            output.setGerenteDataInicio(this.rs.getDate(column+"dataInicio"));
            output.setLocalizacao(this.rs.getString("l_localizacao"));
            output.setGerenteSsn(null);
            
            EmpregadoDAO empdao = (EmpregadoDAO) FactoryDAO.getFactory("Empregado");
            Empregado supervisor = (Empregado) empdao.get(this.rs.getString("e_superssn"));
            
            Empregado emp = (Empregado) empdao.createObject(this.rs.getString("e_ssn"), this.rs.getString("e_nome"), this.rs.getString("e_sexo"), this.rs.getString("e_endereco"), this.rs.getFloat("e_salario"), this.rs.getDate("e_datanasc"), this.rs.getString("e_senha"), supervisor, output);
            
            if(output.getGerenteSsn() == null)
                output.setGerenteSsn(emp);
            
            System.gc();
            return output;
            
        } catch (Exception e) {
            System.err.println("Erro [DEPA] useObjectTemplate:  " + e.toString() );
            return null;
        }
    }
    
    public Object createObject(int numero, String nome, String local, Date gerenteInicio, Empregado gerente){
        Departamento dep = new Departamento();
        dep.setNumero(numero);
        dep.setNome(nome);
        dep.setLocalizacao(local);
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
            
            return this.useObjectTemplate();
            
        } catch (Exception e) {
            System.err.println("Erro ao buscar [GET] o objeto:  " + e.toString() );
            return null;
        }
    }
    
    public Object getGer(String gerssn) {
        try {
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_GETGER);
            this.ps.setString(1,gerssn);
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
            String aux = (String) input;
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_READ);
            this.ps.setString(1,aux);
            
            this.rs = this.ps.executeQuery();
            if(!this.rs.next())
                throw new Exception("Departamento nao encontrado.");
            
            return this.useObjectTemplate();
            
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
            while(this.rs.next()){
                output.add((Departamento) this.useObjectTemplate());
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
