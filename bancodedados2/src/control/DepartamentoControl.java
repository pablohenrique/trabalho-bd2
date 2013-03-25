/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import DAO.FactoryDAO;
import DAO.IObjectDAO;
import Model.Departamento;
import Model.Empregado;
import java.awt.List;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Vector;

/**
 * teste
 * @author yuricampos
 */
public class DepartamentoControl
{
    public void post(int numero, String nome, String gerssn, Date gerdatainicio)
            throws Exception
    {
        FuncoesControle f = new FuncoesControle();
        
        if(f.verificarExistenciaEmpregado(gerssn) == false)
        {
            throw new Exception("Erro: gerente informado nao foi encontrado");
        }
        else
        {
            Departamento departamento = new Departamento();
            departamento.setNumero(numero);
            departamento.setNome(nome);
            departamento.setGerenteSsn((Empregado) FactoryDAO.getFactory("Empregado").read(gerssn));
            departamento.setGerenteDataInicio(gerdatainicio);
            FactoryDAO.getFactory("Departamento").post(departamento);
        }
    }


    public void update(int numero, String nome, String gerssn, Date gerdatainicio) throws Exception {
                     FuncoesControle f = new FuncoesControle();
     if(f.verificarExistenciaEmpregado(gerssn) == false){
         throw new Exception("Erro: empregado informado nao foi encontrado");
     } else{
        Departamento departamento = new Departamento();
        departamento.setNumero(numero);
        departamento.setNome(nome);
        departamento.setGerenteSsn((Empregado) FactoryDAO.getFactory("Empregado").read(gerssn));
        departamento.setGerenteDataInicio(gerdatainicio);
        FactoryDAO.getFactory("Departamento").update(departamento);
     }

        
    }
    
    public void delete(int numero) throws Exception{
        FuncoesControle f = new FuncoesControle();
        if(f.verificarExistenciaDepartamento(numero) == false){
            throw new Exception("Erro: departamento informado nao foi encontrado");
        } else{  
            FactoryDAO.getFactory("Departamento").delete(numero);
        }
    }


    public Departamento getById(int numero) throws Exception {
        Departamento departamento = (Departamento) FactoryDAO.getFactory("Departamento").get(numero);
        return departamento;
    }

    
    public static Vector<Departamento> getAll()
    {
         ArrayList<Object> departamentoObject = (ArrayList<Object>) FactoryDAO.getFactory("Departamento").getAll();
         Vector<Departamento> departamento = new Vector<Departamento>();
         
         for(int i = 0 ; i < departamentoObject.size() ; i++)
         {
             Departamento d = (Departamento) departamentoObject.get(i);             
             departamento.add(d);
         }
         
         return departamento;
    }    


    public Vector<Departamento> SearchByName(String input) throws Exception {
       ArrayList<Object> departamentoObject = (ArrayList<Object>) FactoryDAO.getFactory("Departamento").get(input);
       Vector<Departamento> departamento = new Vector<Departamento>();
                for(int i = 0 ; i < departamentoObject.size() ; i++)
         {
             Departamento d = (Departamento) departamentoObject.get(i);             
             departamento.add(d);
         }
         
         return departamento;
    }  
    

    
}
