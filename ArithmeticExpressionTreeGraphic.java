import javax.swing.*;
import java.awt.*;

public class ArithmeticExpressionTreeGraphic extends JFrame {

    private JPanel canvas;
    private ArbolExpresion expressionTree;

    public ArithmeticExpressionTreeGraphic(ArbolExpresion expressionTree) {
        super("Árbol de Expresión Aritmética Gráfico");
        this.expressionTree = expressionTree;
        this.canvas = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawTree(g, expressionTree.raiz, 0, getWidth(), 50);
            }
        };
        this.getContentPane().add(canvas);
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public int drawTree(Graphics g, NodoExpresion nodo, int x0, int x1, int y) {
        if (nodo == null)
            return x0;

        int m = (x0 + x1) / 2;
        g.setColor(Color.BLUE);
        g.fillOval(m, y, 50, 40);
        g.setColor(Color.lightGray);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        String t = String.valueOf(nodo.operador != '\0' ? nodo.operador : String.valueOf(nodo.valor));

        g.drawString(t, m + 20, y + 30);
        if (nodo.izq != null) {
            int x2 = drawTree(g, nodo.izq, x0, m, y + 50);
            g.drawLine(m + 25, y + 40, x2 + 25, y + 50);
        }
        if (nodo.der != null) {
            int x2 = drawTree(g, nodo.der, m, x1, y + 50);
            g.drawLine(m + 25, y + 40, x2 + 25, y + 50);
        }
        return m;
    }

    public static void main(String[] args) {
        ArbolExpresion expressionTree = new ArbolExpresion();

        // Construye el árbol de expresión a partir de una cadena
        expressionTree.construirArbol("3+2*(25/(2-1))");

        // Crea una instancia de ArithmeticExpressionTreeGraphic y hazla visible
        SwingUtilities.invokeLater(() -> {
            ArithmeticExpressionTreeGraphic frame = new ArithmeticExpressionTreeGraphic(expressionTree);
            frame.setVisible(true);
        });
    }
}
