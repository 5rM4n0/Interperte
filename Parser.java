/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.interprete;

import java.util.List;

public class Parser {

    private final List<Token> tokens;

    private final Token y = new Token(TipoToken.Y, "y");
    private final Token clase = new Token(TipoToken.CLASE, "clase");
    private final Token cadena = new Token(TipoToken.CADENA, "cadena");
    private final Token ademas = new Token(TipoToken.ADEMAS, "ademas");
    private final Token falso = new Token(TipoToken.FALSO, "falso");
    private final Token para = new Token(TipoToken.PARA, "para");
    private final Token fun = new Token(TipoToken.FUN, "fun");
    private final Token si = new Token(TipoToken.SI, "si");
    private final Token nulo = new Token(TipoToken.NULO, "nulo");
    private final Token o = new Token(TipoToken.O, "o");
    private final Token imprimir = new Token(TipoToken.IMPRIMIR, "imprimir");
    private final Token retornar = new Token(TipoToken.RETORNAR, "retornar");
    private final Token supers = new Token(TipoToken.SUPERS, "supers");
    private final Token este = new Token(TipoToken.ESTE, "este");
    private final Token verdadero = new Token(TipoToken.VERDADERO, "verdadero");
    private final Token var = new Token(TipoToken.VAR, "var");
    private final Token mientras = new Token(TipoToken.MIENTRAS, "mientras");
    private final Token pare_der = new Token(TipoToken.PARE_DER, ")");
    private final Token pare_iz = new Token(TipoToken.PARE_IZ, "(");
    private final Token cor_der = new Token(TipoToken.COR_DER, "]");
    private final Token cor_iz = new Token(TipoToken.COR_IZ, "[");
    private final Token coma = new Token(TipoToken.COMA, ",");
    private final Token punto = new Token(TipoToken.PUNTO, ".");
    private final Token pun_y_com = new Token(TipoToken.PUN_Y_COM, ";");
    private final Token menos = new Token(TipoToken.MENOS, "-");
    private final Token mas = new Token(TipoToken.MAS, "+");
    private final Token por = new Token(TipoToken.POR, "*");
    private final Token entre = new Token(TipoToken.ENTRE, "/");
    private final Token excla = new Token(TipoToken.EXCLA, "!");
    private final Token diferente = new Token(TipoToken.DIFERENTE, "!=");
    private final Token asignacion = new Token(TipoToken.ASIGNACION, "=");
    private final Token igual = new Token(TipoToken.IGUAL, "==");
    private final Token menor = new Token(TipoToken.MENOR, "<");
    private final Token menor_o_igual = new Token(TipoToken.MENOR_O_IGUAL, "<=");
    private final Token mayor = new Token(TipoToken.MAYOR, ">");
    private final Token mayor_o_igual = new Token(TipoToken.MAYOR_O_IGUAL, ">=");
    private final Token identificador = new Token(TipoToken.IDENTIFICADOR, "");
    private final Token numero = new Token(TipoToken.NUMERO, "");
    private final Token simbolo = new Token(TipoToken.SIMBOLO, "");
    private final Token palabra_reservada = new Token(TipoToken.PALABRARESERVADA, "");
    private final Token espacio = new Token(TipoToken.SPACE, "");

    private final Token finCadena = new Token(TipoToken.EOF, "");

    private int i = 0;
    private boolean hayErrores = false;

    private Token preanalisis;

    public Parser(List<Token> tokens){
        
        this.tokens = tokens;
    }

    public void parse() {
        i=0;
        preanalisis = tokens.get(i);
        DECLARATION();
        if(!hayErrores && !preanalisis.equals(finCadena)){
            System.out.println("Error en la posición " + preanalisis.posicion + ". No se esperaba el token " + preanalisis.tipo);
        }
        else if(!hayErrores && preanalisis.equals(finCadena)){
            System.out.println("Terminado con exito");
        }
    }

