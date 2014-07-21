/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package semantico;

import semantico.identificadores.Identificador;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Avell G1511
 */
public class TabelaSimbolos {

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
        identificadores.add(identificador);

    }

    public void adicionarIdentificadorComPosicao(int posicao, Identificador identificador) {
        identificadores.set(posicao, identificador);
    }

    public void removeIdentificador(Identificador identificador) {
        identificadores.remove(identificador);
    }

    public void removeIdentificadorPelaPosicao(int posicao) {
        identificadores.remove(posicao);
    }

    public Boolean contains(Identificador identificador) {
        if (identificadores.contains(identificador)) {
            return true;
        }
        return false;
    }

    public Identificador getIdentificador(int posicao) {
        return identificadores.get(posicao);
    }

    public int getSize() {
        return identificadores.size();
    }

    public void clear() {
        identificadores.clear();
    }

    public void removeIdentificadorNoNivel(int nivel, Identificador param) {
        Identificador object = new Identificador();
        for (Identificador identificador : identificadores) {
            if (identificador.equals(param) && identificador.getNivel() == nivel) {
                object = identificador;
            }
        }
        identificadores.remove(object);
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
            if (identificador.getNome().equals(nome) && (identificador.getNivel() == nivel || identificador.getNivel() == -1)) {
                return true;
            }
        }
        return false;


    }

    public Integer getPosicaoIdentificador(int nivel, String nome) {
        int retorno = 0;
        for (int i = 0; i < identificadores.size(); i++) {
            if ((identificadores.get(i).getNome().equals(nome) && identificadores.get(i).getNivel() <= nivel)
                    && (identificadores.get(i).getNivel() > -1)) {
                retorno = i;
            }
        }
        return retorno;
    }

    public void removeNivelAtual(int nivelAtual) {
        for (int i = 0; i < identificadores.size(); i++) {
            if (nivelAtual == identificadores.get(i).getNivel()) {
                identificadores.remove(i);
                i--;
            }
        }
    }

    public Identificador getIdentificadorNoNivel(int nivel, String nome) {
        Identificador retorno = new Identificador("", -1);
        for (Identificador identificador : identificadores) {
            if ((identificador.getNome().equals(nome) && identificador.getNivel() <= nivel) && identificador.getNivel() > retorno.getNivel()) {
                retorno = identificador;
            }
        }
        if ("".equals(retorno.getNome())) {
            return null;
        }
        return retorno;
    }
}
