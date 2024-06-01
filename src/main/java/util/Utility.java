package util;

import domain.AVL;
import domain.BST;
import domain.BTree;

import java.util.IllegalFormatCodePointException;
import java.util.Random;

public class Utility {
    public static int getRandom(int bound) {
        return new Random().nextInt(bound)+1;
    }
    public static boolean equals(Object element1, Object element2) {
        if (element1 == null) {
            return element2 == null;
        }
        return element1.equals(element2);
    }

    public static boolean lessT(Object element1, Object element2) {
        if (element1 == null || element2 == null) {
            throw new IllegalArgumentException("Los elementos no pueden ser nulos");
        }

        if (!(element1 instanceof Comparable) || !(element2 instanceof Comparable)) {
            throw new IllegalArgumentException("Los elementos deben implementar Comparable");
        }

        Comparable comp1 = (Comparable) element1;
        Comparable comp2 = (Comparable) element2;

        return comp1.compareTo(comp2) < 0;
    }

    public static boolean greaterT(Object element1, Object element2) {
        if (element1 == null || element2 == null) {
            throw new IllegalArgumentException("Los elementos no pueden ser nulos");
        }

        if (!(element1 instanceof Comparable) || !(element2 instanceof Comparable)) {
            throw new IllegalArgumentException("Los elementos deben implementar Comparable");
        }

        Comparable comp1 = (Comparable) element1;
        Comparable comp2 = (Comparable) element2;

        return comp1.compareTo(comp2) > 0;
    }

    public static int compare(Object a, Object b) {
        switch (instanceOf(a, b)){
            case "Integer":
                Integer int1 = (Integer)a; Integer int2 = (Integer)b;
                return int1 < int2 ? -1 : int1 > int2 ? 1 : 0; //0 == equal
            case "String":
                String st1 = (String)a; String st2 = (String)b;
                return st1.compareTo(st2)<0 ? -1 : st1.compareTo(st2) > 0 ? 1 : 0;
            case "Character":
                Character c1 = (Character)a; Character c2 = (Character)b;
                return c1.compareTo(c2)<0 ? -1 : c1.compareTo(c2)>0 ? 1 : 0;
            case "Btree":
                BTree b1 = (BTree) a; BTree b2 = (BTree) b;
                return b1.toString().compareTo(b2.toString())<0? -1 : b1.toString().compareTo(b2.toString())>0 ? 1 : 0;
            case "BST":
                BST bst1 = (BST) a; BST bst2 = (BST) b;
                return bst1.toString().compareTo(bst2.toString())<0? -1 : bst1.toString().compareTo(bst2.toString())>0 ? 1 : 0;
            case "AVL":
                AVL avl1 = (AVL) a; AVL avl2 = (AVL) b;
                return avl1.toString().compareTo(avl2.toString())<0? -1 : avl1.toString().compareTo(avl2.toString())>0 ? 1 : 0;
        }
        return 2; //Unknown
    }

    private static String instanceOf(Object a, Object b) {
        if(a instanceof Integer && b instanceof Integer) return "Integer";
        if(a instanceof String && b instanceof String) return "String";
        if(a instanceof Character && b instanceof Character) return "Character";
        if(a instanceof BTree && b instanceof BTree) return "Btree";
        if (a instanceof BST && b instanceof BST) return "BST";
        if (a instanceof AVL && b instanceof AVL) return "AVL";
        return "Unknown";
    }
}