    private void DECLARATION() {
        int caso=-1;
        if (preanalisis.tipo.equals(TipoToken.CLASE)) {
    caso = 0;           
} else if (preanalisis.tipo.equals(TipoToken.FUN)) {
    caso = 1;           
} else if (preanalisis.tipo.equals(TipoToken.VAR)) {
    caso = 2;            
} else if (preanalisis.tipo.equals(TipoToken.EXCLA) || 
           preanalisis.tipo.equals(TipoToken.MENOS) || 
           preanalisis.tipo.equals(TipoToken.NUMERO) || 
           preanalisis.tipo.equals(TipoToken.CADENA) || 
           preanalisis.tipo.equals(TipoToken.IDENTIFICADOR) || 
           preanalisis.tipo.equals(TipoToken.IMPRIMIR) ||
           preanalisis.tipo.equals(TipoToken.VERDADERO) || 
           preanalisis.tipo.equals(TipoToken.FALSO) || 
           preanalisis.tipo.equals(TipoToken.RETORNAR) ||
           preanalisis.tipo.equals(TipoToken.NULO) || 
           preanalisis.tipo.equals(TipoToken.ESTE) ||
           preanalisis.tipo.equals(TipoToken.PARE_IZ) ||
           preanalisis.tipo.equals(TipoToken.SUPERS) ||
           preanalisis.tipo.equals(TipoToken.MIENTRAS) || 
           preanalisis.tipo.equals(TipoToken.SI) || 
           preanalisis.tipo.equals(TipoToken.PARA) || 
           preanalisis.tipo.equals(TipoToken.COR_IZ)) {
    caso = 3;
} else if (preanalisis.tipo.equals(TipoToken.EOF)) {
    caso = 4;
}else{
    
}
        switch(caso){
            case 0:
                CLASS_DECL();
                DECLARATION();
                break;
            case 1:
                FUN_DECL();
                DECLARATION();
                break;
            case 2:
                VAR_DECL();
                DECLARATION();
                break;
            case 3:
                STATEMENT();
                DECLARATION();
                break;
            case 4:
                coincidir(finCadena);
                break;
        }
        
    }
    
