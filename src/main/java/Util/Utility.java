package Util;

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
}
