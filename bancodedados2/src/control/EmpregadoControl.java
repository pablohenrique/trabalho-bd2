/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import DAO.EmpregadoDAO;
import DAO.FactoryDAO;
import Model.Departamento;
import Model.Empregado;
import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author yuricampos
 */
public class EmpregadoControl {

    private EmpregadoDAO dao;

    public EmpregadoControl() {
        this.dao = (EmpregadoDAO) FactoryDAO.getFactory("Empregado");
    }

    private Empregado createObject(String ssn, String nome, String sexo, String endereco, String salario, String datanasc, int dno, String superssn, String senha) {
        Empregado gerente = new Empregado();
        FuncoesControle f = new FuncoesControle();
        gerente.setSsn(superssn);

        Departamento dep = new Departamento();
        dep.setNumero(dno);

        Empregado empregado = new Empregado();
        empregado.setSsn(ssn.trim());
        empregado.setNome(nome);
        empregado.setSexo(sexo);
        empregado.setEndereco(endereco);
        empregado.setSalario(salario);
        empregado.setDataNascimento(f.coverteStringData(datanasc));
        empregado.setDepartamento(dep);
        empregado.setSuperSsn(gerente);
        empregado.setSenha(senha);

        return empregado;
    }

    public void post(String ssn, String nome, String sexo, String endereco, String salario, String datanasc, int dno, String superssn, String senha) throws Exception {
        this.dao.post(this.createObject(ssn, nome, sexo, endereco, salario, datanasc, dno, superssn, senha));
    }

    public void update(String ssn, String nome, String sexo, String endereco, String salario, String datanasc, int dno, String superssn, String senha) throws Exception {
        this.dao.update(this.createObject(ssn, nome, sexo, endereco, salario, datanasc, dno, superssn, senha));
    }

    public void delete(String input) throws Exception {
        this.dao.delete(input);
    }

    public Empregado getById(String input) throws Exception {
        return (Empregado) this.dao.get(input);
    }

    public Vector<Empregado> getByIdVector(String input) throws Exception {
        Vector<Empregado> empregados = new Vector<Empregado>();
        empregados.add((Empregado) this.dao.get(input));
        return empregados;

    }

    public Vector<Empregado> getAll() throws Exception {
        Vector<Empregado> empregados = new Vector<Empregado>();

        for (Object aux : this.dao.getAll()) 
            empregados.add((Empregado) aux);
        

        return empregados;
    }

    public Vector<Empregado> SearchByName(String input) {
        Vector<Empregado> empregados = new Vector<Empregado>();

        for (Object aux : (ArrayList<Object>) this.dao.read(input)) 
            empregados.add((Empregado) aux);
        

        return empregados;
    }

    public Vector<Empregado> SearchBySuperSnn(String superssn) {
        Vector<Empregado> empregados = new Vector<Empregado>();

        for (Object aux : (ArrayList<Object>) this.dao.buscarSupervisor(superssn)) 
            empregados.add((Empregado) aux);
        

        return empregados;
    }

    public int login(String usuario, String senha) {
        return this.dao.acessar(usuario, senha);
    }

    public Vector<Empregado> buscarEndereco(String endereco) {
        Vector<Empregado> empregados = new Vector<Empregado>();
        for (Object aux : (ArrayList<Object>) this.dao.buscarEmpregadoEndereco(endereco)) 
            empregados.add((Empregado) aux);
        

        return empregados;

    }

    public Vector<Empregado> buscarEmpregadoSexo(String sexo) {
        Vector<Empregado> empregados = new Vector<Empregado>();

        for (Object aux : (ArrayList<Object>) this.dao.buscarEmpregadoSexo(sexo)) 
            empregados.add((Empregado) aux);
        

        return empregados;

    }

    public Vector<Empregado> buscarEmpregadoProjeto(int numero) {
        Vector<Empregado> empregados = new Vector<Empregado>();

        for (Object aux : (ArrayList<Object>) this.dao.buscarEmpregadoProjeto(numero))
            empregados.add((Empregado) aux);
        

        return empregados;



    }
}