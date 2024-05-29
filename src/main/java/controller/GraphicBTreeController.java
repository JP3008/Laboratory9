package controller;

import Util.Utility;
import domain.AVLBST;
import domain.BST;
import domain.BTreeNode;
import domain.TreeException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GraphicBTreeController {

    @FXML
    private Pane pane;

    private AVLBST avlbst;
    private BST bst;
    private Alert alert = new Alert(Alert.AlertType.ERROR);
    @FXML
    private RadioButton arbolBST;
    @FXML
    private RadioButton arbolAVL;

    @FXML
    public void initialize() throws TreeException {
        bst = new BST();
        generateInitialTree(); // Generar y dibujar el árbol inicial

        // Agregar un listener para dibujar el árbol cuando el pane cambie de tamaño
        pane.widthProperty().addListener((obs, oldVal, newVal) -> drawTree());
        pane.heightProperty().addListener((obs, oldVal, newVal) -> drawTree());
    }

    private void generateInitialTree() throws TreeException {
            int numberOfNodes = Utility.getRandom(5);
            for (int i = 0; i < numberOfNodes; i++) {
                bst.add(Utility.getRandom(100)); // Usando valores aleatorios entre 0 y 99
            }
            drawTree();

    }

    @FXML
    public void RandomizeButton() throws TreeException {
        if (arbolBST.isSelected()){
            bst= new BST();
            int numberOfNodes = Utility.getRandom(24);
            for (int i = 0; i < numberOfNodes; i++) {
                bst.add(Utility.getRandom(100)); // Usando valores aleatorios entre 0 y 99
            }
            drawTree();
        }
        if (arbolAVL.isSelected()) {
            avlbst = new AVLBST();
            int numberOfNodes = Utility.getRandom(24);
            for (int i = 0; i < numberOfNodes; i++) {
                avlbst.add(Utility.getRandom(100)); // Usando valores aleatorios entre 0 y 99
            }
            drawTree();
        }
    }


    @FXML
    public void levelsOnAction(ActionEvent actionEvent){
        drawTreeLevels();
    }
    private void drawTreeLevels() {
        pane.getChildren().clear(); // Clear the pane before drawing
        if (arbolAVL.isSelected()){
            double paneWidth = pane.getWidth();
            double paneHeight = pane.getHeight();

            drawTreeRecursivelyLevels(avlbst.getRoot(), paneWidth / 2, NODE_RADIUS * 2, paneWidth / 4, 0, paneWidth);
        }
        if (arbolBST.isSelected()) {

            double paneWidth = pane.getWidth();
            double paneHeight = pane.getHeight();

            drawTreeRecursivelyLevels(bst.getRoot(), paneWidth / 2, NODE_RADIUS * 2, paneWidth / 4, 0, paneWidth);
        }
    }

    private void drawTreeRecursivelyLevels(BTreeNode node, double x, double y, double hGap, int level, double paneWidth) {
        if (node == null) {
            return;
        }

        Circle circle = new Circle(x, y, NODE_RADIUS);
        circle.setFill(Color.PALEGREEN);
        circle.setStroke(Color.BLACK);

        Text text = new Text(x - 4, y + 4, node.data.toString());


        pane.getChildren().addAll(circle, text);


        if (node.left != null) {
            double childX = x - hGap;
            double childY = y + VERTICAL_GAP;

            Line line = new Line(x, y + NODE_RADIUS, childX, childY - 10); // Ajuste aquí (-10)
            pane.getChildren().add(line);
            drawTreeRecursivelyLevels(node.left, childX, childY, hGap / 2, level + 1, paneWidth);
        }

        if (node.right != null) {
            double childX = x + hGap;
            double childY = y + VERTICAL_GAP;

            Line line = new Line(x, y + NODE_RADIUS, childX, childY - 10); // Ajuste aquí (-10)
            pane.getChildren().add(line);
            drawTreeRecursivelyLevels(node.right, childX, childY, hGap / 2, level + 1, paneWidth);
        }

        if (level > 0) {
            // Convertimos el nivel actual a String
            String nlevel = "Nivel " + level;

            // Calculamos las coordenadas para la línea
            double startY = y - VERTICAL_GAP + NODE_RADIUS;
            double endY = startY;
            double startX = 0;
            double endX = paneWidth;

            // Creamos la línea que representa el nivel
            Line line = new Line(startX, startY- -30, endX, endY- -30);
            line.setStroke(Color.FIREBRICK);

            // Posicionamos el texto del nivel cerca del borde izquierdo
            Text textLevel = new Text(startX + 10, startY - -20, nlevel);

            // Añadimos la línea y el texto al pane
            pane.getChildren().addAll(line, textLevel);
        }
    }


    private void drawTree() {
        pane.getChildren().clear(); // limpia el panel antes de dibujar otro arbol

        if (arbolAVL.isSelected()) {
            double paneWidth = pane.getWidth();
            drawTreeRecursively(avlbst.getRoot(), paneWidth / 2, NODE_RADIUS * 2, paneWidth / 4);

        }
        if (arbolBST.isSelected()) {
            double paneWidth = pane.getWidth();
            drawTreeRecursively(bst.getRoot(), paneWidth / 2, NODE_RADIUS * 2, paneWidth / 4);
        }
    }

    private void drawTreeRecursively(BTreeNode node, double x, double y, double hGap) {
        if (arbolBST.isSelected()) {
            if (node == null) {
                return;
            }

            Circle circle = new Circle(x, y, NODE_RADIUS);
            circle.setFill(Color.PALEGREEN);
            circle.setStroke(Color.BLACK);

            Text text = new Text(x - 4, y + 4, node.data.toString());
            text.setFont(new Font(12));

            pane.getChildren().addAll(circle, text);

            if (node.left != null) {
                double childX = x - hGap;
                double childY = y + VERTICAL_GAP;

                Line line = new Line(x, y + NODE_RADIUS, childX, childY - NODE_RADIUS);
                pane.getChildren().add(line);

                drawTreeRecursively(node.left, childX, childY, hGap / 2);
            }

            if (node.right != null) {
                double childX = x + hGap;
                double childY = y + VERTICAL_GAP;

                Line line = new Line(x, y + NODE_RADIUS, childX, childY - NODE_RADIUS);
                pane.getChildren().add(line);

                drawTreeRecursively(node.right, childX, childY, hGap / 2);
            }
        } else if (arbolAVL.isSelected()) {
                if (node == null) {
                    return;
                }

                // Dibuja el nodo actual
                Circle circle = new Circle(x, y, NODE_RADIUS);
                circle.setFill(Color.PALEGREEN);
                circle.setStroke(Color.BLACK);

                Text text = new Text(x - 4, y + 4, node.data.toString());
                text.setFont(new Font(12));

                // Muestra el factor de equilibrio


                pane.getChildren().addAll(circle, text);

                // Dibuja el nodo izquierdo y la línea de conexión
                if (node.left != null) {
                    double childX = x - hGap;
                    double childY = y + VERTICAL_GAP;

                    Line line = new Line(x, y + NODE_RADIUS, childX, childY - NODE_RADIUS);
                    pane.getChildren().add(line);

                    drawTreeRecursively(node.left, childX, childY, hGap / 2);
                }

                // Dibuja el nodo derecho y la línea de conexión
                if (node.right != null) {
                    double childX = x + hGap;
                    double childY = y + VERTICAL_GAP;

                    Line line = new Line(x, y + NODE_RADIUS, childX, childY - NODE_RADIUS);
                    pane.getChildren().add(line);

                    drawTreeRecursively(node.right, childX, childY, hGap / 2);
                }
            }
    }

    @FXML
    public void tour(ActionEvent actionEvent) throws TreeException {
        if (avlbst.isEmpty()){
            alert.setContentText("The tree is empty");
            alert.showAndWait();
        }else{
            try {
                Alert alertTreeTour = new Alert(Alert.AlertType.INFORMATION);
                alertTreeTour.setContentText(avlbst.BtreeTour() + "\n" + "\n"
                        + avlbst.inOrder() + "\n" + "\n"
                        + avlbst.postOrder());
                alertTreeTour.showAndWait();
            }catch (TreeException tE){
                alert.setContentText("The tree is empty");
                alert.showAndWait();
            }
        }
    }

    private static final int NODE_RADIUS = 20;
    private static final int VERTICAL_GAP = 100;

    @FXML
    public void arbolAVLonAction(ActionEvent actionEvent) {
        pane.getChildren().clear();
        this.arbolBST.setSelected(false);
        this.arbolAVL.setSelected(true);
    }

    @FXML
    public void arbolBSTonAction(ActionEvent actionEvent) {
        pane.getChildren().clear();
        this.arbolBST.setSelected(true);
        this.arbolAVL.setSelected(false);
    }
}







