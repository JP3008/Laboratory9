package controller;

import Util.Utility;
import domain.AVLBST;
import domain.BST;
import domain.BTreeNode;
import domain.TreeException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;

public class TransversalTour {
    @FXML
    private Pane pane;

    private BST bst;
    private AVLBST avlbst;

    private Alert alert = new Alert(Alert.AlertType.ERROR);
    @FXML
    private TextField textFieldArbolAVL;
    @FXML
    private TextField textFieldArbolBST;
    @FXML
    private RadioButton arbolBST;
    @FXML
    private RadioButton arbolAVL;
    @FXML
    private Text textRecorrido;

    private static final int NODE_RADIUS = 20;
    private static final int VERTICAL_GAP = 100;
    private ToggleGroup seleccionUnaVez;

    @FXML
    public void initialize() {
        bst = new BST();

        //ToggleGroup garantiza que solo se pueda seleccionar un control a la vez
        seleccionUnaVez = new ToggleGroup();
        arbolBST.setToggleGroup(seleccionUnaVez);
        arbolAVL.setToggleGroup(seleccionUnaVez);
    }

    private void drawTree() {
        pane.getChildren().clear(); // limpia el panel antes de dibujar otro arbol

        double paneWidth = pane.getWidth();
        if (arbolBST.isSelected()) {
            drawTreeRecursively(bst.getRoot(), paneWidth / 2, NODE_RADIUS * 2, paneWidth / 4);
        } else if (arbolAVL.isSelected()) {
            drawTreeRecursively(avlbst.getRoot(), paneWidth / 2, NODE_RADIUS * 2, paneWidth / 4);
        }
    }

    private void drawTreeRecursively(BTreeNode node, double x, double y, double hGap) {
        if (node == null) {
            return;
        }

        Circle circle = new Circle(x, y, NODE_RADIUS);
        circle.setFill(Color.WHITE);
        circle.setStroke(Color.BLACK);

        Text text = new Text(x - 4, y + 4, node.data.toString());

        pane.getChildren().addAll(circle, text);

        if (node.left != null) {
            double childX = x - hGap;
            double childY = y + VERTICAL_GAP;

            Line line = new Line(x, y + NODE_RADIUS, childX, childY - NODE_RADIUS);
            pane.getChildren().add(line);
            drawTreeRecursively(node.left, childX, childY - 35, hGap / 2);
        }

        if (node.right != null) {
            double childX = x + hGap;
            double childY = y + VERTICAL_GAP;

            Line line = new Line(x, y + NODE_RADIUS, childX, childY - NODE_RADIUS);
            pane.getChildren().add(line);
            drawTreeRecursively(node.right, childX, childY - 35, hGap / 2);
        }
    }

    @FXML
    public void RandomizeButton(ActionEvent actionEvent) throws TreeException {
        if (arbolBST.isSelected()) {
            bst = new BST();
            int numberOfNodes = Utility.getRandom(24);
            for (int i = 0; i < numberOfNodes; i++) {
                bst.add(Utility.getRandom(100)); // Usando valores aleatorios entre 0 y 99
            }
            drawTree();
        } else if (arbolAVL.isSelected()) {
            avlbst = new AVLBST();
            int numberOfNodes = Utility.getRandom(24);
            for (int i = 0; i < numberOfNodes; i++) {
                avlbst.add(Utility.getRandom(100)); // Usando valores aleatorios entre 0 y 99
            }
            drawTree();
        }

    }

    @FXML
    public void preOrderOnAction(ActionEvent actionEvent) {
        pane.getChildren().clear(); // Limpia el panel antes de dibujar otro árbol
        double paneWidth = pane.getWidth();
        int[] counter = new int[1]; // Arreglo de un solo elemento para mantener el contador
        counter[0] = 1; // Ponemos el contador en 1 para el primer nodo, porque por defecto en Java lo tira en 0

        if (arbolBST.isSelected()) {
            drawTreeRecursivelyPreOrder(bst.getRoot(), paneWidth / 2, 2 * NODE_RADIUS, paneWidth / 4, counter);
        } else if (arbolAVL.isSelected()) {
            drawTreeRecursivelyPreOrder(avlbst.getRoot(), paneWidth / 2, 2 * NODE_RADIUS, paneWidth / 4, counter);
        }

        if (arbolBST.isSelected()){
            textRecorrido.setText("BST Pre Order Transversal Tour (N-L-R)");
        }else if (arbolAVL.isSelected()){
            textRecorrido.setText("AVl Pre Order Transversal Tour (N-L-R)");
        }
    }

    private void drawTreeRecursivelyPreOrder(BTreeNode node, double x, double y, double hGap, int[] counter) {
        if (node == null) {
            return;
        }

        Circle circle = new Circle(x, y, NODE_RADIUS);
        circle.setFill(Color.WHITE);
        circle.setStroke(Color.BLACK);

        Text text = new Text(x - 4, y + 4, node.data.toString());

        // Muestra el número de orden en el recorrido preorden debajo de cada circulito/nodo
        Text orderText = new Text(x - 4, y + 38, Integer.toString(counter[0]));
        counter[0]++;

        pane.getChildren().addAll(circle, text, orderText);

        if (node.left != null) {
            double childX = x - hGap;
            double childY = y + VERTICAL_GAP;

            Line line = new Line(x, y + NODE_RADIUS, childX, childY - NODE_RADIUS);
            pane.getChildren().add(line);
            drawTreeRecursivelyPreOrder(node.left, childX, childY - 35, hGap / 2, counter);
        }

        if (node.right != null) {
            double childX = x + hGap;
            double childY = y + VERTICAL_GAP;

            Line line = new Line(x, y + NODE_RADIUS, childX, childY - NODE_RADIUS);
            pane.getChildren().add(line);
            drawTreeRecursivelyPreOrder(node.right, childX, childY - 35, hGap / 2, counter);
        }
    }

