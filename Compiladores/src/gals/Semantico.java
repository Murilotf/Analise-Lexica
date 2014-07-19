package gals;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javax.swing.JOptionPane;
import semantico.Categoria;
import semantico.ContextoEXPR;
import semantico.ContextoLID;
import semantico.IDConstante;
import semantico.IDMetodo;
import semantico.IDParametro;
import semantico.IDVariavel;
import semantico.Identificador;
import semantico.MecanismoDePassagem;
import semantico.SubCategoria;
import semantico.SubCategoriasVariavel;
import semantico.TabelaSimbolos;
import semantico.Tipo;
import semantico.TipoIDVariavel;
import semantico.Utils;

public class Semantico implements Constants {

    private TabelaSimbolos ts;
    private Stack<Integer> pilhaDeslocamento;
    private Stack<Integer> pilhaIDMetodo;
    private Stack<Boolean> pilhaRetorno;
    private Stack<Integer> pPOSID;
    private Stack<Tipo> pTipoExpr;
    private Stack<Tipo> pTipoVarIndexada;
    private Stack<Stack<IDParametro>> pIDsParametro;
    private Stack<ContextoEXPR> pContextoEXPR;
    private Stack<Integer> pNPA;
    private int nivelAtual;
    private ContextoLID contextoLID;
    private int primeiroID;
    private int qtdIDs;
    private int ultimoID;
    private Categoria categoriaAtual;
    private SubCategoria subCategoria;
    private Tipo tipoAtual;
    private Tipo tipoConst;
    private Tipo tipoLadoEsq;
    private String valConst;
    private int numElementos;
    private int NPF;
    private List<IDParametro> listIDsParametro = new ArrayList<>();
    private boolean resultadoNulo;
    private Tipo tpResultadoMetodo;
    private MecanismoDePassagem mecanismoPassagem;
    private boolean temRetorno = false;

    public void incrementaNivelAtual() {
        nivelAtual++;
    }

    public void decrementaNivelAtual() {
        nivelAtual--;
    }

    public void incrementaNPF() {
        NPF++;
    }

    public void incrementaQtdIDs() {
        qtdIDs++;
    }

