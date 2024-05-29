/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *en esta clase se forman los nodos que van a componer la lista circular
 * @author mjmar
 */
public class Nodo {
    String dato;
    Nodo siguiente;

    public Nodo(String dato) {
        this.dato = dato;
        this.siguiente = this;
    }
    
     public String getDato() {
        return dato;
    }

   
    public Nodo getSiguiente() {
        return siguiente;
    }

    
    public void setSiguiente(Nodo siguiente) {
        this.siguiente = siguiente;
    }

    
    @Override
    public String toString() {
        return dato;
    }
}
