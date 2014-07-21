/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package semantico.identificadores;

import java.util.Objects;
import semantico.tipos.Categoria;



/**
 *
 * @author Avell G1511
 */
public class Identificador {
   
    private String nome;
    private Categoria categoria;
    private int nivel;

    public Identificador() {
    }
    
    public Identificador(String nome, int nivel) {
        this.nome = nome;        
        this.nivel = nivel;
    }

    public Identificador(String nome, Categoria categoria, int nivel) {
        this.nome = nome;
        this.categoria = categoria;
        this.nivel = nivel;
    }

 

    @Override
    public boolean equals(Object obj) {
        if ( !(obj instanceof Identificador) ) {
            return false;
        }
        return this.getNome().equals(((Identificador)obj).getNome());
    }   

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.nome);
        hash = 37 * hash + Objects.hashCode(this.categoria);
        hash = 37 * hash + this.nivel;
        return hash;
    }

   
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
     * @return the categoria
     */
    public Categoria getCategoria() {
        return categoria;
    }

    /**
     * @param categoria the categoria to set
     */
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    /**
     * @return the nivel
     */
    public int getNivel() {
        return nivel;
    }

    /**
     * @param nivel the nivel to set
     */
    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
}
