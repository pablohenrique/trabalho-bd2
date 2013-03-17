/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.sql.Date;

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
    
    
    
    
    
  
    
    
    
    
}
