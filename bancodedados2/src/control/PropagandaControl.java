/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import DAO.FactoryDAO;
import DAO.PropagandaDAO;
import Model.Projeto;
import Model.Propaganda;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author yuricampos
 */
public class PropagandaControl {
    private PropagandaDAO dao;
    
    public PropagandaControl(){
        this.dao = (PropagandaDAO) FactoryDAO.getFactory("Propaganda");
    }
    
    private Propaganda createObjectTemplate(String agencia, Date dataFinal, Date dataInicial, int pnumero, float tarifa ){
        Propaganda p = new Propaganda();
        Projeto projeto = new Projeto();
        projeto.setNumero(pnumero);
        p.setAgencia(agencia);
        p.setDataFinal(dataFinal);
        p.setDataInicio(dataInicial);
        p.setProjeto(projeto);
        p.setTarifa(tarifa);
        return p;
    }
    
    public void post(String agencia, Date dataFinal, Date dataInicial, int pnumero, float tarifa) throws Exception{
        this.dao.post(this.createObjectTemplate(agencia,dataFinal,dataInicial,pnumero,tarifa));
    }
    
    public void update(int numero, String agencia, Date dataFinal, Date dataInicial, int pnumero, float tarifa) throws Exception{
        Propaganda p = this.createObjectTemplate(agencia, dataFinal, dataInicial, pnumero, tarifa);
        p.setNumero(numero);
        this.dao.update(p);
    }
    
    
    public void deletePropaganda(int numeroprop) throws Exception{
        this.dao.delete(numeroprop);
        
    }
    
    public void deleteProjeto(int numeroprojeto) throws Exception{
        this.dao.deletarProjeto(numeroprojeto);
    }
    
    public Propaganda getPropaganda(int nropropaganda) throws Exception{
        return (Propaganda) this.dao.get(nropropaganda);
    }
    
    public Vector<Propaganda> getPropagandaProjeto(int nroprojeto) throws Exception{
        Vector<Propaganda> propagandas = new Vector<Propaganda>();
        for(Object aux : (ArrayList<Object>) this.dao.read(nroprojeto))
            propagandas.add((Propaganda) aux);
        
        return propagandas;
    }
    
    public Vector<Propaganda> getAll() throws Exception{
        Vector<Propaganda> propagandas = new Vector<Propaganda>();
        for(Object aux : this.dao.getAll())
            propagandas.add((Propaganda) aux);
        
        return propagandas;
    }
    
    public float somarTarifa(int projeto){
        return this.dao.somarTarifa(projeto);
    }
    
    public float somarDespesa(int projeto){
        return this.dao.somarDespesa(projeto);
    }
    
    
    
    
}
