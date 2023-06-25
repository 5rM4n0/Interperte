/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interpretes;
public class SolverRelacional {

    private final Nodo nodo;

    public SolverRelacional(Nodo nodo) {
        this.nodo = nodo;
    }

    public Object resolver(){
        return resolver(nodo);
    }
    private Object resolver(Nodo n){
        // No tiene hijos, es un operando
        if(n.getHijos() == null){
            if(n.getValue().tipo == TipoToken.NUMERO){
                return n.getValue().literal;
            }
            else if(n.getValue().tipo == TipoToken.IDENTIFICADOR){
                // Ver la tabla de símbolos
            }
        }

        // Por simplicidad se asume que la lista de hijos del nodo tiene dos elementos
        Nodo izq = n.getHijos().get(0);
        Nodo der = n.getHijos().get(1);

        Object resultadoIzquierdo = resolver(izq);
        Object resultadoDerecho = resolver(der);

        if(resultadoIzquierdo instanceof Double && resultadoDerecho instanceof Double){
            switch (n.getValue().tipo){
                case MENOR:
                    return ((Double)resultadoIzquierdo < (Double) resultadoDerecho);
                case MAYOR:
                    return ((Double)resultadoIzquierdo > (Double) resultadoDerecho);
                case MENOR_O_IGUAL:
                    return ((Double)resultadoIzquierdo <= (Double) resultadoDerecho);
                case MAYOR_O_IGUAL:
                    return ((Double)resultadoIzquierdo >= (Double) resultadoDerecho);
            }
        }
        else{
            // Error por diferencia de tipos
        }

        return null;
    }
}
