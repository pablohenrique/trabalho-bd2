/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import DAO.FactoryDAO;
import DAO.IObjectDAO;
import Model.Departamento;
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
            throw new Exception("Erro: empregado informado nao foi encontrado");
        }
        else
        {
            IObjectDAO dao = FactoryDAO.getFactory("Departamento");
            Departamento departamento = new Departamento();
            departamento.setNumero(numero);
            departamento.setNome(nome);
            departamento.setGerenteSsn(gerssn);
            departamento.setGerenteDataInicio(gerdatainicio);
            dao.post(departamento);
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
        departamento.setGerenteSsn(gerssn);
        departamento.setGerenteDataInicio(gerdatainicio);
        IObjectDAO dao = FactoryDAO.getFactory("Departamento");
        dao.update(departamento);
     }

        
    }


    public Departamento getById(int numero) {
        IObjectDAO dao = FactoryDAO.getFactory("Departamento");
        Departamento departamento = (Departamento) dao.get(numero);
        return departamento;
    }


    public ArrayList<Departamento> getAll()
    {
         IObjectDAO dao = FactoryDAO.getFactory("Departamento");
         ArrayList<Object> departamentoObject = dao.getAll();
         ArrayList<Departamento> departamento = new ArrayList<Departamento>();
         
         for(int i = 0 ; i < departamentoObject.size() ; i++)
         {
             Departamento d = (Departamento) departamentoObject.get(i);             
             departamento.add(d);
         }
         
         return departamento;
    }
    
    public static Vector<Departamento> listarDepartamentos()
    {
         IObjectDAO dao = FactoryDAO.getFactory("Departamento");
         ArrayList<Object> departamentoObject = dao.getAll();
         Vector<Departamento> departamento = new Vector<Departamento>();
         
         for(int i = 0 ; i < departamentoObject.size() ; i++)
         {
             Departamento d = (Departamento) departamentoObject.get(i);             
             departamento.add(d);
         }
         
         return departamento;
    }    


    public Departamento SearchByNameExactly(String input) {
       IObjectDAO dao = FactoryDAO.getFactory("Departamento");
       Departamento departamento = (Departamento) dao.read(input);
       return departamento;
    }

    
}
