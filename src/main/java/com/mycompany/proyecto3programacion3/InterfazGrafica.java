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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
public class InterfazGrafica extends JFrame {
    private JTabbedPane tabbedPane;  // contenedor para colocar las pestañas
    private JTextField barraParaFormulas;
    private JMenuBar menu;
    private JMenu menuArchivo, menuInformacion;
    private JMenuItem botonNuevaHoja, botonTablaHash, botonGuardar, botonAbrir, botonInformacion;
 
    public InterfazGrafica() {
        setTitle("Hoja De Cálculo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        
        tabbedPane = new JTabbedPane();
        add(tabbedPane, BorderLayout.CENTER);
 
        barraParaFormulas = new JTextField();
        add(barraParaFormulas, BorderLayout.NORTH);
        
        crearNuevaHoja();
 
        menu = new JMenuBar();
        
        menuArchivo = new JMenu("Archivo");
        botonNuevaHoja = new JMenuItem("Nueva Hoja");
        botonNuevaHoja.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearNuevaHoja();
            }
        });
 
        botonTablaHash = new JMenuItem("Tabla Hash");
        botonGuardar = new JMenuItem("Guardar");
        botonAbrir = new JMenuItem("Abrir");
        menuArchivo.add(botonNuevaHoja);
        menuArchivo.add(botonTablaHash);
        menuArchivo.add(botonGuardar);
        menuArchivo.add(botonAbrir);
        menu.add(menuArchivo);
 
        menuInformacion = new JMenu("Informacion");
        botonInformacion = new JMenuItem("Mostrar Información");
        menuInformacion.add(botonInformacion);
        menu.add(menuInformacion);
 
        setJMenuBar(menu);
        
        setVisible(true);
    }
 
    private void crearNuevaHoja() {
        JPanel panelHoja = new JPanel(new BorderLayout());
        JTable tabla = new JTable(100, 26);
        tabla.setCellSelectionEnabled(true);
        tabla.putClientProperty("Edicion terminada", Boolean.TRUE);
 
        TableModel modeloTablaFilas = new AbstractTableModel() {
            @Override
            public int getRowCount() {
                return 100;
            }
 
            @Override
            public int getColumnCount() {
                return 1;
            }
 
            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                return rowIndex + 1;
            }
        };
 
        JTable encabezamientoFilas = new JTable(modeloTablaFilas);
        encabezamientoFilas.setPreferredScrollableViewportSize(new Dimension(30, 0));
        encabezamientoFilas.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        encabezamientoFilas.getColumnModel().getColumn(0).setPreferredWidth(30);
        encabezamientoFilas.setRowHeight(tabla.getRowHeight());
        encabezamientoFilas.setFocusable(false);
        encabezamientoFilas.setRowSelectionAllowed(false);
 
        JScrollPane rowHeaderScrollPane = new JScrollPane(encabezamientoFilas);
        rowHeaderScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
 
        panelHoja.add(rowHeaderScrollPane, BorderLayout.WEST);
        panelHoja.add(new JScrollPane(tabla), BorderLayout.CENTER);
 
        tabbedPane.addTab("Hoja " + (tabbedPane.getTabCount() + 1), panelHoja);
    }
 
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
        JPanel panelHoja = (JPanel) tabbedPane.getSelectedComponent();
        JScrollPane scrollPane = (JScrollPane) panelHoja.getComponent(1);
        return (JTable) scrollPane.getViewport().getView();
    }
 
    public void actualizarTabla(HojaDeCalculo modelo) {
        JTable tabla = getTable();
        for (int i = 0; i < tabla.getRowCount(); i++) {
            for (int j = 0; j < tabla.getColumnCount(); j++) {
                Object valor = modelo.getValor(i, j);
                if (valor != null) {
                    tabla.setValueAt(valor.toString(), i, j);
                } else {
                    tabla.setValueAt("", i, j);
                }
            }
        }
    }
}