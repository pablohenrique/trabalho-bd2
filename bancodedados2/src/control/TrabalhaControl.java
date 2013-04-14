/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;


import DAO.FactoryDAO;
import DAO.TrabalhaDAO;
import Model.Empregado;
import Model.Projeto;
import Model.Trabalha;
import java.sql.SQLException;
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
        Empregado e = new Empregado();
        e.setSsn(ssn);
        Projeto p = new Projeto();
        p.setNumero(projetonumero);
        trabalha.setEssn(e);
        trabalha.setProjeto(p);
        trabalha.setHoras(horas);
        return trabalha;
    }
    
    public void post(String ssn, int projetonumero, float horas) throws Exception {
        FuncoesControle f = new FuncoesControle();

        this.dao.post(this.createObjectTemplate(ssn, projetonumero, horas));
        

    }


    public void update(String ssn, int projetonumero, float horas) throws Exception {
        this.dao.update(this.createObjectTemplate(ssn, projetonumero, horas));
        
    }
    
    public void delete(String ssn, int projetonumero) throws Exception{
        this.dao.deletarEmpregadoProjeto(ssn, projetonumero);
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

    public float somarHoras() throws SQLException {
        return this.dao.somarHoras();
    }
    
    public float somarHorasEmpregado(String ssn) throws SQLException{
        return this.dao.somarHorasEmpregado(ssn);
    }
    
    public int buscarQuantidadeEmpregados() throws SQLException{
        return this.dao.buscarQuantidadeEmpregados();
    }
    
    public int buscarProjetoEmpregado(String ssn) throws SQLException{
        return this.dao.buscarProjetoEmpregado(ssn);
    }
    



    
    
}
