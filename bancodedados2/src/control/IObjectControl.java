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
    public ArrayList<Object> SearchByName(String input);
    
    
     /**
     * Usado para efetuar o login do usuarios
     * @param String(usuario), String(senha)
     * @return 0 caso usuario e/ou senha incorretos
     * @return 1 caso funcionario
     * @return 2 caso gerente
     * @return 3 caso supervisor
     * @return 4 caso gerente e supervisor
     */
    public int login(String usuario, String senha);
    
    
    
    
}
