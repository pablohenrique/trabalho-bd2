/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import DAO.EmpregadoDAO;
import Model.Empregado;
import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author yuricampos
 */
public class EmpregadoControl  {


    public void post(String ssn, String nome, String sexo, String endereco, float salario, Date datanasc, int dno, String superssn, String senha) {
        Empregado empregado = new Empregado();
        empregado.setSsn(ssn);
        empregado.setNome(nome);
        empregado.setSexo(sexo);
        empregado.setEndereco(endereco);
        empregado.setSalario(salario);
        empregado.setDataNascimento(datanasc);
        empregado.setDepartamento(dno);
        empregado.setSuperSsn(superssn);
        empregado.setSenha(senha);
        EmpregadoDAO empregadoDAO = new EmpregadoDAO();
        empregadoDAO.post(empregado);
        
    }


    public void update(String ssn, String nome, String sexo, String endereco, float salario, Date datanasc, int dno, String superssn, String senha) {
        Empregado empregado = new Empregado();
        empregado.setSsn(ssn);
        empregado.setNome(nome);
        empregado.setSexo(sexo);
        empregado.setEndereco(endereco);
        empregado.setSalario(salario);
        empregado.setDataNascimento(datanasc);
        empregado.setDepartamento(dno);
        empregado.setSuperSsn(superssn);
        empregado.setSenha(senha);
        EmpregadoDAO empregadoDAO = new EmpregadoDAO();
        empregadoDAO.update(empregado);
    }


    public Object getById(int input) {
        EmpregadoDAO empregadoDAO = new EmpregadoDAO();
        Empregado empregado = (Empregado) empregadoDAO.get(input);
        return empregado;
    }


    public ArrayList<Object> getAll() {
        EmpregadoDAO empregadoDAO = new EmpregadoDAO();
        ArrayList<Object> empregados = empregadoDAO.getAll();
        return empregados;
    }


    public Object SearchByNameExactly(String input) {
        EmpregadoDAO empregadoDAO = new EmpregadoDAO();
        Empregado empregado = (Empregado) empregadoDAO.read(input);
        return empregado;
    }

 
    public int login(String usuario, String senha) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    
    
}
