/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import DAO.EmpregadoDAO;
import Model.Empregado;
import java.util.ArrayList;

/**
 *
 * @author yuricampos
 */
public class EmpregadoControl implements IObjectControl {

    @Override
    public void post(Object input) {
        Empregado e = (Empregado) input;
        EmpregadoDAO edao = new EmpregadoDAO();
        edao.post(e);
    }

    @Override
    public void update(Object input) {
      Empregado e = (Empregado) input;
      EmpregadoDAO edao = new EmpregadoDAO();
      edao.update(e);
    }

    @Override
    public Object getById(int input) {
      EmpregadoDAO edao = new EmpregadoDAO();
      Empregado e = (Empregado) edao.get(input);
      return e;
    }

    @Override
    public ArrayList<Object> getAll() {
       EmpregadoDAO edao = new EmpregadoDAO();
       ArrayList<Object> all =  edao.getAll();
       return all;
    }

    @Override
    public ArrayList<Object> SearchByName(String input) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int login(String usuario, String senha) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object read(String input) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
