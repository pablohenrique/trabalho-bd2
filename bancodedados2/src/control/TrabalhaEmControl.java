/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;


import Model.Trabalha_Em;
import java.util.ArrayList;

/**
 *
 * @author yuricampos
 */
public class TrabalhaEmControl {
    
    
    public void post(String ssn, int projetonumero, float horas) throws Exception {
        FuncoesControle f = new FuncoesControle();
        if(f.verificarExistenciaProjeto(projetonumero) == false){
            throw new Exception("Erro: projeto informado nao foi encontrado");
        }else{
        Trabalha_Em trabalha_em = new Trabalha_Em();
        trabalha_em.setEssn(ssn);
        trabalha_em.setProjeto_numero(projetonumero);
        trabalha_em.setHoras(horas);
        //FALTA INSERIR NO BANCO  
        }

    }


    public void update(String ssn, int projetonumero, float horas) throws Exception {
        FuncoesControle f = new FuncoesControle();
        if(f.verificarExistenciaProjeto(projetonumero) == false){
            throw new Exception("Erro: projeto informado nao foi encontrado");
        } else{
        Trabalha_Em trabalha_em = new Trabalha_Em();
        trabalha_em.setEssn(ssn);
        trabalha_em.setProjeto_numero(projetonumero);
        trabalha_em.setHoras(horas);
        //FALTA INSERIR NO BANCO
        }
    }
    
    



    public Trabalha_Em getById(int input) {
    throw new UnsupportedOperationException("Not supported yet.");
    }


    public ArrayList<Trabalha_Em> getAll() {
throw new UnsupportedOperationException("Not supported yet.");
       
    }


    public Trabalha_Em SearchByNameExactly(String input) {
throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
}
