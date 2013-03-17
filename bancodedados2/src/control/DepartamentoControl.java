/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import DAO.DepartamentoDAO;
import Model.Departamento;
import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author yuricampos
 */
public class DepartamentoControl {


    public void post(int numero, String nome, String gerssn, Date gerdatainicio) throws Exception {
             FuncoesControle f = new FuncoesControle();
     if(f.verificarExistenciaEmpregado(gerssn) == false){
         throw new Exception("Erro: empregado informado nao foi encontrado");
     } else{
        Departamento departamento = new Departamento();
        departamento.setNumero(numero);
        departamento.setNome(nome);
        departamento.setGerenteSsn(gerssn);
        departamento.setGerenteDataInicio(gerdatainicio);
        DepartamentoDAO departamentoDAO = new DepartamentoDAO();
        departamentoDAO.post(departamento);
     }
    }


    public void update(int numero, String nome, String gerssn, Date gerdatainicio) throws Exception {
                     FuncoesControle f = new FuncoesControle();
     if(f.verificarExistenciaEmpregado(gerssn) == false){
         throw new Exception("Erro: empregado informado nao foi encontrado");
     } else{
        Departamento departamento = new Departamento();
        departamento.setNumero(numero);
        departamento.setNome(nome);
        departamento.setGerenteSsn(gerssn);
        departamento.setGerenteDataInicio(gerdatainicio);
        DepartamentoDAO departamentoDAO = new DepartamentoDAO();
        departamentoDAO.update(departamento);
     }

        
    }


    public Departamento getById(int numero) {
        DepartamentoDAO departamentoDAO = new DepartamentoDAO();
        Departamento departamento = (Departamento) departamentoDAO.get(numero);
        return departamento;
    }


    public ArrayList<Departamento> getAll() {
         DepartamentoDAO departamentoDAO = new DepartamentoDAO();
         ArrayList<Object> departamentoObject = departamentoDAO.getAll();
         ArrayList<Departamento> departamento = null;
         for(int i = 0 ; i < departamentoObject.size() ; i++){
             Departamento d = (Departamento) departamentoObject.get(i);
             departamento.add(d);
         }
         return departamento;
    }


    public Departamento SearchByNameExactly(String input) {
       DepartamentoDAO departamentoDAO = new DepartamentoDAO();
       Departamento departamento = (Departamento) departamentoDAO.read(input);
       return departamento;
    }

    
}
