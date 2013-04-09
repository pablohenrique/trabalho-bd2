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
import javax.swing.ComboBoxModel;

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
    public Vector<Empregado> listarEmpregados() throws Exception
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
    
    public String[][] getDependentesTable(Vector<Dependente> list) {
        String[][] dados = new String[list.size()][];  
        
        for(int i=0; i<list.size(); i++)
        {
            Empregado empregado = list.get(i).getEssn();
            
            dados[i] = new String[] {list.get(i).getNome(), list.get(i).getSexo(),list.get(i).getDataNascimentoString(), list.get(i).getParentesco(), empregado.getNome(), empregado.getSsn(), empregado.getDepartamento().getNome() }; 
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
     * 
     * @param superssn
     * @return Vector<Empregado>
     * retorna empregados de um superssn informado
     */     
    public Vector<Empregado> buscaSuperSnn(String superssn) {
        return empregadoControl.SearchBySuperSnn(superssn);
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
    public void inserirDepartamento(String nome, String gerssn, String gerdatainicio) throws Exception{
        departamentoControl.post(nome, gerssn, gerdatainicio);
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
    public void atualizarDepartamento(int numero, String nome, String gerssn, String gerdatainicio) throws Exception{
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
     * @param numero
     * @return Vector<Departamento>
     * @throws Exception 
     * retorna departamento com número informado em um vetor
     */
    public Vector<Departamento> getDepartamentoByNumeroVector(int numero) throws Exception{
        Vector<Departamento> departamento = departamentoControl.getByIdVector(numero);
        return departamento;
    }
    
    /**
     * 
     * @return Vector<Departamento>
     * retorna todos os departamentos cadastrados
     */
    public Vector<Departamento> listarDepartamentos() throws Exception
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
     * 
     * @param gerssn
     * @return Departamento
     * @throws Exception 
     * retorna departamento que possui gerente com ssn informado
     */
    public Vector<Departamento> getDepartamentoByGerente(String gerssn) throws Exception{
        return departamentoControl.getByGer(gerssn);
    }
      
    public String[][] getDepartamentosTable(Vector<Departamento> list) {
        String[][] dados = new String[list.size()][];  
        
        for(int i=0; i<list.size(); i++){
            Departamento dep = list.get(i);
            Empregado emp = list.get(i).getGerenteSsn();
            
            dados[i] = new String[] {dep.getNome(), Integer.toString(dep.getNumero()), emp.getNome(), dep.getGerenteDataInicioString(), emp.getSsn(),
                                     emp.getSexo(), emp.getEndereco(), emp.getSalarioString(), emp.getDataNascimentoString(), emp.getDepartamento().getNome(), Integer.toString(emp.getDepartamento().getNumero()), emp.getSuperSsn().getNome(), emp.getSuperSsn().getSsn()}; 
        }
        
        return dados;         
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
    public void inserirDependente(String nome, String essn, String sexo, String datanascimento, String parentesco) throws Exception{
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
    public void atualizarDependente(String nome, String essn, String sexo, String datanascimento, String parentesco) throws Exception{
        dependenteControl.update(nome, essn, sexo, datanascimento, parentesco);
    }
    
    /**
     * 
     * @return Vector<Dependente>
     * retorna todos os dependentes cadastrados
     */
    public Vector<Dependente> listarDependentes() throws Exception{
        return dependenteControl.getAll();
    }
    
    /**
     * 
     * @param nome
     * @return Vector<Dependente>
     * Retorna dependente que possui o essn informado
     */
    public Vector<Dependente> DependenteBuscaByEssn(String essn) throws Exception {
        return dependenteControl.SearchByEssn(essn);
    }
    
        
    /**
     * 
     * @param essn
     * @param nome
     * @return
     * @throws Exception 
     * retorna dependente com essn e nome informado
     */
    public Dependente buscaDependenteEssnNome(String essn, String nome) throws Exception{
        return dependenteControl.SearchByEssnNome(essn,nome);
    }
    
    /**
     * 
     * @param essn
     * @param nome
     * @return Vector<Dependente> 
     * retorna dependente com ssn e nome informado em vetor
     */
    public Vector<Dependente> buscaDependenteEssnNomeVector(String essn, String nome){
        return dependenteControl.SearchByEssnNomeVector(essn, nome);
    }
    

    /**
     * 
     * @param nome
     * @throws Exception 
     * apaga dependente com nome informado
     */
    public void apagarDependente(String essn, String nome) throws Exception{
        dependenteControl.delete(essn,nome);
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
    public void inserirProjeto(String nome, String localizacao, int departamento) throws Exception {
        projetoControl.post(nome, localizacao, departamento);
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
    public Projeto getProjetoByNumero(int numero) throws Exception {
        return projetoControl.getById(numero);
    }
    
    /**
     * 
     * @param numero
     * @return Vector<Projeto>
     * retorna projeto que possui numero informado em vetor
     */
    public Vector<Projeto> getProjetoByNumeroVector(int numero){
        return projetoControl.getByIdVector(numero);
    }
    
    
    /**
     * 
     * @return Vector<Projeto>
     * retorna todos os projetos
     */
    public Vector<Projeto> listarProjetos() throws Exception {
        return projetoControl.getAll();
    }
    
    /**
     * 
     * @param ssn
     * @return Vector<Projeto>
     * retorn todos os projetos de um ssn
     */
    public Vector<Projeto> listarProjetosByEmp(String ssn) throws Exception {
        return projetoControl.getAllByEmp(ssn);
    }

    /**
     * 
     * @param nomedepto
     * @return Vector<Projeto>
     * retorna todos os projetos de acordo com o nome do departamento 
     */
    public Vector<Projeto> listarProjetosByNomeDepto(String nomedepto) throws Exception {
        return projetoControl.getAllByDepNome(nomedepto);
    }

    /**
     * 
     * @param numero
     * @return Vector<Projeto>
     * retorna todos os projetos de acordo com o numero do departamento 
     */
    public Vector<Projeto> buscarProjetosByNumeroDepto(int numero) throws Exception {
        return projetoControl.getAllByDepNumero(numero);
    }

            
    public String[][] getProjetoBySsn(Vector<Projeto> list){     
        String[][] dados = new String[list.size()][];  
                        
        for(int i=0; i<list.size(); i++){                           
            dados[i] = new String[] {list.get(i).getNome(), Integer.toString(list.get(i).getNumero()), list.get(i).getLocalizacao(), 
                                     list.get(i).getDepartamento().getNome(), Integer.toString(list.get(i).getDepartamento().getNumero())}; 
        }

        return dados; 
    }    
    
    public String[][] getProjetoByDepartamentos(Vector<Projeto> list){
        String[][] dados = new String[list.size()][];  
                        
        for(int i=0; i<list.size(); i++){             
            Projeto p = list.get(i);
            dados[i] = new String[] {p.getNome(), String.valueOf(p.getNumero()), p.getLocalizacao()}; 
        }

        return dados; 
    }
    
    public String[][] getProjetosTable(Vector<Projeto> list) {                
        String[][] dados = new String[list.size()][];  
                        
        for(int i=0; i<list.size(); i++){             
            Projeto p = list.get(i);
            Departamento d = list.get(i).getDepartamento();
            Empregado e = d.getGerenteSsn();
            dados[i] = new String[] {p.getNome(), String.valueOf(p.getNumero()), p.getLocalizacao(), d.getNome(), String.valueOf(d.getNumero()), e.getNome(), e.getSsn()}; 
        }

        return dados;                 
    }    
    /**
     * 
     * @param nome
     * @return Vector<Projeto>
     * retorna projeto que possui  o nome informado (Like)
     */
     public Vector<Projeto> buscaProjetosByNome(String nome) throws Exception{
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
     * atualiza um dept_localizacao YURI REMOVE NAO EXISTE ESSA FUNCAO, COMO AGENTE TINHA CONVERSADO
     */
    public void atualizarLocalizacoes(int departamento, String nome) throws Exception{
        localizacoesControl.update(departamento, nome);
    }
    
    /**
     * 
     * @return Vector<Localizacao>
     * Retorna todos os dept_localizacao
     */
    public Vector<Localizacao> listarLocalizacoes() throws Exception { 
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
     * 
     * @param deptoid
     * @return
     * @throws Exception 
     * retorna todas as localizoes de um departamento
     */
    public Vector<Localizacao> listarLocalizacaoPorDep(int deptoid) throws Exception{
        return localizacoesControl.getAllByDep(deptoid);
    }

    
          
    public String[][] getLocalizacaoPorDep(Vector<Localizacao> list){     
        String[][] dados = new String[list.size()][];  
                        
        for(int i=0; i<list.size(); i++){                           
            Localizacao l = list.get(i);
            if(l != null)
                dados[i] = new String[] {l.getNome(), l.getDepartamento().getNome()}; 
        }

        return dados; 
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
     public Vector<Trabalha> listarTrabalha() throws Exception { 
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