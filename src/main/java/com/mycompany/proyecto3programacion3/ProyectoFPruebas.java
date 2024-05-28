package com.mycompany.proyecto3programacion3;
/**
 *El proyecto consiste en realizar una hoja de calculo y resolver expresiones aritmeticas. Y crear una tabla hash
 * La clase interfaz grafica extends Jframe nos permite crear una interfaz para la hoja de calculo 
 * Desarrollado por:
 * Roberto Ramirez
 * Jean Castañeda
 * Jonathan Chan
 */
public class ProyectoFPruebas {
 
    public static void main(String[] args) {
        // Crear la vista
        InterfazGrafica interfaz = new InterfazGrafica();
        // Crear el controlador
        new FuncionesHojaDeCalculo(interfaz);
    }
}