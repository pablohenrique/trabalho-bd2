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
     IObjectDAO dao0 = FactoryDAO.getFactory("Empregado");   
     Empregado empregado = (Empregado) dao0.read(essn);
     Dependente dependente = new Dependente();
     dependente.setNome(nome);
     dependente.setEssn(empregado);
     dependente.setSexo(sexo);
     dependente.setDataNascimento(datanascimento);
     dependente.setParentesco(parentesco);
     IObjectDAO dependenteDAO = FactoryDAO.getFactory("Dependente");
     dependenteDAO.post(dependente);  
     }

    }


    public void update(String nome, String essn, String sexo, Date datanascimento, String parentesco) throws Exception {
          FuncoesControle f = new FuncoesControle();
     if(f.verificarExistenciaEmpregado(essn) == false){
         throw new Exception("Erro: empregado informado nao foi encontrado");
     } else{
     IObjectDAO dao0 = FactoryDAO.getFactory("Empregado");   
     Empregado empregado = (Empregado) dao0.read(essn);
     Dependente dependente = new Dependente();
     dependente.setNome(nome);
     dependente.setEssn(empregado);
     dependente.setSexo(sexo);
     dependente.setDataNascimento(datanascimento);
     dependente.setParentesco(parentesco);
     IObjectDAO dependenteDAO = FactoryDAO.getFactory("Dependente");
     dependenteDAO.update(dependente);
     }
    }
    
    public void delete(String nome) throws Exception{
        FuncoesControle f = new FuncoesControle();
        if(f.verificarExistenciaDependente(nome) == false){
            throw new Exception("Erro: dependente informado nao foi encontrado");
        } else{
            IObjectDAO dependenteDAO = FactoryDAO.getFactory("Dependente");
            dependenteDAO.delete(nome);
        }
    }
    
        public static Vector<Dependente> getAll(){
         IObjectDAO dao = FactoryDAO.getFactory("Dependente");
         ArrayList<Object> dependenteObject = (ArrayList<Object>) dao.getAll();
         Vector<Dependente> dependente = new Vector<Dependente>();
         
         for(int i = 0 ; i < dependenteObject.size() ; i++)
         {
             Dependente d = (Dependente) dependenteObject.get(i);             
             dependente.add(d);
         }
         
         return dependente;
    }    


    public Vector<Dependente> SearchByName(String input) throws Exception {
       IObjectDAO dao = FactoryDAO.getFactory("Departamento");
       ArrayList<Object> dependenteObject = (ArrayList<Object>) dao.get(input);
       Vector<Dependente> dependente = new Vector<Dependente>();
                for(int i = 0 ; i < dependenteObject.size() ; i++)
         {
             Dependente d = (Dependente) dependenteObject.get(i);             
             dependente.add(d);
         }
         
         return dependente;
    } 

    
}
