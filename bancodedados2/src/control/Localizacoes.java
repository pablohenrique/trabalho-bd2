/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import DAO.FactoryDAO;
import DAO.LocalizacaoDAO;
import Model.Departamento;
import Model.Localizacao;
import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author yuricampos
 */
public class Localizacoes {
    
    private LocalizacaoDAO dao;
    
    public Localizacoes(){
        this.dao = (LocalizacaoDAO) FactoryDAO.getFactory("Localizacao");
    }
    
    private Localizacao createObject(int dnumero, String nome) throws Exception{
        Departamento dep = new Departamento();
        dep.setNumero(dnumero);
        Localizacao localizacao = new Localizacao();
        localizacao.setDepartamento(dep);
        localizacao.setNome(nome);
        return localizacao;
    }
    
    public void post(int dnumero, String nome) throws Exception {        
        this.dao.post(this.createObject(dnumero, nome));        
    }

    //remove essa funcao
    public void update(int dnumero, String nome) throws Exception {
        FuncoesControle f = new FuncoesControle();
        

            this.dao.post(this.createObject(dnumero, nome));
    }
    
    //remove essa funcao
    public Vector<Localizacao>  getAll() throws Exception {
        Vector<Localizacao> localizacao = new Vector<Localizacao>();
        
        for(Object aux : this.dao.getAll())
            localizacao.add((Localizacao) aux);
        
        return localizacao;
    } 
    
    public Vector<Localizacao>  getAllByDep(int depid) throws Exception {
        Vector<Localizacao> localizacao = new Vector<Localizacao>();
        
        for(Object aux : this.dao.buscarDepartamentos(depid))
            localizacao.add((Localizacao) aux);
        
        return localizacao;
    } 
    
    //remove essa funcao
    public Vector<Localizacao>  SearchByName(String input){
        Vector<Localizacao> localizacao = new Vector<Localizacao>();
        
        for(Object aux : (ArrayList<Object>) this.dao.read(input))
            localizacao.add((Localizacao) aux);
        
        return localizacao;
    } 
    
    public void delete(String localizacao) throws Exception{
        this.dao.delete(localizacao);
    }
    
}
