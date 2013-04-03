/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import DAO.EmpregadoDAO;
import DAO.FactoryDAO;
import DAO.IObjectDAO;
import Model.Auditoria;
import Model.Departamento;
import Model.Empregado;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author yuricampos
 */
public class EmpregadoControl
{
    private EmpregadoDAO dao;
    
    public EmpregadoControl(){
        this.dao = (EmpregadoDAO) FactoryDAO.getFactory("Empregado");
    }
    
    private Empregado createObject(String ssn, String nome, String sexo, String endereco, String salario, String datanasc, int dno, String superssn, String senha){
        Empregado gerente = new Empregado();
        gerente.setSsn(superssn);

        Departamento dep = new Departamento();
        dep.setNumero(dno);

        Empregado empregado = new Empregado();
        empregado.setSsn(ssn.trim());
        empregado.setNome(nome);
        empregado.setSexo(sexo);
        empregado.setEndereco(endereco);
        empregado.setSalario(salario);
        empregado.setDataNascimento(datanasc);
        empregado.setDepartamento(dep);
        empregado.setSuperSsn(gerente);
        empregado.setSenha(senha);
        
        return empregado;
    }
    
    public void post(String ssn, String nome, String sexo, String endereco, String salario, String datanasc, int dno, String superssn, String senha) throws Exception{
        FactoryDAO.getFactory("Empregado").post(this.createObject(ssn, nome, sexo, endereco, salario, datanasc, dno, superssn, senha));
    }    
    

    public void update(String ssn, String nome, String sexo, String endereco, String salario, String datanasc, int dno, String superssn, String senha) throws Exception {
        
        FactoryDAO.getFactory("Empregado").update(this.createObject(ssn, nome, sexo, endereco, salario, datanasc, dno, superssn, senha));
        
        /*
        Empregado gerente = new Empregado();
        gerente.setSsn(superssn);

        Departamento dep = new Departamento();
        dep.setNumero(dno);

        Empregado empregadoVerifica = (Empregado) FactoryDAO.getFactory("Empregado").get(ssn);

        float salarioAtual = empregadoVerifica.getSalario();   
        float salarioEmp = Float.parseFloat(salario);

        //IObjectDAO dao0 = FactoryDAO.getFactory("Empregado"); 
        //Empregado gerente = (Empregado) dao0.get(superssn);            

        Empregado empregado = new Empregado();    
        empregado.setSsn(ssn);
        empregado.setNome(nome);
        empregado.setSexo(sexo);
        empregado.setEndereco(endereco);
        empregado.setSalario(salario);
        empregado.setDataNascimento(datanasc);
        empregado.setDepartamento(dep);
        empregado.setSuperSsn(gerente);
        empregado.setSenha(senha);

        System.out.println("empregado atualizado!");            
        FactoryDAO.getFactory("Empregado").update(empregado); 

        if(salarioAtual !=  salarioEmp)
        {
            Auditoria auditoria = new Auditoria();
            auditoria.setAntigosalario(salarioAtual);
            auditoria.setNovosalario(salarioEmp);
            auditoria.setEssn(empregadoVerifica);
            //AQUI GERENTE
            auditoria.setGerssn(gerente);
            Date data = new Date(System.currentTimeMillis()); 
            auditoria.setDatamodificacao(data);
            //FALTA INSERIR NO BANCO    
        } 
        */
    }
    
    public void delete(String input) throws Exception{
        FactoryDAO.getFactory("Empregado").delete(input);
    }
    
    public Empregado getById(String input) throws Exception{
        return (Empregado) FactoryDAO.getFactory("Empregado").get(input);
    }
  
    public Vector<Empregado> getAll() throws Exception {
        Vector<Empregado> empregados = new Vector<Empregado>();
        
        for(Object aux : (ArrayList<Object>) FactoryDAO.getFactory("Empregado").getAll())
            empregados.add((Empregado) aux);
        
        return empregados;
    }    
    
    public Vector<Empregado> SearchByName(String input){
        Vector<Empregado> empregados = new Vector<Empregado>();
        
        for(Object aux : (ArrayList<Object>) FactoryDAO.getFactory("Empregado").read(input))
            empregados.add((Empregado) aux);
        
        return empregados;
    } 
    
    public Vector<Empregado> SearchBySuperSnn(String superssn){
        Vector<Empregado> empregados = new Vector<Empregado>();
        
        for(Object aux : (ArrayList<Object>) this.dao.readbySuperssn(superssn))
            empregados.add((Empregado) aux);
        
        return empregados;
    }

    public int login(String usuario, String senha){
       return this.dao.access(usuario, senha);
    }

}