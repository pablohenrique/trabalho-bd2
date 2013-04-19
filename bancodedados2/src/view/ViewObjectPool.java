/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.HashMap;

/**
 *
 * @author pablohenrique
 */
public class ViewObjectPool {
    public static HashMap<String,Object> objetos = new HashMap<>();
    public static ViewObjectPool instance;
    
    /*
     *                        COMO USAR
     *  Usa-se o metodo set(String,Objeto) para armazenar no pool
     *  todos os objetos necessarios para o funcionamento do sistema.
     *  **Vale salientar que SOMENTE os objetos usados com frequencia**
     *  **deverao ser colocados neste pool.**
     *  
     *  O primeiro parametro da funcao set e uma string e ela sera usada
     *  para armazenar a chave do objeto, ou seja, a chave e usada para
     *  encontrar o objeto! Use nomes sabios!
     *  Chaves corretas: empregadoLogado, controladorGlobal, vacaAmarela
     *  Chaves incorretas: el, cg, va
     */
    
    private ViewObjectPool(){}
    
    public static ViewObjectPool getInstance() {
        if(instance != null)
            return instance;
        else
            return new ViewObjectPool();
    }
    
    public static Object get(String objeto){
        if(objetos.containsKey(objeto))
            return objetos.get(objeto);
        else
            return null;
    }
    
    public static void set(String chave, Object objeto){
/*        for(String aux : objetos.keySet())
            if(objetos.get(aux).getClass() == chave){
                objetos.remove(aux);
                objetos.put(aux, objeto);
                return;
            }*/
        
        if(!objetos.containsKey(objeto))
            objetos.put(chave, objeto);
        else{
            objetos.remove(objeto);
            objetos.put(chave, objeto);
        }
        return;
    }
}
