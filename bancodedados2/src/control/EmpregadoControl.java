/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import DAO.FactoryDAO;
import DAO.IObjectDAO;
import Model.Auditoria;
import Model.Empregado;
import java.sql.Date;
import java.util.ArrayList;

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
        }
         else if(!"M".equals(sexo) || !"F".equals(sexo)){
           throw new Exception("Erro: sexo informado esta incorreto");  
         }
      else{
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
        IObjectDAO empregadoDAO = FactoryDAO.getFactory("Empregado");
        empregadoDAO.post(empregado);
        
    }
    }
    

    

    public void update(String ssn, String nome, String sexo, String endereco, float salario, Date datanasc, int dno, String superssn, String senha) throws Exception {
        FuncoesControle f = new FuncoesControle();
        if(f.verificarExistenciaDepartamento(dno) == false){
            throw new Exception("Erro: departamento informado nao foi encontrado");
        }else if(f.verificarExistenciaEmpregado(superssn) == false){
         throw new Exception("Erro: supervisor informado nao foi encontrado");
        }
         else if(!"M".equals(sexo) || !"F".equals(sexo)){
           throw new Exception("Erro: sexo informado esta incorreto");  
         }
      else{
        IObjectDAO empregadoDAO = FactoryDAO.getFactory("Empregado");
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
       IObjectDAO empregadoDAO = FactoryDAO.getFactory("Empregado");
        Empregado empregado = (Empregado) empregadoDAO.get(input);
        return empregado;
    }


    public ArrayList<Empregado> getAll() {
        IObjectDAO empregadoDAO = FactoryDAO.getFactory("Empregado");
        ArrayList<Object> empregadosObject = empregadoDAO.getAll();
        ArrayList<Empregado> empregados = null;
        for(int i = 0 ; i < empregadosObject.size() ; i++){
            Empregado e = (Empregado) empregadosObject.get(i);
            empregados.add(e);
        }
        return empregados;
    }


    public Empregado SearchByNameExactly(String input) {
        IObjectDAO empregadoDAO = FactoryDAO.getFactory("Empregado");
        Empregado empregado = (Empregado) empregadoDAO.read(input);
        return empregado;
    }

 
    public int login(String usuario, String senha) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    
    
}
