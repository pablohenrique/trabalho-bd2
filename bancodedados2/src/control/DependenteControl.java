/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import DAO.DependenteDAO;
import Model.Dependente;
import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author yuricampos
 */
public class DependenteControl  {

    
    public void post(String nome, String essn, String sexo, Date datanascimento, String parentesco) {
     Dependente dependente = new Dependente();
     dependente.setNome(nome);
     dependente.setEssn(essn);
     dependente.setSexo(sexo);
     dependente.setDataNascimento(datanascimento);
     dependente.setParentesco(parentesco);
     DependenteDAO dependenteDAO = new DependenteDAO();
     dependenteDAO.post(dependente);
    }


    public void update(String nome, String essn, String sexo, Date datanascimento, String parentesco) {
     Dependente dependente = new Dependente();
     dependente.setNome(nome);
     dependente.setEssn(essn);
     dependente.setSexo(sexo);
     dependente.setDataNascimento(datanascimento);
     dependente.setParentesco(parentesco);
     DependenteDAO dependenteDAO = new DependenteDAO();
     dependenteDAO.update(dependente);
    }


    public Object getById(int input) {
        DependenteDAO dependenteDAO = new DependenteDAO();
        Dependente dependente = (Dependente) dependenteDAO.get(input);
        return dependente;
    }


    public ArrayList<Object> getAll() {
       DependenteDAO dependenteDAO = new DependenteDAO();
       ArrayList<Object> dependentes = dependenteDAO.getAll();
       return dependentes;
    }


    public Object SearchByNameExactly(String input) {
        DependenteDAO dependenteDAO = new DependenteDAO();
        Dependente dependente = (Dependente)dependenteDAO.read(input);
        return dependente;
    }

    
}
