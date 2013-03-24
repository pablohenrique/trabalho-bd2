/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import Model.Departamento;
import Model.Dependente;
import Model.Localizacao;
import Model.Empregado;
import Model.Projeto;
import Model.Trabalha;
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
    private Localizacoes localizacoesControl = new Localizacoes();
    private EmpregadoControl empregadoControl = new EmpregadoControl();
    private ProjetoControl projetoControl = new ProjetoControl();
    private TrabalhaControl trabalhaControl = new TrabalhaControl();
    
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
    public void atualizarEmpregado(String ssn, String nome, String sexo, String endereco, String salario, String datanasc, int dno, String superssn, String senha) throws Exception{
        empregadoControl.update(ssn, nome, sexo, endereco, salario, datanasc, dno, superssn, senha);
    }
    
    /**
     * 
     * @param ssn
     * @return Empregado
     * Retorna um empregado usando seu ssn
     */
    public Empregado getEmpregadoBySsn(String ssn) throws Exception {
        return empregadoControl.getById(ssn);
    }
    
        
    /**
     * Funcao que retorna todos empregados
     * @return Vector<Empregado>
     * Retorna todos os empregados do banco
     */    
    public Vector<Empregado> listarEmpregados()
    {
       return empregadoControl.getAll();
    }    
    
    /**
     * 
     * @param ssn 
     * Apaga empregado com ssn informado
     */
    public void apagarEmpregado(String ssn) throws Exception{
        empregadoControl.delete(ssn);
    }    
    
    public String[][] getEmpregadosTable(Vector<Empregado> list)
    {
        String[][] dados = new String[list.size()][];  
        
        for(int i=0; i<list.size(); i++)
        {
            String dno = String.valueOf(list.get(i).getDepartamento().getNumero());
            String departamentoNome = list.get(i).getDepartamento().getNome();
            String supervisorNome = list.get(i).getSuperSsn().getNome();
            String sssn = list.get(i).getSuperSsn().getSsn();            
            
            dados[i] = new String[] {list.get(i).getNome(), list.get(i).getSsn(), list.get(i).getSexo(),
                                     list.get(i).getEndereco(), list.get(i).getSalarioString(), list.get(i).getDataNascimentoString(),
                                     departamentoNome,dno,supervisorNome,sssn}; 
        }
        
        return dados; 
    }
    
    
    /**
     * 
     * @param nome
     * @return Vector<Empregado>
     * retorna vetores de empregados que possuem nome informado (LIKE)
     */
    public Vector<Empregado> buscaNomeEmpregado(String nome){
        return empregadoControl.SearchByName(nome);
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
    public Departamento getDepartamentoByNumero(int numero) throws Exception{
        Departamento departamento = departamentoControl.getById(numero);
        return departamento;
    }
    
    /**
     * 
     * @return Vector<Departamento>
     * retorna todos os departamentos cadastrados
     */
    public Vector<Departamento> listarDepartamentos()
    {        
        return departamentoControl.getAll();
    }        
    
    /**
     * 
     * @param nome
     * @return departamento
     * retorna departamentos que possuem nome (LIKE)
     */
    public Vector<Departamento> buscaNomeDepartamento(String nome) throws Exception{
        return departamentoControl.SearchByName(nome);
    }
    
    /**
     * 
     * @param numero
     * @throws Exception 
     * apaga departamento com id informado
     */
    public void apagarDepartamento(int numero) throws Exception{
        departamentoControl.delete(numero);
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
     * @return Vector<Dependente>
     * retorna todos os dependentes cadastrados
     */
    public Vector<Dependente> listarDependentes(){
        return dependenteControl.getAll();
    }
    
    /**
     * 
     * @param nome
     * @return Vector<Dependente>
     * Retorna dependente que possui o nome informado (LIKE)
     */
    public Vector<Dependente> buscaNomeDependente(String nome) throws Exception{
        return dependenteControl.SearchByName(nome);
    }
    

    /**
     * 
     * @param nome
     * @throws Exception 
     * apaga dependente com nome informado
     */
    public void apagarDependente(String nome) throws Exception{
        dependenteControl.delete(nome);
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
    public Projeto getProjetoByNumero(int numero) throws Exception{
        Projeto projeto = projetoControl.getById(numero);
        return projeto;
    }
    
    /**
     * 
     * @return Vector<Projeto>
     * retorna todos os projetos
     */
    public Vector<Projeto> listarProjetos(){
        return projetoControl.getAll();
    }
    
    /**
     * 
     * @param nome
     * @return Vector<Projeto>
     * retorna projeto que possui  o nome informado (Like)
     */
     public Vector<Projeto> buscaNomeProjeto(String nome) throws Exception{
         return projetoControl.SearchByName(nome);
     }
     
     /**
      * 
      * @param numero
      * @throws Exception 
      * apaga projeto com numero informado
      */
     public void apagarProjeto(int numero) throws Exception{
         projetoControl.delete(numero);
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
    public void inserirLocalizacoes(int departamento, String nome) throws Exception{
        localizacoesControl.post(departamento, nome);
    }
    
    /**
     * 
     * @param departamento
     * @param nome
     * @throws Exception 
     * atualiza um dept_localizacao
     */
    public void atualizarLocalizacoes(int departamento, String nome) throws Exception{
        localizacoesControl.update(departamento, nome);
    }
    
    /**
     * 
     * @return Vector<Localizacao>
     * Retorna todos os dept_localizacao
     */
    public Vector<Localizacao> listarLocalizacoes() {
        return localizacoesControl.getAll();
    }
    
    /**
     * 
     * @param nome
     * @return deptlocalizacao
     * Retorna um deptlocalizacao de acordo com nome da localizacao
     */
    public Vector<Localizacao> BuscaNomeLocalizacao(String nome){
        return localizacoesControl.SearchByName(nome);
    }         
    
   /**
    * 
    * @param localizacao
    * @throws Exception 
    * deleta localizacao informada
    */
    public void apagarLocalizacao(String localizacao) throws Exception{
        localizacoesControl.delete(localizacao);
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
     public void inserirTrabalha(String ssn, int projetonumero, float horas) throws Exception{
         trabalhaControl.post(ssn, projetonumero, horas);
     }
     
     /**
      * 
      * @param ssn
      * @param projetonumero
      * @param horas
      * @throws Exception 
      * Faz atualizacao em trabalha_em
      */
     public void atualizarTrabalha(String ssn, int projetonumero, float horas) throws Exception{
         trabalhaControl.update(ssn, projetonumero, horas);
     }
     
     /**
      * 
      * @return Vector<Trabalha>
      * Retorna todos os trabalha_em
      */
     public Vector<Trabalha> listarTrabalha(){
         return trabalhaControl.getAll();
     }
     
     /**
      * 
      * @param ssn 
      * apaga trabalhaem com ssn informado
      */
     public void deletaTrabalha(String ssn) throws Exception{
         trabalhaControl.delete(ssn);
     }
}