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

    
    public void post(String nome, String essn, String sexo, Date datanascimento, String parentesco) throws Exception {
     FuncoesControle f = new FuncoesControle();
     if(f.verificarExistenciaEmpregado(essn) == false){
         throw new Exception("Erro: empregado informado nao foi encontrado");
     } else{
     Dependente dependente = new Dependente();
     dependente.setNome(nome);
     dependente.setEssn(essn);
     dependente.setSexo(sexo);
     dependente.setDataNascimento(datanascimento);
     dependente.setParentesco(parentesco);
     DependenteDAO dependenteDAO = new DependenteDAO();
     dependenteDAO.post(dependente);  
     }

    }


    public void update(String nome, String essn, String sexo, Date datanascimento, String parentesco) throws Exception {
          FuncoesControle f = new FuncoesControle();
     if(f.verificarExistenciaEmpregado(essn) == false){
         throw new Exception("Erro: empregado informado nao foi encontrado");
     } else{
     Dependente dependente = new Dependente();
     dependente.setNome(nome);
     dependente.setEssn(essn);
     dependente.setSexo(sexo);
     dependente.setDataNascimento(datanascimento);
     dependente.setParentesco(parentesco);
     DependenteDAO dependenteDAO = new DependenteDAO();
     dependenteDAO.update(dependente);
     }
    }


    public Dependente getById(int input) {
        DependenteDAO dependenteDAO = new DependenteDAO();
        Dependente dependente = (Dependente) dependenteDAO.get(input);
        return dependente;
    }


    public ArrayList<Dependente> getAll() {
       DependenteDAO dependenteDAO = new DependenteDAO();
       ArrayList<Object> dependentesObject = dependenteDAO.getAll();
       ArrayList<Dependente> dependentes = null;
       for(int i = 0 ; i < dependentesObject.size() ; i++){
           Dependente d = (Dependente) dependentesObject.get(i);
           dependentes.add(d);
       }
       return dependentes;
    }


    public Dependente SearchByNameExactly(String input) {
        DependenteDAO dependenteDAO = new DependenteDAO();
        Dependente dependente = (Dependente)dependenteDAO.read(input);
        return dependente;
    }

    
}
