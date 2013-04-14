package DAO;

import Model.Departamento;
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
    private final String BEFORECOND = 
"SELECT t.essn AS t_essn, t.pjnumero AS t_pnumero, t.horas AS t_horas,"+
" e.ssn AS e_ssn, e.nome AS e_nome, cia.sexo(e.sexo) AS e_sexo, e.endereco AS e_endereco, e.salario AS e_salario, e.datanasc AS e_datanasc, e.dno AS e_dno, e.superssn AS e_superssn, e.senha AS e_senha,"+
" p.pnumero AS p_numero, p.pjnome AS p_nome, p.plocalizacao AS p_localizacao, p.dnum AS p_dnumero,"+
" d.numero AS d_numero, d.nome AS d_nome, d.gerssn AS d_gerssn, d.gerdatainicio AS d_gerdatainicio"+
" FROM cia.trabalha_em AS t, cia.empregado AS e, cia.projeto AS p, cia.departamento AS d";
    
    private final String SQL_POST = "INSERT INTO cia.trabalha_em VALUES(?,?,?);";
    private final String SQL_UPDATERIGHT = "UPDATE cia.trabalha_em SET horas = ? WHERE essn = ? AND pnumero = ?;";
    private final String SQL_DELETE = "DELETE FROM cia.trabalha_em WHERE essn = ?;";
    private final String SQL_DELETERIGHT = "DELETE FROM cia.trabalha_em WHERE essn = ? AND pnumero = ?;";
    private final String SQL_GET = BEFORECOND + " WHERE t.essn = ? AND e.ssn = t.essn AND t.pjnumero = p.pnumero AND p.dnum = d.numero;";
    private final String SQL_READ = BEFORECOND + " WHERE t.pjnumero = ?  AND e.ssn = t.essn AND t.pjnumero = p.pnumero AND p.dnum = d.numero;";
    private final String SQL_GETALL = BEFORECOND + " WHERE t.essn = e.ssn" + " AND t.pjnumero = p.pnumero" + " AND p.dnum = d.numero;";
    private final String SQL_COUNTHOURS = "SELECT SUM(horas) FROM cia.trabalha_em;";
    private final String SQL_COUNTHOURSEMP = "SELECT SUM(horas) FROM cia.trabalha_em WHERE essn = ?;";
    private final String SQL_COUNTEMP = "SELECT COUNT( DISTINCT(essn) ) FROM cia.trabalha_em;";
    private final String SQL_PROJECTBYEMP = "SELECT COUNT(*) FROM cia.trabalha_em WHERE essn = ?;";
    private PreparedStatement ps;
    private ResultSet rs;
    
    private Object criarObjetoTemplate(){
        try {
            EmpregadoDAO empdao = (EmpregadoDAO) FactoryDAO.getFactory("Empregado");
            DepartamentoDAO depdao = (DepartamentoDAO) FactoryDAO.getFactory("Departamento");
            ProjetoDAO prodao = (ProjetoDAO) FactoryDAO.getFactory("Projeto");
            
            Empregado supervisor = (Empregado) empdao.get(this.rs.getString("e_superssn"));
            Departamento dep = (Departamento) depdao.get(this.rs.getInt("d_numero"));
            
            Empregado emp = (Empregado) empdao.gerarObjeto(this.rs.getString("e_ssn"), this.rs.getString("e_nome"), this.rs.getString("e_sexo"), this.rs.getString("e_endereco"), this.rs.getFloat("e_salario"), this.rs.getDate("e_datanascimento"), this.rs.getString("e_senha"), supervisor, dep);
            Projeto pro = (Projeto) prodao.createObject(this.rs.getInt("p_numero"), this.rs.getString("p_nome"), this.rs.getString("p_localizacao"), dep);
            
            Trabalha output = new Trabalha();
            output.setEssn(emp);
            output.setProjeto(pro);
            output.setHoras(this.rs.getFloat("t_horas"));
            
            System.gc();
            
            return output;
        } catch (Exception e) {
            System.err.println("Erro [TRAB] useObjectTemplate:  " + e.toString() );
            return null;
        }
    }
    
    private ArrayList<Object> buscarVariosObjetosTemplate() throws SQLException{
        ArrayList<Object> output = new ArrayList<>();
        while(rs.next())
            output.add((Trabalha) this.criarObjetoTemplate());
        
        return output;
    }
    
    @Override
    public void post(Object input) {
        try {
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_POST);
            
            Trabalha aux = (Trabalha) input;
            this.ps.setString(1,aux.getEssn().getSsn());
            this.ps.setInt(2,aux.getProjeto().getNumero());
            this.ps.setFloat(3,aux.getHoras());
            
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
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_UPDATERIGHT);
            
            Trabalha aux = (Trabalha) input;
            this.ps.setFloat(1,aux.getHoras());
            this.ps.setString(2,aux.getEssn().getSsn());
            this.ps.setInt(3,aux.getProjeto().getNumero());
            
            System.gc();
            
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
            this.rs = this.ps.executeQuery();
            
            return this.buscarVariosObjetosTemplate();
            
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
            this.rs = this.ps.executeQuery();
            
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
    
    public void deletarEmpregadoProjeto(String ssn, int numero){
        try {
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_DELETERIGHT);
            this.ps.setString(1, ssn);
            this.ps.setInt(2, numero);
            
            if(this.ps.executeUpdate() == 0)
                throw new SQLException("Objeto nao foi deletado.");
            
        } catch (Exception e) {
            System.err.println("Erro ao deletar objeto:  " + e.toString() );
        }
    }
    
    public float somarHoras() throws SQLException{
        try {
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_COUNTHOURS);
            this.rs = this.ps.executeQuery();
            
            return this.rs.getFloat(1);
        } catch (Exception e) {
            System.out.println("Erro ao buscar quantidade de horas: " + e.toString());
            return -1;
        }
    }
    
    public float somarHorasEmpregado(String ssn) throws SQLException{
        try {
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_COUNTHOURSEMP);
            this.ps.setString(1,ssn);
            this.rs = this.ps.executeQuery();
            
            return this.rs.getFloat(1);
        } catch (Exception e) {
            System.out.println("Erro ao buscar quantidade de horas por funcionario: " + e.toString());
            return -1;
        }
    }
    
    public int buscarQuantidadeEmpregados() throws SQLException{
        try {
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_PROJECTBYEMP);
            this.rs = this.ps.executeQuery();
            
            return this.rs.getInt(1);
            
        } catch (Exception e) {
            throw new SQLException("Erro contar empregados em todos os projetos:  " + e.toString());            
        }
    }
    
    public int buscarProjetoEmpregado(String ssn) throws SQLException{
        try {
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_COUNTEMP);
            this.ps.setString(1,ssn);
            this.rs = this.ps.executeQuery();
            
            return this.rs.getInt(1);
            
        } catch (Exception e) {
            throw new SQLException("Erro contar projetos por empregado objeto:  " + e.toString());            
        }
    }
    
}