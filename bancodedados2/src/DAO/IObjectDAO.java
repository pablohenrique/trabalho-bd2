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
public interface IObjectDAO {
    
    /*
     * Usado para inserir um objeto no banco de dados
     * @param Objeto
     * @return void
     */
    public void post(Object obj);
    
    /*
     * Usado para atualizar um objeto no banco de dados
     * @param Objeto
     * @return void
     */
    public void update(Object obj);
    
    /*
     * Usado para recuperar um objeto do banco de dados
     * @param int
     * @return Object
     */
    public Object get(int id);
    
    /*
     * Usado para recuperar todos os objetos de um banco de dados
     * @param Object
     * @return ArrayList<Object>
     */
    public ArrayList<Object> getAll(Object obj);
    
    /*
     * Usado para remover um objeto do banco de dados
     * @param int
     * @return void
     */
    public void delete(int id);
    
}