    @FXML
    public void postOrderOnAction(ActionEvent actionEvent) {
        if (bst != null || avlbst != null) {
            pane.getChildren().clear();
            double paneWidth = pane.getWidth();
            int[] counter = {1}; // contador para nodos visitados

            if (arbolBST.isSelected()) {
                drawTreeRecursivelyPostOrder(bst.getRoot(), paneWidth / 2, 2 * NODE_RADIUS, paneWidth / 4, counter);
            } else if (arbolAVL.isSelected()) {
                drawTreeRecursivelyPostOrder(avlbst.getRoot(), paneWidth / 2, 2 * NODE_RADIUS, paneWidth / 4, counter);
            }

            if (arbolBST.isSelected()){
                textRecorrido.setText("BST Post Order Transversal Tour (L-R-N)");
            }else if (arbolAVL.isSelected()){
                textRecorrido.setText("AVl Post Order Transversal Tour (L-R-N)");
            }
        }
    }

    private void drawTreeRecursivelyPostOrder(BTreeNode node, double x, double y, double hGap, int[] counter) {
        if (node == null) {
            return;
        }

        // Traza los hijos izquierdo y derecho primero (postorden)
        drawTreeRecursivelyPostOrder(node.left, x - hGap, y + VERTICAL_GAP, hGap / 2, counter);
        drawTreeRecursivelyPostOrder(node.right, x + hGap, y + VERTICAL_GAP, hGap / 2, counter);

        // Luego dibuja el nodo actual
        Circle circle = new Circle(x, y, NODE_RADIUS);
        circle.setFill(Color.WHITE);
        circle.setStroke(Color.BLACK);

        Text text = new Text(x - 4, y + 4, node.data.toString());

        // Tira el número de orden en el recorrido postorden debajo de cada nodo
        Text orderText = new Text(x - 4, y + 36, Integer.toString(counter[0]));
        counter[0]++;

        pane.getChildren().addAll(circle, text, orderText);

        // Pone línea desde el nodo hasta su hijo izquierdo
        if (node.left != null) {
            Line lineLeft = new Line(x, y + NODE_RADIUS, x - hGap, y + VERTICAL_GAP - NODE_RADIUS);
            pane.getChildren().add(lineLeft);
        }

        // Pone línea desde el nodo hasta su hijo derecho
        if (node.right != null) {
            Line lineRight = new Line(x, y + NODE_RADIUS, x + hGap, y + VERTICAL_GAP - NODE_RADIUS);
            pane.getChildren().add(lineRight);
        }
    }

    @FXML
    public void inOrderOnAction(ActionEvent actionEvent) {
        if (bst != null || avlbst != null) {
            pane.getChildren().clear(); // Limpia el panel antes de dibujar otro árbol
            double paneWidth = pane.getWidth();
            int[] counter = {1}; // ve los nodos visitados

            if (arbolBST.isSelected()) {
                drawTreeRecursivelyInOrder(bst.getRoot(), paneWidth / 2, 2 * NODE_RADIUS, paneWidth / 4, counter);
            } else if (arbolAVL.isSelected()) {
                drawTreeRecursivelyInOrder(avlbst.getRoot(), paneWidth / 2, 2 * NODE_RADIUS, paneWidth / 4, counter);
            }

            if (arbolBST.isSelected()){
                textRecorrido.setText("BST In Order Transversal Tour (L-N-R)");
            }else if (arbolAVL.isSelected()){
                textRecorrido.setText("AVl In Order Transversal Tour (L-N-R)");
            }
        }
    }

    private void drawTreeRecursivelyInOrder(BTreeNode node, double x, double y, double hGap, int[] counter) {
        if (node == null) {
            return;
        }

        // Dibuja el hijo izquierdo primero (inorden)
        drawTreeRecursivelyInOrder(node.left, x - hGap, y + VERTICAL_GAP, hGap / 2, counter);

        // Luego dibuja el nodo actual
        Circle circle = new Circle(x, y, NODE_RADIUS);
        circle.setFill(Color.WHITE);
        circle.setStroke(Color.BLACK);

        Text text = new Text(x - 4, y + 4, node.data.toString());

        // Tira el número de orden en el recorrido inorden debajo de cada nodo
        Text orderText = new Text(x - 4, y + 35, Integer.toString(counter[0]));
        counter[0]++;

        pane.getChildren().addAll(circle, text, orderText);

        // Tira la línea desde el nodo hasta su hijo izquierdo
        if (node.left != null) {
            Line lineLeft = new Line(x, y + NODE_RADIUS, x - hGap, y + VERTICAL_GAP - NODE_RADIUS);
            pane.getChildren().add(lineLeft);
        }

        // Tira la línea desde el nodo hasta su hijo derecho
        if (node.right != null) {
            Line lineRight = new Line(x, y + NODE_RADIUS, x + hGap, y + VERTICAL_GAP - NODE_RADIUS);
            pane.getChildren().add(lineRight);
        }

        // Pone el hijo derecho después (inorden)
        drawTreeRecursivelyInOrder(node.right, x + hGap, y + VERTICAL_GAP, hGap / 2, counter);
    }
}



