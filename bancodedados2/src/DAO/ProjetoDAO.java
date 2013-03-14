/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.util.ArrayList;

/**
 *
 * @author pablohenrique
 */
public class ProjetoDAO implements IObjectDAO{
    private final String SQL_POST = "";
    private final String SQL_GET = "";
    private final String SQL_GETALL = "";
    private final String SQL_UPDATE = "";
    private final String SQL_DELETE = "";
    private Conexao connect;

    @Override
    public void post(Object obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(Object obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object get(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<Object> getAll(Object obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