    public Semantico() {
        ts = new TabelaSimbolos();
        pilhaDeslocamento = new Stack<>();
        pilhaIDMetodo = new Stack<>();
        pilhaRetorno = new Stack<>();
        pPOSID = new Stack<>();
        pTipoExpr = new Stack<>();
        pTipoVarIndexada = new Stack<>();
        pIDsParametro = new Stack<>();
        pNPA = new Stack<>();
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

    public void execAcao117(Token token) throws SemanticError {

        if (ts.isExisteIdentificadorNesteNivel(nivelAtual, token.getLexeme())) {
            throw new SemanticError("Id já declarado", token.getPosition());
        }
        ts.adicionarIdentificador(new IDMetodo(token.getLexeme(), Categoria.METODO, nivelAtual));
        NPF = 0;
        pilhaDeslocamento.push(0);
        pilhaIDMetodo.push(ts.getSize() - 1);
        qtdIDs = 0;
        listIDsParametro = new ArrayList<>();
        incrementaNivelAtual();

    }

    public void execAcao118(Token token) throws SemanticError {
        IDMetodo iDMetodo;
        iDMetodo = (IDMetodo) ts.getIdentificador(pilhaIDMetodo.peek());
        iDMetodo.setNumeroParametros(NPF);
        iDMetodo.setiDParametros(listIDsParametro);
    }

    public void execAcao119(Token token) throws SemanticError {
        IDMetodo iDMetodo;
        iDMetodo = (IDMetodo) ts.getIdentificador(pilhaIDMetodo.peek());
        if (resultadoNulo) {
            pilhaRetorno.push(false);
        } else {
            iDMetodo.setTipo(tpResultadoMetodo);
            pilhaRetorno.push(true);
        }
        iDMetodo.setResultadoNulo(resultadoNulo);
    }

    public void execAcao120(Token token) throws SemanticError {
        ts.removeNivelAtual(nivelAtual);
        decrementaNivelAtual();
        IDMetodo iDMetodo = (IDMetodo) ts.getIdentificador(pilhaIDMetodo.peek());
        if (!temRetorno && !iDMetodo.isResultadoNulo()) {
            throw new SemanticError("metodo com tipo deve ter retorno", token.getPosition());
        }
        temRetorno = false;
        pilhaDeslocamento.pop();
        pilhaIDMetodo.pop();
        pilhaRetorno.pop();

    }

    public void execAcao121(Token token) throws SemanticError {
        contextoLID = ContextoLID.PAR_FORMAL;
        primeiroID = ts.getSize();
        qtdIDs = 0;

    }

    public void execAcao122(Token token) throws SemanticError {
        ultimoID = primeiroID + ts.getSize();
    }

    public void execAcao123(Token token) throws SemanticError {
        if (tipoAtual == Tipo.CADEIA) {
            subCategoria = SubCategoria.CADEIA;
        } else {
            subCategoria = SubCategoria.PRE_DEFINIDO;
        }

        if (subCategoria != SubCategoria.PRE_DEFINIDO) {
            throw new SemanticError("Par. devem ser de tipo pré-def.", token.getPosition());
        } else {
            int indice = primeiroID;
            do {
                int deslocamento = pilhaDeslocamento.pop();
                Identificador identificador = ts.getIdentificador(indice);
                IDParametro iDParametro = new IDParametro(identificador.getNome(), identificador.getCategoria(), identificador.getNivel());
                iDParametro.setCategoria(Categoria.PARAMETRO);
                iDParametro.setMecanismoDePassagem(mecanismoPassagem);
                iDParametro.setTipo(tipoAtual);
                iDParametro.setDeslocamento(deslocamento);
                deslocamento++;
                pilhaDeslocamento.push(deslocamento);
                ts.adicionarIdentificadorComPosicao(indice, iDParametro);
                listIDsParametro.add(iDParametro);
                indice++;
            } while (indice <= ultimoID - 1);
        }
    }

    public void execAcao124(Token token) throws SemanticError {
        if (tipoAtual == Tipo.CADEIA) {
            throw new SemanticError("Métodos devem ser de tipo pré-def.", token.getPosition());
        }
        resultadoNulo = false;
        tpResultadoMetodo = tipoAtual;
    }

    public void execAcao125(Token token) throws SemanticError {
        resultadoNulo = true;
    }

    public void execAcao126(Token token) throws SemanticError {
        mecanismoPassagem = MecanismoDePassagem.REFERENCIA;
    }

    public void execAcao127(Token token) throws SemanticError {
        mecanismoPassagem = MecanismoDePassagem.VALOR;
    }

    public void execAcao128(Token token) throws SemanticError {
        if (!ts.isExisteIdentificadorNesteEscopo(nivelAtual, token.getLexeme())) {
            throw new SemanticError("Identificador não declarado", token.getPosition());
        }
        pPOSID.push(ts.getPosicaoIdentificador(nivelAtual, token.getLexeme()));

    }

    public void execAcao129(Token token) throws SemanticError {
        Tipo tipoExpr = pTipoExpr.pop();

        if (tipoExpr != Tipo.BOOLEANO && tipoExpr != Tipo.INTEIRO) {
            throw new SemanticError("Tipo inválido da expressão", token.getPosition());
        }
        //* ação de G. código *
    }

    public void execAcao130(Token token) throws SemanticError {
        contextoLID = ContextoLID.LEITURA;
    }

    public void execAcao131(Token token) throws SemanticError {
        pContextoEXPR.push(ContextoEXPR.impressão);
    }

    public void execAcao132(Token token) throws SemanticError {
        if (!pilhaRetorno.isEmpty() && pilhaRetorno.peek()) {
            Tipo tipoEXPR = pTipoExpr.pop();
            IDMetodo iDMetodo = (IDMetodo) ts.getIdentificador(pilhaIDMetodo.peek());
            Tipo tipoMetodo = iDMetodo.getTipo();
            if (tipoEXPR != tipoMetodo) {
                if (!((tipoMetodo == Tipo.CADEIA && tipoEXPR == Tipo.CARACTER) || (tipoMetodo == Tipo.REAL && tipoEXPR == Tipo.INTEIRO))) {
                    throw new SemanticError("Tipo de retorno inválido", token.getPosition());
                }
                temRetorno = true;
            } else {
                //* ação de Geração de Código *
                temRetorno = true;
            }
        } else {
            throw new SemanticError("'Retorne' só pode ser usado em método com tipo", token.getPosition());
        }
    }

    public void execAcao133(Token token) throws SemanticError {
        Identificador identificador = ts.getIdentificador(pPOSID.pop());
        Categoria categoria = identificador.getCategoria();
        if (categoria == Categoria.VARIAVEL) {
            IDVariavel iDVariavel = (IDVariavel) identificador;
            if (iDVariavel.getTipoIDVariavel().getSubCategoriasVariavel() == SubCategoriasVariavel.VETOR) {
                throw new SemanticError("id. Deveria ser indexado", token.getPosition());
            } else {
                tipoLadoEsq = Utils.getTipo(iDVariavel.getTipoIDVariavel().getSubCategoriasVariavel());
            }
        } else if (categoria == Categoria.PARAMETRO) {
            IDParametro iDParametro = (IDParametro) identificador;
            tipoLadoEsq = iDParametro.getTipo();
        } else {
            throw new SemanticError("id. deveria ser var ou par", token.getPosition());
        }
    }

    public void execAcao134(Token token) throws SemanticError {
        Tipo tipoExpr = pTipoExpr.pop();
        if (tipoExpr != tipoLadoEsq) {
            if (!((tipoExpr == Tipo.CARACTER && tipoLadoEsq == Tipo.CADEIA) || (tipoExpr == Tipo.INTEIRO && tipoLadoEsq == Tipo.REAL))) {
                throw new SemanticError("tipos incompatíveis", token.getPosition());
            }
        } else {
            // * G. Código *
        }
    }

    public void execAcao135(Token token) throws SemanticError {
        Identificador identificador = ts.getIdentificador(pPOSID.peek());
        Categoria categoria = identificador.getCategoria();
        if (categoria != Categoria.VARIAVEL) {
            throw new SemanticError("esperava-se uma variável", token.getPosition());
        } else if (resultadoNulo) {
            IDVariavel iDVariavel = (IDVariavel) identificador;
            if (iDVariavel.getTipoIDVariavel().getSubCategoriasVariavel() != SubCategoriasVariavel.VETOR
                    && iDVariavel.getTipoIDVariavel().getSubCategoriasVariavel() != SubCategoriasVariavel.CADEIA) {
                throw new SemanticError("apenas vetores e cadeias podem ser indexados", token.getPosition());
            }
            pTipoVarIndexada.push(Utils.getTipo(iDVariavel.getTipoIDVariavel().getSubCategoriasVariavel()));
        }
    }

    public void execAcao136(Token token) throws SemanticError {
        IDVariavel iDVariavel = (IDVariavel) ts.getIdentificador(pPOSID.pop());
        Tipo tipoExpr = pTipoExpr.pop();
        if (tipoExpr != Tipo.INTEIRO) {
            throw new SemanticError("índice deveria ser inteiro", token.getPosition());
        } else {
            if (pTipoVarIndexada.pop() == Tipo.CADEIA) {
                tipoLadoEsq = Tipo.CADEIA;
            } else {
                tipoLadoEsq = iDVariavel.getTipoIDVariavel().getTpElementos();
            }
        }

    }

    public void execAcao137(Token token) throws SemanticError {
        Identificador identificador = ts.getIdentificador(pPOSID.peek());
        Categoria categoria = identificador.getCategoria();
        if (categoria != Categoria.METODO) {
            throw new SemanticError("id deveria ser um método", token.getPosition());
        }
        IDMetodo iDMetodo = (IDMetodo) identificador;
        if (!iDMetodo.isResultadoNulo()) {
            throw new SemanticError("esperava-se mét sem tipo", token.getPosition());
        }
        pIDsParametro.push(iDMetodo.getIDsParametro());
        pContextoEXPR.push(ContextoEXPR.parAtual);
    }

    public void execAcao138(Token token) throws SemanticError {
        pNPA.push(0);
        pContextoEXPR.push(ContextoEXPR.parAtual);
    }

    public void execAcao139(Token token) throws SemanticError {
        Identificador identificador = ts.getIdentificador(pPOSID.pop());
        IDMetodo iDMetodo = (IDMetodo) identificador;
        if (pNPA.pop() != iDMetodo.getNumeroParametros()) {
            throw new SemanticError("Erro na quant. de parâmetros", token.getPosition());
        } else {
            //* G. Código para chamada de proc*
            pContextoEXPR.pop();
            pIDsParametro.pop();
        }
    }

    public void execAcao140(Token token) throws SemanticError {
        Identificador identificador = ts.getIdentificador(pPOSID.pop());
        Categoria categoria = identificador.getCategoria();
        if (categoria != Categoria.METODO) {
            throw new SemanticError("id deveria ser um método", token.getPosition());
        }
        IDMetodo iDMetodo = (IDMetodo) identificador;
        if (!iDMetodo.isResultadoNulo()) {
            throw new SemanticError("esperava-se método sem tipo", token.getPosition());
        }
        if (iDMetodo.getNumeroParametros() == 0) {
            //*GC p/ chamada de método *
        } else {
            throw new SemanticError("Erro na quantidade de parametros", token.getPosition());
        }
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
