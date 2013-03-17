/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import DAO.DepartamentoDAO;
import DAO.EmpregadoDAO;
import DAO.ProjetoDAO;

/**
 *
 * @author yuricampos
 */
public class FuncoesControle {
    
        public boolean verificarExistenciaProjeto(int projetonumero){
        ProjetoDAO pdao = new ProjetoDAO();
        try{
            pdao.get(projetonumero);
            return true;
        } catch(Exception e){
            return false;
            
        }
    }
        
        public boolean verificarExistenciaDepartamento(int departamentonumero){
            DepartamentoDAO ddao = new DepartamentoDAO();
            try{
                ddao.get(departamentonumero);
                return true;
            }catch(Exception e){
            return false;
        }
        }
            
      
        
        public boolean verificarExistenciaEmpregado(String ssn){
            EmpregadoDAO edao = new EmpregadoDAO();
            try{
                edao.get(ssn);
                return true;
            } catch(Exception e){
                return false;
            }
        }
            
           
    
}
