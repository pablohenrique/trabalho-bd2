/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import DAO.ProjetoDAO;

/**
 *
 * @author yuricampos
 */
public class FuncoesControle {
    
        public boolean verificarExistenciaProjeto(int projetonumero){
        ProjetoDAO pdao = new ProjetoDAO();
        if(pdao.get(projetonumero) == null){
            return false;
        } else{
             return true;
        }
    }
        
    
}
