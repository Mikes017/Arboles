import java.util.LinkedList;
import java.util.Queue;

public class ArbolRojoNegro {

    public static boolean ROJO = false;
    public static boolean NEGRO = true;
  
    public NodoLion raiz;
  
    public NodoLion buscarNodo(int clave) {
        NodoLion nodo = raiz;
        while (nodo != null) {
            if (clave == nodo.dato) {
                return nodo;
            } else if (clave < nodo.dato) {
                nodo = nodo.izquierda;
            } else {
                nodo = nodo.derecha;
            }
        }
        return null;
    }
  
    public void insertarNodo(int clave) {
        NodoLion nodo = raiz;
        NodoLion padre = null;
    
        while (nodo != null) {
            padre = nodo;
            if (clave < nodo.dato) {
                nodo = nodo.izquierda;
            } else if (clave > nodo.dato) {
                nodo = nodo.derecha;
            } else {
                throw new IllegalArgumentException("El árbol binario de búsqueda ya contiene un nodo con la clave " + clave);
            }
        }
    
        NodoLion nuevoNodo = new NodoLion(clave);
        nuevoNodo.color = ROJO;
        if (padre == null) {
            raiz = nuevoNodo;
        } else if (clave < padre.dato) {
            padre.izquierda = nuevoNodo;
        } else {
            padre.derecha = nuevoNodo;
        }
        nuevoNodo.padre = padre;
    
        arreglarPropiedadesRojoNegroDespuesDeInsertar(nuevoNodo);
        raiz.color = NEGRO; 
    }
  
    private void arreglarPropiedadesRojoNegroDespuesDeInsertar(NodoLion nodo) {
        NodoLion padre = nodo.padre;
    
        if (padre == null) {
            return;
        }
    
        if (padre.color == NEGRO) {
            return;
        }
    
        NodoLion abuelo = padre.padre;
    
        if (abuelo == null) {
            padre.color = NEGRO;
            return;
        }
    
        NodoLion tio = obtenerTio(padre);
    
        if (tio != null && tio.color == ROJO) {
            padre.color = NEGRO;
            abuelo.color = ROJO;
            tio.color = NEGRO;
            arreglarPropiedadesRojoNegroDespuesDeInsertar(abuelo);
        } else {
            if (padre == abuelo.izquierda) {
                if (nodo == padre.derecha) {
                    rotarIzquierda(padre);
                    padre = nodo;
                }
                rotarDerecha(abuelo);
                padre.color = NEGRO;
                abuelo.color = ROJO;
            } else {
                if (nodo == padre.izquierda) {
                    rotarDerecha(padre);
                    padre = nodo;
                }
                rotarIzquierda(abuelo);
                padre.color = NEGRO;
                abuelo.color = ROJO;
            }
        }
    }
  
    private NodoLion obtenerTio(NodoLion padre) {
        NodoLion abuelo = padre.padre;
        if (abuelo.izquierda == padre) {
            return abuelo.derecha;
        } else if (abuelo.derecha == padre) {
            return abuelo.izquierda;
        } else {
            throw new IllegalStateException("El padre no es hijo de su abuelo");
        }
    }
  
    public void eliminarNodo(int clave) {
        NodoLion nodo;
    
        nodo=buscarNodo(clave);
    
        NodoLion nodoMovido;
        boolean colorNodoEliminado;
    
        if (nodo.izquierda == null || nodo.derecha == null) {
            nodoMovido = eliminarNodoConCeroOUnHijo(nodo);
            colorNodoEliminado = nodo.color;
        } else {
            NodoLion sucesorInOrden = encontrarMinimo(nodo.derecha);
            nodo.dato = sucesorInOrden.dato;
            nodoMovido = eliminarNodoConCeroOUnHijo(sucesorInOrden);
            colorNodoEliminado = sucesorInOrden.color;
        }
    
        if (colorNodoEliminado == NEGRO) {
            arreglarPropiedadesRojoNegroDespuesDeEliminar(nodoMovido);
    
            if (nodoMovido.getClass() == NillNodo.class) {
                reemplazarHijoDelPadre(nodoMovido.padre, nodoMovido, null);
            }
        }
    }
  
    private NodoLion eliminarNodoConCeroOUnHijo(NodoLion nodo) {
        if (nodo.izquierda != null) {
            reemplazarHijoDelPadre(nodo.padre, nodo, nodo.izquierda);
            return nodo.izquierda;
        } else if (nodo.derecha != null) {
            reemplazarHijoDelPadre(nodo.padre, nodo, nodo.derecha);
            return nodo.derecha;
        } else {
            NodoLion nuevoHijo = nodo.color == NEGRO ? new NillNodo() : null;
            reemplazarHijoDelPadre(nodo.padre, nodo, nuevoHijo);
            return nuevoHijo;
        }
    }
  
