/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

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

    
    public void post(String nome, String essn, String sexo, Date datanascimento, String parentesco) throws Exception {
     FuncoesControle f = new FuncoesControle();
     if(f.verificarExistenciaEmpregado(essn) == false){
         throw new Exception("Erro: empregado informado nao foi encontrado");
     } else{ 
     Empregado empregado = (Empregado) FactoryDAO.getFactory("Empregado").read(essn);
     Dependente dependente = new Dependente();
     dependente.setNome(nome);
     dependente.setEssn(empregado);
     dependente.setSexo(sexo);
     dependente.setDataNascimento(datanascimento);
     dependente.setParentesco(parentesco);
     FactoryDAO.getFactory("Dependente").post(dependente);  
     }

    }


    public void update(String nome, String essn, String sexo, Date datanascimento, String parentesco) throws Exception {
          FuncoesControle f = new FuncoesControle();
     if(f.verificarExistenciaEmpregado(essn) == false){
         throw new Exception("Erro: empregado informado nao foi encontrado");
     } else{
     Empregado empregado = (Empregado) FactoryDAO.getFactory("Empregado").read(essn);
     Dependente dependente = new Dependente();
     dependente.setNome(nome);
     dependente.setEssn(empregado);
     dependente.setSexo(sexo);
     dependente.setDataNascimento(datanascimento);
     dependente.setParentesco(parentesco);
     FactoryDAO.getFactory("Dependente").update(dependente);  
     }
    }
    
    public void delete(String nome) throws Exception{
        FuncoesControle f = new FuncoesControle();
        if(f.verificarExistenciaDependente(nome) == false){
            throw new Exception("Erro: dependente informado nao foi encontrado");
        } else{
            FactoryDAO.getFactory("Dependente").delete(nome);
        }
    }
    
        public static Vector<Dependente> getAll(){
         ArrayList<Object> dependenteObject = (ArrayList<Object>) FactoryDAO.getFactory("Dependente").getAll();
         Vector<Dependente> dependente = new Vector<Dependente>();
         
         for(int i = 0 ; i < dependenteObject.size() ; i++)
         {
             Dependente d = (Dependente) dependenteObject.get(i);             
             dependente.add(d);
         }
         
         return dependente;
    }    


    public Vector<Dependente> SearchByName(String input) throws Exception {
       ArrayList<Object> dependenteObject = (ArrayList<Object>) FactoryDAO.getFactory("Departamento").get(input);
       Vector<Dependente> dependente = new Vector<Dependente>();
                for(int i = 0 ; i < dependenteObject.size() ; i++)
         {
             Dependente d = (Dependente) dependenteObject.get(i);             
             dependente.add(d);
         }
         
         return dependente;
    } 

    
}
