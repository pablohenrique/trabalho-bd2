/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import control.FuncoesControle;
import java.sql.Date;

/**
 *
 * @author pablohenrique
 */
public class Empregado {
    private String ssn;
    private String nome;
    private String endereco;
    private Empregado superssn;
    private String senha;
    private String sexo;
    private float salario;
    private Date dataNascimento;
    private Departamento departamento;

    /**
     * @return the ssn
     */
    public String getSsn() {
        return ssn;
    }

    /**
     * @param ssn the ssn to set
     */
    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the endereco
     */
    public String getEndereco() {
        return endereco;
    }

    /**
     * @param endereco the endereco to set
     */
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    /**
     * @return the superssn
     */
    public Empregado getSuperSsn() {
        return superssn;
    }

    /**
     * @param superssn the superssn to set
     */
    public void setSuperSsn(Empregado superssn) {
        this.superssn = superssn;
    }
    
    /**
     * @return the senha
     */
    public String getSenha() {
        return senha;
    }
    
    /**
     * @param senha the senha to set
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * @return the sexo
     */
    public String getSexo() {       
        return sexo;
    }
    
    /**
     * @param sexo the sexo to set
     */
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    /**
     * @return the salario
     */
    public float getSalario() {
        return salario;
    }
    
    public String getSalarioString(){
        return String.format("%.2f", salario);
    }
    
    public void setSalario(String s) {
        salario = Float.parseFloat(s);
    }    

    /**
     * @param salario the salario to set
     */
    public void setSalario(float salario) {
        this.salario = salario;
    }

    /**
     * @return the departamento
     */
    public Departamento getDepartamento() {
        return departamento;
    }

    /**
     * @param departamento the departamento to set
     */
    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    /**
     * @return the dataNascimento
     */
    public Date getDataNascimento() {
        return dataNascimento;
    }
    
    public String getDataNascimentoString(){
        return FuncoesControle.converteData(dataNascimento);
    }
    
    public void setDataNascimento(String d){
        dataNascimento = FuncoesControle.coverteStringData(d);
    }

    /**
     * @param dataNascimento the dataNascimento to set
     */
    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    
    public String toString()
    {
        return nome;
    }
}
