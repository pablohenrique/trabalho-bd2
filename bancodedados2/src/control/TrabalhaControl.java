/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;


import DAO.FactoryDAO;
import DAO.IObjectDAO;
import DAO.TrabalhaDAO;
import Model.Empregado;
import Model.Projeto;
import Model.Trabalha;
import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author yuricampos
 */
public class TrabalhaControl {
    
    private TrabalhaDAO dao;
    
    public TrabalhaControl(){
        this.dao = (TrabalhaDAO) FactoryDAO.getFactory("Trabalha");
    }
    
    private Trabalha createObjectTemplate(String ssn, int projetonumero, float horas) throws Exception{
        Trabalha trabalha = new Trabalha();
        trabalha.setEssn((Empregado) FactoryDAO.getFactory("Empregado").read(ssn));
        trabalha.setProjeto((Projeto) FactoryDAO.getFactory("Projeto").read(projetonumero));
        trabalha.setHoras(horas);
        return trabalha;
    }
    
    public void post(String ssn, int projetonumero, float horas) throws Exception {
        FuncoesControle f = new FuncoesControle();
        if(f.verificarExistenciaProjeto(projetonumero) == false){
            throw new Exception("Erro: projeto informado nao foi encontrado");
        }else if(f.verificarExistenciaEmpregado(ssn) == false){
         throw new Exception("Erro: empregado informado nao foi encontrado");
     } else{
        this.dao.post(this.createObjectTemplate(ssn, projetonumero, horas));
        }

    }


    public void update(String ssn, int projetonumero, float horas) throws Exception {
        FuncoesControle f = new FuncoesControle();
        if(f.verificarExistenciaProjeto(projetonumero) == false){
            throw new Exception("Erro: projeto informado nao foi encontrado");
        }else if(f.verificarExistenciaEmpregado(ssn) == false){
         throw new Exception("Erro: empregado informado nao foi encontrado");
     } else{
        this.dao.update(this.createObjectTemplate(ssn, projetonumero, horas));
        }
    }
    
    public void delete(String ssn) throws Exception{
        this.dao.delete(ssn);
    }
    
    


// retorn usando ssn
    public Trabalha getById(String input) throws Exception {
        return (Trabalha) this.dao.get(input);
    }
    
    public Vector<Trabalha> getAll() throws Exception {
        Vector<Trabalha> trabalha = new Vector<Trabalha>();
        
        for(Object aux : this.dao.getAll())
            trabalha.add((Trabalha) aux);
        
        return trabalha;
    }    
    
 //retorna usando projeto
    public Vector<Trabalha> SearchByName(String input){
        Vector<Trabalha> trabalha = new Vector<Trabalha>();
        
        for(Object aux : (ArrayList<Object>) this.dao.read(input))
            trabalha.add((Trabalha) aux);
        
        return trabalha;
    }



    
    
}
