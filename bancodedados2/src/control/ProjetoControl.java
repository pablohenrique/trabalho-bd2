/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;


import DAO.FactoryDAO;
import DAO.IObjectDAO;
import Model.Departamento;
import Model.Projeto;
import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author yuricampos
 */
public class ProjetoControl  {


    public void post(int numero, String nome, String localizacao, int dnumero)
            throws Exception
    {
        //FALTA VERIFICAR SE LOCALIZACAO EXISTE
        FuncoesControle f = new FuncoesControle();
        
        if(f.verificarExistenciaDepartamento(dnumero) == false){
            throw new Exception("Erro: departamento informado nao foi encontrado");
        }
        else
        {
            IObjectDAO dao0 = FactoryDAO.getFactory("Departamento");
            Departamento departamento = (Departamento) dao0.read(dnumero);
            Projeto projeto = new Projeto();
            projeto.setNumero(numero);
            projeto.setNome(nome);
            projeto.setLocalizacao(localizacao);
            projeto.setDepartamento(departamento);
            IObjectDAO projetoDAO = FactoryDAO.getFactory("Projeto");
            projetoDAO.post(projeto);
        }
    }

    public void update(int numero, String nome, String localizacao, int dnumero) 
            throws Exception
    {   
        FuncoesControle f = new FuncoesControle();
        
        if(f.verificarExistenciaDepartamento(dnumero) == false)
        {
            throw new Exception("Erro: departamento informado nao foi encontrado");
        }
        else
        {
            IObjectDAO dao0 = FactoryDAO.getFactory("Departamento");
            Departamento departamento = (Departamento) dao0.read(dnumero);
            Projeto projeto = new Projeto();
            projeto.setNumero(numero);
            projeto.setNome(nome);
            projeto.setLocalizacao(localizacao);
            projeto.setDepartamento(departamento);
            IObjectDAO projetoDAO = FactoryDAO.getFactory("Projeto");
            projetoDAO.update(projeto); 
        }

    }
    
    public void delete(int numero) throws Exception{
        FuncoesControle f = new FuncoesControle();
        if(f.verificarExistenciaProjeto(numero) == false){
            throw new Exception("Erro: projeto informado nao foi encontrado");
        } else{
            IObjectDAO projetoDAO = FactoryDAO.getFactory("Projeto");
            projetoDAO.delete(numero);
        }
    }

    public Projeto getById(int input) 
    {
        IObjectDAO projetoDAO = FactoryDAO.getFactory("Projeto");
        Projeto projeto = (Projeto) projetoDAO.get(input);
        return projeto;
    }


  
    
        public static Vector<Projeto> getAll()
    {
         IObjectDAO dao = FactoryDAO.getFactory("Projeto");
         ArrayList<Object> projetoObject = (ArrayList<Object>) dao.getAll();
         Vector<Projeto> projeto = new Vector<Projeto>();
         
         for(int i = 0 ; i < projetoObject.size() ; i++)
         {
             Projeto p = (Projeto) projetoObject.get(i);             
             projeto.add(p);
         }
         
         return projeto;
    }    


    public Vector<Projeto> SearchByName(String input) {
       IObjectDAO dao = FactoryDAO.getFactory("Projeto");
       ArrayList<Object> projetoObject = (ArrayList<Object>) dao.get(input);
       Vector<Projeto> projeto = new Vector<Projeto>();
                for(int i = 0 ; i < projetoObject.size() ; i++)
         {
             Projeto p = (Projeto) projetoObject.get(i);             
             projeto.add(p);
         }
         
         return projeto;
    }  
    
}
