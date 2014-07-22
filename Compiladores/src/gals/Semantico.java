package gals;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javax.swing.JOptionPane;
import semantico.tipos.Categoria;
import semantico.tipos.ContextoEXPR;
import semantico.tipos.ContextoLID;
import semantico.identificadores.IDConstante;
import semantico.identificadores.IDMetodo;
import semantico.identificadores.IDParametro;
import semantico.identificadores.IDVariavel;
import semantico.identificadores.Identificador;
import semantico.tipos.MecanismoDePassagem;
import semantico.tipos.SubCategoria;
import semantico.tipos.SubCategoriasVariavel;
import semantico.TabelaSimbolos;
import semantico.tipos.Tipo;
import static semantico.tipos.Tipo.BOOLEANO;
import static semantico.tipos.Tipo.CADEIA;
import static semantico.tipos.Tipo.CARACTER;
import static semantico.tipos.Tipo.INTEIRO;
import static semantico.tipos.Tipo.REAL;
import semantico.identificadores.TipoIDVariavel;
import semantico.tipos.TipoOpAdd;
import semantico.tipos.TipoOpMult;
import semantico.tipos.TipoOpRel;

/**
 *
 * @author Avell G1511
 */
public class Semantico implements Constants {

    private TabelaSimbolos ts;
    private Stack<Integer> pDeslocamento;
    private Stack<Integer> pIDMetodo;
    private Stack<Boolean> pRetorno;
    private Stack<Boolean> pOpNeg;
    private Stack<Integer> pPOSID;
    private Stack<Tipo> pTipoExpr;
    private Stack<Tipo> pTipoExprSimples;
    private Stack<Tipo> pTipoVarIndexada;
    private Stack<Tipo> pTipoTermo;
    private Stack<Tipo> pTipoFator;
    private Stack<TipoOpRel> pOpRel;
    private Stack<TipoOpAdd> pOpAdd;
    private Stack<TipoOpMult> pOpMult;
    private Stack<Boolean> pOpUn;
    private Stack<Stack<IDParametro>> pIDsParametro;
    private Stack<ContextoEXPR> pContextoEXPR;
    private Stack<MecanismoDePassagem> pRef;
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
    private Tipo tipoVariavel;
    private String valConst;
    private int numElementos;
    private int NPF;
    private List<IDParametro> listIDsParametro;
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
        pDeslocamento = new Stack<>();
        pIDMetodo = new Stack<>();
        pRetorno = new Stack<>();
        pPOSID = new Stack<>();
        pTipoExpr = new Stack<>();
        pTipoExprSimples = new Stack<>();
        pTipoVarIndexada = new Stack<>();
        pContextoEXPR = new Stack<>();
        pTipoTermo = new Stack<>();
        pTipoFator = new Stack<>();
        pOpNeg = new Stack<>();
        pOpUn = new Stack<>();
        pOpRel = new Stack<>();
        pOpAdd = new Stack<>();
        pOpMult = new Stack<>();
        pIDsParametro = new Stack<>();
        pNPA = new Stack<>();
        pRef = new Stack<>();
        listIDsParametro = new ArrayList<>();
    }

    /**
     *
     * @param action
     * @param token
     * @throws SemanticError
     */
    public void executeAction(int action, Token token) throws SemanticError {
        try {
            Method execAcao = getClass().getMethod("execAcao" + action, Token.class);
            execAcao.invoke(this, token);
        } catch (InvocationTargetException e) {
            Throwable targetException = e.getTargetException();
            if (targetException instanceof SemanticError) {
                SemanticError semanticError = (SemanticError) targetException;
                throw semanticError;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro :" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param token
     */
    public void execAcao101(Token token) {
        ts = new TabelaSimbolos();
        nivelAtual = 0;
        pDeslocamento.push(0);
        ts.adicionarIdentificador(new Identificador(token.getLexeme(), Categoria.PROGRAMA, 0));
    }

    /**
     *
     * @param token
     */
    public void execAcao102(Token token) {
        contextoLID = ContextoLID.DECL;
        primeiroID = ts.getSize();
        qtdIDs = 0;
    }

    /**
     *
     * @param token
     */
    public void execAcao103(Token token) {
        ultimoID = qtdIDs + primeiroID;
    }

    /**
     *
     * @param token
     */
    public void execAcao104(Token token) {
        int i = primeiroID;
        int deslocamento = pDeslocamento.pop();
        do {
            if (Categoria.CONSTANTE == categoriaAtual) {
                Identificador identificador = ts.getIdentificador(i);
                IDConstante iDConstante = new IDConstante(identificador.getNome(), identificador.getCategoria(), identificador.getNivel());
                iDConstante.setValor(valConst);
                iDConstante.setCategoria(categoriaAtual);
                iDConstante.setTipo(tipoAtual);
                ts.adicionarIdentificadorComPosicao(i, iDConstante);
            } else if (Categoria.VARIAVEL == categoriaAtual) {
                Identificador identificador = ts.getIdentificador(i);
                IDVariavel iDVariavel = new IDVariavel(identificador.getNome(), identificador.getCategoria(), identificador.getNivel());
                if (subCategoria == SubCategoria.PRE_DEFINIDO) {
                    iDVariavel.setCategoria(categoriaAtual);
                    iDVariavel.setTipoIDVariavel(new TipoIDVariavel(getSubCategoriasVariavel(tipoAtual), null, 0));
                    iDVariavel.setDeslocamento(deslocamento);
                    deslocamento++;
                }

                if (subCategoria == SubCategoria.VETOR) {
                    iDVariavel.setCategoria(categoriaAtual);
                    iDVariavel.setTipoIDVariavel(new TipoIDVariavel(SubCategoriasVariavel.VETOR, tipoAtual, numElementos));
                    iDVariavel.setDeslocamento(deslocamento);
                    deslocamento += numElementos;
                }

                if (subCategoria == SubCategoria.CADEIA) {;
                    iDVariavel.setCategoria(categoriaAtual);
                    iDVariavel.setTipoIDVariavel(new TipoIDVariavel(getSubCategoriasVariavel(tipoAtual), null, Integer.parseInt(valConst)));
                    iDVariavel.setDeslocamento(deslocamento);
                    deslocamento++;
                }
                ts.adicionarIdentificadorComPosicao(i, iDVariavel);
            }
            i++;
        } while (i != ultimoID);
        pDeslocamento.push(deslocamento);
    }

    /**
     *
     * @param token
     */
    public void execAcao105(Token token) {
        tipoAtual = Tipo.INTEIRO;
    }

    /**
     *
     * @param token
     */
    public void execAcao106(Token token) {
        tipoAtual = Tipo.REAL;
    }

    /**
     *
     * @param token
     */
    public void execAcao107(Token token) {
        tipoAtual = Tipo.BOOLEANO;
    }

    /**
     *
     * @param token
     */
    public void execAcao108(Token token) {
        tipoAtual = Tipo.CARACTER;
    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao109(Token token) throws SemanticError {
        if (tipoConst != Tipo.INTEIRO) {
            throw new SemanticError("esperava-se uma const. inteira", token.getPosition());
        }
        if (Integer.parseInt(valConst) > 256) {
            throw new SemanticError("cadeia > que o permitido", token.getPosition());
        }
        tipoAtual = Tipo.CADEIA;
    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao110(Token token) throws SemanticError {
        if (tipoAtual == Tipo.CADEIA) {
            throw new SemanticError("Vetor do tipo cadeia não é permitido", token.getPosition());
        }
        subCategoria = SubCategoria.VETOR;
    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao111(Token token) throws SemanticError {
        if (tipoConst != Tipo.INTEIRO) {
            throw new SemanticError("A dim.deve ser uma constante inteira", token.getPosition());
        }
        numElementos = Integer.parseInt(valConst);
    }

    /**
     *
     * @param token
     */
    public void execAcao112(Token token) {
        if (tipoAtual == Tipo.CADEIA) {
            subCategoria = SubCategoria.CADEIA;
        } else {
            subCategoria = SubCategoria.PRE_DEFINIDO;
        }

    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
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
            ts.adicionarIdentificador(new IDParametro(token.getLexeme(), nivelAtual));
            incrementaNPF();
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

                    if (idVariavel.getTipoIDVariavel().getSubCategoriasVariavel() == SubCategoriasVariavel.BOOLEANO
                            || idVariavel.getTipoIDVariavel().getSubCategoriasVariavel() == SubCategoriasVariavel.VETOR) {
                        throw new SemanticError("Tipo inv. p/ leitura", token.getPosition());
                    }
                }
                if (Categoria.PARAMETRO == categoria) {
                    IDParametro iDParametro = (IDParametro) identificador;
                    if (Tipo.BOOLEANO == iDParametro.getTipo()) {
                        throw new SemanticError("Tipo inv. p/ leitura", token.getPosition());
                    }
                }
                /*Gera Cód. para leitura*/
            }
        }
    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao114(Token token) throws SemanticError {

        if (subCategoria == SubCategoria.CADEIA || subCategoria == SubCategoria.VETOR) {
            throw new SemanticError("Apenas id de tipo pré-def podem ser declarados como constante", token.getPosition());
        }
        categoriaAtual = Categoria.CONSTANTE;
    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao115(Token token) throws SemanticError {

        if (tipoConst != tipoAtual && !(tipoAtual == Tipo.REAL && tipoConst == Tipo.INTEIRO)) {
            throw new SemanticError("Tipo da constante incorreto", token.getPosition());
        }
    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao116(Token token) throws SemanticError {
        categoriaAtual = Categoria.VARIAVEL;
    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao117(Token token) throws SemanticError {

        if (ts.isExisteIdentificadorNesteNivel(nivelAtual, token.getLexeme())) {
            throw new SemanticError("Id já declarado", token.getPosition());
        }
        ts.adicionarIdentificador(new IDMetodo(token.getLexeme(), Categoria.METODO, nivelAtual));
        NPF = 0;
        pDeslocamento.push(0);
        pIDMetodo.push(ts.getSize() - 1);
        qtdIDs = 0;
        listIDsParametro = new ArrayList<>();
        incrementaNivelAtual();

    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao118(Token token) throws SemanticError {
        IDMetodo iDMetodo;
        iDMetodo = (IDMetodo) ts.getIdentificador(pIDMetodo.peek());
        iDMetodo.setNumeroParametros(NPF);
        iDMetodo.setiDParametros(listIDsParametro);
    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao119(Token token) throws SemanticError {
        IDMetodo iDMetodo;
        iDMetodo = (IDMetodo) ts.getIdentificador(pIDMetodo.peek());
        if (resultadoNulo) {
            pRetorno.push(false);
        } else {
            iDMetodo.setTipo(tpResultadoMetodo);
            pRetorno.push(true);
        }
        iDMetodo.setResultadoNulo(resultadoNulo);
    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao120(Token token) throws SemanticError {
        ts.removeNivelAtual(nivelAtual);
        decrementaNivelAtual();
        IDMetodo iDMetodo = (IDMetodo) ts.getIdentificador(pIDMetodo.peek());
        if (!temRetorno && !iDMetodo.isResultadoNulo()) {
            throw new SemanticError("metodo com tipo deve ter retorno", token.getPosition());
        }
        temRetorno = false;
        pDeslocamento.pop();
        pIDMetodo.pop();
        pRetorno.pop();
    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao121(Token token) throws SemanticError {
        contextoLID = ContextoLID.PAR_FORMAL;
        primeiroID = ts.getSize();
        qtdIDs = 0;

    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao122(Token token) throws SemanticError {
        ultimoID = primeiroID + qtdIDs;
    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
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
                int deslocamento = pDeslocamento.pop();
                Identificador identificador = ts.getIdentificador(indice);
                IDParametro iDParametro = new IDParametro(identificador.getNome(), identificador.getCategoria(), identificador.getNivel());
                iDParametro.setCategoria(Categoria.PARAMETRO);
                iDParametro.setMecanismoDePassagem(mecanismoPassagem);
                iDParametro.setTipo(tipoAtual);
                iDParametro.setDeslocamento(deslocamento);
                deslocamento++;
                pDeslocamento.push(deslocamento);
                ts.adicionarIdentificadorComPosicao(indice, iDParametro);
                listIDsParametro.add(iDParametro);
                indice++;
            } while (indice <= ultimoID - 1);
        }
    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao124(Token token) throws SemanticError {
        if (tipoAtual == Tipo.CADEIA) {
            throw new SemanticError("Métodos devem ser de tipo pré-def.", token.getPosition());
        }
        resultadoNulo = false;
        tpResultadoMetodo = tipoAtual;
    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao125(Token token) throws SemanticError {
        resultadoNulo = true;
    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao126(Token token) throws SemanticError {
        mecanismoPassagem = MecanismoDePassagem.REFERENCIA;
    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao127(Token token) throws SemanticError {
        mecanismoPassagem = MecanismoDePassagem.VALOR;
    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao128(Token token) throws SemanticError {
        if (!ts.isExisteIdentificadorNesteEscopo(nivelAtual, token.getLexeme())) {
            throw new SemanticError("Identificador não declarado", token.getPosition());
        }
        pPOSID.push(ts.getPosicaoIdentificador(nivelAtual, token.getLexeme()));

    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao129(Token token) throws SemanticError {
        Tipo tipoExpr = pTipoExpr.pop();

        if (tipoExpr != Tipo.BOOLEANO && tipoExpr != Tipo.INTEIRO) {
            throw new SemanticError("Tipo inválido da expressão", token.getPosition());
        }
        //* ação de G. código *
    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao130(Token token) throws SemanticError {
        contextoLID = ContextoLID.LEITURA;
    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao131(Token token) throws SemanticError {
        pContextoEXPR.push(ContextoEXPR.impressão);
    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao132(Token token) throws SemanticError {
        if (!pRetorno.isEmpty() && pRetorno.peek()) {
            Tipo tipoEXPR = pTipoExpr.pop();
            IDMetodo iDMetodo = (IDMetodo) ts.getIdentificador(pIDMetodo.peek());
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

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao133(Token token) throws SemanticError {
        Identificador identificador = ts.getIdentificador(pPOSID.pop());
        Categoria categoria = identificador.getCategoria();
        if (categoria == Categoria.VARIAVEL) {
            IDVariavel iDVariavel = (IDVariavel) identificador;
            if (iDVariavel.getTipoIDVariavel().getSubCategoriasVariavel() == SubCategoriasVariavel.VETOR) {
                throw new SemanticError("id. Deveria ser indexado", token.getPosition());
            } else {
                tipoLadoEsq = getTipo(iDVariavel.getTipoIDVariavel().getSubCategoriasVariavel());
            }
        } else if (categoria == Categoria.PARAMETRO) {
            IDParametro iDParametro = (IDParametro) identificador;
            tipoLadoEsq = iDParametro.getTipo();
        } else {
            throw new SemanticError("id. deveria ser var ou par", token.getPosition());
        }
    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
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

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao135(Token token) throws SemanticError {
        Identificador identificador = ts.getIdentificador(pPOSID.peek());
        Categoria categoria = identificador.getCategoria();
        if (categoria != Categoria.VARIAVEL) {
            throw new SemanticError("esperava-se uma variável", token.getPosition());
        } else {
            IDVariavel iDVariavel = (IDVariavel) identificador;
            if (iDVariavel.getTipoIDVariavel().getSubCategoriasVariavel() != SubCategoriasVariavel.VETOR
                    && iDVariavel.getTipoIDVariavel().getSubCategoriasVariavel() != SubCategoriasVariavel.CADEIA) {
                throw new SemanticError("apenas vetores e cadeias podem ser indexados", token.getPosition());
            }
            pTipoVarIndexada.push(getTipo(iDVariavel.getTipoIDVariavel().getSubCategoriasVariavel()));
        }
    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
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

    /**
     *
     * @param token
     * @throws SemanticError
     */
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

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao138(Token token) throws SemanticError {
        pNPA.push(0);
        pContextoEXPR.push(ContextoEXPR.parAtual);
    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
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

    /**
     *
     * @param token
     * @throws SemanticError
     */
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

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao141(Token token) throws SemanticError {
        if (pContextoEXPR.peek() == ContextoEXPR.parAtual) {
            int NPA = pNPA.pop();
            if (!pIDsParametro.peek().isEmpty()) {
                Tipo tipoE = pTipoExpr.pop();
                IDParametro iDParametro = pIDsParametro.peek().pop();
                MecanismoDePassagem mecanismoDePassagem = pRef.pop();
                if (tipoE != iDParametro.getTipo() && !(iDParametro.getTipo() == Tipo.REAL && tipoE == Tipo.INTEIRO) && !(iDParametro.getTipo() == Tipo.CADEIA && tipoE == Tipo.CARACTER)) {
                    throw new SemanticError("Incompatibilidade entre parâmetro atual e parâmetro formal", token.getPosition());
                }
                if (iDParametro.getMecanismoDePassagem() != mecanismoDePassagem && iDParametro.getMecanismoDePassagem() == MecanismoDePassagem.REFERENCIA) {
                    throw new SemanticError("Mecanismo de passagem incompatível. Esperava-se passagem por referência", token.getPosition());
                }
            }
            NPA++;
            pNPA.push(NPA);
        }
        if (pContextoEXPR.peek() == ContextoEXPR.impressão) {
            Tipo tipoExp = pTipoExpr.pop();
            if (tipoExp == Tipo.BOOLEANO) {
                throw new SemanticError("Tipo invalido para impressão", token.getPosition());
            }
        }

    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao142(Token token) throws SemanticError {
        pTipoExpr.push(pTipoExprSimples.pop());

    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao143(Token token) throws SemanticError {
        pOpRel.pop();
        Tipo tipoExp = pTipoExpr.pop();
        Tipo tipoExpSimples = pTipoExprSimples.pop();
        if (tipoExp != tipoExpSimples) {
            if ((tipoExp == Tipo.INTEIRO && tipoExpSimples == Tipo.REAL) || (tipoExp == Tipo.REAL && tipoExpSimples == Tipo.INTEIRO)
                    || (tipoExp == Tipo.CADEIA && tipoExpSimples == Tipo.CARACTER) || (tipoExp == Tipo.CARACTER && tipoExpSimples == Tipo.CADEIA)) {
                pTipoExpr.push(Tipo.BOOLEANO);
            } else {
                throw new SemanticError("Operandos incompatíveis", token.getPosition());
            }
        } else {
            pTipoExpr.push(Tipo.BOOLEANO);
        }
        if (!pContextoEXPR.isEmpty() && pContextoEXPR.peek() == ContextoEXPR.parAtual) {
            pRef.pop();
            pRef.push(MecanismoDePassagem.VALOR);
        }


    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao144(Token token) throws SemanticError {
        pOpRel.push(TipoOpRel.Op_Igual);
    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao145(Token token) throws SemanticError {
        pOpRel.push(TipoOpRel.Op_Menor);
    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao146(Token token) throws SemanticError {
        pOpRel.push(TipoOpRel.Op_Maior);
    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao147(Token token) throws SemanticError {
        pOpRel.push(TipoOpRel.Op_Maior_Igual);
    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao148(Token token) throws SemanticError {
        pOpRel.push(TipoOpRel.Op_Menor_Igual);
    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao149(Token token) throws SemanticError {
        pOpRel.push(TipoOpRel.Op_Diferente);
    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao150(Token token) throws SemanticError {
        pTipoExprSimples.push(pTipoTermo.pop());
    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao151(Token token) throws SemanticError {
        TipoOpAdd op = pOpAdd.peek();
        Tipo tipo = pTipoExprSimples.peek();
        if ((op == TipoOpAdd.Op_Add || op == TipoOpAdd.Op_Sub) && (tipo != Tipo.INTEIRO && tipo != Tipo.REAL)) {
            throw new SemanticError("Op. e Operando incompatíveis", token.getPosition());
        }
        if (op == TipoOpAdd.Op_Ou && tipo != Tipo.BOOLEANO) {
            throw new SemanticError("Op. e Operando incompatíveis", token.getPosition());
        }
        if (!pContextoEXPR.isEmpty() && pContextoEXPR.peek() == ContextoEXPR.parAtual) {
            pRef.pop();
            pRef.push(MecanismoDePassagem.VALOR);
        }

    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao152(Token token) throws SemanticError {
        TipoOpAdd op = pOpAdd.pop();
        Tipo tipoExpSimples = pTipoExprSimples.pop();
        Tipo tipoTermo = pTipoTermo.pop();

        if (op == TipoOpAdd.Op_Add || op == TipoOpAdd.Op_Sub) {
            if ((tipoExpSimples != Tipo.INTEIRO && tipoExpSimples != Tipo.REAL) || (tipoTermo != Tipo.INTEIRO && tipoTermo != Tipo.REAL)) {
                throw new SemanticError("Operandos incompatíveis", token.getPosition());
            } else {
                if (tipoExpSimples == Tipo.REAL || tipoTermo == Tipo.REAL) {
                    pTipoExprSimples.push(Tipo.REAL);
                } else {
                    pTipoExprSimples.push(Tipo.INTEIRO);
                }
            }
        }
        if (op == TipoOpAdd.Op_Ou) {
            if (tipoTermo != Tipo.BOOLEANO) {
                throw new SemanticError("Operandos incompatíveis", token.getPosition());
            } else {
                pTipoExprSimples.push(Tipo.BOOLEANO);
            }
        }
    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao153(Token token) throws SemanticError {
        pOpAdd.push(TipoOpAdd.Op_Add);
    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao154(Token token) throws SemanticError {
        pOpAdd.push(TipoOpAdd.Op_Sub);
    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao155(Token token) throws SemanticError {
        pOpAdd.push(TipoOpAdd.Op_Ou);
    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao156(Token token) throws SemanticError {
        pTipoTermo.push(pTipoFator.pop());
    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao157(Token token) throws SemanticError {
        TipoOpMult op = pOpMult.peek();
        Tipo tipo = pTipoTermo.peek();
        if ((op == TipoOpMult.Op_Mult || op == TipoOpMult.Op_Divisao) && (tipo != Tipo.INTEIRO && tipo != Tipo.REAL)) {
            throw new SemanticError("Op. e Operando incompatíveis", token.getPosition());
        }
        if (op == TipoOpMult.Op_DIV && tipo != Tipo.INTEIRO) {
            throw new SemanticError("Op. e Operando incompatíveis", token.getPosition());
        }
        if (op == TipoOpMult.Op_E && tipo != Tipo.BOOLEANO) {
            throw new SemanticError("Op. e Operando incompatíveis", token.getPosition());
        }

        if (!pContextoEXPR.isEmpty() && pContextoEXPR.peek() == ContextoEXPR.parAtual) {
            pRef.pop();
            pRef.push(MecanismoDePassagem.VALOR);
        }

    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao158(Token token) throws SemanticError {
        Tipo tipoTermo = pTipoTermo.pop();
        Tipo tipoFator = pTipoFator.pop();
        TipoOpMult op = pOpMult.pop();
        if (op == TipoOpMult.Op_Mult || op == TipoOpMult.Op_Divisao) {
            if ((tipoTermo != Tipo.INTEIRO && tipoTermo != Tipo.REAL) || (tipoFator != Tipo.INTEIRO && tipoFator != Tipo.REAL)) {
                throw new SemanticError("Operandos incompatíveis", token.getPosition());
            } else {
                if ((tipoTermo == Tipo.REAL || tipoFator == Tipo.REAL) || op == TipoOpMult.Op_Divisao) {
                    pTipoTermo.push(Tipo.REAL);
                } else {
                    pTipoTermo.push(Tipo.INTEIRO);
                }
            }
        }
        if (op == TipoOpMult.Op_DIV) {
            if (tipoTermo != Tipo.INTEIRO || tipoFator != Tipo.INTEIRO) {
                throw new SemanticError("Operandos incompatíveis", token.getPosition());
            } else {
                pTipoTermo.push(Tipo.INTEIRO);
            }
        }
        if (op == TipoOpMult.Op_E) {
            if (tipoTermo != Tipo.BOOLEANO || tipoFator != Tipo.BOOLEANO) {
                throw new SemanticError("Operandos incompatíveis", token.getPosition());
            } else {
                pTipoTermo.push(Tipo.BOOLEANO);
            }
        }

    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao159(Token token) throws SemanticError {
        pOpMult.push(TipoOpMult.Op_Mult);
    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao160(Token token) throws SemanticError {
        pOpMult.push(TipoOpMult.Op_Divisao);
    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao161(Token token) throws SemanticError {
        pOpMult.push(TipoOpMult.Op_E);
    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao162(Token token) throws SemanticError {
        pOpMult.push(TipoOpMult.Op_DIV);
    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao163(Token token) throws SemanticError {
        if (!pOpNeg.isEmpty() && pOpNeg.peek()) {
            throw new SemanticError("Op. 'não' repetido – não pode!", token.getPosition());
        } else {
            pOpNeg.push(true);
            if (!pContextoEXPR.isEmpty() && pContextoEXPR.peek() == ContextoEXPR.parAtual) {
                pRef.push(MecanismoDePassagem.VALOR);
            }
        }
    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao164(Token token) throws SemanticError {
        if (pTipoFator.peek() != Tipo.BOOLEANO) {
            throw new SemanticError("Op. 'não' exige operando bool.", token.getPosition());
        }
        pOpNeg.pop();


    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao165(Token token) throws SemanticError {
        if (!pOpUn.isEmpty() && pOpUn.peek()) {
            throw new SemanticError("Op. 'unário' repetido", token.getPosition());
        } else {
            pOpUn.push(true);
            if (!pContextoEXPR.isEmpty() && pContextoEXPR.peek() == ContextoEXPR.parAtual) {
                pRef.push(MecanismoDePassagem.VALOR);
            }
        }
    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao166(Token token) throws SemanticError {
        Tipo tipo = pTipoFator.peek();
        if (tipo != Tipo.INTEIRO && tipo != Tipo.REAL) {
            throw new SemanticError("Op. unário exige operando num.", token.getPosition());
        }
        pOpUn.pop();
    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao167(Token token) throws SemanticError {
        pOpNeg.push(false);
        pOpUn.push(false);
    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao168(Token token) throws SemanticError {
        pTipoFator.push(pTipoExpr.pop());
        pOpNeg.pop();
        pOpUn.pop();
        if (!pContextoEXPR.isEmpty() && pContextoEXPR.peek() == ContextoEXPR.parAtual && ((!pOpNeg.isEmpty() && pOpNeg.peek()) || (!pOpUn.isEmpty() && pOpUn.peek()))) {
            pRef.pop();
            pRef.push(MecanismoDePassagem.VALOR);
        }
    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao169(Token token) throws SemanticError {
        pTipoFator.push(tipoVariavel);

    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao170(Token token) throws SemanticError {
        pTipoFator.push(tipoConst);
        if (!pContextoEXPR.isEmpty() && pContextoEXPR.peek() == ContextoEXPR.parAtual && (!(!pOpNeg.isEmpty() && pOpNeg.peek()) && !(!pOpUn.isEmpty() && pOpUn.peek()))) {
            pRef.push(MecanismoDePassagem.VALOR);
        }
    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao171(Token token) throws SemanticError {
        Identificador identificador = ts.getIdentificador(pPOSID.peek());
        if (identificador.getCategoria() != Categoria.METODO) {
            throw new SemanticError("id deveria ser um método", token.getPosition());
        } else {
            IDMetodo metodo = (IDMetodo) identificador;
            if (metodo.isResultadoNulo()) {
                throw new SemanticError("esperava-se mét. com tipo", token.getPosition());
            }
            pIDsParametro.push(metodo.getIDsParametro());
        }
        pNPA.push(0);
        pContextoEXPR.push(ContextoEXPR.parAtual);
    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao172(Token token) throws SemanticError {
        int NPA = pNPA.pop();
        Identificador identificador = ts.getIdentificador(pPOSID.pop());
        IDMetodo metodo = (IDMetodo) identificador;
        if (NPA == metodo.getNumeroParametros()) {
            tipoVariavel = metodo.getTipo();
        } else {
            throw new SemanticError("Erro na quant. de parâmetros", token.getPosition());
        }
        pContextoEXPR.push(ContextoEXPR.parAtual);

        if (!pContextoEXPR.isEmpty() && pContextoEXPR.peek() == ContextoEXPR.parAtual && (!(!pOpNeg.isEmpty() && pOpNeg.peek()) && !(!pOpUn.isEmpty() && pOpUn.peek()))) {
            pRef.push(MecanismoDePassagem.VALOR);
        }
        pIDsParametro.pop();
    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao173(Token token) throws SemanticError {
        if (pTipoExpr.pop() != Tipo.INTEIRO) {
            throw new SemanticError("índice deveria ser inteiro", token.getPosition());
        } else {
            if (pTipoVarIndexada.pop() == Tipo.CADEIA) {
                tipoVariavel = Tipo.CARACTER;
                pPOSID.pop();
            } else {
                IDVariavel variavel = (IDVariavel) ts.getIdentificador(pPOSID.pop());
                tipoVariavel = variavel.getTipoIDVariavel().getTpElementos();
            }
        }

        if (!pContextoEXPR.isEmpty() && pContextoEXPR.peek() == ContextoEXPR.parAtual && (!(!pOpNeg.isEmpty() && pOpNeg.peek()) && !(!pOpUn.isEmpty() && pOpUn.peek()))) {
            pRef.push(MecanismoDePassagem.REFERENCIA);
        }
    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao174(Token token) throws SemanticError {
        Identificador identificador = ts.getIdentificador(pPOSID.pop());
        if (identificador.getCategoria() == Categoria.PARAMETRO || identificador.getCategoria() == Categoria.VARIAVEL) {
            if (identificador.getCategoria() == Categoria.VARIAVEL) {
                IDVariavel variavel = (IDVariavel) identificador;
                if (variavel.getTipoIDVariavel().getSubCategoriasVariavel() == SubCategoriasVariavel.VETOR) {
                    throw new SemanticError("vetor deve ser indexado", token.getPosition());
                } else {
                    tipoVariavel = getTipo(variavel.getTipoIDVariavel().getSubCategoriasVariavel());
                }
            } else {
                IDParametro parametro = (IDParametro) identificador;
                tipoVariavel = parametro.getTipo();
            }

            if (!pContextoEXPR.isEmpty() && pContextoEXPR.peek() == ContextoEXPR.parAtual && (!(!pOpNeg.isEmpty() && pOpNeg.peek()) && !(!pOpUn.isEmpty() && pOpUn.peek()))) {
                pRef.push(MecanismoDePassagem.REFERENCIA);
            }

        } else {
            if (identificador.getCategoria() == Categoria.METODO) {
                IDMetodo metodo = (IDMetodo) identificador;
                if (metodo.isResultadoNulo()) {
                    throw new SemanticError("Esperava-se método com tipo", token.getPosition());
                } else {
                    if (metodo.getNumeroParametros() != 0) {
                        throw new SemanticError("Erro na quant. de parâmetros", token.getPosition());
                    } else {
                        tipoVariavel = metodo.getTipo();
                    }

                    if (!pContextoEXPR.isEmpty() && pContextoEXPR.peek() == ContextoEXPR.parAtual && (!(!pOpNeg.isEmpty() && pOpNeg.peek()) && !(!pOpUn.isEmpty() && pOpUn.peek()))) {
                        pRef.push(MecanismoDePassagem.VALOR);
                    }
                }

            } else {
                if (identificador.getCategoria() == Categoria.CONSTANTE) {
                    IDConstante iDConstante = (IDConstante) identificador;
                    tipoVariavel = iDConstante.getTipo();
                    if (!pContextoEXPR.isEmpty() && pContextoEXPR.peek() == ContextoEXPR.parAtual && (!(!pOpNeg.isEmpty() && pOpNeg.peek()) && !(!pOpUn.isEmpty() && pOpUn.peek()))) {
                        pRef.push(MecanismoDePassagem.VALOR);
                    }
                } else {
                    throw new SemanticError("esperava-se var,id-método ou constante", token.getPosition());
                }
            }
        }
    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao175(Token token) throws SemanticError {
        Identificador identificador = ts.getIdentificadorNoNivel(nivelAtual, token.getLexeme());
        if (identificador == null) {
            throw new SemanticError("Id não declarado", token.getPosition());
        } else {
            if (identificador.getCategoria() != Categoria.CONSTANTE) {
                throw new SemanticError("id de Constante esperado", token.getPosition());
            } else {
                IDConstante constante = (IDConstante) identificador;
                tipoConst = constante.getTipo();
                valConst = constante.getValor();
            }
        }
    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao176(Token token) throws SemanticError {
        tipoConst = Tipo.INTEIRO;
        valConst = token.getLexeme();
    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao177(Token token) throws SemanticError {
        tipoConst = Tipo.REAL;
        valConst = token.getLexeme();
    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao178(Token token) throws SemanticError {
        tipoConst = Tipo.BOOLEANO;
        valConst = token.getLexeme();
    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao179(Token token) throws SemanticError {
        tipoConst = Tipo.BOOLEANO;
        valConst = token.getLexeme();
    }

    /**
     *
     * @param token
     * @throws SemanticError
     */
    public void execAcao180(Token token) throws SemanticError {
        if (token.getLexeme().length() - 2 == 1) {
            tipoConst = Tipo.CARACTER;
        } else {
            tipoConst = Tipo.CADEIA;
        }
        valConst = token.getLexeme();
    }

    /**
     *
     * @param subCategoriasVariavel
     * @return
     */
    private static Tipo getTipo(SubCategoriasVariavel subCategoriasVariavel) {
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
                return Tipo.BOOLEANO;
        }
    }

    /**
     *
     * @param tipo
     * @return
     */
    private static SubCategoriasVariavel getSubCategoriasVariavel(Tipo tipo) {
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
                return SubCategoriasVariavel.BOOLEANO;
        }

    }
}
