import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;


public class RedBlackTreeGraphic extends JFrame {

    private JPanel canvas;
    private ArbolRojoNegro arbolRojoNegro;

    public RedBlackTreeGraphic(ArbolRojoNegro arbolRojoNegro) {
        super("Arbol Rojo-Negro Grafico");
        this.arbolRojoNegro = arbolRojoNegro;
        this.canvas = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawTree(g, arbolRojoNegro.raiz, 0, getWidth(), 50);
            }
        };
        this.getContentPane().add(canvas);
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public int drawTree(Graphics g, NodoLion nodo, int x0, int x1, int y) {
        if (nodo == null)
            return x0;

        int m = (x0 + x1) / 2;
        g.setColor(nodo.color == ArbolRojoNegro.ROJO ? Color.RED : Color.BLACK);
        
        g.fillOval(m, y, 50, 40);
        
        g.setColor(Color.BLUE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        String t = String.valueOf(nodo.dato);
        g.drawString(t, m + 20, y + 30);

        if (nodo.izquierda != null) {
            int x2 = drawTree(g, nodo.izquierda, x0, m, y + 50);
            g.drawLine(m + 25, y + 40, x2 + 25, y + 50);
        }

        if (nodo.derecha != null) {
            int x2 = drawTree(g, nodo.derecha, m, x1, y + 50);
            g.drawLine(m + 25, y + 40, x2 + 25, y + 50);
        }

        return m;
    }

    public static void main(String[] args) {
        ArbolRojoNegro arbolRojoNegro = new ArbolRojoNegro();

        int[] valoresInsercion = {56, 100, 34, 23, 45,1,2,3,4,5,6};
        for (int valor : valoresInsercion) {
            arbolRojoNegro.insertarNodo(valor);
        }

        SwingUtilities.invokeLater(() -> {
            RedBlackTreeGraphic frame = new RedBlackTreeGraphic(arbolRojoNegro);
            frame.setVisible(true);
        });
    }
}
