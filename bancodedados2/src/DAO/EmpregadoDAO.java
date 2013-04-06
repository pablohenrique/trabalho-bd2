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
 * ALTERACAO COMENTARIO SOH PRO PABLO VER
 * @author pablohenrique
 */
public class EmpregadoDAO implements IObjectDAO{
    private final String BEFORECOND = 
"SELECT e.ssn AS e_ssn, e.nome AS e_nome, cia.sexo(e.sexo) AS e_sexo, e.endereco AS e_endereco, e.salario AS e_salario, e.datanasc AS e_datanasc, e.dno AS e_dno, " +
" e.superssn AS e_superssn, e.senha AS e_senha, s.ssn AS s_ssn, s.nome AS s_nome, cia.sexo(s.sexo) AS s_sexo, s.endereco AS s_endereco, s.salario AS s_salario, " +
" s.datanasc AS s_datanasc, s.dno AS s_dno, s.superssn AS s_superssn, s.senha AS s_senha, d.numero AS d_numero, d.nome AS d_nome, d.gerssn AS d_gerssn, d.gerdatainicio AS d_gerdatainicio, "+
" l.dlocalizacao AS l_localizacao, l.departamento_numero AS l_numero ";
    
    private final String SQL_POST = "INSERT INTO cia.empregado VALUES(?,?,cia.sexoToBd(?),?,?,?,?,?,?);";
    private final String SQL_UPDATE = "UPDATE cia.empregado SET nome = ?, sexo = cia.sexoToBd(?), endereco = ?, salario = ?, datanasc = ?, dno = ?, superssn = ?, senha = ? WHERE ssn = ?;";
    private final String SQL_DELETE = "DELETE FROM cia.empregado WHERE ssn = ?;";
    private final String SQL_LOGIN = " SELECT cia.login(?,?); ";
    private final String SQL_GET = BEFORECOND + " FROM cia.empregado AS e, cia.departamento AS d, cia.empregado AS s, cia.dept_localizacao AS l WHERE e.ssn = ? AND e.superssn = s.ssn AND e.dno = d.numero AND l.departamento_numero = d.numero;";
    private final String SQL_READ = BEFORECOND + " FROM cia.empregado AS e, cia.departamento AS d, cia.empregado AS s, cia.dept_localizacao AS l AS d, cia.empregado AS s WHERE e.nome LIKE ? AND e.superssn = s.ssn AND e.dno = d.numero AND l.departamento_numero = d.numero;";
    private final String SQL_READ_SUPERSSN = BEFORECOND + " FROM cia.empregado AS e, cia.departamento AS d, cia.empregado AS s, cia.dept_localizacao AS l WHERE e.superssn = ? AND e.superssn = s.ssn AND e.dno = d.numero AND l.departamento_numero = d.numero;";
    private final String SQL_GETALL = BEFORECOND + " FROM ((((cia.empregado AS e LEFT JOIN cia.departamento AS d ON e.dno = d.numero) LEFT JOIN cia.dept_localizacao AS l ON d.numero = l.departamento_numero) LEFT JOIN cia.empregado AS ger ON d.gerssn = ger.ssn) LEFT JOIN cia.empregado AS s ON e.superssn = s.ssn) ORDER BY e.nome ASC;";
    private final String SQL_COUNTEMP = "SELECT COUNT(ssn) FROM empregado;";
    
    private PreparedStatement ps;
    private ResultSet rs;
    
    private Object useObjectTemplate(String column){
        try {
            Empregado output = new Empregado();
            output.setSsn(this.rs.getString(column+"ssn"));
            output.setNome(this.rs.getString(column+"nome"));
            output.setSexo(this.rs.getString(column+"sexo"));
            output.setEndereco(this.rs.getString(column+"endereco"));
            output.setSalario(this.rs.getFloat(column+"salario"));
            output.setDataNascimento(this.rs.getDate(column+"datanasc"));
            output.setSenha(this.rs.getString(column+"senha"));
            
            if(column.equals("s_") || this.rs.getString(column+"ssn") == this.rs.getString("s_ssn"))
                output.setSuperSsn(null);
            else
                output.setSuperSsn((Empregado) useObjectTemplate("s_"));
            
            DepartamentoDAO dao = (DepartamentoDAO) FactoryDAO.getFactory("Departamento");
            output.setDepartamento((Departamento) dao.createObject(this.rs.getInt("d_numero"), this.rs.getString("d_nome"), this.rs.getString("l_localizacao"), this.rs.getDate("d_gerdatainicio"), output));
            
            System.gc();
            return output;
            
        } catch (Exception e) {
            System.err.println("Erro [EMPR] useObjectTemplate:  " + e.toString() );
            return null;
        }
    }
    
