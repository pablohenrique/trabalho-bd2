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
        IObjectDAO dao0 = FactoryDAO.getFactory("Empregado");
        Empregado empregado = (Empregado) dao0.read(ssn);
        IObjectDAO dao = FactoryDAO.getFactory("Projeto");
        Projeto projeto = (Projeto) dao0.read(projetonumero);
        Trabalha trabalha = new Trabalha();
        trabalha.setEssn(empregado);
        trabalha.setProjeto(projeto);
        trabalha.setHoras(horas);
        IObjectDAO trabalhaDAO = FactoryDAO.getFactory("Trabalha");
        trabalhaDAO.post(trabalha);
        }

    }


    public void update(String ssn, int projetonumero, float horas) throws Exception {
        FuncoesControle f = new FuncoesControle();
        if(f.verificarExistenciaProjeto(projetonumero) == false){
            throw new Exception("Erro: projeto informado nao foi encontrado");
        }else if(f.verificarExistenciaEmpregado(ssn) == false){
         throw new Exception("Erro: empregado informado nao foi encontrado");
     } else{
        IObjectDAO dao0 = FactoryDAO.getFactory("Empregado");
        Empregado empregado = (Empregado) dao0.read(ssn);
        IObjectDAO dao = FactoryDAO.getFactory("Projeto");
        Projeto projeto = (Projeto) dao0.read(projetonumero);
        Trabalha trabalha = new Trabalha();
        trabalha.setEssn(empregado);
        trabalha.setProjeto(projeto);
        trabalha.setHoras(horas);
        IObjectDAO trabalhaDAO = FactoryDAO.getFactory("Trabalha");
        trabalhaDAO.update(trabalha);
        }
    }
    
    public void delete(String ssn) throws Exception{
        FuncoesControle f = new FuncoesControle();
        if(f.verificarExistenciaTrabalha(ssn) == false){
            throw new Exception("Erro: empregado informado nao foi encontrado");
        } else{
            IObjectDAO trabalhaDAO = FactoryDAO.getFactory("Trabalha");
            trabalhaDAO.delete(ssn);
        }
    }
    
    


// retorn usando ssn
    public Trabalha getById(String input) throws Exception {
        IObjectDAO trabalhaDAO = FactoryDAO.getFactory("Trabalha");
        Trabalha trabalha = (Trabalha) trabalhaDAO.get(input);
        return trabalha;
    }

    
    
    
        public Vector<Trabalha> getAll()
    {
         IObjectDAO trabalhaDAO = FactoryDAO.getFactory("Trabalha");
        ArrayList<Object> trabalhaObject = (ArrayList<Object>) trabalhaDAO.getAll();
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
        IObjectDAO trabalhaDAO = FactoryDAO.getFactory("Trabalha");
        ArrayList<Object> trabalhaObject = (ArrayList<Object>) trabalhaDAO.read(input);
        Vector<Trabalha> trabalha = new Vector<Trabalha>();
        
        for(int i = 0 ; i < trabalhaObject.size() ; i++)
        {
            Trabalha t = (Trabalha) trabalhaObject.get(i);
            trabalha.add(t);
        }
        
        return trabalha;
    }



    
    
}
