/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.util.ArrayList;
import Model.Departamento;

/**
 *
 * @author pablohenrique
 */
public class DepartamentoDAO implements IObjectDAO{
    private final String SQL_POST = "";
    private final String SQL_GET = "";
    private final String SQL_GETALL = "";
    private final String SQL_UPDATE = "";
    private final String SQL_DELETE = "";
    private Conexao connect;
    
    @Override
    public void post(Object input) {
        Departamento aux = (Departamento) input;
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(Object input) {
        Departamento aux = (Departamento) input;
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object get(int input) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public Object read(String input) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<Object> getAll(Object input) {
        Departamento aux = (Departamento) input;
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(int input) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}