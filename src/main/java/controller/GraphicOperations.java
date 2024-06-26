package controller;

import util.Utility;
import domain.AVL;
import domain.BST;
import domain.BTreeNode;
import domain.TreeException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GraphicOperations
{
    @javafx.fxml.FXML
    private Pane pane;
    @javafx.fxml.FXML
    private RadioButton radioBST;
    @javafx.fxml.FXML
    private RadioButton radioAVL;
    public BST bst;
    public AVL avl;
    private Alert alert = new Alert(Alert.AlertType.INFORMATION);
    @javafx.fxml.FXML
    private Label lbBalance;

    @FXML
    public void initialize() throws TreeException {
        bst = new BST();
        avl = new AVL();


        // Agregar un listener para dibujar el árbol cuando el pane cambie de tamaño
        pane.widthProperty().addListener((obs, oldVal, newVal) -> drawTree());
        pane.heightProperty().addListener((obs, oldVal, newVal) -> drawTree());
    }
    @FXML
    public void RandomizeButton() throws TreeException {
        bst = new BST();
        avl = new AVL();
        if (radioBST.isSelected()) {
            lbBalance.setText("");
            for (int i = 0; i < 19; i++) {
                bst.add( Utility.getRandom(100)); // Usando valores aleatorios entre 0 y 99
            }
            if (bst.isBalanced()){
                lbBalance.setText("BTS is balanced!!!");
            }else{
                lbBalance.setText("BTS is not balanced!!!");
            }
            drawTree();
        }else if (radioAVL.isSelected()) {
            lbBalance.setText("");
            for (int i = 0; i < 19; i++) {
                int randomValue = Utility.getRandom(100);
                if (i ==0) {
                    avl.add(randomValue); // Usando valores aleatorios entre 0 y 99
                }
                if (avl.contains(randomValue) && i!=0){
                    i--;
                }else{
                    avl.add(randomValue);
                }
            }
            if (avl.isBalanced()){
                lbBalance.setText("AVL is balanced!!!");
            }else{
                lbBalance.setText("AVL is not balanced!!!");
            }
            drawTree();
        }else{
            alert.setContentText("No option selected");
            alert.showAndWait();
        }
    }

    private void drawTree() {
        pane.getChildren().clear(); // limpia el panel antes de dibujar otro arbol

        if (radioAVL.isSelected()) {
            double paneWidth = pane.getWidth();
            drawTreeRecursively(avl.getRoot(), paneWidth / 2, NODE_RADIUS * 2, paneWidth / 4);

        }
        if (radioBST.isSelected()) {
            double paneWidth = pane.getWidth();
            drawTreeRecursively(bst.getRoot(), paneWidth / 2, NODE_RADIUS * 2, paneWidth / 4);
        }
    }

    private void drawTreeRecursively(BTreeNode node, double x, double y, double hGap) {
        if (radioBST.isSelected()) {
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
        } else if (radioAVL.isSelected()) {
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

    private static final int NODE_RADIUS = 20;
    private static final int VERTICAL_GAP = 100;

    @javafx.fxml.FXML
    public void nodeHeightOnAction(ActionEvent actionEvent) {
        if (bst.isEmpty() && avl.isEmpty()){
            alert.setContentText("No option selected");
            alert.showAndWait();
        }else {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Height Node");
            dialog.setHeaderText("Enter the element to search the height:");
            dialog.setContentText("Element:");
            String result = dialog.showAndWait().orElse(null);
            if (result == null) {
                alert.setContentText("Nothing was entered");
                alert.showAndWait();
            } else {
                try {
                    int element = Integer.parseInt(result);
                    if (radioBST.isSelected()) {
                        try {
                            if (bst.contains(element)) {
                                System.out.println(element);
                                alert.setContentText("The element " + element + " hight is " + bst.height(element));
                                alert.showAndWait();
                            } else {
                                alert.setContentText("The element " + element + " is  not contained in the tree");
                                alert.showAndWait();
                            }
                        } catch (TreeException e) {
                            alert.setContentText("The tree is Empty");
                            alert.showAndWait();
                        }
                    } else if (radioAVL.isSelected()) {
                        try {
                            if (avl.contains(element)) {
                                alert.setContentText("The element " + element + " hight is " + avl.height(element));
                                alert.showAndWait();
                            } else {
                                alert.setContentText("The element " + element + " is  not contained in the tree");
                                alert.showAndWait();
                            }
                        } catch (TreeException e) {
                            alert.setContentText("The tree is Empty");
                            alert.showAndWait();
                        }
                    } else {
                        alert.setContentText("No option selected");
                        alert.showAndWait();
                    }
                } catch (NumberFormatException nfe) {
                    alert.setContentText("Didn't enter a number");
                    alert.showAndWait();
                }
            }
        }
    }

    @javafx.fxml.FXML
    public void removeOnAction(ActionEvent actionEvent) throws TreeException {
        if (bst.isEmpty() && avl.isEmpty()){
            alert.setContentText("No option selected");
            alert.showAndWait();
        }else{
                if (radioBST.isSelected()) {
                    lbBalance.setText("");
                    int eliminateValueBTS;
                    try {
                        do {
                            eliminateValueBTS = util.Utility.getRandom(100);
                        } while (!bst.contains(eliminateValueBTS));
                        bst.remove(eliminateValueBTS);
                        if (bst.isBalanced()) {
                            lbBalance.setText("BTS is balanced!!!");
                        } else {
                            lbBalance.setText("BTS is not balanced!!!");
                        }
                        drawTree();
                    } catch (TreeException e) {
                        alert.setContentText("The tree BTS is empty");
                        alert.showAndWait();
                    }
                } else if (radioAVL.isSelected()) {
                    lbBalance.setText("");
                    int eliminateValueAVL;
                    try {
                        eliminateValueAVL = util.Utility.getRandom(100);
                        while (!avl.contains(eliminateValueAVL)){
                            eliminateValueAVL = util.Utility.getRandom(100);
                        }
                        avl.remove(eliminateValueAVL);
                        if (avl.isBalanced()) {
                            lbBalance.setText("AVL is balanced!!!");
                        } else {
                            lbBalance.setText("AVL is not balanced!!!");
                        }
                        drawTree();
                    } catch (TreeException e) {
                        alert.setContentText("The tree AVL is empty");
                        alert.showAndWait();
                    }
                } else {
                    alert.setContentText("No option selected");
                    alert.showAndWait();
                }
        }
    }

    @javafx.fxml.FXML
    public void addOnAction(ActionEvent actionEvent) throws TreeException {
        if (radioBST.isSelected()) {
            lbBalance.setText("");
            bst.add(Utility.getRandom(100));
            if (bst.isBalanced()){
                lbBalance.setText("BTS is balanced!!!");
            }else{
                lbBalance.setText("BTS is not balanced!!!");
            }
            drawTree();
        }else if (radioAVL.isSelected()) {
            lbBalance.setText("");
            int randomValue = Utility.getRandom(100);
            while (avl.contains(randomValue)){
                randomValue = Utility.getRandom(100);
            }
            avl.add(randomValue);
            if (avl.isBalanced()){
                lbBalance.setText("AVL is balanced!!!");
            }else{
                lbBalance.setText("AVL is not balanced!!!");
            }
            drawTree();
        }else{
            alert.setContentText("No option selected");
            alert.showAndWait();
        }
    }

    @javafx.fxml.FXML
    public void containsOnAction(ActionEvent actionEvent) {
        if (bst.isEmpty() && avl.isEmpty()){
            alert.setContentText("No option selected");
            alert.showAndWait();
        }else {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Height Node");
            dialog.setHeaderText("Enter the element to search the height:");
            dialog.setContentText("Element:");
            String result = dialog.showAndWait().orElse(null);
            if (result == null) {
                alert.setContentText("Nothing was entered");
                alert.showAndWait();
            } else {
                try {
                    int element = Integer.parseInt(result);
                    if (radioBST.isSelected()) {
                        try {
                            if (bst.contains(element)) {
                                alert.setContentText("The element " + element + " is contained in the tree");
                                alert.showAndWait();
                            } else {
                                alert.setContentText("The element " + element + " is  not contained in the tree");
                                alert.showAndWait();
                            }
                        } catch (TreeException e) {
                            alert.setContentText("The tree is Empty");
                            alert.showAndWait();
                        }
                    } else if (radioAVL.isSelected()) {
                        try {
                            if (avl.contains(element)) {
                                alert.setContentText("The element " + element + " is contained in the tree");
                                alert.showAndWait();
                            } else {
                                alert.setContentText("The element " + element + " is  not contained in the tree");
                                alert.showAndWait();
                            }
                        } catch (TreeException e) {
                            alert.setContentText("The tree is Empty");
                            alert.showAndWait();
                        }
                    } else {
                        alert.setContentText("No option selected");
                        alert.showAndWait();
                    }
                } catch (NumberFormatException nfe) {
                    alert.setContentText("Didn't enter a number");
                    alert.showAndWait();
                }
            }
        }
    }

    @javafx.fxml.FXML
    public void treeHeightOnAction(ActionEvent actionEvent) {
        if (bst.isEmpty() && avl.isEmpty()){
            alert.setContentText("No option selected");
            alert.showAndWait();
        }else {
            if (radioBST.isSelected()) {
                try {
                    alert.setContentText("The height of the tree BTS is " + bst.height());
                    alert.showAndWait();
                } catch (TreeException e) {
                    alert.setContentText("Problem in processing");
                    alert.showAndWait();
                }
            } else if (radioAVL.isSelected()) {
                try {
                    alert.setContentText("The height of the tree AVL is " + avl.height());
                    alert.showAndWait();
                } catch (TreeException e) {
                    alert.setContentText("Problem in processing");
                    alert.showAndWait();
                }
            } else {
                alert.setContentText("No option selected");
                alert.showAndWait();
            }
        }
    }

    @FXML
    public void arbolAVLonAction(ActionEvent actionEvent) {
        pane.getChildren().clear();
        this.radioBST.setSelected(false);
        this.radioAVL.setSelected(true);
    }

    @FXML
    public void arbolBSTonAction(ActionEvent actionEvent) {
        pane.getChildren().clear();
        this.radioBST.setSelected(true);
        this.radioAVL.setSelected(false);
    }
}