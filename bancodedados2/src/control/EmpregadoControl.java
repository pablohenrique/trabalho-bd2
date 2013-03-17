/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import DAO.EmpregadoDAO;
import Model.Auditoria;
import Model.Empregado;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author yuricampos
 */
public class EmpregadoControl  {


    public void post(String ssn, String nome, String sexo, String endereco, float salario, Date datanasc, int dno, String superssn, String senha) throws Exception {
                FuncoesControle f = new FuncoesControle();
        if(f.verificarExistenciaDepartamento(dno) == false){
            throw new Exception("Erro: departamento informado nao foi encontrado");
        }else if(f.verificarExistenciaEmpregado(superssn) == false){
         throw new Exception("Erro: supervisor informado nao foi encontrado");
     } else{
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
    }
    

    

    public void update(String ssn, String nome, String sexo, String endereco, float salario, Date datanasc, int dno, String superssn, String senha) throws Exception {
                        FuncoesControle f = new FuncoesControle();
        if(f.verificarExistenciaDepartamento(dno) == false){
            throw new Exception("Erro: departamento informado nao foi encontrado");
        }else if(f.verificarExistenciaEmpregado(superssn) == false){
         throw new Exception("Erro: supervisor informado nao foi encontrado");
     } else{
        EmpregadoDAO empregadoDAO = new EmpregadoDAO();
        Empregado empregadoVerifica = (Empregado) empregadoDAO.get(ssn);
        float salarioAtual = empregadoVerifica.getSalario();
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
        empregadoDAO.update(empregado);
        if(salarioAtual != salario){
            Auditoria auditoria = new Auditoria();
            auditoria.setAntigosalario(salarioAtual);
            auditoria.setNovosalario(salario);
            auditoria.setEssn(ssn);
            //AQUI E O GERENTE
            auditoria.setGerssn(superssn);
            Date data = new Date(System.currentTimeMillis()); 
            auditoria.setDatamodificacao(data);
            //FALTA INSERIR NO BANCO    
        } 
        

    }
    }


    public Empregado getById(String input) {
        EmpregadoDAO empregadoDAO = new EmpregadoDAO();
        Empregado empregado = (Empregado) empregadoDAO.get(input);
        return empregado;
    }


    public ArrayList<Empregado> getAll() {
        EmpregadoDAO empregadoDAO = new EmpregadoDAO();
        ArrayList<Object> empregadosObject = empregadoDAO.getAll();
        ArrayList<Empregado> empregados = null;
        for(int i = 0 ; i < empregadosObject.size() ; i++){
            Empregado e = (Empregado) empregadosObject.get(i);
            empregados.add(e);
        }
        return empregados;
    }


    public Empregado SearchByNameExactly(String input) {
        EmpregadoDAO empregadoDAO = new EmpregadoDAO();
        Empregado empregado = (Empregado) empregadoDAO.read(input);
        return empregado;
    }

 
    public int login(String usuario, String senha) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    
    
}
