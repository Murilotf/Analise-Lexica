package gals;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Stack;
import semantico.Categoria;
import semantico.ContextoLID;
import semantico.IDConstante;
import semantico.IDParametro;
import semantico.IDVariavel;
import semantico.Identificador;
import semantico.SubCategoria;
import semantico.SubCategoriasVariavel;
import semantico.TabelaSimbolos;
import semantico.Tipo;
import semantico.TipoIDVariavel;
import semantico.Utils;

public class Semantico implements Constants {

    private TabelaSimbolos ts;
    private Stack<Integer> pilhaDeslocamento = new Stack<>();
    private int nivelAtual;
    private ContextoLID contextoLID;
    private int primeiroID;
    private int qtdIDs;
    private int ultimoID;
    private Categoria categoriaAtual;
    private SubCategoria subCategoria;
    private Tipo tipoAtual;
    private Tipo tipoConst;
    private String valConst;
    private int numElementos;
    private int NPF;

    public void incrementaNivelAtual() {
        nivelAtual++;
    }

    public void incrementaNPF() {
        NPF++;
    }

    public void incrementaQtdIDs() {
        qtdIDs++;
    }

    public Semantico() {
        this.ts = new TabelaSimbolos();
    }

    public void executeAction(int action, Token token) throws SemanticError {
        try {
            System.out.println("Ação " + action + " - token: " + token.getLexeme());
            Method execAcao = this.getClass().getMethod("execAcao" + action, Token.class);
            execAcao.invoke(this, token);
        } catch (InvocationTargetException e) {
            Throwable targetException = e.getTargetException();
            //if (targetException instanceof SemanticError) {
            // SemanticError semanticError = (SemanticError) targetException;
            //semanticError.setPosition(token.getPosition()); verificar este caso 
            // throw semanticError;
            // }
            throw new SemanticError("Erro SEMÂNTICO: " + targetException);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void execAcao101(Token token) {
        ts.adicionarIdentificador(new Identificador(token.getLexeme(), Categoria.PROGRAMA, 0));
        nivelAtual = 0;
        pilhaDeslocamento.push(0);
        incrementaNivelAtual();
    }

    public void execAcao102(Token token) {
        contextoLID = ContextoLID.DECL;
        primeiroID = ts.getSize();
        qtdIDs = 0;
    }

    public void execAcao103(Token token) {
        ultimoID = qtdIDs + primeiroID;
    }

    public void execAcao104(Token token) {
        int i = primeiroID;
        int deslocamento = pilhaDeslocamento.pop();
        do {
            if (Categoria.CONSTANTE == categoriaAtual) {
                Identificador simbolo = ts.getIdentificador(i);
                IDConstante iDConstante = new IDConstante(simbolo.getNome(), simbolo.getCategoria(), simbolo.getNivel());
                iDConstante.setValor(valConst);
                iDConstante.setCategoria(this.categoriaAtual);
                iDConstante.setTipo(tipoAtual);
                ts.adicionarIdentificadorComPosicao(i, iDConstante);
            } else if (Categoria.VARIAVEL == categoriaAtual) {
                Identificador simbolo = ts.getIdentificador(i);
                IDVariavel variavel = new IDVariavel(simbolo.getNome(), simbolo.getCategoria(), simbolo.getNivel());
                if (this.subCategoria == SubCategoria.PRE_DEFINIDO) {
                    variavel.setCategoria(categoriaAtual);
                    variavel.setTipoIDVariavel(new TipoIDVariavel(Utils.getSubCategoriasVariavel(tipoAtual), null, 0));
                    variavel.setDeslocamento(deslocamento);
                    deslocamento++;
                }
                if (this.subCategoria == SubCategoria.CADEIA) {;
                    variavel.setCategoria(categoriaAtual);
                    variavel.setTipoIDVariavel(new TipoIDVariavel(Utils.getSubCategoriasVariavel(tipoAtual), null, Integer.parseInt(valConst)));
                    variavel.setDeslocamento(deslocamento);
                    deslocamento++;
                }
                if (this.subCategoria == SubCategoria.VETOR) {
                    variavel.setCategoria(categoriaAtual);
                    variavel.setTipoIDVariavel(new TipoIDVariavel(SubCategoriasVariavel.VETOR, tipoAtual, numElementos));

                    variavel.setDeslocamento(deslocamento);
                    deslocamento += numElementos;
                }
                this.ts.adicionarIdentificadorComPosicao(i, variavel);
            }
            i++;
        } while (i != this.ultimoID);
        pilhaDeslocamento.push(deslocamento);
    }

    public void execAcao105(Token token) {
        tipoAtual = Tipo.INTEIRO;
    }

    public void execAcao106(Token token) {
        tipoAtual = Tipo.REAL;
    }

    public void execAcao107(Token token) {
        tipoAtual = Tipo.BOOLEANO;
    }

    public void execAcao108(Token token) {
        tipoAtual = Tipo.CARACTER;
    }

    public void execAcao109(Token token) throws SemanticError {
        if (tipoConst != Tipo.INTEIRO) {
            throw new SemanticError("esperava-se uma const. inteira", token.getPosition());
        }
        if (Integer.parseInt(valConst) > 256) {
            throw new SemanticError("cadeia > que o permitido", token.getPosition());
        }
        tipoAtual = Tipo.CADEIA;
    }

    public void execAcao110(Token token) throws SemanticError {
        if (tipoAtual == Tipo.CADEIA) {
            throw new SemanticError("Vetor do tipo cadeia não é permitido", token.getPosition());
        }
        subCategoria = SubCategoria.VETOR;
    }

    public void execAcao111(Token token) throws SemanticError {
        if (tipoConst != Tipo.INTEIRO) {
            throw new SemanticError("A dim.deve ser uma constante inteira", token.getPosition());
        }
        numElementos = Integer.parseInt(valConst);
    }

    public void execAcao112(Token token) {
        if (tipoAtual == Tipo.CADEIA) {
            subCategoria = SubCategoria.CADEIA;
            return;
        }
        subCategoria = SubCategoria.PRE_DEFINIDO;
    }

    public void execAcao113(Token token) throws SemanticError {
        if (contextoLID == ContextoLID.DECL) {
            if (ts.isExisteIdentificadorNesteNivel(nivelAtual, token.getLexeme())) {
                throw new SemanticError("Id já declarado", token.getPosition());
            }
            ts.adicionarIdentificador(new Identificador(token.getLexeme(), categoriaAtual, nivelAtual));
            incrementaQtdIDs();
        } else if (contextoLID == ContextoLID.PAR_FORMAL) {
            if (ts.isExisteIdentificadorNesteNivel(nivelAtual, token.getLexeme())) {
                throw new SemanticError("Id de parâmetro repetido", token.getPosition());
            }
            incrementaNPF();
            ts.adicionarIdentificador(new IDParametro(token.getLexeme(), nivelAtual));
            incrementaQtdIDs();
        } else if (contextoLID == ContextoLID.LEITURA) {
            if (!(ts.isExisteIdentificadorNesteEscopo(nivelAtual, token.getLexeme()))) {
                throw new SemanticError("Id não declarado", token.getPosition());
            } else {
                Identificador identificador = ts.getIdentificadorNoNivel(nivelAtual, token.getLexeme());
                Categoria categoria = identificador.getCategoria();
                if (Categoria.VARIAVEL != categoria && Categoria.PARAMETRO != categoria) {
                    throw new SemanticError("Tipo inv p/leitura", token.getPosition());
                }
                if (Categoria.VARIAVEL == categoria) {
                    IDVariavel idVariavel = (IDVariavel) identificador;
                    if (!(idVariavel.getTipoIDVariavel().getSubCategoriasVariavel() == SubCategoriasVariavel.BOOLEANO
                            || idVariavel.getTipoIDVariavel().getSubCategoriasVariavel() == SubCategoriasVariavel.VETOR)) {
                        throw new SemanticError("Tipo inv. p/ leitura", token.getPosition());
                    }
                }
                if (Categoria.PARAMETRO == categoria) {
                    IDParametro iDParametro = (IDParametro) identificador;
                    if (!(Tipo.BOOLEANO == iDParametro.getTipo())) {
                        throw new SemanticError("Tipo inv. p/ leitura", token.getPosition());
                    }
                }
                /*Gera Cód. para leitura*/
            }
        }
    }
}
