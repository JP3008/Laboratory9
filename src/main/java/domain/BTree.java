package domain;

import util.Utility;

public class BTree implements Tree {
    private BTreeNode root; //unica entrada al arbol

    public BTree() {
        this.root = null;
    }

    public BTreeNode getRoot() {
        return this.root;
    }


    public int size() throws TreeException {
        if (isEmpty()) {
            throw new TreeException("Binary Tree is empty");
        }
        return size(root);
    }

    private int size(BTreeNode node) {
        if (node == null)
            return 0;
        else
            return 1 + size(node.left) + size(node.right);
    }


    public void clear() {
        root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }


    public boolean contains(Object element) throws TreeException {
        if (isEmpty()) {
            throw new TreeException("Binary Tree is empty");
        }
        return binarySearch(root, element);
    }

    //método interno
    private boolean binarySearch(BTreeNode node, Object element) {
        if (node == null)
            return false;
        else if (util.Utility.compare(node.data, element) == 0)
            return true; //ya lo encontro
        else
            return binarySearch(node.left, element)
                    || binarySearch(node.right, element);
    }


    private BTreeNode containsNode(Object element) throws TreeException {
        if (isEmpty()) {
            throw new TreeException("Binary Tree is empty");
        }
        return binarySearchNode(root, element);
    }

    //método interno
    private BTreeNode binarySearchNode(BTreeNode node, Object element) {
        if (node == null)
            return null;

        if (util.Utility.compare(node.data, element) == 0)
            return node;

        if (binarySearchNode(node.left, element) != null)
            return binarySearchNode(node.left, element);

        return binarySearchNode(node.right, element);

    }

    public void add(Object element) {
        //this.root = add(root, element);
        this.root = add(root, element, "root");
    }


    private BTreeNode add(BTreeNode node, Object element) {
        if (node == null) { //si el arbol esta vacio
            node = new BTreeNode(element);
        } else {
            //debemos establecer algun criterio de insercion
            int value = util.Utility.getRandom(10);
            if (value % 2 == 0) //si es par inserte por la izq
                node.left = add(node.left, element);
            else //si es impart inserte por la der
                node.right = add(node.right, element);
        }
        return node;
    }

    private BTreeNode add(BTreeNode node, Object element, String path) {
        if (node == null) { //si el arbol esta vacio
            node = new BTreeNode(element, path);
        } else {
            //debemos establecer algun criterio de insercion
            int value = util.Utility.getRandom(10);
            if (value % 2 == 0) //si es par inserte por la izq
                node.left = add(node.left, element, path + "/left");
            else //si es impart inserte por la der
                node.right = add(node.right, element, path + "/right");
        }
        return node;
    }

    public void remove(Object element) throws TreeException {
        if (isEmpty())
            throw new TreeException("Binary Tree is empty");
        root = remove(root, element);
        if (root != null) {
            updatePaths(root, "root");
        }
    }

    private void updatePaths(BTreeNode node, String path) {
        if (node != null) {
            node.sequence = path;
            updatePaths(node.left, path + "/left");
            updatePaths(node.right, path + "/right");
        }
    }


    private BTreeNode remove(BTreeNode node, Object element) {
        if (node != null) {
            if (util.Utility.compare(element, node.data) == 0) {
                if (node.left == null && node.right == null) {
                    return null; // Caso 1: sin hijos
                } else if (node.left == null) {
                    //node.right = newPath(node.right,node.path);
                    return node.right; // Caso 2: un hijo (derecha)
                } else if (node.right == null) {
                    //node.left = newPath(node.left,node.path);
                    return node.left; // Caso 2: un hijo (izquierda)
                } else {
                    // Caso 3: dos hijos
                    Object value = getLeaf(node.right);
                    node.data = value;
                    node.right = removeLeaf(node.right, value);
                }
            } else {
                node.left = remove(node.left, element);
                node.right = remove(node.right, element);
            }
        }
        return node;
    }

    private BTreeNode removeLeaf(BTreeNode node, Object value) {
        if (node == null) {
            return null;
        } else if (node.left == null && node.right == null && util.Utility.compare(node.data, value) == 0) {
            return null;
        } else {
            node.left = removeLeaf(node.left, value);
            node.right = removeLeaf(node.right, value);
            return node;
        }
    }

    private Object getLeaf(BTreeNode node) {
        Object aux;
        if (node == null) {
            return null;
        } else if (node.left == null && node.right == null) {
            return node.data;
        } else {
            aux = getLeaf(node.left);
            if (aux == null) {
                aux = getLeaf(node.right);
            }
            return aux;
        }
    }


    public int height(Object element) throws TreeException {
        if (isEmpty()) {
            throw new TreeException("Binary Tree is empty");
        }
        return height(root, element, 0);
    }

