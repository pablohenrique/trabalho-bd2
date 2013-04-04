/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Departamento;
import Model.Empregado;
import java.util.ArrayList;
import Model.Projeto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author pablohenrique
 */
public class ProjetoDAO implements IObjectDAO{
    private final String BEFORECOND = 
"p.pnumero AS p_numero, p.pjnome AS p_nome, p.plocalizacao AS p_localizacao, " +
"d.numero AS d_numero, d.nome AS d_nome, d.gerssn AS d_gerssn, d.gerdatainicio AS d_dataInicio, " +
"e.ssn AS e_ssn, e.nome AS e_nome, cia.sexo(e.sexo) AS e_sexo, e.endereco AS e_endereco, e.salario AS e_salario, e.datanasc AS e_datanasc, e.dno AS e_dno, e.superssn AS e_superssn, e.senha AS e_senha, " +
"t.essn as t_essn, t.pjnumero AS t_numero, t.horas AS t_horas, " +
" l.dlocalizacao AS l_localizacao, l.departamento_numero AS l_numero "+
" FROM cia.empregado AS e,  cia.projeto AS p,  cia.departamento AS d,  cia.trabalha_em AS t, cia.dept_localizacao as l";
        private final String BEFORECOND_JOIN = 
" p.pnumero AS p_numero, p.pjnome AS p_nome, p.plocalizacao AS p_localizacao, " +
" d.numero AS d_numero, d.nome AS d_nome, d.gerssn AS d_gerssn, d.gerdatainicio AS d_dataInicio, " +
" e.ssn AS e_ssn, e.nome AS e_nome, cia.sexo(e.sexo) AS e_sexo, e.endereco AS e_endereco, e.salario AS e_salario, e.datanasc AS e_datanasc, e.dno AS e_dno, e.superssn AS e_superssn, e.senha AS e_senha, " +
" t.essn as t_essn, t.pjnumero AS t_numero, t.horas AS t_horas, "+
" l.dlocalizacao AS l_localizacao, l.departamento_numero AS l_numero ";

    private final String SQL_POST = "INSERT INTO cia.projeto(pjnome, plocalizacao, dnum) VALUES(?,?,?);";
    private final String SQL_UPDATE = "UPDATE cia.projeto SET pjnome = ?, plocalizacao = ?, dnum = ? WHERE pnumero = ?;";
    private final String SQL_DELETE = "DELETE FROM cia.projeto WHERE pnumero = ?;";
    private final String SQL_GET = "SELECT DISTINCT(p.pnumero)," + BEFORECOND + " WHERE p.pnumero = ? AND t.pjnumero = p.pnumero AND d.gerssn = e.ssn AND p.dnum = d.numero AND l.departamento_numero = e.dno;";
    private final String SQL_READ = "SELECT DISTINCT(p.pnumero)," + BEFORECOND + " WHERE p.pjnome = ? AND t.pjnumero = p.pnumero AND d.gerssn = e.ssn AND p.dnum = d.numero AND l.departamento_numero = e.dno;";
    private final String SQL_GETALL_JOIN = "SELECT DISTINCT(p.pnumero)," + BEFORECOND_JOIN + " FROM ((((cia.projeto as p LEFT OUTER JOIN cia.departamento AS d ON d.numero = p.dnum) LEFT OUTER JOIN cia.dept_localizacao AS l ON d.numero = l.departamento_numero) LEFT OUTER JOIN cia.empregado AS e ON d.gerssn = e.ssn) LEFT OUTER JOIN cia.trabalha_em AS t ON t.pjnumero = p.pnumero);";
    private final String SQL_GETALLDEPNOME = "SELECT DISTINCT(p.pnumero)," + BEFORECOND + " WHERE d.nome = ? AND t.pjnumero = p.pnumero AND d.gerssn = e.ssn AND p.dnum = d.numero AND l.departamento_numero = e.dno;";
    private final String SQL_GETALLDEPNUMERO = "SELECT DISTINCT(p.pnumero)," + BEFORECOND + " WHERE d.numero = ? AND t.pjnumero = p.pnumero AND d.gerssn = e.ssn AND p.dnum = d.numero AND l.departamento_numero = e.dno;";
    private final String SQL_GETALLEMP = "SELECT " + BEFORECOND + " WHERE e.ssn = t.essn AND t.pjnumero = p.pnumero AND p.dnum = d.numero AND e.ssn = ? AND l.departamento_numero = e.dno ORDER BY t.horas ASC;";
    private final String SQL_COUNTHOURS = "SELECT SUM(horas) FROM trabalha_em;";
    private final String SQL_COUNTEMP = "SELECT COUNT( DISTINCT(essn) ) FROM trabalha_em;";
    private final String SQL_PROJECTBYEMP = "SELECT COUNT(*) FROM trabalha_em WHERE essn = ?;";
    
    private PreparedStatement ps;
    private ResultSet rs;
           
