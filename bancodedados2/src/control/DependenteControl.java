/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import DAO.DependenteDAO;
import DAO.FactoryDAO;
import Model.Dependente;
import Model.Empregado;
import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author yuricampos
 */
public class DependenteControl  {
    private DependenteDAO dao;
    
    public DependenteControl(){
        this.dao = (DependenteDAO) FactoryDAO.getFactory("Dependente");
    }
    
    private Dependente createObjectTemplate(String nome, String essn, String sexo, String datanascimento, String parentesco){
        Empregado empregado = new Empregado();
        empregado.setSsn(essn);
        FuncoesControle f = new FuncoesControle();
        Dependente dependente = new Dependente();
        dependente.setNome(nome);
        dependente.setEssn(empregado);
        dependente.setSexo(sexo);
        dependente.setDataNascimento(f.coverteStringData(datanascimento));
        dependente.setParentesco(parentesco);
        
        return dependente;
    }
    
    public void post(String nome, String essn, String sexo, String datanascimento, String parentesco) throws Exception {
        this.dao.post(this.createObjectTemplate(nome, essn, sexo, datanascimento, parentesco));
    }

    public void update(String nome, String essn, String sexo, String datanascimento, String parentesco) throws Exception {
        this.dao.update(this.createObjectTemplate(nome, essn, sexo, datanascimento, parentesco));
    }
    
    public void delete(String essn, String nomedependente) throws Exception{
        DependenteDAO dependentedao = new DependenteDAO();
        dependentedao.deletarDependente(essn, nomedependente);
    }
    
    public Vector<Dependente> getAll() throws Exception{
        Vector<Dependente> dependente = new Vector<Dependente>();

        for(Object aux : (ArrayList<Object>) this.dao.getAll())
            dependente.add((Dependente) aux);

        return dependente;
    }    

    public Vector<Dependente> SearchByEssn(String input) throws Exception {
        Vector<Dependente> dependente = new Vector<Dependente>();

        for(Object aux : (ArrayList<Object>) this.dao.get(input))
            dependente.add((Dependente) aux);

        return dependente;
    } 
    
    public Dependente SearchByEssnNome(String essn, String nome) throws Exception{
        return (Dependente) this.dao.buscarDependente(essn, nome);
    }
    
    public Vector<Dependente> SearchByEssnNomeVector(String essn, String nome){
        Vector<Dependente> dependente = new Vector<Dependente>();
        dependente.add((Dependente) this.dao.buscarDependente(essn, nome));
        return dependente;
    }
    
}
