import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class ArbolExpresion {
    public NodoExpresion raiz;

    public ArbolExpresion() {
        raiz = null;
    }

    private int precedencia(char operador) {
        switch (operador) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return 0;
        }
    }

    private boolean esOperador(char caracter) {
        return caracter == '+' || caracter == '-' || caracter == '*' || caracter == '/';
    }

    public NodoExpresion construirArbol(String expresion) {
        Stack<NodoExpresion> pilaNodos = new Stack<>();
        Stack<Character> pilaOperadores = new Stack<>();

        for (int i = 0; i < expresion.length(); i++) {
            char caracter = expresion.charAt(i);

            if (caracter == '(') {
                pilaOperadores.push(caracter);
            } else if (Character.isDigit(caracter)) {
                StringBuilder numero = new StringBuilder();
                while (i < expresion.length() && Character.isDigit(expresion.charAt(i))) {
                    numero.append(expresion.charAt(i++));
                }
                i--;
                pilaNodos.push(new NodoExpresion(Integer.parseInt(numero.toString())));
            } else if (esOperador(caracter)) {
                while (!pilaOperadores.isEmpty() && precedencia(pilaOperadores.peek()) >= precedencia(caracter)) {
                    construirSubarbol(pilaNodos, pilaOperadores);
                }
                pilaOperadores.push(caracter);
            } else if (caracter == ')') {
                while (!pilaOperadores.isEmpty() && pilaOperadores.peek() != '(') {
                    construirSubarbol(pilaNodos, pilaOperadores);
                }
                pilaOperadores.pop(); // Eliminar el '('
            }
        }

        while (!pilaOperadores.isEmpty()) {
            construirSubarbol(pilaNodos, pilaOperadores);
        }

        raiz = pilaNodos.peek();
        return raiz;
    }

    private void construirSubarbol(Stack<NodoExpresion> pilaNodos, Stack<Character> pilaOperadores) {
        char operador = pilaOperadores.pop();
        NodoExpresion derecho = pilaNodos.pop();
        NodoExpresion izquierdo = pilaNodos.pop();
        NodoExpresion nuevoNodo = new NodoExpresion(operador);
        nuevoNodo.izq = izquierdo;
        nuevoNodo.der = derecho;
        pilaNodos.push(nuevoNodo);
    }

  

    public int resolverExpresionConPilas(NodoExpresion raiz) {
        if (raiz == null) {
            return 0;
        }

        Stack<Integer> pila = new Stack<>();
        resolverExpresionPostorden(raiz, pila);

        return pila.pop();
    }

    private void resolverExpresionPostorden(NodoExpresion nodo, Stack<Integer> pila) {
        if (nodo == null) {
            return;
        }

        resolverExpresionPostorden(nodo.izq, pila);
        resolverExpresionPostorden(nodo.der, pila);

        if (nodo.operador != '\0') {
            int derecha = pila.pop();
            int izquierda = pila.pop();

            switch (nodo.operador) {
                case '+':
                    pila.push(izquierda + derecha);
                    break;
                case '-':
                    pila.push(izquierda - derecha);
                    break;
                case '*':
                    pila.push(izquierda * derecha);
                    break;
                case '/':
                    if (derecha != 0) {
                        pila.push(izquierda / derecha);
                    } else {
                        throw new ArithmeticException("División por cero");
                    }
                    break;
            }
        } else {
            pila.push(nodo.valor);
        }
    }

    public String obtenerNotacionPostfija() {
        StringBuilder sb = new StringBuilder();
        obtenerNotacionPostfija(raiz, sb);
        return sb.toString().trim(); // Eliminamos el espacio adicional al final
    }
    
    private void obtenerNotacionPostfija(NodoExpresion nodo, StringBuilder sb) {
        if (nodo != null) {
            obtenerNotacionPostfija(nodo.izq, sb);
            obtenerNotacionPostfija(nodo.der, sb);
            if (nodo.operador != '\0') {
                sb.append(nodo.operador).append(" ");
            } else {
                sb.append(nodo.valor).append(" ");
            }
        }
    }

    

    public static void main(String[] args) {
        ArbolExpresion arbol = new ArbolExpresion();
        NodoExpresion raiz = arbol.construirArbol("3+5*2");
        System.out.println("Árbol de expresión:");
        int resultado = arbol.resolverExpresionConPilas(raiz);
        System.out.println("Resultado: " + resultado);
        String notacionPostfija = arbol.obtenerNotacionPostfija();
        System.out.println("Notación postfija: " + notacionPostfija);
    }
}
