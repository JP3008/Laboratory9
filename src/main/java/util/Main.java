package util;

import domain.AVL;
import domain.BST;
import domain.BTree;
import domain.TreeException;

public class Main {

    public static void main(String[] args) {
        BST tree = new BST();

        tree.add(5);
        tree.add(7);
        tree.add(43);
        tree.add(28);
        tree.add(32);

        tree.add(51);
        tree.add(74);
        tree.add(63);
        tree.add(98);
        tree.add(12);


        try {
            System.out.println(tree.hasPath(5, 32));
            System.out.println(tree.hasPath(5, 28));
            System.out.println(tree.hasPath(5, 43));
            System.out.println(tree.hasPath(5, 22));
            System.out.println(tree.hasPath(50, 12));
            System.out.println(tree.hasPath(5, 32));
            System.out.println(tree.hasPath(5, 63));
            System.out.println(tree.hasPath(32, 43));
            System.out.println(tree.hasPath(5, 22));
            System.out.println(tree.hasPath(50, 28));
            System.out.println(tree.hasPath(100, 32));
            System.out.println(tree.hasPath(5, 5));
            System.out.println(tree.hasPath(5, 74));
            System.out.println(tree.hasPath(5, 22));
            System.out.println(tree.hasPath(50, 28));
        } catch (TreeException e) {
            e.printStackTrace();
        }
    }

}

