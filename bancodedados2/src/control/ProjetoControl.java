/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;


import DAO.FactoryDAO;
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

    private ProjetoDAO dao;
    
    public ProjetoControl(){
        this.dao = (ProjetoDAO) FactoryDAO.getFactory("Projeto");
    }
    
    private Projeto creteObjectTemplate(String nome, String localizacao, int dnumero) throws Exception{
        Projeto projeto = new Projeto();
        Departamento d = new Departamento();
        d.setNumero(dnumero);
        projeto.setNome(nome);
        projeto.setLocalizacao(localizacao);
        projeto.setDepartamento(d);
        return projeto;
    }

    public void post(String nome, String localizacao, int dnumero) throws Exception {
        this.dao.post(this.creteObjectTemplate(nome, localizacao, dnumero));
    }
    

    public void update(int numero, String nome, String localizacao, int dnumero) throws Exception{           
        Projeto p = this.creteObjectTemplate(nome, localizacao, dnumero);
        p.setNumero(numero);
        this.dao.update(p);
    }
    
    public void delete(int numero) throws Exception{
        this.dao.delete(numero);
    }

    public Projeto getById(int input) throws Exception {
        return (Projeto) this.dao.get(input);
    }
    
    public Vector<Projeto> getByIdVector(int input){
        Vector<Projeto> projeto = new Vector<Projeto>();
        projeto.add((Projeto) this.dao.get(input));
        return projeto;
    }
         
    public Vector<Projeto> getAll() throws Exception {
        Vector<Projeto> projeto = new Vector<Projeto>();
        
        for(Object aux : this.dao.getAll())
            projeto.add((Projeto) aux);

        return projeto;
    }    
        
    public Vector<Projeto> getAllByDepNome(String nomeDepto)  throws Exception{
        Vector<Projeto> projeto = new Vector<Projeto>();

        for(Object aux : this.dao.buscarNomeDepartamento(nomeDepto))
            projeto.add((Projeto) aux);

        return projeto;
    }   
        
    public Vector<Projeto> getAllByDepNumero(int depnumero)  throws Exception{
        Vector<Projeto> projeto = new Vector<Projeto>();

        for(Object aux : this.dao.buscarNumeroDepartamento(depnumero))
            projeto.add((Projeto) aux);

        return projeto;
    }
    
    public Vector<Projeto> getAllByEmp(String ssn) throws Exception {
        Vector<Projeto> projeto = new Vector<Projeto>();
         
        for(Object aux : this.dao.buscarEmpregado(ssn))
            projeto.add((Projeto) aux);
        
        return projeto;
    }

    public Vector<Projeto> SearchByName(String input) throws Exception {
        Vector<Projeto> projeto = new Vector<Projeto>();
        
        for(Object aux : (ArrayList<Object>) this.dao.read(input))
            projeto.add((Projeto) aux);

        return projeto;
    }  
    
}