    private void CLASS_DECL(){
        coincidir(clase);
        coincidir(identificador);
        CLASS_INHER();
        coincidir(cor_iz);
        FUNCTIONS();
        coincidir(cor_der);
    }
    private void CLASS_INHER(){
        if (preanalisis != null && preanalisis.tipo.equals(TipoToken.MENOR)) {
            coincidir(menor);
            coincidir(identificador);
        } else {
            // Producción de cadena vacía (Ɛ)
            // No haces nada en este caso, simplemente sales de la función
        }
    }
    private void FUN_DECL(){
        coincidir(fun);
        FUNCTION();
    }
    private void VAR_DECL(){
        coincidir(var);
        coincidir(identificador);
        VAR_INIT();
    }
    private void VAR_INIT(){
        if (preanalisis != null && preanalisis.tipo.equals(TipoToken.ASIGNACION)) {
            coincidir(asignacion);
        EXPRESSION();
    } else {
        // Producción de cadena vacía (Ɛ)
        // No haces nada en este caso, simplemente sales de la función
    }
        
    }
    private void STATEMENT(){
        int caso=-1;
        if(preanalisis.tipo.equals(TipoToken.EXCLA) || 
        preanalisis.tipo.equals(TipoToken.MENOS) || 
        preanalisis.tipo.equals(TipoToken.NUMERO) || 
        preanalisis.tipo.equals(TipoToken.CADENA) || 
        preanalisis.tipo.equals(TipoToken.IDENTIFICADOR) ||  
        preanalisis.tipo.equals(TipoToken.VERDADERO) || 
        preanalisis.tipo.equals(TipoToken.FALSO) || 
        preanalisis.tipo.equals(TipoToken.NULO) || 
        preanalisis.tipo.equals(TipoToken.ESTE) ||
        preanalisis.tipo.equals(TipoToken.PARE_IZ) ||
        preanalisis.tipo.equals(TipoToken.SUPERS)){
            caso=0;
        }else if (preanalisis.tipo.equals(TipoToken.PARA)) {
    caso = 1;
} else if (preanalisis.tipo.equals(TipoToken.SI)) {
    caso = 2;
} else if (preanalisis.tipo.equals(TipoToken.IMPRIMIR)) {
    caso = 3;
} else if (preanalisis.tipo.equals(TipoToken.RETORNAR)) {
    caso = 4;
} else if (preanalisis.tipo.equals(TipoToken.MIENTRAS)) {
    caso = 5;
} else if (preanalisis.tipo.equals(TipoToken.COR_IZ)) {
    caso = 6;
}
        else{
    hayErrores=true;
}
        switch(caso){
            case 0:
                EXPR_STMT();
                break;
            case 1:
                FOR_STMT();
                break;
            case 2:
                IF_STMT();
                break;
            case 3:
                PRINT_STMT();
                break;
            case 4:
                RETURN_STMT();
                break;
            case 5:
                WHILE_STMT();
                break;
            case 6:
                BLOCK();
                break;
        }
    }
    private void EXPR_STMT(){
        EXPRESSION();
    }
    private void FOR_STMT(){
        coincidir(para);
        coincidir(pare_iz);
        FOR_STMT_1();
        FOR_STMT_2();
        FOR_STMT_3();
        coincidir(pare_der);
        STATEMENT();
    }
    private void FOR_STMT_1(){
        int caso=-1;
        if (preanalisis.tipo.equals(TipoToken.VAR)) {
    caso = 0;
} else if (preanalisis.tipo.equals(TipoToken.IDENTIFICADOR) ||
        preanalisis.tipo.equals(TipoToken.IMPRIMIR) ||
        preanalisis.tipo.equals(TipoToken.RETORNAR) ||
        preanalisis.tipo.equals(TipoToken.MIENTRAS) ||
        preanalisis.tipo.equals(TipoToken.SI) ||
        preanalisis.tipo.equals(TipoToken.PARA) ||
        preanalisis.tipo.equals(TipoToken.COR_IZ)) {
    caso = 1;
} else if (preanalisis.tipo.equals(TipoToken.PUN_Y_COM)) {
    caso = 2;
}
        else{
    hayErrores=true;
}
        switch(caso){
            case 0:
                VAR_DECL();
                break;
            case 1:
                EXPR_STMT();
                break;
            case 2:
                coincidir(pun_y_com);
                break;
        }
    }
    private void FOR_STMT_2(){
        int caso=-1;
        if (preanalisis.tipo.equals(TipoToken.IDENTIFICADOR) ||
        preanalisis.tipo.equals(TipoToken.NUMERO) ||
        preanalisis.tipo.equals(TipoToken.CADENA) ||
        preanalisis.tipo.equals(TipoToken.VERDADERO) ||
        preanalisis.tipo.equals(TipoToken.FALSO) ||
        preanalisis.tipo.equals(TipoToken.NULO) ||
        preanalisis.tipo.equals(TipoToken.ESTE) ||
        preanalisis.tipo.equals(TipoToken.PARE_IZ) ||
        preanalisis.tipo.equals(TipoToken.SUPERS) ||
        preanalisis.tipo.equals(TipoToken.MENOS)) {
    caso = 0;
} else if (preanalisis.tipo.equals(TipoToken.PUN_Y_COM)) {
    caso = 1;
}
        else{
    hayErrores=true;
}

        switch(caso){
            case 0:
                EXPRESSION();
                break;
            case 1:
                coincidir(pun_y_com);
                break;
        }
    }
    private void FOR_STMT_3(){
        if (preanalisis != null && (preanalisis.tipo.equals(TipoToken.NUMERO) || 
        preanalisis.tipo.equals(TipoToken.CADENA) || 
        preanalisis.tipo.equals(TipoToken.IDENTIFICADOR) ||  
        preanalisis.tipo.equals(TipoToken.VERDADERO) || 
        preanalisis.tipo.equals(TipoToken.FALSO) || 
        preanalisis.tipo.equals(TipoToken.NULO) || 
        preanalisis.tipo.equals(TipoToken.ESTE) ||
        preanalisis.tipo.equals(TipoToken.PARE_IZ) ||
        preanalisis.tipo.equals(TipoToken.SUPERS))) {
            EXPRESSION();
        } else {
            // Producción de cadena vacía (Ɛ)
            // No haces nada en este caso, simplemente sales de la función
        }
        
    }
    private void IF_STMT(){
        coincidir(si);
        coincidir(pare_iz);
        EXPRESSION();
        coincidir(pare_der);
        STATEMENT();
        ELSE_STATEMENT();

    }
    private void ELSE_STATEMENT(){
        if (preanalisis != null && preanalisis.tipo.equals(TipoToken.ADEMAS)) {
            coincidir(ademas);
        STATEMENT();
    } else {
        // Producción de cadena vacía (Ɛ)
        // No haces nada en este caso, simplemente sales de la función
    }
        
    }
    private void PRINT_STMT(){
        coincidir(imprimir);
        EXPRESSION();
        coincidir(pun_y_com);
    }
    private void RETURN_STMT(){
        coincidir(retornar);
        RETURN_EXP_OPC();
        coincidir(pun_y_com);
    }
    private void RETURN_EXP_OPC(){
        if (preanalisis != null && (preanalisis.tipo.equals(TipoToken.NUMERO) || 
        preanalisis.tipo.equals(TipoToken.CADENA) || 
        preanalisis.tipo.equals(TipoToken.IDENTIFICADOR) ||  
        preanalisis.tipo.equals(TipoToken.VERDADERO) || 
        preanalisis.tipo.equals(TipoToken.FALSO) || 
        preanalisis.tipo.equals(TipoToken.NULO) || 
        preanalisis.tipo.equals(TipoToken.ESTE) ||
        preanalisis.tipo.equals(TipoToken.PARE_IZ) ||
        preanalisis.tipo.equals(TipoToken.SUPERS))) {
            EXPRESSION();
        } else {
            // Producción de cadena vacía (Ɛ)
            // No haces nada en este caso, simplemente sales de la función
        }
    }
    private void WHILE_STMT(){
        coincidir(mientras);
        coincidir(pare_iz);
        EXPRESSION();
        coincidir(pare_der);
        STATEMENT();
    }
    private void BLOCK(){
        coincidir(cor_iz);
        BLOCK_DECL();
        coincidir(cor_der);
    }
    private void BLOCK_DECL(){
        if (preanalisis != null && (preanalisis.tipo.equals(TipoToken.CLASE) || 
        preanalisis.tipo.equals(TipoToken.FUN) || 
        preanalisis.tipo.equals(TipoToken.VAR) ||  
        preanalisis.tipo.equals(TipoToken.SI) || 
        preanalisis.tipo.equals(TipoToken.IMPRIMIR) || 
        preanalisis.tipo.equals(TipoToken.RETORNAR) || 
        preanalisis.tipo.equals(TipoToken.MIENTRAS) ||
        preanalisis.tipo.equals(TipoToken.IDENTIFICADOR))) {
            DECLARATION();
            BLOCK_DECL();
        } else {
            // Producción de cadena vacía (Ɛ)
            // No haces nada en este caso, simplemente sales de la función
        }
        
    }
    private void EXPRESSION(){
        ASSIGNMENT();
    }
    private void ASSIGNMENT(){
        int caso=-1;
        if (preanalisis.tipo.equals(TipoToken.NUMERO) || 
        preanalisis.tipo.equals(TipoToken.CADENA) || 
        preanalisis.tipo.equals(TipoToken.IDENTIFICADOR) ||  
        preanalisis.tipo.equals(TipoToken.VERDADERO) || 
        preanalisis.tipo.equals(TipoToken.FALSO) || 
        preanalisis.tipo.equals(TipoToken.NULO) || 
        preanalisis.tipo.equals(TipoToken.ESTE) ||
        preanalisis.tipo.equals(TipoToken.PARE_IZ) ||
        preanalisis.tipo.equals(TipoToken.SUPERS)){
    caso = 0;
} else if (preanalisis.tipo.equals(TipoToken.EXCLA) || 
        preanalisis.tipo.equals(TipoToken.MENOS)){
    caso = 1;
}
        else{
    hayErrores=true;
}

        switch (caso) {
            case 0:
                CALL_OPC();
                coincidir(identificador);
                coincidir(asignacion);
                ASSIGNMENT();
                break;
            case 1:
                LOGIC_OR();
                break;
        }
    }
    private void LOGIC_OR(){
        LOGIC_AND();
        LOGIC_OR_2();
    }
    private void LOGIC_OR_2(){
        if (preanalisis != null && preanalisis.tipo.equals(TipoToken.O)) {
            coincidir(o);
        LOGIC_AND();
        LOGIC_OR_2();
        } else {
            // Producción de cadena vacía (Ɛ)
            // No haces nada en este caso, simplemente sales de la función
        }
        
    }
    private void LOGIC_AND(){
        
        EQUALITY();
        LOGIC_AND_2();
    }
    private void LOGIC_AND_2(){
        if (preanalisis != null && preanalisis.tipo.equals(TipoToken.Y)) {
            coincidir(y);
            EQUALITY();
            LOGIC_AND_2();
        } else {
            // Producción de cadena vacía (Ɛ)
            // No haces nada en este caso, simplemente sales de la función
        }
        
    }
    private void EQUALITY(){
        COMPARISON();
        EQUALITY_2();
    }
    private void EQUALITY_2(){
        int caso=-1;
        if (preanalisis.tipo.equals(TipoToken.DIFERENTE)){
    caso = 0;
} else if (preanalisis.tipo.equals(TipoToken.IGUAL)){
    caso = 1;
} else{
    // Producción de cadena vacía (Ɛ)
    // No haces nada en este caso, simplemente sales de la función
}

        switch(caso){
            case 0:
                coincidir(diferente);
                COMPARISON();
                EQUALITY_2();
                break;
            case 1:
                coincidir(igual);
                COMPARISON();
                EQUALITY_2();
                break;
        }
    }
    private void COMPARISON(){
        TERM();
        COMPARISON_2();
    }
    private void COMPARISON_2(){
        int caso=-1;
        if (preanalisis.tipo.equals(TipoToken.MAYOR)){
    caso = 0;
} else if (preanalisis.tipo.equals(TipoToken.MAYOR_O_IGUAL)){
    caso = 1;
} else if (preanalisis.tipo.equals(TipoToken.MENOR)){
    caso = 2;
} else if (preanalisis.tipo.equals(TipoToken.MENOR_O_IGUAL)){
    caso = 3;
} else{
    // Producción de cadena vacía (Ɛ)
    // No haces nada en este caso, simplemente sales de la función
}

        switch(caso){
            case 0:
                coincidir(mayor);
                TERM();
                COMPARISON_2();
                break;
            case 1:
                coincidir(mayor_o_igual);
                TERM();
                COMPARISON_2();
                break;
            case 2:
                coincidir(menor);
                TERM();
                COMPARISON_2();
                break;
            case 3:
                coincidir(menor_o_igual);
                TERM();
                COMPARISON_2();
                break;
        }
    }
    private void TERM(){
        FACTOR();
        TERM_2();
    }
    private void TERM_2(){
        int caso=-1;
        if (preanalisis.tipo.equals(TipoToken.MENOS)){
    caso = 0;
} else if (preanalisis.tipo.equals(TipoToken.MAS)){
    caso = 1;
} else{
    // Producción de cadena vacía (Ɛ)
    // No haces nada en este caso, simplemente sales de la función
}

        switch(caso){
            case 0:
                coincidir(menos);
                FACTOR();
                TERM_2();
                break;
            case 1:
                coincidir(mas);
                FACTOR();
                TERM_2();
                break;
        }
    }
    private void FACTOR(){
        UNARY();
        FACTOR_2();
    }
    private void FACTOR_2(){
        int caso=-1;
        if (preanalisis.tipo.equals(TipoToken.ENTRE)){
    caso = 0;
} else if (preanalisis.tipo.equals(TipoToken.POR)){
    caso = 1;
} else{
    // Producción de cadena vacía (Ɛ)
    // No haces nada en este caso, simplemente sales de la función
}

        switch(caso){
            case 0:
                coincidir(entre);
                UNARY();
                FACTOR_2();
                break;
            case 1:
                coincidir(por);
                UNARY();
                FACTOR_2();
                break;
        }
    }
    private void UNARY(){
        int caso=-1;
        if (preanalisis.tipo.equals(TipoToken.EXCLA)){
    caso = 0;
} else if (preanalisis.tipo.equals(TipoToken.MENOS)){
    caso = 1;
} else if (preanalisis.tipo.equals(TipoToken.IDENTIFICADOR) || 
           preanalisis.tipo.equals(TipoToken.NUMERO) || 
           preanalisis.tipo.equals(TipoToken.CADENA) ||
           preanalisis.tipo.equals(TipoToken.VERDADERO) ||
           preanalisis.tipo.equals(TipoToken.FALSO) ||
           preanalisis.tipo.equals(TipoToken.NULO) ||
           preanalisis.tipo.equals(TipoToken.ESTE) ||
           preanalisis.tipo.equals(TipoToken.PARE_IZ) ||
           preanalisis.tipo.equals(TipoToken.SUPERS)){
    caso = 3;
}
        else{
    hayErrores=true;
}

        switch(caso){
            case 0:
                coincidir(excla);
                UNARY();
                break;
            case 1:
                coincidir(menos);
                UNARY();
                break;
            case 2:
                CALL();
                break;
        }
    }
    private void CALL(){
        PRIMARY();
        CALL_2();
    }
    private void CALL_2(){
        int caso=-1;
        if (preanalisis.tipo.equals(TipoToken.PARE_IZ)){
    caso = 0;
} else if (preanalisis.tipo.equals(TipoToken.PUNTO)){
    caso = 1;
} else{
    // Producción de cadena vacía (Ɛ)
    // No haces nada en este caso, simplemente sales de la función
}

        switch(caso){
            case 0:
                coincidir(pare_iz);
                ARGUMENTS_OPC();
                coincidir(pare_der);
                CALL_2();
                break;
            case 1:
                coincidir(punto);
                coincidir(identificador);
                CALL_2();
                break;
        }
    }
    private void CALL_OPC(){
        if (preanalisis.tipo.equals(TipoToken.NUMERO) || 
        preanalisis.tipo.equals(TipoToken.CADENA) || 
        preanalisis.tipo.equals(TipoToken.IDENTIFICADOR) ||  
        preanalisis.tipo.equals(TipoToken.VERDADERO) || 
        preanalisis.tipo.equals(TipoToken.FALSO) || 
        preanalisis.tipo.equals(TipoToken.NULO) || 
        preanalisis.tipo.equals(TipoToken.ESTE) ||
        preanalisis.tipo.equals(TipoToken.PARE_IZ) ||
        preanalisis.tipo.equals(TipoToken.SUPERS)) {
        CALL();
        coincidir(punto);
    } else {
        // Producción de cadena vacía (Ɛ)
        // No haces nada en este caso, simplemente sales de la función
    }
        
    }
    private void PRIMARY(){
        int caso=-1;
        if (preanalisis.tipo.equals(TipoToken.VERDADERO)){
    caso = 0;
} else if (preanalisis.tipo.equals(TipoToken.FALSO)){
    caso = 1;
} else if (preanalisis.tipo.equals(TipoToken.NULO)){
    caso = 2;
} else if (preanalisis.tipo.equals(TipoToken.ESTE)){
    caso = 3;
} else if (preanalisis.tipo.equals(TipoToken.NUMERO)){
    caso = 4;
} else if (preanalisis.tipo.equals(TipoToken.CADENA)){
    caso = 5;
} else if (preanalisis.tipo.equals(TipoToken.IDENTIFICADOR)){
    caso = 6;
} else if (preanalisis.tipo.equals(TipoToken.PARE_IZ)){
    caso = 7;
} else if (preanalisis.tipo.equals(TipoToken.SUPERS)){
    caso = 8;
}
        else{
    hayErrores=true;
}

        switch(caso){
            case 0:
                coincidir(verdadero);
                break;
            case 1:
                coincidir(falso);
                break;
            case 2:
                coincidir(nulo);
                break;
            case 3:
                coincidir(este);
                break;
            case 4:
                coincidir(numero);
                break;
            case 5:
                coincidir(cadena);
                break;
            case 6:
                coincidir(identificador);
                break;
            case 7:
                coincidir(pare_iz);
                EXPRESSION();
                coincidir(pare_der);
                break;
            case 8:
                coincidir(supers);
                coincidir(punto);
                coincidir(identificador);
                break;    
        }
    }
    private void FUNCTION(){
        coincidir(identificador);
        coincidir(pare_iz);
        PARAMETERS_OPC();
        coincidir(pare_der);
        BLOCK();
    }
    private void FUNCTIONS(){
        if (preanalisis != null && preanalisis.tipo.equals(TipoToken.IDENTIFICADOR)) {
        
    } else {
        // Producción de cadena vacía (Ɛ)
        // No haces nada en este caso, simplemente sales de la función
    }
        
    }
    private void PARAMETERS_OPC(){
        if (preanalisis != null && preanalisis.tipo.equals(TipoToken.IDENTIFICADOR)) {
        PARAMETERS();
    } else {
        // Producción de cadena vacía (Ɛ)
        // No haces nada en este caso, simplemente sales de la función
    }
        
    }
    private void PARAMETERS(){
        coincidir(identificador);
        PARAMETERS_2();
    }
    private void PARAMETERS_2(){
        if (preanalisis != null && preanalisis.tipo.equals(TipoToken.COMA)) {
        coincidir(coma);
        coincidir(identificador);
        PARAMETERS_2();
    } else {
        // Producción de cadena vacía (Ɛ)
        // No haces nada en este caso, simplemente sales de la función
    }
        
    }
    private void ARGUMENTS_OPC(){
        if (preanalisis != null && (preanalisis.tipo.equals(TipoToken.EXCLA) || 
           preanalisis.tipo.equals(TipoToken.MENOS) || 
           preanalisis.tipo.equals(TipoToken.NUMERO) || 
           preanalisis.tipo.equals(TipoToken.CADENA) || 
           preanalisis.tipo.equals(TipoToken.IDENTIFICADOR) || 
           preanalisis.tipo.equals(TipoToken.IMPRIMIR) ||
           preanalisis.tipo.equals(TipoToken.VERDADERO) || 
           preanalisis.tipo.equals(TipoToken.FALSO) || 
           preanalisis.tipo.equals(TipoToken.NULO) || 
           preanalisis.tipo.equals(TipoToken.ESTE) ||
           preanalisis.tipo.equals(TipoToken.PARE_IZ) ||
           preanalisis.tipo.equals(TipoToken.SUPERS))) {
        
    } else {
        // Producción de cadena vacía (Ɛ)
        // No haces nada en este caso, simplemente sales de la función
    }
        ARGUMENTS();
    }
    private void ARGUMENTS(){
        EXPRESSION();
        ARGUMENTS_2();
    }
    private void ARGUMENTS_2(){
        if (preanalisis != null && preanalisis.tipo.equals(TipoToken.COMA)) {
        coincidir(coma);
        EXPRESSION();
        ARGUMENTS_2();
    } else {
        // Producción de cadena vacía (Ɛ)
        // No haces nada en este caso, simplemente sales de la función
    }
        
    }
    
    void coincidir(Token t){
        if(hayErrores) return;

        if(preanalisis.tipo == t.tipo){
            i++;
            preanalisis = tokens.get(i);
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba un  " + t.tipo);

        }
    }
}