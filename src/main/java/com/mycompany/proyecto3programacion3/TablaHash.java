/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto3programacion3;

/**
 *El proyecto consiste en realizar una hoja de calculo y resolver expresiones aritmeticas. Y crear una tabla hash
 * Clase tabla Hash con su funcion hash para encontrar el numero de bucket donde se almacenara la clave y sus metodos de insertar y buscar
 * Desarrollado por:
 * Roberto Ramirez
 * Jean Castañeda
 * Jonathan Chan
 */
public class TablaHash {
    private int[] tabla; //Almacena los elementos de la tabla
    private int tamaño;
 
    public TablaHash(int tamaño) {
        this.tamaño = tamaño;
        tabla = new int[tamaño];
    }
    //Funcion que debeulve el indice del bucket donde se almacenara el dato
    public int funcionHash(int clave) {
        return clave % tamaño;//Devulve el residudo entre la clave y el tamaño de la tabla hash
    }
 
    public void insertar(int clave) {
        int hash = funcionHash(clave);//calcula el indice usando la funcion hash
        while (tabla[hash] != 0) {//Si la posicion esta ocupada es colision
            hash = (hash + 1) % tamaño;//encuentra la siguiente posicion
        }
        tabla[hash] = clave;//inserta la clave en la posicion correcta
    }
 
    public int buscar(int clave) {
        int hash = funcionHash(clave);//Funcion para buscar el inicial
        while (tabla[hash] != 0 && tabla[hash] != clave){ //mientras no encuentra la posicion y no encuentra una posicion vaica
            hash = (hash + 1) % tamaño;//busca la siguiente posicion
        }
        return tabla[hash] == clave ? hash : -1; // Devuelve el índice si encuentra la clave, -1 si no la encuentra
    }
}