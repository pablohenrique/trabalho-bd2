/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import Model.Departamento;
import Model.Dependente;
import Model.Dept_Localizacoes;
import Model.Empregado;
import Model.Projeto;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author yuricampos
 */
public class ControlFacade {
    private DepartamentoControl departamentoControl = new DepartamentoControl();
    private DependenteControl dependenteControl = new DependenteControl();
    private Dept_LocalizacoesControl dept_LocalizacoesControl = new Dept_LocalizacoesControl();
    private EmpregadoControl empregadoControl = new EmpregadoControl();
    private ProjetoControl projetoControl = new ProjetoControl();
    private TrabalhaEmControl trabalhaEmControl = new TrabalhaEmControl();
    
    /**
     * Funcoes de funcionario
     */
    
    /**
     * Funcao realiza login de usuario
     * @param usuario
     * @param senha
     * @return -1 usuario ou senha incorretos
     * @return 0 usuario normal
     * @return 1 funcionario supervisor
     * @return 2 funcionario gerente
     * @return 3 funcionario supervisor e gerente
     */
    public int login(String usuario, String senha)
    {
        return empregadoControl.login(usuario, senha);
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
    public void inserirEmpregado(String ssn, String nome, String sexo, String endereco, String salario, String datanasc, int dno, String superssn, String senha) throws Exception{
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
     * Funcao que retorna todos empregados
     * @return ArrayList<Empregado>
     * Retorna todos os empregados do banco
     */
    public ArrayList<Empregado> getTodosEmpregados()
    {
       return empregadoControl.getAll();
    }
        
    /**
     * Funcao que retorna todos empregados
     * @return Vector<Empregado>
     * Retorna todos os empregados do banco
     */    
    public Vector<Empregado> listarEmpregados()
    {
       return empregadoControl.listarEmpregados();
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
    public ArrayList<Departamento> getTodosDepartamento()
    {        
        return departamentoControl.getAll();
    }

    public Vector<Departamento> listarDepartamentos()
    {        
        return departamentoControl.listarDepartamentos();
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
    
     /**
     * Funcoes de Projeto
     */
    
    /**
     * 
     * @param numero
     * @param nome
     * @param localizacao
     * @param departamento
     * @throws Exception 
     * Faz a insercao de um projeto
     */
    public void inserirProjeto(int numero, String nome, String localizacao, int departamento) throws Exception{
        projetoControl.post(numero, nome, localizacao, departamento);
    }
    
    /**
     * 
     * @param numero
     * @param nome
     * @param localizacao
     * @param departamento
     * @throws Exception 
     * Faz a atualizacao de um projeto
     */
    public void atualizarProjeto(int numero, String nome, String localizacao, int departamento) throws Exception{
        projetoControl.update(numero, nome, localizacao, departamento);
    }
    
    /**
     * 
     * @param numero
     * @return Projeto
     * retorna um projeto de acordo com seu numero
     */
    public Projeto getProjetoByNumero(int numero){
        Projeto projeto = projetoControl.getById(numero);
        return projeto;
    }
    
    /**
     * 
     * @return ArrayList<Projeto>
     * retorna todos os projetos
     */
    public ArrayList<Projeto> getTodosProjetos(){
        ArrayList<Projeto> projetos = projetoControl.getAll();
        return projetos;
    }
    
    /**
     * 
     * @param nome
     * @return Projeto
     * retorna projeto que possui exatamente o nome informado
     */
     public Projeto buscaExataNomeProjeto(String nome){
         Projeto projeto = projetoControl.SearchByNameExactly(nome);
         return projeto;
     }
     
     /**
     * Funcoes de trabalha_em
     */
     
     
     /**
      * 
      * @param ssn
      * @param projetonumero
      * @param horas
      * @throws Exception 
      * Faz insercao em trabalha em
      */
     public void inserirTrabalhaEm(String ssn, int projetonumero, float horas) throws Exception{
         trabalhaEmControl.post(ssn, projetonumero, horas);
     }
     
     /**
      * 
      * @param ssn
      * @param projetonumero
      * @param horas
      * @throws Exception 
      * Faz atualizacao em trabalha_em
      */
     public void atualizarTrabalhaEm(String ssn, int projetonumero, float horas) throws Exception{
         trabalhaEmControl.update(ssn, projetonumero, horas);
     }
     
     
    
    
    
     /**
     * Funcoes de Dept_Localizacao
     */
    
    /**
     * 
     * @param departamento
     * @param nome
     * @throws Exception 
     * Faz a insercao em um dept_localizacao
     */
    public void inserirDeptLocalizacoes(int departamento, String nome) throws Exception{
        dept_LocalizacoesControl.post(departamento, nome);
    }
    
    /**
     * 
     * @param departamento
     * @param nome
     * @throws Exception 
     * atualiza um dept_localizacao
     */
    public void atualizarDeptLocalizacoes(int departamento, String nome) throws Exception{
        dept_LocalizacoesControl.update(departamento, nome);
    }
    
    /**
     * 
     * @return ArrayList<Dept_Localizacoes>
     * Retorna todos os dept_localizacao
     */
    public ArrayList<Dept_Localizacoes> getTodosDeptLocalizacoes() {
        ArrayList<Dept_Localizacoes> dept_localizacoes = dept_LocalizacoesControl.getAll();
        return dept_localizacoes;
    }
    
    /**
     * 
     * @param nome
     * @return deptlocalizacao
     * Retorna um deptlocalizacao de acordo com nome da localizacao
     */
    public Dept_Localizacoes BuscaExataNomeLocalizacao(String nome){
        Dept_Localizacoes deptlocalizacoes = dept_LocalizacoesControl.SearchByNameExactly(nome);
        return deptlocalizacoes;
    }         
}
