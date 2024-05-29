package labo1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CrearGrupos {

    public static void main(String[] args) {
        String[] nombres = LeerNombresDesdeArchivo("reporte.txt");
        int[] habilidades = LeerHabilidadesDesdeArchivo("reporte.txt");

        int cantidadPersonasPorGrupo = SolicitarCantidadPersonas();
        int[][] gruposPorCantidad = CrearGrupos(habilidades, cantidadPersonasPorGrupo, nombres);

        System.out.println("Grupos creados por cantidad de personas por grupo:");
        ImprimirGrupos(gruposPorCantidad, nombres);

        int cantidadTotalGrupos = SolicitarCantidadGrupos();
        int[][] gruposPorCantidadTotal = CrearGruposPorTotal(gruposPorCantidad, cantidadTotalGrupos, nombres);

        System.out.println("\nGrupos creados por cantidad total de grupos:");
        ImprimirGrupos(gruposPorCantidadTotal, nombres);
    }

    public static String[] LeerNombresDesdeArchivo(String archivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea = br.readLine(); // Leer la primera línea (cabecera)
            String[] partes = linea.split(","); // Dividir la línea en partes
            String[] nombres = new String[partes.length - 3]; // Crear arreglo para almacenar nombres
            for (int i = 3; i < partes.length; i++) {
                nombres[i - 3] = partes[i].trim(); // Guardar los nombres
            }
            return nombres;
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
            return null;
        }
    }

    public static int[] LeerHabilidadesDesdeArchivo(String archivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea = br.readLine(); // Leer la primera línea (cabecera)
            String[] partes = linea.split(","); // Dividir la línea en partes
            int[] habilidades = new int[partes.length - 3]; // Crear arreglo para almacenar habilidades
            for (int i = 3; i < partes.length; i++) {
                habilidades[i - 3] = Integer.parseInt(partes[i].trim()); // Convertir y guardar las habilidades
            }
            return habilidades;
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
            return null;
        }
    }

    public static int SolicitarCantidadPersonas() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese la cantidad de personas por grupo: ");
        return scanner.nextInt();
    }

    public static int SolicitarCantidadGrupos() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese la cantidad total de grupos: ");
        return scanner.nextInt();
    }

    public static int[][] CrearGrupos(int[] habilidades, int cantidad, String[] nombres) {
        int totalEstudiantes = habilidades.length;
        int personasPorGrupo = totalEstudiantes / cantidad;
        int[][] grupos = new int[cantidad][personasPorGrupo];
        int estudianteIndex = 0;
        for (int i = 0; i < cantidad; i++) {
            for (int j = 0; j < personasPorGrupo; j++) {
                grupos[i][j] = habilidades[estudianteIndex];
                estudianteIndex++;
            }
        }
        return grupos;
    }

    public static int[][] CrearGruposPorTotal(int[][] grupos, int cantidadTotal, String[] nombres) {
        int cantidadGrupos = grupos.length;
        int personasPorGrupo = grupos[0].length;
        int personasRestantes = cantidadTotal * personasPorGrupo - cantidadGrupos * personasPorGrupo;
        int[][] gruposPorTotal = new int[cantidadTotal][personasPorGrupo];
        int grupoIndex = 0;
        for (int i = 0; i < cantidadGrupos; i++) {
            for (int j = 0; j < personasPorGrupo; j++) {
                if (personasRestantes > 0) {
                    gruposPorTotal[grupoIndex][j] = grupos[i][j];
                    personasRestantes--;
                } else {
                    grupoIndex++;
                    gruposPorTotal[grupoIndex][j] = grupos[i][j];
                }
            }
        }
        return gruposPorTotal;
    }

    public static void ImprimirGrupos(int[][] grupos, String[] nombres) {
        for (int i = 0; i < grupos.length; i++) {
            System.out.print("Grupo " + (i + 1) + ": ");
            for (int j = 0; j < grupos[i].length; j++) {
                int habilidad = grupos[i][j];
                String nombre = nombres[habilidad];
                System.out.print(nombre + " ");
            }
            System.out.println();
        }
    }
}
