package com.mycompany.proyecto3programacion3;

/**
 * Esta clase nos permite controlas las funciones y metodos para que se realizen las operaciones en la hoja de calculo y la tabla hash
 * Desarrollado por:
 * Roberto Ramirez
 * Jean Castañeda
 * Jonathan Chan
 */
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
    //Constructor
    public FuncionesHojaDeCalculo(InterfazGrafica interfaz) {
        this.interfaz = interfaz;
        this.hojas = new LinkedList<>();
        this.hojaActual = 0;
        crearNuevaHoja();
 
        //acciones para los botones
        interfaz.getNuevaHojaItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearNuevaHoja();
            }
        });
 
        interfaz.getGuardarItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarDatos();
            }
        });
 
        interfaz.getCargarItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirHojas();
            }
        });
 
        interfaz.getTablaHashItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarTablaHash();
            }
        });
        
        interfaz.getInfoItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarInformacion();
            }
        });
 
        //acciones para modelo de la tabla para actualizar el modelo de datos
        interfaz.getTable().getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int filas = e.getFirstRow();
                int columnas = e.getColumn();
                String valor = (String) interfaz.getTable().getValueAt(filas, columnas);
                if (valor != null && !valor.trim().isEmpty()) {
                    try {
                        getHojaActual().setValor(filas, columnas, Double.parseDouble(valor));
                        //System.out.println("Valor actualizado en (" + filas + "," + columnas + "): " + valor);
                    } catch (NumberFormatException ex) {
                        //System.out.println("Valor no numérico ingresado: " + valor);
                    }
                }
            }
        });
 
        // Añadir acciones a la barra de fórmulas
        interfaz.getFormulaField().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                procesarFormula(interfaz.getFormulaField().getText());
            }
        });
    }
    //Metodo para crear una nueva hoja
    private void crearNuevaHoja() {
        hojas.add(new HojaDeCalculo(100, 26));
        hojaActual = hojas.size() - 1;
        interfaz.actualizarTabla(getHojaActual());
    }
 
    private HojaDeCalculo getHojaActual() {
        return hojas.get(hojaActual);//Retorna la hoja actual
    }
    //Metodo para procesar la formula ingresada para obtener el resultado de la expresion
    private void procesarFormula(String formula) {
        if (formula.contains("=")) {//Verifica que la formula ingresada tenga el singo = despues de la celda de referencia
            String[] partes = formula.split("=");//Quita los espacios en blanco antes y despues de =
            String referenciaResultado = partes[0].trim();//Obtiene la partes antes de =
            String expresion = partes[1].trim();//Obtiene la parte despues de =
            try {
                String posfija = convertirAPosfija(expresion);//Convierte la expresion en posfija
                double resultado = evaluarPosfija(posfija);//Obtiene el resultado
                int[] indiceResultado = Referencias.convertirReferencia(referenciaResultado);//Convierte la celda en indices de fila y columna
                getHojaActual().setValor(indiceResultado[0], indiceResultado[1], resultado);//Establece el resultado en la celda 
                interfaz.actualizarTabla(getHojaActual());//Actualiza los cambios en la hoja
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(interfaz, "Error en la fórmula: " + ex.getMessage());
            } catch (ArithmeticException ex) {
                JOptionPane.showMessageDialog(interfaz, "Error al realizar la operación: " + ex.getMessage());
            }
        }
    }
 
    private void realizarOperacion(String referenciaResultado, String[] operandos, String operacion) {
        try {
            int[] indiceResultado = Referencias.convertirReferencia(referenciaResultado);
            double resultado = 0;
            boolean primerOperando = true; // Utilizado para manejar correctamente la resta
 
            // Iterar sobre los operandos y realizar la operación adecuada
            for (String operando : operandos) {
                operando = operando.trim();
                double valorOperando;
                if (Character.isLetter(operando.charAt(0))) { // Es una referencia a una celda
                    int[] indice = Referencias.convertirReferencia(operando);
                    Object valor = getHojaActual().getValor(indice[0], indice[1]);
                    if (valor instanceof Number) {
                        valorOperando = ((Number) valor).doubleValue();
                    } else {
                        throw new NumberFormatException("La celda " + operando + " no contiene un número.");
                    }
                } else { // Es una constante numérica
                    valorOperando = Double.parseDouble(operando);
                }
 
                if (operacion.equals("sumar")) {
                    resultado += valorOperando;
                } else if (operacion.equals("restar")) {
                    if (primerOperando) {
                        resultado = valorOperando; // El primer operando se asigna directamente
                        primerOperando = false;
                    } else {
                        resultado -= valorOperando;
                    }
                } else if (operacion.equals("multiplicar")) {
                    if (resultado == 0) {
                        resultado = 1;
                    }
                    resultado *= valorOperando;
                } else if (operacion.equals("dividir")) {
                    if (resultado == 0) {
                        resultado = valorOperando;
                    } else {
                        if (valorOperando == 0) {
                            throw new ArithmeticException("No se puede dividir por cero.");
                        }
                        resultado /= valorOperando;
                    }
                }
            }
            getHojaActual().setValor(indiceResultado[0], indiceResultado[1], resultado);
            interfaz.actualizarTabla(getHojaActual()); // Actualizar la tabla en la vista
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(interfaz, "Por favor, ingrese valores válidos.");
        } catch (ArithmeticException ex) {
            JOptionPane.showMessageDialog(interfaz, "Error al realizar la operación: " + ex.getMessage());
        }
    }
    //Metodo para guardar la hoja de calculo en la memoria del sistema
    private void guardarDatos() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Hoja De Cálculo");
        int userSelection = fileChooser.showSaveDialog(interfaz);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try (FileOutputStream fos = new FileOutputStream(fileToSave);
                 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(hojas);
                JOptionPane.showMessageDialog(interfaz, "Datos guardados exitosamente.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(interfaz, "Error al guardar los datos: " + e.getMessage());
            }
        }
    }
 
    //Metodo para abrir hojas guardadas en la memoria del sistema
    private void abrirHojas() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Abrir Hoja de Cálculo");
 
        int userSelection = fileChooser.showOpenDialog(interfaz);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToOpen = fileChooser.getSelectedFile();
            try (FileInputStream fis = new FileInputStream(fileToOpen);
                 ObjectInputStream ois = new ObjectInputStream(fis)) {
                hojas = (LinkedList<HojaDeCalculo>) ois.readObject();
                hojaActual = 0; // Restablecer a la primera hoja cargada
                interfaz.actualizarTabla(getHojaActual());
                JOptionPane.showMessageDialog(interfaz, "Datos cargados exitosamente.");
            } catch (IOException | ClassNotFoundException e) {
                JOptionPane.showMessageDialog(interfaz, "Error al cargar los datos: " + e.getMessage());
            }
        }
    }
 
    //Metodo para habilitar las tablas hash
    private void mostrarTablaHash() {
        // Crear una ventana nueva para la tabla hash
        JFrame hashFrame = new JFrame("Tabla Hash");
        hashFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        hashFrame.setSize(400, 300);
 
        JTable hashTable = new JTable(20, 2); // Tabla con dos columnas: clave y hash
        hashFrame.add(new JScrollPane(hashTable), BorderLayout.CENTER);
 
        JButton hashButton = new JButton("Generar Hash");
        hashFrame.add(hashButton, BorderLayout.SOUTH);
 
        hashButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarTablaHash(hashTable);
            }
        });
        hashFrame.setVisible(true);
    }
    //Genera la tabla hash
    private void generarTablaHash(JTable tablaHash) {
        TablaHash nuevaTablaHash = new TablaHash(20); // Crear una tabla hash con tamaño predeterminado
 
        for (int i = 0; i < tablaHash.getRowCount(); i++) {
            String claveString = (String) tablaHash.getValueAt(i, 0);
            if (claveString != null && !claveString.isEmpty()) {
                try {
                    int clave = Integer.parseInt(claveString);
                    nuevaTablaHash.insertar(clave);
                    System.out.println("Insertado: " + clave); // Depuración
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(interfaz, "Por favor, ingrese valores válidos en la columna de clave.");
                }
            }
        }
 
        // Mostrar los índices generados en la segunda columna
        for (int i = 0; i < tablaHash.getRowCount(); i++) {
            String claveString = (String) tablaHash.getValueAt(i, 0);
            if (claveString != null && !claveString.isEmpty()) {
                try {
                    int clave = Integer.parseInt(claveString);
                    int indice = nuevaTablaHash.buscar(clave);
                    tablaHash.setValueAt(indice, i, 1);
                    System.out.println("Clave: " + clave + ", Índice Hash: " + indice); // Depuración
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(interfaz, "Por favor, ingrese valores válidos en la columna de clave.");
                }
            }
        }
    }
    private void mostrarInformacion() {
        String mensaje = "PROYECTO 3 - PROGRAMACION 3\n" +
                "Aplicación de Hoja de Cálculo\n"
                + "Desarrollado por: Jean Castañeda, Jonathan Chán y Roberto Ramírez\n"
                + "Versión: 1.0\n"
                + "Descripción: Esta es una aplicación de hoja de cálculo básica con soporte para operaciones aritméticas básicas (Suma, Resta, Multiplicación y División).";
        JOptionPane.showMessageDialog(interfaz, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private String convertirAPosfija(String expresionMatematica) {
        StringBuilder posfija = new StringBuilder();//Construye la expresion de forma dinamica
        Deque<Character> operadores = new ArrayDeque<>();//Controla la pila de los operadores
        boolean ultimoFueOperador = true;//Permite ontener el ultimo caracter analizado si es operador 
        //Recorre cada caracter de la expresion 
        for (int i = 0; i < expresionMatematica.length(); i++) {
            char caracter = expresionMatematica.charAt(i);//Caracter en la posicion actual
            if (Character.isDigit(caracter) || (caracter == '-' && ultimoFueOperador)) {//Valida si el caracter es digito o - despues de un operador
                if (caracter == '-') {//Manejo de numeros negativos
                    posfija.append(caracter);//Agrega el signo negativo a la expresion posfija
                    i++;
                    caracter = expresionMatematica.charAt(i);//Obtiene el siguiente caracter
                    //Obtiene un numero completo incluyendo . decimal
                    while (i < expresionMatematica.length() && (Character.isDigit(caracter) || caracter == '.')) {
                        posfija.append(caracter);
                        i++;
                        if (i < expresionMatematica.length())
                            caracter = expresionMatematica.charAt(i);
                    }
                    posfija.append(" ");//Agrega un espacio despues del numero
                    i--;//Ajusta el indice
                } else {
                    //Se repite el proceso anterior
                    while (i < expresionMatematica.length() && (Character.isDigit(caracter) || caracter == '.')) {
                        posfija.append(caracter);
                        i++;
                        if (i < expresionMatematica.length())
                            caracter = expresionMatematica.charAt(i);
                    }
                    posfija.append(" ");
                    i--;
                }
                ultimoFueOperador = false;
            } else if (Character.isLetter(caracter)) {
                // Manejar referencias a celdas y no solo numeros 
                StringBuilder referencia = new StringBuilder();
                while (i < expresionMatematica.length() && (Character.isLetterOrDigit(expresionMatematica.charAt(i)))) {
                    referencia.append(expresionMatematica.charAt(i));
                    i++;
                }
                posfija.append(referencia.toString()).append(" ");
                i--;
                ultimoFueOperador = false;
                //Controla los parentesis para que se ingresen de forma correcta apretura - cierre
            } else if (caracter == '(') {
                operadores.push(caracter);
                ultimoFueOperador = true;
            } else if (caracter == ')') {
                //Si el ultimo caracter es de cierre los desapila hasta encontrar su correspondiente 
                while (!operadores.isEmpty() && operadores.peek() != '(') {
                    posfija.append(operadores.pop()).append(" ");
                }
                operadores.pop();
                ultimoFueOperador = false;
            } else {
                int prioridad = jerarquia(caracter);//Obtiene la jerarquia de los operadores
                //Desapila los operadores de igual o menor jerarquia y los agrega a la expresion posfija
                while (!operadores.isEmpty() && jerarquia(operadores.peek()) >= prioridad) {
                    posfija.append(operadores.pop()).append(" ");
                }
                operadores.push(caracter);//Agrega operadores a la pila
                ultimoFueOperador = true;//Indica que el ultimo caracter fue operador
            }
        }
        //Desapila cualquier operador y lo agrega a la expresion 
        while (!operadores.isEmpty()) {
            posfija.append(operadores.pop()).append(" ");
        }
        return posfija.toString().trim();//Devuelve la expresion en forma de cadena
    }
    
    //Establece la jerarquia de los operadores para resolver la expresion 
    private int jerarquia(char operador) {
        switch (operador) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
            default:
                return 0;
        }
    }
    
    private double evaluarPosfija(String expresionPosfija) {
        Deque<Double> pila = new ArrayDeque<>();//Pila para almacenar los operandos
        String[] tokens = expresionPosfija.split(" ");//Divide la expresion en tokens (Caracteres)
        for (String token : tokens) {//Recorre cada uno de los tokens
            if (isNumeric(token)) {//Si el token es numero lo agrega a la pila
                pila.push(Double.parseDouble(token));
                //Si el token es una referencia de celda
            } else if (Character.isLetter(token.charAt(0))) {
                int[] indices = Referencias.convertirReferencia(token);
                Object valor = getHojaActual().getValor(indices[0], indices[1]);
                //Verifica si el valor de la celda de referencia es numero
                if (valor instanceof Number) {
                    pila.push(((Number) valor).doubleValue());
                } else {
                    throw new NumberFormatException("La celda " + token + " no contiene un número.");
                }
            } else {//Si el token es operador(SIGNO) saca dos operandos de la pila y realiza la operacion que corresponda segun el signo
                double b = pila.pop();//operando b
                double a = pila.pop();//operando a
                switch (token.charAt(0)) {
                    case '+':
                        pila.push(a + b);
                        break;
                    case '-':
                        pila.push(a - b);
                        break;
                    case '*':
                        pila.push(a * b);
                        break;
                    case '/':
                        pila.push(a / b);
                        break;
                    case '^':
                        pila.push(Math.pow(a, b));
                        break;
                }
            }
        }
        //El resultado final es el unico valor en la pila
        return pila.pop();
    }
    //Funcion para validar que sean numeros
    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}