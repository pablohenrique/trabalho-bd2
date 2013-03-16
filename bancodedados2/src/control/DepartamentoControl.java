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


    public void post(int numero, String nome, String gerssn, Date gerdatainicio) {
        Departamento departamento = new Departamento();
        departamento.setNumero(numero);
        departamento.setNome(nome);
        departamento.setGerenteSsn(gerssn);
        departamento.setGerenteDataInicio(gerdatainicio);
        DepartamentoDAO departamentoDAO = new DepartamentoDAO();
        departamentoDAO.post(departamento);
    }


    public void update(int numero, String nome, String gerssn, Date gerdatainicio) {
        Departamento departamento = new Departamento();
        departamento.setNumero(numero);
        departamento.setNome(nome);
        departamento.setGerenteSsn(gerssn);
        departamento.setGerenteDataInicio(gerdatainicio);
        DepartamentoDAO departamentoDAO = new DepartamentoDAO();
        departamentoDAO.update(departamento);

        
    }


    public Object getById(int numero) {
        DepartamentoDAO departamentoDAO = new DepartamentoDAO();
        Departamento departamento = (Departamento) departamentoDAO.get(numero);
        return departamento;
    }


    public ArrayList<Object> getAll() {
         DepartamentoDAO departamentoDAO = new DepartamentoDAO();
         ArrayList<Object> departamento = departamentoDAO.getAll();
         return departamento;
    }


    public Object SearchByNameExactly(String input) {
       DepartamentoDAO departamentoDAO = new DepartamentoDAO();
       Departamento departamento = (Departamento) departamentoDAO.read(input);
       return departamento;
    }

    
}
