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
   
    private final String SELECT_PRJETO = "p.pnumero AS p_numero, p.pjnome AS p_nome, p.plocalizacao AS p_localizacao, "; 
    private final String SELECT_DEP = "d.numero AS d_numero, d.nome AS d_nome, d.gerssn AS d_gerssn, d.gerdatainicio AS d_dataInicio, ";
    //private final String SELECT_EMP = "e.ssn AS e_ssn, e.nome AS e_nome, cia.sexo(e.sexo) AS e_sexo, e.endereco AS e_endereco, e.salario AS e_salario, e.datanasc AS e_datanasc, e.dno AS e_dno, e.superssn AS e_superssn, e.senha AS e_senha, ";
    private final String SELECT_EMP = "e.ssn AS e_ssn, e.nome AS e_nome, cia.sexo(e.sexo) AS e_sexo, e.endereco AS e_endereco, e.salario AS e_salario, e.datanasc AS e_datanasc, e.dno AS e_dno, e.superssn AS e_superssn, e.senha AS e_senha ";
    //private final String SELECT_TRB = "t.essn as t_essn, t.pjnumero AS t_numero, t.horas AS t_horas ";
    //private final String BEFORECOND = SELECT_PRJETO + ", "  + SELECT_DEP + ", " + SELECT_EMP + ", " + SELECT_TRB + " FROM cia.empregado AS e,  cia.projeto AS p,  cia.departamento AS d,  cia.trabalha_em AS t";
    private final String BEFORECOND = SELECT_PRJETO + SELECT_DEP + SELECT_EMP + " FROM cia.empregado AS e,  cia.projeto AS p,  cia.departamento AS d,  cia.trabalha_em AS t";
    
    private final String SQL_POST = "INSERT INTO cia.projeto(pjnome, plocalizacao, dnum) VALUES(?,?,?);";
    private final String SQL_UPDATE = "UPDATE cia.projeto SET pjnome = ?, plocalizacao = ?, dnum = ? WHERE pnumero = ?;";
    private final String SQL_DELETE = "DELETE FROM cia.projeto WHERE pnumero = ?;";
    private final String SQL_GET = "SELECT DISTINCT(p.pnumero)," + SELECT_DEP + SELECT_EMP + " FROM cia.empregado AS e,  cia.projeto AS p,  cia.departamento AS d WHERE p.pnumero = ? AND d.gerssn = e.ssn AND p.dnum = d.numero;";
    private final String SQL_READ = "SELECT DISTINCT(p.pnumero)," + SELECT_DEP + SELECT_EMP + " FROM cia.empregado AS e,  cia.projeto AS p,  cia.departamento AS d WHERE p.pjnome LIKE UPPER(?) AND d.gerssn = e.ssn AND p.dnum = d.numero;";
    private final String SQL_GETALL_JOIN = "SELECT p.pnumero AS p_numero, p.pjnome AS p_nome, p.plocalizacao AS p_localizacao,d.numero AS d_numero, d.nome AS d_nome, d.gerssn AS d_gerssn, d.gerdatainicio AS d_dataInicio,e.ssn AS e_ssn, e.nome AS e_nome, cia.sexo(e.sexo) AS e_sexo, e.endereco AS e_endereco, e.salario AS e_salario, e.datanasc AS e_datanasc, e.dno AS e_dno, e.superssn AS e_superssn, e.senha AS e_senha,t.essn as t_essn, t.pjnumero AS t_numero, t.horas AS t_horas FROM (((cia.projeto as p LEFT OUTER JOIN cia.departamento AS d ON d.numero = p.dnum) LEFT OUTER JOIN cia.empregado AS e ON d.gerssn = e.ssn) LEFT OUTER JOIN cia.trabalha_em AS t ON t.essn = e.ssn);";
    private final String SQL_GETALLDEPNOME = "SELECT " + SELECT_PRJETO + SELECT_DEP + SELECT_EMP + "  FROM cia.empregado AS e,  cia.projeto AS p,  cia.departamento AS d WHERE UPPER(d.nome) LIKE UPPER(?)  AND d.gerssn = e.ssn AND p.dnum = d.numero;";
    private final String SQL_GETALLDEPNUMERO = "SELECT DISTINCT(p.pnumero)," + BEFORECOND + " WHERE d.numero = ? AND t.pjnumero = p.pnumero AND d.gerssn = e.ssn AND p.dnum = d.numero;";
    private final String SQL_GETALLEMP = "SELECT " + BEFORECOND + " WHERE e.ssn = t.essn AND t.pjnumero = p.pnumero AND p.dnum = d.numero AND e.ssn = ? ORDER BY t.horas ASC;";
    
    private PreparedStatement ps;
    private ResultSet rs;
           
    private Object criarObjetoTemplate(){
        try {
            Projeto output = new Projeto();
            output.setNumero(this.rs.getInt("p_numero"));
            output.setNome(this.rs.getString("p_nome"));
            output.setLocalizacao(this.rs.getString("p_localizacao"));
            //output.setHoras(this.rs.getFloat("t_horas"));
            
            //EmpregadoDAO empdao = (EmpregadoDAO) FactoryDAO.getFactory("Empregado");
            DepartamentoDAO depdao = (DepartamentoDAO) FactoryDAO.getFactory("Departamento");
            
            //Departamento dep = (Departamento) depdao.get(this.rs.getInt("d_numero"));            
            output.setDepartamento( (Departamento) depdao.gerarObjeto(this.rs.getInt("d_numero"), this.rs.getString("d_nome"), this.rs.getDate("d_dataInicio"), null) );
            
            //Empregado supervisor = (Empregado) empdao.get(this.rs.getString("e_superssn"));
            //empdao.gerarObjeto(this.rs.getString("e_ssn"), this.rs.getString("e_nome"), this.rs.getString("e_sexo"), this.rs.getString("e_endereco"), this.rs.getFloat("e_salario"), this.rs.getDate("e_datanasc"), this.rs.getString("e_senha"), null, null);
            
            System.gc();
            return output;
            
        } catch (Exception e) {
            System.err.println("Erro [PROJ] useObjectTemplate:  " + e.toString() );
            return null;
        }
    }
    
    private ArrayList<Object> buscarVariosObjetosTemplate() throws SQLException {
        ArrayList<Object> output = new ArrayList<>();
        while(this.rs.next())
            output.add((Projeto) this.criarObjetoTemplate());
        
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
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_GETALL_JOIN);
            this.rs = this.ps.executeQuery();
            
            return this.buscarVariosObjetosTemplate();
            
        } catch (Exception e) {
            System.err.println("Erro ao recuperar todos os objeto:  " + e.toString() );
            return null;
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
    
    public ArrayList<Object> buscarNumeroDepartamento(int numero)  throws Exception {
        try {
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_GETALLDEPNUMERO);
            this.ps.setInt(1, numero);
            this.rs = this.ps.executeQuery();
            
            return this.buscarVariosObjetosTemplate();
            
        } catch (Exception e) {
            throw new SQLException("Erro ao recuperar todos os objeto:  " + e.toString());            
        }
    }
    
    public ArrayList<Object> buscarNomeDepartamento(String nome) throws Exception {
        try {
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_GETALLDEPNOME);
            this.ps.setString(1, nome);
            this.rs = this.ps.executeQuery();

            return this.buscarVariosObjetosTemplate();
            
        } catch (Exception e) {
            throw new SQLException("Erro ao recuperar todos os objeto:  " + e.toString());            
        }
    }
    
    public ArrayList<Object> buscarEmpregado(String ssn) throws Exception {
        try {
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_GETALLEMP);
            this.ps.setString(1, ssn);
            this.rs = this.ps.executeQuery();

            return this.buscarVariosObjetosTemplate();
            
        } catch (Exception e) {
            throw new SQLException("Erro ao recuperar todos os objeto:  " + e.toString());            
        }
    }
    
}
