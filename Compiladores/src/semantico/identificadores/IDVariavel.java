/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package semantico.identificadores;

import semantico.tipos.Categoria;

/**
 *
 * @author Avell G1511
 */
public class IDVariavel extends Identificador {

    private TipoIDVariavel tipoIDVariavel;
    private int deslocamento;

    public IDVariavel() {
    }

    public IDVariavel(String nome, int nivel) {
        super(nome, nivel);
    }

    public IDVariavel(String nome, Categoria categoria, int nivel) {
        super(nome, categoria, nivel);
    }

    public IDVariavel(TipoIDVariavel tipoIDVariavel, int deslocamento) {
        this.tipoIDVariavel = tipoIDVariavel;
        this.deslocamento = deslocamento;
    }

    public IDVariavel(TipoIDVariavel tipoIDVariavel, int deslocamento, String nome, int nivel) {
        super(nome, nivel);
        this.tipoIDVariavel = tipoIDVariavel;
        this.deslocamento = deslocamento;
    }

    public IDVariavel(TipoIDVariavel tipoIDVariavel, int deslocamento, String nome, Categoria categoria, int nivel) {
        super(nome, categoria, nivel);
        this.tipoIDVariavel = tipoIDVariavel;
        this.deslocamento = deslocamento;
    }
    
    

    /**
     * @return the deslocamento
     */
    public int getDeslocamento() {
        return deslocamento;
    }

    /**
     * @param deslocamento the deslocamento to set
     */
    public void setDeslocamento(int deslocamento) {
        this.deslocamento = deslocamento;
    }

    /**
     * @return the tipoIDVariavel
     */
    public TipoIDVariavel getTipoIDVariavel() {
        return tipoIDVariavel;
    }

    /**
     * @param tipoIDVariavel the tipoIDVariavel to set
     */
    public void setTipoIDVariavel(TipoIDVariavel tipoIDVariavel) {
        this.tipoIDVariavel = tipoIDVariavel;
    }

 


}
