/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package semantico;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Avell G1511
 */
public class TabelaSimbolos {

    
    //COMENTARIO
    
    private List<Identificador> identificadores;

    public TabelaSimbolos() {
        this.identificadores = new ArrayList<>();
    }

    /**
     * @return the identificadores
     */
    public List<Identificador> getIdentificadores() {
        return identificadores;
    }

    /**
     * @param identificadores the identificadores to set
     */
    public void setIdentificadores(List<Identificador> identificadores) {
        this.identificadores = identificadores;
    }

    public void adicionarIdentificador(Identificador identificador) {
        this.identificadores.add(identificador);

    }

    public void adicionarIdentificadorComPosicao(int posicao, Identificador identificador) {
        this.identificadores.add(posicao, identificador);
    }

    public void removeIdentificador(Identificador identificador) {
        this.identificadores.remove(identificador);
    }

    public void removeIdentificadorPelaPosicao(int posicao) {
        this.identificadores.remove(posicao);
    }

    public Boolean contains(Identificador identificador) {
        if (this.identificadores.contains(identificador)) {
            return true;
        }
        return false;
    }

    public Identificador getIdentificador(int posicao) {
        return this.identificadores.get(posicao);
    }

    public int getSize() {
        return this.identificadores.size();
    }

    public void clear() {
        this.identificadores.clear();
    }

    public void removeIdentificadorNoNivel(int nivel, Identificador param) {
        for (Identificador identificador : identificadores) {
            if (identificador.equals(param) && identificador.getNivel() == nivel) {
                this.identificadores.remove(identificador);
                return;
            }
        }
        //NAO ENCONTRADO??
    }

    public Boolean isExisteIdentificadorNesteEscopo(int nivel, String nome) {
        for (Identificador identificador : identificadores) {
            if (identificador.getNome().equals(nome) && identificador.getNivel() <= nivel) {
                return true;
            }
        }
        return false;
    }

    public Boolean isExisteIdentificadorNesteNivel(int nivel, String nome) {
        for (Identificador identificador : identificadores) {
            if (identificador.getNome().equals(nome) && (identificador.getNivel() == nivel || identificador.getNivel() == 0)) {
                return true;
            }
        }
        return false;


    }

    public Integer getPosicaoIdentificador(int nivel, String nome) {

        for (int i = 0; i < this.identificadores.size(); i++) {
            if ((this.identificadores.get(i).getNome().equals(nome) && this.identificadores.get(i).getNivel() <= nivel)
                    && (this.identificadores.get(i).getNivel() > 0)) {
                return i;
            }
        }
        return 0;
    }

    public void removeNivelAtual(int nivelAtual) {
        for (int i = 0; i < this.identificadores.size(); i++) {
            if (nivelAtual == identificadores.get(i).getNivel()) {
                identificadores.remove(i);
                //NAO ENTENDI O i--
                i--;
            }
        }
    }

    public Identificador getIdentificadorNoNivel(int nivel, String nome) {

        for (Identificador identificador : identificadores) {
            if ((identificador.getNome().equals(nome) && identificador.getNivel() <= nivel) && identificador.getNivel() > 0) {
                return identificador;
            }
        }
        return null;
    }
}
