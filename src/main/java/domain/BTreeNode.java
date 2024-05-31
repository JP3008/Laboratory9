package domain;

public class BTreeNode {

    public Object data;
    public BTreeNode left, right;
    public String sequence;
    public String path;
    public int height;

    //Constructor para ingresar datos
    public BTreeNode(Object data) {
        this.data = data;
        this.left = this.right = null;
    }

    //Constructor para ingresar altura
    public BTreeNode(Object data, int height) {
        this.data = data;
        this.height=1;
        this.left = this.right = null;
    }

    //Constructor para sequencia y altura
    public BTreeNode(Object data, String sequence,int height) {
        this.sequence = sequence;
        this.height=height;
        this.data = data;
        this.left = this.right = null;
    }

    //Constructor para caminos
    public BTreeNode(Object data, String path) {
        this.path = path;
        this.data = data;
        this.left = this.right = null;
    }

}
