/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

/**
 *
 * @author pablohenrique
 */
public class FactoryDAO {
    
    public static IObjectDAO getFactory(String dao){
        switch(dao){
            case("Empregado"):
                return null;
            default:
                return null;
        }
    }
    
}
