/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;


import DAO.FactoryDAO;
import DAO.IObjectDAO;
import DAO.ProjetoDAO;
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
            Departamento departamento = (Departamento) FactoryDAO.getFactory("Departamento").read(dnumero);
            Projeto projeto = new Projeto();
            projeto.setNumero(numero);
            projeto.setNome(nome);
            projeto.setLocalizacao(localizacao);
            projeto.setDepartamento(departamento);
            FactoryDAO.getFactory("Projeto").post(projeto);
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
            Departamento departamento = (Departamento) FactoryDAO.getFactory("Departamento").read(dnumero);
            Projeto projeto = new Projeto();
            projeto.setNumero(numero);
            projeto.setNome(nome);
            projeto.setLocalizacao(localizacao);
            projeto.setDepartamento(departamento);
            FactoryDAO.getFactory("Projeto").update(projeto); 
        }

    }
    
    public void delete(int numero) throws Exception{
        FuncoesControle f = new FuncoesControle();
        if(f.verificarExistenciaProjeto(numero) == false){
            throw new Exception("Erro: projeto informado nao foi encontrado");
        } else{
            FactoryDAO.getFactory("Projeto").delete(numero);
        }
    }

    public Projeto getById(int input) throws Exception 
    {
        Projeto projeto = (Projeto) FactoryDAO.getFactory("Projeto").get(input);
        return projeto;
    }


  
    
        public static Vector<Projeto> getAll()
    {
         ArrayList<Object> projetoObject = (ArrayList<Object>) FactoryDAO.getFactory("Projeto").getAll();
         Vector<Projeto> projeto = new Vector<Projeto>();
         
         for(int i = 0 ; i < projetoObject.size() ; i++)
         {
             Projeto p = (Projeto) projetoObject.get(i);             
             projeto.add(p);
         }
         
         return projeto;
    }    
        
        public static Vector<Projeto> getAllByDepNome(String nomeDepto) {
        ProjetoDAO dao = new ProjetoDAO();
        
        ArrayList<Object> projetoObject = (ArrayList<Object>) dao.getAllDep(nomeDepto);
        Vector<Projeto> projeto = new Vector<Projeto>();
         
        for(int i = 0 ; i < projetoObject.size() ; i++){
            Projeto p = (Projeto) projetoObject.get(i);             
            projeto.add(p);
         }
        
        return projeto;
    }   
        
    public static Vector<Projeto> getAllByDepNumero(int depnumero) {
        ProjetoDAO dao = new ProjetoDAO();

        ArrayList<Object> projetoObject = (ArrayList<Object>) dao.getAllDep(depnumero);
        Vector<Projeto> projeto = new Vector<Projeto>();

        for (int i = 0; i < projetoObject.size(); i++) {
            Projeto p = (Projeto) projetoObject.get(i);
            projeto.add(p);
        }

        return projeto;
    }
   

        
    public static Vector<Projeto> getAllByEmp(String ssn) {
        ProjetoDAO dao = new ProjetoDAO();
        
        ArrayList<Object> projetoObject = (ArrayList<Object>) dao.getAllEmp(ssn);
        Vector<Projeto> projeto = new Vector<Projeto>();
         
        for(int i = 0 ; i < projetoObject.size() ; i++){
            Projeto p = (Projeto) projetoObject.get(i);             
            projeto.add(p);
         }
        
        return projeto;
    }    
        

    public Vector<Projeto> SearchByName(String input) throws Exception {

       ArrayList<Object> projetoObject = (ArrayList<Object>) FactoryDAO.getFactory("Projeto").get(input);
       Vector<Projeto> projeto = new Vector<Projeto>();
                for(int i = 0 ; i < projetoObject.size() ; i++)
         {
             Projeto p = (Projeto) projetoObject.get(i);             
             projeto.add(p);
         }
         
         return projeto;
    }  
    
}
