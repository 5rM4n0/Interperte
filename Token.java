package interpretes;


public class Token {

    final TipoToken tipo;
    final String lexema;
    final Object literal;
    final int posicion;

    public Token(TipoToken tipo, String lexema, Object literal, int linea) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.literal = literal;
        this.posicion = linea;
    }
    public Token(TipoToken tipo, String lexema) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.literal = null;
        this.posicion = 0;
    }
    

    public String toString(){
        return tipo + " " + lexema + " " + literal;
    }
    
    public boolean esOperando(){
        switch (this.tipo){
            case IDENTIFICADOR:
            case NUMERO:
            case CADENA:
                return true;
            default:
                return false;
        }
    }

    public boolean esOperador(){
        switch (this.tipo){
            case MAS:
            case MENOS:
            case POR:
            case ENTRE:
            case ASIGNACION:
            case MAYOR:
            case MAYOR_O_IGUAL:
            case MENOR:
            case MENOR_O_IGUAL:
            case Y:
            case O:
            case IGUAL:
            case DIFERENTE:
                return true;
            default:
                return false;
        }
    }

    public boolean esPalabraReservada(){
        switch (this.tipo){
            case VAR:
            case SI:
            case IMPRIMIR:
            case ADEMAS:
            case MIENTRAS:
            case PARA:
                return true;
            default:
                return false;
        }
    }

    public boolean esEstructuraDeControl(){
        switch (this.tipo){
            case SI:
            case ADEMAS:
            case PARA:
            case MIENTRAS:
                return true;
            default:
                return false;
        }
    }

    public boolean precedenciaMayorIgual(Token t){
        return this.obtenerPrecedencia() >= t.obtenerPrecedencia();
    }

    private int obtenerPrecedencia(){
        switch (this.tipo){
            case POR:
            case ENTRE:
                return 7;
            case MAS:
            case MENOS:
                return 6;           
            case MAYOR:
            case MAYOR_O_IGUAL:
            case MENOR:
            case MENOR_O_IGUAL:
                return 5;
            case IGUAL:
            case DIFERENTE:
                return 4;
            case Y:            
                return 3;
            case O:
                return 2;
            case ASIGNACION:
                return 1;
        }

        return 0;
    }

    public int aridad(){
        switch (this.tipo) {
            case POR:
            case ENTRE:
            case MAS:
            case MENOS:
            case ASIGNACION:
            case MAYOR:
            case MAYOR_O_IGUAL:
            case MENOR_O_IGUAL:
            case MENOR:
            case IGUAL:
            case DIFERENTE:
                return 2;
        }
        return 0;
    }
}
