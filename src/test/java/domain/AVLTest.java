package domain;

import util.Utility;
import org.junit.jupiter.api.Test;

class AVLTest {




    @Test
    public void testing() throws TreeException {
        AVL avl = new AVL();

        for (int i = 0; i < 30; i++) {
            int ramval =Utility.getRandom(99);
            if (i==0){
                avl.add(ramval);
            }

            if (avl.contains(ramval) && i!=0){
                i--;
            }else{
                avl.add(ramval);
            }
        }

        System.out.println("Main AVL content:\n" + avl.toString());
        System.out.println("Size of AVL:" + avl.size());
        System.out.println("Minimal value of main AVL " + avl.min());
        System.out.println("Maximal value of main AVL " + avl.max());


        System.out.println("Is the main tree balanced? "+(avl.isBalanced()?"It is! :)": "It is not :("));
        System.out.println("\nAVL content sequence\n"+avl.getSequence());
        for (int i = 0; i <5 ; i++) {
            int ramval =Utility.getRandom(99);
            if (avl.contains(ramval)){
                System.out.println("The value ["+ramval+"] will be removed");
                avl.remove(ramval);
            }else{
                i--;
            }

        }
        System.out.println("\nModified AVL content:\n"+avl.toString());
        System.out.println("Is the main tree balanced? "+(avl.isBalanced()?"It is! :)": "It is not :("));


    }
}