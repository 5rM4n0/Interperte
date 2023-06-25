/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiladores.Interpretes;


import java.util.ArrayList;
import java.util.List;

public class Arbol {
    private final Nodo raiz;

    public Arbol(Nodo raiz){
        this.raiz = raiz;
    }

    public void recorrer(){
        for(Nodo n : raiz.getHijos()){
            Token t = n.getValue();
            switch (t.tipo){
                // Operadores aritméticos
                case MAS:
                case MENOS:
                case POR:
                case ENTRE:
                    SolverAritmetico solver = new SolverAritmetico(n);
                    Object res = solver.resolver();
                    System.out.println(res);
                break;
                case MAYOR:
                case MENOR:
                case MAYOR_O_IGUAL:
                case MENOR_O_IGUAL:
                    SolverRelacional solv = new SolverRelacional(n);
                    Object bul = solv.resolver();
                    break;
                case VAR:
                    // Crear una variable. Usar tabla de simbolos+-
                    break;
                case IMPRIMIR:
                    break;
                case SI:
                    break;
                case PARA:
                    break;
                case MIENTRAS:
                    break;
                

            }
        }
    }

}

