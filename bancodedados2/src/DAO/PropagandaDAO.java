/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author pablohenrique
 */
public class PropagandaDAO implements IObjectDAO{
    
    private final String SQL_POST = "INSERT INTO cia.propaganda(projeto, dataInicio, dataFinal, agencia, tarifa) VALUES(?,?,?,?,?);";
    private final String SQL_UPDATE = "UPDATE cia.propaganda AS p SET p.projeto = ? AND p.dataInicio = ? AND p.dataFinal = ? AND p.agencia = ? AND p.tarifa = ? AND p.ganhos = ? WHERE p.id = ?;";
    private final String SQL_GET = "SELECT * FROM cia.propaganda WHERE id = ?;";
    private final String SQL_READ = "SELECT * FROM cia.propaganda WHERE projeto = ?;";
    private final String SQL_GETALL = "SELECT * FROM cia.propaganda;";
    private final String SQL_DELETE = "DELETE FROM cia.propaganda WHERE id = ?;";
    private final String SQL_DELETEPROJ = "DELETE FROM cia.propaganda WHERE projeto = ?;";

    private Object criarObjetoTemplate(){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public Object gerarObjeto(String ssn){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    private ArrayList<Object> buscarVariosObjetosTemplate() throws SQLException{
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void post(Object input) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(Object input) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    // busca a propaganda pelo id
    @Override
    public Object get(Object input) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    // busca a propaganda pelo projeto
    @Override
    public Object read(Object input) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<Object> getAll() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void delete(Object input) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    // deletar propaganda pelo projeto
    public void deletarProjeto(Object input) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
