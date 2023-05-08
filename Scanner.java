package com.mycompany.interprete;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scanner {

    private final String source;

    private final List<Token> tokens = new ArrayList<>();

    private int linea = 1;
    
    ////////////////////////////   PALABRA RESERVADAS    ////////////////////////////////
    private static final Map<String, TipoToken> palabrasReservadas;
    static {
        palabrasReservadas = new HashMap<>();
        palabrasReservadas.put("y", TipoToken.Y);
        palabrasReservadas.put("clase", TipoToken.CLASE);
        palabrasReservadas.put("ademas", TipoToken.ADEMAS);
        palabrasReservadas.put("falso", TipoToken.FALSO);
        palabrasReservadas.put("para", TipoToken.PARA);
        palabrasReservadas.put("fun", TipoToken.FUN); //definir funciones
        palabrasReservadas.put("si", TipoToken.SI);
        palabrasReservadas.put("nulo", TipoToken.NULO);
        palabrasReservadas.put("o", TipoToken.O);
        palabrasReservadas.put("imprimir", TipoToken.IMPRIMIR);
        palabrasReservadas.put("retornar", TipoToken.RETORNAR);
        palabrasReservadas.put("super", TipoToken.SUPER);
        palabrasReservadas.put("este", TipoToken.ESTE);
        palabrasReservadas.put("verdadero", TipoToken.VERDADERO);
        palabrasReservadas.put("var", TipoToken.VAR); //definir variables
        palabrasReservadas.put("mientras", TipoToken.MIENTRAS);
    }
    
    ////////////////////////////   SIGNOS   ////////////////////////////////
    private static final Map<String, TipoToken> signos;
    static{
        signos = new HashMap<>();
        signos.put("+", TipoToken.MAS);
        signos.put(" ", TipoToken.SPACE);
        signos.put("{", TipoToken.COR_DER);
        signos.put("}", TipoToken.COR_IZ);
        signos.put("(", TipoToken.PARE_DER);
        signos.put(")", TipoToken.PARE_IZ);
        signos.put("*", TipoToken.POR);
        signos.put("/", TipoToken.ENTRE);
        signos.put("-", TipoToken.MENOS);
        signos.put("=", TipoToken.ASIGNACION);
        signos.put("==", TipoToken.IGUAL);
        signos.put("<", TipoToken.MENOR);
        signos.put(">", TipoToken.MAYOR);
        signos.put("<=", TipoToken.MENOR_O_IGUAL);
        signos.put(">=", TipoToken.MAYOR_O_IGUAL);
        signos.put("!", TipoToken.EXCLA);
        signos.put("!=", TipoToken.DIFERENTE);
        signos.put(",", TipoToken.COMA);
        signos.put(";", TipoToken.PUN_Y_COM);
    }

    ////////////////////////////   NUMEROS   ////////////////////////////////
    private static final Map<String, TipoToken> numeros;
    static{
        numeros = new HashMap<>();
        numeros.put("0", TipoToken.NUMERO);
        numeros.put("1", TipoToken.NUMERO);
        numeros.put("2", TipoToken.NUMERO);
        numeros.put("3", TipoToken.NUMERO);
        numeros.put("4", TipoToken.NUMERO);
        numeros.put("5", TipoToken.NUMERO);
        numeros.put("6", TipoToken.NUMERO);
        numeros.put("7", TipoToken.NUMERO);
        numeros.put("8", TipoToken.NUMERO);
        numeros.put("9", TipoToken.NUMERO);
        
    }
    
    Scanner(String source){
        this.source = source;
    }

    List<Token> scanTokens(){

        ////////////////////////////   VARIABLES NECESARIAS    ////////////////////////////////
        char charac=' ';
        String character="";
        String palabra="";
        String sigchar="";
        String num="";
        boolean finalizado=false;
        int caso=0;

        ////////////////////////////   CICLO PARA RECORRER LA CADENA   ////////////////////////////////
        for(int i=0;i<source.length();i++){           
            charac = source.charAt(i);         
            character = String.valueOf(charac);    
            if(i<source.length()-1){
                sigchar = character + source.charAt(i+1);
            }
            
            if(sigchar.equals("/*"))
                caso=3;
            else{
                if(sigchar.equals("//"))
                    caso=4;
                else{
                    if(signos.containsKey(character)||i==source.length()-1){
                        caso=2;
                    }else{
                        if(charac=='"'){
                            caso=5;
                        }else{
                            if(numeros.containsKey(character) && palabra==""){
                                caso=1;
                            }else
                                caso=0;
                        }
                    }
                }     
            }  
            switch(caso){
                case 0:
                    palabra+=character;
                    break;
                case 1:
                    ////////////////////////////   RECUPERAR TODO EL NUMERO    ////////////////////////////////
                    while(numeros.containsKey(character)||charac=='.'){                                               
                        num = num+character;
                        i++;
                        charac = source.charAt(i);
                        character = String.valueOf(charac);
                    }
                    i--;                    
                    ////////////////////////////   CREAR TOKEN DEL NUMERO    ////////////////////////////////
                    tokens.add(new Token(TipoToken.NUMERO, num, Double.parseDouble(num), linea));
                    num="";
                    break;
                case 2:
                    ////////////////////////////   SI ES UNA PALABRA CLAVE   ////////////////////////////////
                    if(palabrasReservadas.containsKey(palabra)){
                        ////////////////////////////   CREAR TOKEN DE PALABRA CLAVE   ////////////////////////////////
                        tokens.add(new Token(palabrasReservadas.get(palabra), palabra, null, linea));
                        palabra="";
                    }
                    ////////////////////////////   SI NO ES PALABRA CLAVE   ////////////////////////////////
                    else{
                        ////////////////////////////   CREAR TOKEN DE IDENTIFICADOR   ////////////////////////////////
                        if(!palabra.equals("")){
                            tokens.add(new Token(TipoToken.IDENTIFICADOR, palabra, null, linea));
                            palabra="";
                        }
                    }                    
                    ////////////////////////////   SI ES UN SIGNO COMPUESTO   ////////////////////////////////
                    if(signos.containsKey(sigchar)){
                        ////////////////////////////   CREAR TOKEN DEL SIGNO   ////////////////////////////////
                        tokens.add(new Token(signos.get(sigchar), sigchar, null, linea));
                        i++;
                    }else{
                        ////////////////////////////   NO FUE SIGNO COMPUESTO   ////////////////////////////////
                        if(charac!=' '&&!character.equals(""))
                        tokens.add(new Token(signos.get(character), character, null, linea));
                    }
                    break;
                case 3:
                    for(int a=i;a<source.length();a++){
                        charac = source.charAt(a);
                        character = String.valueOf(charac);
                        if(a<source.length()-1){
                            sigchar = character + source.charAt(a+1);
                        }
                        i=a;
                        if(sigchar.equals("*/")){
                            i=a+1;
                            finalizado=true;
                            a=source.length();
                        }                  
                    }
                    if(!finalizado){
                        System.out.println("No se encontro fin de comentario");
                        System.exit(1);
                    }
                    break;
                case 4:
                    for(int a=i;a<source.length();a++){
                        charac = source.charAt(a);
                        character = String.valueOf(charac);
                        if(a<source.length()-1){
                            sigchar = character + source.charAt(a+1);
                        }
                        if(sigchar.equals("\n")||a==source.length()-1){
                            i=a+1;
                            
                            a=source.length();
                        }               
                    }
                    
                    break;
                case 5:
                    palabra="";
                    String cadena = "";
                    cadena=cadena+'"';
                    ////////////////////////////   RECUPERAR CADENA   ////////////////////////////////
                    for(int a=i+1;a<source.length();a++){
                        charac = source.charAt(a);
                        character = String.valueOf(charac);
                        if(charac=='"'){
                    ////////////////////////////   SI ES FINAL DE LA CADENA   ////////////////////////////////
                            i=a;
                            finalizado=true;
                            a=source.length();
                        }else{
                            palabra = palabra+character;
                        }
                    }
                    if(!finalizado){
                        System.out.println("No se encontro el fin de cadena");
                        System.exit(1);
                    }
                    cadena=cadena+palabra+'"';
                    ///////////////////////////   CREAR TOKEN DE CADENA   ////////////////////////////////
                    tokens.add(new Token(TipoToken.CADENA, cadena, palabra, linea));
                    palabra="";
                    break;
            }
                     
        }     
        tokens.add(new Token(TipoToken.EOF, "", null, linea));
        return tokens;
    }
}