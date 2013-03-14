/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.util.ArrayList;

/**
 *
 * @author yuricampos
 */
public interface IObjectControl {
    
        /**
     * Usado para inserir um objeto no banco de dados
     * @param Objeto
     * @return void
     */
    public void post(Object input);
    
        /**
     * Usado para atualizar um objeto no banco de dados
     * @param Objeto
     * @return void
     */
    public void update(Object input);
    
     /**
     * Usado para recuperar um objeto do banco de dados
     * @param int(id)
     * @return Object
     */
    public Object getById(int input);
    

    
     /**
     * Usado para recuperar todos os objetos de um banco de dados
     * @param Object
     * @return ArrayList<Object>
     */
    public ArrayList<Object> getAll(Object input);
    
     /**
     * Usado para recuperar todos os objetos de um banco de dados que possuam
     * o nome informado (busca)
     * @param String(nome)
     * @return ArrayList<Object>
     */
    public ArrayList<Object> getSearchByName(String input);
    
    
    
    
}
