/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import control.FuncoesControle;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author pablohenrique
 */
public class Departamento {
    private String nome;
    private int numero;
    private Date gerenteDataInicio;
    private Empregado gerente;

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
        return this.gerenteDataInicio;
    } 

    /**
     * @param gerenteDataInicio the gerenteDataInicio to set
     */
    public void setGerenteDataInicio(Date gerenteDataInicio) {
        this.gerenteDataInicio = gerenteDataInicio;
    }  
    
    /**
     * @return the gerente
     */
    public Empregado getGerenteSsn() {
        return gerente;
    }

    /**
     * @param gerente the gerent to set
     */
    public void setGerenteSsn(Empregado gerente) {
        this.gerente = gerente;
    }
    
    /**
     * @return the nome
     */
    @Override
    public String toString() {
        return this.nome;
    }
}