    public Object createObject(String ssn, String nome, String sexo, String endereco, Float salario, Date datanascimento, String senha, Empregado superssn, Departamento departamento){
        Empregado emp = new Empregado();
        emp.setSsn(ssn);
        emp.setNome(nome);
        emp.setSexo(sexo);
        emp.setEndereco(endereco);
        emp.setSalario(salario);
        emp.setDataNascimento(datanascimento);
        emp.setSenha(senha);
        emp.setSuperSsn(superssn);
        emp.setDepartamento(departamento);
        if(departamento.getGerenteSsn() == null)
            departamento.setGerenteSsn(emp);
        
        System.gc();
        return emp;
    }
    
    private ArrayList<Object> getAllTemplate() throws SQLException{
        ArrayList<Object> output = new ArrayList<>();
        while(rs.next()){
            output.add((Empregado) this.useObjectTemplate("e_"));
        }

        //if(output.isEmpty())
        //    throw new ArrayStoreException("Nao houve objetos encontrados.");

        return output;
    }
    
    @Override
    public void post(Object input) throws Exception {
        try {
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_POST);
            
            Empregado aux = (Empregado) input;
            
            this.ps.setString(1,aux.getSsn().trim());               
            this.ps.setString(2,aux.getNome());
            this.ps.setString(3,aux.getSexo());
            this.ps.setString(4,aux.getEndereco());
            this.ps.setFloat(5,aux.getSalario());
            this.ps.setDate(6,aux.getDataNascimento());
            this.ps.setInt(7,aux.getDepartamento().getNumero());
            this.ps.setString(8,aux.getSuperSsn().getSsn());
            this.ps.setString(9,aux.getSenha());

            System.gc();

            if(this.ps.executeUpdate() != 1)
                throw new SQLException("Objeto nao foi gravado.");
            
        } catch (Exception e) {            
            throw new Exception(e.getMessage().toString());
        }
    }

    @Override
    public void update(Object input) {
        try {
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_UPDATE);
            
            Empregado aux = (Empregado) input;
            this.ps.setString(9,aux.getSsn());
            this.ps.setString(1,aux.getNome());
            this.ps.setString(2,aux.getSexo());
            this.ps.setString(3,aux.getEndereco());
            this.ps.setFloat(4,aux.getSalario());
            this.ps.setDate(5,aux.getDataNascimento());
            this.ps.setInt(6,aux.getDepartamento().getNumero());
            this.ps.setString(7,aux.getSuperSsn().getSsn());
            this.ps.setString(8,aux.getSenha());
            
            System.gc();
            
            if(this.ps.executeUpdate() != 1)
                throw new SQLException("Objeto nao foi atualizado.");
            
            //this.insertObjectTemplate();
            
        } catch (Exception e) {
            System.err.println("Erro ao atualizar o objeto:  " + e.toString() );
        }
    }

    @Override
    public Object get(Object input) throws Exception {
        try {
            String aux = (String) input;
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_GET);
            this.ps.setString(1,aux);
                        
            this.rs = this.ps.executeQuery();
            
            if(!this.rs.next())
                throw new Exception("Departamento nao encontrado.");
            
            return this.useObjectTemplate("e_");
            
        } catch (Exception e) {
             throw new Exception(e.toString());
        }        
    }
    
    @Override
    public Object read(Object input) {
        try {
            String aux = "'%"+(String) input+"%'";
            
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_READ);
            this.ps.setString(1,aux);
            this.rs = this.ps.executeQuery();
            
            if(!this.rs.next())
                throw new Exception("Departamento nao encontrado.");
            
            return this.getAllTemplate();
            
        } catch (Exception e) {
            System.err.println("Erro ao buscar [READ] o objeto:  " + e.toString() );
            return null;
        }
    }
    
        public Object readbySuperssn(String superssn) {
        try {

            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_READ_SUPERSSN);
            this.ps.setString(1,superssn);
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
            System.err.println("Erro ao recuperar todos os objeto:  " + e.toString() );
            return null;
        }
    }

    @Override
    public void delete(Object input) throws Exception {
        try {
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_DELETE);
            this.ps.setString(1,(String) input);
            
            if(this.ps.executeUpdate() == 0)
                throw new SQLException("Objeto nao foi deletado.");
            
        } catch (Exception e) {
             throw new Exception("Erro ao deletar objeto:  " + e.toString() );
        }
    }
    
    public int access(String user, String password){
        try{
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_LOGIN);
            this.ps.setString(1, user);
            this.ps.setString(2, password);
            
            this.rs = this.ps.executeQuery();
            
            if(!this.rs.next())
                throw new SQLException("Login nao pode ser encontrado.");
            
            return this.rs.getInt(1);
            
        }
        catch (Exception e){
            System.err.println("Erro ao logar usuario:  " + e.toString() );
            return -1;
        }
    }
    
    public int countEmp(){
        try{
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_COUNTEMP);
            this.rs = this.ps.executeQuery();
            
            return this.rs.getInt(1);
        }
        catch (Exception e){
            System.err.println("Erro ao contar empregados:  " + e.toString() );
            return -1;
        }
    }
}
