/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package semantico;

import java.util.List;

/**
 *
 * @author Avell G1511
 */
public class IDMetodo extends Identificador {

    
    private int enderecoPrimeiraInstrucao;
    private int numeroParametros;
    private List<IDParametro> iDParametros;
    private Tipo tipo;
    private boolean resultadoNulo;

    public IDMetodo() {
    }

    public IDMetodo(String nome, int nivel) {
        super(nome, nivel);
    }

    public IDMetodo(String nome, Categoria categoria, int nivel) {
        super(nome, categoria, nivel);
    }

    public IDMetodo(int enderecoPrimeiraInstrucao, int numeroParametros, List<IDParametro> iDParametros, Tipo tipo, boolean resultadoNulo) {
        this.enderecoPrimeiraInstrucao = enderecoPrimeiraInstrucao;
        this.numeroParametros = numeroParametros;
        this.iDParametros = iDParametros;
        this.tipo = tipo;
        this.resultadoNulo = resultadoNulo;
    }

    public IDMetodo(int enderecoPrimeiraInstrucao, int numeroParametros, List<IDParametro> iDParametros, Tipo tipo, boolean resultadoNulo, String nome, int nivel) {
        super(nome, nivel);
        this.enderecoPrimeiraInstrucao = enderecoPrimeiraInstrucao;
        this.numeroParametros = numeroParametros;
        this.iDParametros = iDParametros;
        this.tipo = tipo;
        this.resultadoNulo = resultadoNulo;
    }

    public IDMetodo(int enderecoPrimeiraInstrucao, int numeroParametros, List<IDParametro> iDParametros, Tipo tipo, boolean resultadoNulo, String nome, Categoria categoria, int nivel) {
        super(nome, categoria, nivel);
        this.enderecoPrimeiraInstrucao = enderecoPrimeiraInstrucao;
        this.numeroParametros = numeroParametros;
        this.iDParametros = iDParametros;
        this.tipo = tipo;
        this.resultadoNulo = resultadoNulo;
    }
    
    

    /**
     * @return the enderecoPrimeiraInstrucao
     */
    public int getEnderecoPrimeiraInstrucao() {
        return enderecoPrimeiraInstrucao;
    }

    /**
     * @param enderecoPrimeiraInstrucao the enderecoPrimeiraInstrucao to set
     */
    public void setEnderecoPrimeiraInstrucao(int enderecoPrimeiraInstrucao) {
        this.enderecoPrimeiraInstrucao = enderecoPrimeiraInstrucao;
    }

    /**
     * @return the numeroParametros
     */
    public int getNumeroParametros() {
        return numeroParametros;
    }

    /**
     * @param numeroParametros the numeroParametros to set
     */
    public void setNumeroParametros(int numeroParametros) {
        this.numeroParametros = numeroParametros;
    }

    /**
     * @return the iDParametros
     */
    public List<IDParametro> getiDParametros() {
        return iDParametros;
    }

    /**
     * @param iDParametros the iDParametros to set
     */
    public void setiDParametros(List<IDParametro> iDParametros) {
        this.iDParametros = iDParametros;
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

    /**
     * @return the resultadoNulo
     */
    public boolean isResultadoNulo() {
        return resultadoNulo;
    }

    /**
     * @param resultadoNulo the resultadoNulo to set
     */
    public void setResultadoNulo(boolean resultadoNulo) {
        this.resultadoNulo = resultadoNulo;
    }
    
}
