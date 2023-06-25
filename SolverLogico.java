/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interpretes;

public class SolverLogico {

    private final Nodo nodo;

    public SolverLogico(Nodo nodo) {
        this.nodo = nodo;
    }

    public Object resolver(){
        return resolver(nodo);
    }
    private Object resolver(Nodo n){
        // No tiene hijos, es un operando
        if(n.getHijos() == null){
            if(n.getValue().tipo == TipoToken.NUMERO || n.getValue().tipo == TipoToken.CADENA){
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
                case MAS:
                    return ((Double)resultadoIzquierdo + (Double) resultadoDerecho);
                case MENOS:
                    return ((Double)resultadoIzquierdo - (Double) resultadoDerecho);
                case POR:
                    return ((Double)resultadoIzquierdo * (Double) resultadoDerecho);
                case ENTRE:
                    return ((Double)resultadoIzquierdo / (Double) resultadoDerecho);
            }
        }
        else if(resultadoIzquierdo instanceof String && resultadoDerecho instanceof String){
            if (n.getValue().tipo == TipoToken.MAS){
                // Ejecutar la concatenación
            }
        }
        else{
            // Error por diferencia de tipos
        }

        return null;
    }
}
