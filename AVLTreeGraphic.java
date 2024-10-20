import javax.swing.*;
import java.awt.*;

import javax.swing.*;
import java.awt.*;

public class AVLTreeGraphic extends JFrame {

    private JPanel canvas;
    private ArbolAVL arbolAVL;

    public AVLTreeGraphic(ArbolAVL arbolAVL) {
        super("Árbol AVL Gráfico");
        this.arbolAVL = arbolAVL;
        this.canvas = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawTree(g, arbolAVL.raiz, 0, getWidth(), 50);
            }
        };
        this.getContentPane().add(canvas);
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public int drawTree(Graphics g, NodoAVL nodo, int x0, int x1, int y) {
        if (nodo == null)
            return x0;

        int m = (x0 + x1) / 2;
        g.setColor(Color.BLUE);
        g.fillOval(m, y, 50, 40);
        g.setColor(Color.lightGray);
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
        ArbolAVL arbolAVL = new ArbolAVL();

        // Inserta valores al árbol AVL
        int[] valoresInsercion = {20, 15, 25, 10, 5, 1, 30, 35, 40, 45,2,3,4,6,7};
        for (int valor : valoresInsercion) {
            arbolAVL.insertarNodo(valor);
        }

        // Crea una instancia de AVLTreeGraphic y hazla visible
        SwingUtilities.invokeLater(() -> {
            AVLTreeGraphic frame = new AVLTreeGraphic(arbolAVL);
            frame.setVisible(true);
        });
    }
}
