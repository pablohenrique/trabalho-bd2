/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Departamento;
import Model.Empregado;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author caio
 */
public class NovoDao implements IObjectDAO{
 
    private final String SQL_GETALL = "SELECT e.ssn AS E_ssn, e.nome AS E_nome, cia.sexo(e.sexo) AS E_sexo, e.endereco AS E_endereco, e.salario AS E_salario, e.datanasc AS E_datanasc, e.dno AS E_dno, \n" +
"       e.superssn AS E_superssn, e.senha AS E_senha, s.ssn AS S_ssn, s.nome AS S_nome, cia.sexo(s.sexo) AS S_sexo, s.endereco AS S_endereco, s.salario AS S_salario,\n" +
"       s.datanasc AS S_datanasc, s.dno AS S_dno, s.superssn AS S_superssn, s.senha AS S_senha, d.numero AS D_numero, d.nome AS D_nome, d.gerssn AS D_gerssn, d.gerdatainicio AS D_gerdatainicio\n" +
"    FROM (((cia.empregado AS e LEFT JOIN cia.departamento\n" +
"		AS d ON e.dno = d.numero) LEFT JOIN cia.empregado AS ger\n" +
"		ON d.gerssn = ger.ssn) LEFT JOIN cia.empregado AS s \n" +
"			ON e.superssn = s.ssn)\n" +
"    ORDER BY e.nome ASC;";
    private PreparedStatement ps;
    private ResultSet rs;

  
    private Object useObjectTemplateDepartamento(String column)
    {
        try
        {
            if(column == null)
                  column = "";

            Departamento output = new Departamento();
            output.setNumero(this.rs.getInt(this.rs.findColumn(column+"numero")));
            output.setNome(this.rs.getString(this.rs.findColumn(column+"nome")));
            output.setGerenteSsn(null);
            output.setGerenteDataInicio(this.rs.getDate(this.rs.findColumn(column+"gerdatainicio")));
            return output;
            
        }
        catch (Exception e)
        {
            System.err.println("Erro useObjectTemplate:  " + e.toString() );
            return null;
        }
    }   
    
    private Object useObjectTemplateEmpregado(String column)
    {
        try
        {            
            if(column == null)
                  column = "";
            
            Empregado output = new Empregado();
            
            output.setSsn(this.rs.getString(column+"ssn"));
            output.setNome(this.rs.getString(this.rs.findColumn(column+"nome")));
            output.setSexo(this.rs.getString(this.rs.findColumn(column+"sexo")));
            output.setEndereco(this.rs.getString(this.rs.findColumn(column+"endereco")));
            output.setSalario(this.rs.getFloat(this.rs.findColumn(column+"salario")));
            output.setDataNascimento(this.rs.getDate(this.rs.findColumn(column+"datanasc")));
            output.setDepartamento(null);            
            output.setSuperSsn(null);
            output.setSenha(this.rs.getString(this.rs.findColumn(column+"senha")));
            return output;
            
        } catch (Exception e) {
            System.err.println("Erro useObjectTemplate:  " + e.toString() );
            return null;
        }
    }   
    
    public Object getAll() {
        try {
            this.ps = Conexao.getInstance().getConexao().prepareStatement(SQL_GETALL);
            ArrayList<Empregado> output = new ArrayList<>();
            
            this.rs = this.ps.executeQuery();
            while(rs.next())
            {
                Empregado emp = (Empregado) this.useObjectTemplateEmpregado("e_");
                Empregado sup = (Empregado) this.useObjectTemplateEmpregado("s_");
                Departamento dep = (Departamento)this.useObjectTemplateDepartamento("d_");
                emp.setDepartamento(dep);
                emp.setSuperSsn(sup);
                output.add(emp);                
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
    public void post(Object input) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Object input) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object get(Object input) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object read(Object input) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Object input) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
