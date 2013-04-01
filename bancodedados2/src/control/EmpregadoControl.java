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
    
    public void post(String ssn, String nome, String sexo, String endereco, String salario, String datanasc,
                     int dno, String superssn, String senha) throws Exception
    {
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
            
            System.out.println("empregado inserido!");
            
            FactoryDAO.getFactory("Empregado").post(empregado);        
    }    
    

    public void update(String ssn, String nome, String sexo, String endereco, String salario, String datanasc,
                       int dno, String superssn, String senha) throws Exception
    {
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
    }
    
    public void delete(String input) throws Exception
    {/* acho que nao precisa, yuri vc decide... att caio thoams
        FuncoesControle f = new FuncoesControle();
        
        if(f.verificarExistenciaEmpregado(input) == false)
        {
             throw new Exception("Erro: empregado informado nao foi encontrado");
        }
       */ 
        IObjectDAO empregadoDAO = FactoryDAO.getFactory("Empregado");
        empregadoDAO.delete(input);
    }

    //o pablo tem que arrumar o empregado dao (provisorio)
    public Empregado getById(String input) throws Exception
    {
        return (Empregado) FactoryDAO.getFactory("Empregado").get(input);
    }
  
    
    public Vector<Empregado> getAll() throws Exception {
        ArrayList<Object> empregadosObject = (ArrayList<Object>) FactoryDAO.getFactory("Empregado").getAll();
        Vector<Empregado> empregados = new Vector<Empregado>();
        
        for(int i = 0 ; i < empregadosObject.size() ; i++)
        {
            Empregado e = (Empregado) empregadosObject.get(i);
            empregados.add(e);
        }
        
        return empregados;
    }    
    

    public Vector<Empregado> SearchByName(String input)
    {
        ArrayList<Object> empregadosObject = (ArrayList<Object>) FactoryDAO.getFactory("Empregado").read(input);
        Vector<Empregado> empregados = new Vector<Empregado>();
        
        for(int i = 0 ; i < empregadosObject.size() ; i++)
        {
            Empregado e = (Empregado) empregadosObject.get(i);
            empregados.add(e);
        }
        
        return empregados;
    } 
    
 
            
      public Vector<Empregado> SearchBySuperSnn(String superssn)
    {
        EmpregadoDAO dao = new EmpregadoDAO();
        ArrayList<Object> empregadosObject = (ArrayList<Object>) dao.readbySuperssn(superssn);
        Vector<Empregado> empregados = new Vector<Empregado>();
        
        for(int i = 0 ; i < empregadosObject.size() ; i++)
        {
            Empregado e = (Empregado) empregadosObject.get(i);
            empregados.add(e);
        }
        
        return empregados;
    }


    public int login(String usuario, String senha)
    {
       EmpregadoDAO empregadoDAO = new EmpregadoDAO();
       int retorno = empregadoDAO.access(usuario, senha);
    
       return retorno;
    }

}