/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import Model.Dept_Localizacoes;
import java.util.ArrayList;

/**
 *
 * @author yuricampos
 */
public class Dept_LocalizacoesControl {
    
        
    public void post(int departamento, String nome) throws Exception {
        FuncoesControle f = new FuncoesControle();
        if(f.verificarExistenciaDepartamento(departamento) == false){
            throw new Exception("Erro: departamento informado nao foi encontrado");
        }else{
        Dept_Localizacoes dept_localizacoes = new Dept_Localizacoes();
        dept_localizacoes.setDepartamento_numero(departamento);
        dept_localizacoes.setNome(nome);
        //FALTA INSERIR NO BANCO  
        }


    }


    public void update(int departamento, String nome) throws Exception {
        FuncoesControle f = new FuncoesControle();
        if(f.verificarExistenciaDepartamento(departamento) == false){
           throw new Exception("Erro: departamento informado nao foi encontrado"); 
        }else{
        Dept_Localizacoes dept_localizacoes = new Dept_Localizacoes();
        dept_localizacoes.setDepartamento_numero(departamento);
        dept_localizacoes.setNome(nome);
        //FALTA INSERIR NO BANCO  
        }
    }
    

    public ArrayList<Dept_Localizacoes> getAll() {
throw new UnsupportedOperationException("Not supported yet.");
       
    }


    public Dept_Localizacoes SearchByNameExactly(String input) {
throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
