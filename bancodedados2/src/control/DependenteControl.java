/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import DAO.DependenteDAO;
import DAO.FactoryDAO;
import DAO.IObjectDAO;
import Model.Dependente;
import Model.Empregado;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author yuricampos
 */
public class DependenteControl  {

    
    public void post(String nome, String essn, String sexo, String datanascimento, String parentesco) throws Exception {
        
        Empregado empregado = new Empregado();
        empregado.setSsn(essn);
        
        Dependente dependente = new Dependente();
        dependente.setNome(nome);
        dependente.setEssn(empregado);
        dependente.setSexo(sexo);
        dependente.setDataNascimento(datanascimento);
        dependente.setParentesco(parentesco);

        FactoryDAO.getFactory("Dependente").post(dependente);  
    }


    public void update(String nome, String essn, String sexo, String datanascimento, String parentesco) throws Exception {
        
        Empregado empregado = new Empregado();
        empregado.setSsn(essn);
        
        Dependente dependente = new Dependente();
        dependente.setNome(nome);
        dependente.setEssn(empregado);
        dependente.setSexo(sexo);
        dependente.setDataNascimento(datanascimento);
        dependente.setParentesco(parentesco);

        System.out.println("----- essn " + dependente.getEssn().getSsn() + " nome" + dependente.getNome() +" sexp" +  dependente.getSexo() + " data "+  dependente.getDataNascimentoString() + " parentescop" + dependente.getParentesco());
        
        FactoryDAO.getFactory("Dependente").update(dependente);  
    }
    
    public void delete(String essn, String nomedependente) throws Exception{
        DependenteDAO dependentedao = new DependenteDAO();
        dependentedao.deleteDep(essn, nomedependente);
    }
    
        public static Vector<Dependente> getAll() throws Exception{
         ArrayList<Object> dependenteObject = (ArrayList<Object>) FactoryDAO.getFactory("Dependente").getAll();
         Vector<Dependente> dependente = new Vector<Dependente>();
         
         for(int i = 0 ; i < dependenteObject.size() ; i++)
         {
             Dependente d = (Dependente) dependenteObject.get(i);             
             dependente.add(d);
         }
         
         return dependente;
    }    


    public Vector<Dependente> SearchByEssn(String input) throws Exception {
        ArrayList<Object> dependenteObject = (ArrayList<Object>) FactoryDAO.getFactory("Dependente").get(input);
        Vector<Dependente> dependente = new Vector<Dependente>();
        for(int i = 0 ; i < dependenteObject.size() ; i++)
        {
            Dependente d = (Dependente) dependenteObject.get(i);             
            dependente.add(d);
        }

        return dependente;
    } 
    
    
    public Dependente SearchByEssnNome(String essn, String nome) throws Exception{
        DependenteDAO dao = new DependenteDAO();
        Dependente dependente = (Dependente) dao.getDependente(essn, nome);
        return dependente;
        
    }

    
}
