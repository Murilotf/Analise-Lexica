/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package semantico;

/**
 *
 * @author Avell G1511
 */
public class TipoIDVariavel {

    private SubCategoriasVariavel subCategoriasVariavel;
    private Tipo tpElementos;
    private int tamanho;

    public TipoIDVariavel() {
    }

    public TipoIDVariavel(SubCategoriasVariavel subCategoriasVariavel, Tipo tpElementos, int tamanho) {
        this.subCategoriasVariavel = subCategoriasVariavel;
        this.tpElementos = tpElementos;
        this.tamanho = tamanho;
    }
    
    

    /**
     * @return the subCategoriasVariavel
     */
    public SubCategoriasVariavel getSubCategoriasVariavel() {
        return subCategoriasVariavel;
    }

    /**
     * @param subCategoriasVariavel the subCategoriasVariavel to set
     */
    public void setSubCategoriasVariavel(SubCategoriasVariavel subCategoriasVariavel) {
        this.subCategoriasVariavel = subCategoriasVariavel;
    }

    /**
     * @return the tpElementos
     */
    public Tipo getTpElementos() {
        return tpElementos;
    }

    /**
     * @param tpElementos the tpElementos to set
     */
    public void setTpElementos(Tipo tpElementos) {
        this.tpElementos = tpElementos;
    }

    /**
     * @return the tamanho
     */
    public int getTamanho() {
        return tamanho;
    }

    /**
     * @param tamanho the tamanho to set
     */
    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }
}
