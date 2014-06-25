package gals;

public interface ParserConstants
{
    int START_SYMBOL = 58;

    int FIRST_NON_TERMINAL    = 58;
    int FIRST_SEMANTIC_ACTION = 92;

    int[][] PARSER_TABLE =
    {
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  1, -1, -1, -1,  1,  1, -1, -1, -1,  1,  1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  1, -1, -1,  1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 27, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, 77, 78, 78, 78, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 78, 78, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, 83, 79, 80, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 81, 82, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, 30, -1, -1, -1, 37, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 31, 37, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 32, -1, 37, 34, 35, -1, -1, -1, -1, -1, -1, -1, -1, -1, 33, -1, -1, -1, 36, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  3, -1, -1, -1,  2,  2, -1, -1, -1,  2,  2, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  2, -1, -1,  3, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 17, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 16, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 18, -1, -1, -1, -1 },
        { -1, 10, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  9, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, 47, 47, 47, 47, -1, -1, -1, -1, -1, -1, 47, -1, -1, -1, -1, 47, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 47, 47, 47, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, 56, 56, 56, 56, -1, -1, -1, -1, -1, -1, 56, -1, -1, -1, -1, 56, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 56, 56, 56, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, 72, 73, 73, 73, -1, -1, -1, -1, -1, -1, 71, -1, -1, -1, -1, 70, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 69, 73, 73, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, 15, -1, -1, -1, -1, 14, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, 11, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, 44, 44, 44, 44, -1, -1, -1, -1, -1, -1, 44, -1, -1, -1, -1, 44, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 44, 44, 44, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 25, 26, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 59, 60, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 61, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 65, 66, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 68, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 67 },
        { -1, -1, -1, -1, -1, -1, -1, -1, 52, 51, 50, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 55, 54, 53, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, 20, -1, -1, -1, -1, -1, 19, -1, -1, -1, -1, -1, -1, -1, 20, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, 13, 12, -1, -1, -1, 13, -1, 13, -1, -1, -1, -1, -1, -1, 13, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, 45, -1, -1, -1, -1, -1, 46, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, 49, 49, -1, 48, 48, 48, -1, 49, -1, 49, -1, -1, -1, -1, -1, -1, -1, 48, 48, 48, -1, 49, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 49, 49, -1, -1, -1, -1, -1, -1, -1, -1, 49, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, 28, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 29, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, 58, 58, -1, 58, 58, 58, -1, 58, -1, 58, 57, 57, -1, -1, -1, -1, -1, 58, 58, 58, -1, 58, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 58, 58, -1, -1, 57, -1, -1, -1, -1, -1, 58, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, 21, -1, -1, -1, -1, -1, -1, 22, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, 43, -1, -1, -1, -1, -1, 42, -1, 41, -1, -1, -1, -1, -1, -1, 40, -1, -1, -1, -1, -1, 43, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 43, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, 64, 64, -1, 64, 64, 64, -1, 64, -1, 64, 64, 64, 63, 63, -1, -1, -1, 64, 64, 64, -1, 64, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 64, 64, -1, -1, 64, 63, -1, -1, -1, -1, 64, -1, -1, -1, -1, -1, -1, -1, 63 },
        { -1, -1, -1, -1, -1, 76, 76, -1, 76, 76, 76, 74, 76, 75, 76, 76, 76, 76, 76, -1, -1, -1, 76, 76, 76, -1, 76, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 76, 76, -1, -1, 76, 76, -1, -1, -1, -1, 76, -1, -1, -1, -1, -1, -1, -1, 76 },
        { -1, -1, -1, -1, -1, 39, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 39, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 38, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  7,  8, -1, -1, -1,  4,  6, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  5, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, 24, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 23, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, 62, 62, 62, 62, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, -1, 62, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, 62, 62, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 }
    };

    int[][] PRODUCTIONS = 
    {
        { 28,  2,  6, 58,  8 },
        { 64, 65, 59 },
        { 89, 67, 72, 71,  6, 64 },
        {  0 },
        { 35 },
        { 50 },
        { 36 },
        { 30 },
        { 31, 14, 60, 15 },
        { 14, 60, 15 },
        {  0 },
        {  2, 79 },
        {  7, 72 },
        {  0 },
        { 11, 60 },
        {  0 },
        { 66,  6, 65 },
        {  0 },
        { 53,  2, 78, 90,  6, 58 },
        { 12, 74, 72, 20, 89, 84, 13 },
        {  0 },
        {  6, 74, 72, 20, 89, 84 },
        {  0 },
        { 20, 89 },
        {  0 },
        { 54 },
        { 55 },
        { 26, 62, 82, 27 },
        {  6, 62, 82 },
        {  0 },
        {  2, 85 },
        { 59 },
        { 38, 68, 39, 62, 88 },
        { 52, 68, 49, 62 },
        { 41, 12, 72, 13 },
        { 42, 12, 73, 13 },
        { 56, 68 },
        {  0 },
        { 40, 62 },
        {  0 },
        { 21, 68 },
        { 14, 68, 15, 21, 68 },
        { 12, 73, 13 },
        {  0 },
        { 68, 80 },
        {  7, 68, 80 },
        {  0 },
        { 69, 81 },
        { 77, 69 },
        {  0 },
        { 11 },
        { 10 },
        {  9 },
        { 25 },
        { 24 },
        { 23 },
        { 91, 83 },
        { 75, 91, 83 },
        {  0 },
        { 16 },
        { 17 },
        { 43 },
        { 70, 86 },
        { 76, 70, 86 },
        {  0 },
        { 18 },
        { 19 },
        { 57 },
        { 44 },
        { 45, 70 },
        { 17, 70 },
        { 12, 68, 13 },
        {  2, 87 },
        { 61 },
        { 12, 68, 80, 13 },
        { 14, 68, 15 },
        {  0 },
        {  2 },
        { 61 },
        {  4 },
        {  5 },
        { 46 },
        { 47 },
        {  3 }
    };

    String[] PARSER_ERROR =
    {
        "",
        "Era esperado fim de programa",
        "Era esperado id",
        "Era esperado literal",
        "Era esperado num_int",
        "Era esperado num_real",
        "Era esperado \";\"",
        "Era esperado \",\"",
        "Era esperado \".\"",
        "Era esperado \">\"",
        "Era esperado \"<\"",
        "Era esperado \"=\"",
        "Era esperado \"(\"",
        "Era esperado \")\"",
        "Era esperado \"[\"",
        "Era esperado \"]\"",
        "Era esperado \"+\"",
        "Era esperado \"-\"",
        "Era esperado \"*\"",
        "Era esperado \"/\"",
        "Era esperado \":\"",
        "Era esperado \":=\"",
        "Era esperado \"..\"",
        "Era esperado \"<>\"",
        "Era esperado \"<=\"",
        "Era esperado \">=\"",
        "Era esperado \"{\"",
        "Era esperado \"}\"",
        "Era esperado programa",
        "Era esperado var",
        "Era esperado caracter",
        "Era esperado cadeia",
        "Era esperado procedimento",
        "Era esperado inicio",
        "Era esperado fim",
        "Era esperado inteiro",
        "Era esperado booleano",
        "Era esperado funcao",
        "Era esperado se",
        "Era esperado entao",
        "Era esperado senao",
        "Era esperado leia",
        "Era esperado escreva",
        "Era esperado ou",
        "Era esperado e",
        "Era esperado nao",
        "Era esperado falso",
        "Era esperado verdadeiro",
        "Era esperado de",
        "Era esperado faca",
        "Era esperado real",
        "Era esperado vetor",
        "Era esperado enquanto",
        "Era esperado metodo",
        "Era esperado ref",
        "Era esperado val",
        "Era esperado retorne",
        "Era esperado div",
        "<bloco> inv�lido",
        "<com_composto> inv�lido",
        "<constante> inv�lido",
        "<constante_explicita> inv�lido",
        "<comando> inv�lido",
        "<programa> inv�lido",
        "<dcl_var_const> inv�lido",
        "<dcl_metodos> inv�lido",
        "<dcl_metodo> inv�lido",
        "<dimensao> inv�lido",
        "<expressao> inv�lido",
        "<expsimp> inv�lido",
        "<fator> inv�lido",
        "<fator_const> inv�lido",
        "<lid> inv�lido",
        "<lista_expr> inv�lido",
        "<mp_par> inv�lido",
        "<op_add> inv�lido",
        "<op_mult> inv�lido",
        "<oprel> inv�lido",
        "<par_formais> inv�lido",
        "<rep_lid> inv�lido",
        "<rep_lexpr> inv�lido",
        "<resto_expressao> inv�lido",
        "<replistacomando> inv�lido",
        "<rep_expsimp> inv�lido",
        "<rep_par> inv�lido",
        "<rcomid> inv�lido",
        "<rep_termo> inv�lido",
        "<rvar> inv�lido",
        "<senaoparte> inv�lido",
        "<tipo> inv�lido",
        "<tipo_metodo> inv�lido",
        "<termo> inv�lido"
    };
}
