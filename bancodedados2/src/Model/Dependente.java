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
public class Dependente {
    private String nome;
    private String parentesco;
    private String sexo;
    private Date datanascimento;
    private Empregado essn;

    /**
     * @return the essn
     */
    public Empregado getEssn() {
        return essn;
    }

    /**
     * @param essn the essn to set
     */
    public void setEssn(Empregado essn) {
        this.essn = essn;
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
     * @return the parentesco
     */
    public String getParentesco() {
        return parentesco;
    }

    /**
     * @param parentesco the parentesco to set
     */
    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
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
     * @return the datanascimento
     */
    public Date getDataNascimento() {
        return datanascimento;
    }

    public String getDataNascimentoString(){
        return FuncoesControle.converteData(datanascimento);
    }
    
    /**
     * @param datanascimento the datanascimento to set
     */
    public void setDataNascimento(Date datanascimento) {
        this.datanascimento = datanascimento;
    }

    public void setDataNascimento(String d) 
    {
        datanascimento = FuncoesControle.coverteStringData(d);
    }    


}
