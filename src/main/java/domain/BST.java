package domain;

public class BST {

    private BTreeNode root;

    public BST() {
        this.root = null;
    }

    public BTreeNode getRoot() {
        return root;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void clear() {
        root = null;
    }

    public int size() throws TreeException {
        if (isEmpty()) {
            throw new TreeException("Árbol vacío");
        }
        return size(root);

    }

    private int size(BTreeNode node) throws TreeException {
        if (node == null)
            return 0;
        else
            return 1 + size(node.right) + size(node.left);
    }

    public boolean contains(Object element) throws TreeException {

        if (isEmpty()) {
            throw new TreeException("Árbol de busqueda binaria vacío");
        }
        return binarySearch(root, element);
    }

    private boolean binarySearch(BTreeNode node, Object element) {
        if (node == null) {
            return false;
        } else {
            if (Util.Utility.equals(node.data, element)) {
                return true;
            } else {
                if (Util.Utility.lessT(element, node.data)) {
                    return binarySearch(node.left, element);
                } else {
                    return binarySearch(node.right, element);
                }

            }
        }
    }

    public void add(Object element) {
        root = add(root, element);

    }

    private BTreeNode add(BTreeNode node, Object element) {
        if (node == null) {
            node = new BTreeNode(element);
        } else {
            if (Util.Utility.lessT(element, node.data)) {
                node.left = add(node.left, element);
            } else if (Util.Utility.greaterT(element, node.data)) {
                node.right = add(node.right, element);
            }
        }
        return node;

    }

    public BTreeNode remove(BTreeNode node, Object element) {
        if (node != null) {
            if (Util.Utility.lessT(element, node.data)) {
                node.left = remove(node.left, element);
            }
            if (Util.Utility.greaterT(element, node.data)) {
                node.right = remove(node.right, element);
            }
        } else {
            if (Util.Utility.equals(node.data, element)) {
                //Caso 1: nodo sin hijos
                if (node.left == null && node.right == null) {
                    return null;
                }

                //Caso 2: nodo con un hijo
                if (node.left != null && node.right == null) {
                    //nodo sube
                    node = node.left;
                } else {
                    if (node.left == null && node.right != null) {
                        //nodo sube
                        node = node.right;
                    }
                }

                //Caso 3: nodo con dos hijos
                if (node.left != null && node.right != null) {
                    //Hacen busqueda de la hoja para luego sustituir su valor en el nodo a suprimir
                    Object value = min(node.right);
                    node.data = value;
                    node.right = remove(node.right, element);

                }

            }
        }
        return node;
    }


    public Object min() throws TreeException {
        if (root == null) {
            throw new TreeException("The tree is empty.");
        }
        return min(root);
    }

    private Object min(BTreeNode node) {
        if (node.left != null) {
            return min(node.left);
        }
        return node.data;
    }


    public Object max() throws TreeException {
        if (root == null) {
            throw new TreeException("The tree is empty.");
        }
        return max(root);
    }

    private Object max(BTreeNode node) {
        if (node.right != null) {
            return min(node.right);
        }
        return node.data;
    }

    public int height(Object element) throws TreeException {//altura del nodo
        if (isEmpty())
            throw new TreeException("Binary Tree is empty");
        return height(root, element,0);
    }

    private int height(BTreeNode node, Object element, int count) {//altura del nodo
        if (node == null)
            return 0;

        else
        if (Util.Utility.equals(node.data, element)) {
            return count;
        }
        else if (Util.Utility.lessT(element, node.data)){
            return height(node.left, node.data, ++count);
        }else {
            return height(node.right, node.data, ++count);
        }

    }

    public int heightTree() throws TreeException {
        if (isEmpty())
            throw new TreeException("Binary Tree is empty");
        return heightTree(root,0);
    }


    private int heightTree(BTreeNode node, int count) {
        if (node == null)
            return 0;

        else
        if (node.left == null && node.right==null) {
            return count;
        }
        else
            return Math.max(heightTree(node.left, ++count),
                    heightTree(node.right, count));

    }

    public String preOrder() throws TreeException {
        if(isEmpty()){
            throw new TreeException("Binary Tree is empty");
        }
        return preOrder(root)+"\n";
    }


    private String InOrder() throws TreeException {
        return null;
    }

    //node-left-right
    private String preOrder(BTreeNode node){
        String result="";
        if(node!=null){
            result =  node.data+"("+node.sequence+") ";
            result += preOrder(node.left);
            result += preOrder(node.right);
        }
        return result;
    }


    public String inOrder() throws TreeException {
        if(isEmpty()){
            throw new TreeException("Binary Tree is empty");
        }
        return "InOrder Tour: \n" + inOrder(root);
    }

    //left-node-right
    private String inOrder(BTreeNode node){
        String result="";
        if(node!=null){
            result  = inOrder(node.left);
            result += node.data+" ";
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
    private String postOrder(BTreeNode node){
        String result="";
        if(node!=null){
            result = postOrder(node.left);
            result += postOrder(node.right);
            result += node.data+" ";

        }
        return result;
    }


    public String preorderTour(BTreeNode node) {
        String result="";
        if (node!=null){
            result="(" + node.sequence + ") ";
        }
        return result;

    }

    public String BtreeTour() throws TreeException {
        if (isEmpty()) {
            throw new TreeException("El árbol está vacío");
        }
        return "PreOrder Tour: \n" + BtreeTour(root);

    }

    private String BtreeTour(BTreeNode node) {
        String result="";
        if (node!=null){
            result=node.data + " ";
            result+=BtreeTour(node.left);
            result+=BtreeTour(node.right);
        }
        return result;

    }
}

