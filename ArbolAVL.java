public class ArbolAVL {

  public NodoAVL raiz;

  public NodoAVL buscarNodo(int clave) {
      return buscarNodo(clave, raiz);
  }

  private NodoAVL buscarNodo(int clave, NodoAVL nodo) {
      if (nodo == null) {
          return null;
      }

      if (clave == nodo.dato) {
          return nodo;
      } else if (clave < nodo.dato) {
          return buscarNodo(clave, nodo.izquierda);
      } else {
          return buscarNodo(clave, nodo.derecha);
      }
  }

  public void insertarNodo(int clave) {
      raiz = insertarNodo(clave, raiz);
  }

  private NodoAVL insertarNodo(int clave, NodoAVL nodo) {
      if (nodo == null) {
          nodo = new NodoAVL(clave);
      } else if (clave < nodo.dato) {
          nodo.izquierda = insertarNodo(clave, nodo.izquierda);
      } else if (clave > nodo.dato) {
          nodo.derecha = insertarNodo(clave, nodo.derecha);
      } else {
          throw new IllegalArgumentException("El árbol binario de búsqueda ya contiene un nodo con clave " + clave);
      }

      actualizarAltura(nodo);
      return reequilibrar(nodo);
  }

  public void eliminarNodo(int clave) {
      raiz = eliminarNodo(clave, raiz);
  }

  private NodoAVL eliminarNodo(int clave, NodoAVL nodo) {
      if (nodo == null) {
          return null;
      }

      if (clave < nodo.dato) {
          nodo.izquierda = eliminarNodo(clave, nodo.izquierda);
      } else if (clave > nodo.dato) {
          nodo.derecha = eliminarNodo(clave, nodo.derecha);
      } else {
          if (nodo.izquierda == null && nodo.derecha == null) {
              nodo = null;
          } else if (nodo.izquierda == null) {
              nodo = nodo.derecha;
          } else if (nodo.derecha == null) {
              nodo = nodo.izquierda;
          } else {
              eliminarNodoConDosHijos(nodo);
          }
      }

      if (nodo != null) {
          actualizarAltura(nodo);
          nodo = reequilibrar(nodo);
      }

      return nodo;
  }

  private void eliminarNodoConDosHijos(NodoAVL nodo) {
      NodoAVL sucesorInOrden = encontrarMinimo(nodo.derecha);
      nodo.dato = sucesorInOrden.dato;
      nodo.derecha = eliminarNodo(sucesorInOrden.dato, nodo.derecha);
  }

  private NodoAVL encontrarMinimo(NodoAVL nodo) {
      while (nodo.izquierda != null) {
          nodo = nodo.izquierda;
      }
      return nodo;
  }

  private void actualizarAltura(NodoAVL nodo) {
      int alturaIzquierda = altura(nodo.izquierda);
      int alturaDerecha = altura(nodo.derecha);
      nodo.altura = Math.max(alturaIzquierda, alturaDerecha) + 1;
  }

  private NodoAVL reequilibrar(NodoAVL nodo) {
      int factorEquilibrio = factorEquilibrio(nodo);

      if (factorEquilibrio < -1) {
          if (factorEquilibrio(nodo.izquierda) <= 0) {
              nodo = rotarDerecha(nodo);
          } else {
              nodo.izquierda = rotarIzquierda(nodo.izquierda);
              nodo = rotarDerecha(nodo);
          }
      }

      if (factorEquilibrio > 1) {
          if (factorEquilibrio(nodo.derecha) >= 0) {
              nodo = rotarIzquierda(nodo);
          } else {
              nodo.derecha = rotarDerecha(nodo.derecha);
              nodo = rotarIzquierda(nodo);
          }
      }

      return nodo;
  }

  private NodoAVL rotarDerecha(NodoAVL nodo) {
      NodoAVL hijoIzquierdo = nodo.izquierda;

      nodo.izquierda = hijoIzquierdo.derecha;
      hijoIzquierdo.derecha = nodo;

      actualizarAltura(nodo);
      actualizarAltura(hijoIzquierdo);

      return hijoIzquierdo;
  }

  private NodoAVL rotarIzquierda(NodoAVL nodo) {
      NodoAVL hijoDerecho = nodo.derecha;

      nodo.derecha = hijoDerecho.izquierda;
      hijoDerecho.izquierda = nodo;

      actualizarAltura(nodo);
      actualizarAltura(hijoDerecho);

      return hijoDerecho;
  }

  private int factorEquilibrio(NodoAVL nodo) {
      return altura(nodo.derecha) - altura(nodo.izquierda);
  }

  private int altura(NodoAVL nodo) {
      return nodo != null ? nodo.altura : -1;
  }
  

  
}
