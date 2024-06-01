/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.proyecto3programacion3;

/**
 *El proyecto consiste en realizar una hoja de calculo y resolver expresiones aritmeticas. Y crear una tabla hash
 * Desarrollado por:
 * Roberto Ramirez
 * Jean Castañeda
 * Jonathan Chan
 */
public class Proyecto3Programacion3 {

    public static void main(String[] args) {
        // Crear la vista
        InterfazGrafica interfaz = new InterfazGrafica();
        // Crear el controlador
        new FuncionesHojaDeCalculo(interfaz);
    }
}
