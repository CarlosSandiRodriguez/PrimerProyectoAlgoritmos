package Clases;

/**
 * en esta clase se crea una lista enlazada circular
 */
import java.util.Iterator;

public class ListaCircular {

    private Nodo cabeza = null;
    private Nodo cola = null;

    /**
     * se optiene el inicio de la lista
     *
     * @return
     */
    public Nodo getInicio() {
        return this.cabeza;
    }

    /**
     * se optiene el nodo siguiente de la lista
     *
     * @param nodo
     * @return
     */
    public Nodo getSiguiente(Nodo nodo) {
        if (nodo != null) {
            return nodo.siguiente;
        } else {
            return null;
        }
    }

    /**
     * metodo para agregar datos dentro de los nodos en la lista
     *
     * @param dato es la informacion que llega como poarametro para se
     * almacenada
     */
    public void agregar(String dato) {
        Nodo nuevoNodo = new Nodo(dato);
        if (cabeza == null) {
            cabeza = nuevoNodo;
            cola = nuevoNodo;
        } else {
            nuevoNodo.siguiente = cabeza;
            cola.siguiente = nuevoNodo;
            cola = nuevoNodo;
        }
    }

    /**
     * metodo para mostrar los datos que se encuentran dentro de la lista
     */
    public void mostrar() {
        if (cabeza == null) {
            return;
        }
        Nodo temp = cabeza;
        do {
            System.out.println(temp.dato);
            temp = temp.siguiente;
        } while (temp != cabeza);
    }

}
