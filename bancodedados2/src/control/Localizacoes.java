/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import DAO.FactoryDAO;
import DAO.IObjectDAO;
import Model.Departamento;
import Model.Localizacao;
import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author yuricampos
 */
public class Localizacoes {
    
        
    public void post(int dnumero, String nome) throws Exception {
        FuncoesControle f = new FuncoesControle();
        if(f.verificarExistenciaDepartamento(dnumero) == false){
            throw new Exception("Erro: departamento informado nao foi encontrado");
        }else{
        IObjectDAO dao = FactoryDAO.getFactory("Departamento");
        Departamento departamento = (Departamento) dao.read(dnumero);
        Localizacao localizacao = new Localizacao();
        localizacao.setDepartamento(departamento);
        localizacao.setNome(nome);
        IObjectDAO localizacaoDAO = FactoryDAO.getFactory("Localizacao");
        localizacaoDAO.post(departamento);
        }


    }


    public void update(int dnumero, String nome) throws Exception {
        FuncoesControle f = new FuncoesControle();
        if(f.verificarExistenciaDepartamento(dnumero) == false){
           throw new Exception("Erro: departamento informado nao foi encontrado"); 
        }else{
        IObjectDAO dao = FactoryDAO.getFactory("Departamento");
        Departamento departamento = (Departamento) dao.read(dnumero);
        Localizacao localizacao = new Localizacao();
        localizacao.setDepartamento(departamento);
        localizacao.setNome(nome);
        IObjectDAO localizacaoDAO = FactoryDAO.getFactory("Localizacao");
        localizacaoDAO.update(departamento);
        }
    }
    
    
        public Vector<Localizacao>  getAll()
    {
        IObjectDAO empregadoDAO = FactoryDAO.getFactory("Localizacao");
        ArrayList<Object> localizacaoObject = (ArrayList<Object>) empregadoDAO.getAll();
        Vector<Localizacao> localizacao = new Vector<Localizacao>();
        
        for(int i = 0 ; i < localizacaoObject.size() ; i++)
        {
            Localizacao l = (Localizacao) localizacaoObject.get(i);
            localizacao.add(l);
        }
        
        return localizacao;
    } 
        
        public void delete(String localizacao) throws Exception{
            FuncoesControle f = new FuncoesControle();
            if(f.verificarExistenciaLocalizacao(localizacao) == false){
                throw new Exception("Erro: localizacao informado nao foi encontrado"); 
            } else{
                IObjectDAO localizacaoDAO = FactoryDAO.getFactory("Localizacao");
                localizacaoDAO.delete(localizacao);
            }
        }


    
            public Vector<Localizacao>  SearchByName(String input)
    {
        IObjectDAO empregadoDAO = FactoryDAO.getFactory("Localizacao");
        ArrayList<Object> localizacaoObject = (ArrayList<Object>) empregadoDAO.read(input);
        Vector<Localizacao> localizacao = new Vector<Localizacao>();
        
        for(int i = 0 ; i < localizacaoObject.size() ; i++)
        {
            Localizacao l = (Localizacao) localizacaoObject.get(i);
            localizacao.add(l);
        }
        
        return localizacao;
    } 
    
    
}
