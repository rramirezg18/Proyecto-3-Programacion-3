/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto3programacion3;

/**
 *El proyecto consiste en realizar una hoja de calculo y resolver expresiones aritmeticas. Y crear una tabla hash
 * Clase Hoja de calculo
 * Desarrollado por:
 * Roberto Ramirez
 * Jean Castañeda
 * Jonathan Chan
 */
import java.io.Serializable;

public class HojaDeCalculo implements Serializable {
    private Celda[][] celdas;

    //Metodo para crear la hoja de calculo con las celdas
    public HojaDeCalculo(int filas, int columnas) {
        celdas = new Celda[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                celdas[i][j] = new Celda();
            }
        }
    }
    
    //Getters & Setters
    public int getFilas() {
        return celdas.length;
    }

    public int getColumnas() {
        return celdas[0].length; // asumiendo que todas las filas tienen la misma cantidad de columnas
    }

    public Object getValor(int fila, int columna) {
        return celdas[fila][columna].getValor();
    }

    public void setValor(int fila, int columna, Object valor) {
        celdas[fila][columna].setValor(valor);
    }

    /**public void sumar(int fila1, int columna1, int fila2, int columna2, int resultadoFila, int resultadoColumna) {
        Object valor1 = getValor(fila1, columna1);
        Object valor2 = getValor(fila2, columna2);
        System.out.println("Valores para sumar: " + valor1 + " y " + valor2); // Depuración
        if (valor1 instanceof Number && valor2 instanceof Number) {
            double suma = ((Number) valor1).doubleValue() + ((Number) valor2).doubleValue();
            setValor(resultadoFila, resultadoColumna, suma);
            System.out.println("Resultado de la suma: " + suma); // Depuración
        } else {
            System.out.println("Uno o ambos valores no son números."); // Depuración
        }
    }

    public void multiplicar(int fila1, int columna1, int fila2, int columna2, int resultadoFila, int resultadoColumna) {
        Object valor1 = getValor(fila1, columna1);
        Object valor2 = getValor(fila2, columna2);
        System.out.println("Valores para multiplicar: " + valor1 + " y " + valor2); // Depuración
        if (valor1 instanceof Number && valor2 instanceof Number) {
            double producto = ((Number) valor1).doubleValue() * ((Number) valor2).doubleValue();
            setValor(resultadoFila, resultadoColumna, producto);
            System.out.println("Resultado de la multiplicación: " + producto); // Depuración
        } else {
            System.out.println("Uno o ambos valores no son números."); // Depuración
        }
    }**/
}