    private int height(BTreeNode node, Object element, int counter) {
        if (node == null) {
            return 0;
        } else if (Utility.compare(node.data, element) == 0) {
            return counter;
        } else {
            return Math.max(height(node.left, element, ++counter), height(node.right, element, counter));
        }
    }

    public int height() throws TreeException {
        if (isEmpty())
            throw new TreeException("Binary Tree is empty");
        return height(root) - 1;
    }

    @Override
    public Object min() throws TreeException {
        return null;
    }

    @Override
    public Object max() throws TreeException {
        return null;
    }

    private int height(BTreeNode node) {
        if (node == null) {
            return 0;
        }
        return Math.max(height(node.left), height(node.right)) + 1;
    }

    public String preOrder() throws TreeException {
        if (isEmpty()) {
            throw new TreeException("Binary Tree is empty");
        }
        return "PreOrder Tour: \n" + preOrder(root);
    }

    //node-left-right
    public String preOrder(BTreeNode node) {
        String result = "";
        if (node != null) {
            result = node.data + "(" + node.path + ") ";
            result += preOrder(node.left);
            result += preOrder(node.right);
        }
        return result;
    }

    public String inOrder() throws TreeException {
        if (isEmpty()) {
            throw new TreeException("Binary Tree is empty");
        }
        return "InOrder Tour: \n" + inOrder(root);
    }

    //left-node-right
    private String inOrder(BTreeNode node) {
        String result = "";
        if (node != null) {
            result = inOrder(node.left);
            result += node.data + "(" + node.path + ") ";
            result += inOrder(node.right);
        }
        return result;
    }

    public String postOrder() throws TreeException {
        if (isEmpty()) {
            throw new TreeException("Binary Tree is empty");
        }
        return "PostOrder Tour: \n" + postOrder(root);
    }

    //left-right-node
    private String postOrder(BTreeNode node) {
        String result = "";
        if (node != null) {
            result = postOrder(node.left);
            result += postOrder(node.right);
            result += node.data + "(" + node.path + ") ";

        }
        return result;
    }


    public String preorderTour(BTreeNode node) {
        String result = "";
        if (node != null) {
            result = "(" + node.sequence + ") ";
        }
        return result;

    }

    //preOrder: recorre el árbol de la forma: nodo-izq-der
    //inOrder: recorre el árbol de la forma: izq-nodo-der
    //postOrder: recorre el árbol de la forma: izq-der-nodo


    @Override
    public String toString() {
        if (isEmpty())
            return "Binary Tree is empty";

        String res = "";
        try {
            res += preOrder() + "\n";
            res += inOrder() + "\n";
            res += postOrder() + "\n";
            return res;
        } catch (TreeException e) {
            throw new RuntimeException(e);
        }


    }


    private boolean contains(BTreeNode node, Object element) {
        if (node == null) {
            return false;
        }

        if (Utility.compare(node.data, element) == 0) {
            return true;
        }

        return contains(node.left, element) || contains(node.right, element);
    }

    public String hasPath(Object data1, Object data2) throws TreeException {
        if (isEmpty()) {
            throw new TreeException("The tree is empty");
        }

        if (!contains(root, data1) || !contains(root, data2)) {
            if (!contains(root, data1)) {
                return "Wrong element: " + data1;
            }
            if (!contains(root, data2)) {
                return "Wrong element: " + data2;
            }
        }

        String path = hasPath(this.root, data1, data2);
        if (path.isEmpty()) {
            return "Wrong path";
        }
        return "hashPath(" + data1 + ", " + data2 + ") " + path;
    }

    private String hasPath(BTreeNode node, Object data1, Object data2) {
        if (node == null) {
            return "";
        }

        // Buscar el camino desde data1 hasta data2
        if (Utility.compare(node.data, data1) == 0 || Utility.compare(node.data, data2) == 0) {
            String leftPath = hasPath(node.left, data1, data2);
            String rightPath = hasPath(node.right, data1, data2);

            if (!leftPath.isEmpty() && !rightPath.isEmpty()) {
                return node.data + "," + leftPath + "," + rightPath;
            } else if (!leftPath.isEmpty()) {
                return node.data + "," + leftPath;
            } else if (!rightPath.isEmpty()) {
                return node.data + "," + rightPath;
            } else {
                return node.data.toString();
            }
        }


        String leftPath = hasPath(node.left, data1, data2);
        String rightPath = hasPath(node.right, data1, data2);

        if (!leftPath.isEmpty() && !rightPath.isEmpty()) {
            return node.data + "," + leftPath + "," + rightPath;
        } else if (!leftPath.isEmpty()) {
            return node.data + "," + leftPath;
        } else if (!rightPath.isEmpty()) {
            return node.data + "," + rightPath;
        }

        return "";
    }

}
