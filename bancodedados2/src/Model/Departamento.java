/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Date;

/**
 *
 * @author pablohenrique
 */
public class Departamento {
    private String nome;
    private String gerenteSsn;
    private int numero;
    private Date gerenteDataInicio;

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
     * @return the gerenteSsn
     */
    public String getGerenteSsn() {
        return gerenteSsn;
    }

    /**
     * @param gerenteSsn the gerenteSsn to set
     */
    public void setGerenteSsn(String gerenteSsn) {
        this.gerenteSsn = gerenteSsn;
    }

    /**
     * @return the numero
     */
    public int getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }

    /**
     * @return the gerenteDataInicio
     */
    public Date getGerenteDataInicio() {
        return gerenteDataInicio;
    }

    /**
     * @param gerenteDataInicio the gerenteDataInicio to set
     */
    public void setGerenteDataInicio(Date gerenteDataInicio) {
        this.gerenteDataInicio = gerenteDataInicio;
    }
    
    public String toString()
    {
        return this.nome;
    }
}
