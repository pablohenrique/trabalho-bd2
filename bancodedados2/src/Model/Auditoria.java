/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Date;

/**
 *
 * @author yuricampos
 */
public class Auditoria {
    private Empregado gerssn;
    private Empregado essn;
    private float antigosalario;
    private float novosalario;
    private Date datamodificacao;

    /**
     * @return the gerssn
     */
    public Empregado getGerssn() {
        return gerssn;
    }

    /**
     * @param gerssn the gerssn to set
     */
    public void setGerssn(Empregado gerssn) {
        this.gerssn = gerssn;
    }

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
     * @return the antigosalario
     */
    public float getAntigosalario() {
        return antigosalario;
    }

    /**
     * @param antigosalario the antigosalario to set
     */
    public void setAntigosalario(float antigosalario) {
        this.antigosalario = antigosalario;
    }

    /**
     * @return the novosalario
     */
    public float getNovosalario() {
        return novosalario;
    }

    /**
     * @param novosalario the novosalario to set
     */
    public void setNovosalario(float novosalario) {
        this.novosalario = novosalario;
    }

    /**
     * @return the datamodificacao
     */
    public Date getDatamodificacao() {
        return datamodificacao;
    }

    /**
     * @param datamodificacao the datamodificacao to set
     */
    public void setDatamodificacao(Date datamodificacao) {
        this.datamodificacao = datamodificacao;
    }
    
    
    
}
