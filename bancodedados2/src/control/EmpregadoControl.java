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
            empregado.setSsn(ssn);
            empregado.setNome(nome);
            empregado.setSexo(sexo);
            empregado.setEndereco(endereco);
            empregado.setSalario(salario);
            empregado.setDataNascimentoString(datanasc);
            empregado.setDepartamento(dep);
            empregado.setSuperSsn(gerente);
            empregado.setSenha(senha);
            
            System.out.println("empregado inserido!");
            
            IObjectDAO empregadoDAO = FactoryDAO.getFactory("Empregado");
            empregadoDAO.post(empregado);        
    }    
    

    public void update(String ssn, String nome, String sexo, String endereco, float salario, Date datanasc,
                       int dno, String superssn, String senha) throws Exception
    {
        FuncoesControle f = new FuncoesControle();
        if(f.verificarExistenciaDepartamento(dno) == false)
        {
            throw new Exception("Erro: departamento informado nao foi encontrado");
        }
        else if(f.verificarExistenciaEmpregado(superssn) == false)
        {
            throw new Exception("Erro: supervisor informado nao foi encontrado");
        }
        else if(!"M".equals(sexo) || !"F".equals(sexo))
        {
           throw new Exception("Erro: sexo informado esta incorreto");  
        }
        else
        {                        
            IObjectDAO empregadoDAO = FactoryDAO.getFactory("Empregado");     
            Empregado empregadoVerifica = (Empregado) empregadoDAO.get(ssn);
            float salarioAtual = empregadoVerifica.getSalario();   
            IObjectDAO dao0 = FactoryDAO.getFactory("Empregado"); 
            Empregado gerente = (Empregado) dao0.get(superssn);
            IObjectDAO dao1 = FactoryDAO.getFactory("Departamento"); 
            Departamento departamento = (Departamento) dao1.read(dno);
           
            Empregado empregado = new Empregado();    
            empregado.setSsn(ssn);
            empregado.setNome(nome);
            empregado.setSexo(sexo);
            empregado.setEndereco(endereco);
            empregado.setSalario(salario);
            empregado.setDataNascimento(datanasc);
            empregado.setDepartamento(departamento);
            empregado.setSuperSsn(gerente);
            empregado.setSenha(senha);
            empregadoDAO.update(empregado); 
            if(salarioAtual != salario)
            {
                Auditoria auditoria = new Auditoria();
                auditoria.setAntigosalario(salarioAtual);
                auditoria.setNovosalario(salario);
                auditoria.setEssn(empregadoVerifica);
                //AQUI GERENTE
                auditoria.setGerssn(gerente);
                Date data = new Date(System.currentTimeMillis()); 
                auditoria.setDatamodificacao(data);
                //FALTA INSERIR NO BANCO    
            }         
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
        IObjectDAO empregadoDAO = FactoryDAO.getFactory("Empregado");
        Empregado empregado = (Empregado) empregadoDAO.get(input);
        
        return empregado;
    }
  
    
    public Vector<Empregado> getAll()
    {
        IObjectDAO empregadoDAO = FactoryDAO.getFactory("NewDao");
        ArrayList<Object> empregadosObject = (ArrayList<Object>) empregadoDAO.getAll();
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
        IObjectDAO empregadoDAO = FactoryDAO.getFactory("Empregado");
        ArrayList<Object> empregadosObject = (ArrayList<Object>) empregadoDAO.read(input);
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