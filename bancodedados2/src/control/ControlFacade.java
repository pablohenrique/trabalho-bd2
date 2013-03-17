/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import Model.Departamento;
import Model.Dependente;
import Model.Empregado;
import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author yuricampos
 */
public class ControlFacade {
    private DepartamentoControl departamentoControl;
    private DependenteControl dependenteControl;
    private Dept_LocalizacoesControl dept_LocalizacoesControl;
    private EmpregadoControl empregadoControl;
    private ProjetoControl projetoControl;
    private TrabalhaEmControl trabalhaEmControl;
    
    /**
     * Funcoes de funcionario
     */
    
    /**
     * 
     * @param usuario
     * @param senha
     * Funcao realiza login de usuario
     * @return -1 usuario ou senha incorretos
     * @return 0 usuario normal
     * @return 1 funcionario supervisor
     * @return 2 funcionario gerente
     * @return 3 funcionario supervisor e gerente
     */
    public int login(String usuario, String senha){
        int retorno = empregadoControl.login(usuario, senha);
        return retorno;
    }
    
    /**
     * 
     * @param ssn
     * @param nome
     * @param sexo
     * @param endereco
     * @param salario
     * @param datanasc
     * @param dno
     * @param superssn
     * @param senha
     * @throws Exception 
     * Funcao faz a insercao de um usuario
     */
    public void inserirEmpregado(String ssn, String nome, String sexo, String endereco, float salario, Date datanasc, int dno, String superssn, String senha) throws Exception{
        empregadoControl.post(ssn, nome, sexo, endereco, salario, datanasc, dno, superssn, senha);
    }
    
    /**
     * 
     * @param ssn
     * @param nome
     * @param sexo
     * @param endereco
     * @param salario
     * @param datanasc
     * @param dno
     * @param superssn
     * @param senha
     * @throws Exception 
     * Funcao atualiza os dados de um um empregado
     */
    public void atualizarEmpregado(String ssn, String nome, String sexo, String endereco, float salario, Date datanasc, int dno, String superssn, String senha) throws Exception{
        empregadoControl.update(ssn, nome, sexo, endereco, salario, datanasc, dno, superssn, senha);
    }
    
    /**
     * 
     * @param ssn
     * @return Empregado
     * Retorna um empregado usando seu ssn
     */
    public Empregado getEmpregadoBySsn(String ssn){
       Empregado empregado =  empregadoControl.getById(ssn);
        return empregado;
    }
    
    /**
     * 
     * @return ArrayList<Empregado>
     * Retorna todos os empregados do banco
     */
    public ArrayList<Empregado> getTodosEmpregados() {
       ArrayList<Empregado> empregados = empregadoControl.getAll();
       return empregados;
    }
    
    /**
     * 
     * @param nome
     * @return Empregado
     * retorna um empregado que possui exatamente o nome informado
     */
    public Empregado buscaExataNomeEmpregado(String nome){
        Empregado empregado = empregadoControl.SearchByNameExactly(nome);
        return empregado;
    }
    
     /**
     * Funcoes de departamento
     */
    
    /**
     * 
     * @param numero
     * @param nome
     * @param gerssn
     * @param gerdatainicio
     * @throws Exception 
     * Realiza insercao no departamento
     */
    public void inserirDepartamento(int numero, String nome, String gerssn, Date gerdatainicio) throws Exception{
        departamentoControl.post(numero, nome, gerssn, gerdatainicio);
    }
    
    /**
     * 
     * @param numero
     * @param nome
     * @param gerssn
     * @param gerdatainicio
     * @throws Exception 
     * Faz atualizacao em departamento
     */
    public void atualizarDepartamento(int numero, String nome, String gerssn, Date gerdatainicio) throws Exception{
        departamentoControl.update(numero, nome, gerssn, gerdatainicio);
    }
    
    /**
     * 
     * @param numero
     * @return Departamento
     * Retorna um departamento usando o numero de departamento
     */
    public Departamento getDepartamentoByNumero(int numero){
        Departamento departamento = departamentoControl.getById(numero);
        return departamento;
    }
    
    /**
     * 
     * @return ArrayList<Departamento>
     * retorna todos os departamentos cadastrados
     */
    public ArrayList<Departamento> getTodosDepartamento(){ 
        ArrayList<Departamento> departamentos = departamentoControl.getAll();
        return departamentos;   
    }
    
    
    /**
     * 
     * @param nome
     * @return departamento
     * retorna o departamento que possui exatamente o nome informado
     */
    public Departamento buscaExataNomeDepartamento(String nome){
        Departamento departamento = departamentoControl.SearchByNameExactly(nome);
        return departamento;
    }
    
     /**
     * Funcoes de dependente
     */
    
    /**
     * 
     * @param nome
     * @param essn
     * @param sexo
     * @param datanascimento
     * @param parentesco
     * @throws Exception 
     * Faz a insercao de um dependente
     */
    public void inserirDependente(String nome, String essn, String sexo, Date datanascimento, String parentesco) throws Exception{
        dependenteControl.post(nome, essn, sexo, datanascimento, parentesco);
    }
    
    /**
     * 
     * @param nome
     * @param essn
     * @param sexo
     * @param datanascimento
     * @param parentesco
     * @throws Exception 
     * Faz a atualizacao de um dependente
     */
    public void atualizarDependente(String nome, String essn, String sexo, Date datanascimento, String parentesco) throws Exception{
        dependenteControl.update(nome, essn, sexo, datanascimento, parentesco);
    }
    
    /**
     * 
     * @return ArrayList<Dependente>
     * retorna todos os dependentes cadastrados
     */
    public ArrayList<Dependente> getTodosDependentes(){
        ArrayList<Dependente> dependentes = dependenteControl.getAll();
        return dependentes;
    }
    
    /**
     * 
     * @param nome
     * @return Dependente
     * Retorna dependente que possui exatamente o nome informado
     */
    public Dependente buscaExataNomeDependente(String nome){
        Dependente dependente = dependenteControl.SearchByNameExactly(nome);
        return dependente;
    }
    
    
    
    
    
    
    
    
  
    
    
    
    
}