    private Object useObjectTemplate(){
        try {
            String column = "p_";
            Projeto output = new Projeto();
            output.setNumero(this.rs.getInt(column+"numero"));
            output.setNome(this.rs.getString(column+"nome"));
            output.setLocalizacao(this.rs.getString(column+"localizacao"));
            output.setHoras(this.rs.getFloat("t_horas"));
            
            EmpregadoDAO empdao = (EmpregadoDAO) FactoryDAO.getFactory("Empregado");
            DepartamentoDAO depdao = (DepartamentoDAO) FactoryDAO.getFactory("Departamento");
            
            Departamento dep = (Departamento) depdao.get(this.rs.getInt("d_numero"));            
            output.setDepartamento(dep);
            
            Empregado supervisor = (Empregado) empdao.get(this.rs.getString("e_superssn"));
            empdao.createObject(this.rs.getString("e_ssn"), this.rs.getString("e_nome"), this.rs.getString("e_sexo"), this.rs.getString("e_endereco"), this.rs.getFloat("e_salario"), this.rs.getDate("e_datanasc"), this.rs.getString("e_senha"), supervisor, dep);
            
            System.gc();
            return output;
            
        } catch (Exception e) {
            System.err.println("Erro [PROJ] useObjectTemplate:  " + e.toString() );
            return null;
        }
    }
    
    private ArrayList<Object> getAllTemplate() throws SQLException {
        ArrayList<Object> output = new ArrayList<>();
        while(this.rs.next())
            output.add((Projeto) this.useObjectTemplate());            

        if(output.isEmpty())
            throw new ArrayStoreException("Nao houve objetos encontrados.");
        
        return output;
    }
    
    public Object createObject(int numero, String nome, String localizacao, Departamento departamento){
        Projeto pro = new Projeto();
        pro.setNumero(numero);
        pro.setNome(nome);
        pro.setLocalizacao(localizacao);
        pro.setDepartamento(departamento);
        
        System.gc();
        
        return pro;
    }

    @Override
    public void post(Object input) throws Exception {
        try {
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_POST);
            
            Projeto aux = (Projeto) input;
            this.ps.setString(1,aux.getNome());
            this.ps.setString(2,aux.getLocalizacao());
            this.ps.setInt(3,aux.getDepartamento().getNumero());
            
            System.gc();
            
            if(this.ps.executeUpdate() != 1)
                throw new SQLException("Objeto nao foi gravado.");
            
        } catch (Exception e) {
           throw new SQLException("Erro ao salvar objeto:  " + e.toString() );
        }
    }

    @Override
    public void update(Object input) throws Exception {
        try {
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_UPDATE);
            
            Projeto aux = (Projeto) input;
            this.ps.setInt(4,aux.getNumero());
            this.ps.setString(1,aux.getNome());
            this.ps.setString(2,aux.getLocalizacao());
            this.ps.setInt(3,aux.getDepartamento().getNumero());
            
            System.gc();
            
            if(this.ps.executeUpdate() != 1)
                throw new SQLException("Objeto nao foi atualizado.");
            
        } catch (Exception e) {
             throw new SQLException("Erro ao salvar objeto:  " + e.toString() );
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
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_READ);
            this.ps.setString(1,(String) input);
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
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_GETALL_JOIN);
            this.rs = this.ps.executeQuery();
            
            return this.getAllTemplate();
            
        } catch (Exception e) {
            System.err.println("Erro ao recuperar todos os objeto:  " + e.toString() );
            return null;
        }
    }
    
    public ArrayList<Object> getAllDep(int numero)  throws Exception {
        try {
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_GETALLDEPNUMERO);
            this.ps.setInt(1, numero);
            this.rs = this.ps.executeQuery();
            
            return this.getAllTemplate();
            
        } catch (Exception e) {
            throw new SQLException("Erro ao recuperar todos os objeto:  " + e.toString());            
        }
    }
    
    public ArrayList<Object> getAllDep(String nome) throws Exception {
        try {
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_GETALLDEPNOME);
            this.ps.setString(1, nome);
            this.rs = this.ps.executeQuery();

            return this.getAllTemplate();
            
        } catch (Exception e) {
            throw new SQLException("Erro ao recuperar todos os objeto:  " + e.toString());            
        }
    }
    
    public ArrayList<Object> getAllEmp(String ssn) throws Exception {
        try {
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_GETALLEMP);
            this.ps.setString(1, ssn);
            this.rs = this.ps.executeQuery();

            return this.getAllTemplate();
            
        } catch (Exception e) {
            throw new SQLException("Erro ao recuperar todos os objeto:  " + e.toString());            
        }
    }

    @Override
    public void delete(Object input) throws SQLException {
        try {
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_DELETE);
            this.ps.setInt(1,(int) input);
            
            if(this.ps.executeUpdate() != 1)
                throw new SQLException("Objeto nao foi deletado.");
            
        } catch (Exception e) {
            throw new SQLException("Erro deletar objeto:  " + e.toString());            
        }
    }
    
    public int getCount(int input) throws SQLException{
        try {
            String EXIT;
            if(input == 0)
                EXIT = SQL_COUNTHOURS;
            else
                EXIT = SQL_COUNTEMP;
            
            this.ps = Conexao.getInstance().getConexao().prepareStatement(EXIT);
            this.rs = this.ps.executeQuery();
            
            return this.rs.getInt(1);
            
        } catch (Exception e) {
            throw new SQLException("Erro contar projetos objeto:  " + e.toString());            
        }
    }
    
    public int getCount(String ssn) throws SQLException{
        try {
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_PROJECTBYEMP);
            this.ps.setString(1,(String) ssn);
            this.rs = this.ps.executeQuery();
            
            return this.rs.getInt(1);
            
        } catch (Exception e) {
            throw new SQLException("Erro contar projetos por empregado objeto:  " + e.toString());            
        }
    }
    
}
