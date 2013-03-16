/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import DAO.ProjetoDAO;
import Model.Projeto;
import java.util.ArrayList;

/**
 *
 * @author yuricampos
 */
public class ProjetoControl  {


    public void post(int numero, String nome, String localizacao, int departamento) {
        Projeto projeto = new Projeto();
        projeto.setNumero(numero);
        projeto.setNome(nome);
        projeto.setLocalizacao(localizacao);
        projeto.setDepartamento(departamento);
        ProjetoDAO projetoDAO = new ProjetoDAO();
        projetoDAO.post(projeto);
    }


    public void update(int numero, String nome, String localizacao, int departamento) {
        Projeto projeto = new Projeto();
        projeto.setNumero(numero);
        projeto.setNome(nome);
        projeto.setLocalizacao(localizacao);
        projeto.setDepartamento(departamento);
        ProjetoDAO projetoDAO = new ProjetoDAO();
        projetoDAO.update(projeto);
    }


    public Object getById(int input) {
        ProjetoDAO projetoDAO = new ProjetoDAO();
        Projeto projeto = (Projeto) projetoDAO.get(input);
        return projeto;
    }


    public ArrayList<Object> getAll() {
        ProjetoDAO projetoDAO = new ProjetoDAO();
        ArrayList<Object> projetos = projetoDAO.getAll();
        return projetos;
       
    }


    public Object SearchByNameExactly(String input) {
        ProjetoDAO projetoDAO = new ProjetoDAO();
        Projeto projeto = (Projeto) projetoDAO.read(input);
        return projeto;
    }


    
}
