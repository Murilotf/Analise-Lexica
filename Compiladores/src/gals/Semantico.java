package gals;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Stack;
import javax.swing.JOptionPane;
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
            JOptionPane.showMessageDialog(null, "Erro");
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

                    // Essa expressão está errada nao? Vc ta dizendo que se for booleano ou vetor está ok a leitura
                    if (!(idVariavel.getTipoIDVariavel().getSubCategoriasVariavel() == SubCategoriasVariavel.BOOLEANO
                            || idVariavel.getTipoIDVariavel().getSubCategoriasVariavel() == SubCategoriasVariavel.VETOR)) {
                        throw new SemanticError("Tipo inv. p/ leitura", token.getPosition());
                    }
                }
                if (Categoria.PARAMETRO == categoria) {
                    IDParametro iDParametro = (IDParametro) identificador;
                    // Essa expressão está errada nao? Vc ta dizendo que se for booleano está ok o parametro
                    if (!(Tipo.BOOLEANO == iDParametro.getTipo())) {
                        throw new SemanticError("Tipo inv. p/ leitura", token.getPosition());
                    }
                }
                /*Gera Cód. para leitura*/
            }
        }
    }

    public void execAcao114(Token token) throws SemanticError {

        if (subCategoria == SubCategoria.CADEIA || subCategoria == SubCategoria.VETOR) {
            throw new SemanticError("Apenas id de tipo pré-def podem ser declarados como constante", token.getPosition());
        } else {
            categoriaAtual = Categoria.CONSTANTE;
        }


    }

    public void execAcao115(Token token) throws SemanticError {

        if (tipoConst != tipoAtual) {
            throw new SemanticError("Tipo da constante incorreto", token.getPosition());
        }

    }

    public void execAcao116(Token token) throws SemanticError {

        categoriaAtual = Categoria.VARIAVEL;
    }

    
    //fiquei confuso aqui
    public void execAcao117(Token token) throws SemanticError {

        if (ts.isExisteIdentificadorNesteNivel(nivelAtual, token.getLexeme())) {
            throw new SemanticError("Id já declarado", token.getPosition());
        } else {
            ts.adicionarIdentificador(new IDParametro(token.getLexeme(), Categoria.METODO, nivelAtual));
            NPF = 0;
            pilhaDeslocamento.push(0);
            incrementaNivelAtual();
        }
    }

    public void execAcao118(Token token) throws SemanticError {
    }

    public void execAcao119(Token token) throws SemanticError {
    }

    public void execAcao120(Token token) throws SemanticError {
    }

    public void execAcao121(Token token) throws SemanticError {
    }

    public void execAcao122(Token token) throws SemanticError {
    }

    public void execAcao123(Token token) throws SemanticError {
    }

    public void execAcao124(Token token) throws SemanticError {
    }

    public void execAcao125(Token token) throws SemanticError {
    }

    public void execAcao126(Token token) throws SemanticError {
    }

    public void execAcao127(Token token) throws SemanticError {
    }

    public void execAcao128(Token token) throws SemanticError {
    }

    public void execAcao129(Token token) throws SemanticError {
    }

    public void execAcao130(Token token) throws SemanticError {
    }

    public void execAcao131(Token token) throws SemanticError {
    }

    public void execAcao132(Token token) throws SemanticError {
    }

    public void execAcao133(Token token) throws SemanticError {
    }

    public void execAcao134(Token token) throws SemanticError {
    }

    public void execAcao135(Token token) throws SemanticError {
    }

    public void execAcao136(Token token) throws SemanticError {
    }

    public void execAcao137(Token token) throws SemanticError {
    }

    public void execAcao138(Token token) throws SemanticError {
    }

    public void execAcao139(Token token) throws SemanticError {
    }

    public void execAcao140(Token token) throws SemanticError {
    }

    public void execAcao141(Token token) throws SemanticError {
    }

    public void execAcao142(Token token) throws SemanticError {
    }

    public void execAcao143(Token token) throws SemanticError {
    }

    public void execAcao144(Token token) throws SemanticError {
    }

    public void execAcao145(Token token) throws SemanticError {
    }

    public void execAcao146(Token token) throws SemanticError {
    }

    public void execAcao147(Token token) throws SemanticError {
    }

    public void execAcao148(Token token) throws SemanticError {
    }

    public void execAcao149(Token token) throws SemanticError {
    }

    public void execAcao150(Token token) throws SemanticError {
    }

    public void execAcao151(Token token) throws SemanticError {
    }

    public void execAcao152(Token token) throws SemanticError {
    }

    public void execAcao153(Token token) throws SemanticError {
    }

    public void execAcao154(Token token) throws SemanticError {
    }

    public void execAcao155(Token token) throws SemanticError {
    }

    public void execAcao156(Token token) throws SemanticError {
    }

    public void execAcao157(Token token) throws SemanticError {
    }

    public void execAcao158(Token token) throws SemanticError {
    }

    public void execAcao159(Token token) throws SemanticError {
    }

    public void execAcao160(Token token) throws SemanticError {
    }

    public void execAcao161(Token token) throws SemanticError {
    }

    public void execAcao162(Token token) throws SemanticError {
    }

    public void execAcao163(Token token) throws SemanticError {
    }

    public void execAcao164(Token token) throws SemanticError {
    }

    public void execAcao165(Token token) throws SemanticError {
    }

    public void execAcao166(Token token) throws SemanticError {
    }

    public void execAcao167(Token token) throws SemanticError {
    }

    public void execAcao168(Token token) throws SemanticError {
    }

    public void execAcao169(Token token) throws SemanticError {
    }

    public void execAcao170(Token token) throws SemanticError {
    }

    public void execAcao171(Token token) throws SemanticError {
    }

    public void execAcao172(Token token) throws SemanticError {
    }

    public void execAcao173(Token token) throws SemanticError {
    }

    public void execAcao174(Token token) throws SemanticError {
    }

    public void execAcao175(Token token) throws SemanticError {
    }

    public void execAcao176(Token token) throws SemanticError {
    }

    public void execAcao177(Token token) throws SemanticError {
    }

    public void execAcao178(Token token) throws SemanticError {
    }

    public void execAcao179(Token token) throws SemanticError {
    }

    public void execAcao180(Token token) throws SemanticError {
    }

    public void execAcao181(Token token) throws SemanticError {
    }
}
