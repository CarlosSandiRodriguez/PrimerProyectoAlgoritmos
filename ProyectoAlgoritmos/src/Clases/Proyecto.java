/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * en esta clase se construye la logica para leer un archivo.java analizarlo
 * extraer los metodos que hay en él para posteriormente guardarlos en una lista
 * circular y calcular su complejidad
 *
 * @author mjmar
 */
public class Proyecto {

    ListaCircular lista = new ListaCircular();
    String filePath = "CrearGrupos.java";
    private static Set<String> nombres = new HashSet<>();

    /**
     * se lee el archivo.java usando un beffered
     *
     * @param filePath ruta del archivo.java
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void leerArchivo(String filePath) throws FileNotFoundException, IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String linea;
            StringBuilder contenidoMetodo = new StringBuilder();
            while ((linea = br.readLine()) != null) {
                if (linea.trim().startsWith("public") || linea.trim().startsWith("private")) {
                    if (contenidoMetodo.length() > 0) {
                        lista.agregar(contenidoMetodo.toString());
                        contenidoMetodo.setLength(0);
                    }
                }
                contenidoMetodo.append(linea).append("\n");
            }

            if (contenidoMetodo.length() > 0) {
                lista.agregar(contenidoMetodo.toString());
            }
        }
    }

    /**
     * este metodo se utiliza para imprimir en pantalla el nombre de todos los
     * metodos encontrados en el archivo.java
     */
    public void verNombres() {
        String filePath = "CrearGrupos.java";

        try (BufferedReader reader = new BufferedReader(
                new FileReader(filePath))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String nombreMetodo = extraerNombres(linea);
                if (nombreMetodo != null) {
                    nombres.add(nombreMetodo);
                    System.out.println("Nombre del metodo: " + nombreMetodo);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     *  este metodo utiliza expresiones regulares para detectar el nombre de los
     * metodos que se encuentran en el archivo.java
     * 
     * @param linea es la variable que almacena los datos que se van leyendo
     * del archivo.java
     * @return 
     */
    public static String extraerNombres(String linea) {

        Pattern pattern = Pattern.compile("\\b(public|private|protected|"
                + "|static|final|abstract)\\s+(?!class)(\\w+[\\[\\]]*\\s+)+"
                + "(\\w+)\\s*\\([^\\)]*\\)\\s*"
                + "(?:throws\\s+[^\\{]+)?\\{?");
        Matcher matcher = pattern.matcher(linea);

        if (matcher.find()) {

            String nMetodo = matcher.group(3);

            if (!nMetodo.equals("System")) {
                return nMetodo;
            }
        }

        return null;
    }

    /**
     * este metodo extrae el cuerpo de los metodos leidos en el archivo.java
     * para posteriormente guardarlos en los nodos de la lista circular
     */
    public void guardarEnLista() {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            StringBuilder contenidoMetodo = new StringBuilder();
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().startsWith("public") || linea.trim().startsWith("private")) {
                    if (contenidoMetodo.length() > 0) {
                        lista.agregar(contenidoMetodo.toString());
                        contenidoMetodo.setLength(0);
                    }
                }
                contenidoMetodo.append(linea).append("\n");
            }

            if (contenidoMetodo.length() > 0) {
                lista.agregar(contenidoMetodo.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * metodo para ver los datos de la lista
     */
    public void verLista() {
        guardarEnLista();
        System.out.println("Nombres de los métodos:");
        lista.mostrar();
    }

    /**
     * se recorre la lista circular y se envia cada nodo de esta al metodo
     * calcularComplejidad para imprimir la complejidad de cada metodo
     * dependiendo de cuanto aumento el contador
     */
    public void verComplejidad() {
        guardarEnLista();
        Nodo actual = lista.getInicio();
        while (actual != null && actual.siguiente != lista.getInicio()) {
            String metodo = actual.getDato();

            int complejidad = calcularComplejidad(metodo);
            if (complejidad == 0) {
                System.out.println("Complejidad del metodo en el peor de los casos: O(1)");
            } else if (complejidad == 1) {
                System.out.println("Complejidad del metodo en el peor de los casos: O(n)");
            } else {
                System.out.println("Complejidad del metodo en el peor de los casos: O(n^" + complejidad + ")");
            }
            System.out.println(metodo);
            actual = actual.siguiente;
        }
    }

    /**
     * usando expresiones regulares se detectan bucles en los metodos extraidos
     * del archivo.java para determinar su complejidad si se encuentra un bucle
     * el contador suma uno
     *
     * @param contenido ete parametro recibe el contenido que se encuentra
     * dentro de cada nodo en la lista circular
     * @return
     */
    private int calcularComplejidad(String contenido) {
        int complejidad = 0;

        Pattern pattern = Pattern.compile("\\b(for|while|if|do)\\b");
        Matcher matcher = pattern.matcher(contenido);
        while (matcher.find()) {
            complejidad++;
        }
        return complejidad;
    }

    public void menu() throws IOException {
        ListaCircular lista = new ListaCircular();
        Scanner entrada = new Scanner(System.in);
        System.out.println("\nBienvenido al menu de opciones");
        System.out.println("Seleccione una de las siguientes opciones:");
        System.out.println("1. Ver los nombres de los metodos");
        System.out.println("2. Ver procedimientos de la lista");
        System.out.println("3. calcular la complegidad");
        System.out.println("4. salir");

        int opcion = entrada.nextInt();
        switch (opcion) {
            case 1:
                verNombres();
                menu();
                break;
            case 2:
                verLista();
                menu();
                break;
            case 3:
                verComplejidad();
                menu();
                break;
            case 4:
                System.exit(0);
                break;
            default:
                System.out.println("Opción no válida");
                menu();
                break;
        }
    }

}
