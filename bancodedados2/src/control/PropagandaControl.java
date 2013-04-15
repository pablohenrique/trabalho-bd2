/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import DAO.FactoryDAO;
import DAO.PropagandaDAO;
import Model.Propaganda;

/**
 *
 * @author yuricampos
 */
public class PropagandaControl {
    private PropagandaDAO dao;
    
    public PropagandaControl(){
        this.dao = (PropagandaDAO) FactoryDAO.getFactory("Propaganda");
    }
    
    private Propaganda createObjectTemplate(){
        Propaganda p = new Propaganda();
        return p;
    }
    
    public void post(){
        
    }
    
    public void update(){
        
    }
    
    public void delete(){
        
    }
    
    public Propaganda get(){
    return createObjectTemplate();
    }
    
    
    
    
    
}
