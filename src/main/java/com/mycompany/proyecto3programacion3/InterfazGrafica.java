/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto3programacion3;

/**
 *El proyecto consiste en realizar una hoja de calculo y resolver expresiones aritmeticas. Y crear una tabla hash
 * La clase interfaz grafica extends Jframe nos permite crear una interfaz para la hoja de calculo 
 * Desarrollado por:
 * Roberto Ramirez
 * Jean Castañeda
 * Jonathan Chan
 */
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
 
public class InterfazGrafica extends JFrame {
    //variables para los botones y tablas
    private JTable tabla;
    private JTextField barraParaFormulas;
    private JMenuBar menu;
    private JMenu menuArchivo, menuInformacion;
    private JMenuItem botonNuevaHoja, botonTablaHash, botonGuardar, botonAbrir, botonInformacion;
 
    public InterfazGrafica() {
        setTitle("Hoja De Cálculo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        //Crea la barra para las opciones del menu
        barraParaFormulas = new JTextField();
        add(barraParaFormulas, BorderLayout.NORTH);
 
        // Crear la tabla con las celdas 
        tabla = new JTable(100, 26);
        tabla.setCellSelectionEnabled(true); // Permite selección de celdas
        tabla.putClientProperty("Edicion terminada", Boolean.TRUE); // Terminar la edición al perder el foco
 
        // Crear un modelo de tabla para la columna de identificadores de fila
        TableModel modeloTablaFilas = new AbstractTableModel() {
            @Override
            public int getRowCount() {
                return 100; // Número de filas
            }
 
            @Override
            public int getColumnCount() {
                return 1; //Usa la primer columna para los identificadores de fila
            }
 
            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                return rowIndex + 1; // Filas numeradas del 1 al 100
            }
        };
 
        // Crear una tabla para los identificadores de fila
        JTable encabezamientoFilas = new JTable(modeloTablaFilas);
        encabezamientoFilas.setPreferredScrollableViewportSize(new Dimension(30, 0)); //separacion entre el numero de las filas y las filas
        encabezamientoFilas.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Desactivar el ajuste automático del tamaño de la columna
        encabezamientoFilas.getColumnModel().getColumn(0).setPreferredWidth(30); // Establecer el ancho de la columna
        encabezamientoFilas.setRowHeight(tabla.getRowHeight()); // Establecer la altura de fila igual a la de la tabla principal
        encabezamientoFilas.setFocusable(false); // Desactivar el enfoque
        encabezamientoFilas.setRowSelectionAllowed(false); // Desactivar la selección de filas
 
        // Crear un panel de desplazamiento para la tabla de identificadores de fila
        JScrollPane rowHeaderScrollPane = new JScrollPane(encabezamientoFilas);
        rowHeaderScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER); // Desactivar la barra de desplazamiento vertical
 
        //Tabla para colocar la numeracion de las filas
        add(rowHeaderScrollPane, BorderLayout.WEST);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
 
        // habilita el menu
        menu = new JMenuBar();
        
        // Opciones Menú Archivo
        menuArchivo = new JMenu("Archivo");
        botonNuevaHoja = new JMenuItem("Nueva Hoja");
        botonTablaHash = new JMenuItem("Tabla Hash");
        botonGuardar = new JMenuItem("Guardar");
        botonAbrir = new JMenuItem("Cargar");
        menuArchivo.add(botonNuevaHoja);
        menuArchivo.add(botonTablaHash);
        menuArchivo.add(botonGuardar);
        menuArchivo.add(botonAbrir);
        menu.add(menuArchivo);
 
        // Menú Informacion
        menuInformacion = new JMenu("Informacion");
        botonInformacion = new JMenuItem("Mostrar Información");
        menuInformacion.add(botonInformacion);
        menu.add(menuInformacion);
        
        setJMenuBar(menu);
 
        // habilita la ventana
        setVisible(true);
    }
    //metodos para las botones y la caja de texto de las formulas
    public JTextField getFormulaField() {
        return barraParaFormulas;
    }
 
    public JMenuItem getNuevaHojaItem() {
        return botonNuevaHoja;
    }
 
    public JMenuItem getTablaHashItem() {
        return botonTablaHash;
    }
 
    public JMenuItem getGuardarItem() {
        return botonGuardar;
    }
 
    public JMenuItem getCargarItem() {
        return botonAbrir;
    }
 
    public JMenuItem getInfoItem() {
        return botonInformacion;
    }
 
    public JTable getTable() {
        return tabla;
    }
    //Metodo que permite actualizar los datos la hoja de calculo a string
    public void actualizarTabla(HojaDeCalculo modelo) {
        for (int i = 0; i < tabla.getRowCount(); i++) {//recorre las filas
            for (int j = 0; j < tabla.getColumnCount(); j++) {//reccore cada columna en la fila actual
                Object valor = modelo.getValor(i, j);
                if (valor != null) { //Si el valor no es null lo convierte a string
                    tabla.setValueAt(valor.toString(), i, j);
                } else {
                    tabla.setValueAt("", i, j); // Limpia la celda si el valor es null
                }
            }
        }
    }
}