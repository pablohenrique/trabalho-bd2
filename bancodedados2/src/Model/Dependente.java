/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Date;
/**
 *
 * @author pablohenrique
 */
public class Dependente {
    private String essn;
    private String nome;
    private String parentesco;
    private char sexo;
    private Date datanascimento;

    /**
     * @return the essn
     */
    public String getEssn() {
        return essn;
    }

    /**
     * @param essn the essn to set
     */
    public void setEssn(String essn) {
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
    public char getSexo() {
        return sexo;
    }

    /**
     * @param sexo the sexo to set
     */
    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    /**
     * @return the datanascimento
     */
    public Date getDatanascimento() {
        return datanascimento;
    }

    /**
     * @param datanascimento the datanascimento to set
     */
    public void setDatanascimento(Date datanascimento) {
        this.datanascimento = datanascimento;
    }
}