    private NodoLion encontrarMinimo(NodoLion nodo) {
        while (nodo.izquierda != null) {
            nodo = nodo.izquierda;
        }
        return nodo;
    }
  
    private void arreglarPropiedadesRojoNegroDespuesDeEliminar(NodoLion nodo) {
        if (nodo == raiz) {
            return;
        }
    
        NodoLion hermano = obtenerHermano(nodo);
    
        if (hermano.color == ROJO) {
            manejarHermanoRojo(nodo, hermano);
            hermano = obtenerHermano(nodo);
        }
    
        if (esNegro(hermano.izquierda) && esNegro(hermano.derecha)) {
            hermano.color = ROJO;
            if (nodo.padre.color == ROJO) {
                nodo.padre.color = NEGRO;
            } else {
                arreglarPropiedadesRojoNegroDespuesDeEliminar(nodo.padre);
            }
        } else {
            manejarHermanoConAlMenosUnHijoRojo(nodo, hermano);
        }
    }
  
    private void manejarHermanoRojo(NodoLion nodo, NodoLion hermano) {
        hermano.color = NEGRO;
        nodo.padre.color = ROJO;
        if (nodo == nodo.padre.izquierda) {
            rotarIzquierda(nodo.padre);
        } else {
            rotarDerecha(nodo.padre);
        }
    }
  
    private void manejarHermanoConAlMenosUnHijoRojo(NodoLion nodo, NodoLion hermano) {
        boolean nodoEsHijoIzquierdo = nodo == nodo.padre.izquierda;
    
        if (nodoEsHijoIzquierdo && esNegro(hermano.derecha)) {
            hermano.izquierda.color = NEGRO;
            hermano.color = ROJO;
            rotarDerecha(hermano);
            hermano = obtenerHermano(nodo);
        } else if (!nodoEsHijoIzquierdo && esNegro(hermano.izquierda)) {
            hermano.derecha.color = NEGRO;
            hermano.color = ROJO;
            rotarIzquierda(hermano);
            hermano = obtenerHermano(nodo);
        }
    
        hermano.color = nodo.padre.color;
        nodo.padre.color = NEGRO;
    
        if (nodoEsHijoIzquierdo) {
            hermano.derecha.color = NEGRO;
            rotarIzquierda(nodo.padre);
        } else {
            hermano.izquierda.color = NEGRO;
            rotarDerecha(nodo.padre);
        }
    }
  
    private NodoLion obtenerHermano(NodoLion nodo) {
        NodoLion padre = nodo.padre;
        if (nodo == padre.izquierda) {
            return padre.derecha;
        } else if (nodo == padre.derecha) {
            return padre.izquierda;
        } else {
            throw new IllegalStateException("El nodo no es hijo de su padre");
        }
    }
  
    private boolean esNegro(NodoLion nodo) {
        return nodo == null || nodo.color == NEGRO;
    }
  
    private void rotarDerecha(NodoLion nodo) {
        NodoLion padre = nodo.padre;
        NodoLion hijoIzquierdo = nodo.izquierda;
    
        nodo.izquierda = hijoIzquierdo.derecha;
        if (hijoIzquierdo.derecha != null) {
            hijoIzquierdo.derecha.padre = nodo;
        }
    
        hijoIzquierdo.derecha = nodo;
        nodo.padre = hijoIzquierdo;
    
        reemplazarHijoDelPadre(padre, nodo, hijoIzquierdo);
    }
  
    private void rotarIzquierda(NodoLion nodo) {
        NodoLion padre = nodo.padre;
        NodoLion hijoDerecho = nodo.derecha;
    
        nodo.derecha = hijoDerecho.izquierda;
        if (hijoDerecho.izquierda != null) {
            hijoDerecho.izquierda.padre = nodo;
        }
    
        hijoDerecho.izquierda = nodo;
        nodo.padre = hijoDerecho;
    
        reemplazarHijoDelPadre(padre, nodo, hijoDerecho);
    }
  
    private void reemplazarHijoDelPadre(NodoLion padre, NodoLion hijoViejo, NodoLion hijoNuevo) {
        if (padre == null) {
            raiz = hijoNuevo;
        } else if (padre.izquierda == hijoViejo) {
            padre.izquierda = hijoNuevo;
        } else if (padre.derecha == hijoViejo) {
            padre.derecha = hijoNuevo;
        } else {
            throw new IllegalStateException("El nodo no es hijo de su padre");
        }
    
        if (hijoNuevo != null) {
            hijoNuevo.padre = padre;
        }
    }
  
    
}
