
package interpretes;

public enum TipoToken {
    // Crear un tipoToken por palabra reservada
    // Crear un tipoToken: identificador, una cadena y numero
    // Crear un tipoToken por cada "Signo del lenguaje" (ver clase Scanner)


    // Palabras clave:
    Y, CLASE, CADENA, ADEMAS, FALSO, PARA, FUN, SI, NULO, O, IMPRIMIR, RETORNAR, SUPERS, ESTE, VERDADERO,
    VAR, MIENTRAS, 
    
    PARE_DER, PARE_IZ, COR_DER, COR_IZ, COMA, PUNTO, PUN_Y_COM, MENOS, MAS, POR, ENTRE,
    EXCLA, DIFERENTE, ASIGNACION, IGUAL, MENOR, MENOR_O_IGUAL, MAYOR, MAYOR_O_IGUAL, IDENTIFICADOR,
    NUMERO, SIMBOLO, PALABRARESERVADA, SPACE,

    EOF
}
