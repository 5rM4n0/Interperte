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
    
    ////////////////////////////   PALABRA RESERVADAS    ////////////////////////////////
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
        boolean idnum=false;

        ////////////////////////////   CICLO PARA RECORRER LA CADENA   ////////////////////////////////
        for(int i=0;i<source.length();i++){           
            charac = source.charAt(i);         
            character = String.valueOf(charac);    
            if(i<source.length()-1){
                sigchar = character + source.charAt(i+1);
            }
            
            ////////////////////////////   SE ENCONTRO UN SIMBOLO, NUMERO O ESPACIO   ////////////////////////////////
            if(signos.containsKey(character) || numeros.containsKey(character) || charac=='"' || i==source.length()-1){

                if(numeros.containsKey(character)){
                    idnum=false;
                }else{
                    idnum=true;
                }
                if(palabra!=""&& !idnum){
                    palabra=palabra+character;
                }
                ////////////////////////////   SI COMIENZA CON UN NÚMERO    ////////////////////////////////
                if(numeros.containsKey(character) && palabra==""){
                    num=character;
                    i++;
                    ////////////////////////////   RECUPERAR TODO EL NUMERO    ////////////////////////////////
                    while(numeros.containsKey(character)){                       
                        charac = source.charAt(i);         
                        character = String.valueOf(charac);
                        if(numeros.containsKey(character)||character=="."){
                            num = num+character;
                        }
                        i++;
                    }
                    i--;
                    ////////////////////////////   CREAR TOKEN DEL NUMERO    ////////////////////////////////
                    tokens.add(new Token(TipoToken.NUMERO, num, Integer.parseInt(num), linea));
                    if(!signos.containsKey(character)){
                        palabra=palabra+character;
                    }
                }
                ////////////////////////////   SI NO COMIENZA CON NUMERO   ////////////////////////////////
                else{
                    ////////////////////////////   SI ES UNA PALABRA CLAVE   ////////////////////////////////
                    if(palabrasReservadas.containsKey(palabra)){
                        ////////////////////////////   CREAR TOKEN DE PALABRA CLAVE   ////////////////////////////////
                        tokens.add(new Token(palabrasReservadas.get(palabra), palabra, null, linea));
                        palabra="";
                    }
                    ////////////////////////////   SI NO ES PALABRA CLAVE   ////////////////////////////////
                    else{
                        if(palabra!="" && idnum){
                            ////////////////////////////   CREAR TOKEN DE IDENTIFICADOR   ////////////////////////////////
                            tokens.add(new Token(TipoToken.IDENTIFICADOR, palabra, null, linea));
                            palabra="";
                        }
                    }
                }
                ////////////////////////////   SI ES UN SIGNO   ////////////////////////////////             
                if(charac!=' ' && signos.containsKey(character)||charac=='"'){
                    ////////////////////////////   SI ES UN SIGNO COMPUESTO   ////////////////////////////////
                    if(signos.containsKey(sigchar)){
                        ////////////////////////////   CREAR TOKEN DEL SIGNO   ////////////////////////////////
                        tokens.add(new Token(signos.get(sigchar), sigchar, null, linea));
                        i++;
                    }
                    else{
                        ////////////////////////////   SI ES UN COMENTARIO   ////////////////////////////////
                        if(sigchar.equals("//")|| sigchar.equals("/*")){
                            for(int a=i;a<source.length();a++){
                                charac = source.charAt(a);
                                character = String.valueOf(charac);
                                if(a<source.length()-1){
                                    sigchar = character + source.charAt(a+1);
                                }
                                
                                i=a;
                                if(sigchar.equals("*/")){
                                    i=a+1;
                                    a=source.length();
                                }                     
                            }
                        }
                        else{
                            ////////////////////////////   SI ES UNA CADENA   ////////////////////////////////
                            if(charac=='"'){
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
                                        a=source.length();
                                    }else{
                                        palabra = palabra+character;
                                    }                               
                                }
                                cadena=cadena+palabra+'"';
                                ////////////////////////////   CREAR TOKEN DE CADENA   ////////////////////////////////
                                tokens.add(new Token(TipoToken.CADENA, cadena, palabra, linea));
                                palabra="";
                            }else{
                                ////////////////////////////   NO FUE SIGNO COMPUESTO, COMENTARIO NI CADENA, ES OTRO SIGNO   ////////////////////////////////
                                tokens.add(new Token(signos.get(character), character, null, linea));
                            }
                        }                        
                    }                   
                }                
            }
            ////////////////////////////   NO FUE SIGNO, NUMERO O ESPACIO   ////////////////////////////////          
            else{
                palabra = palabra+character;
            }           
        }       
        tokens.add(new Token(TipoToken.EOF, "", null, linea));
        return tokens;
    }
}