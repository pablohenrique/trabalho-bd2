/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;


import DAO.FactoryDAO;
import DAO.IObjectDAO;
import Model.Empregado;
import Model.Projeto;
import Model.Trabalha;
import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author yuricampos
 */
public class TrabalhaControl {
    
    
    public void post(String ssn, int projetonumero, float horas) throws Exception {
        FuncoesControle f = new FuncoesControle();
        if(f.verificarExistenciaProjeto(projetonumero) == false){
            throw new Exception("Erro: projeto informado nao foi encontrado");
        }else if(f.verificarExistenciaEmpregado(ssn) == false){
         throw new Exception("Erro: empregado informado nao foi encontrado");
     } else{
        Empregado empregado = (Empregado) FactoryDAO.getFactory("Empregado").read(ssn);
        Projeto projeto = (Projeto) FactoryDAO.getFactory("Projeto").read(projetonumero);
        Trabalha trabalha = new Trabalha();
        trabalha.setEssn(empregado);
        trabalha.setProjeto(projeto);
        trabalha.setHoras(horas);
        FactoryDAO.getFactory("Trabalha").post(trabalha);
        }

    }


    public void update(String ssn, int projetonumero, float horas) throws Exception {
        FuncoesControle f = new FuncoesControle();
        if(f.verificarExistenciaProjeto(projetonumero) == false){
            throw new Exception("Erro: projeto informado nao foi encontrado");
        }else if(f.verificarExistenciaEmpregado(ssn) == false){
         throw new Exception("Erro: empregado informado nao foi encontrado");
     } else{
        Empregado empregado = (Empregado) FactoryDAO.getFactory("Empregado").read(ssn);
        Projeto projeto = (Projeto) FactoryDAO.getFactory("Projeto").read(projetonumero);
        Trabalha trabalha = new Trabalha();
        trabalha.setEssn(empregado);
        trabalha.setProjeto(projeto);
        trabalha.setHoras(horas);
        FactoryDAO.getFactory("Trabalha").update(trabalha);
        }
    }
    
    public void delete(String ssn) throws Exception{
        FuncoesControle f = new FuncoesControle();
        if(f.verificarExistenciaTrabalha(ssn) == false){
            throw new Exception("Erro: empregado informado nao foi encontrado");
        } else{
            FactoryDAO.getFactory("Trabalha").delete(ssn);
        }
    }
    
    


// retorn usando ssn
    public Trabalha getById(String input) throws Exception {
        Trabalha trabalha = (Trabalha) FactoryDAO.getFactory("Trabalha").get(input);
        return trabalha;
    }

    
    
    
        public Vector<Trabalha> getAll()
    {
        ArrayList<Object> trabalhaObject = (ArrayList<Object>) FactoryDAO.getFactory("Trabalha").getAll();
        Vector<Trabalha> trabalha = new Vector<Trabalha>();
        
        for(int i = 0 ; i < trabalhaObject.size() ; i++)
        {
            Trabalha t = (Trabalha) trabalhaObject.get(i);
            trabalha.add(t);
        }
        
        return trabalha;
    }    
    
 //retorna usando projeto
    public Vector<Trabalha> SearchByName(String input)
    {
        ArrayList<Object> trabalhaObject = (ArrayList<Object>) FactoryDAO.getFactory("Trabalha").read(input);
        Vector<Trabalha> trabalha = new Vector<Trabalha>();
        
        for(int i = 0 ; i < trabalhaObject.size() ; i++)
        {
            Trabalha t = (Trabalha) trabalhaObject.get(i);
            trabalha.add(t);
        }
        
        return trabalha;
    }



    
    
}
