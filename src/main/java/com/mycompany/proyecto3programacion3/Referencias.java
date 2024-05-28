package com.mycompany.proyecto3programacion3;
/**

 * Esta clase nos permite obtener las referencias de cada celda con coordenadas fila y columna
 * Desarrollado por:
 * Roberto Ramirez
 * Jean Castañeda
 * Jonathan Chan
 */
public class Referencias {
    public static int[] convertirReferencia(String referencia) {
    // obtiene el indice de la colummna A a Z
    int columna = referencia.charAt(0) - 'A';
 
    // Obtiene el número de fila del 1 al 100 y lo convierte a índice de 0 a 99
    int fila = Integer.parseInt(referencia.substring(1)) - 1;
 
    // Retorna los índices [fila, columna] en forma de coordenada
        return new int[]{fila, columna};
    }
}
