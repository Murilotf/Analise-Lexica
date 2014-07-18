/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package semantico;

/**
 *
 * @author Avell G1511
 */
public class IDConstante extends Identificador{
    
    private String valor;
    private Tipo tipo;
    

    public IDConstante() {
    }

    public IDConstante(String nome, int nivel) {
        super(nome, nivel);
    }

    public IDConstante(String nome, Categoria categoria, int nivel) {
        super(nome, categoria, nivel);
    }

    public IDConstante(String valor, Tipo tipo) {
        this.valor = valor;
        this.tipo = tipo;
    }

    public IDConstante(String valor, Tipo tipo, String nome, int nivel) {
        super(nome, nivel);
        this.valor = valor;
        this.tipo = tipo;
    }

    public IDConstante(String valor, Tipo tipo, String nome, Categoria categoria, int nivel) {
        super(nome, categoria, nivel);
        this.valor = valor;
        this.tipo = tipo;
    }
    
    

    /**
     * @return the valor
     */
    public String getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(String valor) {
        this.valor = valor;
    }

    /**
     * @return the tipo
     */
    public Tipo getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    
    
}

