public class NodoExpresion {
    char operador;
    int valor;
    NodoExpresion izq, der;

    public NodoExpresion(char operador) {
        this.operador = operador;
        this.valor = 0;
        this.izq = null;
        this.der = null;
    }

    public NodoExpresion(int valor) {
        this.operador = '\0';
        this.valor = valor;
        this.izq = null;
        this.der = null;
    }
}
