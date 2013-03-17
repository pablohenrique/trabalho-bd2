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


    public void post(int numero, String nome, String localizacao, int departamento) throws Exception {
        //FALTA VERIFICAR SE LOCALIZACAO EXISTE
        FuncoesControle f = new FuncoesControle();
        if(f.verificarExistenciaDepartamento(departamento) == false){
            throw new Exception("Erro: departamento informado nao foi encontrado");
        } else{
        Projeto projeto = new Projeto();
        projeto.setNumero(numero);
        projeto.setNome(nome);
        projeto.setLocalizacao(localizacao);
        projeto.setDepartamento(departamento);
        ProjetoDAO projetoDAO = new ProjetoDAO();
        projetoDAO.post(projeto);
        }

    }


    public void update(int numero, String nome, String localizacao, int departamento) throws Exception {
                FuncoesControle f = new FuncoesControle();
        if(f.verificarExistenciaDepartamento(departamento) == false){
            throw new Exception("Erro: departamento informado nao foi encontrado");
        } else{
        Projeto projeto = new Projeto();
        projeto.setNumero(numero);
        projeto.setNome(nome);
        projeto.setLocalizacao(localizacao);
        projeto.setDepartamento(departamento);
        ProjetoDAO projetoDAO = new ProjetoDAO();
        projetoDAO.update(projeto); 
        }

    }


    public Projeto getById(int input) {
        ProjetoDAO projetoDAO = new ProjetoDAO();
        Projeto projeto = (Projeto) projetoDAO.get(input);
        return projeto;
    }


    public ArrayList<Projeto> getAll() {
        ProjetoDAO projetoDAO = new ProjetoDAO();
        ArrayList<Object> projetosObject = projetoDAO.getAll();
        ArrayList<Projeto> projetos = null;
        for(int i = 0 ; i < projetosObject.size() ; i++){
            Projeto p = (Projeto) projetosObject.get(i);
            projetos.add(p);
        }
        return projetos;
       
    }


    public Projeto SearchByNameExactly(String input) {
        ProjetoDAO projetoDAO = new ProjetoDAO();
        Projeto projeto = (Projeto) projetoDAO.read(input);
        return projeto;
    }


    
}
