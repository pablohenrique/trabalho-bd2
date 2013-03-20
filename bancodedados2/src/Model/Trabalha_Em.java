/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author yuricampos
 */
public class Trabalha_Em {
    private Empregado essn;
    private Projeto projeto;
    private float horas;

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
     * @return the projeto_numero
     */
    public Projeto getProjeto() {
        return projeto;
    }

    /**
     * @param projeto_numero the projeto_numero to set
     */
    public void setProjeto(Projeto projeto_numero) {
        this.projeto = projeto_numero;
    }

    /**
     * @return the horas
     */
    public float getHoras() {
        return horas;
    }

    /**
     * @param horas the horas to set
     */
    public void setHoras(float horas) {
        this.horas = horas;
    }
    
    
}
