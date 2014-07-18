/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package semantico;

/**
 *
 * @author Avell G1511
 */
public class IDParametro extends Identificador {

    private MecanismoDePassagem mecanismoDePassagem;
    private Tipo tipo;
    private int deslocamento;

    public IDParametro() {
    }

    public IDParametro(String nome, int nivel) {
        super(nome, nivel);
    }

    public IDParametro(String nome, Categoria categoria, int nivel) {
        super(nome, categoria, nivel);
    }

    public IDParametro(MecanismoDePassagem mecanismoDePassagem, Tipo tipo, int deslocamento) {
        this.mecanismoDePassagem = mecanismoDePassagem;
        this.tipo = tipo;
        this.deslocamento = deslocamento;
    }

    public IDParametro(MecanismoDePassagem mecanismoDePassagem, Tipo tipo, int deslocamento, String nome, int nivel) {
        super(nome, nivel);
        this.mecanismoDePassagem = mecanismoDePassagem;
        this.tipo = tipo;
        this.deslocamento = deslocamento;
    }

    public IDParametro(MecanismoDePassagem mecanismoDePassagem, Tipo tipo, int deslocamento, String nome, Categoria categoria, int nivel) {
        super(nome, categoria, nivel);
        this.mecanismoDePassagem = mecanismoDePassagem;
        this.tipo = tipo;
        this.deslocamento = deslocamento;
    }

    /**
     * @return the mecanismoDePassagem
     */
    public MecanismoDePassagem getMecanismoDePassagem() {
        return mecanismoDePassagem;
    }

    /**
     * @param mecanismoDePassagem the mecanismoDePassagem to set
     */
    public void setMecanismoDePassagem(MecanismoDePassagem mecanismoDePassagem) {
        this.mecanismoDePassagem = mecanismoDePassagem;
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
}
