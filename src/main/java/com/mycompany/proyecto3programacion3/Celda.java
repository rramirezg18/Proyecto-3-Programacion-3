/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto3programacion3;

/**
 *
 * @author ianto
 */
import java.io.Serializable;

public class Celda implements Serializable {
    private Object valor;
    //Constructores
    public Celda() {
        this.valor = null;
    }

    public Celda(Object valor) {
        this.valor = valor;
    }
    //Setter & Getters
    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }
} 