package domain;

import domain.BTreeNode;
import domain.Tree;
import domain.TreeException;
import util.Utility;

import java.util.ArrayList;
import java.util.List;

public class AVL implements Tree {
    public BTreeNode getRoot() {
        return root;
    }

    private BTreeNode root; //unica entrada al arbol

    public AVL(){
        this.root = null;
    }

    @Override
    public int size() throws TreeException {
        if(isEmpty()){
            throw new TreeException("AVL Binary Search Tree is empty");
        }
        return size(root);
    }

    private int size(BTreeNode node){
        if(node==null)
            return 0;
        else
            return 1+size(node.left)+size(node.right);
    }

    @Override
    public void clear() {
        root = null;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public boolean contains(Object element) throws TreeException {
        if(isEmpty()){
            throw new TreeException("AVL Binary Search Tree is empty");
        }
        return binarySearch(root, element);
    }

    //método interno
    private boolean binarySearch(BTreeNode node, Object element){
        if(node==null)
            return false;
        else
        if(Utility.compare(node.data, element)==0)
            return true; //ya lo encontro
        else if (Utility.compare(element, node.data)<0)
            return binarySearch(node.left, element);
        else return binarySearch(node.right, element);
    }

    @Override
    public void add(Object element) {
        this.root = add(root, element, " added as ");
    }

    private BTreeNode add(BTreeNode node, Object element, String sequence) {
        if (node == null) {
            node = new BTreeNode(element, sequence);
        } else {
            if (Utility.compare(element, node.data) < 0)
                node.left = add(node.left, element, sequence + "/left");
            else if (Utility.compare(element, node.data) > 0)
                node.right = add(node.right, element, sequence + "/right");

        }

        node = reBalance(node, element);
        return node;
    }

    private BTreeNode reBalance(BTreeNode node, Object element) {
        if (node == null) return node; // Si el nodo es nulo, no hay nada que reequilibrar

        int balance = getBalanceFactor(node);

        // Casos de rotación
        if (balance > 1 && Utility.compare(element, node.left.data) < 0) {
            node.path += ". Singly Right Rotation ";
            return rightRotate(node);
        }
        if (balance < -1 && Utility.compare(element, node.right.data) > 0) {
            node.path += ". Singly Left Rotation ";
            return leftRotate(node);
        }
        if (balance > 1 && Utility.compare(element, node.left.data) > 0) {
            node.path += ". Double Left/Right Rotation ";
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        if (balance < -1 && Utility.compare(element, node.right.data) < 0) {
            node.path += ". Double Right/Left Rotation ";
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        return node;
    }


    private BTreeNode leftRotate(BTreeNode node) {
        if (node == null || node.right == null) return node; // Manejo de nodos nulos
        BTreeNode node1 = node.right;
        BTreeNode node2 = node1.left;

        node1.left = node;
        node.right = node2;

        node.height = Math.max(height(node.left), height(node.right)) + 1;
        node1.height = Math.max(height(node1.left), height(node1.right)) + 1;
        return node1;
    }

    private BTreeNode rightRotate(BTreeNode node) {
        if (node == null || node.left == null) return node; // Manejo de nodos nulos
        BTreeNode node1 = node.left;
        BTreeNode node2 = node1.right;
        
        node1.right = node;
        node.left = node2;

        node.height = Math.max(height(node.left), height(node.right)) + 1;
        node1.height = Math.max(height(node1.left), height(node1.right)) + 1;
        return node1;
    }

    private int getBalanceFactor(BTreeNode node) {
        if (node == null) return 0;
        else return height(node.left) - height(node.right);
    }

    @Override
    public void remove(Object element) throws TreeException {
        if(isEmpty())
            throw new TreeException("AVL Binary Search Tree is empty");
        root = remove(root,element);
    }

    private BTreeNode remove(BTreeNode node, Object element){
        if(node != null) {

            if (Utility.compare(element, node.data)<0)
                node.left = remove(node.left, element);
            else if(Utility.compare(element, node.data) >0)
                node.right = remove(node.right, element);
            else if (Utility.compare(node.data, element) == 0) {

                //Caso 1. Si es una hoja (osea nodo sin hijos)
                if (node.left == null && node.right == null) {
                    return null;
                }
                //Caso 2. El nodo solo tiene un hijo
                else if (node.left == null && node.right != null) {
                    node.right = newPath(node.right, node.path);
                    return node.right;
                }
                //Caso 2. El nodo solo tiene un hijo
                else if (node.left != null && node.right == null) {
                    node.left = newPath(node.left, node.path);
                    return node.left;
                } else
                    //Caso 3. El nodo tiene dos hijos
                    if (node.left != null && node.right != null){
                        Object value = min(node.right);
                        node.data = value;
                        node.right = remove(node.right, value);
                    }
            }
            node = reBalance(node, element);
        }
        return node;
    }

    public List<BTreeNode> getLeaves() throws TreeException {
        if (isEmpty()) {
            throw new TreeException("AVL Binary Search Tree is empty");
        }
        List<BTreeNode> leaves = new ArrayList<>();
        getLeaves(root, leaves);
        return leaves;
    }

    private void getLeaves(BTreeNode node, List<BTreeNode> leaves) {
        if (node == null) return;
        if (node.left == null && node.right == null) {
            leaves.add(node);
        } else {
            if (node.left != null) getLeaves(node.left, leaves);
            if (node.right != null) getLeaves(node.right, leaves);
        }
    }

    private BTreeNode newPath(BTreeNode node, String path) {
        if (node != null){
            node.path = path;
        }
        return node;
    }

    @Override
    public int height(Object element) throws TreeException {
        if(isEmpty()){
            throw new TreeException("AVL Binary Search Tree is empty");
        }
        return height(root, element, 0);
    }

    private int height(BTreeNode node, Object element, int counter){
        if (node == null) return 0; //significa que el elemento no existe
        else if (Utility.compare(node.data, element) == 0) return counter;
        else if (Utility.compare(element, node.data)<0)
            return height(node.left, element, ++counter);
        else return height(node.right, element, ++counter);
        //else return Math.max(height(node.left, element, ++counter), height(node.right, element, counter));
    }

    @Override
    public int height() throws TreeException {
        if(isEmpty())
            throw new TreeException("AVL Binary Search Tree is empty");
        return height(root)-1;
    }

    private int height(BTreeNode node){
        if (node == null) return 0;
        else return Math.max(height(node.left), height(node.right)) +1;
    }

    @Override
    public Object min() throws TreeException {
        if(isEmpty())
            throw new TreeException("AVL Binary Search Tree is empty");
        return min(root);
    }

    private Object min(BTreeNode node){
        if (node.left!=null)
            return min(node.left);
        return node.data;
    }

    @Override
    public Object max() throws TreeException {
        if(isEmpty())
            throw new TreeException("AVL Binary Search Tree is empty");
        return max(root);
    }

    private Object max(BTreeNode node){
        if (node.right!=null)
            return max(node.right);
        return node.data;
    }

    @Override
    public String preOrder() throws TreeException {
        if(isEmpty()){
            throw new TreeException("AVL Binary Search Tree is empty");
        }
        return preOrder(root)+"\n";
    }

    //node-left-right
    private String preOrder(BTreeNode node){
        String result="";
        if(node!=null){
            result =  node.data + "  ";
            result += preOrder(node.left);
            result += preOrder(node.right);
        }
        return result;
    }

    @Override
    public String inOrder() throws TreeException {
        if(isEmpty()){
            throw new TreeException("AVL Binary Search Tree is empty");
        }
        return inOrder(root)+"\n";
    }

    //left-node-right
    private String inOrder(BTreeNode node){
        String result="";
        if(node!=null){
            result  = inOrder(node.left);
            result += node.data+"  ";
            result += inOrder(node.right);
        }
        return result;
    }

    @Override
    public String postOrder() throws TreeException {
        if (isEmpty()) {
            throw new TreeException("AVL Binary Search Tree is empty");
        }
        return postOrder(root) + "\n";
    }

    //left-right-node
    private String postOrder(BTreeNode node){
        String result="";
        if(node!=null){
            result = postOrder(node.left);
            result += postOrder(node.right);
            result += node.data+"  ";

        }
        return result;
    }

    public String preOrderWithoutPath() throws TreeException {
        if(isEmpty()){
            throw new TreeException("AVL Binary Search Tree is empty");
        }
        return preorderWithoutPath(root)+"\n";
    }

    private String preorderWithoutPath(BTreeNode node){
        String result="";
        if(node!=null){
            result =  node.data + " ";
            result += preorderWithoutPath(node.left);
            result += preorderWithoutPath(node.right);
        }
        return result;
    }

    public int getPositionOrders(String notation, int index){
        int position = 0;
        String[] values = notation.split(" ");

        for (int i = 0; i < values.length; i++) {
            int currentValue = Integer.parseInt(values[i]);
            if (currentValue == index) {
                return i + 1;
            }
        }

        return -1;
    }

    //preOrder: recorre el árbol de la forma: nodo-izq-der
    //inOrder: recorre el árbol de la forma: izq-nodo-der
    //postOrder: recorre el árbol de la forma: izq-der-nodo
    @Override
    public String toString() {
        if(isEmpty())
            return "AVL Binary Search Tree is empty";
        String result = "AVL BINARY SEARCH TREE TOUR...\n";
        result+="PreOrder: "+preOrder(root)+"\n";
        result+="InOrder: "+inOrder(root)+"\n";
        result+="PostOrder: "+postOrder(root)+"\n";
        return result;
    }

    public String getSequence() throws TreeException {
        if (isEmpty()){
            throw new TreeException("AVL tree is empty");
        }

        return getSequence(root);

    }

    private String getSequence(BTreeNode node) {
        String result="";
        if(node!=null){
            result =  node.data + " "+ node.path + "\n";
            result += getSequence(node.left);
            result += getSequence(node.right);
        }
        return result;


    }
   /* private String getInternalSequence(BTreeNode node){
        String result="";
        if(node!=null){
            result =  node.data + " "+ node.path + "\n";
            result += getInternalSequence(node.left);
            result += getInternalSequence(node.right);
        }
        return result;
    }

    public String getSequence(Object object) throws TreeException {
        BTreeNode currentNode = null;
        if (contains(object)) {
            currentNode = getNodeByValue(object, root);
        }
        return getInternalSequence(currentNode);
    }
*/
    private BTreeNode getNodeByValue(Object element, BTreeNode node){
        if(element==null)
            return null;
        else
        if(Utility.compare(node.data, element)==0)
            return node; //ya lo encontro
        else if (Utility.compare(element, node.data) < 0)
            return getNodeByValue(element, node.left);
        else return getNodeByValue(element, node.right);
    }

    public boolean isBalanced(){
        return isBalanced(root);
    }

    protected boolean isBalanced(BTreeNode node) {
        return Math.abs((height(node.left)) - height(node.right)) <= 1;
    }
}