package domain;

public class BTreeNode {

    public Object data;
    public BTreeNode left, right;
    public String sequence;
    public int height;


    public BTreeNode(Object data) {
        this.data = data;
        height = 1;
    }

    public BTreeNode(Object data, String sequence) {
        this.sequence = sequence;
        this.data = data;
        this.left = this.right = null;
    }

}
