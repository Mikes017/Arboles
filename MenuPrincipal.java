import java.util.Scanner;

import javax.swing.SwingUtilities;

public class MenuPrincipal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\nMenú Principal:");
            System.out.println("1. Arbol AVL");
            System.out.println("2. Arbol Red-Black");
            System.out.println("3. Arbol de Expresión Aritmética");
            System.out.println("4. Salir");
            System.out.print("Ingrese su opción: ");
             opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    System.out.println("\n--- Arbol AVL ---");
                    menuAVL();
                    break;
                case 2:
                    System.out.println("\n--- Arbol Red-Black ---");
                    menuRedBlack();
                    break;
                case 3:
                    System.out.println("\n--- Arbol de Expresión Aritmética ---");
                    menuArbolExpresion();
                    break;
                case 4:
                    System.out.println("¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, ingrese una opción válida.");
                    break;
            }
        }while(opcion!=4);
    }

    public static void menuAVL() {
        ArbolAVL arbol = new ArbolAVL();
        Scanner scanner = new Scanner(System.in);
        int opcion;
    
        do {
            System.out.println("\n--- Menu AVL ---");
            System.out.println("1. Agregar clave");
            System.out.println("2. Buscar un valor");
            System.out.println("3. Eliminar clave");
            System.out.println("4. Mostrar arbol en ventana");
            System.out.println("5. Salir");
            System.out.print("Ingrese su opción: ");
            opcion = scanner.nextInt();
    
            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el valor a agregar: ");
                    int valorAgregar = scanner.nextInt();
                    arbol.insertarNodo(valorAgregar);
                    break;
                case 2:
                    System.out.print("Ingrese el valor a buscar: ");
                    int valorBuscar = scanner.nextInt();
                    NodoAVL nodo = arbol.buscarNodo(valorBuscar);
                    if (nodo != null) {
                        System.out.println("El valor " + valorBuscar + " está presente en el árbol.");
                    } else {
                        System.out.println("El valor " + valorBuscar + " no está presente en el árbol.");
                    }
                    break;
                case 3:
                    System.out.print("Ingrese el valor a eliminar: ");
                    int valorEliminar = scanner.nextInt();
                    arbol.eliminarNodo(valorEliminar);
                    break;
                case 4:
                    SwingUtilities.invokeLater(() -> {
                        AVLTreeGraphic frame = new AVLTreeGraphic(arbol);
                        frame.setVisible(true);
                    });
                    break;
                case 5:
                    System.out.println("Adiós");
                    break;
                default:
                    System.out.println("Opción inválida. Inténtelo de nuevo.");
                    break;
            }
        } while (opcion != 5);
    }
    
    
    

public static void menuRedBlack() {
    Scanner scanner = new Scanner(System.in);
    ArbolRojoNegro arbol = new ArbolRojoNegro();
    int opcion;

    do {
        System.out.println("\nMenú Árbol Red-Black:");
        System.out.println("1. Agregar clave");
        System.out.println("2. Eliminar clave");
        System.out.println("3. Mostrar árbol en ventana");
        System.out.println("4. Salir");
        System.out.print("Ingrese su opción: ");
        opcion = scanner.nextInt();

        switch (opcion) {
            case 1:
                System.out.print("Ingrese el valor a agregar: ");
                int valorAgregar = scanner.nextInt();
                arbol.insertarNodo(valorAgregar);
                System.out.println("Valor " + valorAgregar + " agregado al árbol Red-Black.");
                break;
            case 2:
                System.out.print("Ingrese el valor a eliminar: ");
                int valorEliminar = scanner.nextInt();
                arbol.eliminarNodo(valorEliminar);
                System.out.println("Valor " + valorEliminar + " eliminado del árbol Red-Black.");
                break;
            case 3:
                SwingUtilities.invokeLater(() -> {
                    RedBlackTreeGraphic frame = new RedBlackTreeGraphic(arbol);
                    frame.setVisible(true);
                });
                break;
                
            case 4:
                System.out.println("¡Hasta luego!");
                
                break;
                
            default:
                System.out.println("Opción inválida. Por favor, ingrese una opción válida.");
                break;
        }
    }while(opcion!=4);
}



public static void menuArbolExpresion() {
    ArbolExpresion arbolExpresion = new ArbolExpresion();

    Scanner scanner = new Scanner(System.in);
    int opcion;

    do {
        System.out.println("\n--- Submenú Arbol de Expresión ---");
        System.out.println("1. Construir árbol de expresión");
        System.out.println("2. Resolver expresión");
        System.out.println("3. Obtener notación postfija");
        System.out.println("4. Ver arbol en ventana");
        System.out.println("5. Volver al menú principal");
        System.out.print("Ingrese su opción: ");
        opcion = scanner.nextInt();

        switch (opcion) {
            case 1:
                scanner.nextLine(); // Consumir el salto de línea
                System.out.print("Ingrese la expresión matemática: ");
                String expresion = scanner.nextLine();
                arbolExpresion.construirArbol(expresion);
                break;
            case 2:
                int resultado = arbolExpresion.resolverExpresionConPilas(arbolExpresion.raiz);
                System.out.println("Resultado de la expresión: " + resultado);
                break;
              
            case 3:
                String notacionPostfija = arbolExpresion.obtenerNotacionPostfija();
                System.out.println("Notación postfija: " + notacionPostfija);
                break;
            case 4:
                SwingUtilities.invokeLater(() -> {
                    ArithmeticExpressionTreeGraphic frame = new ArithmeticExpressionTreeGraphic(arbolExpresion);
                    frame.setVisible(true);
                });
            case 5:
                System.out.println("Volviendo al menú principal...");
                break;    
            default:
                System.out.println("Opción inválida. Inténtelo de nuevo.");
        }
    } while (opcion != 5);
}

}