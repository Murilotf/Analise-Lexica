/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package semantico;

import static semantico.Tipo.CARACTER;
import static semantico.Tipo.INTEIRO;
import static semantico.Tipo.REAL;

/**
 *
 * @author Avell G1511
 */
public class Utils {

    public static SubCategoriasVariavel getSubCategoriasVariavel(Tipo tipo) {
        switch (tipo) {
            case INTEIRO:
                return SubCategoriasVariavel.INTEIRO;
            case REAL:
                return SubCategoriasVariavel.REAL;
            case CARACTER:
                return SubCategoriasVariavel.CARACTER;
            case CADEIA:
                return SubCategoriasVariavel.CADEIA;
            case BOOLEANO:
                return SubCategoriasVariavel.BOOLEANO;
            default:
                return null;
        }

    }

    public static Tipo getTipo(SubCategoriasVariavel subCategoriasVariavel) {
        switch (subCategoriasVariavel) {
            case INTEIRO:
                return Tipo.INTEIRO;
            case BOOLEANO:
                return Tipo.BOOLEANO;
            case CARACTER:
                return Tipo.CARACTER;
            case CADEIA:
                return Tipo.CADEIA;
            case REAL:
                return Tipo.REAL;
            default:
                return null;
        }
    }
}
