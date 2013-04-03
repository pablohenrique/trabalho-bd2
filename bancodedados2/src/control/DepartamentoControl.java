/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import DAO.DepartamentoDAO;
import DAO.FactoryDAO;
import Model.Departamento;
import Model.Empregado;
import java.util.ArrayList;
import java.util.Vector;

/**
 * teste
 * @author yuricampos
 */
public class DepartamentoControl
{
    private DepartamentoDAO dao;
    
    public DepartamentoControl(){
        this.dao = (DepartamentoDAO) FactoryDAO.getFactory("Departamento");
    }
    
    private Departamento createObjectTemplate(String nome, String gerssn, String gerdatainicio){
        Empregado em = new Empregado();
        em.setSsn(gerssn);
        
        Departamento departamento = new Departamento();        
        departamento.setNome(nome);
        departamento.setGerenteSsn(em);
        departamento.setGerenteDataInicio(gerdatainicio);
        
        return departamento;
    }
    
    public void post(String nome, String gerssn, String gerdatainicio) throws Exception {
        this.dao.post(this.createObjectTemplate(nome, gerssn, gerdatainicio));
    }

    public void update(int numero, String nome, String gerssn, String gerdatainicio) throws Exception {
        this.dao.update(this.createObjectTemplate(nome, gerssn, gerdatainicio));        
    }
    
    public void delete(int numero) throws Exception{
        FuncoesControle f = new FuncoesControle();
        
        if(f.verificarExistenciaDepartamento(numero) == false)
            throw new Exception("Erro: departamento informado nao foi encontrado");
        else
            this.dao.delete(numero);
    }

    public Departamento getById(int numero) throws Exception {
        return (Departamento) this.dao.get(numero);
    }
    
    public Departamento getByGer(String gerssn) throws Exception {
        return (Departamento) this.dao.getGer(gerssn);
    }
    
    public Vector<Departamento> getAll() throws Exception {
         Vector<Departamento> departamento = new Vector<Departamento>();
         
         for(Object aux : (ArrayList<Object>) this.dao.getAll())
             departamento.add((Departamento) aux);
         
         return departamento;
    }    

    public Vector<Departamento> SearchByName(String input) throws Exception {
        Vector<Departamento> departamento = new Vector<Departamento>();
        
        for(Object aux : (ArrayList<Object>) this.dao.get(input))
             departamento.add((Departamento) aux);
        
        return departamento;
    }  
    
}
