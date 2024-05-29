<div align="center">
<h1><strong>PROYECTO 3 PROGRAMACION 3</strong></h1>
</div>
# **Proyecto desarrollado por:**

#### Roberto Antonio Ramirez Gomez 7690-22-12700

#### Jean Klaus Castañeda Santos 7690-22-892

#### Jonathan Joel Chan Cuellar 7690-22-1805
---
**ENLACES**
[**VIDEO DE PRUEBAS Y RESULTADOS**](https://youtu.be/tuLrkigF4q4)

[**ENLACE PARA VER EL MANUAL DE USUARIO**](https://github.com/rramirezg18/Proyecto-3-Programacion-3/blob/main/Manual%20Usuario.md "MANUAL DE USUARIO")

[**ENLACE PARA VER EL MANUAL TECNICO**](https://github.com/rramirezg18/Proyecto-3-Programacion-3/blob/main/Manual%20T%C3%A9cnico.md "MANUAL TECNICO")

[**ENLACE PARA VER LA DOCUMENTACION**](https://github.com/rramirezg18/Proyecto-3-Programacion-3/blob/main/Documentacion.md "DOCUMENTACION DEL PROYECTO")

---
---

## Descripcion Del Proyecto:
El proyecto se centra en el desarrollo de una hoja de cálculo similar a Excel, utilizando matrices ortogonales para representar las celdas y almacenar sus datos. Implementará el patrón de diseño Modelo-Vista-Controlador (MVC) y permitirá realizar operaciones básicas como suma y multiplicación. Además, incluirá la capacidad de crear múltiples hojas internas y generar tablas hash utilizando un algoritmo propio. Opcionalmente, se puede agregar la funcionalidad de guardar y cargar datos en almacenamiento secundario.
**El proyecto incluira las siguientes clases:**
- #### Celda.Java
- #### FuncionesHojaDeCalculo.Java
- #### HojaDeCalculo.Java
- #### InterfazGrafica.Java
- #### Proyecto3Programacion3.Java
- #### Referencias.Java
- #### TablaHash.Java
- ---
## clase Celda
Para el desarrollo del proyecto de hoja electrónica, creamos una clase llamada Celda. Esta clase representa una celda en la hoja de cálculo y es fundamental para almacenar datos en la misma. Cada instancia de esta clase puede contener un valor de cualquier tipo, lo que proporciona flexibilidad para trabajar con diferentes tipos de datos en la hoja de cálculo.
### Código
```java
package com.mycompany.proyecto3programacion3;
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
```
---
---
## clase FuncionesHojaDeCalculo

La clase FuncionesHojaDeCalculo controla las funciones y métodos necesarios para realizar operaciones en la hoja de cálculo y en la tabla hash. Esta clase es crucial para el funcionamiento de la aplicación de hoja de cálculo.

La clase FuncionesHojaDeCalculo maneja las acciones del usuario en la interfaz gráfica, como la creación de nuevas hojas de cálculo, el procesamiento de fórmulas ingresadas, el guardado y la carga de datos, la visualización de la tabla hash, entre otras. Además, implementa métodos para la conversión y evaluación de expresiones matemáticas, lo que permite realizar operaciones en las celdas de la hoja de cálculo de manera eficiente y precisa.

Esta clase cumple un papel fundamental en la aplicación de hoja de cálculo, proporcionando las funcionalidades necesarias para la manipulación de datos y la realización de operaciones aritméticas básicas, lo que contribuye al éxito y la eficacia del proyecto.

### Lo mas importante del codigo de esta clase

```java
package com.mycompany.proyecto3programacion3;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

public class FuncionesHojaDeCalculo {
    private InterfazGrafica interfaz;
    private LinkedList<HojaDeCalculo> hojas;
    private int hojaActual;

    public FuncionesHojaDeCalculo(InterfazGrafica interfaz) {
        this.interfaz = interfaz;
        this.hojas = new LinkedList<>();
        this.hojaActual = 0;
        crearNuevaHoja();

        // Acciones para los botones y eventos
        interfaz.getNuevaHojaItem().addActionListener(e -> crearNuevaHoja());
        interfaz.getGuardarItem().addActionListener(e -> guardarDatos());
        interfaz.getCargarItem().addActionListener(e -> abrirHojas());
        interfaz.getTablaHashItem().addActionListener(e -> mostrarTablaHash());
        interfaz.getInfoItem().addActionListener(e -> mostrarInformacion());

        // Acciones para el modelo de la tabla para actualizar el modelo de datos
        interfaz.getTable().getModel().addTableModelListener(e -> {
            int filas = e.getFirstRow();
            int columnas = e.getColumn();
            String valor = (String) interfaz.getTable().getValueAt(filas, columnas);
            if (valor != null && !valor.trim().isEmpty()) {
                try {
                    getHojaActual().setValor(filas, columnas, Double.parseDouble(valor));
                } catch (NumberFormatException ex) {
                    // Manejo de errores
                }
            }
        });

        // Añadir acciones a la barra de fórmulas
        interfaz.getFormulaField().addActionListener(e -> procesarFormula(interfaz.getFormulaField().getText()));
    }

    private void crearNuevaHoja() {
        hojas.add(new HojaDeCalculo(100, 26));
        hojaActual = hojas.size() - 1;
        interfaz.actualizarTabla(getHojaActual());
    }

    private HojaDeCalculo getHojaActual() {
        return hojas.get(hojaActual);
    }

    private void procesarFormula(String formula) {
        // Procesamiento de la fórmula
    }

    private void guardarDatos() {
        // Guardar datos en la memoria del sistema
    }

    private void abrirHojas() {
        // Abrir hojas de cálculo guardadas
    }

    private void mostrarTablaHash() {
        // Mostrar tabla hash en una nueva ventana
    }

    private void mostrarInformacion() {
        // Mostrar información acerca del proyecto
    }
}
```
---
---
## clase HojaDeCalculo

La clase HojaDeCalculo es una parte fundamental del proyecto de programación 3. Su objetivo es representar una hoja de cálculo similar a Excel. Al ser instanciada, crea una matriz bidimensional de objetos Celda que corresponden a las celdas individuales de la hoja.

Este arreglo bidimensional se inicializa con un tamaño determinado de filas y columnas proporcionado en el constructor de la clase. La misma provee métodos para obtener el número de filas y columnas de la hoja, así como para acceder y modificar los valores almacenados en cada celda.

### Código

```java
package com.mycompany.proyecto3programacion3;

/**
 *
 * @author ianto
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
   ```
---
---
## clase InterfazGrafica

La clase InterfazGrafica es una parte esencial del proyecto de programación 3, encargada de crear la interfaz de usuario para la hoja de cálculo. Al ser instanciada, genera una ventana JFrame que contiene una serie de elementos clave:

La clase también proporciona métodos para obtener referencias a los elementos de la interfaz, como la barra de fórmulas, la tabla principal y los elementos del menú. Además, incluye un método actualizarTabla que actualiza la visualización de la hoja de cálculo con los datos proporcionados por un modelo HojaDeCalculo, convirtiendo los valores en formato de objeto a formato de cadena para su visualización en la tabla.

### Código

```java
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
 ```
---
---
## clase Proyecto3Programacion3

El código de la clase Proyecto3Programacion3 constituye el punto de inicio de la aplicación de hoja de cálculo. En su método main, primero se crea la interfaz gráfica mediante la instancia de InterfazGrafica. Luego, se instancia el controlador FuncionesHojaDeCalculo, el cual gestiona las interacciones del usuario y las operaciones de la hoja de cálculo. Esta clase sirve como el punto de entrada de la aplicación, orquestando la creación de la interfaz y el controlador para su funcionamiento.

### Código

```java
package com.mycompany.proyecto3programacion3;

/**
 *
 * @author ianto
 */
public class Proyecto3Programacion3 {

    public static void main(String[] args) {
        // Crear la vista
        InterfazGrafica interfaz = new InterfazGrafica();
        // Crear el controlador
        new FuncionesHojaDeCalculo(interfaz);
    }
}
 ```
---
---
## clase Referencias

La clase Referencias proporciona un método estático llamado convertirReferencia que permite obtener las coordenadas de fila y columna a partir de una referencia de celda en formato de cadena. Este método toma una cadena que representa la referencia de una celda en una hoja de cálculo (por ejemplo, "A1", "B2") y devuelve un arreglo de enteros que contiene las coordenadas correspondientes [fila, columna]. La columna se obtiene convirtiendo la primera letra de la referencia en un índice de columna (A=0, B=1, ..., Z=25), mientras que la fila se obtiene restando 1 al número de fila dado en la referencia.

Este código es esencial para poder relacionar las celdas de la hoja de cálculo con sus coordenadas en la matriz de datos.

### Código

```java
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
 ```
---
---
## clase TablaHash

La clase TablaHash implementa una tabla hash para almacenar y buscar claves enteras, empleando una estrategia de sondeo lineal para manejar colisiones. Su objetivo es proporcionar métodos para insertar y buscar elementos en la tabla, utilizando una función de hash básica para calcular los índices de los buckets. Esta implementación, parte del proyecto de hoja de cálculo y resolución de expresiones aritméticas, es esencial para la eficiente gestión de datos, ayudando a optimizar la búsqueda y recuperación de información.

### Código

```java
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
```
---
---
## RESULTADOS DEL PROYECTO

El programa consiste en una aplicación de hoja de cálculo desarrollada en Java. Al unir todas las clases, crea una interfaz gráfica (InterfazGrafica) que permite al usuario interactuar con la hoja de cálculo. La interfaz incluye una tabla para mostrar los datos, una barra de fórmulas para ingresar fórmulas y un menú con opciones como crear una nueva hoja, guardar y abrir, tabla hash, entre otras.

Además de la interfaz gráfica, hay una clase (FuncionesHojaDeCalculo) que actúa como controlador. Esta clase maneja la lógica detrás de las acciones del usuario, como crear una nueva hoja, procesar fórmulas y actualizar la tabla.

También hay otras clases importantes como HojaDeCalculo, que representa la estructura de la hoja de cálculo y contiene métodos para obtener y establecer valores en las celdas, y TablaHash, que implementa una tabla hash para almacenar claves enteras y proporciona métodos para insertar y buscar claves en la tabla.

Finalmente, hay una clase adicional llamada Referencias, que proporciona un método para convertir referencias de celdas en coordenadas de fila y columna.

El programa combina una interfaz gráfica intuitiva con funcionalidades de hoja de cálculo y una tabla hash para ofrecer una aplicación completa para el manejo de datos y cálculos.
---
---

## Ejemplos de cómo resolver expresiones matemáticas
---
#### Ejemplo Método 1: Ingresando la celda de referencia seguido el signo “=” el usuario ingresa la expresión matemática que desea resolver, puede usar los siguientes signos “+”, “-“, “*”, /”, “^”, “()”.
![ejemplos ](imagen9.jpg)
#### Nota: El usuario siempre se debe ingresar la referencia de la celda donde desea mostrar el resultado.
---
#### Ejemplo Método 2: El usuario puede ingresar valores directamente a cada una de las celdas, en la barra de fórmulas deberá ingresar la expresión matemática con las referencias de las celdas que desee incluir, puede usar los siguientes signos “+”, “-“, “*”, /”, “^”, “()”.

---
![ejemplo 2 ](imagen10.jpg)
#### Nota: El usuario siempre se debe ingresar la referencia de la celda donde desea mostrar el resultado.
---
